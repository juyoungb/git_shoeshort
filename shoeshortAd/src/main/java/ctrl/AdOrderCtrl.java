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
		String sdate = request.getParameter("sdate");	
		String edate = request.getParameter("edate");
		String schtype = request.getParameter("schtype");
		String keyword = request.getParameter("keyword");
		String args = "", schargs="";
		args = "&cpage=" + cpage;
		
		System.out.println("schtype:"+schtype);
		System.out.println("keyword:"+keyword);
		if(keyword == null) System.out.println("keyword empty:"); 
		if(schtype != null && keyword != null) {
			System.out.println(1231231);
			System.out.println("schtype: "+schtype);
			if(!schtype.equals("all")) {
				where+=" and "+schtype+" like '%"+keyword+"%' ";
			}else {
				where+=" and( oi_name like '%"+keyword+"%' or oi_id like '%"+keyword+"%' )";
			}
		}
		
		if(sdate != "" && sdate != null) where+=" and date(oi_date) >= date('"+sdate+"') ";
		if(edate != "" && edate != null) where+=" and date(oi_date) <= date('"+edate+"') ";
		if(period != null) {
			for(String per:period) {
				System.out.println(per);//t, w, m
				if(per.equals("t")) where+=" and date(oi_date) = date(now()) ";
				if(per.equals("w")) where+=" and (date(oi_date) >= date(now())-7 and date(oi_date) <= date(now())) ";
				if(per.equals("m")) where+=" and (date(oi_date) >= date(now())-30 and date(oi_date) <= date(now())) ";
			}
		}
		if(status != null) {
			if(!status[0].equals("all")) {
			for(int i=0; i<status.length; i++) {
				if(i == 0) where+=" and (oi_status='"+status[i]+"' ";
				else where+=" or oi_status='"+status[i]+"' ";
			}
			where+=") ";
			}
		}
		//-------------------------------------------------------------
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
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("orderList", orderList);
		
		return "OrderAdmin/adOrderList";
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

}
