package vo;

public class RcmdSearch {
	private String gender = "men,women,kids";
	private String rc_season = "봄,여름,가을,겨울"; 
	private String rc_age = "10대,20대,30대,40대";
	private String rc_keyword = "바닷가,졸업식 ,수학여행,결혼식,소개팅,생일선물,운동회,어버이날";
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRc_season() {
		return rc_season;
	}
	public void setRc_season(String rc_season) {
		this.rc_season = rc_season;
	}
	public String getRc_age() {
		return rc_age;
	}
	public void setRc_age(String rc_age) {
		this.rc_age = rc_age;
	}
	public String getRc_keyword() {
		return rc_keyword;
	}
	public void setRc_keyword(String rc_keyword) {
		this.rc_keyword = rc_keyword;
	}
	
	
}
