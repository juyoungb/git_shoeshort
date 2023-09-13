package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class EvtLuckyDao {
	private JdbcTemplate jdbc;
	
	public EvtLuckyDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public List<EvtLuckyInfo> getLuckyList(String pi_id) {
		String sql = "select a.el_idx,a.pi_id,b.Pi_img1, b.pi_name, a.el_sdate,a.el_edate,a.el_min_price,a.el_max_price, "+
				" CASE " +
				"  	WHEN" + 
				"   	instr(DATE_FORMAT(a.el_sdate, '%Y-%m-%d %p %h:%i'), 'PM') > 0" + 
				"   THEN" + 
				"        replace(DATE_FORMAT(a.el_sdate, '%Y-%m-%d %p %h:%i'), 'PM', '오후')" + 
				"   else" + 
				"        replace(DATE_FORMAT(a.el_sdate, '%Y-%m-%d %p %h:%i'), 'AM', '오전')" + 
				"   end as sdate," + 
				" CASE" + 
				"     WHEN" + 
				"     	instr(DATE_FORMAT(a.el_edate, '%Y-%m-%d %p %h:%i'), 'PM') > 0" + 
				"     then" + 
				"        replace(DATE_FORMAT(a.el_edate, '%Y-%m-%d %p %h:%i'), 'PM', '오후')" + 
				"     ELSE" + 
				"        replace(DATE_FORMAT(a.el_edate, '%Y-%m-%d %p %h:%i'), 'AM', '오전')" + 
				"     end as edate from t_evt_lucky_list a, t_product_info b where a.pi_id = b.pi_id and el_isview='y' ";
		if(pi_id != null) {
			sql += " and b.pi_id ='"+pi_id+"' ";
		}
		List <EvtLuckyInfo> evtList = jdbc.query(sql, new RowMapper<EvtLuckyInfo>(){
			public EvtLuckyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				EvtLuckyInfo el = new EvtLuckyInfo();						
				el.setEl_idx(rs.getInt("el_idx"));
				el.setPi_id(rs.getString("pi_id"));
				el.setPi_name(rs.getString("pi_name"));
				el.setPi_img1(rs.getString("pi_img1"));
				el.setSdate(rs.getString("sdate")); // 시간을 변경해서 가져오려고
				el.setEdate(rs.getString("edate"));// 시간을 변경해서 가져오려고
				
				el.setEl_sdate(rs.getString("el_sdate"));	
				el.setEl_edate(rs.getString("el_edate"));					
				el.setEl_min_price(rs.getInt("el_min_price"));			
				el.setEl_max_price(rs.getInt("el_max_price"));
					
				return el;
			}			
		});
		return  evtList;
	}

	public int luckyInsert(String mi_id, int price, int el_idx)  {
		String sql = "select count(*) from t_evt_lucky_detail where el_idx="+el_idx+" and mi_id='"+mi_id+"' ";
		System.out.println("sql :" + sql);
		int result = jdbc.queryForObject(sql, Integer.class);
		
		if(result == 0 ) {
			sql = "insert into t_evt_lucky_detail values(null, "+el_idx+", '"+mi_id+"', "+price+")";
			System.out.println("sql :" + sql);
			result = jdbc.update(sql);
			return result;
		}
		// 이미참여했으면
		return -1;
		
		
	}




}
