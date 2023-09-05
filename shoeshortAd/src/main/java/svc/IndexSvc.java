package svc;

import java.util.List;

import dao.*;
import vo.*;

public class IndexSvc {
	private IndexDao indexDao;

	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}

	public IndexData getTotalData() {
		IndexData totalData = indexDao.getTotalData();
		return totalData;
	}
	public AdminInfo getLoginInfo(String uid, String pwd) {
		AdminInfo mi = indexDao.getLoginInfo(uid, pwd);
		
		return mi;
	}
	
}
