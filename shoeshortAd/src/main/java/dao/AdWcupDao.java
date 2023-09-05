package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class AdWcupDao {
	private JdbcTemplate jdbc;
	public AdWcupDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	public List<EventWcupInfo> getEvtWcupList() {
		String sql ="select * from t_evt_wcup where ew_isview='y'";
			List <EventWcupInfo> wcupList = jdbc.query(sql, new RowMapper<EventWcupInfo>(){
				public EventWcupInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
					EventWcupInfo ewi = new EventWcupInfo();						
					ewi.setEw_idx(rs.getInt("ew_idx"));
					ewi.setEw_title(rs.getString("ew_title"));
					ewi.setEw_csdate(rs.getString("ew_csdate"));
					ewi.setEw_cedate(rs.getString("ew_cedate"));
					ewi.setEw_vsdate(rs.getString("ew_vsdate"));
					ewi.setEw_vedate(rs.getString("ew_vedate"));
					ewi.setEw_status(rs.getString("ew_status"));
					ewi.setEw_rule(rs.getInt("ew_rule"));
					if(rs.getString("ew_status").equals("b")) {//참여자 수 
						String sql1 ="select count(*) from t_evt_wcup_join where ew_idx="+rs.getInt("ew_idx");
						ewi.setEw_people(jdbc.queryForObject(sql1, Integer.class));
						ewi.setWcupDetail(getEventWcupDList(rs.getInt("ew_idx")));
					}
					return ewi;
				}			
			});
			
			return wcupList;
		}
		public List<WcupDetail> getEventWcupDList(int ewidx){
			String sql ="select  a.mi_id,ewl_win, b.si_img, b.pi_id,b.si_idx from  t_evt_wcup_list a, t_style_info b where a.si_idx = b.si_idx  and ew_idx='"+ewidx+"' order by ewl_win desc";

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

		public List<EventWcupInfo> getEvtWcupList(String ewidx) {
			String sql ="select * from t_evt_wcup where ew_isview='y' and ew_idx="+ewidx;
			List <EventWcupInfo> wcupList = jdbc.query(sql, new RowMapper<EventWcupInfo>(){
				public EventWcupInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
					EventWcupInfo ewi = new EventWcupInfo();						
					ewi.setEw_idx(rs.getInt("ew_idx"));
					ewi.setEw_title(rs.getString("ew_title"));
					ewi.setEw_csdate(rs.getString("ew_csdate"));
					ewi.setEw_cedate(rs.getString("ew_cedate"));
					ewi.setEw_vsdate(rs.getString("ew_vsdate"));
					ewi.setEw_vedate(rs.getString("ew_vedate"));
					ewi.setEw_status(rs.getString("ew_status"));
					ewi.setEw_rule(rs.getInt("ew_rule"));
					return ewi;
				}			
			});
			
			return wcupList;
		}

		public int WcupIn(EventWcupInfo ewi) {	

			String sql = "insert into t_evt_wcup values(null, ?, ? ,? ,? ,? ,? ,?,'a','y')";	
			int result = jdbc.update(sql,ewi.getAi_idx(), ewi.getEw_title(),ewi.getEw_csdate(),ewi.getEw_cedate(),
					ewi.getEw_vsdate().equals("")? null:ewi.getEw_vsdate(),
					ewi.getEw_vedate().equals("")? null:ewi.getEw_vedate(), ewi.getEw_rule()); //result: 1
			return result;
		}

		public int WcupDel(String ewidx) {//del
			int result = jdbc.update("update t_evt_wcup set ew_isview= 'n' where ew_idx = ?",ewidx);
			return result;
		}

		public int WcupUp(EventWcupInfo ewi) {
			//System.out.println(ewi.getEw_title()+" "+ewi.getEw_cedate()+" "+ewi.getEw_vsdate()+" "+ewi.getEw_vedate()+" "+	ewi.getEw_status()+" "+ewi.getEw_idx()); //result: 1
			String sql = "update t_evt_wcup set ew_title=?, ew_cedate=?, ew_vsdate=?, ew_vedate=?,ew_status=? where ew_idx=?";	
			int result = jdbc.update(sql, ewi.getEw_title(),ewi.getEw_cedate(),
					ewi.getEw_vsdate().equals("")? null:ewi.getEw_vsdate(),
					ewi.getEw_vedate().equals("")? null:ewi.getEw_vedate(),
					ewi.getEw_status(),ewi.getEw_idx()); //result: 1
			
			return result;
		}
}
