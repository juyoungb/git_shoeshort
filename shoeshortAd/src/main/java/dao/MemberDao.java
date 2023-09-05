package dao;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import vo.*;

public class MemberDao {
	private JdbcTemplate jdbc;
	
	public MemberDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	public int getMemberCount(String where) {
	// ȸ�� ���ڵ带 int�� rcnt�� �����ϴ� �޼ҵ�
		String sql = "select count(*) rcnt from t_member_info "+ where;
		int rcnt = jdbc.queryForObject(sql, Integer.class);
		
		return rcnt;
	}

	public List<MemberInfo> getMemberList(int cpage, int psize, String where) {
	// ȸ�� ������ List<MemberInfo> �� �ν��Ͻ��� ���� �� �����ϴ� �޼ҵ�
		String sql ="select * from t_member_info " + 
				where + "limit " + ((cpage - 1) * psize) + "," + psize ; 
		System.out.println(sql);
		
		List<MemberInfo> memberList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				MemberInfo mi = new MemberInfo();
				mi.setMi_id(rs.getString("mi_id"));
	            mi.setMi_pw(rs.getString("mi_pw"));
	            mi.setMi_name(rs.getString("mi_name"));
	            mi.setMi_gender(rs.getString("mi_gender"));
	            mi.setMi_birth(rs.getString("mi_birth"));
	            mi.setMi_phone(rs.getString("mi_phone"));
	            mi.setMi_email(rs.getString("mi_email"));
	            mi.setMi_point(rs.getInt("mi_point"));
	            mi.setMi_status(rs.getString("mi_status"));
	            mi.setMi_date(rs.getString("mi_date"));
	            mi.setMi_lastlogin(rs.getString("mi_lastlogin"));
	            return mi;
				
		});
		return memberList;
	}

	public MemberAddr getMemberAddr(String miid) {
	// ȸ�� �Ѹ��� �ּ������� �޾Ƽ� �����ϴ� �޼ҵ�
		String sql ="select * from t_member_addr where mi_id = "+ miid +"' "; 
		MemberAddr memberAddr = jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			MemberAddr ma = new MemberAddr();
			
            return ma;
		});
		return memberAddr;
	}
}
