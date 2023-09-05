package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class RcmdDao {
	private JdbcTemplate jdbc;
	
	public RcmdDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public int getRcmdCount() {
	// 상품 정보 레코드 개수를 받아오는 메소드
		String sql = "select count(*)rcnt from t_product_info";
		
		int rcnt = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
            return rs.getInt("rcnt");
		});
		return rcnt;
	}

	public List<ProductInfo> getRcmdList(int cpage, int psize, String where) {
	// 상품정보 리스트 목록을 받아와서 ProductInfo에 저장하는 메소드
		String sql = "select * from t_product_info " +
				where +" order by pi_date desc limit "+ ((cpage - 1) * psize) + ", "+ psize;
		// System.out.println(sql);
		
		List<ProductInfo> rcmdList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				ProductInfo pi = new ProductInfo();
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPb_id(rs.getString("pb_id"));
				pi.setPi_gubun(rs.getString("pi_gubun"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPi_price(rs.getInt("pi_price"));
				pi.setPi_cost(rs.getInt("pi_cost"));
				pi.setPi_dc(rs.getDouble("pi_dc"));
				pi.setPi_img1(rs.getString("pi_img1"));
				pi.setPi_date(rs.getString("pi_date"));
				pi.setPi_rc_season(rs.getString("pi_rc_season"));
				pi.setPi_rc_age(rs.getString("pi_rc_age"));
				pi.setPi_rc_keyword(rs.getString("pi_rc_keyword"));
	            return pi;
		});
		
		return rcmdList;
	}
}