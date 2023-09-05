package vo;

public class StyleGood {
// 특정 댓글의 좋아요/싫어요 레코드를 저장하는 클래스
	public int sg_idx, si_idx;
	public String mi_id, sg_date;
	
	public int getSg_idx() {
		return sg_idx;
	}
	public void setSg_idx(int sg_idx) {
		this.sg_idx = sg_idx;
	}
	public int getSi_idx() {
		return si_idx;
	}
	public void setSi_idx(int si_idx) {
		this.si_idx = si_idx;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getSg_date() {
		return sg_date;
	}
	public void setSg_date(String sg_date) {
		this.sg_date = sg_date;
	}
	
}
