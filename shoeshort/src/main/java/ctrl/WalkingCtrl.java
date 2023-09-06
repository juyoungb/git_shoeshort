package ctrl;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.http.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class WalkingCtrl {
	private WalkingSvc walkingSvc;

	public void setWalkingSvc(WalkingSvc walkingSvc) {
		this.walkingSvc = walkingSvc;
	}
	@GetMapping("/walkingList")
	public String walkingList(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, pcnt = 0, spage = 0, rcnt = 0, psize = 10, bsize = 10, num = 0;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null) 
			psize = Integer.parseInt(request.getParameter("psize"));
		
		// 1. URL을 만들기 위한 StringBuilder.
        StringBuilder urlBuilder = new StringBuilder("https://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_L_TRKROAD"); /*URL*/
        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        urlBuilder.append("&" + URLEncoder.encode("key","UTF-8") + "=3864148A-EDE4-3C0C-A5BF-15A1A894D2C7"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("domain","UTF-8") + "=http://localhost:8088/shoeshort/walkingList.jsp"); /*domain*/
        urlBuilder.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("size","UTF-8") + "=" + URLEncoder.encode(psize + "", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("page","UTF-8") + "=" + URLEncoder.encode(cpage + "", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("geomFilter","UTF-8") + "=" + URLEncoder.encode("BOX(13663271.680031825,3894007.9689600193,14817776.555251127,4688953.0631258525)", "UTF-8")); /*지오메트리 필터*/
         urlBuilder.append("&" + URLEncoder.encode("attribute","UTF-8") + "=" + URLEncoder.encode("true", "UTF-8")); /*속성 반환 여부*/
        urlBuilder.append("&" + URLEncoder.encode("crs","UTF-8") + "=" + URLEncoder.encode("EPSG:3857", "UTF-8")); /*응답결과 좌표계*/
        // urlBuilder.append("&" + URLEncoder.encode("geometry","UTF-8") + "=" + URLEncoder.encode("false", "UTF-8")); /*지오메트리 반환 여부*/
        
        // 3. URL 객체 생성.
        URL url = new URL(urlBuilder.toString());
        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");
        // 6. 통신을 위한 Content-type SET. 
        conn.setRequestProperty("Content-type", "application/json");
        // 7. 통신 응답 코드 확인.
        System.out.println("Response code: " + conn.getResponseCode());
        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
       
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

        JSONParser p = new JSONParser();
        JSONObject jo = (JSONObject)p.parse(sb.toString());
        System.out.println("jo : " + jo);
        JSONObject response = (JSONObject)jo.get("response");
        JSONObject record = (JSONObject)response.get("record");
        
        JSONObject result = (JSONObject)response.get("result");
        JSONObject featureCollection = (JSONObject)result.get("featureCollection" );
        
        JSONArray features = (JSONArray)featureCollection.get("features");

        rcnt = Integer.parseInt(record.get("total").toString());
        
        pcnt = rcnt / psize; if(rcnt % psize > 0)pcnt++;
		spage = (cpage - 1) / bsize * bsize + 1;
		num = rcnt - (psize *(cpage - 1));
		
        PageInfo pi = new PageInfo();
        pi.setBsize(bsize);			pi.setCpage(cpage);
		pi.setPcnt(pcnt);			pi.setPsize(psize);	
		pi.setRcnt(rcnt);			pi.setSpage(spage);		
		pi.setNum(num);
        // 페이징을 위한 정보를 PageInfo형 인스턴스 pi에 저장

        request.setAttribute("pi", pi);
        request.setAttribute("features", features);
        
        return "walkingTrails/walkingList";
    }
}
