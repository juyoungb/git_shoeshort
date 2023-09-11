package ctrl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class MainCtrl {
	private ProductSvc productSvc;
	private StyleSvc styleSvc;
	private MainSvc mainSvc;
	
	public void setProductSvc(ProductSvc productSvc) {
		this.productSvc = productSvc;
	}		
	public void setStyleSvc(StyleSvc styleSvc) {
		this.styleSvc = styleSvc;
	}
	public void setMainSvc(MainSvc mainSvc) {
		this.mainSvc = mainSvc;
	}
	
	@GetMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		List<ProductInfo> newList = productSvc.getMainList("pi_date");
		List<ProductInfo> saleList = productSvc.getMainList("pi_sale");
		List<StyleInfo> styleList = styleSvc.getTopStyle();

		MainMedia videoInfo = mainSvc.getVideoInfo();
		List<MainMedia> imgList = mainSvc.getImgList();
		
		model.addAttribute("videoInfo", videoInfo);
		model.addAttribute("imgList", imgList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("newList", newList);
		model.addAttribute("styleList", styleList);
		
		
		Date now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 
		SimpleDateFormat now_format = new SimpleDateFormat("yyyyMMddHHmm"); 
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd0600"); 
		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd1800");
		String tomorrowDate = Calculation(format1.format(now), 0 ,0, 1);
	
		String nowTime ="";

		System.out.println(now_format.format(now).compareTo(format2.format(now)));
		if(now_format.format(now).compareTo(format2.format(now)) < 0) { // 현재 시간이 오후6시 보다 전일 경우
			nowTime = format1.format(now);
			
		}else {
			nowTime = format2.format(now);
		}
		//3일후 날짜 20230913
		int[] nums = new int[5];
		String[] strArray = new String[8];
		
		for (int i = 3; i < 11; i++) {
			String tmp = (Calculation(nowTime.substring(0,8), 0 ,0, i).substring(4));
			strArray[i-3] = tmp.substring(0,2) +"/" +tmp.substring(2);
		}	
		// 날씨 api
	//	StringBuilder urlBuilder = new StringBuilder(URL); /*URL*/
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=eWkcrHqnyDprO5UT9G7wOMwne%2FeXwUDzs6%2FhOXdBNPe5Tb%2FWHjOooTWtBbiZAa%2Ft0kZfv3Y5KPh4sf7umxKrtQ%3D%3D");  /*Service Key*/       
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("stnId","UTF-8") + "=" + URLEncoder.encode("108", "UTF-8")); /*108 전국, 109 서울, 인천, 경기도 등 (활용가이드 하단 참고자료 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("regId","UTF-8") + "=" + URLEncoder.encode("11B00000", "UTF-8")); 
        urlBuilder.append("&" + URLEncoder.encode("tmFc","UTF-8") + "=" + URLEncoder.encode(nowTime, "UTF-8")); /*-일 2회(06:00,18:00)회 생성 되며 발표시각을 입력 YYYYMMDD0600 (1800)-최근 24시간 자료만 제공*/
        System.out.println(urlBuilder);
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();      
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
      
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
             
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(sb.toString());  
        
        JSONObject parse_response = (JSONObject)jsonObject.get("response");     
        JSONObject parse_body = (JSONObject)parse_response.get("body"); // response 로 부터 body 찾아오기
        JSONObject parse_items = (JSONObject)parse_body.get("items"); // body 로 부터 items 받아오기
        
        //필요한 데이터가 items의 item 뒤에 [ 로 시작하므로 jsonArray 
        JSONArray itemList = (JSONArray) parse_items.get("item");
        
        String[] rnSt = {"rnSt3Pm", "rnSt4Pm","rnSt5Pm","rnSt6Pm", "rnSt7Pm", "rnSt8", "rnSt9","rnSt10"};
        JSONObject jo =null;
        int sum = 0; // 강수량 전체 합계 변수
        for(int i = 0 ; i < itemList.size(); i++ ) {        	
        	 jo = (JSONObject)itemList.get(i);
    	    for(int j = 0 ; i < rnSt.length; i++) {
    	    	sum += Integer.parseInt((String.valueOf(jo.get(rnSt[i]))));	        	
    	    	
    	    }
    	   
    	}    
      	
        
        String pcb_id = "a";  // a가 스니커즈
		
		// 강수량이 50%가 넘는다는 뜻(비가 온다는 뜻) 
        if(sum / 8 >= 50 )  pcb_id = "c";  // 샌들/슬리퍼
        
        List<ProductInfo> productList = productSvc.getShoesList(pcb_id);	
    
       
        request.setAttribute("itemList", itemList);
     
        model.addAttribute("productList", productList);
        model.addAttribute("jo", jo);
        model.addAttribute("strArray", strArray);
        
		return "index";
	}
	
	private String Calculation(String nowTime, int year, int month, int day) throws ParseException {
		SimpleDateFormat tmp = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		Date date = tmp.parse(nowTime);
		
		cal.setTime(date);
		
		

		cal.add(Calendar.YEAR,  year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DATE,  day);
			
		return tmp.format(cal.getTime());
	}
	

}