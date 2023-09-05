package svc;

import java.util.*;

import org.json.simple.JSONArray;

import dao.*;
import vo.*;

public class StoreSvc {
	private StoreDao storeDao;

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public List<StoreInfo> getStoreList(String where) {
		List<StoreInfo> si = storeDao.getStoreList(where);
		
		return si;
	}
}
