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
import com.medical.app.model.ManufacturerBean;

@Service("manufactService")
@Transactional
public class ManufactService{
	private List<ManufacturerBean> manufactBeanList;
	
	@Autowired
	MedicalAppConfig appConfig;
	
	public List<ManufacturerBean> fetchManufactList(){
		
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select * from manufacturer order by man_name";
			manufactBeanList = jdbcTemplate.query(sqlQuery, new RowMapper<ManufacturerBean>() {
				 
	            @Override
	            public ManufacturerBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ManufacturerBean manBean = new ManufacturerBean();
	            	
	            	manBean.setManId(rs.getString("man_id"));
	            	manBean.setManName(rs.getString("man_name"));
	            	manBean.setAddress(rs.getString("address"));
	            	manBean.setPhoneNo(rs.getString("phone_no"));
	            	manBean.setCustCare(rs.getString("customer_care"));
					
	                return manBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return manufactBeanList;
	}
	
	public int insertManufacturer(ManufacturerBean manForm){
		int result = 0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			int count=0;
						
			if(manForm != null){
				if(manForm.getManId() != null && !manForm.getManId().equals("")){
					System.out.println("inside If="+manForm.getManId());
					String sqlQuery = "select count(*) from manufacturer where man_id=? or man_name=?";
					
					count = jdbcTemplate.queryForObject(sqlQuery,new Object[]{manForm.getManId(), manForm.getManName()}, Integer.class);
					
					System.out.println("count="+count);
					if(count > 0){
						result = -1;
						System.out.println("found duplicate");
					}else{
						System.out.println("Inside else for insert");
						sqlQuery = "insert into manufacturer values(?,?,?,?,?)";
						result = jdbcTemplate.update(sqlQuery, new Object[]{manForm.getManId(),manForm.getManName(),
								manForm.getAddress(),manForm.getPhoneNo(),manForm.getCustCare()});
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateManufacturer(ManufacturerBean manForm){
		int result=0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			if(manForm != null){
				if(manForm.getManId() != null && !manForm.getManId().equals("")){
					
					String sqlQuery = "update manufacturer set man_name=?,address=?,phone_no=?,customer_care=? where man_id=?";
					
					result = jdbcTemplate.update(sqlQuery, new Object[]{manForm.getManName(),manForm.getAddress(),
							manForm.getPhoneNo(),manForm.getCustCare(),manForm.getManId()});
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteManufacturer(String manId){
		int result = 0;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			String sqlQuery = "delete from manufacturer where man_id=?";
			
			result = jdbcTemplate.update(sqlQuery, new Object[]{manId});
			
			if(result > 0){
				System.out.println("Manufacturer deleted successfully.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
