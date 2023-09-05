package svc;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import dao.*;
import vo.*;

public class OrderSvc {
	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public List<OrderInfo> getOrderList(String mi_id) {
		List<OrderInfo> ordertList = orderDao.getOrderList(mi_id);
		return ordertList;
	}

	public List<OrderInfo> getOrderDetail(String oiid) {
		List<OrderInfo> orderList = orderDao.getOrderDetail(oiid);
		return orderList;
	}

	public List<OrderCart> getCartList(String miid) {
		List<OrderCart> cartList = orderDao.getCartList(miid);
		
		return cartList;
	}

	public int cartUpCnt(OrderCart oc) {
		System.out.println("svc");
		int result = orderDao.cartUpCnt(oc);
		return result;
	}

	public int cartInsert(OrderCart oc) {
		int result = orderDao.cartInsert(oc);		
		return result;
	}

	public int cartDel(String where) {
		int result = orderDao.cartDel(where);
		
		return result;
	}

	public List<OrderCart> getBuyList(String kind, String sql) {
		List<OrderCart> pdtList = orderDao.getBuyList(kind, sql);
		return pdtList;
	}

	public List<MemberAddr> getAddrList(String miid) {
		List<MemberAddr> addrList = orderDao.getAddrList(miid);
		return addrList;
	}
	@Transactional(rollbackFor = {Exception.class})
	public String orderIn(String kind, OrderInfo oi, String ocidxs) {
		String result = orderDao.orderIn(kind,oi,ocidxs);	
		return result;
	}

	public OrderInfo getOneOrderInfo(String miid, String oiid) {
		OrderInfo orderInfo = orderDao.getOneOrderInfo(miid,oiid);
		return orderInfo;
	}
	
}
