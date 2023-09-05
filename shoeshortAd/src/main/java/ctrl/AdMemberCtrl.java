package ctrl;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class AdMemberCtrl {
	private AdMemberSvc adMemberSvc;
	
	public void setAdMemberSvc(AdMemberSvc adMemberSvc) {
		this.adMemberSvc = adMemberSvc;
	}
	@GetMapping("/allMem")
	public String allMem(HttpServletRequest request) throws Exception{
	// 어드민 회원관리에서 전체회원관리를 클릭하면 실행되는 메소드
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 5, rcnt = 0, pcnt = 0;
		
		if(request.getParameter("cpage") != null) cpage=Integer.parseInt(request.getParameter("cpage"));
		
		String where =" where 1=1 ";
		String args = "", schargs="";
		args = "&cpage=" + cpage;
		
		String schtype = request.getParameter("schtype");
		String keyword = request.getParameter("keyword");
		String statusS = request.getParameter("statusS");
		String edusdate = request.getParameter("edusdate");	// 날짜 검색 시작 날짜
		String eduedate = request.getParameter("eduedate");	// 날짜 검색 마지막 날짜
		String date = request.getParameter("date");
		String andOr = " and ";
		
		if(statusS == null || statusS.equals("")) {
			statusS = "";
		}
		
		if(statusS != null || !statusS.equals("")) {
			String[] arrSch = statusS.split(",");
			for (int i = 0 ; i < arrSch.length; i++) {
				if (arrSch[i].equals("allS")) {	// 상태 검색값이 전체일 경우
					if (!where.equals(" where 1=1 ")) {	// 앞에 조건이 이미 있으면 and를 or로 바꿈
						andOr = " or ";
						where += andOr + " ( mi_status = 'a' or mi_status = 'b' or mi_status = 'c') ";
					} else {
						where += andOr + " ( mi_status = 'a' or mi_status = 'b' or mi_status = 'c') ";
					}
				}
				if(arrSch[i].equals("a")) {	// 상태 검색값이 정상회원일 경우
					if (!where.equals(" where 1=1 ")) {
						andOr = " or ";
						where += andOr + " mi_status = 'a' "; 
					} else {
						where += andOr + " mi_status = 'a' "; 
					}
				}
				if(arrSch[i].equals("b")) {	// 상태 검색값이 탈퇴계정일 경우
					if (!where.equals(" where 1=1 ")) {
						andOr = " or ";
						where += andOr + " mi_status = 'b' "; 
					} else {
						where += andOr + " mi_status = 'b' "; 
					}
				}
				if(arrSch[i].equals("c")) {	// 상태 검색값이 휴면계정일 경우
					if (!where.equals(" where 1=1 ")) {
						andOr = " or ";
						where += andOr + " mi_status = 'c' "; 	
					} else {
						where += andOr + " mi_status = 'c' "; 
					}
				}
			}
		}
		// 검색 조건 날짜(달력)부분 조건 영역
		if (where != " where 1=1 ") andOr = " and ";
		
		if (edusdate == null) {
			edusdate = "";
		}else if (!edusdate.equals("")){	// edusdate는 값이 있고, eduedate가 null이거나 빈문자열이면
				where += andOr + " (mi_date >= '"+ edusdate +"') ";
				// System.out.println(where);
		} 
		if(eduedate == null) {
			eduedate = "";
		} else if (!eduedate.equals("")) {	// eduedate는 값이 있고, edusdate가 null이거나 빈문자열이면
			if (!where.equals(" where 1=1 ")) {
				andOr = " or ";
				where += andOr + " (mi_date <= '"+ eduedate +"') ";
				// System.out.println(where);
			}
		}
		// 검색 조건 날짜(버튼)부분 조건 영역
		if (!where.equals(" where 1=1 "))	andOr = " and ";
		
		if (date == null) {
			date = "";
		} else if (!date.equals("")) {
			if (date.equals("today")) {
				where += andOr +" (mi_date = now())";
			}
			if (date.equals("week")) {
				where += andOr +" DATE_SUB(mi_date, INTERVAL 1 week)";
			}
			
			if (date.equals("month")) {
				where += andOr +" DATE_SUB(mi_date, INTERVAL 1 year)";
			}
		}

		if(keyword == null) {
			keyword ="";
		} else if(!keyword.equals("")) {
			
			URLEncoder.encode(keyword, "UTF-8");
			if (schtype.equals("id")) {	// 셀렉트박스 검색 조건이 아이디일 경우
				where += " and mi_" + schtype + " like '%" + keyword + "%' ";
			}
			if(schtype.equals("name")) {	// 셀렉트박스 검색 조건이 이름일 경우
				where += " and mi_" + schtype + " like '%" + keyword + "%' "; 
			}
			if(schtype.equals("email")) {	// 셀렉트박스 검색 조건이 이메일일 경우
				where += " and mi_" + schtype +" like '%" + keyword + "%' "; 
			}
			if(schtype.equals("total")) {	// 셀렉트박스 검색 조건이 전체일 경우
				where += " and (mi_id like '%"+ keyword +"%') or (mi_name like '%"+ keyword +"%') or (mi_email like '%"+ keyword +"%') "; 
			}
			schargs = "&schtype=" + schtype + "&keyword=" + keyword;
			args += schargs;
		}
		
		// 회원 레코드 개수를 리턴받아옴
		rcnt = adMemberSvc.getMemberCount(where);
		

		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++;
	
		List<MemberInfo> memberList = adMemberSvc.getMemberList(cpage, psize, where);
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("memberList", memberList);
		
		return "adMember/allMem";
	}
	@RequestMapping("/memDetail")
	public String memDetail(HttpServletRequest request) throws Exception {
	// 회원 1명의 주소정보를 보여주는 메소드
		request.setCharacterEncoding("utf-8");
		String miid = request.getParameter("miid");
		
		MemberDetails memberDetails = adMemberSvc.getMemberDetails(miid);
		request.setAttribute("memberDetails", memberDetails);
		
		return "adMember/adMemDetail";
	}
	
	@GetMapping("/pointMem")
	public String pointMem(HttpServletRequest request) throws Exception{
		// 회원 포인트 리스트를 받아오는 메소드
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 5, rcnt = 0, pcnt = 0;
		
		if(request.getParameter("cpage") != null) cpage=Integer.parseInt(request.getParameter("cpage"));
		
		String where =" where 1=1  and a.mi_id = b.mi_id ";
		String args = "", schargs="";
		args = "&cpage=" + cpage;
		
		String schtype = request.getParameter("schtype");
		String keyword = request.getParameter("keyword");
		
		if (schtype == null ||  keyword == null ) {
			schtype = "";	keyword = "";	
		
		} else if (!schtype.equals("") && !keyword.equals("") ){
			if (schtype.equals("allP")) {	// 검색값이 전체일 경우
				where += " and ( a.mi_id like '%"+ keyword +"%' or a.mi_name like '%"+ keyword +"%') ";
			}
			if(schtype.equals("id")) {	// 검색값이 정상회원일 경우
					where += " and  a.mi_"+ schtype +" like '%"+ keyword +"%' "; 
			}
			if(schtype.equals("name")) {	// 검색값이 탈퇴계정일 경우
				where += " and 	 a.mi_"+ schtype +" like '%"+ keyword +"%' ";
			}	
		}
		
		rcnt = adMemberSvc.getMemberPointCount(where);
		
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++;
		
		List<MemberPoint> pointList = adMemberSvc.getPointList(where, cpage, psize);
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pointList", pointList);
		
		
		return "adMember/pointMem";
	}
	@GetMapping("pointForm")
	public String pointForm(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String miid = request.getParameter("miid");
		
		MemberInfo memberPoint = adMemberSvc.getPointForm(miid);
		request.setAttribute("memberPoint", memberPoint);
		
		return "adMember/pointForm";
	}
	
	@GetMapping("givePoint")
	public void givePoint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String miid = request.getParameter("miid");
		// 적립할 포인트를 받아옴
		int point = Integer.parseInt(request.getParameter("point"));
		String mp_desc = request.getParameter("mp_desc");
		if (mp_desc.equals("a")) mp_desc = "가입 축하금";
		if (mp_desc.equals("b")) mp_desc = "상품 구매";
		if (mp_desc.equals("c")) mp_desc = "게시글 작성";
		if (mp_desc.equals("d")) mp_desc = "관리자 직권";
			
		String mp_detail = request.getParameter("mp_detail");
		if (mp_detail == null) mp_detail = "";
		
		int result = adMemberSvc.getGivePoint(miid, point, mp_desc, mp_detail);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result != 1) {
			out.println("<script>alert('포인트 적립이 처리되지 않았습니다 \n다시 시도하세요..'); window.close();</script>");
		}else {
			out.println("<script>alert('포인트 적립이 되었습니다.'); window.close(); opener.location.reload(); </script>");
			out.close();
		}
	}
}
