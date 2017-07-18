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
import com.medical.app.model.DistributorBean;

@Service("distributorService")
@Transactional
public class DistributorService {
private List<DistributorBean> distributorBeanList;
	
	@Autowired
	MedicalAppConfig appConfig;
	
	public List<DistributorBean> fetchDistributorList(){
		
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select d.dist_id,d.dist_name,d.address,d.email_id,d.phone_no,d.vat_no,d.opening_bal from distributor d order by d.dist_name";
			distributorBeanList = jdbcTemplate.query(sqlQuery, new RowMapper<DistributorBean>() {
				 
	            @Override
	            public DistributorBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	DistributorBean distBean = new DistributorBean();
	            	
	            	distBean.setDistId(rs.getString("dist_id"));
	            	distBean.setDistName(rs.getString("dist_name"));
	            	distBean.setAddress(rs.getString("address"));
	            	distBean.setPhoneNo(rs.getString("phone_no"));
	            	distBean.setEmailId(rs.getString("email_id"));
	            	distBean.setVatNo(rs.getString("vat_no"));
	            	distBean.setOpeningBal(rs.getDouble("opening_bal"));
					
	                return distBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return distributorBeanList;
	}
	
	public int insertDistributor(DistributorBean distForm){
		int result = 0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			int count=0;
						
			if(distForm != null){
				if(distForm.getDistId() != null && !distForm.getDistId().equals("")){
					String sqlQuery = "select count(*) from distributor where dist_id=?";
					
					count = jdbcTemplate.queryForObject(sqlQuery,new Object[]{distForm.getDistId()}, Integer.class);
					
					if(count > 0){
						result = -1;
					}else{
						sqlQuery = "insert into distributor values(?,?,?,?,?,?,?)";
						result = jdbcTemplate.update(sqlQuery, new Object[]{distForm.getDistId(),distForm.getDistName(),
								distForm.getAddress(),distForm.getPhoneNo(),distForm.getVatNo(),distForm.getOpeningBal(),distForm.getEmailId()});
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateDistributor(DistributorBean distForm){
		int result=0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			if(distForm != null){
				if(distForm.getDistId() != null && !distForm.getDistId().equals("")){
					
					String sqlQuery = "update distributor set dist_name=?,address=?,phone_no=?,vat_no=?,opening_bal=?,email_id=? where dist_id=?";
					
					result = jdbcTemplate.update(sqlQuery, new Object[]{distForm.getDistName(),
							distForm.getAddress(),distForm.getPhoneNo(),distForm.getVatNo(),distForm.getOpeningBal(),distForm.getEmailId(),distForm.getDistId()});
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteDistributor(String distId){
		int result = 0;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			String sqlQuery = "delete from distributor where dist_id=?";
			
			result = jdbcTemplate.update(sqlQuery, new Object[]{distId});
			
			if(result > 0){
				System.out.println("Distributor deleted successfully.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
