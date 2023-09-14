package ctrl;

import java.io.*;
import java.net.URLEncoder;
import java.security.KeyStore.Entry;
import java.time.*;
import java.util.*;

import javax.print.attribute.standard.PrinterInfo;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import svc.*;
import vo.*;

@Controller
public class AdLuckyCtrl {
	private AdLuckySvc adLuckySvc;

	public void setAdLuckySvc(AdLuckySvc adLuckySvc) {
		this.adLuckySvc = adLuckySvc;
	}

	@GetMapping("/adLuckyList")
	public String adOrderList(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception  {
		int elidx = 0;
		// 페이징에 필요한 변수 선언
		int cpage = 1, spage = 0, psize = 12, bsize = 10, rcnt = 0, pcnt = 0;
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		
		// 전체 레코드 개수를 구함
		rcnt = adLuckySvc.getLuckyCount();
		
		List<LuckyInfo> luckyList = adLuckySvc.getLuckyList(elidx);
		
		pcnt = rcnt / psize;	
		if (rcnt % psize > 0)	pcnt++;
		spage = (cpage - 1) / bsize * bsize + 1;
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);		pageInfo.setCpage(cpage);	pageInfo.setCpage(spage);
		pageInfo.setPsize(psize);		pageInfo.setPcnt(pcnt);		pageInfo.setRcnt(rcnt);
		
		
	
		
		model.addAttribute("luckyList", luckyList);
		model.addAttribute("pageInfo", pageInfo);
		return "EventAdmin/LuckyAdmin/adLuckyList";
	}
	
	//등록,수정 버튼 클릭
	@GetMapping("/adLuckyIn")
	public String adLuckyIn( HttpServletRequest request,HttpServletResponse response, Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		//상태값 (한개에서 처리하기 위해서)in: 등록 up :수정
		String kind = request.getParameter("kind");

		if(kind.equals("up")) { //수정
			int elidx = Integer.parseInt(request.getParameter("elidx"));
			System.out.println("elidx : " + elidx);
			List<LuckyInfo> upLuckyList = adLuckySvc.getLuckyList(elidx);// 수정일때 값을 가져오려고 
			model.addAttribute("ul", upLuckyList.get(0));
		
		}

		
		model.addAttribute("kind", kind);
		return "EventAdmin/LuckyAdmin/adLuckyIn";
	}
	
	@PostMapping("/adLuckyIn")
	public void adLuckyIn( HttpServletRequest request, HttpServletResponse response)   throws Exception  {
		request.setCharacterEncoding("utf-8");
		
		String kind = request.getParameter("kind");
		
		String pi_id = request.getParameter("piid");
		String el_title = request.getParameter("title");
		String el_isview = request.getParameter("isview");	

		String el_sdate = request.getParameter("el_sdate") + " " +request.getParameter("stime");
		String el_edate = request.getParameter("el_edate") + " " +request.getParameter("etime");
	
		int min_price = Integer.parseInt(request.getParameter("min"));	
		int max_price = Integer.parseInt(request.getParameter("max"));	
		
		

		int result = 0;

		
		LuckyInfo li = new LuckyInfo();
		li.setEl_sdate(el_sdate);		
		li.setEl_edate(el_edate);		
		li.setPi_id(pi_id);			
		li.setEl_title(el_title);		
		li.setEl_isview(el_isview);	
		li.setEl_min_price(min_price);
		li.setEl_max_price(max_price);
		
		if(kind.equals("in")) 
		{
			result = adLuckySvc.LuckyIn(li); 	// 등록
		
		}else {		
		
			int el_idx = Integer.parseInt(request.getParameter("elidx"));
			System.out.println("el_idx : " +el_idx);
			li.setEl_idx(el_idx);
			result = adLuckySvc.luckyUp(li);	//수정


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
			out.println("alert('"+(kind.equals("in")?"등록":"수정")+"이 완료되었습니다.');");
			out.println("window.close(); opener.location.reload();");
			out.println("</script>");
		}
		out.close();
	
	}
	
	@PostMapping("/adLuckyDel")
	public void adLuckyDel(HttpServletRequest request,HttpServletResponse response) throws Exception  {
		request.setCharacterEncoding("utf-8");
		System.out.print("delete");
			String elidx = request.getParameter("elidx");
			int result = adLuckySvc.luckyDel(elidx);	
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			
		out.println(result);
	}
		
}
