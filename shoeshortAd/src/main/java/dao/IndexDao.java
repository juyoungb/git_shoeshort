package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class IndexDao {
	private JdbcTemplate jdbc;
	
	public IndexDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
	public AdminInfo getLoginInfo(String uid, String pwd) {
		String sql = "select * from t_admin_info where ai_id = ? and ai_pw = ? ";
		List<AdminInfo> results = jdbc.query(sql, new RowMapper<AdminInfo>(){
			public AdminInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdminInfo ai = new AdminInfo();
				ai.setAi_idx(rs.getInt("ai_idx"));		ai.setAi_id(rs.getString("ai_id"));
				ai.setAi_pw(rs.getString("ai_pw"));		ai.setAi_name(rs.getString("ai_name"));
	            return ai;
			}
		}, uid, pwd);
		return results.isEmpty() ? null : results.get(0);
	}
	/* 상단: 오늘 주문 접수, 오늘 가입 가입수, 이번달 매출, 총 회원, 누적 포인트 */
	// 중단:남녀 성비 55:50, 주문처리(주문접수, 상품준비, 배송중, 배송완료),상품현황(판매중,  판매중지, 품절)
	// 스타일월드컵(투표기간, 참여자, 모집기간, 지원자) 행운의비밀번호 찾기(마감일, 참여자, 당첨자) 추천 신발
	public IndexData getTotalData() {
		/*상단: 오늘 주문 접수, 오늘 가입 가입수, 이번달 매출, 총 회원, 누적 포인트 */
		IndexData indexData = new IndexData();
//		1. 오늘 주문 접수	2. 오늘 가입수		3. 이번 달 매출		4. 전체 회원 수		5. 전체 회원 누적 포인트	6. 남자 회원 수 
		String sql="select" + 
				"(select count(*) from t_order_info where date(oi_date) = date(now()) and ( oi_status != 'd' or oi_status != 'e' )) tdOrderCnt," + 
				"(select count(*) from t_member_info where date(mi_date) = date(now())) todayMem," + 
				"(select ifnull(sum(oi_pay), 0) from t_order_info where  month(oi_date) = month(now())) salesMonth," + 
				"(select count(*) from t_member_info) allMem," + 
				"(select sum(mp_point) from t_member_point where mp_su ='s') acmltPoint," + 
				"(select count(*) from t_member_info where mi_gender='남') maleCnt";
		jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			indexData.setTdOrderCnt(rs.getInt("tdOrderCnt"));			indexData.setTodayMem(rs.getInt("todayMem"));
			indexData.setSalesMonth(rs.getInt("salesMonth"));			indexData.setAllMem(rs.getInt("allMem"));
			indexData.setAcmltPoint(rs.getInt("acmltPoint"));			indexData.setMaleCnt(rs.getInt("maleCnt"));				
            return indexData;
		});
		
//	7. 주문현황
		sql="select count(*) as ord, sum(case when oi_status = 'b' then 1 else 0 end) do, sum(case when oi_status = 'c' then 1 else 0 end) doing, sum(case when oi_status = 'd' then 1 else 0 end) done "
		+ " from t_order_info where oi_date >= date_add(now(), interval -30 day)";
		jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			indexData.setOrdCntTotal(rs.getInt("ord"));		indexData.setOrdCnt1(rs.getInt("do"));
			indexData.setOrdCnt2(rs.getInt("doing"));		indexData.setOrdCnt3(rs.getInt("done"));				
            return indexData;
		});
// 8. 상품 현황(판매중, 판매중지, 품절)
		sql = "select (select count(*) from t_product_info) pcnt , (select count(*) from t_product_info where pi_isview='y') sale, (select count(*) from t_product_info where pi_isview='n') unsale, "+
			  "(select sum(ps_stock) from t_product_stock where ps_isview='y') ps";
		jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			indexData.setPcnt(rs.getInt("pcnt"));			indexData.setSale(rs.getInt("sale"));
			indexData.setUnsale(rs.getInt("unsale"));		indexData.setPs(rs.getInt("ps"));				
            return indexData;
		});	
//9. 이 판매량 1위 신발
		sql = "select a.pi_img1, a.pi_name, sum(c.ps_idx) ps_idx from t_product_info a, t_order_info b, t_order_detail c "+ 
			  " where a.pi_id =c.pi_id and b.oi_id = c.oi_id and b.oi_date >= date_sub(now(), interval 30 day) group by c.pi_id order by ps_idx desc limit 0,1 ";
		System.out.print(sql);
		jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			indexData.setPi_img1(rs.getString("pi_img1"));			indexData.setPi_name(rs.getString("pi_name"));			
            return indexData;
		});	
//10. 이벤트 처리
		sql = "select" + 
				"(select datediff(date(ew_vedate), now()) from t_evt_wcup where ew_isview ='y' and ew_status ='b') ve," + 
				"(select count(*) from t_evt_wcup_join a, t_evt_wcup b where a.ew_idx =b.ew_idx and b.ew_status = 'b') prt," + 
				"(select datediff(date(ew_cedate), now()) from t_evt_wcup where ew_isview ='y' and ew_status ='a') ce," + 
				"(select count(*)from t_evt_wcup_list a, t_evt_wcup b where a.ew_idx = b.ew_idx and b.ew_isview ='y' and b.ew_status ='a') vol";
		jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			indexData.setVe(rs.getInt("ve"));		indexData.setPrt(rs.getInt("prt"));
			indexData.setCe(rs.getInt("ce"));		indexData.setVol(rs.getInt("vol"));	
            return indexData;
		});
		
		sql ="select (select count(*) from t_evt_lucky_detail a, t_evt_lucky_detail b where a.el_idx =b.el_idx) mem, "+
			"(select datediff(date(el_edate), now()) from t_evt_lucky_list where el_isview='y' order by el_edate desc limit 0,1) diff";
		jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			indexData.setLuckyMem(rs.getInt("mem"));		indexData.setLuckyDiff(rs.getInt("diff"));
            return indexData;
		});
//10. 그래프
		//최근 10일 매출액 
		
		sql="select date_range.date as date, ifnull(sum(oi_pay), 0) sale " + 
				" from(select curdate() - interval n day as date from (select 0 as n union all select 1 " +
				" union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 " +
				" union all select 7 union all select 8 union all select 9) as numbers) as date_range " + 
				" left join t_order_info on date(oi_date) = date_range.date where date_range.date >= curdate() - interval 10 day " + 
				" group by date_range.date order by date_range.date";
		List<ChartData> salesChart= jdbc.query(sql, new RowMapper<ChartData>() {
			public ChartData mapRow(ResultSet rs, int rowNum) throws SQLException{
				ChartData cd =new ChartData();
				cd.setDate(rs.getString("date"));				cd.setVal(rs.getString("sale"));
				return cd;
			}
		});	
		indexData.setSalesChart(salesChart);
		//최근 10일 가입 회원 수
		sql = "select date_range.date as date, ifnull(count(mi_date), 0) as count " +
				" from (   select curdate() - interval n day as date from ( select 0 as n union all select 1 " +
				" union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 " +
				" union all select 7 union all select 8 union all select 9 ) as numbers) as date_range " +
				" left join t_member_info on date(mi_date) = date_range.date where date_range.date >= curdate() - interval 10 day " +
				" group by date_range.date order by date_range.date";
		List<ChartData> memChart= jdbc.query(sql, new RowMapper<ChartData>() {
			public ChartData mapRow(ResultSet rs, int rowNum) throws SQLException{
				ChartData cd =new ChartData();
				cd.setDate(rs.getString("date"));				cd.setVal(rs.getString("count"));
				return cd;
			}
		});
		indexData.setMemChart(memChart);
		//최근 10일 주문 수
		sql="select date_range.date as date, ifnull(count(oi_date), 0) as count " + 
				" from (   select curdate() - interval n day as date from ( select 0 as n union all select 1 " +
				" union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 " +
				" union all select 7 union all select 8 union all select 9 ) as numbers) as date_range " + 
				" left join t_order_info on date(oi_date) = date_range.date where date_range.date >= curdate() - interval 10 day " + 
				" group by date_range.date order by date_range.date";
		List<ChartData> ordChart= jdbc.query(sql, new RowMapper<ChartData>() {
			public ChartData mapRow(ResultSet rs, int rowNum) throws SQLException{
				ChartData cd =new ChartData();
				cd.setDate(rs.getString("date"));				cd.setVal(rs.getString("count"));
				return cd;
			}
		});
		indexData.setOrdChart(ordChart);
				
//11. 공지사항
		sql="select nl_idx ,nl_ctgr, nl_title from t_notice_list where nl_isview = 'y' order by nl_date desc limit 0,5";
		List<NoticeInfo> noticeInfo= jdbc.query(sql, new RowMapper<NoticeInfo>() {
			public NoticeInfo mapRow(ResultSet rs, int rowNum) throws SQLException{
				String ctgr = "";
				NoticeInfo ni =new NoticeInfo();
				ni.setNl_idx(rs.getString("nl_idx"));
				
				if (rs.getString("nl_ctgr").equals("a")) ctgr = "공지사항";
				if (rs.getString("nl_ctgr").equals("b")) ctgr = "이벤트 안내";
				if (rs.getString("nl_ctgr").equals("c")) ctgr = "이벤트 발표";
				
				ni.setNl_ctgr(ctgr);
				ni.setNl_title(rs.getString("nl_title"));
				return ni;
			}
		});	
		indexData.setNoticeInfo(noticeInfo);
		return indexData;
	}
}
