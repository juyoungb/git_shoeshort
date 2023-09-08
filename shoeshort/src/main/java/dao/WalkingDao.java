package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class WalkingDao {
	private JdbcTemplate jdbc;
	
	public WalkingDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public List<ProductInfo> getProductInfo() {
		// 산책 신발 추천 정보들을 리턴하는 메소드
		List<ProductInfo> product = new ArrayList<ProductInfo>();
		String sql = "SELECT * FROM t_product_info WHERE pi_gubun = 'm' AND pcb_id = 'a'  " + 
				" AND pi_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() order by pi_sale desc " + 
				" LIMIT 1 ";
		
			product.addAll(jdbc.query(sql, (ResultSet rs, int rowNum) -> {
			ProductInfo pi = new ProductInfo();
			pi.setPi_id(rs.getString("pi_id"));
			pi.setPi_name(rs.getString("pi_name"));
			pi.setPi_price(rs.getInt("pi_price"));
			pi.setPi_dc(rs.getDouble("pi_dc"));
			pi.setPi_img1(rs.getString("pi_img1"));
			return pi;
		}));
		
		sql = "SELECT * FROM t_product_info WHERE pi_gubun = 'w' AND pcb_id = 'a'  " + 
				" AND pi_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() order by pi_sale desc " + 
				" LIMIT 1 ";
			product.addAll(jdbc.query(sql, (ResultSet rs, int rowNum) -> {
			ProductInfo pi = new ProductInfo();
			pi.setPi_id(rs.getString("pi_id"));
			pi.setPi_name(rs.getString("pi_name"));
			pi.setPi_price(rs.getInt("pi_price"));
			pi.setPi_dc(rs.getDouble("pi_dc"));
			pi.setPi_img1(rs.getString("pi_img1"));
			return pi;
		}));
		
		sql = "SELECT * FROM t_product_info WHERE pi_gubun = 'k' AND pcb_id = 'a'  " + 
				" AND pi_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() order by pi_sale desc " + 
				" LIMIT 1 ";
			product.addAll(jdbc.query(sql, (ResultSet rs, int rowNum) -> {
			ProductInfo pi = new ProductInfo();
			pi.setPi_id(rs.getString("pi_id"));
			pi.setPi_name(rs.getString("pi_name"));
			pi.setPi_price(rs.getInt("pi_price"));
			pi.setPi_dc(rs.getDouble("pi_dc"));
			pi.setPi_img1(rs.getString("pi_img1"));
			return pi;
		}));
		
		return product;
	}
}
