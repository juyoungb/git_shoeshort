package api;
import dao.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class ApiDao {
	private JdbcTemplate jdbc;
	public ApiDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
	public MemberInfo isMember(HashMap<String, String> loginInfo) {
		System.out.println("apiDao");
		//mi.setMi_gender(loginInfo.get("gender").equals("male") ? "남":"여");
		String sql ="select mi_id, mi_pw from t_member_info where replace(substring(mi_birth, 6),'-','') ='"+loginInfo.get("birthday")+"' and mi_gender = '"+
					(loginInfo.get("gender").equals("male") ? "남":"여")+"'";
		if(loginInfo.containsKey("email"))	sql+=" and mi_email ='"+loginInfo.get("email")+"' ";
		System.out.println(sql);
		List<MemberInfo> results = jdbc.query(sql, new RowMapper<MemberInfo>(){
			public MemberInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberInfo mi = new MemberInfo();
				mi.setMi_id(rs.getString("mi_id"));
	            mi.setMi_pw(rs.getString("mi_pw"));
	            return mi;
			}
		});
		if(!results.isEmpty()) {
			sql="update t_member_info set mi_lastlogin=now() where mi_id='"+results.get(0).getMi_id()+"'";
			jdbc.update(sql);
		}
		return results.isEmpty() ? null : results.get(0);
	}
}
