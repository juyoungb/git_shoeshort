package ctrl;
import java.io.*;
import java.net.URLEncoder;
import java.time.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class AdOrderCtrl {
	private AdOrderSvc adOrderSvc;

	public void setAdOrderSvc(AdOrderSvc adOrderSvc) {
		this.adOrderSvc = adOrderSvc;
	}
	@GetMapping("/adOrderList")
	public String adOrderList(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 5, rcnt = 0, pcnt = 0;
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		String where =" where 1=1 ";
		String[] period = request.getParameterValues("period");
		String[] status = request.getParameterValues("status");
		String sdate = (request.getParameter("sdate") == null ? "" :  request.getParameter("sdate"));
		String edate = (request.getParameter("edate") == null ? "" :  request.getParameter("edate"));
		String schtype = (request.getParameter("schtype") == null ? "" :  request.getParameter("schtype"));
		String keyword = (request.getParameter("keyword") == null ? "" :request.getParameter("keyword").trim());
		String args = "", schargs="", periods="",status2="";
		args = "&cpage=" + cpage;

		if(!schtype.equals("") && !keyword.equals("")) {
			if(!schtype.equals("all")) {
				where+=" and "+ (schtype.equals("oi_id")? "a.":"c.") + schtype +" like '%"+keyword+"%' ";
			}else {
				where+=" and( mi_name like '%"+keyword+"%' or a.oi_id like '%"+keyword+"%' )";
			}
		}
		
	//--------------------------------------
		if(sdate != "" && sdate != null) where+=" and date(oi_date) >= date('"+sdate+"') ";
		if(edate != "" && edate != null) where+=" and date(oi_date) <= date('"+edate+"') ";
		if(period != null) {
			if(!period[0].equals("all")) {
			where +=" and (";
			for(int i=0; i<period.length; i++) {
				periods+=","+period[i];
				System.out.println(period[i]);//t, w, m
				if(i >0) where+=" or ";
				if(period[i].equals("t")) where+=" date(oi_date) = date(now()) ";
				else if(period[i].equals("w")) where+=" (date(oi_date) >= date(now())-7 and date(oi_date) <= date(now())) ";
				else if(period[i].equals("m")) where+=" (date(oi_date) >= date(now())-30 and date(oi_date) <= date(now())) ";
			}
			where +=")";
			}
		}
		if(status != null) {
			if(!status[0].equals("all")) {
			for(int i=0; i<status.length; i++) {
				status2+=","+status[i];
				System.out.println(status[i]);
				if(i == 0) where+=" and (oi_status='"+status[i]+"' ";
				else where+=" or oi_status='"+status[i]+"' ";
			}
			where+=") ";
			}
		}
		//-------------------------------------------------------------
		System.out.println("status2 :"+status2);
		System.out.println("where :"+where);
		rcnt = adOrderSvc.getOrderCount(where);
		
		System.out.println("rcnt"+rcnt);
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++;
		
		List<OrderInfo> orderList = adOrderSvc.getMemberList(cpage, psize, where);
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);
		pageInfo.setSdate(sdate);			pageInfo.setEdate(edate);
		if(!periods.equals("")) pageInfo.setPeriods(periods.substring(1));
		if(!status2.equals("")) pageInfo.setStatus2(status2.substring(1));
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("orderList", orderList);
		
		return "orderAdmin/adOrderList";
	}
	@PostMapping("/orderStatus")
	public void orderForm(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		String schOrder = request.getParameter("schOrder");
		String[] arr = request.getParameterValues("chk");
		String where = " where "; 
		for(int i=1 ; i<arr.length ; i++) {
			if(i == 1) where +="oi_id ='"+arr[i]+"' ";
			else	   where +=" or oi_id ='"+arr[i]+"' ";
		}
		int result = adOrderSvc.upOrderStatus(where,schOrder);
		System.out.println("result"+result);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result <= 0) {
			out.println("<script>");
			out.println("alert('처리에 실패했습니다.');");
			out.println("location.href = 'adOrderList';");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('처리가 완료되었습니다.');");
			out.println("location.href = 'adOrderList';");
			out.println("</script>");
		}
	}
	@GetMapping("/orderDetail")
	public String orderDetail(HttpServletRequest request, Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		String oiid = request.getParameter("oiid");
		String uid = request.getParameter("uid");
		
		OrderInfo orderInfo = adOrderSvc.getOrderInfo(uid,oiid);
		MemberInfo memberInfo = adOrderSvc.getMemberInfo(uid);
		System.out.println(orderInfo.getOi_id());
		model.addAttribute("oi", orderInfo);
		model.addAttribute("mi", memberInfo);
		return "orderAdmin/adOrdDetail";
	}

}
