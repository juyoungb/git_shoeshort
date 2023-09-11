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

	public int memberInsert(MemberInfo mi) {
		String sql = "insert into t_member_info  values (?, ?, ?, ?, ?, ?, ?,1000, 'a', now(), null)";
		int result = jdbc.update(sql, mi.getMi_id(), mi.getMi_pw(), mi.getMi_name(), mi.getMi_gender(), mi.getMi_birth(),mi.getMi_phone(), mi.getMi_email());
		sql ="insert into t_member_addr(mi_id,ma_rname, ma_name, ma_phone, ma_zip, ma_addr1,ma_addr2, ma_basic) values(?, ?, '기본 주소', ?, ?, ?, ?, 'y')";
		result = jdbc.update(sql, mi.getMi_id(), mi.getMi_name(), mi.getMi_phone(), mi.getMa_zip(), mi.getMa_addr1(), mi.getMa_addr2()); 
		
		return result;
	}

	public int chkDupId(String uid) {
		String sql = "select count(*) from t_member_info where mi_id = '" + uid + "' ";
		int result = jdbc.queryForObject(sql, Integer.class);
		return result;
	}

	public int memberUpdate(MemberInfo mi) {
		String sql = "update t_member_info set mi_phone='" + mi.getMi_phone() + "', mi_email='" + mi.getMi_email() + "' "+
				 "where mi_id='" + mi.getMi_id() + "' ";
		int result = jdbc.update(sql);
	
		return result;
	}

	public List<PointDetail> getPointList(String miid) {
		String sql="select*from t_member_point where mi_id='"+miid+"' ";
		List<PointDetail> pointList = jdbc.query(sql, new RowMapper<PointDetail>() {
			public PointDetail mapRow(ResultSet rs, int rowNum) throws SQLException{
				PointDetail pd =new PointDetail();
				pd.setMp_sdate(rs.getString("mp_sdate"));
				pd.setMp_edate(rs.getString("mp_edate"));
				pd.setMp_desc(rs.getString("mp_desc"));
				pd.setMp_detail(rs.getString("mp_detail"));
				pd.setMp_point(rs.getInt("mp_point"));
				pd.setMp_su(rs.getString("mp_su"));
				return pd;
			}
		});
		return pointList;
	}
	public int setMemberInfo(String uid, String kind, String data) {
		String sql = "update t_member_info set mi_"+kind+"='"+data+"' where mi_id='"+uid+"'";
		int result = jdbc.update(sql);
	
		return result;
	}

	public MemberInfo getMemberInfo(String mi_id, String mi_pw) {
		String sql = "select * from t_member_info where mi_id = ? and mi_pw = ? ";
		List<MemberInfo> results = jdbc.query(sql, new RowMapper<MemberInfo>(){
			public MemberInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
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
			}
		}, mi_id, mi_pw);
		// ������ �������� ������ �Ű������� ���� �߰��� �ʿ䰡 ����
		
		return results.isEmpty() ? null : results.get(0);
	}

	public List<MemberAddr> getMemberAddr(String mi_id) {
		String sql="select*from t_member_addr where mi_id='"+mi_id+"' order by ma_basic desc";
		List<MemberAddr> addrList = jdbc.query(sql, new RowMapper<MemberAddr>() {
			public MemberAddr mapRow(ResultSet rs, int rowNum) throws SQLException{
				MemberAddr ma =new MemberAddr();
				ma.setMa_idx(rs.getInt("ma_idx"));
				ma.setMi_id(mi_id);
				ma.setMa_name(rs.getString("ma_name"));
				ma.setMa_rname(rs.getString("ma_rname"));
				ma.setMa_phone(rs.getString("ma_phone"));
				ma.setMa_zip(rs.getString("ma_zip"));
				ma.setMa_addr1(rs.getString("ma_addr1"));
				ma.setMa_addr2(rs.getString("ma_addr2"));
				ma.setMa_basic(rs.getString("ma_basic"));
				ma.setMa_date(rs.getString("ma_date"));
				return ma;
			}
		});
		return addrList;
	}
	
	public MemberAddr getMemberAddr(int maidx) {
		String sql = "select*from t_member_addr where ma_idx=?";
		List<MemberAddr> results = jdbc.query(sql, new RowMapper<MemberAddr>(){
			public MemberAddr mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberAddr ma = new MemberAddr();
				ma.setMa_idx(maidx);
				ma.setMa_name(rs.getString("ma_name"));
				ma.setMa_rname(rs.getString("ma_rname"));
				ma.setMa_phone(rs.getString("ma_phone"));
				ma.setMa_zip(rs.getString("ma_zip"));
				ma.setMa_addr1(rs.getString("ma_addr1"));
				ma.setMa_addr2(rs.getString("ma_addr2"));
				ma.setMa_basic(rs.getString("ma_basic"));
	            return ma;
			}
		}, maidx);		
		return results.isEmpty() ? null : results.get(0);
	}
	
	public int AddrInsert(MemberAddr ma) {
		String sql="";
		if(ma.getMa_basic().equals("y")) AddrUpBasic(ma.getMi_id());
		sql = "insert into t_member_addr values(null, ?, ?, ?, ?, ?, ?, ?, ?, now())";
		int result = jdbc.update(sql, ma.getMi_id(), ma.getMa_name(), ma.getMa_rname(), ma.getMa_phone(),ma.getMa_zip(), ma.getMa_addr1(),ma.getMa_addr2(), ma.getMa_basic());
			
			return result;
	}

	public int AddrUpdate(MemberAddr ma) {
		ma.getAll();
		if(ma.getMa_basic().equals("y")) AddrUpBasic(ma.getMi_id());
		String sql = "update t_member_addr set ma_name='"+ma.getMa_name()+"', ma_rname='"+ma.getMa_rname()+"', ma_phone='"+ma.getMa_phone()+
				"', ma_zip='"+ma.getMa_zip()+"', ma_addr1='"+ma.getMa_addr1()+"',  ma_addr2='"+ma.getMa_addr2()+"', ma_basic='"+ma.getMa_basic()+"' where ma_idx="+ma.getMa_idx(); 
		int result = jdbc.update(sql);
		return result;
	}
	public void AddrUpBasic(String uid) {
		String sql = "update t_member_addr set ma_basic='n' where mi_id='"+uid+"' "; 
		jdbc.update(sql);
	}

	public int AddrDelete(String maidx) {
		String sql = "delete from t_member_addr where ma_idx="+maidx;
		int result = jdbc.update(sql);
	
		return result;
	}

	public String getMemberId(MemberInfo mi) {
		String sql = "select mi_id from t_member_info where mi_name='"+mi.getMi_name()+"' and mi_email ='"+mi.getMi_email()+"' ";
		String mi_id = "";
		try {
			mi_id = jdbc.queryForObject(sql, String.class);
		}catch(Exception e) {
			 mi_id ="none";
		}
		return mi_id;
	}

	public int checkMember(MemberInfo mi) {
		String sql = "select count(*) from t_member_info where mi_name='"+mi.getMi_name()+"' and mi_email ='"+mi.getMi_email()+"' and mi_id='"+mi.getMi_id()+"'";
		int result = jdbc.queryForObject(sql, Integer.class);
		return result;
	}
}
