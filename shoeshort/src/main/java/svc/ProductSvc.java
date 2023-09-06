package svc;

import vo.*;
import java.util.List;
import dao.*;

public class ProductSvc {
	private ProductDao productDao;
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public List<ProductInfo> getNewList() {
		List<ProductInfo> productList = productDao.getNewList(); 	

		return productList;
	}

	public List<ProductInfo> productView(String piid) {
		List<ProductInfo> producList = productDao.productView(piid); 	

		 return producList;
	}
	
	public List<ProductStock> productStockView(String piid) {
		 List<ProductStock> stockList = productDao.productStockView(piid); 	

		 return stockList;
	}

	public List<ProductInfo> getProductList(int cpage, int psize, String where, String orderBy, String s) {

		 List<ProductInfo> procductList = productDao.getProductList(cpage, psize, where, orderBy, s); 	

		
		return procductList;
	}

	public int getProductCount(String where) {
		 int rcnt = productDao.getProductCount(where);
	
		return rcnt;
	}

	public List<ProductBrand> getBrandList() {
		List<ProductBrand> brandList = productDao.getBrandList();
		
		return brandList;
	}

	public List<ProductCtgrBig> getCtgrList() {
		List<ProductCtgrBig> ctgrList = productDao.getCtgrList();
		return ctgrList;
	}

	public List<ProductStock> getSizeList() {
		List<ProductStock> sizeList =  productDao.getSizeList();
		return sizeList;
	}
	
	public List<ProductInfo> getMainList(String orderby) {
		List<ProductInfo> mainList = productDao.getMainList(orderby);
		return mainList;
	}

	public List<ProductInfo> getShoesList(String pcb_id) {
		List<ProductInfo> productList = productDao.getShoesList(pcb_id);
		return productList;
	}

}
