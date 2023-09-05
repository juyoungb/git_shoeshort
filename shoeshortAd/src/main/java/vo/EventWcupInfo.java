package vo;

import java.util.List;

public class EventWcupInfo {
	private int ai_idx,ew_idx, ew_rule, ew_rank, ew_people, seq;
	private String ew_title, ew_csdate, ew_cedate,  ew_vsdate, ew_vedate, ew_status,ew_vid;
	private String mi_id, sys, img1, img2, rand, round,winner;
	private List<WcupDetail> wcupDetail; 
	
	public int getAi_idx() {
		return ai_idx;
	}
	public void setAi_idx(int ai_idx) {
		this.ai_idx = ai_idx;
	}
	public int getEw_idx() {
		return ew_idx;
	}
	public void setEw_idx(int ew_idx) {
		this.ew_idx = ew_idx;
	}
	public int getEw_rule() {
		return ew_rule;
	}
	public void setEw_rule(int ew_rule) {
		this.ew_rule = ew_rule;
	}
	public int getEw_rank() {
		return ew_rank;
	}
	public void setEw_rank(int ew_rank) {
		this.ew_rank = ew_rank;
	}
	public int getEw_people() {
		return ew_people;
	}
	public void setEw_people(int ew_people) {
		this.ew_people = ew_people;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getEw_title() {
		return ew_title;
	}
	public void setEw_title(String ew_title) {
		this.ew_title = ew_title;
	}
	public String getEw_csdate() {
		return ew_csdate;
	}
	public void setEw_csdate(String ew_csdate) {
		this.ew_csdate = ew_csdate;
	}
	public String getEw_cedate() {
		return ew_cedate;
	}
	public void setEw_cedate(String ew_cedate) {
		this.ew_cedate = ew_cedate;
	}
	public String getEw_vsdate() {
		return ew_vsdate;
	}
	public void setEw_vsdate(String ew_vsdate) {
		this.ew_vsdate = ew_vsdate;
	}
	public String getEw_vedate() {
		return ew_vedate;
	}
	public void setEw_vedate(String ew_vedate) {
		this.ew_vedate = ew_vedate;
	}
	public String getEw_status() {
		return ew_status;
	}
	public void setEw_status(String ew_status) {
		this.ew_status = ew_status;
	}
	public String getEw_vid() {
		return ew_vid;
	}
	public void setEw_vid(String ew_vid) {
		this.ew_vid = ew_vid;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getSys() {
		return sys;
	}
	public void setSys(String sys) {
		this.sys = sys;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getRand() {
		return rand;
	}
	public void setRand(String rand) {
		this.rand = rand;
	}
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public List<WcupDetail> getWcupDetail() {
		return wcupDetail;
	}
	public void setWcupDetail(List<WcupDetail> wcupDetail) {
		this.wcupDetail = wcupDetail;
	}
}
