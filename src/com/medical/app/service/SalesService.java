package com.medical.app.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medical.app.configurations.MedicalAppConfig;
import com.medical.app.model.CustomerBean;
import com.medical.app.model.ProductBean;
import com.medical.app.model.SalesBean;
import com.medical.app.model.TransactionBean;

@Service("salesService")
@Transactional
public class SalesService {
	@Autowired
	MedicalAppConfig appConfig;
	
	public String getNextBillNo(){
		String billNo = "0";
		StringBuilder newBillNo = new StringBuilder(); 
		JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
		List<SalesBean> salesBeanList = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		String startDate = "";
		String endDate = "";
		if(cal.get(cal.MONTH) <= 2 ){
			startDate = "01/04/"+(cal.get(cal.YEAR)-1)+" 00:00:00";
			endDate = "31/03/"+cal.get(cal.YEAR)+" 23:59:59";
		}else{
			startDate = "01/04/"+(cal.get(cal.YEAR))+" 00:00:00";
			endDate = "31/03/"+(cal.get(cal.YEAR)+1)+" 23:59:59";
		}
		try{
			String strQuery = "SELECT max(bill_no)+1 bill_no FROM SALES where sales_dt between ? and ?";
			
			salesBeanList = jdbcTemplate.query(strQuery,new Object[]{new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime())}, new RowMapper<SalesBean>() {
	            @Override
	            public SalesBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	SalesBean salesBean = new SalesBean();
	            	
	            	salesBean.setBillNo(rs.getString("bill_no"));
	            	
	            	return salesBean;
	            }
	        });
			
			if(salesBeanList.size() > 0 && salesBeanList.get(0).getBillNo() != null && !salesBeanList.get(0).getBillNo().equals("")){
				billNo = salesBeanList.get(0).getBillNo();
				
				switch(billNo.length()){
					case 1: 
						newBillNo.append("0000");
						newBillNo.append(billNo);
						break;
					case 2:
						newBillNo.append("000");
						newBillNo.append(billNo);
						break;
					case 3:
						newBillNo.append("00");
						newBillNo.append(billNo);
						break;
					case 4:
						newBillNo.append("0");
						newBillNo.append(billNo);
						break;
				}	
			}else if(salesBeanList.size() > 0 && (salesBeanList.get(0).getBillNo() == null || salesBeanList.get(0).getBillNo().equals("")) && cal.get(Calendar.MONTH) == Calendar.APRIL){
				System.out.println("Inside March");
				newBillNo.append("00001");
			}else{
				System.out.println("Inside else for bill other than march");
				newBillNo.append("00001");
			}
			System.out.println("new bill no="+newBillNo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return newBillNo.toString();
	}
	
	public List<ProductBean> fetchProductList(String prodId){
		List<ProductBean> prodBeanList = null;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select pd.prod_dtl_id,pd.mrp,pd.rate,pd.cur_stock,pd.tax_id,t.tax_perc,p.prod_code from product p, product_details pd,tax t where ");
			strQuery.append(" p.prod_id=? and pd.prod_id=p.prod_id and t.tax_id=pd.tax_id and pd.cur_stock > 0 order by pd.prod_dtl_id desc");
			
			prodBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{prodId}, new RowMapper<ProductBean>() {
				 
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	prodBean.setProdDtlId(rs.getInt("pd.prod_dtl_id"));
	            	prodBean.setMrp(rs.getDouble("pd.mrp"));
	            	prodBean.setRate(rs.getDouble("pd.rate"));
	            	prodBean.setOldRate(rs.getDouble("pd.rate"));
	            	prodBean.setStock(rs.getInt("pd.cur_stock"));
	            	prodBean.setTaxPerc(rs.getDouble("t.tax_perc"));
	            	prodBean.setTaxId(rs.getInt("pd.tax_id"));
	            	prodBean.setProdCode(rs.getString("p.prod_code"));
	            	
	            	ProductBean discData = fetchDiscRate(prodBean.getProdDtlId());
	            	
	            	if(discData != null){
	            		prodBean.setDisc1(discData.getDisc1());
	            		prodBean.setDisc2(discData.getDisc2());
	            		prodBean.setScheme(discData.getScheme());
	            	}
	            	
	            	return prodBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return prodBeanList;
	}
	
	public ProductBean fetchDiscRate(int prodDtlId){
		List<ProductBean> prodBeanList = null;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select disc1_perc, disc2_perc, scheme_perc from sales_prod_details where prod_dtl_id = ? order by sales_prod_dtl_id desc limit 1");
			
			prodBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{prodDtlId}, new RowMapper<ProductBean>() {
				 
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	prodBean.setDisc1(rs.getDouble("disc1_perc"));
	            	prodBean.setDisc2(rs.getDouble("disc2_perc"));
	            	prodBean.setScheme(rs.getDouble("scheme_perc"));
	            	
	            	return prodBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		if(prodBeanList.size() > 0)
			return prodBeanList.get(0);
		else
			return null;
	}
	
	public List<ProductBean> fetchPckSizeList(String prodName, String manId){
		List<ProductBean> prodBeanList = null;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select distinct p.prod_id,p.prod_code,p.pckg_size from product p, ");
			strQuery.append("product_details pd where p.prod_id=pd.prod_id and pd.cur_stock > 0 ");
			strQuery.append("and p.man_id=? and p.prod_name=?");
			
			prodBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{manId, prodName}, new RowMapper<ProductBean>() {
				 
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	prodBean.setProdId(rs.getString("p.prod_id"));
	            	prodBean.setPckSize(rs.getString("p.pckg_size"));
	            	prodBean.setProdCode(rs.getString("p.prod_code"));

	            	return prodBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return prodBeanList;
	}
	
	public int stockMinus(ProductBean prodData){
		int result = 0;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
		if(prodData != null){
			String fetchCust = "select cust_id from customer where cust_name='Direct Stock Minus'";
			
			List<CustomerBean> custIdList = jdbcTemplate.query(fetchCust, new Object[]{ }, new RowMapper<CustomerBean>() {
				 
	            @Override
	            public CustomerBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	CustomerBean custBean = new CustomerBean();
	            	custBean.setCustId(rs.getString("cust_id"));
	            	
	            	return custBean;
	            }
			});
			
			String strQuery = "insert into sales(bill_no,cust_id,sales_dt,sales_added_dt) values(0,?,curdate(),curdate())";
			jdbcTemplate.update(strQuery, new Object[]{custIdList.get(0).getCustId()});
			
			String fetchSalesId = "select sales_id from sales where date(sales_added_dt)=date(curdate()) and cust_id = ? order by sales_id desc limit 1";
			
			List<SalesBean> salesIdList = jdbcTemplate.query(fetchSalesId, new Object[]{ custIdList.get(0).getCustId()}, new RowMapper<SalesBean>() {
				 
	            @Override
	            public SalesBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	SalesBean salesBean = new SalesBean();
	            	salesBean.setSalesId(rs.getLong("sales_id"));
	            	
	            	return salesBean;
	            }
			});
			
			String updateSales = "insert into sales_prod_details(prod_dtl_id,sales_id,qty_sold) values(?,?,?)";
			
			result = jdbcTemplate.update(updateSales, new Object[]{prodData.getProdDtlId(), salesIdList.get(0).getSalesId(), prodData.getQtyMinus()});
			
			result = jdbcTemplate.update("update product_details set cur_stock=cur_stock-? where prod_dtl_id=?", new Object[]{prodData.getQtyMinus(), prodData.getProdDtlId()});
		}
		return result;
	}
	
	public int saveSales(SalesBean newSalesBean, SalesBean oldSalesBean){
		int result = 0;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
		try{
		
			if(newSalesBean != null && oldSalesBean != null && (oldSalesBean.getOldProdList() == null || oldSalesBean.getOldProdList().size() == 0)){
				System.out.println("inside if");
				StringBuilder strQuery = new StringBuilder(); 
				strQuery.append("insert into sales(bill_no,cust_id,sales_dt,gross_amt,total_vat,net_amt,round_off_amt,status,tot_scheme,sales_added_dt) ");
				strQuery.append(" values(?,?,?,?,?,?,?,?,?,curdate())");
					
			int success = jdbcTemplate.update(strQuery.toString(), new Object[]{newSalesBean.getBillNo(), newSalesBean.getCustId(), new java.sql.Date(sdf.parse(newSalesBean.getBillDate()).getTime()), newSalesBean.getGrossAmt(),
						newSalesBean.getTotTax(), newSalesBean.getNetAmt(), newSalesBean.getRoundOffAmt(),"SD", newSalesBean.getTotScheme()});
				
				if(success > 0){
					strQuery = new StringBuilder();
					
					strQuery.append("select sales_id from sales where bill_no=? and date(sales_added_dt)=date(curdate())");
					
					List<SalesBean> salesIdList = jdbcTemplate.query(strQuery.toString(), new Object[]{ newSalesBean.getBillNo()}, new RowMapper<SalesBean>() {
						 
			            @Override
			            public SalesBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
			            	SalesBean salesBean = new SalesBean();
			            	salesBean.setSalesId(rs.getLong("sales_id"));
			            	
			            	return salesBean;
			            }
					});
					
					long newSalesId = salesIdList.get(0).getSalesId();
					
					Iterator<ProductBean> itrNewProd = newSalesBean.getProdList().iterator();
					
					while(itrNewProd.hasNext()){
						ProductBean tempProd = itrNewProd.next();
						
						strQuery = new StringBuilder();
						
						strQuery.append("insert into sales_prod_details(prod_dtl_id,sales_id,qty_sold,disc1_perc,disc1_val,disc2_perc,disc2_val,scheme_perc,scheme_val,tax_val,tot_amt,new_rate)");
						strQuery.append(" values(?,?,?,?,?,?,?,?,?,?,?,?)");
						
						success = jdbcTemplate.update(strQuery.toString(), new Object[]{tempProd.getProdDtlId(), newSalesId, tempProd.getQty(),
							tempProd.getDisc1(),tempProd.getDisc1Val(),tempProd.getDisc2(), tempProd.getDisc2Val(), tempProd.getScheme(), tempProd.getSchemeVal(),
							tempProd.getTaxVal(),tempProd.getTotAmt(),tempProd.getNewRate()});
						
						if(success > 0){
							int qty = 0;
							if(tempProd.getScheme() == 0.5){
								qty = tempProd.getQty();
							}else{
								qty = tempProd.getQty() + new Double(tempProd.getSchemeVal()).intValue();
							}
							jdbcTemplate.update("update product_details set cur_stock=cur_stock-? where prod_dtl_id=?", new Object[]{qty, tempProd.getProdDtlId()});
						}
					}
					result = success;
				}
			}else{
				StringBuilder strQuery = new StringBuilder(); 
				strQuery.append("update sales set cust_id=?,sales_dt=?,gross_amt=?,total_vat=?,net_amt=?,round_off_amt=?,status=?,tot_scheme=? ");
				strQuery.append(" where sales_id=?");
					
				int success = jdbcTemplate.update(strQuery.toString(), new Object[]{oldSalesBean.getCustId(), new java.sql.Date(sdf.parse(oldSalesBean.getBillDate()).getTime()), oldSalesBean.getGrossAmt(),
						oldSalesBean.getTotTax(), oldSalesBean.getNetAmt(), oldSalesBean.getRoundOffAmt(),"SD",oldSalesBean.getTotScheme(), oldSalesBean.getSalesId()});
				
				if(success > 0){
					Map<Integer, ProductBean> oldProdMap = new HashMap<Integer, ProductBean>();
					Map<Integer, ProductBean> newProdMap = new HashMap<Integer, ProductBean>();
					
					Iterator<ProductBean> itrProd = oldSalesBean.getProdList().iterator();
					Iterator<ProductBean> itrOldProd = oldSalesBean.getOldProdList().iterator();
					
					while(itrOldProd.hasNext()){
						ProductBean oldProdForm = itrOldProd.next();
						
						oldProdMap.put(oldProdForm.getProdDtlId(), oldProdForm);
					}
					
					while(itrProd.hasNext()){
						ProductBean prodForm = itrProd.next();
						
						newProdMap.put(prodForm.getProdDtlId() , prodForm);
					}
					itrProd = null;
					itrOldProd = null;
					itrProd = oldSalesBean.getProdList().iterator();
					itrOldProd = oldSalesBean.getOldProdList().iterator();
					
					while(itrOldProd.hasNext()){
						ProductBean prodBean = itrOldProd.next();
						
						if(newProdMap.containsKey(prodBean.getProdDtlId())){
							
							ProductBean newProdBean = newProdMap.get(prodBean.getProdDtlId());
							
							strQuery = new StringBuilder();
	
							strQuery.append("update sales_prod_details set qty_sold=?,disc1_perc=?,disc1_val=?,disc2_perc=?,disc2_val=?,scheme_perc=?,scheme_val=?,tax_val=?,tot_amt=?,new_rate=? ");
							strQuery.append(" where sales_id=? and prod_dtl_id=?");
	
							success = jdbcTemplate.update(strQuery.toString(), new Object[]{newProdBean.getQty(), newProdBean.getDisc1(), newProdBean.getDisc1Val(), newProdBean.getDisc2(), newProdBean.getDisc2Val(),
								newProdBean.getScheme(), newProdBean.getSchemeVal(),newProdBean.getTaxVal(), newProdBean.getTotAmt(), newProdBean.getNewRate(), oldSalesBean.getSalesId(), newProdBean.getProdDtlId()});
							
							if(success > 0){
								int qty = 0;
								int oldQty = 0;
								if(prodBean.getScheme() == 0.5){
									oldQty = prodBean.getQty();
								}else{
									oldQty = prodBean.getQty() + new Double(prodBean.getSchemeVal()).intValue(); 
								}
								
								if(newProdBean.getScheme() == 0.5){
									qty = newProdBean.getQty();
								}else{
									qty = newProdBean.getQty() + new Double(newProdBean.getSchemeVal()).intValue(); 
								}
								
								int stockToMinus = qty - oldQty;
								
								jdbcTemplate.update("update product_details set cur_stock=cur_stock-(?) where prod_dtl_id=?", new Object[]{stockToMinus, newProdBean.getProdDtlId()});
							}
						}else{
							success = 0;
							
							success = jdbcTemplate.update("delete from sales_prod_details where sales_id=? and prod_dtl_id=?", new Object[]{ oldSalesBean.getSalesId(), prodBean.getProdDtlId()});
							
							if(success > 0){
								int stockToAdd = 0;
								if(prodBean.getScheme() == 0.5){
									stockToAdd = prodBean.getQty();
								}else{
									stockToAdd = prodBean.getQty() + new Double(prodBean.getSchemeVal()).intValue();
								}
								
								jdbcTemplate.update("update product_details set cur_stock=cur_stock+? where prod_dtl_id=?", new Object[]{stockToAdd, prodBean.getProdDtlId()});
							}
							
						}
					}
					
					while(itrProd.hasNext()){
						ProductBean prodBean = itrProd.next();
						
						if(oldProdMap.containsKey(prodBean.getProdDtlId())){
							//do nothing
							System.out.println("do nothing");
						}else{
							strQuery = new StringBuilder();
	
							strQuery.append("insert into sales_prod_details(prod_dtl_id,sales_id,qty_sold,disc1_perc,disc1_val,disc2_perc,disc2_val,scheme_perc,scheme_val,tax_val,tot_amt,new_rate) ");
							strQuery.append(" values(?,?,?,?,?,?,?,?,?,?,?,?)");
	
							success = jdbcTemplate.update(strQuery.toString(), new Object[]{prodBean.getProdDtlId(), oldSalesBean.getSalesId(), prodBean.getQty(),
								prodBean.getDisc1(),prodBean.getDisc1Val(),prodBean.getDisc2(), prodBean.getDisc2Val(), prodBean.getScheme(), prodBean.getSchemeVal(),
								prodBean.getTaxVal(),prodBean.getTotAmt(),prodBean.getNewRate()});
							
							if(success > 0){
								int qty = 0;
								if(prodBean.getScheme() == 0.5){
									qty = prodBean.getQty();
								}else{
									qty = prodBean.getQty() + new Double(prodBean.getSchemeVal()).intValue();
								}
								jdbcTemplate.update("update product_details set cur_stock=cur_stock-? where prod_dtl_id=?", new Object[]{qty, prodBean.getProdDtlId()});
							}
						}
					}
					result=success;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int submitSales(SalesBean newSalesBean, SalesBean oldSalesBean){
		int result = 0;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
		try{
		
			if(newSalesBean != null && oldSalesBean != null && (oldSalesBean.getOldProdList() == null || oldSalesBean.getOldProdList().size() == 0)){
				System.out.println("inside if");
				StringBuilder strQuery = new StringBuilder(); 
				strQuery.append("insert into sales(bill_no,cust_id,sales_dt,gross_amt,total_vat,net_amt,round_off_amt,status,tot_scheme,sales_added_dt) ");
				strQuery.append(" values(?,?,?,?,?,?,?,?,?,curdate())");
					
			int success = jdbcTemplate.update(strQuery.toString(), new Object[]{newSalesBean.getBillNo(), newSalesBean.getCustId(), new java.sql.Date(sdf.parse(newSalesBean.getBillDate()).getTime()), newSalesBean.getGrossAmt(),
						newSalesBean.getTotTax(), newSalesBean.getNetAmt(), newSalesBean.getRoundOffAmt(),"ST", newSalesBean.getTotScheme()});
				
				if(success > 0){
					strQuery = new StringBuilder();
					
					strQuery.append("select sales_id from sales where bill_no=? and date(sales_added_dt)=date(curdate())");
					
					List<SalesBean> salesIdList = jdbcTemplate.query(strQuery.toString(), new Object[]{ newSalesBean.getBillNo()}, new RowMapper<SalesBean>() {
						 
			            @Override
			            public SalesBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
			            	SalesBean salesBean = new SalesBean();
			            	salesBean.setSalesId(rs.getLong("sales_id"));
			            	
			            	return salesBean;
			            }
					});
					
					long newSalesId = salesIdList.get(0).getSalesId();
					
					Iterator<ProductBean> itrNewProd = newSalesBean.getProdList().iterator();
					
					strQuery = new StringBuilder();
	
					strQuery.append("insert into sales_prod_details(prod_dtl_id,sales_id,qty_sold,disc1_perc,disc1_val,disc2_perc,disc2_val,scheme_perc,scheme_val,tax_val,tot_amt,new_rate)");
					strQuery.append(" values(?,?,?,?,?,?,?,?,?,?,?,?)");
					
					while(itrNewProd.hasNext()){
						ProductBean tempProd = itrNewProd.next();
						success = jdbcTemplate.update(strQuery.toString(), new Object[]{tempProd.getProdDtlId(), newSalesId, tempProd.getQty(),
							tempProd.getDisc1(),tempProd.getDisc1Val(),tempProd.getDisc2(), tempProd.getDisc2Val(), tempProd.getScheme(), tempProd.getSchemeVal(),
							tempProd.getTaxVal(),tempProd.getTotAmt(),tempProd.getNewRate()});
						
						if(success > 0){
							int qty = 0;
							if(tempProd.getScheme() == 0.5){
								qty = tempProd.getQty();
							}else{
								qty = tempProd.getQty() + new Double(tempProd.getSchemeVal()).intValue();
							}
							jdbcTemplate.update("update product_details set cur_stock=cur_stock-? where prod_dtl_id=?", new Object[]{qty, tempProd.getProdDtlId()});
						}
					}
					
					double balance = 0;
					
					String sql = "select ifNull(balance,0) balance from ledger where ledger_id = (select max(ledger_id) from ledger where cust_id=?)";
					
					List<TransactionBean> balList = jdbcTemplate.query(sql, new Object[]{newSalesBean.getCustId()}, new RowMapper<TransactionBean>() {
						 
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
					
					StringBuilder strLedInsert = new StringBuilder();
					
					strLedInsert.append("insert into ledger(cust_id,sales_id, description, debit, balance) values(?,?,?,?,?)");
					
					jdbcTemplate.update(strLedInsert.toString() , new Object[]{newSalesBean.getCustId(), newSalesId, 
							"New Bill Added", newSalesBean.getNetAmt(), (balance + newSalesBean.getNetAmt())});
					
					result = success;
				}
			}else{
				StringBuilder strQuery = new StringBuilder(); 
				strQuery.append("update sales set cust_id=?,sales_dt=?,gross_amt=?,total_vat=?,net_amt=?,round_off_amt=?,status=?,tot_scheme=? ");
				strQuery.append(" where sales_id=?");
					
				int success = jdbcTemplate.update(strQuery.toString(), new Object[]{oldSalesBean.getCustId(), new java.sql.Date(sdf.parse(oldSalesBean.getBillDate()).getTime()), oldSalesBean.getGrossAmt(),
						oldSalesBean.getTotTax(), oldSalesBean.getNetAmt(), oldSalesBean.getRoundOffAmt(),"ST",oldSalesBean.getTotScheme(), oldSalesBean.getSalesId()});
				
				if(success > 0){
					Map<Integer, ProductBean> oldProdMap = new HashMap<Integer, ProductBean>();
					Map<Integer, ProductBean> newProdMap = new HashMap<Integer, ProductBean>();
					
					Iterator<ProductBean> itrProd = oldSalesBean.getProdList().iterator();
					Iterator<ProductBean> itrOldProd = oldSalesBean.getOldProdList().iterator();
					
					while(itrOldProd.hasNext()){
						ProductBean oldProdForm = itrOldProd.next();
						
						oldProdMap.put(oldProdForm.getProdDtlId(), oldProdForm);
					}
					
					while(itrProd.hasNext()){
						ProductBean prodForm = itrProd.next();
						
						newProdMap.put(prodForm.getProdDtlId() , prodForm);
					}
					itrProd = null;
					itrOldProd = null;
					itrProd = oldSalesBean.getProdList().iterator();
					itrOldProd = oldSalesBean.getOldProdList().iterator();
					
					while(itrOldProd.hasNext()){
						ProductBean prodBean = itrOldProd.next();
						
						if(newProdMap.containsKey(prodBean.getProdDtlId())){
							
							ProductBean newProdBean = newProdMap.get(prodBean.getProdDtlId());
							
							strQuery = new StringBuilder();
	
							strQuery.append("update sales_prod_details set qty_sold=?,disc1_perc=?,disc1_val=?,disc2_perc=?,disc2_val=?,scheme_perc=?,scheme_val=?,tax_val=?,tot_amt=?,new_rate=? ");
							strQuery.append(" where sales_id=? and prod_dtl_id=?");
	
							success = jdbcTemplate.update(strQuery.toString(), new Object[]{newProdBean.getQty(), newProdBean.getDisc1(), newProdBean.getDisc1Val(), newProdBean.getDisc2(), newProdBean.getDisc2Val(),
								newProdBean.getScheme(), newProdBean.getSchemeVal(),newProdBean.getTaxVal(), newProdBean.getTotAmt(), newProdBean.getNewRate(), oldSalesBean.getSalesId(), newProdBean.getProdDtlId()});
							
							if(success > 0){
								int qty = 0;
								int oldQty = 0;
								if(prodBean.getScheme() == 0.5){
									oldQty = prodBean.getQty();
								}else{
									oldQty = prodBean.getQty() + new Double(prodBean.getSchemeVal()).intValue(); 
								}
								
								if(newProdBean.getScheme() == 0.5){
									qty = newProdBean.getQty();
								}else{
									qty = newProdBean.getQty() + new Double(newProdBean.getSchemeVal()).intValue(); 
								}
								
								int stockToMinus = qty - oldQty;
								
								jdbcTemplate.update("update product_details set cur_stock=cur_stock-(?) where prod_dtl_id=?", new Object[]{stockToMinus, newProdBean.getProdDtlId()});
							}
						}else{
							success = 0;
							
							success = jdbcTemplate.update("delete from sales_prod_details where sales_id=? and prod_dtl_id=?", new Object[]{ oldSalesBean.getSalesId(), prodBean.getProdDtlId()});
							
							if(success > 0){
								int stockToAdd = 0;
								if(prodBean.getScheme() == 0.5){
									stockToAdd = prodBean.getQty();
								}else{
									stockToAdd = prodBean.getQty() + new Double(prodBean.getSchemeVal()).intValue();
								}
								
								jdbcTemplate.update("update product_details set cur_stock=cur_stock+? where prod_dtl_id=?", new Object[]{stockToAdd, prodBean.getProdDtlId()});
							}
							
						}
					}
					
					while(itrProd.hasNext()){
						ProductBean prodBean = itrProd.next();
						
						if(oldProdMap.containsKey(prodBean.getProdDtlId())){
							//do nothing
							System.out.println("do nothing");
						}else{
							strQuery = new StringBuilder();
	
							strQuery.append("insert into sales_prod_details(prod_dtl_id,sales_id,qty_sold,disc1_perc,disc1_val,disc2_perc,disc2_val,scheme_perc,scheme_val,tax_val,tot_amt,new_rate) ");
							strQuery.append(" values(?,?,?,?,?,?,?,?,?,?,?,?)");
	
							success = jdbcTemplate.update(strQuery.toString(), new Object[]{prodBean.getProdDtlId(), oldSalesBean.getSalesId(), prodBean.getQty(),
								prodBean.getDisc1(),prodBean.getDisc1Val(),prodBean.getDisc2(), prodBean.getDisc2Val(), prodBean.getScheme(), prodBean.getSchemeVal(),
								prodBean.getTaxVal(),prodBean.getTotAmt(),prodBean.getNewRate()});
							
							if(success > 0){
								int qty = 0;
								if(prodBean.getScheme() == 0.5){
									qty = prodBean.getQty();
								}else{
									qty = prodBean.getQty() + new Double(prodBean.getSchemeVal()).intValue();
								}
								jdbcTemplate.update("update product_details set cur_stock=cur_stock-? where prod_dtl_id=?", new Object[]{qty, prodBean.getProdDtlId()});
							}
						}
					}
					double balance = 0;
					
					String sql = "select ifNull(balance,0) balance from ledger where ledger_id = (select max(ledger_id) from ledger where cust_id=?)";
					
					List<TransactionBean> balList = jdbcTemplate.query(sql, new Object[]{oldSalesBean.getCustId()}, new RowMapper<TransactionBean>() {
						 
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
					
					StringBuilder strLedInsert = new StringBuilder();
					
					strLedInsert.append("insert into ledger(cust_id,sales_id, description, debit, balance) values(?,?,?,?,?)");
					
					jdbcTemplate.update(strLedInsert.toString() , new Object[]{oldSalesBean.getCustId(), oldSalesBean.getSalesId(), 
							"New Bill Added", oldSalesBean.getNetAmt(), (balance + oldSalesBean.getNetAmt())});
					
					result=success;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public List<SalesBean> fetchAllBill(){
		List<SalesBean> salesBeanList = null;
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder sqlQuery = new StringBuilder(); 
			sqlQuery.append("select sales_id,bill_no,sales_dt,c.cust_id,c.cust_name,gross_amt,total_vat,net_amt,round_off_amt,tot_scheme ");
			sqlQuery.append("from sales s, customer c where status='SD' and s.cust_id=c.cust_id");
			
			salesBeanList = jdbcTemplate.query(sqlQuery.toString(), new RowMapper<SalesBean>() {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	            @Override
	            public SalesBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	SalesBean salesBean = new SalesBean();

	            	salesBean.setSalesId(rs.getLong("sales_id"));
	            	salesBean.setBillNo(rs.getString("bill_no"));
	            	salesBean.setBillDate(sdf.format(new java.util.Date(rs.getDate("sales_dt").getTime())));
	            	salesBean.setCustId(rs.getString("cust_id"));
	            	salesBean.setCustName(rs.getString("cust_name"));
	            	salesBean.setGrossAmt(rs.getDouble("gross_amt"));
	            	salesBean.setTotTax(rs.getDouble("total_vat"));
	            	salesBean.setNetAmt(rs.getDouble("net_amt"));
	            	salesBean.setRoundOffAmt(rs.getDouble("round_off_amt"));
	            	salesBean.setTotScheme(rs.getDouble("tot_scheme"));
					
	                return salesBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return salesBeanList;
	}
	
	public SalesBean fetchSales(long salesId){
		List<SalesBean> salesBeanList = null;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder sqlQuery = new StringBuilder(); 
			sqlQuery.append("select sales_id,bill_no,sales_dt,c.cust_id,c.cust_name,c.address,c.cust_code,c.vat_no,gross_amt,total_vat,net_amt,round_off_amt,tot_scheme ");
			sqlQuery.append("from sales s, customer c where sales_id=? and s.cust_id=c.cust_id");
			
			salesBeanList = jdbcTemplate.query(sqlQuery.toString(), new Object[]{salesId}, new RowMapper<SalesBean>() {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	            @Override
	            public SalesBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	SalesBean salesBean = new SalesBean();

	            	salesBean.setSalesId(rs.getLong("sales_id"));
	            	
	            	String billNo = rs.getString("bill_no");
	            	StringBuilder newBillNo = new StringBuilder();
	            	if(billNo.length() > 0){
	            		switch(billNo.length()){
						case 1: 
							newBillNo.append("0000");
							newBillNo.append(billNo);
							break;
						case 2:
							newBillNo.append("000");
							newBillNo.append(billNo);
							break;
						case 3:
							newBillNo.append("00");
							newBillNo.append(billNo);
							break;
						case 4:
							newBillNo.append("0");
							newBillNo.append(billNo);
							break;
	            		}
	            	}
	            	salesBean.setBillNo(newBillNo.toString());
	            	salesBean.setBillDate(sdf.format(new java.util.Date(rs.getDate("sales_dt").getTime())));
	            	salesBean.setCustId(rs.getString("cust_id"));
	            	salesBean.setCustName(rs.getString("cust_name"));
	            	salesBean.setCustCode(rs.getString("cust_code"));
	            	salesBean.setAddress(rs.getString("address"));
	            	salesBean.setVatNo(rs.getString("vat_no"));
	            	salesBean.setGrossAmt(rs.getDouble("gross_amt"));
	            	salesBean.setTotTax(rs.getDouble("total_vat"));
	            	salesBean.setNetAmt(rs.getDouble("net_amt"));
	            	salesBean.setRoundOffAmt(rs.getDouble("round_off_amt"));
	            	salesBean.setTotScheme(rs.getDouble("tot_scheme"));
	            	salesBean.setOldProdList(fetchProducts(rs.getLong("sales_id")));
	                
					return salesBean;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return salesBeanList.get(0);
	}
	
	public List<ProductBean> fetchProducts(long salesId){
		List<ProductBean> prodBeanList = null;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder sqlQuery = new StringBuilder(); 
			sqlQuery.append("select p.prod_id,p.prod_name,p.prod_code,p.man_id,m.man_name,p.pckg_size,pd.prod_dtl_id,pd.mrp,pd.rate,pd.cur_stock,pd.tax_id,t.tax_perc,");
			sqlQuery.append("spd.qty_sold,spd.disc1_perc,spd.disc1_val,spd.disc2_perc,spd.disc2_val,spd.scheme_perc,spd.scheme_val,spd.tax_val,spd.tot_amt,spd.new_rate ");
			sqlQuery.append("from product p,product_details pd,sales_prod_details spd,tax t,manufacturer m ");
			sqlQuery.append("where spd.prod_dtl_id = pd.prod_dtl_id and pd.prod_id = p.prod_id and pd.tax_id = t.tax_id and ");
			sqlQuery.append("p.man_id = m.man_id and spd.sales_id=?");
			
			prodBeanList = jdbcTemplate.query(sqlQuery.toString(), new Object[]{salesId}, new RowMapper<ProductBean>() {
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodForm = new ProductBean();

	            	prodForm.setProdId(rs.getString("p.prod_id"));
					prodForm.setProdName(rs.getString("p.prod_name"));
					prodForm.setProdCode(rs.getString("p.prod_code"));
					prodForm.setManId(rs.getString("p.man_id"));
					prodForm.setManName(rs.getString("m.man_name"));
					prodForm.setPckSize(rs.getString("p.pckg_size"));
					prodForm.setProdDtlId(rs.getInt("pd.prod_dtl_id"));
					prodForm.setMrp(rs.getDouble("pd.mrp"));
					if(rs.getString("spd.new_rate") != null && !rs.getString("spd.new_rate").equals("") && !rs.getString("spd.new_rate").equals("0") && !rs.getString("spd.new_rate").equalsIgnoreCase("null")){
						prodForm.setRate(rs.getDouble("spd.new_rate"));
					}else{
						prodForm.setRate(rs.getDouble("pd.rate"));
					}
					prodForm.setTaxId(rs.getInt("pd.tax_id"));
					prodForm.setTaxPerc(rs.getDouble("t.tax_perc"));
					prodForm.setQty(rs.getInt("spd.qty_sold"));
					prodForm.setDisc1(rs.getDouble("spd.disc1_perc"));
					prodForm.setDisc1Val(rs.getDouble("spd.disc1_val"));
					prodForm.setDisc2(rs.getDouble("spd.disc2_perc"));
					prodForm.setDisc2Val(rs.getDouble("spd.disc2_val"));
					prodForm.setScheme(rs.getDouble("spd.scheme_perc"));
					prodForm.setSchemeVal(rs.getDouble("spd.scheme_val"));
					prodForm.setTaxVal(rs.getDouble("spd.tax_val"));
					prodForm.setStock(rs.getInt("pd.cur_stock"));
					prodForm.setTotAmt(rs.getDouble("spd.tot_amt"));
					
	                return prodForm;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return prodBeanList;
	}
	
	public int deleteSales(String salesId){
		int result = 0;
		List<ProductBean> prodBeanList = null;
		
		try{
			StringBuilder strFetchStock = new StringBuilder();
			strFetchStock.append("select prod_dtl_id,qty_sold,scheme_perc,scheme_val from sales_prod_details where sales_id=?");
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			prodBeanList = jdbcTemplate.query(strFetchStock.toString(), new Object[]{salesId}, new RowMapper<ProductBean>() {
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodForm = new ProductBean();

	            	prodForm.setProdDtlId(rs.getInt("prod_dtl_id"));
					prodForm.setQty(rs.getInt("qty_sold"));
					prodForm.setScheme(rs.getDouble("scheme_perc"));
					prodForm.setSchemeVal(rs.getDouble("scheme_val"));
					
	                return prodForm;
	            }
	        });
			
			Iterator<ProductBean> itrProd = prodBeanList.iterator();
			String upQuery = "update product_details set cur_stock=cur_stock+? where prod_dtl_id=?";
			while(itrProd.hasNext()){
				ProductBean prodBean = itrProd.next();
				int qty = 0;
				if(prodBean.getScheme() - Double.valueOf(prodBean.getSchemeVal()).intValue() == 0){
					qty = prodBean.getQty() + Double.valueOf(prodBean.getSchemeVal()).intValue();
				}else{
					qty = prodBean.getQty();
				}
				jdbcTemplate.update(upQuery, new Object[]{qty, prodBean.getProdDtlId()});
			}
			
			result = jdbcTemplate.update("delete from sales_prod_details where sales_id=?", new Object[]{salesId});
			result = jdbcTemplate.update("delete from sales where sales_id=?", new Object[]{salesId});
			
			if(result > 0){
				System.out.println("Sales deleted successfully.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
