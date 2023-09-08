package dao;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import vo.*;

public class ProductDao {
	private JdbcTemplate jdbc;

	public ProductDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	public List<ProductInfo> getNewList() {
		String sql = "select * from t_product_info where pi_isview='y' order by pi_date desc limit 0, 12";
		List<ProductInfo> productList = jdbc.query(sql,  new RowMapper<ProductInfo>(){
			public ProductInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductInfo pi = new ProductInfo();
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPi_price(rs.getInt("pi_price"));
				pi.setPi_cost(rs.getInt("pi_cost"));
				pi.setPi_dc(rs.getDouble("pi_dc"));
				pi.setPi_com(rs.getString("pi_com"));
				pi.setPi_img1(rs.getString("pi_img1"));
				pi.setPi_img2(rs.getString("pi_img2"));
				pi.setPi_img3(rs.getString("pi_img3"));
				pi.setPi_desc(rs.getString("pi_desc"));
				return pi;
			}
		});
	
		return productList; 
	}
	public List<ProductInfo> productView(String piid) {
		String sql = "select * from t_product_info a ,t_product_brand b where a.pb_id=b.pb_id and pi_isview='y'  and pi_id='" + piid+"' ";
		//System.out.println(sql);
		List<ProductInfo> productList = jdbc.query(sql,  new RowMapper<ProductInfo>(){
			public ProductInfo mapRow(ResultSet rs, int rowNum) throws SQLException {			
				ProductInfo pi = new ProductInfo();
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPb_id(rs.getString("pb_id"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPi_price(rs.getInt("pi_price"));
				pi.setPb_name(rs.getString("pb_name"));
				pi.setPi_dc(rs.getDouble("pi_dc"));
				pi.setPi_img1(rs.getString("pi_img1"));
				pi.setPi_img2(rs.getString("pi_img2"));
				pi.setPi_img3(rs.getString("pi_img3"));
				pi.setPi_desc(rs.getString("pi_desc"));
				pi.setPi_com(rs.getString("pi_com"));
	            return pi;
			}
		});

		return  productList;
	}
	
	public List<ProductStock> productStockView(String piid) {
			String sql = "select * from t_product_stock  where ps_isview='y' and pi_id='" + piid + "' ";
			//System.out.println(sql);

			List<ProductStock> stockList = jdbc.query(sql,  new RowMapper<ProductStock>(){
				public ProductStock mapRow(ResultSet rs, int rowNum) throws SQLException {
					ProductStock ps = new ProductStock();
					ps.setPs_idx(rs.getInt("ps_idx"));
					ps.setPi_id(rs.getString("pi_id"));
					ps.setPs_size(rs.getString("ps_size"));
					ps.setPs_stock(rs.getInt("ps_stock"));
					return ps;
				}
			});
	
			return stockList;
			
			
		}

	public List<ProductInfo> getProductList(int cpage, int psize, String where, String orderBy) {
		String sql = "select a.pi_id, a.pi_name, a.pi_img1, a.pi_price, a.pi_dc, a.pi_sale, sum(b.ps_stock) stock, if(c.pb_id ='NN', 'NIKE',if(c.pb_id='CC','Crocs', 'Dr. Martens')) pbname" + 
				" from t_product_info a, t_product_stock b, t_product_brand c where a.pi_id = b.pi_id  and a.pb_id=c.pb_id and "
				+ "a.pi_isview = 'y' and b.ps_isview ='y' "+  where +" group by a.pi_id " + orderBy +" limit " + ((cpage - 1) * psize) + "," + psize ;
			System.out.println(sql);
		
			 List<ProductInfo> productList = jdbc.query(sql,
						(ResultSet rs,int rowNum)->{
						ProductInfo pi = new ProductInfo();
						pi.setPi_id(rs.getString("pi_id"));	
						pi.setPi_name(rs.getString("pi_name"));
						pi.setPi_img1(rs.getString("pi_img1"));
						pi.setPi_price(rs.getInt("pi_price"));
						pi.setPi_dc(rs.getDouble("pi_dc"));
						pi.setPi_sale(rs.getInt("pi_sale"));
						pi.setStock(rs.getInt("stock"));						
						pi.setPb_name(rs.getString("pbname"));						
						return pi;
				});	
			 
				
			return productList;
	}

	public int getProductCount(String where) {		
		String sql = "select count(stock) cnt " + 
				"from("  
					+ "select sum(b.ps_stock) stock "
					+ "from t_product_info a, t_product_stock b " 
					+ "where a.pi_id = b.pi_id  and a.pi_isview = 'y' " + where + " group by a.pi_id "
					+ ") tmp";
	
		//System.out.println("getProductCountsql :"+sql);
		int rcnt = jdbc.queryForObject(sql, Integer.class);
		
		return rcnt;
	}

	public List<ProductBrand> getBrandList() {
		String sql = "select * from t_product_brand";
		List<ProductBrand> brandList = jdbc.query(sql,
				(ResultSet rs,int rowNum)->{
					ProductBrand pb = new ProductBrand();
					pb.setPb_id(rs.getString("pb_id"));
					pb.setPb_name(rs.getString("pb_name"));
					
					return pb;
				});	
		return brandList;
	}

	public List<ProductCtgrBig> getCtgrList() {
		String sql = "select * from t_product_ctgr_big";
		//System.out.println(sql);
		List<ProductCtgrBig> ctgrList  = jdbc.query(sql,
				(ResultSet rs,int rowNum)->{
					ProductCtgrBig pcb = new ProductCtgrBig();
					pcb.setPcb_id(rs.getString("pcb_id"));
					pcb.setPcb_name(rs.getString("pcb_name"));
					
					return pcb;
				});	
		return ctgrList;
	}

	public List<ProductStock> getSizeList() {
		String sql = "select ps_size from t_product_stock group by ps_size order by ps_size";
		//System.out.println(sql);
		List<ProductStock> stockList = jdbc.query(sql,
				(ResultSet rs,int rowNum)->{
					ProductStock sl = new ProductStock();
					sl.setPs_size(rs.getString("ps_size"));
					return sl;
				});	
		return stockList;
	}
	
	// add main product list
	public List<ProductInfo> getMainList(String orderby) {
		String sql = "select * from t_product_info where pi_isview='y' order by "+orderby+" desc limit 0, 12";
		List<ProductInfo> productList = jdbc.query(sql,  new RowMapper<ProductInfo>(){
			public ProductInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductInfo pi = new ProductInfo();
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPi_price(rs.getInt("pi_price"));
				pi.setPi_cost(rs.getInt("pi_cost"));
				pi.setPi_dc(rs.getDouble("pi_dc"));
				pi.setPi_com(rs.getString("pi_com"));
				pi.setPi_img1(rs.getString("pi_img1"));
				pi.setPi_img2(rs.getString("pi_img2"));
				pi.setPi_img3(rs.getString("pi_img3"));
				pi.setPi_desc(rs.getString("pi_desc"));
				pi.setPi_com(rs.getString("pi_com"));
				return pi;
			}
		});
	
		return productList; 
	}

	public List<ProductInfo> getShoesList(String pcb_id) {
		String sql ="select a.pi_id, a.pi_name from t_product_info a, t_product_stock b where a.pi_id=b.pi_id and a.pcb_id='"+pcb_id+"' and a.pi_isview='y' "
				+ "and b.ps_stock != 0 order by a.pi_sale desc limit 0,3";
		List<ProductInfo> productList = jdbc.query(sql,  new RowMapper<ProductInfo>(){
			public ProductInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductInfo pi = new ProductInfo();
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPi_name(rs.getString("pi_name"));
				return pi;
			}
		});
	
		return productList;
	}


}

