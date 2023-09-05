package svc;

import java.util.*;
import dao.*;
import vo.*;

public class CsSvc {
	private CsDao csDao;
	
	public void setCsDao(CsDao csDao) {
		this.csDao = csDao;
	}
	public int getNoticeListCnt() {
		int rcnt = csDao.getNoticeListCnt();
		
		return rcnt;
	}
	public List<NoticeList> getNoticeList(String where, int cpage, int psize) {
		List<NoticeList> noticeList = csDao.getNoticeList(where, cpage, psize);
		
		return noticeList;
	}
	public NoticeList getNoticeInfo(int nlidx) {
		NoticeList noticeInfo = csDao.getNoticeInfo(nlidx);
		
		return noticeInfo;
	}
	public int getFaqListCnt() {
		int rcnt = csDao.getFaqListCnt();
		
		return rcnt;
	}
	public List<FaqList> getFaqList(String where, int cpage, int psize) {
		List<FaqList> faqList = csDao.getFaqList(where, cpage, psize);
		
		return faqList;
	}
	public FaqList getFaqInfo(int flidx) {
		FaqList faqInfo = csDao.getFaqInfo(flidx);
		
		return faqInfo;
	}
	
}
