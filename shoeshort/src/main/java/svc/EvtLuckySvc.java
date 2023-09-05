package svc;

import java.util.*;
import dao.*;
import vo.*;

public class EvtLuckySvc {
	private EvtLuckyDao evtLuckyDao;

	public void setEvtLuckyDao(EvtLuckyDao evtLuckyDao) {
		this.evtLuckyDao = evtLuckyDao;
	}

	public List<EvtLuckyInfo> getLuckyList(String pi_id) {
		 List<EvtLuckyInfo> evtList = evtLuckyDao.getLuckyList(pi_id);
		return evtList;
	}

	public int luckyInsert(String mi_id, int price, int el_idx) {
		int result = evtLuckyDao.luckyInsert(mi_id, price, el_idx);
		return result;
	}


}
