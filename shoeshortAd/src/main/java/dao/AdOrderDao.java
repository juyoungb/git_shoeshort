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
		String sql = "select count(*) from t_order_info a, t_member_info c  "+ where +" and a.mi_id= c.mi_id ";
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
				oi.setOi_id(rs.getString("oi_id"));
				oi.setMi_id(rs.getString("mi_id"));
				oi.setMi_name(rs.getString("mi_name"));
				oi.setCnt(rs.getString("cnt"));
				oi.setOi_pay(rs.getInt("oi_pay"));
				oi.setOi_date(rs.getString("oi_date"));
				oi.setOi_status(rs.getString("oi_status"));
	            return oi;
		});
		return memberList;
	}
	public int upOrderStatus(String where, String schOrder) {
		int result = jdbc.update("update t_order_info set oi_status='"+schOrder+"' "+where);
		return result;
	}
	public OrderInfo getOrderInfo(String uid, String oiid) {
		String sql="select*from t_order_info where mi_id='"+uid+"' and oi_id='"+oiid+"' ";
		List<OrderInfo> results = jdbc.query(sql, new RowMapper<OrderInfo>(){
			public OrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderInfo oi =new OrderInfo();
				oi.setOi_id(oiid);								oi.setMi_id(uid);
				oi.setOi_name(rs.getString("oi_name"));			oi.setOi_phone(rs.getString("oi_phone"));
				oi.setOi_zip(rs.getString("oi_zip"));			oi.setOi_addr1(rs.getString("oi_addr1"));
				oi.setOi_addr2(rs.getString("oi_addr2"));		oi.setOi_memo(rs.getString("oi_memo"));
				oi.setOi_payment(rs.getString("oi_payment"));	oi.setOi_invoice(rs.getString("oi_invoice"));
				oi.setOi_status(rs.getString("oi_status"));		oi.setOi_date(rs.getString("oi_date"));
				oi.setOi_pay(rs.getInt("oi_pay"));				oi.setOi_upoint(rs.getInt("oi_upoint"));
				oi.setOi_spoint(rs.getInt("oi_spoint"));		oi.setDetailList(setDeList(oiid));
				return oi;				
			}
		});
		return results.isEmpty() ? null : results.get(0);
	}
	public List<OrderDetail> setDeList(String oiid){
		String sql="select*from t_order_detail where oi_id='"+oiid+"' ";
		List<OrderDetail> orderDetail = jdbc.query(sql, new RowMapper<OrderDetail>() {
			public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException{
				OrderDetail od =new OrderDetail();
				od.setOi_id(rs.getString("oi_id"));				od.setPi_id(rs.getString("pi_id"));
				od.setOd_name(rs.getString("od_name"));				od.setOd_img(rs.getString("od_img"));
				od.setOd_idx(rs.getInt("od_idx"));				od.setPs_idx(rs.getInt("ps_idx"));
				od.setOd_cnt(rs.getInt("od_cnt"));				od.setOd_price(rs.getInt("od_price"));
				od.setOd_size(rs.getInt("od_size"));				
				return od;				
			}
		});
		return orderDetail;
	}
	public MemberInfo getMemberInfo(String uid) {
		String sql = "select * from t_member_info where mi_id = ?";
		List<MemberInfo> results = jdbc.query(sql, new RowMapper<MemberInfo>(){
			public MemberInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberInfo mi = new MemberInfo();
				mi.setMi_id(rs.getString("mi_id"));	            mi.setMi_name(rs.getString("mi_name"));
	            mi.setMi_birth(rs.getString("mi_birth"));       mi.setMi_email(rs.getString("mi_email"));
	            return mi;
			}
		}, uid);
		
		return results.isEmpty() ? null : results.get(0);
	}
}
