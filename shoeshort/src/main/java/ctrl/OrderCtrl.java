package ctrl;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class OrderCtrl {
	private OrderSvc orderSvc;
	private MemberSvc memberSvc;
	
	public void setOrderSvc(OrderSvc orderSvc) {
		this.orderSvc = orderSvc;
	}
	public void setMemberSvc(MemberSvc memberSvc) {
		this.memberSvc = memberSvc;
	}
	
	@GetMapping("/orderInfo")
	public String orderInfo(HttpServletRequest request, Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		List<OrderInfo> orderList = orderSvc.getOrderList(mi.getMi_id());
		model.addAttribute("orderList", orderList);
		return "order/orderInfo";
	}
	
	@GetMapping("/orderDetail")
	public String orderDetail(HttpServletRequest request, Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		String oiid = request.getParameter("ol");
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		
//		System.out.println(oiid);
		MemberInfo memberInfo = memberSvc.getMemberInfo(mi.getMi_id(), mi.getMi_pw());
		OrderInfo orderInfo = orderSvc.getOneOrderInfo(mi.getMi_id(),oiid);
		model.addAttribute("oi", orderInfo);
		model.addAttribute("mi", memberInfo);
		return "order/orderDetail";
	}
	
	@GetMapping("/orderCart")
	public String orderCart(HttpServletRequest request, Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		System.out.println(mi.getMi_id());
		List<OrderCart> cartList = orderSvc.getCartList(mi.getMi_id());
		model.addAttribute("cartList", cartList);
		
		return "order/orderCart";
	}	
	
	@RequestMapping("/cartView")
	public String cartView(HttpServletRequest request, Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		System.out.println("orderCart");
		String piid = request.getParameter("piid");
		System.out.println("piid :" + piid);
		HttpSession session = request.getSession();
		
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		System.out.println(mi.getMi_id());
		List<OrderCart> cartList = orderSvc.getCartList(mi.getMi_id());
		model.addAttribute("cartList", cartList);
		System.out.println(cartList.get(0).getPs_idx());
		
		return "order/cartView";
	}
	
	@PostMapping("/cartUpCnt")
	public void cartUpCnt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		int ocidx = Integer.parseInt(request.getParameter("ocidx"));	
		int cnt = Integer.parseInt(request.getParameter("cnt"));
		int opt = Integer.parseInt(request.getParameter("opt"));
		
		HttpSession session = request.getSession();
		MemberInfo mi =(MemberInfo)session.getAttribute("loginInfo");	
		isLogin(response,mi);	
		
		OrderCart oc = new OrderCart();
		oc.setOc_idx(ocidx);
		oc.setMi_id(mi.getMi_id());
		oc.setOc_cnt(cnt);
		oc.setPs_idx(opt+"");
		System.out.println("opt"+oc.getPs_idx());
		int result = orderSvc.cartUpCnt(oc);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
	}


	@PostMapping("/cartProcIn")
	@ResponseBody
	public void cartProcIn(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception  {		
		request.setCharacterEncoding("utf-8");	
		String piid = request.getParameter("piid");	
		int pssize = Integer.parseInt(request.getParameter("psidx"));
		
		int cnt = Integer.parseInt(request.getParameter("cnt"));

		//System.out.println(piid);
		
		System.out.println(cnt);
		HttpSession session = request.getSession();
		MemberInfo loginInfo =(MemberInfo)session.getAttribute("loginInfo");
		String miid = loginInfo.getMi_id();	
		
		OrderCart oc = new OrderCart();
		oc.setMi_id(miid);		    oc.setPi_id(piid);
		oc.setPs_size(pssize);		oc.setOc_cnt(cnt);
		//System.out.println(piid+cnt+pssize+miid);
		int result = orderSvc.cartInsert(oc);
		//System.out.println("result :" + result);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
	}
	
	@PostMapping("/cartDel")
	public void cartDel(HttpServletRequest request, HttpServletResponse response) throws Exception  {
			request.setCharacterEncoding("utf-8");
			String ocidx =request.getParameter("ocidx");
			HttpSession session = request.getSession();
			MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
			System.out.println(ocidx);
			isLogin(response,mi);
			
			String where =" where mi_id='"+mi.getMi_id()+"' ";
			if (ocidx.indexOf(',') >= 0) {
				String arr[] = ocidx.split(",");
				for(int i=0; i<arr.length; i++) {
					if(i==0)	where+=" and (oc_idx="+arr[i];
					else		where+=" or oc_idx="+arr[i];
				}
				where+= ")";
			} else {
				where +=" and oc_idx ="+ ocidx;
			}
			int result = orderSvc.cartDel(where);
			System.out.println("result:"+result);
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(result);
		}

	@PostMapping("/orderForm")
	public String orderForm(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind");
		System.out.println("orderForm");
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		isLogin(response,mi);
		
		String miid = mi.getMi_id();
		String select ="select a.pi_id, a.pi_name, a.pi_img1, b.ps_size, " + 
				" if(a.pi_dc >0, round(a.pi_price*(1-a.pi_dc)), a.pi_price) price, ",
			   from = " from t_product_info a, t_product_stock b ", 
			   where = " where a.pi_id =b.pi_id and a.pi_isview ='y' and b.ps_isview ='y' ";
		if(kind.equals("c")){	
			String oc = request.getParameter("oc");
			select += " c.oc_cnt cnt, c.oc_idx";
			from += ", t_order_cart c ";
			where += "and a.pi_id = c.pi_id and b.ps_idx =c.ps_idx and c.mi_id='"+miid+"' and(";

			if(oc.equals("")) {
				String[] arr = request.getParameterValues("chk");
				for(int i=1 ; i<arr.length ; i++) {
					if(i == 1) where +="c.oc_idx ="+arr[i];
					else	   where +=" or c.oc_idx ="+arr[i];
				}
				
			}else {
				where +="c.oc_idx ="+oc;
			}			
			where +=") order by a.pi_id, c.ps_idx";
		} else {
			System.out.println("바로구매");
			String piid = request.getParameter("piid");
			int size = Integer.parseInt(request.getParameter("size"));
			int cnt = Integer.parseInt(request.getParameter("cnt"));
			
			System.out.println(piid+":"+size+":"+cnt);
			select +=cnt + " cnt ";
			where +=" and a.pi_id ='"+piid+"' and b.ps_size ="+size;
			
		}
		
		List<OrderCart> pdtList = orderSvc.getBuyList(kind, select+from+where);
		System.out.println("pdtList.size() :"+pdtList.size());
		List<MemberAddr> addrList = orderSvc.getAddrList(miid);
		System.out.println(pdtList.get(0).getPi_name());
		request.setAttribute("detailList", pdtList.get(0));
		model.addAttribute("pdtList", pdtList);	
		model.addAttribute("addrList", addrList);
		model.addAttribute("kind", kind);
		
		return "order/orderForm";
	}
	@PostMapping("/orderIn")
	public void orderIn(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		isLogin(response,mi);
		
		String kind  = request.getParameter("kind"),
			   ocidxs= request.getParameter("ocidxs"); 
		int    total = Integer.parseInt(request.getParameter("total"));
		/*od.getPi_id(), od.getPs_idx(), od.getOd_cnt(), od.getOd_price(),od.getOd_name(), od.getOd_img(),od.getOd_size()*/
		String oi_name =request.getParameter("oi_name").trim();
		String p2 = request.getParameter("p2"), p3 = request.getParameter("p3");
		String oi_phone = "010-"+p2+"-"+p3;
		String oi_zip = request.getParameter("oi_zip").trim();
		String oi_addr1 = request.getParameter("oi_addr1").trim(), oi_addr2 = request.getParameter("oi_addr2").trim();
		String oi_payment = request.getParameter("oi_payment");
		
		OrderInfo oi = new OrderInfo();
		oi.setMi_id(mi.getMi_id());		oi.setOi_name(oi_name);
		oi.setOi_phone(oi_phone);		oi.setOi_zip(oi_zip);
		oi.setOi_addr1(oi_addr1);		oi.setOi_addr2(oi_addr2);
		oi.setOi_payment(oi_payment);	oi.setOi_pay(total);
		oi.setOi_spoint(Math.round(total *0.002f));//double -> float
		oi.setOi_status(oi_payment.equals("c")? "a":"b");//?
		System.out.println("piid"+request.getParameter("piid"));
		if(!kind.equals("c")) {// cnt, size, piid
			oi.setCnt(request.getParameter("cnt"));
			oi.setPiid(request.getParameter("piid"));
			oi.setSize(request.getParameter("pssize"));
		}
		String result = orderSvc.orderIn(kind, oi, ocidxs);
		String[] arr = result.split(",");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(arr[1].equals(arr[2])) {
			out.println("<form name='frm' action='orderEnd' method='post'>");			
			out.println("<input type='hidden' name='oiid' value='"+arr[0]+"' />");
			out.println("</form>");
			out.println("<script>");
			out.println("document.frm.submit();");			
			out.println("</script>");	
		} else{		
			System.out.println("result"+arr[1]+"!!"+arr[2]);
			out.println("<script>alert(''); history.back();</script>");
		}
		out.close();
	}
	@PostMapping("orderEnd")
	public String orderEnd(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		String oiid = request.getParameter("oiid");
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		
		System.out.println(oiid);
		MemberInfo memberInfo = memberSvc.getMemberInfo(mi.getMi_id(), mi.getMi_pw());
		OrderInfo orderInfo = orderSvc.getOneOrderInfo(mi.getMi_id(),oiid);
		model.addAttribute("oi", orderInfo);
		model.addAttribute("mi", memberInfo);
		return "order/orderEnd";
	}
	//------------------------------------------------------------------------------------------
	public void isLogin(HttpServletResponse response,MemberInfo mi) throws Exception {
		if(mi == null) {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('로그인을 하십시오.'); "
				+ "location.href='login_form';</script>");
		out.close();
		}
	}
}