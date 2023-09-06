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
/*select*from t_product_info;
select*from t_order_info;
select*from t_order_detail;

int orderCnt, Allmem, TodayMem, MonthMem
 

1. 주문처리  전체 회원 수  가입 수(오늘 가입 수) 
-주문처리 (orderCnt)
 select count(*) from t_order_info
 where oi_status != 'd' or oi_status != 'e';
- 전체 회원 수 (Allmem)
select count(*) from t_member_info ;
- 가입 수() 
select count(*)
from t_member_info
where date(mi_date) = date_sub(curdate(), interval 1 day);
 
2. 월별 매출액 List (month, total) 가장 최근 5개월
select left(b.oi_date, 7) as month,
       sum(if(a.pi_dc = 0, (a.pi_price - a.pi_cost) * c.ps_idx, ((a.pi_price - (a.pi_price * a.pi_dc)) - a.pi_cost) * c.ps_idx)) total
FROM t_product_info a,t_order_info b,t_order_detail c 
where a.pi_id = c.pi_id and b.oi_id = c.oi_id
group by month order by month desc limit 0, 5;

3. 월별 가입자 수 List (month, total)
select left(mi_date,7) as month, count(*) total
from t_member_info
group by month order by month desc limit 0, 5;

4. 월별 상품 판매 수 List (month, total)
select left(b.oi_date,7) as month, sum(c.ps_idx) stock_cnt
from t_product_info a, t_order_info b, t_order_detail c 
where a.pi_id = c.pi_id and b.oi_id = c.oi_id
group by month order by month desc limit 0, 5;

5. 최근 가입 회원 0,10 List
select * from t_member_info 
order by mi_date desc limit 0, 10;*/