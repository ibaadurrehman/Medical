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
import com.medical.app.model.CustomerBean;

@Service("customerService")
@Transactional
public class CustomerService {
private List<CustomerBean> customerBeanList;
	
	@Autowired
	MedicalAppConfig appConfig;
	
	public List<CustomerBean> fetchCustomerList(){
		
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select * from customer where cust_name <> 'Direct Stock Minus' order by cust_name";
			customerBeanList = jdbcTemplate.query(sqlQuery, new RowMapper<CustomerBean>() {
				 
	            @Override
	            public CustomerBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	CustomerBean custBean = new CustomerBean();
	            	
	            	custBean.setCustId(rs.getString("cust_id"));
	            	custBean.setCustCode(rs.getString("cust_code"));
	            	custBean.setCustName(rs.getString("cust_name"));
	            	custBean.setOwnerName(rs.getString("owner_name"));
	            	custBean.setAddress(rs.getString("address"));
	            	custBean.setPhoneNo(rs.getString("phone_no"));
	            	custBean.setEmailId(rs.getString("email_id"));
	            	custBean.setVatNo(rs.getString("vat_no"));
	            	custBean.setOpeningBal(rs.getDouble("opening_bal"));
					
	                return custBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return customerBeanList;
	}
	
	public int insertCustomer(CustomerBean custForm){
		int result = 0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			int count=0;
			
			String custCode = "C00" + getCustId();
						
			if(custForm != null){
				if(custForm.getCustName() != null && !custForm.getCustName().equals("")){
					String sqlQuery = "select count(*) from customer where cust_name=?";
					
					count = jdbcTemplate.queryForObject(sqlQuery,new Object[]{custForm.getCustName()}, Integer.class);
					
					if(count > 0){
						result = -1;
					}else{
						sqlQuery = "insert into customer(cust_code,cust_name,owner_name,address,phone_no,email_id,vat_no,opening_bal) values(?,?,?,?,?,?,?,?)";
						result = jdbcTemplate.update(sqlQuery, new Object[]{custCode,custForm.getCustName(),custForm.getOwnerName(),
								custForm.getAddress(),custForm.getPhoneNo(),custForm.getEmailId(),custForm.getVatNo(),custForm.getOpeningBal()});
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateCustomer(CustomerBean custForm){
		int result=0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			if(custForm != null){
				if(custForm.getCustId() != null && !custForm.getCustId().equals("")){
					
					String sqlQuery = "update customer set cust_name=?,owner_name=?,address=?,phone_no=?,email_id=?,vat_no=?,opening_bal=?,cust_code=? where cust_id=?";
					
					result = jdbcTemplate.update(sqlQuery, new Object[]{custForm.getCustName(),custForm.getOwnerName(),custForm.getAddress(),custForm.getPhoneNo(),
							custForm.getEmailId(),custForm.getVatNo(),custForm.getOpeningBal(),custForm.getCustCode(),custForm.getCustId()});
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteCustomer(String custId){
		int result = 0;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			String sqlQuery = "delete from customer where cust_id=?";
			
			result = jdbcTemplate.update(sqlQuery, new Object[]{custId});
			
			if(result > 0){
				System.out.println("Customer deleted successfully.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int getCustId(){
		Integer custId=0;
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			String sqlQuery = "SELECT AUTO_INCREMENT custId FROM information_schema.`TABLES` WHERE `table_schema` = 'pharmadb' AND `table_name` = 'customer';";
			
			custId = jdbcTemplate.queryForObject(sqlQuery, Integer.class);
			System.out.println("custId"+custId);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return custId;
	}
}
