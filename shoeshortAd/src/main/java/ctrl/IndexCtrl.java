package ctrl;


import java.util.*;
import java.io.*;
import java.net.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class IndexCtrl {
	private IndexSvc indexSvc;
	public void setIndexSvc(IndexSvc indexSvc) {
		this.indexSvc = indexSvc;
	}
	@GetMapping("/")
	public String index(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		AdminInfo adminInfo = (AdminInfo)session.getAttribute("loginInfo");
		if(adminInfo != null) {
		IndexData totalData = indexSvc.getTotalData();//1.
		request.setAttribute("td", totalData);
		model.addAttribute("td", totalData);
		return "index";
		}
		return "adLoginForm";
	}
	@PostMapping("/")
	public String loginProc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid").trim().toLowerCase();
		String pwd = request.getParameter("pwd").trim();
		AdminInfo loginInfo = indexSvc.getLoginInfo(uid, pwd);
		
		if (loginInfo == null) {	
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디와 비밀번호가 틀렸습니다.')");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("loginInfo", loginInfo);
		}
		
		return "redirect:/";
	}
}
