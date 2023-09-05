package vo;

import java.util.*;

public class OrderInfo {
// �ϳ��� �ֹ������� ������ Ŭ����
	
	private String oi_id, mi_id, oi_name, oi_rname, oi_phone, oi_zip, oi_addr1, oi_addr2;
	private String oi_memo, oi_payment, oi_invoice, oi_status, oi_date;
	private String cnt, size, piid; // 추가
	private int oi_pay, oi_upoint, oi_spoint;
	private List<OrderDetail> detailList;
	
	public String getOi_id() {
		return oi_id;
	}
	public void setOi_id(String oi_id) {
		this.oi_id = oi_id;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getOi_name() {
		return oi_name;
	}
	public void setOi_name(String oi_name) {
		this.oi_name = oi_name;
	}
	public String getOi_rname() {
		return oi_rname;
	}
	public void setOi_rname(String oi_rname) {
		this.oi_rname = oi_rname;
	}
	public String getOi_phone() {
		return oi_phone;
	}
	public void setOi_phone(String oi_phone) {
		this.oi_phone = oi_phone;
	}
	public String getOi_zip() {
		return oi_zip;
	}
	public void setOi_zip(String oi_zip) {
		this.oi_zip = oi_zip;
	}
	public String getOi_addr1() {
		return oi_addr1;
	}
	public void setOi_addr1(String oi_addr1) {
		this.oi_addr1 = oi_addr1;
	}
	public String getOi_addr2() {
		return oi_addr2;
	}
	public void setOi_addr2(String oi_addr2) {
		this.oi_addr2 = oi_addr2;
	}
	public String getOi_memo() {
		return oi_memo;
	}
	public void setOi_memo(String oi_memo) {
		this.oi_memo = oi_memo;
	}
	public String getOi_payment() {
		return oi_payment;
	}
	public void setOi_payment(String oi_payment) {
		this.oi_payment = oi_payment;
	}
	public String getOi_invoice() {
		return oi_invoice;
	}
	public void setOi_invoice(String oi_invoice) {
		this.oi_invoice = oi_invoice;
	}
	public String getOi_status() {
		return oi_status;
	}
	public void setOi_status(String oi_status) {
		this.oi_status = oi_status;
	}
	public String getOi_date() {
		return oi_date;
	}
	public void setOi_date(String oi_date) {
		this.oi_date = oi_date;
	}
	public int getOi_pay() {
		return oi_pay;
	}
	public void setOi_pay(int oi_pay) {
		this.oi_pay = oi_pay;
	}
	public int getOi_upoint() {
		return oi_upoint;
	}
	public void setOi_upoint(int oi_upoint) {
		this.oi_upoint = oi_upoint;
	}
	public int getOi_spoint() {
		return oi_spoint;
	}
	public void setOi_spoint(int oi_spoint) {
		this.oi_spoint = oi_spoint;
	}
	public List<OrderDetail> getDetailList() {
		return detailList;
	}
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getPiid() {
		return piid;
	}
	public void setPiid(String piid) {
		this.piid = piid;
	}
	public void setDetailList(List<OrderDetail> detailList) {
		this.detailList = detailList;
	}
	
	
	public void getAll() {
		System.out.println(getMi_id());
		System.out.println(getOi_phone());
		System.out.println(getOi_id());
		System.out.println(getOi_name());
		System.out.println(getOi_zip()	);
		System.out.println(getOi_addr1());
		System.out.println(getOi_addr2());
		System.out.println(getOi_memo());
		System.out.println(getOi_payment());
		System.out.println(getOi_invoice());
		System.out.println(getOi_status());
		System.out.println(getOi_date());
		System.out.println(getOi_pay()	);
		System.out.println(getOi_upoint());
		System.out.println(getOi_spoint());	
		//getDeList();
	}
	
}
