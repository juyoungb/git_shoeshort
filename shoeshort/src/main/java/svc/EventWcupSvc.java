package svc;

import java.util.*;
import dao.*;
import vo.*;

public class EventWcupSvc {
	private EventWcupDao eventWcupDao;
	
	public void setEventWcupDao(EventWcupDao eventWcupDao) {
		this.eventWcupDao = eventWcupDao;
	}
	public EventWcupInfo getEventList(String ewstatus){
		EventWcupInfo eventList = eventWcupDao.getEventWcupList(ewstatus);
		return eventList;		
	}
	public List<WcupDetail> getEventDList(int ewidx){
		List<WcupDetail> detailList = eventWcupDao.getEventWcupDList(ewidx);
		return detailList;		
	}
	public List<StyleInfo> getStyleList(String miid) {	
		List<StyleInfo> styleList = eventWcupDao.getStyleList(miid);
		return styleList;
	}
	public int joinWcup(int siidx, int ewidx, String miid) {
		int result= eventWcupDao.joinWcup(siidx,ewidx,miid);
		return result;
	}
	public EvtWcupRule getWcup(String uid, int ewidx) {
		EvtWcupRule getWcup= eventWcupDao.getWcup(uid,ewidx);	
		
		return getWcup;
	}
	public int wcupValid(String keyword, String mi_id, String ewidx) {
		int result= eventWcupDao.wcupValid(keyword,mi_id,Integer.parseInt(ewidx));
		return result;
	}
	public int wcupWin(String miid, String rule, String winner, String ewidx) {
		int result = eventWcupDao.wcupWin(miid,rule,winner,ewidx);
		return result;
	}
	
}

