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
		
//	1. 오늘 주문 접수 
		String sql = "select count(*) from t_order_info where date(oi_date) = date(now()) and ( oi_status != 'd' or oi_status != 'e' )";
		indexData.setTdOrderCnt(jdbc.queryForObject(sql, Integer.class));
//	2. 오늘 가입수
		sql = "select count(*) from t_member_info where date(mi_date) = date(now())";
		indexData.setTodayMem(jdbc.queryForObject(sql, Integer.class));
//	3. 이번 달 매출
		sql = "select ifnull(sum(oi_pay), 0) from t_order_info where  month(oi_date) = month(now())";
		indexData.setSalesMonth(jdbc.queryForObject(sql, Integer.class));
//	4. 전체 회원 수 
		sql = "select count(*) from t_member_info";
		indexData.setAllMem(jdbc.queryForObject(sql, Integer.class));
//  5. 전체 회원 누적 포인트		
		sql = "select sum(mp_point) from t_member_point where mp_su ='s'";
		indexData.setAcmltPoint(jdbc.queryForObject(sql, Integer.class));
//	6. 남자 회원 수 
		sql = "select count(*) from t_member_info where mi_gender='남'";
		indexData.setMaleCnt(jdbc.queryForObject(sql, Integer.class));
//	7. 주문현황
		sql="select count(*) as ord, sum(case when oi_status = 'b' then 1 else 0 end) do, sum(case when oi_status = 'c' then 1 else 0 end) doing, sum(case when oi_status = 'd' then 1 else 0 end) done "
		+ " from t_order_info where oi_date >= date_add(now(), interval -100 day)";
		jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			indexData.setOrdCntTotal(rs.getInt("ord"));
			indexData.setOrdCnt1(rs.getInt("do"));
			indexData.setOrdCnt2(rs.getInt("doing"));
			indexData.setOrdCnt3(rs.getInt("done"));				
            return indexData;
		});
// 8. 상품 현황(판매중, 판매중지, 품절)
		sql = "select sum(ps_stock) ps, sum(case when ps_isview = 'y' then ps_stock else 0 end) pson, sum(case when ps_isview = 'n' then ps_stock else 0 end) as psoff, "
				+ " sum(case when ps_isview = 'y' and ps_stock = 0 then 1 else 0 end) pssold from t_product_stock";
		jdbc.queryForObject(sql,(ResultSet rs, int rowNum) -> {
			indexData.setPs(rs.getInt("ps"));
			indexData.setPsOn(rs.getInt("pson"));
			indexData.setPsOff(rs.getInt("psoff"));
			indexData.setPsSold(rs.getInt("pssold"));				
            return indexData;
		});	
//9. 이 주의 신발
		
//10. 이벤트 처리
		
//10. 그래프
		//최근 10일 매출액 
		
		sql="select date_range.date as date, ifnull(sum(oi_pay), 0) sale " + 
				" from(select curdate() - interval n day as date from (select 0 as n union all select 1 " +
				" union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 " +
				" union all select 7 union all select 8 union all select 9) as numbers) as date_range " + 
				" left join t_order_info on date(oi_date) = date_range.date where date_range.date >= curdate() - interval 10 day " + 
				" group by date_range.date order by date_range.date";
		List<ChartData> chartData= jdbc.query(sql, new RowMapper<ChartData>() {
			public ChartData mapRow(ResultSet rs, int rowNum) throws SQLException{
				ChartData cd =new ChartData();
				cd.setDate(rs.getString("date"));
				cd.setVal(rs.getString("sale"));
				//System.out.println(rs.getString("date"));
				//System.out.println(rs.getString("sale"));
				return cd;
			}
		});	
		System.out.println("2");
		indexData.setSalesData(chartData);
		//최근 10일 가입 회원 수
		sql = "select date_range.date as date, ifnull(count(mi_date), 0) as count " +
				" from (   select curdate() - interval n day as date from ( select 0 as n union all select 1 " +
				" union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 " +
				" union all select 7 union all select 8 union all select 9 ) as numbers) as date_range " +
				" left join t_member_info on date(mi_date) = date_range.date where date_range.date >= curdate() - interval 10 day " +
				" group by date_range.date order by date_range.date";
		chartData= jdbc.query(sql, new RowMapper<ChartData>() {
			public ChartData mapRow(ResultSet rs, int rowNum) throws SQLException{
				ChartData cd =new ChartData();
				cd.setDate(rs.getString("date"));
				cd.setVal(rs.getString("count"));
				return cd;
			}
		});
		indexData.setMemCntData(chartData);
		//최근 10일 주문 수
		sql="select date_range.date as date, ifnull(count(oi_date), 0) as count " + 
				" from (   select curdate() - interval n day as date from ( select 0 as n union all select 1 " +
				" union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 " +
				" union all select 7 union all select 8 union all select 9 ) as numbers) as date_range " + 
				" left join t_order_info on date(oi_date) = date_range.date where date_range.date >= curdate() - interval 10 day " + 
				" group by date_range.date order by date_range.date";
		chartData= jdbc.query(sql, new RowMapper<ChartData>() {
			public ChartData mapRow(ResultSet rs, int rowNum) throws SQLException{
				ChartData cd =new ChartData();
				cd.setDate(rs.getString("date"));
				cd.setVal(rs.getString("count"));
				return cd;
			}
		});
		indexData.setOrdCntData(chartData);
				
//11. 공지사항
		sql="select nl_idx ,nl_ctgr, nl_title from t_notice_list where nl_isview = 'y' order by nl_date desc limit 0,5";
		List<NoticeInfo> noticeInfo= jdbc.query(sql, new RowMapper<NoticeInfo>() {
			public NoticeInfo mapRow(ResultSet rs, int rowNum) throws SQLException{
				NoticeInfo ni =new NoticeInfo();
				ni.setNl_idx(rs.getString("nl_idx"));
				ni.setNl_ctgr(rs.getString("nl_ctgr"));
				ni.setNl_title(rs.getString("nl_title"));
				return ni;
			}
		});	
		indexData.setNoticeInfo(noticeInfo);
		return indexData;
	}
}
