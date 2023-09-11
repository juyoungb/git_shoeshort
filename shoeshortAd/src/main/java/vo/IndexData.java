package vo;

import java.util.List;

public class IndexData {
	/*상단: 오늘 주문 접수, 오늘 가입 가입수, 이번달 매출, 총 회원, 누적 포인트 */
	private int tdOrderCnt,  allMem, todayMem, salesMonth, total,acmltPoint; 
	private int ve, prt, ce, vol;
	private int maleCnt, ordCntTotal, ordCnt1, ordCnt2, ordCnt3;
	private int pcnt, sale, unsale, ps, luckyMem, luckyDiff;
	private String pi_img1, pi_name;
	private List<NoticeInfo> noticeInfo; 
	private List<ChartData> salesChart; 
	private List<ChartData> ordChart; 
	private List<ChartData> memChart;
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
	public int getVe() {
		return ve;
	}
	public void setVe(int ve) {
		this.ve = ve;
	}
	public int getPrt() {
		return prt;
	}
	public void setPrt(int prt) {
		this.prt = prt;
	}
	public int getCe() {
		return ce;
	}
	public void setCe(int ce) {
		this.ce = ce;
	}
	public int getVol() {
		return vol;
	}
	public void setVol(int vol) {
		this.vol = vol;
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
	public int getPcnt() {
		return pcnt;
	}
	public void setPcnt(int pcnt) {
		this.pcnt = pcnt;
	}
	public int getSale() {
		return sale;
	}
	public void setSale(int sale) {
		this.sale = sale;
	}
	public int getUnsale() {
		return unsale;
	}
	public void setUnsale(int unsale) {
		this.unsale = unsale;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public int getLuckyMem() {
		return luckyMem;
	}
	public void setLuckyMem(int luckyMem) {
		this.luckyMem = luckyMem;
	}
	public int getLuckyDiff() {
		return luckyDiff;
	}
	public void setLuckyDiff(int luckyDiff) {
		this.luckyDiff = luckyDiff;
	}
	public String getPi_img1() {
		return pi_img1;
	}
	public void setPi_img1(String pi_img1) {
		this.pi_img1 = pi_img1;
	}
	public String getPi_name() {
		return pi_name;
	}
	public void setPi_name(String pi_name) {
		this.pi_name = pi_name;
	}
	public List<NoticeInfo> getNoticeInfo() {
		return noticeInfo;
	}
	public void setNoticeInfo(List<NoticeInfo> noticeInfo) {
		this.noticeInfo = noticeInfo;
	}
	public List<ChartData> getSalesChart() {
		return salesChart;
	}
	public void setSalesChart(List<ChartData> salesChart) {
		this.salesChart = salesChart;
	}
	public List<ChartData> getOrdChart() {
		return ordChart;
	}
	public void setOrdChart(List<ChartData> ordChart) {
		this.ordChart = ordChart;
	}
	public List<ChartData> getMemChart() {
		return memChart;
	}
	public void setMemChart(List<ChartData> memChart) {
		this.memChart = memChart;
	}
}
