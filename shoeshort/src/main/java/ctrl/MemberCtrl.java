package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class MemberCtrl {
	private MemberSvc memberSvc;
	
	public void setMemberSvc(MemberSvc memberSvc) {
		this.memberSvc = memberSvc;
	}

	@GetMapping("/memberJoin")
	public String joinForm() {
		return "member/joinForm";
	}
	@GetMapping("/memberSch")
	public String memberSch() {
		return "member/memberSch";
	}
	
	@PostMapping("/memberSch")
	public void schInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind");
	
		MemberInfo mi = new MemberInfo();
		mi.setMi_name(request.getParameter("uname"));
		mi.setMi_email(request.getParameter("email").trim().toLowerCase());
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println("kind :"+kind);
		out.println("<script>");
		if(kind.equals("id")) {
			String mi_id = memberSvc.getMemberId(mi);
			System.out.println(mi_id);
				if (!mi_id.equals("")) {
				out.println("alert('회원님의 아이디는 "+mi_id+"입니다.');");
				out.println("location.href = 'memberSch';");
				}else {
					out.println("alert('일치하는 회원이 없습니다.');");
					out.println("history.back();");
				}
			}else {
			mi.setMi_id(request.getParameter("uid"));
			int result = memberSvc.checkMember(mi);
			if(result != 1) {
				out.println("alert('일치하는 회원이 없습니다.');");
				out.println("history.back();");
			}else {
				
				out.println("alert('회원님의 이메일로 임시비밀번호가 발송되었습니다.');");
				out.println("location.href = 'login';");
			}
		}
		out.println("</script>");
		out.close();
	}
	@PostMapping("/memberJoin")
	public String joinProc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberInfo mi = new MemberInfo();
		mi.setMi_id(request.getParameter("mi_id"));		mi.setMi_pw(request.getParameter("mi_pw"));
		mi.setMi_name(request.getParameter("mi_name"));		mi.setMi_gender(request.getParameter("mi_gender"));
		
		String year = request.getParameter("year"), month = request.getParameter("month"), day = request.getParameter("day");		
		
		mi.setMi_birth(year + "-" + month + "-" + day);
		String p2 = request.getParameter("p2");		String p3 = request.getParameter("p3");
		mi.setMi_phone("010-"+ p2 + "-" + p3);
		String e1 = request.getParameter("e1");		String e2 = request.getParameter("e2");
		mi.setMi_email(e1 + "@" + e2);
		mi.setMa_zip(request.getParameter("zip"));
		mi.setMa_addr1(request.getParameter("addr1"));
		mi.setMa_addr2(request.getParameter("addr2").trim());
		int result = memberSvc.memberInsert(mi);
		if (result != 1) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		return "redirect:/login";
	}

	@PostMapping("/dupId")
	@ResponseBody	
	public String dupId(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid").trim().toLowerCase();
		int result = memberSvc.chkDupId(uid);
		
		return result + ""; 
	}
	
	@GetMapping("/memberInfo")
	public String updateForm(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("loginInfo");
		MemberInfo mi = memberSvc.getMemberInfo(memberInfo.getMi_id(), memberInfo.getMi_pw());
		List<MemberAddr> ma = memberSvc.getMemberAddr(memberInfo.getMi_id());
		model.addAttribute("mi", mi);
		model.addAttribute("ma", ma);
		
		return "member/memberInfo";
	}
	
	@GetMapping("/mypageMain")
	public String mypageMain()  {
		return "member/mypageMain";
	}
	@GetMapping("/pointInfo")
	public String pointInfo(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		String miid=loginInfo.getMi_id();
		List<PointDetail> pointList = memberSvc.getPointList(miid);
		request.setAttribute("pointList", pointList);
		
		return "member/pointInfo";
	}
		
	@PostMapping("/changeInfo")
	public void changeInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		String kind = request.getParameter("kind");
		String data ="";
		if (kind.equals("pw")){
			data = request.getParameter("npwd");
		}else if(kind.equals("email")) {
			data = request.getParameter("e1")+"@"+request.getParameter("e3");
		}else if(kind.equals("phone")) {
			data= "010-"+request.getParameter("p2")+"-"+request.getParameter("p3");
		}
		int result = memberSvc.setMemberInfo(uid,kind,data);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result != 1) {
			out.println("<script>alert('변경에 실패했습니다..'); window.close();</script>");
		}else {
			out.println("<script>alert('변경되었습니다..'); window.close(); opener.location.reload(); </script>");out.close();
		}
	}
	@GetMapping("/changeForm")
	public String changeForm() {
		return "member/changeForm";
	}
	@GetMapping("/addrForm")
	public String addressForm(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind");
		String n = request.getParameter("n");
		MemberAddr ma =new MemberAddr();
		if(kind.equals("up")) {
			int maidx = Integer.parseInt(n);	
			ma = memberSvc.getMemberAddr(maidx);
			ma.getAll();
			System.out.println("test");
		}
		model.addAttribute("ma", ma);
		return "member/addrForm";
	}
	@PostMapping("/addrSet")
	public void addrSet(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		String kind = request.getParameter("kind");
		String uid = request.getParameter("u");
		String maidx = request.getParameter("n");
		String msg ="등록";
		int result=0;
		MemberAddr ma =new MemberAddr();
		if(kind.equals("in")) {	//등록 폼
			ma.setMi_id(uid); 									ma.setMa_rname(request.getParameter("rname")); 
			ma.setMa_name(request.getParameter("name")); 
			ma.setMa_phone(request.getParameter("p1")+"-"+request.getParameter("p2")+"-"+request.getParameter("p3")); 
			ma.setMa_zip(request.getParameter("zip"));
			ma.setMa_addr1(request.getParameter("addr1"));		ma.setMa_addr2(request.getParameter("addr2"));
			ma.setMa_basic(request.getParameter("chkVal"));
			//ma.getAll();					
			result = memberSvc.AddrInsert(ma);	
		} else{// 수정 폼
			msg = "변경";
			ma.setMi_id(uid); 									ma.setMa_idx(Integer.parseInt(maidx)); 
			ma.setMa_rname(request.getParameter("rname")); 		ma.setMa_name(request.getParameter("name")); 
			ma.setMa_phone(request.getParameter("p1")+"-"+request.getParameter("p2")+"-"+request.getParameter("p3")); 
			ma.setMa_zip(request.getParameter("zip"));
			ma.setMa_addr1(request.getParameter("addr1"));		ma.setMa_addr2(request.getParameter("addr2"));
			ma.setMa_basic(request.getParameter("chkVal"));				
			result = memberSvc.AddrUpdate(ma);
		}
		if (result != 1) {
			out.println("<script>alert('"+msg+"에 실패했습니다.');");
			out.println("window.close(); opener.location.reload();</script>");
		}else {
			out.println("<script>alert('추가되었습니다.');");
			out.println("window.close(); opener.location.reload();</script>");
		}
		out.close();
	}
	@PostMapping("/addrDel")
	public String addrDel(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String maidx = request.getParameter("n");
		int result = memberSvc.AddrDelete(maidx);
		
		return result+"";
	}
	
}
