package ctrl;

import java.net.URLEncoder;
import java.util.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
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
public class ProductCtrl {
	private ProductSvc productSvc;
	private StyleSvc styleSvc;
	
	public void setStyleSvc(StyleSvc styleSvc) {
		this.styleSvc = styleSvc;
	}
	public void setProductSvc(ProductSvc productSvc) {
		this.productSvc = productSvc;
	}


	@GetMapping("/newProduct")
	public String newProduct(HttpServletRequest request) throws Exception {
		
		List<ProductInfo> productList = productSvc.getNewList(); 
		
		request.setAttribute("productList", productList);
		return "product/newProduct";
	}	
	
	
	@GetMapping("/productView")
	public String productView(Model model, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid");
		System.out.println("piid :" +piid);
		
		List<ProductInfo> productList = productSvc.productView(piid); 
		
		List<ProductStock> stockList = productSvc.productStockView(piid); 
	
		
		//스타일 

		//List<StyleInfo> styleList = styleSvc.getProductViewStyle(piid);
		request.setAttribute("productList", productList); 
		model.addAttribute("stockList", stockList);	
		//model.addAttribute("styleList", styleList);	
		
		return "product/productView";
	}
	
	@GetMapping("/productList")
	public String productList(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
	        
		int cpage = 1, spage = 0, psize = 12, bsize = 10, rcnt = 0, pcnt = 0;
		String gender ="", where = "", schargs ="", init ="";
		
		if(request.getParameter("cpage") != null) 
		cpage = Integer.parseInt(request.getParameter("cpage"));
		

		String pcb = request.getParameter("pcb"); 	
		String sch = request.getParameter("sch"); 
		
		if (sch != null && !sch.equals("")) {
			
			schargs +="&sch=" + sch;
			String[] arrSch = sch.split(",");
			for (int i = 0 ; i < arrSch.length ; i++) {
				char c = arrSch[i].charAt(0);
				if(c == 'n') {
					where += " and a.pi_name like '%" + arrSch[i].substring(1) + "%' ";				
				} else if (c =='b') {
					String[] arr = arrSch[i].substring(1).split(":"); 
					where += " and (";
					for(int j = 0 ; j < arr.length; j++) {
						where += (j == 0 ? "" : " or ") + "a.pb_id ='" + arr[j] + "' "; 
					} 
					where += ") ";
				} else if (c == 'c') {
						String[] arr = arrSch[i].substring(1).split(":"); 
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + "a.pcb_id ='" + arr[j] + "' "; 
						} 
						where += ") ";

				} else if (c =='s') {
						String[] arr = arrSch[i].substring(1).split(":"); 
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + "b.ps_size = " + arr[j]; 
						} 
						where += ") ";	
				}else if (c =='g') {
							String[] arr = arrSch[i].substring(1).split(":"); 
							where += " and (";
							for(int j = 0 ; j < arr.length; j++) {
								where += (j == 0 ? "" : " or ") + "a.pi_gubun ='" + arr[j] + "' "; 
							} 
							where += ") ";
													
				} else if (c =='p') { 
					String sp = arrSch[i].substring(1, arrSch[i].indexOf('~'));
					if (sp !=null && !sp.equals(""))
					where += " and a.pi_price >= " + sp;
					
					String ep = arrSch[i].substring(arrSch[i].indexOf('~') + 1) ;
					if (ep !=null && !ep.equals(""))
					where += " and a.pi_price <= " + ep;
					
				}
			}
		}	
	
		String orderBy = " order by "; 
		String  ob = request.getParameter("ob"); 
		
		if(ob == null || ob.equals(""))  ob = "a";
		String obargs = "&ob=" + ob; 
		switch (ob) {	
		case "a" :
			orderBy += " a.pi_date desc ";  break;
		case "b" : 
			orderBy += " a.pi_sale desc ";  break;
		case "c" : 
			orderBy += " a.pi_price asc ";  break;
		case "d" : 
			orderBy += " a.pi_price desc ";  break;	
		}
		List<ProductInfo> productList = productSvc.getProductList(cpage, psize, where, orderBy);
		
		rcnt = productSvc.getProductCount(where); 
	
		List<ProductBrand> brandList = productSvc.getBrandList(); 
		List<ProductCtgrBig> ctgrList = productSvc.getCtgrList();
		List<ProductStock> stockList = productSvc.getSizeList(); 

		pcnt = rcnt /psize;
		if(rcnt % psize > 0) 	pcnt++;
		spage = (cpage -1) / bsize * bsize + 1;
	
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCpage(cpage);		pageInfo.setSpage(spage);
		pageInfo.setPsize(psize); 		pageInfo.setBsize(bsize);				
		pageInfo.setPcnt(pcnt);		    pageInfo.setRcnt(rcnt);
		pageInfo.setSch(sch);			pageInfo.setOb(ob);
	   	pageInfo.setSchargs(schargs);	pageInfo.setObargs(obargs);   
	   	
	   	
	   	System.out.print("pageInfo.getInit() :" +pageInfo.getInit());
	   	if(pageInfo.getInit() == null) 	pageInfo.setInit(sch);	

		request.setAttribute("pageInfo", pageInfo);	  	 
		request.setAttribute("ctgrList", ctgrList);   	
		request.setAttribute("brandList", brandList); 	  
		request.setAttribute("stockList", stockList);     
		request.setAttribute("productList", productList);
		model.addAttribute("gender", gender);
		return "product/productList";
	}

	@GetMapping("/testWeather")
	public String testWeather(HttpServletRequest request) throws Exception {
		//현재 날짜를 가져옴
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
/*		SimpleDateFormat date1 = new SimpleDateFormat("yyyyMMdd0600");
		SimpleDateFormat date2 = new SimpleDateFormat("yyyyMMdd1800");*/
/*		Date cur = new Date();
		System.out.println(cur);
		
		String dateTime = dateFormat.format(new Date());
		String compareTime1 = date1.format(new Date());
		String compareTime2 = date2.format(new Date());
		System.out.println(dateTime);
		//System.out.println(date1.compareto(date2));
		System.out.println(compareTime2);*/
		
	    
	      
		
	//	System.out.println("dateTime:" + dateTime);
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=eWkcrHqnyDprO5UT9G7wOMwne%2FeXwUDzs6%2FhOXdBNPe5Tb%2FWHjOooTWtBbiZAa%2Ft0kZfv3Y5KPh4sf7umxKrtQ%3D%3D");  /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("stnId","UTF-8") + "=" + URLEncoder.encode("108", "UTF-8")); /*108 전국, 109 서울, 인천, 경기도 등 (활용가이드 하단 참고자료 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("regId","UTF-8") + "=" + URLEncoder.encode("11B00000", "UTF-8")); 
        urlBuilder.append("&" + URLEncoder.encode("tmFc","UTF-8") + "=" + URLEncoder.encode("202309080600", "UTF-8")); /*-일 2회(06:00,18:00)회 생성 되며 발표시각을 입력 YYYYMMDD0600 (1800)-최근 24시간 자료만 제공*/
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
        
        int sum = 0; // 강수량 전체 합계 변수
        for(int i = 0 ; i < itemList.size(); i++ ) {        	
        	JSONObject jo = (JSONObject)itemList.get(i);
    	    for(int j = 0 ; i < rnSt.length; i++) {
    	    	sum += Integer.parseInt((String.valueOf(jo.get(rnSt[i]))));	        	
        	}
        }	
		
        String pcb_id = "a";  // a가 스니커즈
		
		// 강수량이 50%가 넘는다는 뜻(비가 온다는 뜻) 
        if(sum / 8 >= 50 )  pcb_id = "c";  // 샌들/슬리퍼
        
        List<ProductInfo> productList = productSvc.getShoesList(pcb_id);	

        request.setAttribute("itemList", itemList);
        request.setAttribute("productList", productList);
        return "product/testWeather";
 
	}
	
	 @RequestMapping(value = "/trans", produces = "application/text; charset=utf-8")
	   @ResponseBody
	    public static String naverPapago(HttpServletRequest request) throws Exception {
	      request.setCharacterEncoding("utf-8");
	      String clientId = "wBlZUp0siZGe_kgC7560";//애플리케이션 클라이언트 아이디값";
	        String clientSecret = "RF_OLwA8GZ";//애플리케이션 클라이언트 시크릿값";
	        String text = request.getParameter("text");
	        
	        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
	        try {
	           text = URLEncoder.encode(text, "UTF-8");
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("인코딩 실패", e);
	        }

	        Map<String, String> requestHeaders = new HashMap<>();
	        requestHeaders.put("X-Naver-Client-Id", clientId);
	        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

	        String responseBody = post(apiURL, requestHeaders, text);

	        System.out.println("responseBody : " + responseBody);
	        
	        // 번역된 결과를 받아오기 위해 JSON인스턴스 만든 후 setAttribute
	        JSONParser parser = new JSONParser();
	        JSONObject jo = (JSONObject) parser.parse(responseBody);
	        JSONObject message = (JSONObject)jo.get("message");
	        JSONObject result = (JSONObject)message.get("result");
	        String translatedText = (String)result.get("translatedText");
	        
	        return translatedText;
	    }

	    private static String post(String apiUrl, Map<String, String> requestHeaders, String text){
	        HttpURLConnection con = connect(apiUrl);
	        String postParams = "source=en&target=ko&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
	        try {
	            con.setRequestMethod("POST");
	            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	                con.setRequestProperty(header.getKey(), header.getValue());
	            }

	            con.setDoOutput(true);
	            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
	                wr.write(postParams.getBytes());
	                wr.flush();
	            }

	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
	                return readBody(con.getInputStream());
	            } else {  // 에러 응답
	                return readBody(con.getErrorStream());
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("API 요청과 응답 실패", e);
	        } finally {
	            con.disconnect();
	        }
	    }

	    private static HttpURLConnection connect(String apiUrl){
	        try {
	            URL url = new URL(apiUrl);
	            return (HttpURLConnection)url.openConnection();
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
	        } catch (IOException e) {
	            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
	        }
	    }

	    private static String readBody(InputStream body){
	        InputStreamReader streamReader = new InputStreamReader(body);

	        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
	            StringBuilder responseBody = new StringBuilder();

	            String line;
	            while ((line = lineReader.readLine()) != null) {
	                responseBody.append(line);
	            }

	            return responseBody.toString();
	        } catch (IOException e) {
	            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
	        }
	    }
}
