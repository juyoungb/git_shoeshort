package ctrl;

import java.util.*;
import java.io.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class CsCtrl {
	private CsSvc csSvc;
	
	public void setCsSvc(CsSvc csSvc) {
		this.csSvc = csSvc;
	}
	@GetMapping("noticeList")
	public String noticeList (HttpServletRequest request) throws Exception {
		// 공지사항 관련 처리 영역
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0;
		// 페이지 번호, 페이지 크기, 블록 크기, 레코드(게시글) 개수, 페이지 개수 등을 저장할 변수
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		// cpage 값이 있으면 받아서 int형으로 형변환 함(보안상의 이유와 산술연산해야 하기 때문에)
		
		String where =" where nl_isview='y' ";
		String args = "";
		args = "&cpage=" + cpage;
		
		rcnt = csSvc.getNoticeListCnt();
		// 검색된 게시글의 총 개수로 게시글 일련번호와 전체 페이지수 계산을 위해 필요한 값
		
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++; // 전체 페이지수 (마지막페이지 번호)
		
		List<NoticeList> noticeList = csSvc.getNoticeList(where, cpage, psize);
		//목록 화면에서 보여줄 게시글 목록을 ArrayList<NoticeList>형으로 받아옴
		//필요한 만큼만 받아오기 위해 cpage, psize가 필요함
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setArgs(args);
		//페이징과 검색에 필요한 정보들을 pageInfo형 인스턴스에 저장
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("noticeList", noticeList);

		return "cs/notice_list";
	}
	@GetMapping("noticeView")
	public String noticeView(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int nlidx = Integer.parseInt(request.getParameter("nlidx"));
		
		NoticeList noticeInfo = csSvc.getNoticeInfo(nlidx);
		 request.setAttribute("noticeInfo", noticeInfo);
		
		return "cs/notice_view";
	}
	@GetMapping("faqList")
	public String faqList (HttpServletRequest request) throws Exception {
		// faq 관련 처리 영역
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0;
		// 페이지 번호, 페이지 크기, 블록 크기, 레코드(게시글) 개수, 페이지 개수 등을 저장할 변수
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		// cpage 값이 있으면 받아서 int형으로 형변환 함(보안상의 이유와 산술연산해야 하기 때문에)
		
		String where =" where fl_isview='y' ";
		String args = "";
		args = "&cpage=" + cpage;
		
		rcnt = csSvc.getFaqListCnt();
		// 검색된 게시글의 총 개수로 게시글 일련번호와 전체 페이지수 계산을 위해 필요한 값
		
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++; // 전체 페이지수 (마지막페이지 번호)
		
		List<FaqList> faqList = csSvc.getFaqList(where, cpage, psize);
		//목록 화면에서 보여줄 게시글 목록을 ArrayList<NoticeList>형으로 받아옴
		//필요한 만큼만 받아오기 위해 cpage, psize가 필요함
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setArgs(args);
		//페이징과 검색에 필요한 정보들을 pageInfo형 인스턴스에 저장
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("faqList", faqList);

		return "cs/faq_list";
	}
	@GetMapping("faqView")
	public String faqView(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int flidx = Integer.parseInt(request.getParameter("flidx"));
		
		FaqList faqInfo = csSvc.getFaqInfo(flidx);
		 request.setAttribute("faqInfo", faqInfo);
		
		return "cs/faq_view";
	}
}
