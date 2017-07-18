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

@Service("purPayService")
@Transactional
public class PurchasePayService {
	@Autowired
	MedicalAppConfig appConfig;
	
	public int insertPurTransaction(TransactionBean transData){
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
				StringBuilder sqlQuery;

				Iterator<TransactionBean> itrList = transData.getBillList().iterator();
				
				if(transData.getBillList().size() > 1){
					long transId = 0;
					sqlQuery = new StringBuilder();
					sqlQuery.append("insert into transactions_master(trans_type_id, trans_dt, trans_amt, trans_pay_type_id, self_bank_id, cheque_no, cheque_dt, amt_adjusted, remarks, status,balance) ");
					sqlQuery.append("values(?,?,?,?,?,?,?,?,?,?,?)");
					
					if(transData.getCheqDate() != null && !transData.getCheqDate().equals("")){
						jdbcTemplate.update(sqlQuery.toString() , new Object[]{"1", new java.sql.Date(sdf.parse(transData.getTransDate()).getTime()),transData.getTransAmt(),transData.getTransPayType(),transData.getBankId(), transData.getCheqNo()
							, new java.sql.Date(sdf.parse(transData.getCheqDate()).getTime()), transData.getAmtAdj(), transData.getRemarks(), "P", (balance - transData.getTransAmt())});
					}else{
						jdbcTemplate.update(sqlQuery.toString() , new Object[]{"1", new java.sql.Date(sdf.parse(transData.getTransDate()).getTime()),transData.getTransAmt(),transData.getTransPayType(),null, null
							, null, transData.getAmtAdj(), transData.getRemarks(), "P", 0});
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
					sqlQuery.append("insert into transactions_child(pur_id, dist_id, trans_id, amt_paid, bal_amt) ");
					sqlQuery.append("values(?,?,?,?,?)");
					
					int count = 1;
					
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
						
						result = jdbcTemplate.update(sqlQuery.toString() , new Object[]{itrBean.getPurId(), transData.getDistId(), transId, amtPaid, 0});
						
						StringBuilder sqlQuery1 = new StringBuilder();
						sqlQuery1.append("update transactions_master set status='P' where trans_id in (select trans_id from transactions_child where pur_id=? and dist_id=?)");
						
						jdbcTemplate.update(sqlQuery1.toString() , new Object[]{itrBean.getPurId(), transData.getDistId()});
					}
				}else if(transData.getBillList().size() == 1){
					long transId = 0;
					double balAmt = transData.getBalAmt();
					String status = "P";
					
					if(balAmt > 0.0){
						status = "U";
					}
					
					sqlQuery = new StringBuilder();
					sqlQuery.append("insert into transactions_master(trans_type_id, trans_dt, trans_amt, trans_pay_type_id, self_bank_id, cheque_no, cheque_dt, amt_adjusted, remarks, status, balance) ");
					sqlQuery.append("values(?,?,?,?,?,?,?,?,?,?,?)");
					
					if(transData.getCheqDate() != null && !transData.getCheqDate().equals("")){
						jdbcTemplate.update(sqlQuery.toString() , new Object[]{"1", new java.sql.Date(sdf.parse(transData.getTransDate()).getTime()),transData.getTransAmt(),transData.getTransPayType(),transData.getBankId(), transData.getCheqNo()
							, new java.sql.Date(sdf.parse(transData.getCheqDate()).getTime()), transData.getAmtAdj(), transData.getRemarks(), status, (balance - transData.getTransAmt())});
					}else{
						jdbcTemplate.update(sqlQuery.toString() , new Object[]{"1", new java.sql.Date(sdf.parse(transData.getTransDate()).getTime()),transData.getTransAmt(),transData.getTransPayType(),null, null
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
					sqlQuery.append("insert into transactions_child(pur_id, dist_id, trans_id, amt_paid, bal_amt) ");
					sqlQuery.append("values(?,?,?,?,?)");
					
					if(itrList.hasNext()){
						TransactionBean itrBean = itrList.next();
						double addedAmt = transData.getTransAmt();
						
						result = jdbcTemplate.update(sqlQuery.toString() , new Object[]{itrBean.getPurId(), transData.getDistId(), transId, addedAmt, balAmt});

						if(status.endsWith("P")){
							sqlQuery = new StringBuilder();
							sqlQuery.append("update transactions_master set status='P' where trans_id in (select trans_id from transactions_child where pur_id=? and dist_id=?)");
							
							jdbcTemplate.update(sqlQuery.toString() , new Object[]{itrBean.getPurId(), transData.getDistId()});
						}else{
							sqlQuery = new StringBuilder();
							sqlQuery.append("update transactions_master set status='P' where trans_id in (select trans_id from transactions_child where trans_id <> ? and pur_id=? and dist_id=?)");
							
							jdbcTemplate.update(sqlQuery.toString() , new Object[]{transId, itrBean.getPurId(), transData.getDistId()});
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public List<TransactionBean> fetchBillDetails(String distId){
		List<TransactionBean> transBeanList = new ArrayList<TransactionBean>();
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			if(distId != null && !distId.equals("") && !distId.equalsIgnoreCase("null")){
				StringBuilder sqlQuery = new StringBuilder();
				sqlQuery.append("select p.pur_id,p.bill_no,p.net_amt,p.pur_dt,tc.amt_paid amt_paid,tc.bal_amt bal_amt from purchase p, ");
				sqlQuery.append("(select pur_id,amt_paid,bal_amt from transactions_child where dist_id=? and trans_id in ");
				sqlQuery.append("(select trans_id from transactions_master where status='U')) tc where p.pur_id=tc.pur_id ");
				sqlQuery.append("union ");
				sqlQuery.append("select p.pur_id,p.bill_no,p.net_amt,p.pur_dt,0 amt_paid,0 bal_amt from purchase p ");
				sqlQuery.append("where p.dist_id = ? and p.status = 'ST' and p.pur_id not in (select distinct pur_id from ");
				sqlQuery.append("transactions_child where dist_id=?)");
				
				transBeanList = jdbcTemplate.query(sqlQuery.toString(), new Object[]{distId, distId, distId}, new RowMapper<TransactionBean>() {
					 
		            @Override
		            public TransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
		            	TransactionBean transBean = new TransactionBean();
		            	
		            	transBean.setPurId(rs.getLong("pur_id"));
		            	transBean.setBillNo(rs.getString("bill_no"));
		            	transBean.setBillDate(rs.getString("pur_dt"));
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
}
