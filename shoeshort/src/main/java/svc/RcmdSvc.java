package svc;

import java.util.*;
import dao.*;
import vo.*;


public class RcmdSvc {
	private RcmdDao	rcmdDao;

	public void setRcmdDao(RcmdDao rcmdDao) {
		this.rcmdDao = rcmdDao;
	}

	public int getRcmdCount() {
		int rcnt = rcmdDao.getRcmdCount();
		
		return rcnt;
	}

	public List<ProductInfo> getRcmdList( int cpage, int psize, String where) {
		List<ProductInfo> rcmdList = rcmdDao.getRcmdList(cpage, psize, where);
		
		return rcmdList;
	}

}
