package dao;

import java.util.*;
import java.sql.*;
import java.time.LocalDate;

import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class OrderDao {
private JdbcTemplate jdbc;
	
	public OrderDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public List<OrderInfo> getOrderList(String mi_id) {
		String sql="select*from t_order_info where mi_id='"+mi_id+"'  order by oi_date desc  ";
		System.out.println("getOrderList: "+sql);
		List<OrderInfo> orderList = jdbc.query(sql, new RowMapper<OrderInfo>() {
			public OrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException{
				OrderInfo oi =new OrderInfo();
				oi.setOi_id(rs.getString("oi_id"));				oi.setMi_id(mi_id);
				oi.setOi_name(rs.getString("oi_name"));			oi.setOi_phone(rs.getString("oi_phone"));
				oi.setOi_zip(rs.getString("oi_zip"));			oi.setOi_addr1(rs.getString("oi_addr1"));
				oi.setOi_addr2(rs.getString("oi_addr2"));		oi.setOi_memo(rs.getString("oi_memo"));
				oi.setOi_payment(rs.getString("oi_payment"));	oi.setOi_invoice(rs.getString("oi_invoice"));
				oi.setOi_status(rs.getString("oi_status"));		oi.setOi_date(rs.getString("oi_date"));
				oi.setOi_pay(rs.getInt("oi_pay"));				oi.setOi_upoint(rs.getInt("oi_upoint"));
				oi.setOi_spoint(rs.getInt("oi_spoint"));
				oi.setDetailList(setDeList(rs.getString("oi_id")));
				return oi;				
			}
		});
		return orderList;
	}
	public List<OrderInfo> getOrderDetail(String oiid) {
		String sql="select*from t_order_info where oi_id='"+oiid+"' ";
		List<OrderInfo> orderList = jdbc.query(sql, new RowMapper<OrderInfo>() {
			public OrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException{
				OrderInfo oi =new OrderInfo();
				oi.setOi_id(oiid);									oi.setMi_id(rs.getString("mi_id"));
				oi.setOi_name(rs.getString("oi_name"));				oi.setOi_phone(rs.getString("oi_phone"));
				oi.setOi_zip(rs.getString("oi_zip"));				oi.setOi_addr1(rs.getString("oi_addr1"));
				oi.setOi_addr2(rs.getString("oi_addr2"));			oi.setOi_memo(rs.getString("oi_memo"));
				oi.setOi_payment(rs.getString("oi_payment"));		oi.setOi_invoice(rs.getString("oi_invoice"));
				oi.setOi_status(rs.getString("oi_status"));			oi.setOi_date(rs.getString("oi_date"));
				oi.setOi_pay(rs.getInt("oi_pay"));					oi.setOi_upoint(rs.getInt("oi_upoint"));
				oi.setOi_spoint(rs.getInt("oi_spoint"));			oi.setDetailList(setDeList(oiid));
				return oi;				
			}
		});
		return orderList;
	}
	public List<OrderDetail> setDeList(String oiid){
		String sql="select*from t_order_detail where oi_id='"+oiid+"' ";
		//System.out.println("sql2:"+sql);
		List<OrderDetail> orderDetail = jdbc.query(sql, new RowMapper<OrderDetail>() {
			public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException{
				OrderDetail od =new OrderDetail();
				od.setOi_id(rs.getString("oi_id"));				od.setPi_id(rs.getString("pi_id"));
				od.setOd_name(rs.getString("od_name"));				od.setOd_img(rs.getString("od_img"));
				od.setOd_idx(rs.getInt("od_idx"));				od.setPs_idx(rs.getInt("ps_idx"));
				od.setOd_cnt(rs.getInt("od_cnt"));				od.setOd_price(rs.getInt("od_price"));
				od.setOd_size(rs.getInt("od_size"));				
				return od;				
			}
		});
		return orderDetail;
	}

	public List<OrderCart> getCartList(String miid) {
		//System.out.println(getOrderId());
		String sql="select a.*, b.pi_img1, b.pi_name, if(b.pi_dc >0, round(b.pi_price*(1-b.pi_dc)), b.pi_price) price "+
				" from t_order_cart a, t_product_info b where a.pi_id =b.pi_id and b.pi_isview = 'y' and a.mi_id ='"+miid+"' order by a.pi_id, a.ps_idx";
		List<OrderCart> cartList = jdbc.query(sql, new RowMapper<OrderCart>() {
			public OrderCart mapRow(ResultSet rs, int rowNum) throws SQLException{
				OrderCart oc =new OrderCart();
				oc.setOc_idx(rs.getInt("oc_idx"));		            oc.setMi_id(rs.getString("mi_id"));
				oc.setPi_id(rs.getString("pi_id"));		            oc.setPs_idx(rs.getString("ps_idx"));
				oc.setOc_cnt(rs.getInt("oc_cnt"));		            oc.setOc_date(rs.getString("oc_date"));
				oc.setPi_name(rs.getString("pi_name"));		        oc.setPi_img1(rs.getString("pi_img1"));
				oc.setPi_price(rs.getInt("price"));					oc.setStockList(getStockList(oc.getPi_id()));
				return oc;				
			}
		});
		return cartList;
	}
	public List<ProductStock> getStockList(String piid){
		String sql = "select ps_idx, ps_size, ps_stock from t_product_stock where ps_isview='y' and pi_id='"+piid+"' order by ps_size";
		List<ProductStock> productList = jdbc.query(sql, new RowMapper<ProductStock>() {
			public ProductStock mapRow(ResultSet rs, int rowNum) throws SQLException{
			ProductStock ps = new ProductStock();
			ps.setPs_idx(rs.getInt("ps_idx"));				ps.setPi_id(piid);
			ps.setPs_size(rs.getString("ps_size"));			ps.setPs_stock(rs.getInt("ps_stock"));	
			return ps;				
			}
		
		});
		return productList;
	}
	public int cartUpCnt(OrderCart oc) {
        int result = 0;
        
        try {
            String sql = "";
            if (oc.getOc_cnt() == 0) { 
                sql = "select oc_idx, oc_cnt from t_order_cart where mi_id = ? AND ps_idx = ?";
                List<Map<String, Object>> rows = jdbc.queryForList(sql, oc.getMi_id(),oc.getPs_idx());

                if (!rows.isEmpty()) {
                    Map<String, Object> row = rows.get(0);
                    int idx = (int) row.get("oc_idx");
                    int count = (int) row.get("oc_cnt");                 
                    sql = "update t_order_cart set ps_idx = ?, oc_cnt = oc_cnt + ? where mi_id = ? and oc_idx = ?";
                    result = jdbc.update(sql,oc.getPs_idx(),count,oc.getMi_id(),oc.getOc_idx());
                    sql = "delete from t_order_cart where oc_idx = "+idx;
                    jdbc.update(sql);
                } else {
                    sql = "update t_order_cart set ps_idx = ? where mi_id = ? and oc_idx = ?";
                    result = jdbc.update(sql,oc.getPs_idx(), oc.getMi_id(),oc.getOc_idx());
                }
            } else { 
                sql = "update t_order_cart set oc_cnt = ? where mi_id = ? and oc_idx = ?";
                result = jdbc.update(sql, oc.getOc_cnt(),oc.getMi_id(),oc.getOc_idx());
            }
        } catch (Exception e) {
            //System.out.println("OrdercDao �겢�옒�뒪�쓽 cartUpCnt() 硫붿냼�뱶 �삤瑜�");
            e.printStackTrace();
        }
        return result;
    }

	public int cartDel(String where) {
		String sql = "delete from t_order_cart "+where;
		//System.out.println(sql);
		int result = jdbc.update(sql);
		return result;
	}

	public int cartInsert(OrderCart oc) {
		String sql = "select IFNULL(Max(oc_idx), 0) oc_idx from t_order_cart a, t_product_stock b where a.ps_idx = b.ps_idx and a.mi_id =  '"+ oc.getMi_id() +"' "
				+ "and a.pi_id = '"+ oc.getPi_id() +"' and b.ps_size = "+ oc.getPs_size();	
		System.out.println("sql"+sql);
		int oc_idx = jdbc.queryForObject(sql, Integer.class);
		if(oc_idx > 0) {
			sql = "update t_order_cart set oc_cnt = oc_cnt + "+ oc.getOc_cnt() +" where oc_idx = "+ oc_idx ;
		} else {
			sql = "select ps_idx from t_product_stock where pi_id = '" + oc.getPi_id() + "' and ps_size = " + oc.getPs_size();
			int ps_idx = jdbc.queryForObject(sql, Integer.class);
			sql = "insert into t_order_cart (mi_id, pi_id, ps_idx, oc_cnt) "
					+ "values ('"+ oc.getMi_id() +"', '"+ oc.getPi_id() +"', "+ ps_idx +", "+ oc.getOc_cnt() +") " ;

		}
		int result = jdbc.update(sql);		
		
		return result;
	}

	public List<OrderCart> getBuyList(String kind, String sql) {
		List<OrderCart> pdtList = jdbc.query(sql, new RowMapper<OrderCart>() {
			public OrderCart mapRow(ResultSet rs, int rowNum) throws SQLException{
			OrderCart oc =new OrderCart();
			if(kind.equals("c")) 		oc.setOc_idx(rs.getInt("oc_idx"));
			else if(kind.equals("d")) 	oc.setOc_idx(0);
			oc.setPi_id(rs.getString("pi_id"));				oc.setPi_img1(rs.getString("pi_img1"));
			oc.setPi_name(rs.getString("pi_name"));			oc.setPi_price(rs.getInt("price"));
			oc.setOc_cnt(rs.getInt("cnt"));					oc.setPs_size(rs.getInt("ps_size"));
			return oc;
			}
	 	});	
		return pdtList;
	}

	public List<MemberAddr> getAddrList(String miid) {
		String sql="select*from t_member_addr where mi_id=? order by ma_basic desc";
		List<MemberAddr> pdtList = jdbc.query(sql, new RowMapper<MemberAddr>() {
			public MemberAddr mapRow(ResultSet rs, int rowNum) throws SQLException{				
			MemberAddr ma =new MemberAddr();
			ma.setMa_idx(rs.getInt("ma_idx"));				ma.setMa_name(rs.getString("ma_name"));
			ma.setMa_rname(rs.getString("ma_rname"));		ma.setMa_phone(rs.getString("ma_phone"));
			ma.setMa_phone(rs.getString("ma_phone"));		ma.setMa_zip(rs.getString("ma_zip"));
			ma.setMa_addr1(rs.getString("ma_addr1"));		ma.setMa_addr2(rs.getString("ma_addr2"));
			ma.setMa_basic(rs.getString("ma_basic"));
			return ma;
			}
	 	},miid);		
		return pdtList;
	}

	public String orderIn(String kind, OrderInfo oi, String ocidxs) {
        String oi_id = getOrderId();
        String result = oi_id + ",";
        int rcount = 0, target = 0;
    	String sql = "insert into t_order_info (oi_id, mi_id, oi_name, oi_phone, oi_zip, oi_addr1, oi_addr2, oi_memo, oi_payment, oi_pay, oi_status, oi_date) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";
    	System.out.println("sql:"+sql);
    	//주문번호 생성 후 syntax 에러
    	target++;
        rcount = jdbc.update(sql, oi_id, oi.getMi_id(), oi.getOi_name(), oi.getOi_phone(),
        oi.getOi_zip(), oi.getOi_addr1(), oi.getOi_addr2(), oi.getOi_memo(),oi.getOi_payment(), oi.getOi_pay(), oi.getOi_status());
        System.out.println(target+" : "+sql+" : "+ rcount);
        if (kind.equals("c")) {
        	sql = "select a.pi_id, a.ps_idx, a.oc_cnt, b.pi_name, b.pi_img1, c.ps_size, "+
    				"if(b.pi_dc >0, round(b.pi_price*(1-b.pi_dc)), b.pi_price) price " + 
    				"from t_order_cart a, t_product_info b, t_product_stock c " + 
    				"where a.pi_id = b.pi_id and a.ps_idx = c.ps_idx and a.mi_id = '"+oi.getMi_id()+"' and (";
            String delWhere =" where mi_id='"+oi.getMi_id()+"'and (";
            
            System.out.println("ocidxs : "+ocidxs);
			String[] arr = ocidxs.substring(1).split(",");
            
			//a.oc_idx =  or a.oc_idx = 57 or a.oc_idx = 54 or a.oc_idx = 58 or a.oc_idx = 55 or a.oc_idx = 56)
			for (int i=0; i< arr.length ; i++) {
				if(i == 0) {
					sql+="a.oc_idx = "+arr[i];
					delWhere +="oc_idx = "+arr[i];
				}else {
					sql+=" or a.oc_idx = "+arr[i];
					delWhere+=" or oc_idx = "+arr[i];
				}
			}
			sql+=")";
			delWhere+=")";
			int i=0;
			System.out.println(target+" : "+sql+" : "+ rcount);
			List<Map<String, Object>> rows = jdbc.queryForList(sql);	
			if(!rows.isEmpty()){ 
				do {
					Map<String, Object> rs = rows.get(i);
					sql = "insert into t_order_detail values(null,'"+oi_id+"','"+rs.get("pi_id")+"','"+
							rs.get("ps_idx")+"','"+rs.get("oc_cnt")+"',"+rs.get("price")+",'"+rs.get("pi_name")+
							"','"+rs.get("pi_img1")+"',"+rs.get("ps_size")+")";		
					target++;	rcount += jdbc.update(sql);	
					System.out.println(target+" : "+sql+" : "+ rcount);
					sql="update t_product_info set pi_sale = pi_sale + "+rs.get("oc_cnt")+" where pi_id ='"+rs.get("pi_id")+"' ";
					target++;		rcount += jdbc.update(sql);	;
					System.out.println(target+" : "+sql+" : "+ rcount);
					sql="update t_product_stock set ps_stock = ps_stock - "+rs.get("oc_cnt")+
						", ps_sale = ps_sale +"+rs.get("oc_cnt")+" where ps_idx ="+rs.get("ps_idx");
					target++;		rcount += jdbc.update(sql);
					System.out.println(target+" : "+sql+" : "+ rcount);
					i++;
				} while(i<rows.size());
				System.out.println("target: "+target+" rcount: "+ rcount);
				String[] tmp = usePoint(oi,target, rcount, oi_id).split(",");
				
				System.out.println("tmp : "+tmp[0]+" : "+ tmp[1]);
				target=Integer.parseInt(tmp[0]);
				rcount=Integer.parseInt(tmp[1]);
				sql = "delete from t_order_cart"+delWhere;
				jdbc.update(sql);	
				System.out.println(target+" : "+sql+" : "+ rcount);
			}else {
				return  result+ "1,4";
			}
        } else {// 바로 구매
/*
1. [즉시 구매] -> orderForm
2. orderForm -> Post -> orderIn -> t_order_info 생성 -> t_order_detail 생성 
*/
        	sql ="select a.pi_id,a.pi_name, if(a.pi_dc >0, round(a.pi_price*(1-a.pi_dc)), a.pi_price) price, a.pi_img1, b.ps_idx, b.ps_size "
        			+ "from t_product_info a, t_product_stock b where a.pi_id = b.pi_id and a.pi_isview ='y' and b.ps_size = ? and a.pi_id= ?";
        	List<Map<String, Object>> rows = jdbc.queryForList(sql,oi.getSize(),oi.getPiid());
        	if(!rows.isEmpty()){ // cnt, size, piid
        		Map<String, Object> rs = rows.get(0);
	        	sql = "insert into t_order_detail values(null, ?, ?, ?, ?, ?, ?, ?, ?)";	
				target++;	rcount += jdbc.update(sql,oi_id, rs.get("pi_id"),rs.get("ps_idx"),oi.getCnt() 
						,rs.get("price"), rs.get("pi_name") ,rs.get("pi_img1"),oi.getSize());
				System.out.println(target+" : "+sql+" : "+ rcount);
				sql="update t_product_info set pi_sale = pi_sale + ?  where pi_id =? ";
				target++;		rcount += jdbc.update(sql,oi.getCnt(),rs.get("pi_id"));	;

				System.out.println(target+" : "+sql+" : "+ rcount);
				sql="update t_product_stock set ps_stock = ps_stock - ?, ps_sale = ps_sale + ? where ps_idx =?";
				target++;		rcount += jdbc.update(sql,oi.getCnt(), oi.getCnt(), rs.get("ps_idx"));				
				
				System.out.println(target+" : "+sql+" : "+ rcount);
				String[] tmp = usePoint(oi,target, rcount, oi_id).split(",");
				target+=Integer.parseInt(tmp[0]);
				rcount+=Integer.parseInt(tmp[1]);	
				System.out.println("5차 t_point 완");
        	}
        	else {
        		return  result+ "1,4";
        	}
		}
        return result + rcount + "," + target;
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
	public String usePoint(OrderInfo oi, int target, int rcount, String oi_id) {
		System.out.println("busePoint target:"+target);
		System.out.println("busePoint rcount:"+rcount);
		String sql="";
		if(oi.getOi_spoint() > 0) {
			sql = "update t_member_info set mi_point = mi_point + "+oi.getOi_spoint()+
					" where mi_id ='"+oi.getMi_id()+"' ";
			target++;		rcount += jdbc.update(sql);
			
			sql = "insert into t_member_point (ai_idx, mi_id, mp_point, mp_desc, mp_detail,mp_edate) values(1,'"+oi.getMi_id()+
					"','"+oi.getOi_spoint()+"','상품구매','"+oi_id+"', date_add(now(), interval 1 year))";//date_add(now(), interval 1 year) 현재 시간에서 1년 더하기
			target++;		rcount += jdbc.update(sql);
			System.out.println(target+" : "+sql+" : "+ rcount);
		}
		if(oi.getOi_upoint() > 0) {
			sql = "update t_member_info set mi_point = mi_point - "+oi.getOi_spoint()+
					" where mi_id ='"+oi.getMi_id()+"' ";
			target++;		rcount += jdbc.update(sql);
			sql = "insert into t_member_point (mi_id, mp_point, mp_su, mp_desc, mp_detail) values('"+oi.getMi_id()+
					"','"+oi.getOi_spoint()+"','u',상품구매','"+oi_id+"')";
			target++;		rcount += jdbc.update(sql);
			System.out.println(target+" : "+sql+" : "+ rcount);
		}
		System.out.println("ausePoint target:"+target);
		System.out.println("ausePoint rcount:"+rcount);
		return target+","+rcount; 
	}
	
	public String getOrderId() {
		 
        String oi_id = null;
        LocalDate today = LocalDate.now();
        String td = (today + "").substring(2).replace("-", "");

        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        String eng = alpha.charAt(rnd.nextInt(26)) + "" + alpha.charAt(rnd.nextInt(26));

        String sql = "select ifnull(max(cast(substring(oi_id, 9, 4) as unsigned)), 1000) seq from t_order_info " +
                "where left(oi_id, 6) = ?";
        int maxSeq = jdbc.queryForObject(sql, new Object[]{td}, Integer.class);
        System.out.println("getOrderId: "+sql);
/*
주문번호 형식 : 날짜 6자리 + 랜덤 알파벳 + 1001~
순서로 조합.12자리
주문이 늘어날 때마다 뒤에 숫자가 커짐
*/
        
        int num = maxSeq + 1;
        oi_id = td + eng + num ;
        return oi_id;
	    }

	public OrderInfo getOneOrderInfo(String miid, String oiid) {
		String sql="select*from t_order_info where mi_id='"+miid+"' and oi_id='"+oiid+"' ";
		List<OrderInfo> results = jdbc.query(sql, new RowMapper<OrderInfo>(){
			public OrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderInfo oi =new OrderInfo();
				
				oi.setOi_id(oiid);		
				
				oi.setMi_id(miid);
				oi.setOi_name(rs.getString("oi_name"));			oi.setOi_phone(rs.getString("oi_phone"));
				oi.setOi_zip(rs.getString("oi_zip"));			oi.setOi_addr1(rs.getString("oi_addr1"));
				oi.setOi_addr2(rs.getString("oi_addr2"));		oi.setOi_memo(rs.getString("oi_memo"));
				oi.setOi_payment(rs.getString("oi_payment"));	oi.setOi_invoice(rs.getString("oi_invoice"));
				oi.setOi_status(rs.getString("oi_status"));		oi.setOi_date(rs.getString("oi_date"));
				oi.setOi_pay(rs.getInt("oi_pay"));				oi.setOi_upoint(rs.getInt("oi_upoint"));
				oi.setOi_spoint(rs.getInt("oi_spoint"));		oi.setDetailList(setDeList(oiid));
				return oi;				
			}
		});
		return results.isEmpty() ? null : results.get(0);
	}

}