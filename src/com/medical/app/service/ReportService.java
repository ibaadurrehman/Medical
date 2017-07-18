package com.medical.app.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medical.app.configurations.MedicalAppConfig;
import com.medical.app.model.CustomerBean;
import com.medical.app.model.DistributorBean;
import com.medical.app.model.ProductBean;
import com.medical.app.model.ReportBean;
import com.medical.app.model.TransactionBean;

@Service("reportService")
@Transactional
public class ReportService {
	@Autowired
	MedicalAppConfig appConfig;
	
	public List<ProductBean> fetchAllStock(){
		List<ProductBean> prodBeanList = new ArrayList<ProductBean>();
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("select p.prod_id,p.prod_code,p.prod_name,m.man_id,m.man_name,");
			sqlQuery.append("p.pckg_size,pd.mrp,pd.rate,pd.cur_stock from product p, product_details pd, manufacturer m ");
			sqlQuery.append("where p.prod_id = pd.prod_id and p.man_id = m.man_id and pd.cur_stock > 0 ");
			sqlQuery.append("order by p.prod_name, p.pckg_size");
			
			prodBeanList = jdbcTemplate.query(sqlQuery.toString(), new RowMapper<ProductBean>() {
				 
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	
	            	prodBean.setProdId(rs.getString("p.prod_id"));
	            	prodBean.setProdCode(rs.getString("p.prod_code"));
	            	prodBean.setProdName(rs.getString("p.prod_name"));
	            	prodBean.setManId(rs.getString("m.man_id"));
	            	prodBean.setManName(rs.getString("m.man_name"));
	            	prodBean.setPckSize(rs.getString("p.pckg_size"));
	            	prodBean.setMrp(rs.getDouble("pd.mrp"));
	            	prodBean.setRate(rs.getDouble("pd.rate"));
	            	prodBean.setStock(rs.getInt("pd.cur_stock"));
	            	
	            	return prodBean;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return prodBeanList;
	}
	
	public List<ProductBean> fetchStock(ProductBean prodData){
		List<ProductBean> prodBeanList = new ArrayList<ProductBean>();
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			List<String> dataList = new ArrayList<String>();
			
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("select p.prod_id,p.prod_code,p.prod_name,m.man_id,m.man_name,");
			sqlQuery.append("p.pckg_size,pd.mrp,pd.rate,pd.cur_stock from product p, product_details pd, manufacturer m ");
			sqlQuery.append("where p.prod_id = pd.prod_id and p.man_id = m.man_id and pd.cur_stock > 0 ");
			if(prodData.getManId() != null && !prodData.getManId().equals("")){
				sqlQuery.append(" and p.man_id=? ");
				dataList.add(prodData.getManId());
			}
			if(prodData.getProdName() != null && !prodData.getProdName().equals("")){
				sqlQuery.append(" and p.prod_name=? ");
				dataList.add(prodData.getProdName());
			}
			if(prodData.getPckSize() != null && !prodData.getPckSize().equals("")){
				sqlQuery.append(" and p.pckg_size=? ");
				dataList.add(prodData.getPckSize());
			}
			
			sqlQuery.append("order by p.prod_name, p.pckg_size");
			
			prodBeanList = jdbcTemplate.query(sqlQuery.toString(), dataList.toArray(), new RowMapper<ProductBean>() {
				 
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	
	            	prodBean.setProdId(rs.getString("p.prod_id"));
	            	prodBean.setProdCode(rs.getString("p.prod_code"));
	            	prodBean.setProdName(rs.getString("p.prod_name"));
	            	prodBean.setManId(rs.getString("m.man_id"));
	            	prodBean.setManName(rs.getString("m.man_name"));
	            	prodBean.setPckSize(rs.getString("p.pckg_size"));
	            	prodBean.setMrp(rs.getDouble("pd.mrp"));
	            	prodBean.setRate(rs.getDouble("pd.rate"));
	            	prodBean.setStock(rs.getInt("pd.cur_stock"));
	            	
	            	return prodBean;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return prodBeanList;
	}
	
	public List<TransactionBean> fetchAllDistBal(ReportBean reptData){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String startDate = "01/04/"+reptData.getFinYear().split("-")[0];
		String endDate = "31/03/"+reptData.getFinYear().split("-")[1];
		
		List<TransactionBean> transBeanList = new ArrayList<TransactionBean>();
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select F.dist_id,F.dist_name,F.open_bal,F.billing,F.payment,F.amt_adjusted,F.balance from ");
			strQuery.append("(select D.dist_id dist_id,D.dist_name dist_name,D.open_bal open_bal,C.billing billing,B.payment payment,B.amt_adjusted amt_adjusted,((D.open_bal + C.billing) - (B.payment+B.amt_adjusted)) balance from ");
			strQuery.append("(select sum(A.trans_amt) payment,sum(A.amt_adjusted) amt_adjusted ,A.dist_id from ");
			strQuery.append("(select distinct tm.trans_amt,tm.amt_adjusted ,tm.trans_id, tc.dist_id from transactions_master tm, transactions_child tc where trans_dt between ? and ? and tm.trans_id in (");
			strQuery.append("select distinct trans_id FROM transactions_child where dist_id is not null and (cust_id is null or cust_id = '' )) ");
			strQuery.append("and tm.trans_id=tc.trans_id)A group by A.dist_id) B,(");
			strQuery.append("SELECT sum(net_amt) billing,dist_id FROM purchase where status='ST' and pur_dt between ? and ? group by dist_id) C, ");
			strQuery.append("(select ifnull(opening_bal,0) open_bal,dist_id,dist_name from distributor) D ");
			strQuery.append("where B.dist_id = C.dist_id and C.dist_id = D.dist_id and D.dist_id = B.dist_id ");
			strQuery.append("union ");
			strQuery.append("select d.dist_id dist_id,d.dist_name dist_name,d.opening_bal open_bal,sum(p.net_amt) billing,0 payment,0 amt_adjusted,(d.opening_bal + sum(p.net_amt)) balance  from purchase p, distributor d ");
			strQuery.append("where p.dist_id=d.dist_id and p.status = 'ST' and p.pur_id not in (select distinct ifnull(pur_id,0) from transactions_child) and p.pur_dt between ? and ? group by d.dist_id)F ");
			strQuery.append("order by F.dist_name");
			
			transBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()),
				new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()),new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime())} ,new RowMapper<TransactionBean>() {
				int count=1; 
	            @Override
	            public TransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	TransactionBean transBean = new TransactionBean();
	            	
	            	transBean.setCount(count++);
	            	transBean.setDistId(rs.getString("F.dist_id"));
	            	transBean.setName(rs.getString("F.dist_name"));
					transBean.setOpeningBal(rs.getDouble("F.open_bal"));
					transBean.setBilling(rs.getDouble("F.billing"));
					transBean.setPayment(rs.getDouble("F.payment"));
					transBean.setAmtAdj(rs.getDouble("F.amt_adjusted"));
					transBean.setBalance(rs.getDouble("F.balance"));
					
	                return transBean;
	            }
	             
	        });
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return transBeanList;
	}
	
	public List<TransactionBean> fetchAllCustBal(ReportBean reptData){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String startDate = "01/04/"+reptData.getFinYear().split("-")[0];
		String endDate = "31/03/"+reptData.getFinYear().split("-")[1];
		
		List<TransactionBean> transBeanList = new ArrayList<TransactionBean>();
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select F.cust_id,F.cust_name,F.open_bal,F.billing,F.payment,F.amt_adjusted,F.balance from ");
			strQuery.append("(select D.cust_id cust_id,D.cust_name cust_name,D.open_bal open_bal,C.billing billing,B.payment payment,B.amt_adjusted amt_adjusted,((D.open_bal + C.billing) - (B.payment+B.amt_adjusted)) balance from ");
			strQuery.append("(select sum(A.trans_amt) payment,sum(A.amt_adjusted) amt_adjusted,A.cust_id from ");
			strQuery.append("(select distinct tm.trans_amt,tm.amt_adjusted,tm.trans_id, tc.cust_id from transactions_master tm, transactions_child tc where trans_dt between ? and ? and tm.trans_id in (");
			strQuery.append("select distinct trans_id FROM transactions_child where cust_id is not null and (dist_id is null or dist_id = '' )) ");
			strQuery.append("and tm.trans_id=tc.trans_id)A group by A.cust_id) B,(");
			strQuery.append("SELECT sum(net_amt) billing,cust_id FROM sales where status='ST' and sales_dt between ? and ? group by cust_id) C, ");
			strQuery.append("(select ifnull(opening_bal,0) open_bal,cust_id,cust_name from customer) D ");
			strQuery.append("where B.cust_id = C.cust_id and C.cust_id = D.cust_id and D.cust_id = B.cust_id ");
			strQuery.append("union ");
			strQuery.append("select d.cust_id cust_id,d.cust_name cust_name,d.opening_bal open_bal,sum(p.net_amt) billing,0 payment,0 amt_adjusted,(d.opening_bal + sum(p.net_amt)) balance  from sales p, customer d ");
			strQuery.append("where p.cust_id=d.cust_id and p.status='ST' and p.sales_id not in (select distinct ifnull(sales_id,0) from transactions_child) and p.sales_dt between ? and ? group by d.cust_id)F ");
			strQuery.append("order by F.cust_name");
			
			transBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()),
				new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()),new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime())} ,new RowMapper<TransactionBean>() {
				int count=1; 
	            @Override
	            public TransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	TransactionBean transBean = new TransactionBean();
	            	
	            	transBean.setCount(count++);
	            	transBean.setDistId(rs.getString("F.cust_id"));
	            	transBean.setName(rs.getString("F.cust_name"));
					transBean.setOpeningBal(rs.getDouble("F.open_bal"));
					transBean.setBilling(rs.getDouble("F.billing"));
					transBean.setPayment(rs.getDouble("F.payment"));
					transBean.setAmtAdj(rs.getDouble("F.amt_adjusted"));
					transBean.setBalance(rs.getDouble("F.balance"));
					
	                return transBean;
	            }
	             
	        });
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return transBeanList;
	}
	
	public List<TransactionBean> fetchDistBal(ReportBean reptData){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String startDate = "01/04/"+reptData.getFinYear().split("-")[0];
		String endDate = "31/03/"+reptData.getFinYear().split("-")[1];
		
		List<TransactionBean> transBeanList = new ArrayList<TransactionBean>();
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select F.dist_id,F.dist_name,F.open_bal,F.billing,F.payment,F.amt_adjusted,F.balance from ");
			strQuery.append("(select E.dist_id dist_id,E.dist_name dist_name,E.open_bal open_bal,E.billing billing,E.payment payment,E.amt_adjusted amt_adjusted,E.balance balance from (");
			strQuery.append("select D.dist_id dist_id,D.dist_name dist_name,D.open_bal open_bal,C.billing billing,B.payment payment, B.amt_adjusted amt_adjusted,((D.open_bal + C.billing) - (B.payment+B.amt_adjusted)) balance from ");
			strQuery.append("(select sum(A.trans_amt) payment,sum(A.amt_adjusted) amt_adjusted ,A.dist_id from ");
			strQuery.append("(select distinct tm.trans_amt,tm.amt_adjusted ,tm.trans_id, tc.dist_id from transactions_master tm, transactions_child tc where trans_dt between ? and ? and tm.trans_id in (");
			strQuery.append("select distinct trans_id FROM transactions_child where dist_id is not null and (cust_id is null or cust_id = '' )) ");
			strQuery.append("and tm.trans_id=tc.trans_id)A group by A.dist_id) B,(");
			strQuery.append("SELECT sum(net_amt) billing,dist_id FROM purchase where status='ST' and pur_dt between ? and ? group by dist_id) C, ");
			strQuery.append("(select ifnull(opening_bal,0) open_bal,dist_id,dist_name from distributor) D ");
			strQuery.append("where B.dist_id = C.dist_id and C.dist_id = D.dist_id and D.dist_id = B.dist_id)E ");
			strQuery.append("where E.dist_id=? ");
			strQuery.append("union ");
			strQuery.append("select d.dist_id dist_id,d.dist_name dist_name,d.opening_bal open_bal,sum(p.net_amt) billing,0 payment,0 amt_adjusted,(d.opening_bal + sum(p.net_amt)) balance  from purchase p, distributor d ");
			strQuery.append("where p.dist_id=d.dist_id and p.status='ST' and p.pur_id not in (select distinct ifnull(pur_id,0) from transactions_child) and p.pur_dt between ? and ? and d.dist_id=? group by d.dist_id)F ");
			
			
			transBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()),
				new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()), reptData.getDistId(),
				new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()), reptData.getDistId()} ,new RowMapper<TransactionBean>() {
				
				int count=1; 
	            @Override
	            public TransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	TransactionBean transBean = new TransactionBean();
	            	
	            	transBean.setCount(count++);
	            	transBean.setDistId(rs.getString("F.dist_id"));
	            	transBean.setName(rs.getString("F.dist_name"));
					transBean.setOpeningBal(rs.getDouble("F.open_bal"));
					transBean.setBilling(rs.getDouble("F.billing"));
					transBean.setPayment(rs.getDouble("F.payment"));
					transBean.setAmtAdj(rs.getDouble("F.amt_adjusted"));
					transBean.setBalance(rs.getDouble("F.balance"));
					
	                return transBean;
	            }
	             
	        });
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return transBeanList;
	}
	
	public List<TransactionBean> fetchCustBal(ReportBean reptData){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String startDate = "01/04/"+reptData.getFinYear().split("-")[0];
		String endDate = "31/03/"+reptData.getFinYear().split("-")[1];
		
		List<TransactionBean> transBeanList = new ArrayList<TransactionBean>();
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select F.cust_id,F.cust_name,F.open_bal,F.billing,F.payment,F.amt_adjusted,F.balance from ");
			strQuery.append("(select E.cust_id cust_id,E.cust_name cust_name,E.open_bal open_bal,E.billing billing,E.payment payment,E.amt_adjusted amt_adjusted,E.balance balance from (");
			strQuery.append("select D.cust_id cust_id,D.cust_name cust_name,D.open_bal open_bal,C.billing billing,B.payment payment,B.amt_adjusted amt_adjusted,((D.open_bal + C.billing) - (B.payment+B.amt_adjusted)) balance from ");
			strQuery.append("(select sum(A.trans_amt) payment,sum(A.amt_adjusted) amt_adjusted,A.cust_id from ");
			strQuery.append("(select distinct tm.trans_amt,tm.amt_adjusted,tm.trans_id, tc.cust_id from transactions_master tm, transactions_child tc where trans_dt between ? and ? and tm.trans_id in (");
			strQuery.append("select distinct trans_id FROM transactions_child where cust_id is not null and (dist_id is null or dist_id = '' )) ");
			strQuery.append("and tm.trans_id=tc.trans_id)A group by A.cust_id) B,(");
			strQuery.append("SELECT sum(net_amt) billing,cust_id FROM sales where status='ST' and sales_dt between ? and ? group by cust_id) C, ");
			strQuery.append("(select ifnull(opening_bal,0) open_bal,cust_id,cust_name from customer) D ");
			strQuery.append("where B.cust_id = C.cust_id and C.cust_id = D.cust_id and D.cust_id = B.cust_id)E ");
			strQuery.append("where E.cust_id=? ");
			strQuery.append("union ");
			strQuery.append("select d.cust_id cust_id,d.cust_name cust_name,d.opening_bal open_bal,sum(p.net_amt) billing,0 payment,0 amt_adjusted,(d.opening_bal + sum(p.net_amt)) balance  from sales p, customer d ");
			strQuery.append("where p.cust_id=d.cust_id and p.status='ST' and p.sales_id not in (select distinct ifnull(sales_id,0) from transactions_child) and p.sales_dt between ? and ? and d.cust_id=? group by d.cust_id)F ");
			
			
			transBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()),
				new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()), reptData.getCustId(),
				new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()), reptData.getCustId()} ,new RowMapper<TransactionBean>() {
				int count=1; 
	            @Override
	            public TransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	TransactionBean transBean = new TransactionBean();
	            	
	            	transBean.setCount(count++);
	            	transBean.setDistId(rs.getString("F.cust_id"));
	            	transBean.setName(rs.getString("F.cust_name"));
					transBean.setOpeningBal(rs.getDouble("F.open_bal"));
					transBean.setBilling(rs.getDouble("F.billing"));
					transBean.setPayment(rs.getDouble("F.payment"));
					transBean.setAmtAdj(rs.getDouble("F.amt_adjusted"));
					transBean.setBalance(rs.getDouble("F.balance"));
					
	                return transBean;
	            }
	             
	        });
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return transBeanList;
	}
	
	public List<ReportBean> fetchAllPurReport(ReportBean repData){
		List<ReportBean> repBeanList = new ArrayList<ReportBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select E.dist_id,E.dist_name,E.bill_no,E.pur_dt,E.net_amt,E.amt_paid,E.amt_adjusted from ");
			strQuery.append("(select B.dist_id dist_id,B.dist_name dist_name,B.bill_no bill_no,B.pur_dt pur_dt,B.net_amt net_amt,C.amt_paid amt_paid,C.amt_adjusted amt_adjusted from ");
			strQuery.append("((select p.bill_no,p.pur_id,p.pur_dt,p.net_amt,p.dist_id,d.dist_name from purchase p, distributor d ");
			strQuery.append("where p.dist_id=d.dist_id)B,(select A.pur_id, sum(A.amt_paid) amt_paid, sum(tm.amt_adjusted) amt_adjusted from transactions_master tm, ");
			strQuery.append("(select amt_paid, pur_id, trans_id from transactions_child ");
			strQuery.append("where dist_id is not null and (cust_id is null or cust_id = '' ))A ");
			strQuery.append("where tm.trans_id=A.trans_id group by A.pur_id)C) ");
			strQuery.append("where B.pur_id=C.pur_id and B.pur_dt between ? and ? ");
			strQuery.append("union ");
			strQuery.append("select d.dist_id dist_id,d.dist_name dist_name,p.bill_no bill_no,p.pur_dt pur_dt,p.net_amt net_amt,0 amt_paid,0 amt_adjusted from purchase p, distributor d ");
			strQuery.append("where p.dist_id=d.dist_id and p.pur_id not in (select distinct ifnull(pur_id,0) from transactions_child) and p.pur_dt between ? and ? )E ");
			strQuery.append("order by E.dist_name,E.bill_no");
			
			repBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{new java.sql.Date(sdf.parse(repData.getFromDate()).getTime()),new java.sql.Date(sdf.parse(repData.getToDate()).getTime()), new java.sql.Date(sdf.parse(repData.getFromDate()).getTime()),new java.sql.Date(sdf.parse(repData.getToDate()).getTime())} ,new RowMapper<ReportBean>() {
				int count=1;
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	            @Override
	            public ReportBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ReportBean repBean = new ReportBean();
	            	
	            	repBean.setCount(count++);
	            	repBean.setDistId(rs.getString("E.dist_id"));
	            	repBean.setName(rs.getString("E.dist_name"));
	            	repBean.setBillNo(rs.getString("E.bill_no"));
	            	repBean.setBillDt(sdf1.format(new java.util.Date(rs.getDate("E.pur_dt").getTime())));
	            	repBean.setBillAmt(rs.getDouble("E.net_amt"));
	            	repBean.setPayment(rs.getDouble("E.amt_paid"));

	            	if((rs.getDouble("E.amt_paid") + rs.getDouble("E.amt_adjusted")) == rs.getDouble("E.net_amt")){
	            		repBean.setBalance(0);
	            		repBean.setAdjAmt(rs.getDouble("E.amt_adjusted"));
	            	}else{
	            		repBean.setBalance(rs.getDouble("E.net_amt") - rs.getDouble("E.amt_paid"));
	            		repBean.setAdjAmt(0);
	            	}
	            	
	            	return repBean;
	            }
	             
	        });
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return repBeanList;
	}
	
	public List<ReportBean> fetchAllSalesReport(ReportBean repData){
		List<ReportBean> repBeanList = new ArrayList<ReportBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select E.cust_id,E.cust_name,E.bill_no,E.sales_dt,E.net_amt,E.amt_paid,E.amt_adjusted from ");
			strQuery.append("(select B.cust_id cust_id,B.cust_name cust_name,B.bill_no bill_no,B.sales_dt sales_dt,B.net_amt net_amt,C.amt_paid amt_paid,C.amt_adjusted amt_adjusted from ");
			strQuery.append("((select p.bill_no,p.sales_id,p.sales_dt,p.net_amt,p.cust_id,d.cust_name from sales p, customer d ");
			strQuery.append("where p.cust_id=d.cust_id)B,(select A.sales_id, sum(A.amt_paid) amt_paid, sum(tm.amt_adjusted) amt_adjusted from transactions_master tm, ");
			strQuery.append("(select amt_paid, sales_id, trans_id from transactions_child ");
			strQuery.append("where cust_id is not null and (dist_id is null or dist_id = '' ))A ");
			strQuery.append("where tm.trans_id=A.trans_id group by A.sales_id)C) ");
			strQuery.append("where B.sales_id=C.sales_id and B.sales_dt between ? and ? ");
			strQuery.append("union ");
			strQuery.append("select d.cust_id cust_id,d.cust_name cust_name,p.bill_no bill_no,p.sales_dt sales_dt,p.net_amt net_amt,0 amt_paid,0 amt_adjusted from sales p, customer d ");
			strQuery.append("where p.cust_id=d.cust_id and p.sales_id not in (select distinct ifnull(sales_id,0) from transactions_child) and p.sales_dt between ? and ? )E ");
			strQuery.append("order by E.cust_name,E.bill_no");
			
			repBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{new java.sql.Date(sdf.parse(repData.getFromDate()).getTime()),new java.sql.Date(sdf.parse(repData.getToDate()).getTime()),new java.sql.Date(sdf.parse(repData.getFromDate()).getTime()),new java.sql.Date(sdf.parse(repData.getToDate()).getTime())} ,new RowMapper<ReportBean>() {
				int count=1;
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	            @Override
	            public ReportBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ReportBean repBean = new ReportBean();
	            	
	            	repBean.setCount(count++);
	            	repBean.setCustId(rs.getString("E.cust_id"));
	            	repBean.setName(rs.getString("E.cust_name"));
	            	repBean.setBillNo(rs.getString("E.bill_no"));
	            	repBean.setBillDt(sdf1.format(new java.util.Date(rs.getDate("E.sales_dt").getTime())));
	            	repBean.setBillAmt(rs.getDouble("E.net_amt"));
	            	repBean.setPayment(rs.getDouble("E.amt_paid"));

	            	if((rs.getDouble("E.amt_paid") + rs.getDouble("E.amt_adjusted")) == rs.getDouble("E.net_amt")){
	            		repBean.setBalance(0);
	            		repBean.setAdjAmt(rs.getDouble("E.amt_adjusted"));
	            	}else{
	            		repBean.setBalance(rs.getDouble("E.net_amt") - rs.getDouble("E.amt_paid"));
	            		repBean.setAdjAmt(0);
	            	}
	            	
	            	return repBean;
	            }
	        });
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return repBeanList;
	}
	
	public List<ReportBean> fetchDistPurReport(ReportBean repData){
		List<ReportBean> repBeanList = new ArrayList<ReportBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select E.dist_id,E.dist_name,E.bill_no,E.pur_dt,E.net_amt,E.amt_paid,E.amt_adjusted from ");
			strQuery.append("(select B.dist_id dist_id,B.dist_name dist_name,B.bill_no bill_no,B.pur_dt pur_dt,B.net_amt net_amt,C.amt_paid amt_paid,C.amt_adjusted amt_adjusted from ");
			strQuery.append("((select p.bill_no,p.pur_id,p.pur_dt,p.net_amt,p.dist_id,d.dist_name from purchase p, distributor d ");
			strQuery.append("where p.dist_id=d.dist_id)B,(select A.pur_id, sum(A.amt_paid) amt_paid, sum(tm.amt_adjusted) amt_adjusted from transactions_master tm, ");
			strQuery.append("(select amt_paid, pur_id, trans_id from transactions_child ");
			strQuery.append("where dist_id is not null and (cust_id is null or cust_id = '' ))A ");
			strQuery.append("where tm.trans_id=A.trans_id group by A.pur_id)C) ");
			strQuery.append("where B.pur_id=C.pur_id and B.pur_dt between ? and ? and B.dist_id = ? ");
			strQuery.append("union ");
			strQuery.append("select d.dist_id dist_id,d.dist_name dist_name,p.bill_no bill_no,p.pur_dt pur_dt,p.net_amt net_amt,0 amt_paid,0 amt_adjusted from purchase p, distributor d ");
			strQuery.append("where p.dist_id=d.dist_id and p.dist_id=? and p.pur_id not in (select distinct ifnull(pur_id,0) from transactions_child) and p.pur_dt between ? and ? )E ");
			strQuery.append("order by E.dist_name,E.bill_no");
			
			repBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{new java.sql.Date(sdf.parse(repData.getFromDate()).getTime()),new java.sql.Date(sdf.parse(repData.getToDate()).getTime()), repData.getDistId(),
				repData.getDistId(),new java.sql.Date(sdf.parse(repData.getFromDate()).getTime()),new java.sql.Date(sdf.parse(repData.getToDate()).getTime())} ,new RowMapper<ReportBean>() {
				
				int count=1;
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	            @Override
	            public ReportBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ReportBean repBean = new ReportBean();
	            	
	            	repBean.setCount(count++);
	            	repBean.setDistId(rs.getString("E.dist_id"));
	            	repBean.setName(rs.getString("E.dist_name"));
	            	repBean.setBillNo(rs.getString("E.bill_no"));
	            	repBean.setBillDt(sdf1.format(new java.util.Date(rs.getDate("E.pur_dt").getTime())));
	            	repBean.setBillAmt(rs.getDouble("E.net_amt"));
	            	repBean.setPayment(rs.getDouble("E.amt_paid"));

	            	if((rs.getDouble("E.amt_paid") + rs.getDouble("E.amt_adjusted")) == rs.getDouble("E.net_amt")){
	            		repBean.setBalance(0);
	            		repBean.setAdjAmt(rs.getDouble("E.amt_adjusted"));
	            	}else{
	            		repBean.setBalance(rs.getDouble("E.net_amt") - rs.getDouble("E.amt_paid"));
	            		repBean.setAdjAmt(0);
	            	}
	            	
	            	return repBean;
	            }
	             
	        });
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return repBeanList;
	}
	
	public List<ReportBean> fetchCustSalesReport(ReportBean repData){
		List<ReportBean> repBeanList = new ArrayList<ReportBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select E.cust_id,E.cust_name,E.bill_no,E.sales_dt,E.net_amt,E.amt_paid,E.amt_adjusted from ");
			strQuery.append("(select B.cust_id cust_id,B.cust_name cust_name,B.bill_no bill_no,B.sales_dt sales_dt,B.net_amt net_amt,C.amt_paid amt_paid,C.amt_adjusted amt_adjusted from ");
			strQuery.append("((select p.bill_no,p.sales_id,p.sales_dt,p.net_amt,p.cust_id,d.cust_name from sales p, customer d ");
			strQuery.append("where p.cust_id=d.cust_id)B,(select A.sales_id, sum(A.amt_paid) amt_paid, sum(tm.amt_adjusted) amt_adjusted from transactions_master tm, ");
			strQuery.append("(select amt_paid, sales_id, trans_id from transactions_child ");
			strQuery.append("where cust_id is not null and (dist_id is null or dist_id = '' ))A ");
			strQuery.append("where tm.trans_id=A.trans_id group by A.sales_id)C) ");
			strQuery.append("where B.sales_id=C.sales_id and B.sales_dt between ? and ? and B.cust_id = ? ");
			strQuery.append("union ");
			strQuery.append("select d.cust_id cust_id,d.cust_name cust_name,p.bill_no bill_no,p.sales_dt sales_dt,p.net_amt net_amt,0 amt_paid,0 amt_adjusted from sales p, customer d ");
			strQuery.append("where p.cust_id=d.cust_id and p.cust_id=? and p.sales_id not in (select distinct ifnull(sales_id,0) from transactions_child) and p.sales_dt between ? and ? )E ");
			strQuery.append("order by E.cust_name,E.bill_no");
			
			repBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{new java.sql.Date(sdf.parse(repData.getFromDate()).getTime()),new java.sql.Date(sdf.parse(repData.getToDate()).getTime()), repData.getCustId(),
				repData.getCustId(),new java.sql.Date(sdf.parse(repData.getFromDate()).getTime()),new java.sql.Date(sdf.parse(repData.getToDate()).getTime())} ,new RowMapper<ReportBean>() {
				int count=1;
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	            @Override
	            public ReportBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ReportBean repBean = new ReportBean();
	            	
	            	repBean.setCount(count++);
	            	repBean.setCustId(rs.getString("E.cust_id"));
	            	repBean.setName(rs.getString("E.cust_name"));
	            	repBean.setBillNo(rs.getString("E.bill_no"));
	            	repBean.setBillDt(sdf1.format(new java.util.Date(rs.getDate("E.sales_dt").getTime())));
	            	repBean.setBillAmt(rs.getDouble("E.net_amt"));
	            	repBean.setPayment(rs.getDouble("E.amt_paid"));

	            	if((rs.getDouble("E.amt_paid") + rs.getDouble("E.amt_adjusted")) == rs.getDouble("E.net_amt")){
	            		repBean.setBalance(0);
	            		repBean.setAdjAmt(rs.getDouble("E.amt_adjusted"));
	            	}else{
	            		repBean.setBalance(rs.getDouble("E.net_amt") - rs.getDouble("E.amt_paid"));
	            		repBean.setAdjAmt(0);
	            	}
	            	
	            	return repBean;
	            }
	        });
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return repBeanList;
	}
	
	public List<ReportBean> fetchFinYear(){
		List<ReportBean> repBeanList = new ArrayList<ReportBean>();
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			String strQuery = "SELECT distinct year(trans_dt) transYear FROM transactions_master order by transYear";
			
			repBeanList = jdbcTemplate.query(strQuery.toString() ,new RowMapper<ReportBean>() {
				
				@Override
	            public ReportBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ReportBean repBean = new ReportBean();
	            	
	            	repBean.setFinYear(rs.getInt("transYear")-1 +"-"+rs.getInt("transYear"));
	            	repBean.setCount(rs.getInt("transYear"));
	            	
	            	return repBean;
	            }
			});
			
			Iterator<ReportBean> itrList = repBeanList.iterator();
			int lastYear = 0;
			while(itrList.hasNext()){
				ReportBean tempBean = itrList.next();
				lastYear = tempBean.getCount();
			}
			
			if(repBeanList.size() == 0){
				ReportBean temp = new ReportBean();
				temp.setFinYear((Calendar.getInstance().get(1)-1) + "-" + Calendar.getInstance().get(1));
				repBeanList.add(temp);
				temp = new ReportBean();
				temp.setFinYear(Calendar.getInstance().get(1) + "-" + (Calendar.getInstance().get(1)+1));
				repBeanList.add(temp);
			}else{
				ReportBean temp = new ReportBean();
				temp.setFinYear(lastYear + "-" + (lastYear+1));
				repBeanList.add(temp);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return repBeanList;
	}
	
	public List<ReportBean> fetchDistLedger(ReportBean repData){
		List<ReportBean> repBeanList = new ArrayList<ReportBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select E.trans_id, E.pur_id, E.amt_paid, E.trans_dt, E.cheque_no, E.cheque_dt, E.self_bank_id, E.bank_name, E.bill_no, E.dist_id, E.dist_name from ");
			strQuery.append("(select null trans_id, p.pur_id pur_id,p.net_amt amt_paid,p.pur_dt trans_dt,null cheque_no,null cheque_dt, ");
			strQuery.append("null self_bank_id,null bank_name,p.bill_no bill_no,p.dist_id dist_id,d.dist_name dist_name from purchase p, distributor d where p.status='ST' and p.dist_id = d.dist_id ");			
			strQuery.append("union ");
			strQuery.append("select tm.trans_id trans_id, A.pur_id pur_id, A.amt_paid amt_paid,tm.trans_dt trans_dt,tm.cheque_no cheque_no,tm.cheque_dt cheque_dt,tm.self_bank_id self_bank_id, ");
			strQuery.append("b.bank_name bank_name, null bill_no,A.dist_id dist_id,d.dist_name dist_name from transactions_master tm, bank_details b, distributor d, ");
			strQuery.append("(select amt_paid, pur_id, trans_id,dist_id from transactions_child where dist_id is not null and (cust_id is null or cust_id = '' ) )A ");
			strQuery.append("where tm.trans_id = A.trans_id and tm.self_bank_id = b.bank_id and A.dist_id=d.dist_id ");
			strQuery.append("union ");
			strQuery.append("select tc.trans_id trans_id, B.pur_id pur_id, B.amt_paid amt_paid,tc.trans_dt trans_dt,tc.cheque_no cheque_no,tc.cheque_dt cheque_dt,tc.self_bank_id self_bank_id, ");
			strQuery.append("null bank_name, null bill_no, B.dist_id dist_id,d.dist_name dist_name from transactions_master tc, distributor d, ");
			strQuery.append("(select amt_paid, pur_id, trans_id,dist_id from transactions_child where dist_id is not null and (cust_id is null or cust_id = '' ) )B ");
			strQuery.append("where tc.trans_id = B.trans_id and (tc.self_bank_id is null or tc.self_bank_id = 0) and B.dist_id=d.dist_id) E ");
			strQuery.append("where E.dist_id=? and E.trans_dt between ? and ?");
			strQuery.append("order by E.trans_dt");
			
			double balance = fetchDistLedgerBal(repData.getDistId());
			
			repBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{repData.getDistId(), new java.sql.Date(sdf.parse(repData.getFromDate()).getTime()),new java.sql.Date(sdf.parse(repData.getToDate()).getTime())} ,new RowMapper<ReportBean>() {
				
				int count=1;
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
				DecimalFormat df = new DecimalFormat("#.00");
	            @Override
	            public ReportBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ReportBean repBean = new ReportBean();
	            	
	            	repBean.setCount(count++);
	            	repBean.setDistId(rs.getString("E.dist_id"));
	            	repBean.setName(rs.getString("E.dist_name"));
	            	repBean.setBillNo(rs.getString("E.bill_no"));
	            	repBean.setBillDt(sdf1.format(new java.util.Date(rs.getDate("E.trans_dt").getTime())));
	            	repBean.setCheqNo(rs.getString("E.cheque_no"));
	            	if(rs.getDate("E.cheque_dt") != null && !rs.getDate("E.cheque_dt").equals("null") && !rs.getDate("E.cheque_dt").equals("")){
	            		repBean.setCheqDt(sdf1.format(new java.util.Date(rs.getDate("E.cheque_dt").getTime())));
	            	}else{
	            		repBean.setCheqDt("");
	            	}
	            	repBean.setBankName(rs.getString("E.bank_name"));

	            	if(rs.getString("E.trans_id") != null && !rs.getString("E.trans_id").equals("null") && !rs.getString("E.trans_id").equals("")){
	            		repBean.setPayment(rs.getDouble("E.amt_paid"));
	            		repBean.setBalance(new Double(df.format(balance - rs.getDouble("E.amt_paid"))));
	            	}else{
	            		repBean.setDeposit(rs.getDouble("E.amt_paid"));
	            		repBean.setBalance(new Double(df.format(balance + rs.getDouble("E.amt_paid"))));
	            	}
	            	
	            	return repBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return repBeanList;
	}
	
	public double fetchDistLedgerBal(String distId){
		List<ReportBean> repBeanList = new ArrayList<ReportBean>();
		double bal = 0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select A.amt_paid, B.amt_adjusted, C.net_amt, (C.net_amt - (A.amt_paid + B.amt_adjusted)) balance from ");
			strQuery.append("(select sum(amt_paid) amt_paid, dist_id from transactions_child where dist_id=?) A, (select sum(amt_adjusted) amt_adjusted from transactions_master where trans_id in ( ");
			strQuery.append("select distinct trans_id from transactions_child where dist_id = ?)) B, (select sum(net_amt) net_amt, dist_id from purchase where dist_id=?) C");			
			
			repBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{distId,distId,distId} ,new RowMapper<ReportBean>() {
	            @Override
	            public ReportBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ReportBean repBean = new ReportBean();
	            	
	            	repBean.setBalance(rs.getDouble("balance"));
	            	return repBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(repBeanList.size() > 0 && repBeanList.get(0) != null && repBeanList.get(0).getBalance() > 0){
			bal = repBeanList.get(0).getBalance();
		}
		
		return bal;
	}
	
	public List<ReportBean> fetchCustLedger(ReportBean repData){
		List<ReportBean> repBeanList = new ArrayList<ReportBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select E.trans_id, E.sales_id, E.amt_paid, E.trans_dt, E.cheque_no, E.cheque_dt, E.bank_id, E.bank_name, E.bill_no, E.cust_id, E.cust_name from ");
			strQuery.append("(select null trans_id, p.sales_id pur_id,p.net_amt amt_paid,p.sales_dt trans_dt,null cheque_no,null cheque_dt, ");
			strQuery.append("null bank_id,null bank_name,p.bill_no bill_no,p.cust_id cust_id,d.cust_name cust_name from sales p, customer d where p.status='ST' and p.cust_id = d.cust_id ");			
			strQuery.append("union ");
			strQuery.append("select tm.trans_id trans_id, A.sales_id sales_id, A.amt_paid amt_paid,tm.trans_dt trans_dt,tm.cheque_no cheque_no,tm.cheque_dt cheque_dt,tm.bank_id bank_id, ");
			strQuery.append("b.bank_name bank_name, null bill_no,A.cust_id cust_id,d.cust_name cust_name from transactions_master tm, bank_details b, customer d, ");
			strQuery.append("(select amt_paid, sales_id, trans_id,cust_id from transactions_child where cust_id is not null and (dist_id is null or dist_id = '' ) )A ");
			strQuery.append("where tm.trans_id = A.trans_id and tm.bank_id = b.bank_id and A.cust_id=d.cust_id ");
			strQuery.append("union ");
			strQuery.append("select tc.trans_id trans_id, B.sales_id sales_id, B.amt_paid amt_paid,tc.trans_dt trans_dt,tc.cheque_no cheque_no,tc.cheque_dt cheque_dt,tc.bank_id bank_id, ");
			strQuery.append("null bank_name, null bill_no, B.cust_id cust_id,d.cust_name cust_name from transactions_master tc, customer d, ");
			strQuery.append("(select amt_paid, sales_id, trans_id,cust_id from transactions_child where cust_id is not null and (dist_id is null or dist_id = '' ) )B ");
			strQuery.append("where tc.trans_id = B.trans_id and (tc.bank_id is null or tc.bank_id = 0) and B.cust_id=d.cust_id) E ");
			strQuery.append("where E.cust_id=? and E.trans_dt between ? and ?");
			strQuery.append("order by E.trans_dt");
			
			double balance = fetchCustLedgerBal(repData.getCustId());
			
			repBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{repData.getCustId(), new java.sql.Date(sdf.parse(repData.getFromDate()).getTime()),new java.sql.Date(sdf.parse(repData.getToDate()).getTime())} ,new RowMapper<ReportBean>() {
				
				int count=1;
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
				DecimalFormat df = new DecimalFormat("#.00");
	            @Override
	            public ReportBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ReportBean repBean = new ReportBean();
	            	
	            	repBean.setCount(count++);
	            	repBean.setDistId(rs.getString("E.dist_id"));
	            	repBean.setName(rs.getString("E.dist_name"));
	            	repBean.setBillNo(rs.getString("E.bill_no"));
	            	repBean.setBillDt(sdf1.format(new java.util.Date(rs.getDate("E.trans_dt").getTime())));
	            	repBean.setCheqNo(rs.getString("E.cheque_no"));
	            	repBean.setCheqDt(sdf1.format(new java.util.Date(rs.getDate("E.cheque_dt").getTime())));
	            	repBean.setBankName(rs.getString("E.bank_name"));

	            	if(rs.getString("E.trans_id") != null && !rs.getString("E.trans_id").equals("null") && !rs.getString("E.trans_id").equals("")){
	            		repBean.setPayment(rs.getDouble("E.amt_paid"));
	            		repBean.setBalance(new Double(df.format(balance - rs.getDouble("E.amt_paid"))));
	            	}else{
	            		repBean.setDeposit(rs.getDouble("E.amt_paid"));
	            		repBean.setBalance(new Double(df.format(balance + rs.getDouble("E.amt_paid"))));
	            	}
	            	
	            	return repBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return repBeanList;
	}
	
	public double fetchCustLedgerBal(String custId){
		List<ReportBean> repBeanList = new ArrayList<ReportBean>();
		double bal = 0;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			StringBuilder strQuery = new StringBuilder();
			
			strQuery.append("select A.amt_paid, B.amt_adjusted, C.net_amt, (C.net_amt - (A.amt_paid + B.amt_adjusted)) balance from ");
			strQuery.append("(select sum(amt_paid) amt_paid, cust_id from transactions_child where cust_id=?) A, (select sum(amt_adjusted) amt_adjusted from transactions_master where trans_id in ( ");
			strQuery.append("select distinct trans_id from transactions_child where cust_id = ?)) B, (select sum(net_amt) net_amt, cust_id from sales where cust_id=?) C");			
			
			repBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{custId,custId,custId} ,new RowMapper<ReportBean>() {
	            @Override
	            public ReportBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ReportBean repBean = new ReportBean();
	            	
	            	repBean.setBalance(rs.getDouble("balance"));
	            	return repBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(repBeanList.size() > 0 && repBeanList.get(0) != null && repBeanList.get(0).getBalance() > 0){
			bal = repBeanList.get(0).getBalance();
		}
		
		return bal;
	}
	
}
