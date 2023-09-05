package vo;

import java.util.List;

public class IndexData {
	/*상단: 오늘 주문 접수, 오늘 가입 가입수, 이번달 매출, 총 회원, 누적 포인트 */
	private int tdOrderCnt,  allMem, todayMem, salesMonth, total,acmltPoint; 
	private int maleCnt, ordCntTotal, ordCnt1, ordCnt2, ordCnt3;
	private int ps, psOn, psOff, psSold;
	private List<NoticeInfo> noticeInfo; 
	public int getTdOrderCnt() {
		return tdOrderCnt;
	}
	public void setTdOrderCnt(int tdOrderCnt) {
		this.tdOrderCnt = tdOrderCnt;
	}
	public int getAllMem() {
		return allMem;
	}
	public void setAllMem(int allMem) {
		this.allMem = allMem;
	}
	public int getTodayMem() {
		return todayMem;
	}
	public void setTodayMem(int todayMem) {
		this.todayMem = todayMem;
	}
	public int getSalesMonth() {
		return salesMonth;
	}
	public void setSalesMonth(int salesMonth) {
		this.salesMonth = salesMonth;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getAcmltPoint() {
		return acmltPoint;
	}
	public void setAcmltPoint(int acmltPoint) {
		this.acmltPoint = acmltPoint;
	}
	public int getMaleCnt() {
		return maleCnt;
	}
	public void setMaleCnt(int maleCnt) {
		this.maleCnt = maleCnt;
	}
	public int getOrdCntTotal() {
		return ordCntTotal;
	}
	public void setOrdCntTotal(int ordCntTotal) {
		this.ordCntTotal = ordCntTotal;
	}
	public int getOrdCnt1() {
		return ordCnt1;
	}
	public void setOrdCnt1(int ordCnt1) {
		this.ordCnt1 = ordCnt1;
	}
	public int getOrdCnt2() {
		return ordCnt2;
	}
	public void setOrdCnt2(int ordCnt2) {
		this.ordCnt2 = ordCnt2;
	}
	public int getOrdCnt3() {
		return ordCnt3;
	}
	public void setOrdCnt3(int ordCnt3) {
		this.ordCnt3 = ordCnt3;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public int getPsOn() {
		return psOn;
	}
	public void setPsOn(int psOn) {
		this.psOn = psOn;
	}
	public int getPsOff() {
		return psOff;
	}
	public void setPsOff(int psOff) {
		this.psOff = psOff;
	}
	public int getPsSold() {
		return psSold;
	}
	public void setPsSold(int psSold) {
		this.psSold = psSold;
	}
	public List<NoticeInfo> getNoticeInfo() {
		return noticeInfo;
	}
	public void setNoticeInfo(List<NoticeInfo> noticeInfo) {
		this.noticeInfo = noticeInfo;
	}
}
