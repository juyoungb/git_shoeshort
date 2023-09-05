package svc;

import java.util.*;
import dao.*;
import vo.*;

public class AdStyleSvc {
	private AdStyleDao adStyleDao;

	public void setAdStyleDao(AdStyleDao adStyleDao) {
		this.adStyleDao = adStyleDao;
	}

	public int getStyleCount(String where) {
		int rcnt = adStyleDao.getStyleCount(where);
		
		return rcnt;
	}

	public List<StyleInfo> getStyleList(int cpage, int psize, String where) {
		List<StyleInfo> styleList = adStyleDao.getStyleList(cpage, psize, where);
		
		
		return styleList;
	}

	public int getStyleDel(int siidx, String piid) {
		int result = adStyleDao.getStyleDel(siidx, piid);
		
		return result;
	}

	public StyleInfo getStyleView(int siidx) {
		StyleInfo styleView = adStyleDao.getStyleView(siidx);
		
		return styleView;
	}

	public ProductInfo getStyleProduct(String piid) {
		ProductInfo styleProduct = adStyleDao.getStyleProduct(piid);
		
		return styleProduct;
	}
}
