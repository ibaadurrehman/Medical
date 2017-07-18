package com.medical.app.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medical.app.configurations.MedicalAppConfig;
import com.medical.app.model.BankBean;

@Service("bankService")
@Transactional
public class BankService {
private List<BankBean> bankBeanList;
	
	@Autowired
	MedicalAppConfig appConfig;
	
	public List<BankBean> fetchBankList(){
		
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select b.bank_id,b.bank_name,b.address,b.phone_no,b.opening_bal,b.bank_code,t.bank_type_id,t.bank_type from bank_details b,bank_type t where b.bank_type_id=t.bank_type_id order by bank_code";
			bankBeanList = jdbcTemplate.query(sqlQuery, new RowMapper<BankBean>() {
				 
	            @Override
	            public BankBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	BankBean bankBean = new BankBean();
	            	
	            	bankBean.setBankId(rs.getInt("bank_id"));
	            	bankBean.setBankName(rs.getString("bank_name"));
	            	bankBean.setAddress(rs.getString("address"));
	            	bankBean.setPhoneNo(rs.getString("phone_no"));
	            	bankBean.setOpeningBal(rs.getDouble("opening_bal"));
	            	bankBean.setBankCode(rs.getString("bank_code"));
	            	bankBean.setBankTypeId(rs.getInt("bank_type_id"));
	            	bankBean.setBankType(rs.getString("bank_type"));

	            	return bankBean;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return bankBeanList;
	}
	
	public List<BankBean> getStatement(BankBean bankData){
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("select A.trans_id,A.trans_dt,A.trans_amt,A.trans_pay_type_id,A.cheque_no,A.cheque_dt,A.trans_type_id,A.balance,A.dist_id,A.cust_id,A.status,A.dist_name,A.cust_name from ");
			sqlQuery.append("(select distinct tm.trans_id trans_id ,tm.trans_dt trans_dt,tm.trans_amt trans_amt,tm.trans_pay_type_id trans_pay_type_id,tm.cheque_no cheque_no,tm.cheque_dt cheque_dt,tm.trans_type_id trans_type_id,tm.balance balance, ");
			sqlQuery.append("tc.dist_id dist_id,tc.cust_id cust_id,tm.status status,d.dist_name,0 cust_name from transactions_master tm, transactions_child tc, distributor d ");
			sqlQuery.append("where tm.self_bank_id=? and tm.trans_dt between ? and ? and tm.trans_id = tc.trans_id and d.dist_id=tc.dist_id union ");
			sqlQuery.append("select distinct tm.trans_id trans_id ,tm.trans_dt trans_dt,tm.trans_amt trans_amt,tm.trans_pay_type_id trans_pay_type_id,tm.cheque_no cheque_no,tm.cheque_dt cheque_dt,tm.trans_type_id trans_type_id,tm.balance balance, ");
			sqlQuery.append("tc.dist_id dist_id,tc.cust_id cust_id,tm.status status,0 dist_name,c.cust_name cust_name from transactions_master tm, transactions_child tc, customer c ");
			sqlQuery.append("where tm.self_bank_id=? and tm.trans_dt between ? and ? and tm.trans_id = tc.trans_id and c.cust_id=tc.cust_id union ");
			sqlQuery.append("select tm.trans_id trans_id,tm.trans_dt trans_dt,tm.trans_amt trans_amt,tm.trans_pay_type_id trans_pay_type_id,0 cheque_no,0 cheque_dt,tm.trans_type_id trans_type_id,tm.balance balance,0 dist_id,0 cust_id,tm.status status,0 dist_name,'Cash Deposit' cust_name ");
			sqlQuery.append("from transactions_master tm where tm.self_bank_id=? and tm.trans_dt between ? and ? and tm.trans_pay_type_id = 1 union ");
			sqlQuery.append("select tm.trans_id trans_id,tm.trans_dt trans_dt,tm.trans_amt trans_amt,tm.trans_pay_type_id trans_pay_type_id,0 cheque_no,0 cheque_dt,tm.trans_type_id trans_type_id,tm.balance balance,0 dist_id,0 cust_id,tm.status status,0 dist_name,0 cust_name ");
			sqlQuery.append("from transactions_master tm where tm.self_bank_id=? and tm.trans_dt between ? and ? and status='O' and trans_type_id=0 and trans_pay_type_id=0) A ");
			sqlQuery.append("order by A.trans_id");
			
			bankBeanList = jdbcTemplate.query(sqlQuery.toString(), new Object[] {bankData.getBankId(), new java.sql.Date(sdf.parse(bankData.getFromDate()).getTime()), new java.sql.Date(sdf.parse(bankData.getToDate()).getTime()),
								bankData.getBankId(), new java.sql.Date(sdf.parse(bankData.getFromDate()).getTime()), new java.sql.Date(sdf.parse(bankData.getToDate()).getTime()),
								bankData.getBankId(), new java.sql.Date(sdf.parse(bankData.getFromDate()).getTime()), new java.sql.Date(sdf.parse(bankData.getToDate()).getTime()),
								bankData.getBankId(), new java.sql.Date(sdf.parse(bankData.getFromDate()).getTime()), new java.sql.Date(sdf.parse(bankData.getToDate()).getTime())}, new RowMapper<BankBean>() {
				 
	            @Override
	            public BankBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	BankBean bankBean = new BankBean();
	            	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
	    			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	            	try{
	            		bankBean.setTransDate(sdf2.format(sdf1.parse(rs.getString("A.trans_dt"))));
	            	}catch(Exception e){
	            		bankBean.setTransDate(rs.getString("A.trans_dt"));
	            	}
	            	if(rs.getString("trans_pay_type_id").equals("1")){
	            		bankBean.setMode("Cash");
	            	}else if(rs.getString("trans_pay_type_id").equals("2")){
	            		bankBean.setMode("Cheque");
	            	}
	            	
	            	if(rs.getString("trans_type_id").equals("1")){
	            		bankBean.setParticulars("To "+ rs.getString("A.dist_name"));
	            		bankBean.setDeposit("-");
		            	bankBean.setWithdrawl(rs.getString("A.trans_amt"));
	            	}else if(rs.getString("trans_type_id").equals("2")){
            			bankBean.setParticulars("By "+ rs.getString("A.cust_name"));
	            		bankBean.setDeposit(rs.getString("A.trans_amt"));
		            	bankBean.setWithdrawl("-");
	            	}else if(rs.getString("trans_type_id").equals("0") && rs.getString("status").equals("O")){
	            		bankBean.setParticulars("Opening Balance");
	            		bankBean.setDeposit("-");
		            	bankBean.setWithdrawl("-");
	            	}
	            	
	            	if(rs.getString("cheque_no") != null && !rs.getString("cheque_no").equals("")){
	            		bankBean.setChequeNo(rs.getString("cheque_no"));
	            	}else{
	            		bankBean.setChequeNo("");
	            	}
	            	
	            	bankBean.setBalance(rs.getString("A.balance"));

	            	return bankBean;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return bankBeanList;
	}
	
	public List<BankBean> fetchPartyBankList(){
		
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select b.bank_id,b.bank_name,b.address,b.phone_no,b.bank_code,b.bank_type_id from bank_details b where b.bank_type_id=(select bank_type_id from bank_type where bank_type='Party') order by bank_code";
			bankBeanList = jdbcTemplate.query(sqlQuery, new RowMapper<BankBean>() {
				 
	            @Override
	            public BankBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	BankBean bankBean = new BankBean();
	            	
	            	bankBean.setBankId(rs.getInt("bank_id"));
	            	bankBean.setBankName(rs.getString("bank_name"));
	            	bankBean.setAddress(rs.getString("address"));
	            	bankBean.setPhoneNo(rs.getString("phone_no"));
	            	bankBean.setBankCode(rs.getString("bank_code"));
	            	bankBean.setBankTypeId(rs.getInt("bank_type_id"));

	            	return bankBean;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return bankBeanList;
	}
	
	public List<BankBean> fetchSelfBankList(){
		
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select b.bank_id,b.bank_name,b.address,b.phone_no,b.bank_code,b.bank_type_id from bank_details b where b.bank_type_id=(select bank_type_id from bank_type where bank_type='Self') order by bank_code";
			bankBeanList = jdbcTemplate.query(sqlQuery, new RowMapper<BankBean>() {
				 
	            @Override
	            public BankBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	BankBean bankBean = new BankBean();
	            	
	            	bankBean.setBankId(rs.getInt("bank_id"));
	            	bankBean.setBankName(rs.getString("bank_name"));
	            	bankBean.setAddress(rs.getString("address"));
	            	bankBean.setPhoneNo(rs.getString("phone_no"));
	            	bankBean.setBankCode(rs.getString("bank_code"));
	            	bankBean.setBankTypeId(rs.getInt("bank_type_id"));

	            	return bankBean;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return bankBeanList;
	}
	
	public int insertBank(BankBean bankForm){
		int result = 0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			int count=0;
			
			String bankCode = "B00" + getBankId();
						
			if(bankForm != null){
				
				String sqlQuery = "select count(*) from bank_details where bank_name=? and bank_type_id=?";
				
				count = jdbcTemplate.queryForObject(sqlQuery,new Object[]{bankForm.getBankName(),bankForm.getBankTypeId()}, Integer.class);
				
				System.out.println("count="+count);
				if(count > 0){
					result = -1;
					System.out.println("found duplicate");
				}else{
					System.out.println("Inside else for insert");
					sqlQuery = "insert into bank_details(bank_code,bank_name,address,phone_no,opening_bal,bank_type_id) values(?,?,?,?,?,?)";
					
					result = jdbcTemplate.update(sqlQuery, new Object[]{bankCode,bankForm.getBankName(),
							bankForm.getAddress(),bankForm.getPhoneNo(), bankForm.getOpeningBal(), bankForm.getBankTypeId()});
					
					sqlQuery = "select max(bank_id) bank_id from bank_details";
					bankBeanList = jdbcTemplate.query(sqlQuery, new RowMapper<BankBean>() {
						 
			            @Override
			            public BankBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
			            	BankBean bankBean = new BankBean();
			            	
			            	bankBean.setBankId(rs.getInt("bank_id"));

			            	return bankBean;
			            }
			        });
					
					sqlQuery = "insert into transactions_master(trans_dt,remarks,self_bank_id,balance,trans_type_id,trans_pay_type_id,status) values(curdate(),?,?,?,0,0,'O')";
					
					result = jdbcTemplate.update(sqlQuery, new Object[]{"Opening Balance",bankBeanList.get(0).getBankId(), bankForm.getOpeningBal()});
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateBank(BankBean bankForm){
		int result=0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			if(bankForm != null){
				if(bankForm.getBankId() != 0){
					
					String sqlQuery = "update bank_details set bank_name=?,address=?,phone_no=?,bank_type_id=?,opening_bal=? where bank_id=?";
					
					result = jdbcTemplate.update(sqlQuery, new Object[]{bankForm.getBankName(),bankForm.getAddress(),bankForm.getPhoneNo(),bankForm.getBankTypeId(),bankForm.getOpeningBal(),bankForm.getBankId()});
					
					sqlQuery = "select count(*) count from transactions_master where remarks='Opening Balance' and trans_id=(select ifnull(max(trans_id),0) from transactions_master where self_bank_id=?)";
					bankBeanList = jdbcTemplate.query(sqlQuery, new Object[]{bankForm.getBankId()}, new RowMapper<BankBean>() {
						 
			            @Override
			            public BankBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
			            	BankBean bankBean = new BankBean();
			            	
			            	bankBean.setBankId(rs.getInt("count"));

			            	return bankBean;
			            }
			        });
					
					if(bankBeanList.size() > 0 && bankBeanList.get(0).getBankId() > 0){
						sqlQuery = "update transactions_master set balance=? where trans_id=(select ifnull(max(trans_id),0) from transactions_master where self_bank_id=?)";
						
						result = jdbcTemplate.update(sqlQuery, new Object[]{bankForm.getOpeningBal(),bankForm.getBankId()});
					}else{
						sqlQuery = "insert into transactions_master(trans_dt,remarks,self_bank_id,balance,trans_type_id,trans_pay_type_id,status) values(curdate(),?,?,?,0,0,'O')";
						
						result = jdbcTemplate.update(sqlQuery, new Object[]{"Opening Balance",bankForm.getBankId(), bankForm.getOpeningBal()});
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteBank(String bankId){
		int result = 0;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sqlQuery = "select count(*) count from transactions_master where bank_id=? or self_bank_id=?";
			bankBeanList = jdbcTemplate.query(sqlQuery, new Object[]{bankId, bankId}, new RowMapper<BankBean>() {
				 
	            @Override
	            public BankBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	BankBean bankBean = new BankBean();
	            	
	            	bankBean.setBankId(rs.getInt("count"));

	            	return bankBean;
	            }
	        });
			
			if(bankBeanList.size() > 0 && bankBeanList.get(0).getBankId() > 0){
				result = -1;
				System.out.println("Bank cannot be deleted as transactions are present "+bankId);
			}else if(bankBeanList.size() > 0 && bankBeanList.get(0).getBankId() == 0){
				sqlQuery = "delete from bank_details where bank_id=?";
				
				result = jdbcTemplate.update(sqlQuery, new Object[]{bankId});
				
				if(result > 0){
					System.out.println("Bank deleted successfully.");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int getBankId(){
		Integer bankId=0;
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			String sqlQuery = "SELECT AUTO_INCREMENT bankId FROM information_schema.`TABLES` WHERE `table_schema` = 'pharmadb' AND `table_name` = 'bank_details';";
			
			bankId = jdbcTemplate.queryForObject(sqlQuery, Integer.class);
			System.out.println("custId"+bankId);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return bankId;
	}
}
