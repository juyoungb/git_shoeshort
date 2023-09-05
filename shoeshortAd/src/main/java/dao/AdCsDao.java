package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class AdCsDao {
	private JdbcTemplate jdbc;
	
	public AdCsDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public int getNoticeListCount(String where) {
		// 공지사항 레코드 개수를 리턴하는 메소드
		String sql = "select count(*) from t_notice_list "+ where;
		
		int rcnt = jdbc.queryForObject(sql, Integer.class);
		
		return rcnt;
	}
	public List<NoticeList> getNoticeList(String where, int cpage, int psize) {
		// 공지사항 글들의 정보를 리턴하는 메소드
		String sql = "select nl_idx, ai_idx, nl_read, nl_ctgr, nl_title, nl_content, nl_isview, if(curdate() = date(nl_date), right(nl_date,8), replace(mid(nl_date, 3, 8), '-', '.')) wdate "
				+ "from t_notice_list " + where +" order by nl_date desc limit "+ ((cpage - 1) * psize) + ", "+ psize;
		
		//  System.out.println(sql);
		
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
		
		return noticeInfo;
	}
	
	public int getNoticeProcIn(NoticeList noticeInfo) {
		// 공지사항 등록 처리 후 리턴하는 메소드
		String sql = "insert into t_notice_list (ai_idx, nl_ctgr, nl_title, nl_content) "
				+ " values ("+ noticeInfo.getAi_idx() +", '"+ noticeInfo.getNl_ctgr() +"' ,"
						+ " '"+ noticeInfo.getNl_title() +"', '"+ noticeInfo.getNl_content() +"')";
		int result = jdbc.update(sql);
		
		return result;
	}
	
	public NoticeList getNoticeProc(int nlidx) {
		// 수정할 공지사항 정보를 리턴하는 메소드
		String sql = "select * from t_notice_list where nl_idx = "+ nlidx;
		NoticeList noticeInfo = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
			NoticeList nl = new NoticeList();
			nl.setAi_idx(rs.getInt("ai_idx"));
			nl.setNl_idx(nlidx);
			nl.setNl_read(rs.getInt("nl_read"));
			nl.setNl_title(rs.getString("nl_title"));
			nl.setNl_ctgr(rs.getString("nl_ctgr"));
			nl.setNl_content(rs.getString("nl_content"));
			nl.setNl_date(rs.getString("nl_date"));
			return nl;
		});
		
		return noticeInfo;
	}

	public int getNoticeProcUp(NoticeList ni) {
		// 공지글 수정 처리 메소드
		String sql = "update t_notice_list set nl_title = '"+ ni.getNl_title() +"' , nl_content = '"+ ni.getNl_content() +"' ,"
				+ " nl_ctgr = '" + ni.getNl_ctgr() +"' where nl_idx = "+ ni.getNl_idx();
		
		int result = jdbc.update(sql);
				
		return result;
	}

	public int getNoticeProcDel(int nlidx) {
		// 공지글 삭제 처리 메소드
		String sql = "update t_notice_list set nl_isview = 'n' where nl_idx = "+ nlidx;
		
		int result = jdbc.update(sql);
				
		return result;
	}

	public int getFaqListCount(String where) {
		// faq 레코드 개수를 리턴하는 메소드
		String sql = "select count(*) from t_faq_list "+ where;
		
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

	public int getFaqProcIn(FaqList faqInfo) {
	// faq 등록 처리 후 리턴하는 메소드
		String sql = "insert into t_faq_list (ai_idx, fl_title, fl_content, fl_answer) "
				+ " values ("+ faqInfo.getAi_idx() +" , '"+ faqInfo.getFl_title() +"',"
						+ " '"+ faqInfo.getFl_content() +"', '"+ faqInfo.getFl_answer() +"')";
		int result = jdbc.update(sql);
		
		return result;
	}

	public FaqList getFaqInfo(int flidx) {
		// 하나의 faq정보를 리턴하는 메소드
 		String sql = "select * from t_faq_list where fl_idx = "+ flidx;
		
		FaqList faqInfo = jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
			FaqList fl = new FaqList();
			fl.setFl_idx(flidx);
			fl.setAi_idx(rs.getInt("ai_idx"));
			fl.setFl_read(rs.getInt("fl_read"));
			fl.setFl_title(rs.getString("fl_title"));
			fl.setFl_answer(rs.getString("fl_answer"));
			fl.setFl_content(rs.getString("fl_content"));
			fl.setFl_date(rs.getString("fl_date"));
			return fl;
		});
		return faqInfo;
	}

	public int getAdFaqProcUp(FaqList faqInfo) {
		String sql = "update t_faq_list set fl_title = '"+ faqInfo.getFl_title() +"', fl_content = '"+ faqInfo.getFl_content() +"' "
				+ ", fl_answer = '"+ faqInfo.getFl_answer() +"' where fl_idx = "+ faqInfo.getFl_idx();
		System.out.println(sql);
		int result = jdbc.update(sql);
		
		return result;
	}

	public int getAdFaqProcDel(int flidx) {
		String sql = "update t_faq_list set fl_isview = 'n' where fl_idx = "+ flidx;
		int result = jdbc.update(sql);
		
		return result;
	}
}
