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
public class AdStoreCtrl {
	private AdStoreSvc adStoreSvc;

	public void setAdStoreSvc(AdStoreSvc adStoreSvc) {
		this.adStoreSvc = adStoreSvc;
	}
	@GetMapping("/adStore")
	public String adStore(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0;
		// 페이지 번호, 페이지 크기, 블록 크기, 레코드(게시글) 개수, 페이지 개수 등을 저장할 변수
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		// cpage 값이 있으면 받아서 int형으로 형변환 함(보안상의 이유와 산술연산해야 하기 때문에)
		
		String where =" where bsi_isview = 'y' ";
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
			if (schtype.equals("brand")) {//검색조건이 '아이디'일 경우
				where += " and bsi_" + schtype + " like '%" + keyword + "%' ";
			}
			if(schtype.equals("name")) {	//검색조건이 '내용'일 경우
				where += " and bsi_" + schtype + " like '%" + keyword + "%' "; 
			}
			schargs = "&schtype=" + schtype + "&keyword=" + keyword;
			args += schargs;
		}
		
		rcnt = adStoreSvc.getStoreCount(where);
		// 검색된 게시글의 총 개수로 게시글 일련번호와 전체 페이지수 계산을 위해 필요한 값
		
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++; // 전체 페이지수 (마지막페이지 번호)
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);
		//페이징과 검색에 필요한 정보들을 pageInfo형 인스턴스에 저장
		
		List<StoreInfo> storeList = adStoreSvc.getStoreList(where, psize, cpage);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("storeList", storeList);
		
		return "storeAdmin/adStore";
	}
	@GetMapping("/storeFormUp")
	public String storeFormUp(HttpServletRequest request) throws Exception{
	// 매장 정보 수정 폼	
		request.setCharacterEncoding("utf-8");
		int bsiidx = Integer.parseInt(request.getParameter("bsiidx"));
	
		StoreInfo storeInfo = adStoreSvc.storeUpForm(bsiidx);
		request.setAttribute("storeInfo", storeInfo);
		
		return "storeAdmin/adStoreFormUp";
	}
	
	@GetMapping("/storeFormIn")
	public String storeFormIn(){
	// 매장 정보 등록 폼 이동
		return "storeAdmin/adStoreFormIn";
	}
	
	@GetMapping("/adStoreUp")
	public void adStoreUp(HttpServletRequest request, HttpServletResponse response) throws Exception{
	// 매장 정보 수정 처리
		request.setCharacterEncoding("utf-8");
		int bsiidx = Integer.parseInt(request.getParameter("bsiidx"));
		String brand = request.getParameter("brand");
		String name = request.getParameter("name");
		String addr = request.getParameter("addr");
		String isview = request.getParameter("isview");
		
		if (brand == null) brand = "";	if (name == null) name = "";
		if (addr == null) addr = "";
		
		StoreInfo si = new StoreInfo();
		si.setBsi_idx(bsiidx); 	si.setBsi_brand(brand); 	si.setBsi_name(name);
		si.setBsi_addr(addr);		si.setBsi_isview(isview);
		
		int result = adStoreSvc.AdStoreUp(si);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result != 1) {
			out.println("<script>alert('매장정보 수정 처리가 되지 않았습니다 \n다시 시도하세요..'); window.close();</script>");
		}else {
			out.println("<script>alert('매장 정보이 수정 되었습니다.'); window.close(); opener.location.reload(); </script>");
			out.close();
		}
	}
	@GetMapping("/adStoreIn")
	public void adStoreIn(HttpServletRequest request, HttpServletResponse response) throws Exception{
	// 매장 정보 등록 처리
		request.setCharacterEncoding("utf-8");
		int aiidx = Integer.parseInt(request.getParameter("aiidx"));
		
		String brand = request.getParameter("brand");
		String name = request.getParameter("name");
		String addr = request.getParameter("addr");
		String isview = request.getParameter("isview");
		String pbid = "";
		
		if (brand == null) brand = "";	if (name == null) name = "";
		if (addr == null) addr = "";
		if (brand.equals("나이키")) pbid = "NN";
		if (brand.equals("크록스")) pbid = "CC";
		if (brand.equals("닥터마틴")) pbid = "DD";
		
		StoreInfo si = new StoreInfo();
		si.setBsi_brand(brand); 	si.setBsi_name(name);	si.setBsi_addr(addr);
		si.setBsi_isview(isview);	si.setPb_id(pbid);	si.setAi_idx(aiidx);
		
		int result = adStoreSvc.AdStoreIn(si);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result != 1) {
			out.println("<script>alert('매장 정보가 등록 처리가 되지 않았습니다 \n다시 시도하세요..'); window.close();</script>");
		}else {
			out.println("<script>alert('매장 정보가 등록 되었습니다.'); window.close(); opener.location.reload(); </script>");
			out.close();
		}
	}
}
