package svc;

import java.util.*;
import dao.*;
import vo.*;

public class StyleSvc {
	private StyleDao styleDao;

	public void setStyleDao(StyleDao styleDao) {
		this.styleDao = styleDao;
	}

	public List<StyleInfo> getStyle(int cpage, int psize, String orderBy) {
		List<StyleInfo> styleList = styleDao.getStyle(cpage, psize, orderBy);
		
		return styleList;
	}
	public List<StyleInfo> getTopStyle() {
		List<StyleInfo> styleList = styleDao.getTopStyle();
		
		return styleList;
	}
	public int getStyleCount() {
		int rcnt = styleDao.getStyleCount();
		
		return rcnt;
	}

	public int readUpdate(int siidx) {
		int result = styleDao.readUpdate(siidx);
		
		return result;
	}

	public StyleInfo getStyleView(int siidx) {
		StyleInfo styleView = styleDao.getStyleView(siidx);
		
		return styleView;
	}

	public ProductInfo getStyleProduct(String piid) {
		ProductInfo styleProduct = styleDao.getStyleProduct(piid);
		
		return styleProduct;
	}

	public MemberInfo getLoginInfo(String miid) {
		MemberInfo loginInfo = styleDao.getLoginInfo(miid);
		
		return loginInfo;
	}

	public int getStyleDel(int siidx, String piid) {
		int result = styleDao.getStyleDel(siidx, piid);
		
		return result;
	}

	public int getStyleGood(StyleGood styleGood) {
		int result = styleDao.getStyleGood(styleGood);
		
		return result;
	}

	public StyleInfo getStyleFormProc(StyleInfo styleInfo) {
		StyleInfo si = styleDao.getStyleFormProc(styleInfo);
		
		return si;
	}

	public int getStyleUp(StyleInfo styleInfo) {
		int result = styleDao.getStyleUp(styleInfo);
		
		return result;
	}

	public List<StyleInfo> getMemStyle(int cpage, int psize, String orderBy, String miid) {
		List<StyleInfo> memStyle = styleDao.getMemStyle(cpage, psize, orderBy, miid);
		
		return memStyle;
	}
}
