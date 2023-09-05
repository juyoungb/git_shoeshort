package svc;

import java.util.*;
import dao.*;
import vo.*;

public class AdStoreSvc {
	private AdStoreDao adStoreDao;

	public void setAdStoreDao(AdStoreDao adStoreDao) {
		this.adStoreDao = adStoreDao;
	}

	public int getStoreCount(String where) {
		int result = adStoreDao.getStoreCount(where);
		return result;
	}
	
	public List<StoreInfo> getStoreList(String where, int psize, int cpage) {
		List<StoreInfo> storeList = adStoreDao.getStoreList(where, psize, cpage);
		
		return storeList;
	}

	public StoreInfo storeUpForm(int bsiidx) {
		StoreInfo storeInfo = adStoreDao.storeUpForm(bsiidx);
		
		return storeInfo;
	}

	public int AdStoreUp(StoreInfo si) {
		int result = adStoreDao.AdStoreUp(si);
		
		return result;
	}

	public int AdStoreIn(StoreInfo si) {
		int result = adStoreDao.AdStoreIn(si);
		
		return result;
	}

}
