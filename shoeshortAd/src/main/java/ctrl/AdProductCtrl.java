package ctrl;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import svc.*;
import vo.*;

@Controller
public class AdProductCtrl {
	private AdProductSvc adProductSvc;

	public void setAdProductSvc(AdProductSvc adProductSvc) {
		this.adProductSvc = adProductSvc;
	}

	@GetMapping("/adProductList")
	public String adProductList(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, spage = 0,  psize = 15, bsize = 10, rcnt = 0, pcnt = 0;
	
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));

		String sch = request.getParameter("sch");	
		if (sch == null)	sch = "";
		
		String where =" where a.pi_id = b.pi_id and a.pcb_id = c.pcb_id and a.pb_id = d.pb_id " ,schargs="";
		
		String args = ""; 	//쿼리스트링을 저장할 변수
		args = "&cpage=" + cpage;
		
		String schtype = request.getParameter("schtype"); // 검색조건(제목, 내용, 제목+내용)
		String keyword = request.getParameter("keyword"); // 검색어
		//System.out.println("schtype :" +schtype);
		//System.out.println("keyword :" +keyword);
		
		
		if(schtype == null || keyword == null) {
			schtype =""; 	keyword ="";
			// 화면상의 검색어가 NULL로 보이지 않게하기 위해 빈문자열로 채움
		} else if(schtype.equals("") || !schtype.equals("") && !keyword.equals("")) {
			// 검색조건과 검색어가 모두 있을 경우
			URLEncoder.encode(keyword, "UTF-8");
			//쿼리 스트링으로 주고 받는 검색어가 한글일 경우 브라우저에 따라 문제가 발생할 수 있으므로 유니 코드로 변환
			if (schtype.equals("")) {//검색조건이 '상품아이디'일 경우
				where += " and (a.pi_id like '%" + keyword + "%' or a.pi_name like '%" + keyword + "%' or d.pb_name like '%" + keyword + "%') ";
			}			
			if (schtype.equals("pi_id")) {//검색조건이 '상품아이디'일 경우
				where += " and a." + schtype + " like '%" + keyword + "%' ";
			}
			if(schtype.equals("pi_name")) {	// 상품명으로 검색
				where += " and a." + schtype + " like '%" + keyword + "%' "; 
			}
			if(schtype.equals("pb_name")) {//검색조건이 '브랜드'일 경우	
				where += " and d." + schtype + " like '%"+ keyword + "%' "; 
			}
			schargs = "&schtype=" + schtype + "&keyword=" + keyword;
			//System.out.println("schargs :" +schargs);
			args += schargs;
			
		}

		//System.out.println("where :" + where);
		
		
		//검색조건 sch
		if (sch != null && !sch.equals("")) {	
			args +="&sch=" + sch;
			String[] arrSch = sch.split(",");
			for (int i = 0 ; i < arrSch.length ; i++) {
				char c = arrSch[i].charAt(0);
				if  (c == 'g') { //g(ender) 성별
					if(!arrSch[i].substring(1).equals("전체")) {
						String[] arr = arrSch[i].substring(1).split(":"); // :�� ��� �������
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + "a.pi_gubun ='" + arr[j].charAt(0) + "' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
						} 
						where += ") ";
					}	
				} else if  (c == 'c') { //c(tgr) 카테고리
					//System.out.println("arrSch[i].substring(1) :" + arrSch[i].substring(1));
					if(!arrSch[i].substring(1).equals("전체")) {
						String[] arr = arrSch[i].substring(1).split(":"); // :�� ��� �������				
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + " c.pcb_name ='" + arr[j] + "' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
						} 
						where += ") ";
					}	
				}else if (c =='s') {// s(eason) 계절
					//System.out.println("arrSch[i].substring(1) :" + arrSch[i].substring(1));
					if(!arrSch[i].substring(1).equals("전체")) {
						String[] arr = arrSch[i].substring(1).split(":"); 					
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + "a.pi_rc_season = '" + arr[j]+"' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����						
						} 
						where += ") ";
					}
				}else if (c =='i') {// i(sview) 게시여부
					//System.out.println("arrSch[i].substring(1) :" + arrSch[i].substring(1));
					if(!arrSch[i].substring(1).equals("전체")) {
						String[] arr = arrSch[i].substring(1).split(":"); 					
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + "a.pi_isview= '" + arr[j]+"' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����						
						} 
						where += ") ";
					}
					
				}else if (c =='a') {// a(ge) 나이
					//System.out.println("arrSch[i].substring(1) :" + arrSch[i].substring(1));
					if(!arrSch[i].substring(1).equals("전체")) {
						String[] arr = arrSch[i].substring(1).split(":"); 					
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + "a.pi_rc_age ='" + arr[j] + "' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
						} 
						where += ") ";
					}						
				}else if (c =='k') {// k (수영장,졸업식, ...)
					//System.out.println("arrSch[i].substring(1) :" + arrSch[i].substring(1));
					if(!arrSch[i].substring(1).equals("전체")) {
						String[] arr = arrSch[i].substring(1).split(":"); 					
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + "a.pi_rc_keyword ='" + arr[j] + "' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
						} 
						where += ") ";
					}						
				}else if (c =='d') {// d(ate) 날짜 
					if(!arrSch[i].substring(1).equals("전체")) {
						//System.out.println("arrSch[i].substring(1) :" + arrSch[i].substring(1));
						String[] arr = arrSch[i].substring(1).split(":"); 
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + "Date(a.pi_date) ='" + arr[j] + "' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
						} 
						where += ") ";
					}						
				}
				
			}
		}	
		
		rcnt = adProductSvc.getProductCount(where);// 검색된 게시글의 총 개수로 게시글 일련번호와 전체 페이지수 계산을 위해 필요한 값
		System.out.println("where :" + where);
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++; // 전체 페이지수 (마지막페이지 번호)

		spage = (cpage -1) / bsize * bsize + 1;
		
		List<ProductInfo> productList = adProductSvc.getProductList(cpage, psize, where);
		//목록 화면에서 보여줄 게시글 목록을 List<StyleInfo>형으로 받아옴
		//필요한 만큼만 받아오기 위해 cpage, psize가 필요함
		
		//브랜드와 카테고리(스니커즈, 구두, 샌들/슬리퍼)를 가져오는 list
		List<ProductCtgr> ctgrList = adProductSvc.getCtgrList();
		
		List<ProductBrand> brandList = adProductSvc.getBrandList();
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);
		pageInfo.setSpage(spage);
		//페이징과 검색에 필요한 정보들을 pageInfo형 인스턴스에 저장
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("productList", productList);
		request.setAttribute("brandList", brandList);
		request.setAttribute("ctgrList", ctgrList);
				
		return "productAdmin/adProductList";
	}
	
	@GetMapping("/adProductStockList")  
	public String adProductStockList(Model model,HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid");
		List<ProductStock> stockList = adProductSvc.getStockList(piid);
		model.addAttribute("stockList", stockList);
		model.addAttribute("piid", piid);
		
		return "productAdmin/adProductStockList";
	}
	
	@PostMapping("/stockUpdate")  
	public String stockUpdate(Model model,HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		String pi_id = request.getParameter("piid");

		String sch = request.getParameter("sch");	
		if (sch == null)	sch = "";
	
		
		String arr[] = null;
		if (sch != null && !sch.equals("")) {			
			String[] arrSch = sch.split(",");
			for (int i = 0 ; i < arrSch.length ; i++) {
				char c = arrSch[i].charAt(0);
				if  (c == 's') { //size 사이즈	
					// s225 120:230 12:240 100:260 100
					arrSch[i].substring(0);
					arr = arrSch[i].substring(1).split(":"); 
				} 
			}
		}	
		
		ProductInfo pi = new ProductInfo();
		
		pi.setPi_id(pi_id);		
		int result = adProductSvc.UpdateStock(pi, arr);
		
		return "redirect:/adProductStockList";
	}
	
	
	@GetMapping("/adProducIsview") // 게시/미시게 변경
	public String adProducIsview (Model model, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid");
		String type = request.getParameter("type");
		int result = adProductSvc.IsviewProduct(piid, type);
		
		return "redirect:/adProductList";
	}
	
	@GetMapping("/adProductProc")
	public String adProductProc() {
		
		return "productAdmin/adProductProc";
	}
	
	@PostMapping("/dupId")
	@ResponseBody
	public String dupId(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid").trim();
		int result = adProductSvc.chkPiid(piid);
		return result + "";
	}
	
	@PostMapping("/adProductFile") // 상품등록 form으로 보낼때
	public String adProductFile( HttpServletRequest request, @RequestPart("uploadFile1") Part file1,
			@RequestPart("uploadFile2") Part file2, @RequestPart("uploadFile3") Part file3, @RequestPart("uploadFile4") Part file4)   throws Exception  {
		String uploadFiles ="E:/_project/shoeshortAd/src/main/webapp/resources/img/product";
		System.out.println("adProductFile");
		//String path = "D:/_bjk/shoeshortAd/src/main/webapp/resources/img/lucky_img";
		String pi_id = request.getParameter("piid").trim().toUpperCase(); //상품명
		String ctgr = request.getParameter("ctgr");//상품명
		String pb_id = pi_id.substring(0,2);//
		String piname = request.getParameter("piname"); // 상품이름
		String isview = request.getParameter("isview"); // 게시여부
		String gender = request.getParameter("gender"); // 성별
		int price = Integer.parseInt(request.getParameter("price")); // 판매가격
		int cost = Integer.parseInt(request.getParameter("cost")); // 원가
		String com = request.getParameter("com"); // 원산지
		
		//파일 
		String f1 = getUploadFileName(file1.getHeader("content-disposition"));
		String f2 = getUploadFileName(file2.getHeader("content-disposition"));
		String f3 = getUploadFileName(file3.getHeader("content-disposition"));
		String f4 = getUploadFileName(file3.getHeader("content-disposition"));
		
		String pi_img1 = f1 !=null ? f1: ".png";
		String pi_img2 = f2 !=null ? f2: ".png";
		String pi_img3 = f3 !=null ? f3: ".png";
		String pi_desc = f4 !=null ? f4: ".png";
		
	
		String files = "";
		for (Part part : request.getParts()) {
			if (part.getName().startsWith("el_img")) {
			String cd =  part.getHeader("content-disposition");
				String uploadName = getUploadFileName(cd);
				if (!uploadName.equals("")) {
				// 업로드할 파일이 있으면
					uploadFiles += ", " + uploadName;
					part.write(uploadName);
				}	
			}
		}
		
		String sch = request.getParameter("sch");	
		if (sch == null)	sch = "";

		
		ProductInfo pi = new ProductInfo();
		
		pi.setPi_id(pi_id);
		pi.setPi_name(piname);
		pi.setPi_isview(isview);
		pi.setPi_gubun(gender);
		pi.setPi_price(price);
		pi.setPi_cost(cost);
		pi.setPi_com(com);
		pi.setPi_img1(pi_img1);
		pi.setPi_img2(pi_img2);
		pi.setPi_img3(pi_img3);
		pi.setPi_desc(pi_desc);
		pi.setPcb_id(ctgr);
		pi.setPb_id(pb_id);
		
		String arr[] = null;
		//검색조건 sch
		
		if (sch != null && !sch.equals("")) {			
			String[] arrSch = sch.split(",");
			for (int i = 0 ; i < arrSch.length ; i++) {
				char c = arrSch[i].charAt(0);
				if  (c == 's') { //size 사이즈	
					// s225 120:230 12:240 100:260 100
					arrSch[i].substring(0);
					arr = arrSch[i].substring(1).split(":"); 
				} 
			}
		}	
				
		int result = adProductSvc.insertProduct(pi, arr);
		
		return "redirect:/adProductProc"; 
	}
	
	private String getUploadFileName(String cd) {
		String uploadName = null;
		String[] arrContent = cd.split(";");
		
		int fIdx = arrContent[2].indexOf("\"");
		int sIdx = arrContent[2].lastIndexOf("\"");
		
		uploadName = arrContent[2].substring(fIdx + 1, sIdx);
		return uploadName;
	}
	

}
