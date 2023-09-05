package dao;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import vo.*;

public class LoginDao {
	private JdbcTemplate jtbcTemplate;
	
	public LoginDao(DataSource dataSource) {
		this.jtbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public AdminInfo getLoginInfo(String uid, String pwd) {
		String sql = "select * from t_admin_info where ai_id = ? and ai_pw = ? ";
		List<AdminInfo> results = jtbcTemplate.query(sql, new RowMapper<AdminInfo>(){
			public AdminInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdminInfo ai = new AdminInfo();
				ai.setAi_idx(rs.getInt("ai_idx"));		ai.setAi_id(rs.getString("ai_id"));
				ai.setAi_pw(rs.getString("ai_pw"));		ai.setAi_name(rs.getString("ai_name"));
	            return ai;
			}
		}, uid, pwd);
		return results.isEmpty() ? null : results.get(0);
	}
}
