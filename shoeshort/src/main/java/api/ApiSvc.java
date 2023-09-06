package api;

import java.util.*;
import java.io.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import vo.*;

public class ApiSvc {
	private ApiDao apiDao;
	public void setApiDao(ApiDao apiDao) {
		this.apiDao = apiDao;
	}
	public String getAccessToken(String code) {
		String access_token = "";
		String refresh_token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");	// 요청시 post 방식사용
			conn.setDoOutput(true);			// post방식을 사용하기 위한 셋팅
			
			// post요청에 필요한 파라미터 생성
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=456f5080f677824581cb16867e1a0280");//JavaScript 키는 포트번호 8087일때만 사용가능
			sb.append("&redirect_uri=http://localhost:8087/shoeshort/kakaoLoginProc");
			sb.append("&code=" + code);
			bw.write(sb.toString());
			bw.flush();
			// conn객체에 등록된 url로 bw의 파라미터들을 가지고 실행(연결)
			
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode: "+responseCode);
			//값이 200이면 성공
			
			//요청을 통해 얻은 JSON형식의 Response 메시지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "", result="";
			while((line=br.readLine()) != null){
				result+=line;
			}
			System.out.println("result : " + result);
			//JSON파싱 및 JSONObject객체 생성
			JSONParser p = new JSONParser();
			JSONObject jo = (JSONObject)p.parse(result);
			access_token = (String)jo.get("access_token");
			// 실제 데이터를 가져올 수 있는 코드값
			refresh_token = (String)jo.get("refresh_token");
			
			br.close();			bw.close();
			
		} catch(Exception e) {
			System.out.println("getAccessToken() 메소드 오류");
			e.printStackTrace();
		}
		
		return access_token;
	}

	public HashMap<String, String> getUserInfo(String accessToken) {
		//요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 리턴
		HashMap<String, String> loginInfo = new HashMap<String, String>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn =(HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer "+accessToken);
			
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : "+responseCode);
			//값이 200이면 성공
			
			//요청을 통해 얻은 JSON형식의 Response 메시지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "", result="";
			while((line=br.readLine()) != null){
				result+=line;
			}
			System.out.println("result : " + result);
			
			JSONParser p = new JSONParser();
			JSONObject jo = (JSONObject)p.parse(result);
			JSONObject properties = (JSONObject)jo.get("properties");// Open Api에서 properies에 해당하는 데이터만 가져옴
			JSONObject kakao_account = (JSONObject)jo.get("kakao_account");
			
			System.out.println("kakao_account"+kakao_account);// 제공해주는 데이터 
			String nickname = properties.get("nickname").toString();
			String gender = kakao_account.get("gender").toString();
			String birthday = kakao_account.get("birthday").toString();

			if(kakao_account.containsKey("email")) {//이메일은 없을 수도 있으므로 있을때만 가져옴
				String email  = kakao_account.get("email").toString();
				loginInfo.put("email", email);				
			}
			loginInfo.put("nickname", nickname);
			loginInfo.put("gender", gender);
			loginInfo.put("birthday", birthday);
			//받아온 로그인 정보들을 HashMap인 loginInfo에 저장
			
			}catch(Exception e) {
			System.out.println("getUserInfo() 메소드 오류");
			e.printStackTrace();
		}
		
		return loginInfo;
	}
	public MemberInfo isMember(HashMap<String, String> loginInfo) {
		MemberInfo userInfo = apiDao.isMember(loginInfo);
		return userInfo;
	}
}
