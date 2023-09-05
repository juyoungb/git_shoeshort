package svc;

import java.util.*;
import dao.*;
import vo.*;

public class AdWcupSvc {
	private AdWcupDao adWcupDao;

	public void setAdWcupDao(AdWcupDao adWcupDao) {
		this.adWcupDao = adWcupDao;
	}

	public List<EventWcupInfo> getEvtWcupList() {
		List<EventWcupInfo> wcupList = adWcupDao.getEvtWcupList();
		return wcupList;
	}

	public List<EventWcupInfo> getEvtWcupList(String ewidx) {
		List<EventWcupInfo> wcupList = adWcupDao.getEvtWcupList(ewidx);
		return wcupList;
	}

	public int WcupIn(EventWcupInfo ewi) {
		int result = adWcupDao.WcupIn(ewi);	
		return result;
	}

	public int WcupDel(String ewidx) {
		int result = adWcupDao.WcupDel(ewidx);		
		return result;
	}

	public int WcupUp(EventWcupInfo ewi) {
		int result = adWcupDao.WcupUp(ewi);		
		return result;
	}
	
}
