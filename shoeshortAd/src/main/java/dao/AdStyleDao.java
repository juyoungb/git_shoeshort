package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class AdStyleDao {
	private JdbcTemplate jdbc;
	
	public AdStyleDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public int getStyleCount(String where) {
		// 스타일 정보 레코드 개수를 받아오는 메소드
		String sql = "select count(*)rcnt from t_style_info "+ where;
		
		int result = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
	        return rs.getInt("rcnt");
		});
		return result;
	}

	public List<StyleInfo> getStyleList(int cpage, int psize, String where) {
	// adStyleList에서 보여줄 정보들을 List<StyleInfo>형 인스턴스에 저장 후 리턴하는 메소드
		String sql = "select si_idx, mi_id, pi_id, si_img, si_content, if(curdate() = date(si_date), right(si_date,8)," + 
				" replace(mid(si_date, 3, 8), '-', '.')) wdate, si_read, si_good from t_style_info "  
				+ where +" limit "+ ((cpage - 1) * psize) + ", "+ psize;
		// System.out.println(sql);
		List<StyleInfo> styleList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				StyleInfo si = new StyleInfo();
				si.setSi_idx(rs.getInt("si_idx"));
				si.setMi_id(rs.getString("mi_id"));
				si.setSi_content(rs.getString("si_content"));
				si.setPi_id(rs.getString("pi_id"));
				si.setSi_good(rs.getInt("si_good"));
				si.setSi_img(rs.getString("si_img"));
				si.setSi_date(rs.getString("wdate"));
				si.setSi_good(rs.getInt("si_read"));
				si.setSi_good(rs.getInt("si_good"));
	            return si;
		});
		
		return styleList;
	}

	public int getStyleDel(int siidx, String piid) {
		// 스타일 삭제 쿼리 실행 메소드
		String sql = "update t_product_info set pi_stylecnt = pi_stylecnt - 1 where pi_id = '"+ piid +"' ";
		int result = jdbc.update(sql);	// t_product_info 테이블에 pi_style 개수를 -1해줌
		
		sql = "delete from t_style_good where si_idx = "+ siidx;
		result = jdbc.update(sql);
		
		sql = "delete from t_style_info where si_idx = "+ siidx;
		result = jdbc.update(sql);
		
		return result;
	}

	public StyleInfo getStyleView(int siidx) {
	// adStyleView에 보여줄 값들을 StyleInfo형 인스턴스에 저장 후 리턴하는 메소드
		String sql = "select * from t_style_info where si_idx = "+ siidx;
		
		StyleInfo styleView = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
			StyleInfo si = new StyleInfo();
			si.setSi_idx(rs.getInt("si_idx"));
			si.setMi_id(rs.getString("mi_id"));
			si.setPi_id(rs.getString("pi_id"));
			si.setSi_img(rs.getString("si_img"));
			si.setSi_good(rs.getInt("si_good"));
			si.setSi_read(rs.getInt("si_read"));
			si.setSi_content(rs.getString("si_content"));
			si.setSi_date(rs.getString("si_date"));
            return si;
		});
		return styleView;
	}

	public ProductInfo getStyleProduct(String piid) {
	// adStyleView에서 보여줄 상품 정보를 ProductInfo형 인스턴스로 저장하여 리턴하는 메소드
		String sql = "select * from t_product_info where pi_id = '"+ piid +"' ";
		
		ProductInfo styleProduct = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
			ProductInfo pi = new ProductInfo();
			pi.setPi_id(piid);
			pi.setPi_name(rs.getString("pi_name"));
			pi.setPi_price(rs.getInt("pi_price"));
			pi.setPi_img1(rs.getString("pi_img1"));
			
            return pi;
		});		
		return styleProduct;
	}
}
