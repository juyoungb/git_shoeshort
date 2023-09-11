package ctrl;

import java.util.*;
import java.io.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class RcmdCtrl {
	private RcmdSvc rcmdSvc;

	public void setRcmdSvc(RcmdSvc rcmdSvc) {
		this.rcmdSvc = rcmdSvc;
	}
	@GetMapping("/rcmd")
	public String rcmdList(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		int cpage = 1, spage = 0, psize = 12, bsize = 10, rcnt = 0, pcnt = 0;
		String schG = "", schS = "", schA = "", schK = "";
		
		
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		
		String sch = request.getParameter("sch");	// 검색 조건(추천검색어)
		if (sch == null)	sch = "";
		String where = " where pi_isview = 'y' ", schargs = "";

		if (sch != null && !sch.equals("")) {
			schargs += "&sch="+ sch;
			// System.out.print(sch);
			String[] arrSch = sch.split(",");
			for (int i = 0 ; i < arrSch.length; i++) {
				char c = arrSch[i].charAt(0);
				if (c == 'g') {	// 검색 조건이 성별이면
					String[] arr = arrSch[i].substring(1).split(":");
					where +=  " and ( ";
					for (int j = 0; j < arr.length ; j++) {
						schG = arr[j];
						where += (j == 0 ? "" : " or ") + "pi_gubun = '"+ arr[j] +"' ";
					}
					where += " )";
				}
				if (c == 's') {	// 검색 조건이 계절이면
					String[] arr = arrSch[i].substring(1).split(":");
					where +=  " and ( ";
					for (int j = 0; j < arr.length ; j++) {
						schS = arr[j];
						where += (j == 0 ? "" : " or ") + "pi_rc_season = '"+ arr[j] +"' ";
					}
					where += " )";
				}
				if (c == 'a') {	// 검색 조건이 연령별
					String[] arr = arrSch[i].substring(1).split(":");
					where +=  " and ( ";
					for (int j = 0; j < arr.length ; j++) {
						schA = arr[j];
						where += (j == 0 ? "" : " or ") + "pi_rc_age = '"+ arr[j] +"' ";
					}
					where += " )";
				}
				if (c == 'k') {	// 검색 조건이 키워드면
					String[] arr = arrSch[i].substring(1).split(":");
					where +=  " and ( ";
					for (int j = 0; j < arr.length ; j++) {
						schK = arr[j];
						where += (j == 0 ? "" : " or ") + "pi_rc_keyword = '"+ arr[j] +"' ";
					}
					where += " )";
				}
				
			}
		}
		
		// List<ProductInfo>형 인스턴스 rcmdList에 값을 받아와서 저장
		List<ProductInfo> rcmdList = rcmdSvc.getRcmdList(cpage, psize, where);
		
		// rcnt에 레코드 개수를 받아와서 저장
		rcnt = rcmdSvc.getRcmdCount(where);
			
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;
		spage = (cpage - 1) / bsize * bsize + 1;
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);		pageInfo.setCpage(cpage);	pageInfo.setCpage(spage);
		pageInfo.setPsize(psize);		pageInfo.setPcnt(pcnt);		pageInfo.setRcnt(rcnt);
		pageInfo.setSch(sch);			pageInfo.setSchG(schG);		pageInfo.setSchS(schS);
		pageInfo.setSchA(schA);			pageInfo.setSchK(schK);
		// schG = 검색조건 성별, schS = 검색조건 계절, schA = 검색조건 연령별, schK = 검색조건 키워드
		
		
		request.setAttribute("rcmdList", rcmdList);
		request.setAttribute("pageInfo", pageInfo);
		
		return "rcmd/rcmd_list";
	}
}
