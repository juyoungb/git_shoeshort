package dao;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import vo.*;

public class CsDao {
	private JdbcTemplate jdbc;
	
	public CsDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public int getNoticeListCnt() {
		String sql = "select count(*) from t_notice_list ";
		
		int rcnt = jdbc.queryForObject(sql, Integer.class);
		
		return rcnt;
	}

	public List<NoticeList> getNoticeList(String where, int cpage, int psize) {
		// 공지사항 글들의 정보를 리턴하는 메소드
		String sql = "select nl_idx, ai_idx, nl_read, nl_ctgr, nl_title, nl_content, nl_isview, if(curdate() = date(nl_date), right(nl_date,8), replace(mid(nl_date, 3, 8), '-', '.')) wdate "
				+ "from t_notice_list " + where +" order by nl_date desc limit "+ ((cpage - 1) * psize) + ", "+ psize;
		
			List<NoticeList> noticeList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				NoticeList nl = new NoticeList();
				nl.setNl_idx(rs.getInt("nl_idx"));
				nl.setAi_idx(rs.getInt("ai_idx"));
				nl.setNl_read(rs.getInt("nl_read"));
				nl.setNl_ctgr(rs.getString("nl_ctgr"));
				nl.setNl_title(rs.getString("nl_title"));
				nl.setNl_content(rs.getString("nl_content"));
				nl.setNl_date(rs.getString("wdate"));
				nl.setNl_isview(rs.getString("nl_isview"));
				
				return nl;
		});
		return noticeList;
	}

	public NoticeList getNoticeInfo(int nlidx) {
	// 하나의 공지사항 정보들을 리턴하는 메소드	
		String sql = "select * from t_notice_list where nl_idx = "+ nlidx;
		NoticeList noticeInfo = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
			NoticeList nl = new NoticeList();
			nl.setNl_idx(nlidx);
			nl.setNl_read(rs.getInt("nl_read"));
			nl.setNl_title(rs.getString("nl_title"));
			nl.setNl_ctgr(rs.getString("nl_ctgr"));
			nl.setNl_content(rs.getString("nl_content"));
			nl.setNl_date(rs.getString("nl_date"));
			return nl;
		});
		sql = "update t_notice_list set nl_read = nl_read + 1 where nl_idx = "+ nlidx;
		int result = jdbc.update(sql);
		
		return noticeInfo;
	}

	public int getFaqListCnt() {
	// faq 레코드 개수를 리턴하는 메소드
		String sql = "select count(*) from t_faq_list ";
		
		int rcnt = jdbc.queryForObject(sql, Integer.class);
		
		return rcnt;
	}

	public List<FaqList> getFaqList(String where, int cpage, int psize) {
		// faq들의 정보를 리턴하는 메소드
		String sql = "select fl_idx, ai_idx, fl_read, fl_title, fl_content, fl_answer,fl_isview, if(curdate() = date(fl_date), right(fl_date,8), replace(mid(fl_date, 3, 8), '-', '.')) wdate "
				+ "from t_faq_list " + where +" order by fl_date desc limit "+ ((cpage - 1) * psize) + ", "+ psize;
		
			List<FaqList> faqList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				FaqList fl = new FaqList();
				fl.setFl_idx(rs.getInt("fl_idx"));
				fl.setAi_idx(rs.getInt("ai_idx"));
				fl.setFl_read(rs.getInt("fl_read"));
				fl.setFl_title(rs.getString("fl_title"));
				fl.setFl_content(rs.getString("fl_content"));
				fl.setFl_date(rs.getString("wdate"));
				fl.setFl_isview(rs.getString("fl_isview"));
				
				return fl;
		});
		return faqList;
	}

	public FaqList getFaqInfo(int flidx) {
	// 하나의 faq 정보들을 리턴하는 메소드	
		String sql = "select * from t_faq_list where fl_idx = "+ flidx;
		FaqList faqInfo = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
			FaqList fl = new FaqList();
			fl.setFl_idx(flidx);
			fl.setFl_read(rs.getInt("fl_read"));
			fl.setFl_title(rs.getString("fl_title"));
			fl.setFl_answer(rs.getString("fl_answer"));
			fl.setFl_content(rs.getString("fl_content"));
			fl.setFl_date(rs.getString("fl_date"));
			return fl;
		});
		// 조회수 증가 쿼리 실행
		sql = "update t_faq_list set fl_read = fl_read + 1 where fl_idx = "+ flidx;
		int result = jdbc.update(sql);
			
		return faqInfo;
	}
}
