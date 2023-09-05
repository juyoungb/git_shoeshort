package svc;

import dao.*;
import vo.*;

public class LoginSvc {
	private LoginDao loginDao;

	public void setLoginDaoSpr(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	public AdminInfo getLoginInfo(String uid, String pwd) {
		AdminInfo mi = loginDao.getLoginInfo(uid, pwd);
		
		return mi;
	}
}
