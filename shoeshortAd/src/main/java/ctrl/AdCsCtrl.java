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
public class AdCsCtrl {
	private AdCsSvc adCsSvc;

	public void setAdCsSvc(AdCsSvc adCsSvc) {
		this.adCsSvc = adCsSvc;
	}
	@GetMapping("adNoticeList")
	public String adNoticeList(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0;
		// 페이지 번호, 페이지 크기, 블록 크기, 레코드(게시글) 개수, 페이지 개수 등을 저장할 변수
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		// cpage 값이 있으면 받아서 int형으로 형변환 함(보안상의 이유와 산술연산해야 하기 때문에)
		
		String where =" where nl_isview='y' ";
		String args = "", schargs="";	//쿼리스트링을 저장할 변수
		args = "&cpage=" + cpage;
		
		String ctgr = request.getParameter("ctgr"); // 분류조건(공지사항, 이벤트 안내, 이벤트 발표)
		String schtype = request.getParameter("schtype"); // 검색조건(제목, 내용)
		String keyword = request.getParameter("keyword"); // 검색어
	
		if (ctgr == null) ctgr = "";
		else if (ctgr != null && !ctgr.equals("")) {
			where += " and nl_ctgr = '"+ ctgr +"' ";
		}
		
		if(schtype == null || keyword == null) {
			schtype =""; 	keyword ="";
			// 화면상의 검색어가 NULL로 보이지 않게하기 위해 빈문자열로 채움
		} else if(!schtype.equals("") && !keyword.equals("")) {
			// 검색조건과 검색어가 모두 있을 경우
			URLEncoder.encode(keyword, "UTF-8");
			//쿼리 스트링으로 주고 받는 검색어가 한글일 경우 브라우저에 따라 문제가 발생할 수 있으므로 유니 코드로 변환
			where += " and nl_"+ schtype +" like '%"+ keyword.trim() +"%' ";
		}
		
		rcnt = adCsSvc.getNoticeListCount(where);
		// 검색된 게시글의 총 개수로 게시글 일련번호와 전체 페이지수 계산을 위해 필요한 값
		
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++; // 전체 페이지수 (마지막페이지 번호)
		
		List<NoticeList> noticeList = adCsSvc.getNoticeList(where, cpage, psize);
		//목록 화면에서 보여줄 게시글 목록을 ArrayList<FreeList>형으로 받아옴
		//필요한 만큼만 받아오기 위해 cpage, psize가 필요함
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);	pageInfo.setCtgr(ctgr);
		//페이징과 검색에 필요한 정보들을 pageInfo형 인스턴스에 저장
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("noticeList", noticeList);
		// request의 영역은 요청을 받은 파일과 forward()로 이동하는 파일에서 사용할 수 있음
		
		return "csAdmin/adNoticeList";
	}
	@GetMapping("adNoticeView")
	public String adNoticeView(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int nlidx = Integer.parseInt(request.getParameter("nlidx"));
		
		NoticeList noticeInfo = adCsSvc.getNoticeInfo(nlidx);
		request.setAttribute("noticeInfo", noticeInfo);
		
		return "csAdmin/adNoticeView";
	}
	@GetMapping("adNoticeForm")
	public String adNoticeForm(HttpServletRequest request) throws Exception {
		
		return "csAdmin/adNoticeForm";
	}
	@PostMapping("adNoticeProcIn")
	public String adNoticeProcIn(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String ctgr = request.getParameter("ctgr");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		NoticeList noticeInfo = new NoticeList();
		noticeInfo.setAi_idx(idx); noticeInfo.setNl_ctgr(ctgr);
		noticeInfo.setNl_title(title); noticeInfo.setNl_content(content);
		
		int result = adCsSvc.getNoticeProcIn(noticeInfo);
		
		return "redirect:/adNoticeList";
	}
	@GetMapping("adNoticeProcUpForm")
	public String adNoticeProcUpForm (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int nlidx = Integer.parseInt(request.getParameter("nlidx"));
		String ctgr = request.getParameter("ctgr");
		
		NoticeList noticeInfo = adCsSvc.getNoticeProc(nlidx);
		
		PageInfo pi = new PageInfo();
		pi.setCtgr(ctgr);
		
		request.setAttribute("noticeInfo", noticeInfo);
		request.setAttribute("pi", pi);
		
		return "csAdmin/adNoticeProcUpForm";
	}
	@PostMapping("adNoticeProcUp")
	public String adNoticeProcUp (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int nlidx = Integer.parseInt(request.getParameter("nlidx"));
		String title = request.getParameter("title").trim();
		String content = request.getParameter("content").trim();
		String ctgr = request.getParameter("ctgr");
		
		NoticeList noticeInfo = new NoticeList();
		noticeInfo.setNl_idx(nlidx); 		noticeInfo.setNl_title(title);
		noticeInfo.setNl_content(content);	noticeInfo.setNl_ctgr(ctgr);
		
		request.setAttribute("noticeInfo", noticeInfo);
		
		int result = adCsSvc.getNoticeProcUp(noticeInfo);
		
		return "csAdmin/adNoticeView";
	}
	@GetMapping("adNoticeProcDel")
	public String adNoticeProcDel (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int nlidx = Integer.parseInt(request.getParameter("nlidx"));
		
		int result = adCsSvc.getNoticeProcDel(nlidx);
		
		return "redirect:/adNoticeList";
	}
	@GetMapping("adFaqList")
	public String adFaqList(HttpServletRequest request) throws Exception {
	// 자주묻는 질문 리스트 처리 메소드	
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0;
		// 페이지 번호, 페이지 크기, 블록 크기, 레코드(게시글) 개수, 페이지 개수 등을 저장할 변수
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		// cpage 값이 있으면 받아서 int형으로 형변환 함(보안상의 이유와 산술연산해야 하기 때문에)
		
		String where =" where fl_isview='y' ";
		String args = "", schargs="";	//쿼리스트링을 저장할 변수
		args = "&cpage=" + cpage;
		
		String schtype = request.getParameter("schtype"); // 검색조건(제목, 내용)
		String keyword = request.getParameter("keyword"); // 검색어
		
		if(schtype == null || keyword == null) {
			schtype =""; 	keyword ="";
			// 화면상의 검색어가 NULL로 보이지 않게하기 위해 빈문자열로 채움
		} else if(!schtype.equals("") && !keyword.equals("")) {
			// 검색조건과 검색어가 모두 있을 경우
			URLEncoder.encode(keyword, "UTF-8");
			//쿼리 스트링으로 주고 받는 검색어가 한글일 경우 브라우저에 따라 문제가 발생할 수 있으므로 유니 코드로 변환
			where += " and fl_"+ schtype +" like '%"+ keyword.trim() +"%' ";
		}
		
		rcnt = adCsSvc.getFaqListCount(where);
		// 검색된 게시글의 총 개수로 게시글 일련번호와 전체 페이지수 계산을 위해 필요한 값
		
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++; // 전체 페이지수 (마지막페이지 번호)
		
		List<FaqList> faqList = adCsSvc.getFaqList(where, cpage, psize);
		//목록 화면에서 보여줄 게시글 목록을 ArrayList<FreeList>형으로 받아옴
		//필요한 만큼만 받아오기 위해 cpage, psize가 필요함
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);
		//페이징과 검색에 필요한 정보들을 pageInfo형 인스턴스에 저장
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("faqList", faqList);
		// request의 영역은 요청을 받은 파일과 forward()로 이동하는 파일에서 사용할 수 있음
		
		return "csAdmin/adFaqList";
	}
	@GetMapping("adFaqForm")
	public String adFaqForm (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		return "csAdmin/adFaqForm";
	}
	@PostMapping("adFaqProcIn")
	public String adFaqProcIn (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String answer = request.getParameter("answer");
		
		FaqList faqInfo = new FaqList();
		faqInfo.setAi_idx(idx);	faqInfo.setFl_title(title); faqInfo.setFl_content(content); faqInfo.setFl_answer(answer);
		
		int result = adCsSvc.getFaqProcIn(faqInfo);
		
		return "redirect:/adFaqList";
	}
	@GetMapping("adFaqView")
	public String adFaqView (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int flidx = Integer.parseInt(request.getParameter("flidx"));
		
		FaqList faqInfo = adCsSvc.getFaqInfo(flidx);
		request.setAttribute("faqInfo", faqInfo);
		
		return "csAdmin/adFaqView";
	}
	@GetMapping("adFaqProcUpForm")
	public String adFaqProcUpForm (HttpServletRequest request) throws Exception  {
		request.setCharacterEncoding("utf-8");
		int flidx = Integer.parseInt(request.getParameter("flidx"));
		FaqList faqInfo = adCsSvc.getFaqInfo(flidx);
		
		request.setAttribute("faqInfo", faqInfo);
		
		return "csAdmin/adFaqProcUpForm";
	}
	@PostMapping("adFaqProcUp")
	public String adFaqProcUp (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int flidx = Integer.parseInt(request.getParameter("flidx"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String answer = request.getParameter("answer");
		
		FaqList faqInfo = new FaqList();
		faqInfo.setFl_idx(flidx); 	faqInfo.setFl_title(title); faqInfo.setFl_content(content);
		faqInfo.setFl_answer(answer);
		request.setAttribute("faqInfo", faqInfo);
		
		int result = adCsSvc.getAdFaqProcUp(faqInfo);
		
		return "csAdmin/adFaqView";
	}
	@GetMapping("adFaqProcDel")
	public String adFaqProcDel(HttpServletRequest request) throws Exception {
		int flidx = Integer.parseInt(request.getParameter("flidx"));
		 
		int result = adCsSvc.getAdFaqProcDel(flidx);
		
		return "redirect:/adFaqList";
	}
}
