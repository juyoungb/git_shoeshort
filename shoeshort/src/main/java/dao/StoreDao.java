package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.json.*;
import org.json.JSONObject;
import org.json.simple.*;
import org.json.simple.JSONArray;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class StoreDao {
	private JdbcTemplate jdbc;
	
	public StoreDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public List<StoreInfo> getStoreList(String where) {
	// 처음 매장 관리에 들어왔을 때 나오는 매장 정보 리스트를 리턴하는 메소드
		String sql = "select * from t_brandstore_info "+ where;
		// System.out.println(sql);
		List<StoreInfo> si = jdbc.query(sql, (ResultSet rs, int rowNum)->{
			StoreInfo s = new StoreInfo();
			s.setAi_idx(rs.getInt("ai_idx"));
			s.setBsi_idx(rs.getInt("bsi_idx"));
			s.setPb_id(rs.getString("pb_id"));
			s.setBsi_name(rs.getString("bsi_name"));
			s.setBsi_addr(rs.getString("bsi_addr"));
			s.setBsi_date(rs.getString("bsi_date"));
			return s;
		});
		return si;
		// 커밋 테스트입니다.
	}
}
