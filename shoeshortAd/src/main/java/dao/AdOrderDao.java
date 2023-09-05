package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class AdOrderDao {
	private JdbcTemplate jdbc;
	public AdOrderDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
	public int getOrderCount(String where) {
		String sql = "select count(*) from t_order_info "+ where;
		System.out.println(sql);
		int rcnt = jdbc.queryForObject(sql, Integer.class);
		
		return rcnt;
	}
	public List<OrderInfo> getMemberList(int cpage, int psize, String where) {
		String sql="select a.*, c.mi_name, sum(b.od_cnt) cnt from t_order_info a, t_order_detail b, t_member_info c " + 
		  where+" and a.oi_id = b.oi_id and a.mi_id = c.mi_id group by oi_id order by oi_date desc limit " + ((cpage - 1) * psize) + "," + psize ; 	
		System.out.println(sql);
		
		List<OrderInfo> memberList = jdbc.query(sql,(ResultSet rs, int rowNum) -> {
				OrderInfo oi = new OrderInfo();
				oi.setOi_id(rs.getString("oi_id"));			oi.setMi_id(rs.getString("mi_id"));
				oi.setMi_name(rs.getString("mi_name"));		oi.setCnt(rs.getString("cnt"));
				oi.setOi_pay(rs.getInt("oi_pay"));			oi.setOi_date(rs.getString("oi_date"));
				oi.setOi_status(rs.getString("oi_status"));
	            return oi;
		});
		return memberList;
	}
	public int upOrderStatus(String where, String schOrder) {
		int result = jdbc.update("update t_order_info set oi_status='"+schOrder+"' "+where);
		return result;
	}

}
