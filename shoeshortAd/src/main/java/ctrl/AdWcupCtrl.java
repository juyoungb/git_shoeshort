package ctrl;

import java.io.*;
import java.time.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class AdWcupCtrl {
	private  AdWcupSvc adWcupSvc;

	public void setAdWcupSvc(AdWcupSvc adWcupSvc) {
		this.adWcupSvc = adWcupSvc;
	}

	@GetMapping("/adEvtWcupMain")
	public String adEvtMain(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		List<EventWcupInfo> wcupList = adWcupSvc.getEvtWcupList();
		model.addAttribute("wcupList", wcupList);
		return "EventAdmin/adEvtWcup/adWcupMain";
	}
	@GetMapping("/adWcupIn")
	public String adEvtWcupIn(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind");
		
		if(kind.equals("up")) {
			String ewidx = request.getParameter("ewidx");
			List<EventWcupInfo> wcupList = adWcupSvc.getEvtWcupList(ewidx);//由ъ뒪�듃濡� 諛쏆쓣 �븘�슂�뾾�쓬
			model.addAttribute("wl", wcupList.get(0));
		}		
		model.addAttribute("kind",kind);
		return "EventAdmin/adEvtWcup/adWcupIn";
	}
	@PostMapping("/adWcupIn")
	public void adEvtWcupIn(HttpServletRequest request,HttpServletResponse response) throws Exception  {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		AdminInfo adminInfo = (AdminInfo)session.getAttribute("loginInfo");
		
		String kind = request.getParameter("kind");
		String ew_csdate = request.getParameter("ew_csdate"), ew_cedate = request.getParameter("ew_cedate");
		String ew_vsdate = request.getParameter("ew_vsdate"), ew_vedate = request.getParameter("ew_vedate");
		String title = request.getParameter("title"),		 rule = request.getParameter("rule");
		int result=0;
		EventWcupInfo ewi = new EventWcupInfo();
		ewi.setAi_idx(adminInfo.getAi_idx());
		ewi.setEw_csdate(ew_csdate);		ewi.setEw_cedate(ew_cedate);
		ewi.setEw_vsdate(ew_vsdate);		ewi.setEw_vedate(ew_vedate);
		ewi.setEw_title(title);				ewi.setEw_rule(Integer.parseInt(rule));
		
		if(kind.equals("in")) result = adWcupSvc.WcupIn(ewi);
		else {		
			String ewidx = request.getParameter("ewidx");
			String ewstatus = request.getParameter("ewstatus");
			ewi.setEw_status(ewstatus);
			ewi.setEw_idx(Integer.parseInt(ewidx));
			 result = adWcupSvc.WcupUp(ewi);	
		}
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result != 1) {
			out.println("<script>");
			out.println("alert('"+(kind.equals("in")?"등록":"수정")+"이 실패했습니다.');");
			out.println("window.close(); opener.location.reload();");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('"+(kind.equals("in")?"등록":"수정")+"이 실패했습니다.');");
			out.println("window.close(); opener.location.reload();");
			out.println("</script>");
		}
	}
	@PostMapping("/adWcupDel")
	public String adWcupDel(HttpServletRequest request) throws Exception  {
		request.setCharacterEncoding("utf-8");
			String ewidx = request.getParameter("ewidx");
			int result = adWcupSvc.WcupDel(ewidx);	
		return result+"";
	}
	
}
