package dao;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import vo.*;

public class AdMemberDao {
	private JdbcTemplate jdbc;
	
	public AdMemberDao(DataSource dataSource) {
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
				where + " order by mi_date desc limit " + ((cpage - 1) * psize) + "," + psize ; 
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

	public MemberDetails getMemberDetails(String miid) {
	// ȸ�� �Ѹ��� �ּ������� �޾Ƽ� �����ϴ� �޼ҵ�
		String sql ="select * from t_member_info a, t_member_addr b where a.mi_id = b.mi_id and a.mi_id = '"+ miid +"' "; 
		// System.out.println(sql);
		MemberDetails memberDetails = jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			MemberDetails md = new MemberDetails();
			md.setMi_id(miid);
			md.setMi_name(rs.getString("mi_name"));
			md.setMi_birth(rs.getString("mi_birth"));
			md.setMi_email(rs.getString("mi_email"));
			md.setMi_status(rs.getString("mi_status"));
			md.setMi_point(rs.getInt("mi_point"));
			md.setMa_addr1(rs.getString("ma_addr1"));
			md.setMa_addr2(rs.getString("ma_addr2"));
			md.setMi_date(rs.getString("mi_date"));
			
            return md;
		});
		return memberDetails;
	}

	public int getMemberPointCount(String where) {
		// ȸ�� ���ڵ带 int�� rcnt�� �����ϴ� �޼ҵ�
			String sql = "select count(*) rcnt from t_member_info a, t_member_point b "+ where;
			// System.out.println(sql);
			int rcnt = jdbc.queryForObject(sql, Integer.class);
			
			return rcnt;
		}
	
	public List<MemberPoint> getPointList(String where, int cpage, int psize) {
		String sql = "select a.mi_id, a.mi_name, b.mp_su, b.mp_point, b.mp_desc, b.mp_detail, b.mp_sdate, b.mp_edate, b.ai_idx from t_member_info a, t_member_point b " 
				+ where +"  order by b.mp_idx desc limit " + ((cpage -1) * psize) + ", " + psize ;
		// System.out.println(sql);
		
		List<MemberPoint> pointList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				MemberPoint mp = new MemberPoint();
				mp.setMi_id(rs.getString("mi_id"));
	            mp.setMi_name(rs.getString("mi_name"));
	            mp.setMp_su(rs.getString("mp_su"));
	            mp.setMp_point(rs.getInt("mp_point"));
	            mp.setMp_desc(rs.getString("mp_desc"));
	            mp.setMp_detail(rs.getString("mp_detail"));
	            mp.setMp_sdate(rs.getString("mp_sdate"));
	            mp.setMp_edate(rs.getString("mp_edate"));
	            mp.setAi_idx(rs.getInt("ai_idx"));
	            return mp;
				
		});
		return pointList;
	}
	
	public MemberInfo getPointForm(String miid) {
	//	ȸ�� ����Ʈ ó�� �� ���� �޾ƿ��� �޼ҵ�
		String sql = "select * from t_member_info where mi_id = '"+ miid +"' ";
		MemberInfo memberPoint = jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			MemberInfo mi = new MemberInfo();
			mi.setMi_id(miid);
			mi.setMi_point(rs.getInt("mi_point"));
			
            return mi;
		});
		
		return memberPoint;
	}
	
	
	public int getGivePoint(String miid, int point, String mp_desc, String mp_detail) {
	// ȸ�� ����Ʈ ���� ó�� �޼ҵ�
		String sql = " insert into t_member_point (mi_id, mp_su, mp_point, mp_desc, mp_detail, mp_edate, ai_idx) " + 
				" values ('"+ miid +"','s',"+ point +", '"+ mp_desc +"','"+ mp_detail +"', DATE_ADD(NOW(), INTERVAL 1 YEAR), 1) ";
		// System.out.println(sql);
		int result = jdbc.update(sql);
		
		sql = "update t_member_info set mi_point = mi_point + "+ point +" where mi_id = '"+ miid +"' ";
		// System.out.println(sql);
		
		result =+ jdbc.update(sql);
		
		return result;
	}
}
