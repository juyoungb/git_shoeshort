package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class AdProductDao {

	private JdbcTemplate jdbc;
	
	public AdProductDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
	public int getProductCount(String where) {		
		String sql = "select count(distinct(a.pi_id))  rcnt from t_product_info a, t_product_stock b, t_product_ctgr_big c, t_product_brand d"+ where;
		System.out.println(sql);
		int result = jdbc.queryForObject(sql, Integer.class);
		return result;
	}

	public List<ProductInfo> getProductList(int cpage, int psize, String where) {
		String sql ="select a.pi_img1, a.pi_id, a.pi_name,d.pb_name, c.pcb_name, a.pi_cost, sum(b.ps_stock) stock, a.pi_date, a.pi_last, a.pi_isview  "
				+ " from t_product_info a, t_product_stock b, t_product_ctgr_big c, t_product_brand d "
				+" "+ where + " group by a.pi_id limit " + ((cpage - 1) * psize) + "," + psize ; 
		System.out.println(sql);
		List<ProductInfo> productList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				ProductInfo pi = new ProductInfo();
				pi.setPi_img1(rs.getString("pi_img1"));
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPb_name(rs.getString("pb_name"));
				pi.setPcb_name(rs.getString("pcb_name"));
				pi.setPi_cost(rs.getInt("pi_cost"));
				pi.setStock(rs.getInt("stock"));
				pi.setPi_date(rs.getString("pi_date"));
				pi.setPi_last(rs.getString("pi_last"));
				pi.setPi_isview(rs.getString("pi_isview"));
				
	            return pi;
		});
		
		return productList;
	}
	public List<ProductStock> getStockList(String piid) {
		String sql = "select * from t_product_stock where pi_id='" + piid + "' ";
		List<ProductStock> stockList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				ProductStock ps = new ProductStock();
				ps.setPs_size(rs.getString("ps_size"));
				ps.setPs_stock(rs.getInt("ps_stock"));
				
				
	            return ps;
		});
		
		return stockList;
	}
	
	public int IsviewProduct(String piid, String type) {
		String sql = "";
		if(type.equals("미게시")) {
			sql = "update  t_product_info set pi_isview='n' where pi_id ='" + piid + "' ";	
		} else if (type.equals("게시")) {		
			sql = "update  t_product_info set pi_isview='y' where pi_id ='" + piid + "' ";
		
		} 
		System.out.println(sql);
		int result = jdbc.update(sql);
		return result;
	}
	
	public List<ProductCtgr> getCtgrList() {
		String sql = "select  *from t_product_ctgr_big";
		List<ProductCtgr> ctgrList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				ProductCtgr pc = new ProductCtgr();
				pc.setPcb_id(rs.getString("pcb_id"));
				pc.setPcb_name(rs.getString("pcb_name"));			
	            return pc;
		});
		return ctgrList;
	}	
	
	public List<ProductBrand> getBrandList() {
		String sql = "select  *from t_product_brand";
				
		List<ProductBrand> brandList = jdbc.query(sql, 
				(ResultSet rs, int rowNum) -> {
				ProductBrand pb = new ProductBrand();
				pb.setPb_id(rs.getString("pb_id"));
				pb.setPb_name(rs.getString("pb_name"));
				
	            return pb;
		});
		return brandList;
	}
	
	//상품아이디가 중복인지 확인
	public int chkPiid(String piid) {
		String sql = "select count(*) from t_product_info where pi_id = '" + piid + "' ";
		//System.out.println(sql);
		int result = jdbc.queryForObject(sql, Integer.class);
		return result;
	}
	
	public int insertProduct(ProductInfo pi, String[] arr) {
		String sql = "insert into t_product_info (pi_id, pi_name, pcb_id,pb_id,pi_gubun,pi_com, pi_desc,ai_idx,pi_img1,pi_img2,pi_img3, pi_price, pi_cost) "
		+ "values ('"+ pi.getPi_id() +"','"+ pi.getPi_name() +"','"+ pi.getPcb_id() +"', '"+ pi.getPb_id()+"',"
				+ " '"+pi.getPi_gubun()+"','"+pi.getPi_com()+"', '"+pi.getPi_desc()+"', 1, '"+pi.getPi_img1()+"','"+pi.getPi_img2()+"' ,'"+pi.getPi_img3()+"',"+pi.getPi_price()+","+pi.getPi_cost()+")";
		System.out.println(sql);
		int result = jdbc.update(sql);
		
		insertStock(pi, arr); // 사이즈와 갯수 insert
		
		return  result;	
	}
	
	public void insertStock(ProductInfo pi, String[] arr)  {		
		for(int j = 0 ; j < arr.length; j++) {
			pi.setStock(Integer.parseInt(arr[j].substring(4)));
			pi.setPs_size(Integer.parseInt(arr[j].substring(0,3)));
			String sql = "insert into t_product_stock (pi_id, ps_size, ps_stock, ps_isview) "
					+ " values('" + pi.getPi_id() + "', " + pi.getPs_size()+ ", "+pi.getStock() +", 'y')";
			System.out.println(sql);
			 jdbc.update(sql);
		}
		
	}

}

