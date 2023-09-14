package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class AdLuckyDao {
	private JdbcTemplate jdbc;
	
	public AdLuckyDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public List<LuckyInfo> getLuckyList(int elidx) {		
		String sql = "select a.el_idx, a.el_title, a.pi_id,a.mi_id, a.el_sdate, a.el_isview,a.el_min_price, a.el_max_price, a.el_final_price, "
				+ "a.el_edate, b.pi_name, b.pi_img1, mid(a.el_sdate, 11) stime, mid(el_edate, 11) etime "
				+ "from t_evt_lucky_list a, t_product_info b where a.pi_id = b.pi_id ";
		if(elidx != 0) {
			sql += " and a.el_idx = "+elidx;
		}
		System.out.println(sql);
		List <LuckyInfo> luckyList = jdbc.query(sql, new RowMapper<LuckyInfo>(){
			public LuckyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				LuckyInfo li = new LuckyInfo();						
				li.setEl_idx(rs.getInt("el_idx"));
				li.setEl_title(rs.getString("el_title"));
				li.setPi_id(rs.getString("pi_id"));
				li.setMi_id(rs.getString("mi_id"));
				li.setPi_img1(rs.getString("pi_img1")); 
				li.setEl_sdate(rs.getString("el_sdate")); 
				li.setStime(rs.getString("stime")); 
				li.setEl_edate(rs.getString("el_edate")); 
				li.setEtime(rs.getString("etime")); 
				li.setEl_isview(rs.getString("el_isview")); 				
				li.setEl_min_price(rs.getInt("el_min_price"));				
				li.setEl_max_price(rs.getInt("el_max_price"));
				li.setEl_final_price(rs.getInt("el_final_price"));
				return li;
			}			
		});
		
		return luckyList;
	}

	public int luckyDel(String elidx) {
		String sql = "delete from t_evt_lucky_list where el_idx = " + elidx;
		int result = jdbc.update(sql);
		// detail의 테이블도 다 지움
		sql = "delete from t_evt_lucky_list where el_idx=" + elidx;
		result = jdbc.update(sql);
		return result;
	
	}

	public int LuckyIn(LuckyInfo li) {
		String sql ="insert Into t_evt_lucky_list (el_idx, el_title, pi_id, el_min_price, el_max_price, el_sdate, el_edate, el_date, ai_idx, el_isview)" + 
				"VALUES (null, '"+li.getEl_title()+"', '"+li.getPi_id()+"', "+li.getEl_min_price()+","+li.getEl_max_price()+",'"+li.getEl_sdate()+"','"+li.getEl_edate()+"', now(), '1', '"+li.getEl_isview()+"')";
			System.out.println("sql :" +sql);
		int result = jdbc.update(sql);
		return result;
	}

	public List<LuckyInfo> geUpLuckyList(int elidx) {
		String sql = "select * from t_evt_lucky_list a, t_evt_lucky_detail b where a.mi_id = b.mi_id and a.el_isview='y' and el_idx=" + elidx;
		List <LuckyInfo> upLuckyList = jdbc.query(sql, new RowMapper<LuckyInfo>(){
			public LuckyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				LuckyInfo li = new LuckyInfo();						
				li.setEl_idx(rs.getInt("el_idx"));
				li.setEl_title(rs.getString("el_title"));
				li.setPi_id(rs.getString("pi_id"));
				li.setMi_id(rs.getString("mi_id"));
				li.setEl_sdate(rs.getString("el_sdate")); 
				li.setEl_edate(rs.getString("el_edate")); 
				li.setEl_isview(rs.getString("el_isview")); 
				li.setEl_final_price(rs.getInt("el_final_price")); 
				return li;
			}			
		});
		
		return upLuckyList;
	}

	public int luckyUp(LuckyInfo li) {
		String sql = "update t_evt_lucky_list set el_title = '"+li.getEl_title()+"', "
				+ " el_sdate = '"+li.getEl_sdate()+"', el_edate = '"+li.getEl_edate()+"' ,el_isview = '"+li.getEl_isview()+"', "
				+ " el_min_price="+li.getEl_min_price()+", el_max_price="+li.getEl_max_price()+" "
				+ " WHERE el_idx = '"+li.getEl_idx()+"' ";
		System.out.println(sql);
		int result = jdbc.update(sql);
		return result;
	}

	public int getLuckyCount() {
		String sql = "select count(*)rcnt from t_evt_lucky_list";
		
		int rcnt = jdbc.queryForObject(sql, Integer.class);
		
		
		return rcnt;
	}
	
}
