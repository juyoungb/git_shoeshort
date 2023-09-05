package api;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.http.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class ApiCtrl {
	private ApiSvc apiSvc;
	public void setApiSvc(ApiSvc apiSvc) {
		this.apiSvc = apiSvc;
	}
	private LoginSvc loginSvc;
	public void setLoginSvc(LoginSvc loginSvc) {
		this.loginSvc = loginSvc;
	}
	@GetMapping("/kakaoLoginProc")
	public String kakaoLoginProc(HttpServletRequest request, Model model, String code) throws Exception {
		String accessToken = apiSvc.getAccessToken(code);
		HashMap <String,String> loginInfo = apiSvc.getUserInfo(accessToken);
		if(!loginInfo.isEmpty()) {
		MemberInfo userInfo = apiSvc.isMember(loginInfo);
			if(userInfo == null) {
				model.addAttribute("userInfo",loginInfo);
				return "member/joinForm";
			}else {
				MemberInfo mi = loginSvc.getLoginInfo(userInfo.getMi_id(), userInfo.getMi_pw());
				HttpSession session = request.getSession();
				session.setAttribute("loginInfo", mi);
			}
		}else {
			System.out.println("로그인 불가");
		}
		return "redirect:/";	
	}
	@GetMapping("/kakaoLogoutProc")
	public String kakaoLogoutProc() {
		return "redirect:/";
	}
  
}