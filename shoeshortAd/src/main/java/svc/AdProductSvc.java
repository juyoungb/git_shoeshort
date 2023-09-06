package svc;

import java.util.*;
import dao.*;
import vo.*;

public class AdProductSvc {
	private AdProductDao adProductDao;

	public void setAdProductDao(AdProductDao adProductDao) {
		this.adProductDao = adProductDao;
	}

	public int getProductCount(String where) {
		int result = adProductDao.getProductCount(where);
		return result;
	}

	public List<ProductInfo> getProductList(int cpage, int psize, String where) {
		List<ProductInfo> productList = adProductDao.getProductList(cpage, psize, where);
		
		
		return productList;
	}

	public List<ProductStock> getStockList(String piid) {
		List<ProductStock> stockList = adProductDao.getStockList(piid);
		return stockList;
	}

	public int IsviewProduct(String piid, String type) {
		int result = adProductDao.IsviewProduct(piid, type);
		return result;
	}
	
	public List<ProductCtgr> getCtgrList() {
		List<ProductCtgr> ctgrList = adProductDao.getCtgrList();
		return ctgrList;	
	}

	public List<ProductBrand> getBrandList() {
		List<ProductBrand> brandList = adProductDao.getBrandList();
		return brandList;
	}

	public int chkPiid(String piid) {
		int result = adProductDao.chkPiid(piid);
		return result;
	}


	public int insertProduct(ProductInfo pi, String[] arr) {
		int result = adProductDao.insertProduct(pi, arr);
		return result;
	}

	public int UpdateStock(ProductInfo pi, String[] arr) {
		int result = adProductDao.UpdateStock(pi, arr);
		return result;
	}
	

}
