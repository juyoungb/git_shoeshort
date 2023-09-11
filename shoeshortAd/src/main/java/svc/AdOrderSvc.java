package svc;

import java.util.*;
import dao.*;
import vo.*;

public class AdOrderSvc {
	private AdOrderDao adOrderDao;

	public void setAdOrderDao(AdOrderDao adOrderDao) {
		this.adOrderDao = adOrderDao;
	}

	public int getOrderCount(String where) {
		int rcnt = adOrderDao.getOrderCount(where);
		return rcnt;
	}

	public List<OrderInfo> getMemberList(int cpage, int psize, String where) {
		List<OrderInfo> OrderList = adOrderDao.getMemberList(cpage, psize, where);
		return OrderList;
	}

	public int upOrderStatus(String where, String schOrder) {
		int result = adOrderDao.upOrderStatus(where,schOrder);
		return result;
	}

	public OrderInfo getOrderInfo(String uid, String oiid) {
		OrderInfo orderInfo = adOrderDao.getOrderInfo(uid,oiid);
		return orderInfo;
	}

	public MemberInfo getMemberInfo(String uid) {
		MemberInfo memberInfo = adOrderDao.getMemberInfo(uid);
		return memberInfo;
	}
	
}
