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
public class EvtLuckyCtrl {
	private EvtLuckySvc evtLuckySvc;

	public void setEvtLuckySvc(EvtLuckySvc evtLuckySvc) {
		this.evtLuckySvc = evtLuckySvc;
	}

	@GetMapping("/luckyMain")
	public String luckyMain(Model model) throws Exception {
		String pi_id = null;
		List<EvtLuckyInfo> evtList = evtLuckySvc.getLuckyList(pi_id);
		
		model.addAttribute("evtList", evtList);
		return "event/evt_lucky/luckyMain";
	}
	
	@PostMapping("/luckProcIn")
	@ResponseBody
	public void luckProcIn(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception  {		
		System.out.print("luckProcIn");
		int price = Integer.parseInt(request.getParameter("price"));	// 사용자가 입력한 가격
		System.out.println("price :" + price);
		int el_idx = Integer.parseInt(request.getParameter("elidx"));	// 사용자가 입력한 가격
		System.out.println("el_idx :" + el_idx);
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo =(MemberInfo)session.getAttribute("loginInfo");

		int result = 0;
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(loginInfo != null) {
			System.out.println("sada");
			result = evtLuckySvc.luckyInsert(loginInfo.getMi_id(), price, el_idx);
			System.out.println("result :" + result);
			
		} 
			out.println(result);
	}
	
	@GetMapping("/luckyView")
	public String luckyView(HttpServletRequest request, Model model) throws Exception  {		
		request.setCharacterEncoding("utf-8");
		String pi_id = request.getParameter("piid");

		List<EvtLuckyInfo> evtList = evtLuckySvc.getLuckyList(pi_id);
		String edate = evtList.get(0).getEl_edate();
		int minPrice = evtList.get(0).getEl_min_price();
		int maxPrice = evtList.get(0).getEl_max_price();
		
		model.addAttribute("evtList", evtList);
		model.addAttribute("edate", edate);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);
		
		return "event/evt_lucky/luckyView";
	}
	
}
