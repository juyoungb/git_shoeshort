package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class AdStoreDao {
	private JdbcTemplate jdbc;
	
	public AdStoreDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public int getStoreCount(String where) {
	// 스타일 정보 레코드 개수를 받아오는 메소드
		String sql = "select count(*)rcnt from t_brandstore_info "+ where;
		
		int result = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
	        return rs.getInt("rcnt");
		});
		return result;
	}
	
	public List<StoreInfo> getStoreList(String where, int psize, int cpage) {
		String sql = "select * from t_brandstore_info "+ where +" order by bsi_date desc limit " + ((cpage - 1) * psize) +", "+ psize;
		// System.out.println(sql);
		List<StoreInfo> storeList = jdbc.query(sql, (ResultSet rs, int rowNum)->{
			StoreInfo si = new StoreInfo();
			si.setAi_idx(rs.getInt("ai_idx"));
			si.setBsi_idx(rs.getInt("bsi_idx"));
			si.setPb_id(rs.getString("pb_id"));
			si.setBsi_name(rs.getString("bsi_name"));
			si.setBsi_brand(rs.getString("bsi_brand"));
			si.setBsi_addr(rs.getString("bsi_addr"));
			si.setBsi_isview(rs.getString("bsi_isview"));
			si.setBsi_date(rs.getString("bsi_date"));
			return si;
		});
		return storeList;
	}

	public StoreInfo storeUpForm(int bsiidx) {
		String sql = "select * from t_brandstore_info where bsi_idx = "+ bsiidx;
		StoreInfo storeInfo = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) ->{
			StoreInfo si = new StoreInfo();
			si.setAi_idx(rs.getInt("ai_idx"));
			si.setBsi_idx(rs.getInt("bsi_idx"));
			si.setPb_id(rs.getString("pb_id"));
			si.setBsi_name(rs.getString("bsi_name"));
			si.setBsi_brand(rs.getString("bsi_brand"));
			si.setBsi_addr(rs.getString("bsi_addr"));
			si.setBsi_isview(rs.getString("bsi_isview"));
			si.setBsi_date(rs.getString("bsi_date"));
			return si;
		});
		
		return storeInfo;
	}

	public int AdStoreUp(StoreInfo si) {
	// 매장 정보 수정처리 후 리턴하는 메소드
		String sql = "update t_brandstore_info set bsi_isview = '"+ si.getBsi_isview() +"', bsi_brand = '"+ si.getBsi_brand() +"'"
				+ ", bsi_name = '"+ si.getBsi_name() +"', bsi_addr = '"+ si.getBsi_addr() +"' where bsi_idx = "+ si.getBsi_idx();
		int result = jdbc.update(sql);
		
		return result;
	}

	public int AdStoreIn(StoreInfo si) {
	// 매장 정보 등록처리 후 리턴하는 메소드
		String sql = "insert into t_brandstore_info (ai_idx, pb_id, bsi_name, bsi_brand, bsi_addr) "
				+ " values ("+ si.getAi_idx() +", '"+ si.getPb_id() +"', '"+ si.getBsi_name() +"',"
							+ " '"+ si.getBsi_brand() +"', '"+ si.getBsi_addr() +"') ";
		int result = jdbc.update(sql);
		return result;
	}
}
