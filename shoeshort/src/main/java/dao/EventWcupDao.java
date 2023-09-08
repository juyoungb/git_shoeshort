package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class EventWcupDao {
	private JdbcTemplate jdbc;
	
	public EventWcupDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	public EventWcupInfo getEventWcupList(String ewstatus){
		String sql ="select * from t_evt_wcup where ew_isview='y' and ew_status=?";
		////System.out.println(sql);
		List <EventWcupInfo> results = jdbc.query(sql, new RowMapper<EventWcupInfo>(){
			public EventWcupInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				EventWcupInfo ewi = new EventWcupInfo();						
				ewi.setEw_idx(rs.getInt("ew_idx"));
				ewi.setEw_title(rs.getString("ew_title"));
				ewi.setEw_csdate(rs.getString("ew_csdate"));
				ewi.setEw_cedate(rs.getString("ew_cedate"));
				ewi.setEw_vsdate(rs.getString("ew_vsdate"));
				ewi.setEw_vedate(rs.getString("ew_vedate"));
				ewi.setEw_rule(rs.getInt("ew_rule"));
				if(ewstatus.equals("b")) {
				String sql1 ="select count(*) from t_evt_wcup_join where ew_idx="+rs.getInt("ew_idx");
				//System.out.println(ewstatus+":getEventWcupList:"+sql1);
				ewi.setEw_people(jdbc.queryForObject(sql1, Integer.class));
				}else if(ewstatus.equals("a")) {
					String sql1 ="select count(*) from t_evt_wcup_list where ew_idx="+rs.getInt("ew_idx");
					//System.out.println(ewstatus+":getEventWcupList:"+sql1);
					ewi.setEw_people(jdbc.queryForObject(sql1, Integer.class));
				}
				return ewi;
			}			
		}, ewstatus);
		
		return results.isEmpty() ? null : results.get(0);	
	}
	
	public List<WcupDetail> getEventWcupDList(int ewidx){
		String sql ="select  a.mi_id,ewl_win, b.si_img, b.pi_id,b.si_idx from  t_evt_wcup_list a, t_style_info b where a.si_idx = b.si_idx  and ew_idx='"+ewidx+"' order by ewl_win desc";
		//System.out.println("getEvetnWcupDList :"+sql);
		List <WcupDetail> detailList = jdbc.query(sql, new RowMapper<WcupDetail>(){
			public WcupDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				WcupDetail wd = new WcupDetail();						
				wd.setEwl_win(rs.getInt("ewl_win"));
				wd.setMi_id(rs.getString("mi_id"));
				wd.setSi_img(rs.getString("si_img"));
				wd.setSi_idx(rs.getString("si_idx"));
				wd.setPi_id(rs.getString("Pi_id"));
				return wd;
			}			
		});
		
		return detailList;	
	}

	public List<StyleInfo> getStyleList(String miid) {
		String sql ="select*from t_style_info where mi_id='"+miid+"'";
		List<StyleInfo> styleList = jdbc.query(sql, new RowMapper<StyleInfo>() {
			public StyleInfo mapRow(ResultSet rs, int rowNum) throws SQLException{
				StyleInfo si =new StyleInfo();
				si.setMi_id(miid);
				si.setPi_id(rs.getString("pi_id"));				si.setSi_img(rs.getString("si_img"));
				si.setSi_content(rs.getString("si_content"));				si.setSi_isview(rs.getString("si_isview"));
				si.setSi_date(rs.getString("si_date"));				si.setSi_good(rs.getInt("Si_good"));
				si.setSi_read(rs.getInt("Si_read"));				si.setSi_idx(rs.getInt("Si_idx"));
				return si;
			}
		});
		return styleList;
	}

	public int joinWcup(int siidx, int ewidx, String miid) {
		int result=0;
		//이미 참여함
		if(wcupValid("list",miid,ewidx)==1) return 0;
		else {// 참여하지 않음
			String sql = "insert into t_evt_wcup_list values(null, ?,?,?,0)";	
			result = jdbc.update(sql,ewidx,siidx,miid);
		}
		return result;
	}
	public int wcupValid(String keyword, String miid, int ewidx) {
		int result =0;
		String sql = "select count(*) from t_style_info where mi_id='"+miid+"'";
		if(keyword.equals("list")) {
			result = jdbc.queryForObject(sql, Integer.class);
			if(result == 0) return 3;//  등록된 스타일이 없는 경우
		}
		sql = "select count(*) from t_evt_wcup_"+keyword+" where mi_id ='"+miid+"' and ew_idx ="+ewidx;
		//System.out.println("wcupValid :"+sql);
		result = jdbc.queryForObject(sql, Integer.class);
		
		return result;		
	}
	public EvtWcupRule getWcup(String uid, int ewidx) {
		String sql = "select * from t_evt_wcup_join where mi_id=? and ew_idx =?";
		// //System.out.println(sql);
		List<EvtWcupRule> results = jdbc.query(sql, new RowMapper<EvtWcupRule>(){
			public EvtWcupRule mapRow(ResultSet rs, int rowNum) throws SQLException {
				EvtWcupRule ewr = new EvtWcupRule();
				ewr.setEwj_idx(rs.getString("ewj_idx"));
				ewr.setEw_idx(ewidx+"");
				ewr.setMi_id(rs.getString("mi_id"));
				ewr.setEwj_ing(rs.getString("ewj_ing"));
				ewr.setEwj_seq(rs.getString("ewj_seq"));
				ewr.setEwj_status(rs.getString("ewj_status"));
	            return ewr;
			}
		}, uid, ewidx);
		
		return results.isEmpty() ? null : results.get(0);
	}
	// 월드컵 우승자 선택 메소드
	public int wcupWin(String miid, String rule, String winner, String ewidx) {//db수정필요
		//트랜잭션 추가해야함
		// 1.t_evt_wcup_join insert -> t_evt_wcup_list update -> t_member_point insert
		String sql ="select ewl_idx from t_evt_wcup_list where ew_idx = "+ewidx+" and mi_id = '"+winner+"'";
		System.out.println(sql);
		int ewlidx = jdbc.queryForObject(sql, Integer.class);//우승자 번호 추출
		
		sql = "insert into t_evt_wcup_join(ew_idx, mi_id, ewj_vid) values (?, ?, ?)";	
		int result = jdbc.update(sql,ewidx,miid,ewlidx); //result: 1
		sql = "update t_evt_wcup_list set ewl_win = ewl_win +1 where ewl_idx = ?";
        result += jdbc.update(sql,ewlidx); 	//result: 2
        sql = "update t_member_info set mi_point = mi_point + 500 where mi_id ='"+miid+"' ";
		result+= jdbc.update(sql);	//result: 3
		sql = "insert into t_member_point (mi_id, mp_point, mp_desc, mp_detail,mp_edate, ai_idx) values('"+miid+"'"
				+ ",500,'이벤트 참여', '스타일 월드컵' , date_add(now(), interval 1 year), 1)";
		result += jdbc.update(sql);	//result: 4
		
		return result;
	}
	// -----------------------------------------------
	public String wcupSeq(int cnt) {
		int a[] = new int[cnt];
		String seq ="";
		Random r = new Random();
		for(int i=0; i<cnt; i++){
			a[i] = r.nextInt(cnt) + 1;  
			for(int j=0; j<i; j++){
				if(a[i] == a[j]) i--;	
			}
		}
		for(int i=0; i<cnt; i++){
			seq+=a[i];
		}
		return seq;
	}
}

