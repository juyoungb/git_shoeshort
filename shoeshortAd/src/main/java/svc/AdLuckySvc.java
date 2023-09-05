package svc;

import java.util.*;
import dao.*;
import vo.*;


public class AdLuckySvc {
	private AdLuckyDao adLuckyDao;

	public void setAdLuckyDao(AdLuckyDao adLuckyDao) {
		this.adLuckyDao = adLuckyDao;
	}

	public List<LuckyInfo> getLuckyList(int elidx) {
		List<LuckyInfo> luckyList = adLuckyDao.getLuckyList(elidx);
		return luckyList;
	}

	public int luckyDel(String elidx) {
		int result = adLuckyDao.luckyDel(elidx);		
		return result;
	}

	public int LuckyIn(LuckyInfo li) {
		int result = adLuckyDao.LuckyIn(li);
		return result;
	}

	public List<LuckyInfo> geUpLuckyList(int elidx) {
		List<LuckyInfo> upLuckyList = adLuckyDao.geUpLuckyList(elidx);
		return upLuckyList;
	}

	public int luckyUp(LuckyInfo li) {
		int result = adLuckyDao.luckyUp(li);
		return result;
	}

	public int getLuckyCount() {
		int rcnt = adLuckyDao.getLuckyCount();
		return rcnt;
	}

}
