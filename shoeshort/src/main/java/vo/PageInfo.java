package vo;



public class PageInfo {
// 게시판목록, 상품목록 등에서 페이징에 필요한 정보들을 저장할 클래스
	private int cpage,  spage, psize, bsize, rcnt , pcnt;
	private String schtype, keyword, args, schargs, pcb, pcs, obargs, vargs, sch, ob, v, schG, schA,schS, schK;	
	private String gender;
	// schG = 검색조건 성별, schS = 검색조건 계절, schA = 검색조건 연령별, schK = 검색조건 키워드
	
	public int getCpage() {
		return cpage;
	}
	public void setCpage(int cpage) {
		this.cpage = cpage;
	}
	public int getSpage() {
		return spage;
	}
	public void setSpage(int spage) {
		this.spage = spage;
	}
	public int getPsize() {
		return psize;
	}
	public void setPsize(int psize) {
		this.psize = psize;
	}
	public int getBsize() {
		return bsize;
	}
	public void setBsize(int bsize) {
		this.bsize = bsize;
	}
	public int getRcnt() {
		return rcnt;
	}
	public void setRcnt(int rcnt) {
		this.rcnt = rcnt;
	}
	public int getPcnt() {
		return pcnt;
	}
	public void setPcnt(int pcnt) {
		this.pcnt = pcnt;
	}
	public String getSchtype() {
		return schtype;
	}
	public void setSchtype(String schtype) {
		this.schtype = schtype;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public String getSchargs() {
		return schargs;
	}
	public void setSchargs(String schargs) {
		this.schargs = schargs;
	}
	public String getPcb() {
		return pcb;
	}
	public void setPcb(String pcb) {
		this.pcb = pcb;
	}
	public String getPcs() {
		return pcs;
	}
	public void setPcs(String pcs) {
		this.pcs = pcs;
	}
	public String getObargs() {
		return obargs;
	}
	public void setObargs(String obargs) {
		this.obargs = obargs;
	}
	public String getVargs() {
		return vargs;
	}
	public void setVargs(String vargs) {
		this.vargs = vargs;
	}
	public String getSch() {
		return sch;
	}
	public void setSch(String sch) {
		this.sch = sch;
	}
	public String getOb() {
		return ob;
	}
	public void setOb(String ob) {
		this.ob = ob;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public String getSchG() {
		return schG;
	}
	public void setSchG(String schG) {
		this.schG = schG;
	}
	public String getSchA() {
		return schA;
	}
	public void setSchA(String schA) {
		this.schA = schA;
	}
	public String getSchS() {
		return schS;
	}
	public void setSchS(String schS) {
		this.schS = schS;
	}
	public String getSchK() {
		return schK;
	}
	public void setSchK(String schK) {
		this.schK = schK;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
