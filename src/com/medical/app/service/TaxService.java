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
import com.medical.app.model.TaxBean;

@Service("taxService")
@Transactional
public class TaxService {
	private List<TaxBean> taxBeanList;
	
	@Autowired
	MedicalAppConfig appConfig;
	
	public List<TaxBean> fetchTaxList(){
		
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select * from tax order by tax_name";
			taxBeanList = jdbcTemplate.query(sqlQuery, new RowMapper<TaxBean>() {
				 
	            @Override
	            public TaxBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	TaxBean taxBean = new TaxBean();
	            	
	            	taxBean.setTaxId(rs.getInt("tax_id"));
	            	taxBean.setTaxName(rs.getString("tax_name"));
	            	taxBean.setTaxPerc(rs.getDouble("tax_perc"));

	            	return taxBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return taxBeanList;
	}
	
	public int insertTax(TaxBean taxForm){
		int result = 0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			int count=0;
						
			if(taxForm != null){
				
				String sqlQuery = "select count(*) from tax where tax_perc=?";
				
				count = jdbcTemplate.queryForObject(sqlQuery,new Object[]{taxForm.getTaxPerc()}, Integer.class);
				
				System.out.println("count="+count);
				if(count > 0){
					result = -1;
					System.out.println("found tax duplicate");
				}else{
					System.out.println("Inside else for insert");
										
					sqlQuery = "insert into tax(tax_name,tax_perc) values(?,?)";
					
					result = jdbcTemplate.update(sqlQuery, new Object[]{taxForm.getTaxName(),
							taxForm.getTaxPerc()});
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateTax(TaxBean taxForm){
		int result=0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			if(taxForm != null){
				if(taxForm.getTaxId() != 0){
					
					String sqlQuery = "update tax set tax_name=?,tax_perc=? where tax_id=?";
					
					result = jdbcTemplate.update(sqlQuery, new Object[]{taxForm.getTaxName(),taxForm.getTaxPerc(),taxForm.getTaxId()});
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteTax(String taxId){
		int result = 0;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			String sqlQuery = "delete from tax where tax_id=?";
			
			result = jdbcTemplate.update(sqlQuery, new Object[]{taxId});
			
			if(result > 0){
				System.out.println("Tax deleted successfully.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
