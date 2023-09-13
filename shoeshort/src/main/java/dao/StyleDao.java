package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class StyleDao {
	private JdbcTemplate jdbc;
	
	public StyleDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public List<StyleInfo> getStyle(int cpage, int psize, String orderBy) {
	// 스타일 목록을 받아와서 StyleInfo에 저장하는 메소드 
		String sql = "select * from t_style_info "  + orderBy +" limit "+ ((cpage - 1) * psize) + ", "+ psize;
		List<StyleInfo> styleList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				StyleInfo si = new StyleInfo();
				si.setSi_idx(rs.getInt("si_idx"));
				si.setMi_id(rs.getString("mi_id"));
				si.setPi_id(rs.getString("pi_id"));
				si.setSi_img(rs.getString("si_img"));
				si.setSi_good(rs.getInt("si_good"));
				si.setSi_content(rs.getString("si_content"));
	            return si;
		});
		
		return styleList;
	}

	public List<StyleInfo> getMemStyle(int cpage, int psize, String orderBy, String miid) {
	// 회원 스타일 리스트를 List<StyleInfo>형 인스턴스에 저장 후 리턴 하는 메소드	
		String sql = "select * from t_style_info where mi_id = '"+ miid +"' "+ orderBy +" limit "+ ((cpage - 1) * psize) + ", "+ psize;
		System.out.println(sql);
			List<StyleInfo> memStyle = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				StyleInfo si = new StyleInfo();
				si.setSi_idx(rs.getInt("si_idx"));
				si.setMi_id(rs.getString("mi_id"));
				si.setPi_id(rs.getString("pi_id"));
				si.setSi_img(rs.getString("si_img"));
				si.setSi_good(rs.getInt("si_good"));
				si.setSi_content(rs.getString("si_content"));
	            return si;
		});
			
			return memStyle;
	}
	
	public int readUpdate(int siidx) {
	// 해당 스타일글에 조회수 증가 메소드
		String sql = "update t_style_info set si_read = si_read + 1 where si_idx = "+ siidx;
		
		int result = jdbc.update(sql);
		
		return result;
	}

	public StyleInfo getStyleView(int siidx) {
	// 해당 스타일글에 스타일 정보를 받아오는 메소드
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
	public int getStyleCount() {
		// 스타일 정보 레코드 개수를 받아오는 메소드
		String sql = "select count(*)rcnt from t_style_info";
		
		int result = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
            return rs.getInt("rcnt");
		});
		return result;
	}
	
	public ProductInfo getStyleProduct(String piid) {
	// 해당 스타일글에 상품 정보를 받아오는 메소드	
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

	public MemberInfo getLoginInfo(String miid) {
		String sql = "select * from t_member_info where mi_id = '"+ miid +"' ";
		
		MemberInfo loginInfo = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
			MemberInfo mi = new MemberInfo();
			mi.setMi_id(miid);
			
            return mi;
		});
		return loginInfo;
	}

	public int getStyleDel(int siidx, String piid) {
	// 스타일 삭제 쿼리 실행 메소드
		String sql = "update t_product_info set pi_stylecnt = pi_stylecnt - 1 where pi_id ='"+ piid +"' ";
		
		int result = jdbc.update(sql);	// t_product_info테이블에 pi_style 값을 -1하는 쿼리 실행
		
		sql = "delete from t_style_good where si_idx = "+ siidx;
		
		result = jdbc.update(sql);
		
		sql = "delete from t_style_info where si_idx = "+ siidx;
		
		result = jdbc.update(sql);
		
		return result;
	}

	public int getStyleGood(StyleGood styleGood) {
	// 스타일 좋아요 처리 메소드
		String sql = "select count(*) from t_style_good where mi_id = '"+ styleGood.getMi_id() + "' and si_idx = "+ styleGood.getSi_idx();
		System.out.println(sql);

		int result = jdbc.queryForObject(sql, Integer.class);
		
		if (result == 0) {	// 아직 좋아요를 하지 않은 회원이면 아래 쿼리 실행
			
			sql = "update t_style_info set si_good = si_good + 1 where si_idx = "+ styleGood.getSi_idx();
			System.out.println(sql);
    		result = jdbc.update(sql);
    		
    		sql = "insert into t_style_good (mi_id, si_idx) values ('"+ styleGood.getMi_id() +"', "+ styleGood.getSi_idx() +")";
    		System.out.println(sql);
    		result += jdbc.update(sql);
        	}
		// System.out.println(result);
		return result;	// 좋아요에 참여했으면  select만 실행하고  result를 리턴
	}

	public StyleInfo getStyleFormProc(StyleInfo styleInfo) {
	// 스타일 등록  처리 메소드
		String sql = "update t_product_info set pi_stylecnt = pi_stylecnt + 1 where pi_id = '"+ styleInfo.getPi_id() + "' ";
		
		int result = jdbc.update(sql);	// t_product_info테이블에 pi_style 값을 -1하는 쿼리 실행
		
		
		sql = "insert into t_style_info (mi_id, pi_id, si_content, si_img) "
				+ "values ('"+ styleInfo.getMi_id() +"','"+ styleInfo.getPi_id() +"','"+ styleInfo.getSi_content() +"','"+ styleInfo.getSi_img() +"')";
		
		result = jdbc.update(sql);
		
		sql = "select si_idx from t_style_info where mi_id = '"+ styleInfo.getMi_id() +"' and pi_id = '"+ styleInfo.getPi_id() +"'";
		StyleInfo si = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
			StyleInfo mi = new StyleInfo();
			mi.setSi_idx(rs.getInt("si_idx"));
			
            return mi;
		});
		return si;
	}

	public int getStyleUp(StyleInfo styleInfo) {
		String sql = "update t_style_info set si_img = '"+ styleInfo.getSi_img() +"', si_content = '"+ styleInfo.getSi_content() +
				"' where si_idx = "+ styleInfo.getSi_idx();
		int result = jdbc.update(sql);
		
		return result;
	}

	public List<StyleInfo> getTopStyle() {
		String sql ="select * from t_style_info order by si_good desc limit 0,6";
		//System.out.println(sql);
		List<StyleInfo> styleList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				StyleInfo si = new StyleInfo();
				si.setSi_idx(rs.getInt("si_idx"));
				si.setMi_id(rs.getString("mi_id"));
				si.setPi_id(rs.getString("pi_id"));
				si.setSi_img(rs.getString("si_img"));
				si.setSi_good(rs.getInt("si_good"));
				si.setSi_content(rs.getString("si_content"));
	            return si;
		});
		
		return styleList;
	}



}
