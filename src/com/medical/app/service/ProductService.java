package com.medical.app.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medical.app.configurations.MedicalAppConfig;
import com.medical.app.model.ProductBean;

@Service("productService")
@Transactional
public class ProductService {
	private List<ProductBean> prodBeanList;
	
	@Autowired
	MedicalAppConfig appConfig;
	
	public List<ProductBean> fetchProdList(String manId){
		
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select p.prod_id,p.prod_code,p.prod_name,pd.prod_dtl_id,pd.mrp,pd.rate,p.pckg_size,m.man_id,m.man_name,t.tax_id,t.tax_name,t.tax_perc from product p,manufacturer m,"  +
							  "tax t,product_details pd where p.man_id=m.man_id and pd.tax_id=t.tax_id and p.prod_id=pd.prod_id and p.man_id=? order by p.prod_code";
			
			prodBeanList = jdbcTemplate.query(sqlQuery, new Object[]{manId}, new RowMapper<ProductBean>() {
				 
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	
	            	prodBean.setProdId(rs.getString("prod_id"));
	            	prodBean.setProdDtlId(rs.getInt("prod_dtl_id"));
	            	prodBean.setProdCode(rs.getString("prod_code"));
	            	prodBean.setProdName(rs.getString("prod_name"));
	            	prodBean.setMrp(rs.getDouble("mrp"));
	            	prodBean.setRate(rs.getDouble("rate"));
	            	prodBean.setPckSize(rs.getString("pckg_size"));
	            	prodBean.setManId(rs.getString("man_id"));
	            	prodBean.setManName(rs.getString("man_name"));
	            	prodBean.setTaxId(rs.getInt("tax_id"));
	            	prodBean.setTaxName(rs.getString("tax_name"));
	            	prodBean.setTaxPerc(rs.getDouble("tax_perc"));

	            	return prodBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return prodBeanList;
	}
	
	public List<ProductBean> fetchProdDtlList(String prodName){
		
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select p.prod_id,p.prod_code,p.prod_name,pd.prod_dtl_id,pd.cur_stock,pd.mrp,pd.rate,p.pckg_size,m.man_id,m.man_name,t.tax_id,t.tax_name,t.tax_perc from product p,manufacturer m,"  +
							  "tax t,product_details pd where p.man_id=m.man_id and pd.tax_id=t.tax_id and p.prod_id=pd.prod_id and p.prod_Name=? order by p.prod_code";
			
			prodBeanList = jdbcTemplate.query(sqlQuery, new Object[]{prodName}, new RowMapper<ProductBean>() {
				 
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	
	            	prodBean.setProdId(rs.getString("prod_id"));
	            	prodBean.setProdDtlId(rs.getInt("prod_dtl_id"));
	            	prodBean.setProdCode(rs.getString("prod_code"));
	            	prodBean.setProdName(rs.getString("prod_name"));
	            	prodBean.setMrp(rs.getDouble("mrp"));
	            	prodBean.setRate(rs.getDouble("rate"));
	            	prodBean.setPckSize(rs.getString("pckg_size"));
	            	prodBean.setManId(rs.getString("man_id"));
	            	prodBean.setManName(rs.getString("man_name"));
	            	prodBean.setTaxId(rs.getInt("tax_id"));
	            	prodBean.setTaxName(rs.getString("tax_name"));
	            	prodBean.setTaxPerc(rs.getDouble("tax_perc"));

	            	return prodBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return prodBeanList;
	}
	
	public List<ProductBean> fetchProdNameList(String manId){
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select distinct prod_name from product where man_id=?";
			
			prodBeanList = jdbcTemplate.query(sqlQuery, new Object[]{manId}, new RowMapper<ProductBean>() {
				 
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	prodBean.setProdName(rs.getString("prod_name"));
	
	            	return prodBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return prodBeanList;
	}
	
	public List<ProductBean> fetchProdStockList(String manId){
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select distinct p.prod_name from product p, product_details pd where p.prod_id=pd.prod_id and pd.cur_stock > 0 and p.man_id=?";
			
			prodBeanList = jdbcTemplate.query(sqlQuery, new Object[]{manId}, new RowMapper<ProductBean>() {
				 
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	prodBean.setProdName(rs.getString("prod_name"));
	
	            	return prodBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return prodBeanList;
	}
	
	public int insertProd(ProductBean prodForm){
		int result = 0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			int count=0;
			String prodId="";
						
			if(prodForm != null){
				String sqlQuery = "select prod_id from product where prod_name=? and man_id=? and pckg_size=?";
				
				List<ProductBean> bean = jdbcTemplate.query(sqlQuery,new Object[]{prodForm.getProdName(),prodForm.getManId(),prodForm.getPckSize()}, new RowMapper<ProductBean>(){
					@Override
		            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
		            	ProductBean prodBean = new ProductBean();
		            	prodBean.setProdId(rs.getString("prod_id"));
		            	
		             	return prodBean;
					}
				});
				
				if(bean.size() > 0){
					sqlQuery = "select count(*) prodCount from product_details where prod_id=? and mrp=? and rate=?";
					count = jdbcTemplate.queryForObject(sqlQuery,new Object[]{bean.get(0).getProdId(), prodForm.getMrp(), prodForm.getRate()}, Integer.class);
					
					if(count > 0){
						result = -1;
						System.out.println("found duplicate");
					}else{
						sqlQuery = "insert into product_details(prod_id,mrp,rate,cur_stock,tax_id) values(?,?,?,?,?)";
						
						result = jdbcTemplate.update(sqlQuery, new Object[]{bean.get(0).getProdId(), prodForm.getMrp(), prodForm.getRate(), 0, prodForm.getTaxId()});
					}
				}else{
					StringBuilder prodCode = new StringBuilder();
					prodCode.append(prodForm.getManId().substring(0, 2));
					prodCode.append("00");
					prodCode.append(prodForm.getProdName().substring(0, 1));
					prodCode.append(getProdId());
					prodForm.setProdCode(prodCode.toString());
					prodId = String.valueOf(getProdId());
					sqlQuery = "insert into product(prod_code,prod_name,man_id,pckg_size) values(?,?,?,?)";
					
					count = 0;
					count = jdbcTemplate.update(sqlQuery, new Object[]{prodCode.toString(), prodForm.getProdName(), prodForm.getManId(), prodForm.getPckSize()});
					
					if(count > 0){
						count = 0;
						sqlQuery = "insert into product_details(prod_id,mrp,rate,cur_stock,tax_id) values(?,?,?,?,?)";
						
						count = jdbcTemplate.update(sqlQuery, new Object[]{prodId, prodForm.getMrp(), prodForm.getRate(), 0, prodForm.getTaxId()});
						
						if(count > 0){
							System.out.println("Successfully inserted product");
							result = count;
						}else{
							System.out.println("Error inserting product details");
							result = -3;
						}
					}else{
						System.out.println("Error inserting product");
						result=-2;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateProd(ProductBean prodForm){
		int result=0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			if(prodForm != null){
				if(prodForm.getProdId() != null && !prodForm.getProdId().equals("") && prodForm.getProdDtlId() != 0){

					String sqlQuery = "select count(*) from product_details d, product p where p.prod_id=d.prod_id and p.prod_id=? and mrp=? and rate=? and p.pckg_size=?";
					int count = jdbcTemplate.queryForObject(sqlQuery,new Object[]{prodForm.getProdId(), prodForm.getMrp(), prodForm.getRate(), prodForm.getPckSize()}, Integer.class);
					
					if(count <= 0){
						sqlQuery = "update product set prod_name=?,man_id=?,pckg_size=? where prod_id=?";
						result = jdbcTemplate.update(sqlQuery, new Object[]{prodForm.getProdName(),prodForm.getManId(),prodForm.getPckSize(), prodForm.getProdId()});

						sqlQuery = "update product_details set mrp=?,rate=?,tax_id=? where prod_dtl_id=?";
						result = jdbcTemplate.update(sqlQuery, new Object[]{prodForm.getMrp(),prodForm.getRate(),prodForm.getTaxId(), prodForm.getProdDtlId()});
					}else{
						result = -1;
						System.out.println("Product duplicate...");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteProd(String prodId, String prodDtlId){
		int result = 0;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			String sqlQuery = "select count(p.prod_dtl_id) purCount,count(s.prod_dtl_id) salCount from purchase_prod_details p,sales_prod_details s "+
							"where p.prod_dtl_id=? and s.prod_dtl_id=?";
			
			List<ProductBean> bean = jdbcTemplate.query(sqlQuery,new Object[]{prodDtlId, prodDtlId}, new RowMapper<ProductBean>(){
				@Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	
	            	prodBean.setCount(rs.getInt("purCount"));
	            	prodBean.setCount(prodBean.getCount() + rs.getInt("salCount"));
	            	
	             	return prodBean;
				}
			});
			
			if(bean.size() > 0 && bean.get(0).getCount() > 0){
				result = -1;
			}else{
				sqlQuery = "delete from product_details where prod_dtl_id=?";
				
				result = jdbcTemplate.update(sqlQuery, new Object[]{prodDtlId});
				
				if(result > 0){
					result = 0; 
					
					sqlQuery = "select count(*) from product_details where prod_id=?";
					result = jdbcTemplate.queryForObject(sqlQuery, new Object[]{prodId}, Integer.class);
					
					if(result == 0){
						sqlQuery = "delete from product where prod_id=?";
						result = jdbcTemplate.update(sqlQuery, new Object[]{prodId});
					}else{
						result = 1;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int getProdId(){
		Integer prodId=0;
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			String sqlQuery = "SELECT AUTO_INCREMENT prodId FROM information_schema.`TABLES` WHERE `table_schema` = 'pharmadb' AND `table_name` = 'product';";
			
			prodId = jdbcTemplate.queryForObject(sqlQuery, Integer.class);
			System.out.println("prodId"+prodId);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return prodId;
	}
}
