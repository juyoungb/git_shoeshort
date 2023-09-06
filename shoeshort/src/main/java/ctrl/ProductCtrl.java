package ctrl;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.*;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;

import svc.*;
import vo.*;

@Controller
public class ProductCtrl {
	private ProductSvc productSvc;
	
	public void setProductSvc(ProductSvc productSvc) {
		this.productSvc = productSvc;
	}
	
	// New/�Ż�ǰ List�����ִ� @GetMapping
	@GetMapping("/newProduct")
	public String newProduct(HttpServletRequest request) throws Exception {
		
		List<ProductInfo> productList = productSvc.getNewList(); 
		
		request.setAttribute("productList", productList);
		return "product/newProduct";
	}	
	
	// ��ǰ Ŭ���� view�� �̵��ϴ� @GetMapping
	@GetMapping("/productView")
	public String productView(Model model, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid");
		
		List<ProductInfo> productList = productSvc.productView(piid); // ��ǰ�� ���� ������ �������°�
		
		List<ProductStock> stockList = productSvc.productStockView(piid); // ��ǰ�� ���� �Ź� ������ �������°�
	
		
		request.setAttribute("productList", productList); 
		model.addAttribute("stockList", stockList);	
		
		return "product/productView";
	}
	
	
	// ��з�(����,����,Ű��) Ŭ���� List�� �̵��ϴ� @GetMapping
	@GetMapping("/productList")
	public String productList(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		request.setCharacterEncoding("utf-8");

		int cpage = 1, spage = 0, psize = 12, bsize = 10, rcnt = 0, pcnt = 0;
		String gender ="", s = "", where = "", schargs ="";
		
		if(request.getParameter("cpage") != null) 
		cpage = Integer.parseInt(request.getParameter("cpage"));
		
		
		gender = request.getParameter("g"); 
		if(gender != null ) where += " and a.pi_gubun= '"+ gender +"' ";
		
		String pcb = request.getParameter("pc	b"); // ��з� ����		
		String sch = request.getParameter("sch"); // �˻� ����(���ݴ� : p, ��ǰ�� : n, �귣�� : b, ������ :s, ����:m)
		
		
		if (sch != null && !sch.equals("")) {// �˻����� : &sch=ntest,bB1:B2:B3,p100000~20000	
			schargs +="&sch=" + sch;
			String[] arrSch = sch.split(",");
			for (int i = 0 ; i < arrSch.length ; i++) {
				char c = arrSch[i].charAt(0);
				if(c == 'n') {// ��ǰ�� �˻��� ���(n �˻���)
					where += " and a.pi_name like '%" + arrSch[i].substring(1) + "%' ";
					
				} else if (c =='b') {// �귣�� �˻��� ���(b �귣�� 1:�귣��2)
				// where +=" and (a.pb_id = '��1' or a.pb_id = '��2')";
					String[] arr = arrSch[i].substring(1).split(":"); // :�� ��� �������
					where += " and (";
					for(int j = 0 ; j < arr.length; j++) {
						where += (j == 0 ? "" : " or ") + "a.pb_id ='" + arr[j] + "' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
					} 
					where += ") ";
					
				} else if (c =='s') {// �귣�� �˻��� ���(s ������ 1:������2)
						s = "k";
						String[] arr = arrSch[i].substring(1).split(":"); // :�� ��� �������
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + "b.ps_size = " + arr[j]; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
							
						} 
						where += ") ";
						
						
				}else if (c =='g') {// �귣�� �˻��� ���(s �귣�� 1:�귣��2)
						// where +=" and (a.pb_id = '��1' or a.pb_id = '��2')";
							String[] arr = arrSch[i].substring(1).split(":"); // :�� ��� �������
							where += " and (";
							for(int j = 0 ; j < arr.length; j++) {
								where += (j == 0 ? "" : " or ") + "a.pi_gubun ='" + arr[j] + "' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
							} 
							where += ") ";
													
				} else if (c =='p') { // ���ݴ� �˻��� ���(p���۰�~���ᰡ (�̻�~����))
					String sp = arrSch[i].substring(1, arrSch[i].indexOf('~'));
					if (sp !=null && !sp.equals(""))
					where += " and a.pi_price >= " + sp;
					
					String ep = arrSch[i].substring(arrSch[i].indexOf('~') + 1) ;
					if (ep !=null && !ep.equals(""))
					where += " and a.pi_price <= " + ep;
					
				}
			}
		}	
		
		String orderBy = " order by "; // ��� ���� ���� 
		String  ob = request.getParameter("ob"); // ���� ����
		//System.out.println(ob);
		
		if(ob == null || ob.equals(""))  ob = "a";
		String obargs = "&ob=" + ob; // ���� ������ ���� ������Ʈ��
		switch (ob) {	
		case "a" : //�ֽż�(�⺻��)
			orderBy += " a.pi_date desc ";  break;
		case "b" : // �Ǹŷ�(�α��)
			orderBy += " a.pi_sale desc ";  break;
		case "c" : // ���� ���ݼ�
			orderBy += " a.pi_price asc ";  break;
		case "d" : // ���� ���ݼ�
			orderBy += " a.pi_price desc ";  break;	
		}
		
		System.out.println(where);
		List<ProductInfo> productList = productSvc.getProductList(cpage, psize, where, orderBy,s);//�˻��� ��ǰ�� �� ���� ���������� ������ ��ǰ ����� �޾ƿ�

		rcnt = productSvc.getProductCount(where); // �˻��� ��ǰ�� �� ������ ��ü ���������� ���� �� ����
		
	
		
		List<ProductBrand> brandList = productSvc.getBrandList(); // �˻� �������� ������ �귣����� ����� �޾ƿ�
		List<ProductCtgrBig> ctgrList = productSvc.getCtgrList(); // �˻� �������� ������ ī�װ����� ����� �޾ƿ�
		List<ProductStock> stockList = productSvc.getSizeList(); // �˻� �������� ������ ��������� ����� �޾ƿ�

		pcnt = rcnt /psize;
		if(rcnt % psize > 0) 	pcnt++;
		spage = (cpage -1) / bsize * bsize + 1;
		
		// ����¡�� ��ũ�� ���õ� �������� pageInfo�� �ν��Ͻ��� ����
		
		
			
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCpage(cpage);		pageInfo.setSpage(spage);
		pageInfo.setPsize(psize); 		pageInfo.setBsize(bsize);				
		pageInfo.setPcnt(pcnt);		    pageInfo.setRcnt(rcnt);
		pageInfo.setSch(sch);			pageInfo.setOb(ob);
	   	pageInfo.setSchargs(schargs);	pageInfo.setObargs(obargs);   
	   	pageInfo.setGender(gender);   

	   	

		request.setAttribute("pageInfo", pageInfo);	  	  // ������ ����
		request.setAttribute("ctgrList", ctgrList);   	  // ī�װ� 
		request.setAttribute("brandList", brandList); 	  // �귣��
		request.setAttribute("stockList", stockList);     // ������ ����Ʈ
		request.setAttribute("productList", productList); // ��ǰ ����Ʈ
		model.addAttribute("gender", gender);
		return "product/productList";
	}
	
	@GetMapping("/testWeather")
	public String testWeather(HttpServletRequest request) throws Exception {
		//현재 날짜를 가져옴
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
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
        urlBuilder.append("&" + URLEncoder.encode("tmFc","UTF-8") + "=" + URLEncoder.encode("202309060600", "UTF-8")); /*-일 2회(06:00,18:00)회 생성 되며 발표시각을 입력 YYYYMMDD0600 (1800)-최근 24시간 자료만 제공*/
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
}
