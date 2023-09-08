package svc;

import java.util.*;
import dao.*;
import vo.*;

public class WalkingSvc {
	private WalkingDao walkingDao;

	public void setWalkingDao(WalkingDao walkingDao) {
		this.walkingDao = walkingDao;
	}

	public List<ProductInfo> getProductInfo() {
		List<ProductInfo> product = walkingDao.getProductInfo();
		
		return product;
	}
}
