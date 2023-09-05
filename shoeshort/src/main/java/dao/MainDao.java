package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class MainDao {
private JdbcTemplate jdbc;
	
	public MainDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public MainMedia getVideoInfo() {
		String sql = "select*from t_main_video where mv_isview = 'y'";

		List<MainMedia> results = jdbc.query(sql, new RowMapper<MainMedia>(){
			public MainMedia mapRow(ResultSet rs, int rowNum) throws SQLException {
				MainMedia mv = new MainMedia();
				mv.setAi_idx(rs.getInt("ai_idx"));
				mv.setMm_idx(rs.getInt("mv_idx"));
				mv.setMm_name(rs.getString("mv_name"));
				mv.setMm_media(rs.getString("mv_video"));
				mv.setMm_date(rs.getString("mv_date"));
				mv.setMm_isview(rs.getString("mv_isview"));
	            return mv;
			}
		});

		
		return results.isEmpty() ? null : results.get(0);
	}

	public List<MainMedia> getImgList() {
		String sql = "select*from t_main_img where mg_isview = 'y'";
		List<MainMedia> imgList = jdbc.query(sql, new RowMapper<MainMedia>() {
			public MainMedia mapRow(ResultSet rs, int rowNum) throws SQLException{
				MainMedia mg =new MainMedia();
				mg.setAi_idx(rs.getInt("ai_idx"));
				mg.setMm_idx(rs.getInt("mg_idx"));
				mg.setMm_seq(rs.getInt("mg_seq"));
				mg.setMm_name(rs.getString("mg_name"));
				mg.setMm_link(rs.getString("mg_link"));
				mg.setMm_media(rs.getString("mg_img"));
				mg.setMm_date(rs.getString("mg_date"));
				mg.setMm_isview(rs.getString("mg_isview"));
				return mg;
			}
		});
		return imgList;
	}
	
}
