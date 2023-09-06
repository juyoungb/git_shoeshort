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
public class EventWcupCtrl {
	private EventWcupSvc eventWcupSvc;
	
	public void seteventWcupSvc(EventWcupSvc eventWcupSvc) {
		this.eventWcupSvc = eventWcupSvc;
	}
	
	@GetMapping("/wcup")
	public String eventList(HttpServletRequest request, Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		request.setAttribute("url", "wcup");
		EventWcupInfo voteList = (EventWcupInfo)eventWcupSvc.getEventList("b");	
		EventWcupInfo recruitList = (EventWcupInfo)eventWcupSvc.getEventList("a");
		if(voteList != null || recruitList != null ) {		 		
		List<WcupDetail> detailList = eventWcupSvc.getEventDList(voteList.getEw_idx());
		model.addAttribute("el", voteList);
		model.addAttribute("rl", recruitList);
		model.addAttribute("detailList", detailList);
		} 

		return "event/evt_wcup/wcupList"; 
	}
	@GetMapping("/wcupJoin")
	public String wcupJoin(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		String ewidx = request.getParameter("n");
		
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		isLogin(response,mi);//로그인 여부 출력
		int result= userValid(response,"list",mi.getMi_id(),ewidx);
		if(result > 0) return null;
		else {//스타일이 등록되어 있고 월드컵에 참여하지 않은 회원일 경우
			List<StyleInfo> styleList = eventWcupSvc.getStyleList(mi.getMi_id());
			model.addAttribute("styleList",styleList);
			model.addAttribute("ewidx", ewidx);
		}
		
		return "event/evt_wcup/wcupJoin"; 
	}
	@PostMapping("/wcupVol")
	public void wcupVol(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		
		int siidx =Integer.parseInt(request.getParameter("chk"));
		int ewidx =Integer.parseInt(request.getParameter("ewidx"));
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		isLogin(response,mi);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		int result= eventWcupSvc.joinWcup(siidx,ewidx,mi.getMi_id());	
		if(result != 1) {
			out.println("<script>");
			out.println("alert('이미참여하였습니다.');");
			out.println("window.close(); opener.location.reload();");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('참여가 완료 되었습니다.');");
			out.println("window.close(); opener.location.reload();");
			out.println("</script>");
		}
		out.close();
	}
	@GetMapping("/wcupGame")
	public String wcupGame(HttpServletRequest request,HttpServletResponse response,  Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		isLogin(response,mi);
		int ewidx =Integer.parseInt(request.getParameter("gi")); 
		model.addAttribute("ewidx",  ewidx);	
		EventWcupInfo eventList = (EventWcupInfo)eventWcupSvc.getEventList("b");
		List<WcupDetail> detailList = eventWcupSvc.getEventDList(eventList.getEw_idx()); 

		model.addAttribute("el", eventList);
		model.addAttribute("dl", detailList);
		return "event/evt_wcup/wcupGame"; 
	}
	@GetMapping("/wcupEnjoy")
	public String wcupEnjoy(HttpServletRequest request,HttpServletResponse response,  Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		String uid =request.getParameter("uid"), title=request.getParameter("title");
		int ewidx=Integer.parseInt(request.getParameter("ewidx")), seq =Integer.parseInt(request.getParameter("seq"));				
		String sys=request.getParameter("sys");
		
		int ewrule =Integer.parseInt(request.getParameter("ewrule"));	
		int rs= userValid(response,"join",uid,request.getParameter("ewidx"));
		List<WcupDetail> detailList = null;
		detailList = eventWcupSvc.getEventDList(ewidx);
		
		String img1="",img2="",imgs ="", winner="";
		String[][] arr = new String[2][ewrule];
		String rand =request.getParameter("rand");
		if(rand.equals("0")) {//wcupMain to wcupEnjoy
			rand =wcupSeq(ewrule); //4231
		}
		for(int i=0; i<ewrule; i++){//Random sequence
			arr[0][Integer.parseInt(rand.split("")[i])-1] = detailList.get(i).getSi_img();
			arr[1][Integer.parseInt(rand.split("")[i])-1] = detailList.get(i).getMi_id();
		}
		for(int i=0; i<ewrule; i++) {//1234
			if(sys.split("")[i].equals("1")) {
			imgs+= ","+arr[0][i];
			winner+= ","+arr[1][i];
			}
		}

		if(ewrule == 4 || ewrule == 8) {
			String[] images = imgs.substring(1).split(",");
			if(seq == 1 || seq == 5 || seq == 7 || (seq == 3 && ewrule == 4)) { // [11]111111
				img1=images[0];
				img2=images[1];			
			}else if(seq == 2 || seq == 6) {// 01[11]1111
				img1=images[1];
				img2=images[2];
			}else if(seq == 3 && ewrule == 8) {// 0101[11]11
				img1=images[2];
				img2=images[3];
			}else if(seq == 4 && ewrule == 8 ) {// 010101[11]
				img1=images[3];
				img2=images[4];					
			}else {
				img1=images[0];		
			}
/* 
1. [11]111111 - [0], [1]	
2. 01[11]1111 - [1], [2]
3. 0101[11]11 - [2], [3]
4. 010101[11] - [3], [4]
5. [0101]0101 - [0], [1]				
6. 0001[0101] - [1], [2]
7. 00010001	  - [0], [1]
8. 00000001	  - [0]
*/
		}		
		
		EventWcupInfo ewi = new EventWcupInfo();
		ewi.setEw_title(title);	ewi.setEw_idx(ewidx); ewi.setSys(sys);
		ewi.setEw_rule(ewrule);	ewi.setMi_id(uid); ewi.setSeq(seq);
		ewi.setImg1(img1);		ewi.setImg2(img2); ewi.setRand(rand);
		ewi.setWinner(winner.substring(1));
		model.addAttribute("ewi", ewi);

		return "event/evt_wcup/wcupEnjoy";
	}
	@PostMapping("/wcupWin")
	public void wcupWin(HttpServletRequest request,HttpServletResponse response,  Model model) throws Exception  {
		//transaction 걸어줘야함
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		response.setContentType("text/html; charset=utf-8");
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		isLogin(response,mi);
		String ewidx =request.getParameter("ewidx");
		String winner=request.getParameter("winner");
		String rule=request.getParameter("rule");
		int result = eventWcupSvc.wcupWin(mi.getMi_id(),rule,winner,ewidx);
		//System.out.println("wcupWin"+result);
		//insert t_wcup_join
		//update t_wcup_list
		//insert t_member_point 500p
		PrintWriter out = response.getWriter();
		if(result ==4) {
			out.println("<script>");
			out.println("alert('[이벤트 참여완료]500포인트가 적립되었습니다.');");
			out.println("window.close(); opener.location.reload();");
			out.println("</script>");
			out.close();
		}
	}
//--------------------------------------------function-------------------------------------------
	//userValid
	public int userValid(HttpServletResponse response,String keyword, String uid, String ewidx) throws Exception {
		int result= eventWcupSvc.wcupValid(keyword,uid,ewidx);
		if(result> 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
//			if(!keyword.equals("join")) {
				out.println("alert('"+(result == 1 ? "이미 참여하셨습니다.":"등록된 스타일이 없습니다." )+"');");
				if(!keyword.substring(4).equals("1")) out.println("window.close(); opener.location.reload();");
				else out.println("history.back();");
				out.println("</script>");
				out.close();
//			}		
//			else {
//				if(result>0)
//				out.println("alert('이미 참여하셨습니다.');");
//			}
		}
		return result;
	}
	
	
	//isLogin
	public void isLogin(HttpServletResponse response,MemberInfo mi) throws Exception {
		if(mi == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인이 필요합니다.');");
			out.println("window.close(); opener.location.reload();");
			out.println("</script>");
			out.close();
		}
		
	}
	// -------wcupRandomSeq-------------------------------------------
	public String wcupSeq(int cnt) {
		int a[] = new int[cnt];
		String seq ="";
		Random r = new Random();
		for(int i=0; i<cnt; i++){
			a[i] = r.nextInt(cnt) + 1;  
			for(int j=0; j<i; j++){
				if(a[i] == a[j]) i--;	
			}
		}
		for(int i=0; i<cnt; i++){
			seq+=a[i];
		}
		return seq;
	}	
}
