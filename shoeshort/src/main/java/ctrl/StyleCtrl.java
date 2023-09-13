package ctrl;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.http.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import svc.*;
import vo.*;

@Controller
public class StyleCtrl {
	private StyleSvc styleSvc;
	
	public void setStyleSvc(StyleSvc styleSvc) {
		this.styleSvc = styleSvc;
	}
	
	// 스타일 리스트 영역 시작 test
	@GetMapping("/style") 
	public String style(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, spage = 0, psize = 12, bsize = 10, rcnt = 0, pcnt = 0;
		
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		
			String orderBy = " order by ";	// 목록 정렬 순서
		String ob = request.getParameter("ob"); 	// 정렬 조건
		if (ob == null || ob.equals(""))	ob = "a";
		String obargs = "&ob="+ ob;		// 정렬조건을 위한 쿼리 스트링
		switch(ob) {
		case "a" : // 인기순(좋아요순)
			orderBy += " si_good desc ";	break;
		case "b" : // 최신등록순
			orderBy += " si_date desc ";	break;
		}
		
		// rcnt에 레코드 개수를 받아와서 저장
		rcnt = styleSvc.getStyleCount();
		
		// List<StyleInfo>형 인스턴스 styleList에  값을 받아와서 저장
		List<StyleInfo> styleList = styleSvc.getStyle(cpage, psize, orderBy);
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;
		spage = (cpage - 1) / bsize * bsize + 1;
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);		pageInfo.setCpage(cpage);	pageInfo.setCpage(spage);
		pageInfo.setPsize(psize);		pageInfo.setPcnt(pcnt);		pageInfo.setRcnt(rcnt);
		pageInfo.setOb(ob);				pageInfo.setObargs(obargs); 
		
		request.setAttribute("styleList", styleList);
		request.setAttribute("pageInfo", pageInfo);
		
		return "style/style_list";
	}
	
	// 회원 스타일 리스트 영역
	@GetMapping("/memStyle")
	public String memStyle (HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String miid = request.getParameter("miid");
		int cpage = 1, spage = 0, psize = 12, bsize = 10, rcnt = 0, pcnt = 0;
		
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		
			String orderBy = " order by ";	// 목록 정렬 순서
		String ob = request.getParameter("ob"); 	// 정렬 조건
		if (ob == null || ob.equals(""))	ob = "a";
		String obargs = "&ob="+ ob;		// 정렬조건을 위한 쿼리 스트링
		switch(ob) {
		case "a" : // 인기순(좋아요순)
			orderBy += " si_good desc ";	break;
		case "b" : // 최신등록순
			orderBy += " si_date desc ";	break;
		}
		
		// rcnt에 레코드 개수를 받아와서 저장
		rcnt = styleSvc.getStyleCount();
		
		// List<StyleInfo>형 인스턴스 styleList에  값을 받아와서 저장
		List<StyleInfo> memStyle = styleSvc.getMemStyle(cpage, psize, orderBy, miid);
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;
		spage = (cpage - 1) / bsize * bsize + 1;
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);		pageInfo.setCpage(cpage);	pageInfo.setCpage(spage);
		pageInfo.setPsize(psize);		pageInfo.setPcnt(pcnt);		pageInfo.setRcnt(rcnt);
		pageInfo.setOb(ob);				pageInfo.setObargs(obargs); 
		
		request.setAttribute("memStyle", memStyle);
		request.setAttribute("pageInfo", pageInfo);
		
		
		return "style/memStyle_list";
	}
	
	// 스타일 등록폼 영역
	@GetMapping("/styleForm")
	public String StyleForm (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		// 주문내역에서 스타일 등록 폼으로 이동할때 받아옴
		String piid = request.getParameter("piid");
		
		StyleInfo styleInfo = new StyleInfo();
		styleInfo.setPi_id(piid);
		
		request.setAttribute("styleInfo", styleInfo);
		
		return "style/style_form";
	}
	
	// 스타일 등록 처리 영역
	@PostMapping("/StyleFormProc")
	public String StyleFormProc (MultipartFile[] uploadFile, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid");
		String si_content = request.getParameter("si_content");
		
		// 스타일 이미지 저장영역(이미지를 지정한 폴더에 저장하고 업로드한 파일 이름을 디비에 저장해서 스타일 뷰에서 보여줌)	// 학원 컴퓨터 폴더는 lastproject / 집 컴터는 workspace
		String uploadPath = "D://limhyun//last_project//shoeshort//src//main//webapp//resources//img//style_img";
		String files = "";
		System.out.println(files);
		// 저장될 경로에 \\를 //로 변경
		for (MultipartFile file : uploadFile) {
			File saveFile = new File(uploadPath, file.getOriginalFilename());
			// 저장할 파일 객체를 생성
			try {
				file.transferTo(saveFile);
				files = file.getOriginalFilename();
			} catch (Exception e) {e.printStackTrace();}	
		}
		
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		String miid = loginInfo.getMi_id();
		
		StyleInfo styleInfo = new StyleInfo();
		styleInfo.setMi_id(miid);
		styleInfo.setPi_id(piid);
		styleInfo.setSi_content(si_content);
		styleInfo.setSi_img(files);
		System.out.println(files);
		
		// 뷰로 가져갈 쿼리스트링을 저장할 변수
		
		
		StyleInfo si = styleSvc.getStyleFormProc(styleInfo);
		styleInfo.setSi_idx(si.getSi_idx());
		int siidx = si.getSi_idx();
		
		String args = "?siidx="+ siidx +"&piid="+ piid;
		
		return "redirect:/styleView"+ args;
	}
	
	// 스타일 뷰 영역 시작
	@GetMapping("/styleView")
	public String styleView(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int siidx = Integer.parseInt(request.getParameter("siidx"));
		String piid = request.getParameter("piid");
		
		// 1. 스타일 글 조회수 증가
		int result = styleSvc.readUpdate(siidx);
		
		// 2. 보여줄 스타일 정보 가져오기
		StyleInfo styleView = styleSvc.getStyleView(siidx);
		
		// 3. 해당 스타일 글에 상품정보 가져오기
		ProductInfo styleProduct = styleSvc.getStyleProduct(piid);
		
		
		request.setAttribute("styleView", styleView);
		request.setAttribute("styleProduct", styleProduct);
		
		return "style/style_view";
	}
	
	// 스타일 삭제 영역 시작
	@GetMapping("/styleProcDel")
	public String styleProcDel (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int siidx = Integer.parseInt(request.getParameter("siidx"));
		String piid = request.getParameter("piid");
		int result = styleSvc.getStyleDel(siidx, piid);
		
		return "redirect:/style";
	}
	
	// 스타일 수정 폼 뷰 영역 시작
	@GetMapping("/styleProcUp")
	public String styleProcUpFrom (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		int siidx = Integer.parseInt(request.getParameter("siidx"));
		String piid = request.getParameter("piid");
		
		StyleInfo styleView = styleSvc.getStyleView(siidx);
		request.setAttribute("styleView", styleView);
		
		return "style/style_up_form";
	}
	
	
	// 스타일 수정 처리 영역 시작
	@PostMapping("/styleProcUp")
	public String styleProcUp (HttpServletRequest request, MultipartFile[] uploadFile) throws Exception {
		request.setCharacterEncoding("utf-8");
		int siidx = Integer.parseInt(request.getParameter("siidx"));
		String piid = request.getParameter("piid");
		String si_content = request.getParameter("si_content");
		// 수정할 내용들 받아오기
		
		// 스타일 이미지 저장영역(이미지를 지정한 폴더에 저장하고 업로드한 파일 이름을 디비에 저장해서 스타일 뷰에서 보여줌)
		String uploadPath = "D://limhyun//workspace//shoeshort//src//main//webapp//resources//img//style_img";
		String files = "";
		// 저장될 경로에 \\를 //로 변경
		for (MultipartFile file : uploadFile) {
			File saveFile = new File(uploadPath, file.getOriginalFilename());
			// 저장할 파일 객체를 생성
			try {
				file.transferTo(saveFile);
				files = file.getOriginalFilename();
			} catch (Exception e) {e.printStackTrace();}	
		}
		
		StyleInfo styleInfo = new StyleInfo();
		styleInfo.setSi_idx(siidx);
		styleInfo.setPi_id(piid);
		styleInfo.setSi_content(si_content);
		styleInfo.setSi_img(files);
		
		// 수정된 결과 받기
		int result = styleSvc.getStyleUp(styleInfo);
		return "redirect:/styleView?siidx="+ siidx +"&piid="+ piid;
	}
	// 스타일 좋아요 처리 영역 시작
	@RequestMapping("/styleGood")
	@ResponseBody
	public String styleGood (HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		int siidx = Integer.parseInt(request.getParameter("siidx"));
		String piid = request.getParameter("piid");
		
		StyleGood styleGood = new StyleGood();
		styleGood.setSi_idx(siidx);
		styleGood.setMi_id(loginInfo.getMi_id());
		
		int result = styleSvc.getStyleGood(styleGood);
		System.out.println(result);
		
		return result + "";
	}
}
