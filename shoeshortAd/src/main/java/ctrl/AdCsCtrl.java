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
		// ������ ��ȣ, ������ ũ��, ��� ũ��, ���ڵ�(�Խñ�) ����, ������ ���� ���� ������ ����
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		// cpage ���� ������ �޾Ƽ� int������ ����ȯ ��(���Ȼ��� ������ ��������ؾ� �ϱ� ������)
		
		String where =" where nl_isview='y' ";
		String args = "", schargs="";	//������Ʈ���� ������ ����
		args = "&cpage=" + cpage;
		
		String ctgr = request.getParameter("ctgr"); // �з�����(��������, �̺�Ʈ �ȳ�, �̺�Ʈ ��ǥ)
		String schtype = request.getParameter("schtype"); // �˻�����(����, ����)
		String keyword = request.getParameter("keyword"); // �˻���
	
		if (ctgr == null) ctgr = "";
		else if (ctgr != null && !ctgr.equals("")) {
			where += " and nl_ctgr = '"+ ctgr +"' ";
		}
		
		if(schtype == null || keyword == null) {
			schtype =""; 	keyword ="";
			// ȭ����� �˻�� NULL�� ������ �ʰ��ϱ� ���� ���ڿ��� ä��
		} else if(!schtype.equals("") && !keyword.equals("")) {
			// �˻����ǰ� �˻�� ��� ���� ���
			URLEncoder.encode(keyword, "UTF-8");
			//���� ��Ʈ������ �ְ� �޴� �˻�� �ѱ��� ��� �������� ���� ������ �߻��� �� �����Ƿ� ���� �ڵ�� ��ȯ
			where += " and nl_"+ schtype +" like '%"+ keyword.trim() +"%' ";
		}
		
		rcnt = adCsSvc.getNoticeListCount(where);
		// �˻��� �Խñ��� �� ������ �Խñ� �Ϸù�ȣ�� ��ü �������� ����� ���� �ʿ��� ��
		
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++; // ��ü �������� (������������ ��ȣ)
		
		List<NoticeList> noticeList = adCsSvc.getNoticeList(where, cpage, psize);
		//��� ȭ�鿡�� ������ �Խñ� ����� ArrayList<FreeList>������ �޾ƿ�
		//�ʿ��� ��ŭ�� �޾ƿ��� ���� cpage, psize�� �ʿ���
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);	pageInfo.setCtgr(ctgr);
		//����¡�� �˻��� �ʿ��� �������� pageInfo�� �ν��Ͻ��� ����
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("noticeList", noticeList);
		// request�� ������ ��û�� ���� ���ϰ� forward()�� �̵��ϴ� ���Ͽ��� ����� �� ����
		
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
	// ���ֹ��� ���� ����Ʈ ó�� �޼ҵ�	
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0;
		// ������ ��ȣ, ������ ũ��, ��� ũ��, ���ڵ�(�Խñ�) ����, ������ ���� ���� ������ ����
		if(request.getParameter("cpage") != null) 
			cpage=Integer.parseInt(request.getParameter("cpage"));
		// cpage ���� ������ �޾Ƽ� int������ ����ȯ ��(���Ȼ��� ������ ��������ؾ� �ϱ� ������)
		
		String where =" where fl_isview='y' ";
		String args = "", schargs="";	//������Ʈ���� ������ ����
		args = "&cpage=" + cpage;
		
		String schtype = request.getParameter("schtype"); // �˻�����(����, ����)
		String keyword = request.getParameter("keyword"); // �˻���
		
		if(schtype == null || keyword == null) {
			schtype =""; 	keyword ="";
			// ȭ����� �˻�� NULL�� ������ �ʰ��ϱ� ���� ���ڿ��� ä��
		} else if(!schtype.equals("") && !keyword.equals("")) {
			// �˻����ǰ� �˻�� ��� ���� ���
			URLEncoder.encode(keyword, "UTF-8");
			//���� ��Ʈ������ �ְ� �޴� �˻�� �ѱ��� ��� �������� ���� ������ �߻��� �� �����Ƿ� ���� �ڵ�� ��ȯ
			where += " and fl_"+ schtype +" like '%"+ keyword.trim() +"%' ";
		}
		
		rcnt = adCsSvc.getFaqListCount(where);
		// �˻��� �Խñ��� �� ������ �Խñ� �Ϸù�ȣ�� ��ü �������� ����� ���� �ʿ��� ��
		
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++; // ��ü �������� (������������ ��ȣ)
		
		List<FaqList> faqList = adCsSvc.getFaqList(where, cpage, psize);
		//��� ȭ�鿡�� ������ �Խñ� ����� ArrayList<FreeList>������ �޾ƿ�
		//�ʿ��� ��ŭ�� �޾ƿ��� ���� cpage, psize�� �ʿ���
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);
		//����¡�� �˻��� �ʿ��� �������� pageInfo�� �ν��Ͻ��� ����
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("faqList", faqList);
		// request�� ������ ��û�� ���� ���ϰ� forward()�� �̵��ϴ� ���Ͽ��� ����� �� ����
		
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
