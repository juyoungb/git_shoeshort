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
	//스타일 월드컵 메인에 가져올 데이터
	@GetMapping("/wcupMain")
	public String eventList(HttpServletRequest request, Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		request.setAttribute("url", "wcup");
		EventWcupInfo voteList = (EventWcupInfo)eventWcupSvc.getEventList("b");	
		EventWcupInfo recruitList = (EventWcupInfo)eventWcupSvc.getEventList("a");
		if(voteList != null || recruitList != null ) {		 		
		List<WcupDetail> detailList = eventWcupSvc.getEventDList(voteList.getEw_idx());
		//voteList.setRand(wcupSeq(voteList.getEw_rule()));
		model.addAttribute("el", voteList);
		model.addAttribute("rl", recruitList);
		model.addAttribute("detailList", detailList);
		} 
		return "event/evt_wcup/wcupMain"; 
	}
	@GetMapping("/wcupGame")
	public String wcupGame(HttpServletRequest request,HttpServletResponse response,  Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		
		int ewidx =Integer.parseInt(request.getParameter("gi")); 
		model.addAttribute("ewidx",  ewidx);	
		EventWcupInfo eventList = (EventWcupInfo)eventWcupSvc.getEventList("b");
		
		model.addAttribute("el", eventList);
		return "event/evt_wcup/wcupGame"; 
	}
	//스타일 월드컵 지원하는 폼
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
	//스타일 월드컵 게임 진행 구조
	@GetMapping("/wcupEnjoy")
	public String wcupEnjoy(HttpServletRequest request,HttpServletResponse response,  Model model) throws Exception  {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		isLogin(response,mi);
		String uid =mi.getMi_id(), title=request.getParameter("title");
		int ewidx=Integer.parseInt(request.getParameter("ewidx")), stage =Integer.parseInt(request.getParameter("stage"));
		String sys = request.getParameter("sys");	//선택한 값 저장용 변수 ex) 1111 -> 1011 0은 선택 받지 못한 데이터 최종적으로 하나만 남게 됨
		String choice = request.getParameter("choice");		// 사용자가 선택한 값 저장 변수
		int ewrule = Integer.parseInt(request.getParameter("ewrule"));	//8
		userValid(response,"join",uid,request.getParameter("ewidx"));//참여 여부 판별
		
		List<WcupDetail> detailList = null;
		detailList = eventWcupSvc.getEventDList(ewidx);
		
		String img1="",img2="",imgs ="", winner="";
		String[][] arr = new String[2][ewrule];
		String rand="";
		if(stage == 1) rand = wcupSeq(ewrule);
		else rand =request.getParameter("rand");
		System.out.println("RAND :"+rand);
		System.out.println("sys :"+sys);
		System.out.println("ewrule :"+ewrule);
		if(stage>1) sys = wcupResult(ewrule, sys,stage, choice);
		for(int i=0; i<ewrule; i++){ //42318765
			//Random 배열에 램덤 순서에 맞게 이미지와 아이디를   담는다.(0: 스타일 이미지 , 1: 아이디)
			arr[0][Integer.parseInt(rand.split(",")[i])-1] = detailList.get(i).getSi_img();
			arr[1][Integer.parseInt(rand.split(",")[i])-1] = detailList.get(i).getMi_id();
		}
		for(String ar:arr[0]) {
			img1+=","+ar;
		}
		img1="";
		for(int i=0; i<ewrule; i++) {//1234
			if(sys.split("")[i].equals("1")) {//""으로 스플릿 후 값이 1일때만  
			imgs+= ","+arr[0][i];   //스타일이미지
			winner+= ","+arr[1][i];	//아이디
			//sys = 10111111 -> imgs   =  ,이미지1,이미지2,이미지3 ,이미지4,이미지5,이미지6,이미지7 
			//					winner =  ,아이디1,아이디2,아이디3 ,아이디4,아이디5,아이디6,아이디7 
			}
		}
		if(ewrule == 4 || ewrule == 8) {
			String[] images = imgs.substring(1).split(",");	//이미지1,이미지2,이미지3 ,이미지4,이미지5,이미지6,이미지7 
			if(stage == 1 || stage == 5 || stage == 7 || (stage == 3 && ewrule == 4)) { // [11]111111
				img1=images[0];
				img2=images[1];			
			}else if(stage == 2 || stage == 6) {// 01[11]1111
				img1=images[1];
				img2=images[2];
			}else if(stage == 3 && ewrule == 8) {// 0101[11]11
				img1=images[2];
				img2=images[3];
			}else if(stage == 4 && ewrule == 8 ) {// 010101[11]
				img1=images[3];
				img2=images[4];					
			}else {
				img1=images[0];		
			}
		}		
		EventWcupInfo ewi = new EventWcupInfo();
		ewi.setEw_title(title);	ewi.setEw_idx(ewidx); ewi.setSys(sys);
		ewi.setEw_rule(ewrule);	ewi.setMi_id(uid); ewi.setStage(stage);
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
		PrintWriter out = response.getWriter();
		if(result ==4) {
			out.println("<script>");
			out.println("alert('[이벤트 참여완료]500포인트가 적립되었습니다.');");
			out.println("window.close(); opener.location.reload();");
			out.println("</script>");
			out.close();
		}
	}
//--------------------------------------------function method-------------------------------------------
	//userValid
	public int userValid(HttpServletResponse response,String keyword, String uid, String ewidx) throws Exception {
		int result= eventWcupSvc.wcupValid(keyword,uid,ewidx);
		if(result> 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+(result == 1 ? "이미 참여하셨습니다.":"등록된 스타일이 없습니다." )+"');");
			if(!keyword.substring(4).equals("1")) out.println("window.close(); opener.location.reload();");
			else out.println("history.back();");
			out.println("</script>");
			out.close();

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
	// -------랜덤 숫자 생성-------------------------------------------
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
			seq+=","+a[i];
		}
		return seq.substring(1);
	}
	public String wcupResult(int ewrule, String sys, int stage,String choice) {//1, 8, 11111111, 1    
		String[] arrSys = new String[ewrule];
		String result="";
		arrSys = sys.split("");// 1111 1111 값을 배열로 변경해줌
		if(ewrule==4) {	//4강일 경우 
			if(stage == 2) {
				if(choice.equals("1"))	arrSys[1] ="0";
				else					arrSys[0] ="0";
			}else if(stage == 3) {
				if(choice.equals("1"))	arrSys[3] ="0";
				else					arrSys[2] ="0";
			}else if(stage == 4) {
				for(int i=0,j=0; i<(arrSys.length); i++){
					if(arrSys[i].equals("1")) {//1010 값이 1일경우
						if(choice.equals("1")) {
							if(j==1) {
								arrSys[i] = "0";
								break;
							}
							j++;
						}
						else { arrSys[i] = "0";
						break;
						}
					}
				}	
			}
		}else if(ewrule == 8) { //8강일 경우 
			if(stage<6) { 
				if(stage == 2) {
					if(choice.equals("1"))	arrSys[1] ="0";
					else					arrSys[0] ="0";
				}else {
					if(choice.equals("1"))	arrSys[1+((stage-2)*2)] ="0";
					else					arrSys[(stage-2)*2] ="0";
				}
			}else if(stage >= 6){//
				int arrSize = arrSys.length, num = 0;
				if(stage == 6) arrSize /= 2;
				if(stage == 7) num = 4;
				for(int i=num,j=0; i<arrSize; i++){
					if(arrSys[i].equals("1")) {
						if(choice.equals("1")) {
							if(j==1) {
								arrSys[i] = "0";
								break;
							}
							j++;
						}
						else { arrSys[i] = "0";
						break;
						}
					}
				}
			}
		}
		for(String arr:arrSys) {  // 배열을 다시 문자열로 변경
			result+=arr;
		}
		return result;	
	}
}
