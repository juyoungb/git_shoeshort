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
public class AdStyleCtrl {
	private AdStyleSvc adStyleSvc;
	
	public void setAdStyleSvc(AdStyleSvc adStyleSvc) {
		this.adStyleSvc = adStyleSvc;
	}
	@GetMapping("/adStyle")
	public String adStyleList (HttpServletRequest request)throws Exception {
	// style 게시글 리스트를 보여주는 메소드
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0;
		// 페이지 번호, 페이지 크기, 블록 크기, 레코드(게시글) 개수, 페이지 개수 등을 저장할 변수
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		// cpage 값이 있으면 받아서 int형으로 형변환 함(보안상의 이유와 산술연산해야 하기 때문에)
		
		String where =" where si_isview='y' ";
		String args = "", schargs="";	//쿼리스트링을 저장할 변수
		args = "&cpage=" + cpage;
		
		String schtype = request.getParameter("schtype"); // 검색조건(제목, 내용, 제목+내용)
		String keyword = request.getParameter("keyword"); // 검색어
	
		if(schtype == null || keyword == null) {
			schtype =""; 	keyword ="";
			// 화면상의 검색어가 NULL로 보이지 않게하기 위해 빈문자열로 채움
		} else if(!schtype.equals("") && !keyword.equals("")) {
			// 검색조건과 검색어가 모두 있을 경우
			URLEncoder.encode(keyword, "UTF-8");
			//쿼리 스트링으로 주고 받는 검색어가 한글일 경우 브라우저에 따라 문제가 발생할 수 있으므로 유니 코드로 변환
			if (schtype.equals("id")) {//검색조건이 '아이디'일 경우
				where += " and mi_" + schtype + " like '%" + keyword + "%' ";
			}
			if(schtype.equals("content")) {	//검색조건이 '내용'일 경우
				where += " and si_" + schtype + " like '%" + keyword + "%' "; 
			}
			schargs = "&schtype=" + schtype + "&keyword=" + keyword;
			args += schargs;
		}
		
		rcnt = adStyleSvc.getStyleCount(where);
		// 검색된 게시글의 총 개수로 게시글 일련번호와 전체 페이지수 계산을 위해 필요한 값
		
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++; // 전체 페이지수 (마지막페이지 번호)
		
		List<StyleInfo> styleList = adStyleSvc.getStyleList(cpage, psize, where);
		//목록 화면에서 보여줄 게시글 목록을 ArrayList<StyleInfo>형으로 받아옴
		//필요한 만큼만 받아오기 위해 cpage, psize가 필요함
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);
		//페이징과 검색에 필요한 정보들을 pageInfo형 인스턴스에 저장
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("styleList", styleList);

		return "styleAdmin/adStyleList";
	}
	// 스타일 뷰 영역 시작
	@GetMapping("/adStyleView")	
	public String styleView(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int siidx = Integer.parseInt(request.getParameter("siidx"));
		String piid = request.getParameter("piid");
		
		// 1. 보여줄 스타일 정보 가져오기
		StyleInfo styleView = adStyleSvc.getStyleView(siidx);
		
		// 2. 해당 스타일 글에 상품정보 가져오기
		ProductInfo styleProduct = adStyleSvc.getStyleProduct(piid);
		
		
		request.setAttribute("styleView", styleView);
		request.setAttribute("styleProduct", styleProduct);
		
		return "styleAdmin/adStyleView";
	}
	
	// 스타일 삭제 영역 시작
	@GetMapping("/adStyleProcDel")
	public String adStyleView (Model model, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int siidx = Integer.parseInt(request.getParameter("siidx"));
		String piid = request.getParameter("piid");
		int result = adStyleSvc.getStyleDel(siidx, piid);
		
		return "redirect:/adStyle";
	}
}
