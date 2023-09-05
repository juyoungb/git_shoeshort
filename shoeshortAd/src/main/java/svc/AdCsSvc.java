package svc;

import java.util.*;
import dao.*;
import vo.*;

public class AdCsSvc {
	private AdCsDao adCsDao;

	public void setAdCsDao(AdCsDao adCsDao) {
		this.adCsDao = adCsDao;
	}

	public int getNoticeListCount(String where) {
		int rcnt = adCsDao.getNoticeListCount(where);
		
		return rcnt;
	}

	public List<NoticeList> getNoticeList(String where, int cpage, int psize) {
		List<NoticeList> noticeList = adCsDao.getNoticeList(where, cpage, psize);
		
		return noticeList;
	}

	public NoticeList getNoticeInfo(int nlidx) {
		NoticeList noticeInfo = adCsDao.getNoticeInfo(nlidx);
		
		return noticeInfo;
	}

	public NoticeList getNoticeProc(int nlidx) {
		NoticeList noticeInfo = adCsDao.getNoticeProc(nlidx);
		
		return noticeInfo;
	}

	public int getNoticeProcUp(NoticeList ni) {
		int result = adCsDao.getNoticeProcUp(ni);
		
		return result;
	}

	public int getNoticeProcDel(int nlidx) {
		int result = adCsDao.getNoticeProcDel(nlidx);
		
		return result;
	}

	public int getFaqListCount(String where) {
		int rcnt = adCsDao.getFaqListCount(where);
		
		return rcnt;
	}

	public List<FaqList> getFaqList(String where, int cpage, int psize) {
		List<FaqList> faqList = adCsDao.getFaqList(where, cpage, psize);
		
		return faqList;
	}

	public int getNoticeProcIn(NoticeList noticeInfo) {
		int result = adCsDao.getNoticeProcIn(noticeInfo);
		
		return result;
	}

	public int getFaqProcIn(FaqList faqInfo) {
		int result = adCsDao.getFaqProcIn(faqInfo);
		
		return result;
	}

	public FaqList getFaqInfo(int flidx) {
		FaqList faqInfo = adCsDao.getFaqInfo(flidx);
		
		return faqInfo;
	}

	public int getAdFaqProcUp(FaqList faqInfo) {
		int result = adCsDao.getAdFaqProcUp(faqInfo);
		
		return result;
	}

	public int getAdFaqProcDel(int flidx) {
		int result = adCsDao.getAdFaqProcDel(flidx);
		
		return result;
	}
}
