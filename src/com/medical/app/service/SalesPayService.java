package com.medical.app.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medical.app.configurations.MedicalAppConfig;
import com.medical.app.model.TransactionBean;

@Service("salPayService")
@Transactional
public class SalesPayService {
	@Autowired
	MedicalAppConfig appConfig;
	
	public int insertSalTransaction(TransactionBean transData){
		int result = 0;
		double balance = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			String sql = "select ifNull(balance,0) balance from transactions_master where trans_id = (select max(trans_id) from transactions_master where self_bank_id=?)";
			
			List<TransactionBean> balList = jdbcTemplate.query(sql, new Object[]{transData.getSelfBankId()}, new RowMapper<TransactionBean>() {
				 
	            @Override
	            public TransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	TransactionBean transBean = new TransactionBean();
	            	
	            	transBean.setBalance(rs.getDouble("balance"));
					
	                return transBean;
	            }
	        });
			
			if(balList.size() > 0){
				balance = balList.get(0).getBalance();
			}
			
			if(transData != null){
				StringBuilder sqlQuery;

				Iterator<TransactionBean> itrList = transData.getBillList().iterator();
				
				if(transData.getBillList().size() > 1){
					long transId = 0;
					sqlQuery = new StringBuilder();
					sqlQuery.append("insert into transactions_master(trans_type_id, trans_dt, trans_amt, trans_pay_type_id, bank_id, self_bank_id, cheque_no, cheque_dt, amt_adjusted, remarks, status,balance) ");
					sqlQuery.append("values(?,?,?,?,?,?,?,?,?,?,?,?)");
					
					if(transData.getCheqDate() != null && !transData.getCheqDate().equals("")){
						jdbcTemplate.update(sqlQuery.toString() , new Object[]{"2", new java.sql.Date(sdf.parse(transData.getTransDate()).getTime()),transData.getTransAmt(),transData.getTransPayType(),transData.getBankId(),transData.getSelfBankId(), transData.getCheqNo()
							, new java.sql.Date(sdf.parse(transData.getCheqDate()).getTime()), transData.getAmtAdj(), transData.getRemarks(), "P",(balance + transData.getTransAmt())});
					}else{
						jdbcTemplate.update(sqlQuery.toString() , new Object[]{"2", new java.sql.Date(sdf.parse(transData.getTransDate()).getTime()),transData.getTransAmt(),transData.getTransPayType(),null,null, null
							, null, transData.getAmtAdj(), transData.getRemarks(), "P",0});
					}
					
					
					List<TransactionBean> transBeanList = jdbcTemplate.query("select max(trans_id) trans_id from transactions_master", new Object[]{}, new RowMapper<TransactionBean>() {
						 
			            @Override
			            public TransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
			            	TransactionBean transBean = new TransactionBean();
			            	
			            	transBean.setTransId(rs.getLong("trans_id"));
							
			                return transBean;
			            }
			             
			        });
					
					transId = transBeanList.get(0).getTransId();
					
					sqlQuery = new StringBuilder();
					sqlQuery.append("insert into transactions_child(sales_id, cust_id, trans_id, amt_paid, bal_amt) ");
					sqlQuery.append("values(?,?,?,?,?)");
					
					int count  = 1;
					
					while(itrList.hasNext()){
						TransactionBean itrBean = itrList.next();
						double amtPaid = 0;
						if(itrBean.getBalAmt() > 0){
							amtPaid = itrBean.getBalAmt();
						}else{
							amtPaid = itrBean.getBillAmt();
						}
						
						if(count == 1){
							if(transData.getAmtAdj() > 0){
								amtPaid = amtPaid - transData.getAmtAdj();
							}
						}
						
						count++;
						
						
						result = jdbcTemplate.update(sqlQuery.toString() , new Object[]{itrBean.getSalesId(), transData.getCustId(), transId, amtPaid, 0});
						
						StringBuilder sqlQuery1 = new StringBuilder();
						sqlQuery1.append("update transactions_master set status='P' where trans_id in (select trans_id from transactions_child where sales_id=? and cust_id=?)");
							
						jdbcTemplate.update(sqlQuery1.toString() , new Object[]{itrBean.getSalesId(), transData.getCustId()});
						
					}
				}else if(transData.getBillList().size() == 1){
					long transId = 0;
					double balAmt = transData.getBalAmt();
					String status = "P";
					
					if(balAmt > 0.0){
						status = "U";
					}
					
					sqlQuery = new StringBuilder();
					sqlQuery.append("insert into transactions_master(trans_type_id, trans_dt, trans_amt, trans_pay_type_id, bank_id, self_bank_id, cheque_no, cheque_dt, amt_adjusted, remarks, status,balance) ");
					sqlQuery.append("values(?,?,?,?,?,?,?,?,?,?,?,?)");
					
					if(transData.getCheqDate() != null && !transData.getCheqDate().equals("")){
						jdbcTemplate.update(sqlQuery.toString() , new Object[]{"2", new java.sql.Date(sdf.parse(transData.getTransDate()).getTime()),transData.getTransAmt(),transData.getTransPayType(),transData.getBankId(),transData.getSelfBankId(), transData.getCheqNo()
							, new java.sql.Date(sdf.parse(transData.getCheqDate()).getTime()), transData.getAmtAdj(), transData.getRemarks(), status, (balance + transData.getTransAmt())});
					}else{
						jdbcTemplate.update(sqlQuery.toString() , new Object[]{"2", new java.sql.Date(sdf.parse(transData.getTransDate()).getTime()),transData.getTransAmt(),transData.getTransPayType(),null,null, null
							, null, transData.getAmtAdj(), transData.getRemarks(), status, 0});
					}
					
					
					List<TransactionBean> transBeanList = jdbcTemplate.query("select max(trans_id) trans_id from transactions_master", new Object[]{}, new RowMapper<TransactionBean>() {
						 
			            @Override
			            public TransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
			            	TransactionBean transBean = new TransactionBean();
			            	
			            	transBean.setTransId(rs.getLong("trans_id"));
							
			                return transBean;
			            }
			             
			        });
					
					transId = transBeanList.get(0).getTransId();
					
					sqlQuery = new StringBuilder();
					sqlQuery.append("insert into transactions_child(sales_id, cust_id, trans_id, amt_paid, bal_amt) ");
					sqlQuery.append("values(?,?,?,?,?)");
					
					if(itrList.hasNext()){
						TransactionBean itrBean = itrList.next();
						double addedAmt = itrBean.getAmtPaid();
						
						result = jdbcTemplate.update(sqlQuery.toString() , new Object[]{itrBean.getSalesId(), transData.getCustId(), transId, addedAmt , balAmt});
						
						if(status.endsWith("P")){
							sqlQuery = new StringBuilder();
							sqlQuery.append("update transactions_master set status='P' where trans_id in (select trans_id from transactions_child where sales_id=? and cust_id=?)");
							
							jdbcTemplate.update(sqlQuery.toString() , new Object[]{itrBean.getSalesId(), transData.getCustId()});
						}else{
							sqlQuery = new StringBuilder();
							sqlQuery.append("update transactions_master set status='P' where trans_id in (select trans_id from transactions_child where trans_id <> ? and sales_id=? and cust_id=?)");
							
							jdbcTemplate.update(sqlQuery.toString() , new Object[]{transId, itrBean.getSalesId(), transData.getCustId()});
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public List<TransactionBean> fetchBillDetails(String custId){
		List<TransactionBean> transBeanList = new ArrayList<TransactionBean>();
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			if(custId != null && !custId.equals("") && !custId.equalsIgnoreCase("null")){
				StringBuilder sqlQuery = new StringBuilder();
				sqlQuery.append("select s.sales_id,s.bill_no,s.net_amt,s.sales_dt,tc.amt_paid amt_paid,tc.bal_amt bal_amt from sales s, ");
				sqlQuery.append("(select sales_id,amt_paid,bal_amt from transactions_child where cust_id=? and trans_id in ");
				sqlQuery.append("(select trans_id from transactions_master where status='U')) tc where s.sales_id=tc.sales_id ");
				sqlQuery.append("union ");
				sqlQuery.append("select s.sales_id,s.bill_no,s.net_amt,s.sales_dt,0 amt_paid,0 bal_amt from sales s ");
				sqlQuery.append("where s.cust_id = ? and s.status = 'ST' and s.sales_id not in (select distinct sales_id from ");
				sqlQuery.append("transactions_child where cust_id=?)");
				
				transBeanList = jdbcTemplate.query(sqlQuery.toString(), new Object[]{custId, custId, custId}, new RowMapper<TransactionBean>() {
					 
		            @Override
		            public TransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
		            	TransactionBean transBean = new TransactionBean();
		            	
		            	transBean.setSalesId(rs.getLong("sales_id"));
		            	transBean.setBillNo(rs.getString("bill_no"));
		            	transBean.setBillDate(rs.getString("sales_dt"));
		            	transBean.setBillAmt(rs.getDouble("net_amt"));
 		            	transBean.setBalAmt(rs.getDouble("bal_amt"));
		            	transBean.setAmtPaid(rs.getDouble("amt_paid"));
						
		                return transBean;
		            }
		             
		        });
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return transBeanList;
	}
	
	public int addCashDeposit(TransactionBean transData){
		int result = 0;
		double balance = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			String sql = "select ifNull(balance,0) balance from transactions_master where trans_id = (select max(trans_id) from transactions_master where self_bank_id=?)";
			
			List<TransactionBean> balList = jdbcTemplate.query(sql, new Object[]{transData.getBankId()}, new RowMapper<TransactionBean>() {
				 
	            @Override
	            public TransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	TransactionBean transBean = new TransactionBean();
	            	
	            	transBean.setBalance(rs.getDouble("balance"));
					
	                return transBean;
	            }
	        });
			
			if(balList.size() > 0){
				balance = balList.get(0).getBalance();
			}
			
			if(transData != null){
				StringBuilder sqlQuery = new StringBuilder();
				sqlQuery.append("insert into transactions_master(trans_type_id, trans_dt, trans_amt, trans_pay_type_id, self_bank_id, status, balance) ");
				sqlQuery.append("values(?,?,?,?,?,?,?)");
				
				result = jdbcTemplate.update(sqlQuery.toString() , new Object[]{"2", new java.sql.Date(sdf.parse(transData.getTransDate()).getTime()), transData.getTransAmt(), "1", transData.getBankId(), "P", (balance + transData.getTransAmt())});

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
