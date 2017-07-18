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
import com.medical.app.model.ProductBean;
import com.medical.app.model.PurchaseBean;
import com.medical.app.model.TransactionBean;

@Service("purchaseService")
@Transactional
public class PurchaseService {
	@Autowired
	MedicalAppConfig appConfig;
	
	public List<ProductBean> fetchProductList(String prodId){
		List<ProductBean> prodBeanList = null;
		try
		{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select pd.prod_dtl_id,pd.mrp,pd.rate,pd.cur_stock,pd.tax_id,t.tax_perc,p.prod_code,p.prod_id from product p, product_details pd,tax t where ");
			strQuery.append(" p.prod_id=? and pd.prod_id=p.prod_id and t.tax_id=pd.tax_id order by pd.prod_dtl_id desc");
			
			prodBeanList = jdbcTemplate.query(strQuery.toString(), new Object[]{prodId}, new RowMapper<ProductBean>() {
				 
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodBean = new ProductBean();
	            	prodBean.setProdDtlId(rs.getInt("pd.prod_dtl_id"));
	            	prodBean.setMrp(rs.getDouble("pd.mrp"));
	            	prodBean.setMrpStr(rs.getString("pd.mrp"));
	            	prodBean.setRate(rs.getDouble("pd.rate"));
	            	prodBean.setStock(rs.getInt("pd.cur_stock"));
	            	prodBean.setTaxPerc(rs.getDouble("t.tax_perc"));
	            	prodBean.setTaxId(rs.getInt("pd.tax_id"));
	            	prodBean.setProdCode(rs.getString("p.prod_code"));
	            	prodBean.setProdId(rs.getString("p.prod_id"));
	            	
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
			strQuery.append("select disc1_perc, disc2_perc, scheme_perc from purchase_prod_details where prod_dtl_id = ? order by pur_prod_dtl_id desc limit 1");
			
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
			strQuery.append("select distinct p.prod_id,p.prod_code,p.pckg_size from product p ");
			strQuery.append(" where p.man_id=? and p.prod_name=?");
			
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
	
	public int savePurchase(PurchaseBean newPurBean, PurchaseBean oldPurBean){
		int result = 0;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		String startDate = "";
		String endDate = "";
		if(cal.get(cal.MONTH) <= 2 ){
			startDate = "01/04/"+(cal.get(cal.YEAR)-1);
			endDate = "31/03/"+cal.get(cal.YEAR);
		}else{
			startDate = "01/04/"+(cal.get(cal.YEAR));
			endDate = "31/03/"+(cal.get(cal.YEAR)+1);
		}
		
		try{
		
			if(newPurBean != null && oldPurBean != null && (oldPurBean.getOldProdList() == null || oldPurBean.getOldProdList().size() == 0)){
				System.out.println("inside if");
				String initialQry = "select pur_id from purchase where bill_no=? and dist_id=? and pur_dt between ? and ?";
				
				List<PurchaseBean> purValidList = jdbcTemplate.query(initialQry, new Object[]{ newPurBean.getBillNo(), newPurBean.getDistId(), new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()) }, new RowMapper<PurchaseBean>() {
					 
		            @Override
		            public PurchaseBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
		            	PurchaseBean purBean = new PurchaseBean();
		            	purBean.setPurId(rs.getLong("pur_id"));
		            	
		            	return purBean;
		            }
				});
				
				if(purValidList.size() == 0){
				
					StringBuilder strQuery = new StringBuilder(); 
					strQuery.append("insert into purchase(bill_no,dist_id,pur_dt,gross_amt,total_vat,net_amt,round_off_amt,status,pur_added_dt,tot_scheme) ");
					strQuery.append(" values(?,?,?,?,?,?,?,?,curdate(),?)");
						
					int success = jdbcTemplate.update(strQuery.toString(), new Object[]{newPurBean.getBillNo(), newPurBean.getDistId(), new java.sql.Date(sdf.parse(newPurBean.getBillDate()).getTime()), newPurBean.getGrossAmt(),
							newPurBean.getTotTax(), newPurBean.getNetAmt(), newPurBean.getRoundOffAmt(),"SD", newPurBean.getTotScheme()});
					
					if(success > 0){
						strQuery = new StringBuilder();
						
						strQuery.append("select pur_id from purchase where bill_no=? and dist_id=? and date(pur_added_dt)=date(curdate())");
						
						List<PurchaseBean> purIdList = jdbcTemplate.query(strQuery.toString(), new Object[]{ newPurBean.getBillNo(), newPurBean.getDistId() }, new RowMapper<PurchaseBean>() {
							 
				            @Override
				            public PurchaseBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
				            	PurchaseBean purBean = new PurchaseBean();
				            	purBean.setPurId(rs.getLong("pur_id"));
				            	
				            	return purBean;
				            }
						});
						
						long newPurId = purIdList.get(0).getPurId();
						
						Iterator<ProductBean> itrNewProd = newPurBean.getProdList().iterator();
						
						while(itrNewProd.hasNext()){
							ProductBean tempProd = itrNewProd.next();
							strQuery = new StringBuilder();
							
							strQuery.append("select count(*) prodDtlCount from product_details where prod_id=? and mrp=? and rate=?");
							
							List<ProductBean> prodList = jdbcTemplate.query(strQuery.toString(), new Object[]{ tempProd.getProdId(), tempProd.getMrp(), tempProd.getRate() }, new RowMapper<ProductBean>() {

								@Override
					            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
					            	ProductBean prodBean = new ProductBean();
					            	prodBean.setCount(rs.getInt("prodDtlCount"));
					            	
					            	return prodBean;
					            }
							});
							
							if(prodList.get(0).getCount() == 0){
								jdbcTemplate.update("insert into product_details(prod_id,mrp,rate,cur_stock,tax_id) values(?,?,?,?,?)"
								, new Object[]{tempProd.getProdId(), tempProd.getMrp(), tempProd.getRate(),0, tempProd.getTaxId()});
								
								strQuery = new StringBuilder();
								
								strQuery.append("select prod_dtl_id from product_details where prod_id=? and mrp=? and rate=?");
								
								List<ProductBean> prodDtlList = jdbcTemplate.query(strQuery.toString(), new Object[]{ tempProd.getProdId(), tempProd.getMrp(), tempProd.getRate() }, new RowMapper<ProductBean>() {

									@Override
						            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
						            	ProductBean prodBean = new ProductBean();
						            	prodBean.setProdDtlId(rs.getInt("prod_dtl_id"));
						            	
						            	return prodBean;
						            }
								});
								
								tempProd.setProdDtlId(prodDtlList.get(0).getProdDtlId());
							}
							
							
							strQuery = new StringBuilder();
							
							strQuery.append("insert into purchase_prod_details(prod_dtl_id,pur_id,qty_pur,disc1_perc,disc1_val,disc2_perc,disc2_val,scheme_perc,scheme_val,tax_val,tot_amt) ");
							strQuery.append(" values(?,?,?,?,?,?,?,?,?,?,?)");

							success = jdbcTemplate.update(strQuery.toString(), new Object[]{tempProd.getProdDtlId(), newPurId, tempProd.getQty(),
								tempProd.getDisc1(),tempProd.getDisc1Val(),tempProd.getDisc2(), tempProd.getDisc2Val(), tempProd.getScheme(), tempProd.getSchemeVal(),
								tempProd.getTaxVal(),tempProd.getTotAmt()});
							
							if(success > 0){
								/*int qty = 0;
								if(tempProd.getScheme() == 0.5){
									qty = tempProd.getQty();
								}else{
									qty = tempProd.getQty()+1;
								}
								jdbcTemplate.update("update product_details set cur_stock=cur_stock+? where prod_dtl_id=?", new Object[]{qty, tempProd.getProdDtlId()});*/
							}
						}
						
						result = success;
					}
				}else{
					result = -1;
				}
			}else{
				System.out.println("Inside else");
				StringBuilder strQuery = new StringBuilder(); 
				strQuery.append("update purchase set bill_no=?,dist_id=?,pur_dt=?,gross_amt=?,total_vat=?,net_amt=?,round_off_amt=?,status=?,tot_scheme=? ");
				strQuery.append(" where pur_id=?");
					
				int success = jdbcTemplate.update(strQuery.toString(), new Object[]{oldPurBean.getBillNo(), oldPurBean.getDistId(), new java.sql.Date(sdf.parse(oldPurBean.getBillDate()).getTime()), oldPurBean.getGrossAmt(),
						oldPurBean.getTotTax(), oldPurBean.getNetAmt(), oldPurBean.getRoundOffAmt(),"SD",oldPurBean.getTotScheme(), oldPurBean.getPurId()});
				
				if(success > 0){
					Map<Integer, ProductBean> oldProdMap = new HashMap<Integer, ProductBean>();
					Map<Integer, ProductBean> newProdMap = new HashMap<Integer, ProductBean>();
					
					Iterator<ProductBean> itrProd = oldPurBean.getProdList().iterator();
					Iterator<ProductBean> itrOldProd = oldPurBean.getOldProdList().iterator();
					
					System.out.println("oldlist size="+oldPurBean.getOldProdList().size());
					System.out.println("newlist size="+oldPurBean.getProdList().size());
	
					while(itrOldProd.hasNext()){
						ProductBean oldProdForm = itrOldProd.next();
						
						oldProdMap.put(oldProdForm.getProdDtlId(), oldProdForm);
						System.out.println("oldProdForm.getProdDtlId() Mapadded="+oldProdForm.getProdDtlId());
					}
					
					while(itrProd.hasNext()){
						ProductBean prodForm = itrProd.next();
						
						newProdMap.put(prodForm.getProdDtlId() , prodForm);
						System.out.println("prodForm.getProdDtlId() map="+prodForm.getProdDtlId());
					}
					itrProd = null;
					itrOldProd = null;
					itrProd = oldPurBean.getProdList().iterator();
					itrOldProd = oldPurBean.getOldProdList().iterator();
					
					while(itrOldProd.hasNext()){
						ProductBean prodBean = itrOldProd.next();
						System.out.println("Iter old="+prodBean.getProdDtlId());
						if(newProdMap.containsKey(prodBean.getProdDtlId())){
							System.out.println("Inside old map");
							ProductBean newProdBean = newProdMap.get(prodBean.getProdDtlId());
							
							strQuery = new StringBuilder();
	
							strQuery.append("update purchase_prod_details set qty_pur=?,disc1_perc=?,disc1_val=?,disc2_perc=?,disc2_val=?,scheme_perc=?,scheme_val=?,tax_val=?,tot_amt=? ");
							strQuery.append(" where pur_id=? and prod_dtl_id=?");
	
							success = jdbcTemplate.update(strQuery.toString(), new Object[]{newProdBean.getQty(), newProdBean.getDisc1(), newProdBean.getDisc1Val(), newProdBean.getDisc2(), newProdBean.getDisc2Val(),
								newProdBean.getScheme(), newProdBean.getSchemeVal(),newProdBean.getTaxVal(), newProdBean.getTotAmt(), oldPurBean.getPurId(), newProdBean.getProdDtlId()});
							
							if(success > 0){
								jdbcTemplate.update("update product_details set tax_id=? where prod_dtl_id=?", new Object[]{newProdBean.getTaxId(), newProdBean.getProdDtlId()});
							}
						}else{
							success = 0;
							
							success = jdbcTemplate.update("delete from purchase_prod_details where pur_id=? and prod_dtl_id=?", new Object[]{ oldPurBean.getPurId(), prodBean.getProdDtlId()});
							
							if(success > 0){
								String strQuery1 = "select count(*) count from purchase_prod_details where prod_dtl_id=?";
								List<ProductBean> prodDtlList = jdbcTemplate.query(strQuery1, new Object[]{ prodBean.getProdDtlId()}, new RowMapper<ProductBean>() {

									@Override
						            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
						            	ProductBean prodBean = new ProductBean();
						            	prodBean.setCount(rs.getInt("count"));
						            	
						            	return prodBean;
						            }
								});
								
								if(prodDtlList.get(0).getCount() == 0){
									jdbcTemplate.update("delete from product_details where prod_dtl_id=?", new Object[]{prodBean.getProdDtlId()});
								}
							}
						}
					}
					
					while(itrProd.hasNext()){
						ProductBean prodBean = itrProd.next();
						System.out.println("Inside new Map");
						System.out.println("Iter New="+prodBean.getProdDtlId());
						if(oldProdMap.containsKey(prodBean.getProdDtlId())){
							//do nothing
							System.out.println("do nothing");
						}else{
							strQuery = new StringBuilder();
							
							strQuery.append("select count(*) prodDtlCount from product_details where prod_id=? and mrp=? and rate=?");
							
							List<ProductBean> prodList = jdbcTemplate.query(strQuery.toString(), new Object[]{ prodBean.getProdId(), prodBean.getMrp(), prodBean.getRate() }, new RowMapper<ProductBean>() {

								@Override
					            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
					            	ProductBean prodBean = new ProductBean();
					            	prodBean.setCount(rs.getInt("prodDtlCount"));
					            	
					            	return prodBean;
					            }
							});
							
							if(prodList.get(0).getCount() == 0){
								jdbcTemplate.update("insert into product_details(prod_id,mrp,rate,cur_stock,tax_id) values(?,?,?,?,?)"
								, new Object[]{prodBean.getProdId(), prodBean.getMrp(), prodBean.getRate(),0, prodBean.getTaxId()});
								
								strQuery = new StringBuilder();
								
								strQuery.append("select prod_dtl_id from product_details where prod_id=? and mrp=? and rate=?");
								
								List<ProductBean> prodDtlList = jdbcTemplate.query(strQuery.toString(), new Object[]{ prodBean.getProdId(), prodBean.getMrp(), prodBean.getRate() }, new RowMapper<ProductBean>() {

									@Override
						            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
						            	ProductBean prodBean = new ProductBean();
						            	prodBean.setProdDtlId(rs.getInt("prod_dtl_id"));
						            	
						            	return prodBean;
						            }
								});
								
								prodBean.setProdDtlId(prodDtlList.get(0).getProdDtlId());
							}
							
							strQuery = new StringBuilder();
	
							strQuery.append("insert into purchase_prod_details(prod_dtl_id,pur_id,qty_pur,disc1_perc,disc1_val,disc2_perc,disc2_val,scheme_perc,scheme_val,tax_val,tot_amt) ");
							strQuery.append(" values(?,?,?,?,?,?,?,?,?,?,?)");
	
							success = jdbcTemplate.update(strQuery.toString(), new Object[]{prodBean.getProdDtlId(), oldPurBean.getPurId(), prodBean.getQty(),
								prodBean.getDisc1(),prodBean.getDisc1Val(),prodBean.getDisc2(), prodBean.getDisc2Val(), prodBean.getScheme(), prodBean.getSchemeVal(),
								prodBean.getTaxVal(),prodBean.getTotAmt()});
							
							if(success > 0){
								jdbcTemplate.update("update product_details set tax_id=? where prod_dtl_id=?", new Object[]{prodBean.getTaxId(), prodBean.getProdDtlId()});
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
	
	public int submitPurchase(PurchaseBean newPurBean, PurchaseBean oldPurBean){
		int result = 0;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		String startDate = "";
		String endDate = "";
		if(cal.get(cal.MONTH) <= 2 ){
			startDate = "01/04/"+(cal.get(cal.YEAR)-1);
			endDate = "31/03/"+cal.get(cal.YEAR);
		}else{
			startDate = "01/04/"+(cal.get(cal.YEAR));
			endDate = "31/03/"+(cal.get(cal.YEAR)+1);
		}
		
		try{
			if(newPurBean != null && oldPurBean != null && (oldPurBean.getOldProdList() == null || oldPurBean.getOldProdList().size() == 0)){
				String initialQry = "select pur_id from purchase where bill_no=? and dist_id=? and pur_dt between ? and ?";
				
				List<PurchaseBean> purValidList = jdbcTemplate.query(initialQry, new Object[]{ newPurBean.getBillNo(), newPurBean.getDistId(), new java.sql.Date(sdf.parse(startDate).getTime()), new java.sql.Date(sdf.parse(endDate).getTime()) }, new RowMapper<PurchaseBean>() {
					 
		            @Override
		            public PurchaseBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
		            	PurchaseBean purBean = new PurchaseBean();
		            	purBean.setPurId(rs.getLong("pur_id"));
		            	
		            	return purBean;
		            }
				});
				
				if(purValidList.size() == 0){
					StringBuilder strQuery = new StringBuilder(); 
					strQuery.append("insert into purchase(bill_no,dist_id,pur_dt,gross_amt,total_vat,net_amt,round_off_amt,status,pur_added_dt,tot_scheme) ");
					strQuery.append(" values(?,?,?,?,?,?,?,?,curdate(),?)");
						
					int success = jdbcTemplate.update(strQuery.toString(), new Object[]{newPurBean.getBillNo(), newPurBean.getDistId(), new java.sql.Date(sdf.parse(newPurBean.getBillDate()).getTime()), newPurBean.getGrossAmt(),
							newPurBean.getTotTax(), newPurBean.getNetAmt(), newPurBean.getRoundOffAmt(),"ST",newPurBean.getTotScheme()});
					
					if(success > 0){
						strQuery = new StringBuilder();
						
						strQuery.append("select pur_id from purchase where bill_no=? and dist_id=? and date(pur_added_dt)=date(curdate())");
						
						List<PurchaseBean> purIdList = jdbcTemplate.query(strQuery.toString(), new Object[]{ newPurBean.getBillNo(), newPurBean.getDistId() }, new RowMapper<PurchaseBean>() {
							 
				            @Override
				            public PurchaseBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
				            	PurchaseBean purBean = new PurchaseBean();
				            	purBean.setPurId(rs.getLong("pur_id"));
				            	
				            	return purBean;
				            }
						});
						
						long newPurId = purIdList.get(0).getPurId();
						
						Iterator<ProductBean> itrNewProd = newPurBean.getProdList().iterator();
						
						while(itrNewProd.hasNext()){
							ProductBean tempProd = itrNewProd.next();
							
							strQuery = new StringBuilder();
							
							strQuery.append("select count(*) prodDtlCount from product_details where prod_id=? and mrp=? and rate=?");
							
							List<ProductBean> prodList = jdbcTemplate.query(strQuery.toString(), new Object[]{ tempProd.getProdId(), tempProd.getMrp(), tempProd.getRate() }, new RowMapper<ProductBean>() {

								@Override
					            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
					            	ProductBean prodBean = new ProductBean();
					            	prodBean.setCount(rs.getInt("prodDtlCount"));
					            	
					            	return prodBean;
					            }
							});
							
							if(prodList.get(0).getCount() == 0){
								jdbcTemplate.update("insert into product_details(prod_id,mrp,rate,cur_stock,tax_id) values(?,?,?,?,?)"
								, new Object[]{tempProd.getProdId(), tempProd.getMrp(), tempProd.getRate(),0, tempProd.getTaxId()});
								
								strQuery = new StringBuilder();
								
								strQuery.append("select prod_dtl_id from product_details where prod_id=? and mrp=? and rate=?");
								
								List<ProductBean> prodDtlList = jdbcTemplate.query(strQuery.toString(), new Object[]{ tempProd.getProdId(), tempProd.getMrp(), tempProd.getRate() }, new RowMapper<ProductBean>() {

									@Override
						            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
						            	ProductBean prodBean = new ProductBean();
						            	prodBean.setProdDtlId(rs.getInt("prod_dtl_id"));
						            	
						            	return prodBean;
						            }
								});
								
								tempProd.setProdDtlId(prodDtlList.get(0).getProdDtlId());
								
								System.out.println("prodDtl"+tempProd.getProdDtlId());
							}
							
							strQuery = new StringBuilder();
							
							strQuery.append("insert into purchase_prod_details(prod_dtl_id,pur_id,qty_pur,disc1_perc,disc1_val,disc2_perc,disc2_val,scheme_perc,scheme_val,tax_val,tot_amt) ");
							strQuery.append(" values(?,?,?,?,?,?,?,?,?,?,?)");
							
							success = jdbcTemplate.update(strQuery.toString(), new Object[]{tempProd.getProdDtlId(), newPurId, tempProd.getQty(),
								tempProd.getDisc1(),tempProd.getDisc1Val(),tempProd.getDisc2(), tempProd.getDisc2Val(), tempProd.getScheme(), tempProd.getSchemeVal(),
								tempProd.getTaxVal(),tempProd.getTotAmt()});
							
							if(success > 0){
								int qty = 0;
								if(tempProd.getScheme() == 0.5){
									qty = tempProd.getQty();
								}else{
									qty = tempProd.getQty() + new Double(tempProd.getSchemeVal()).intValue();
								}
								jdbcTemplate.update("update product_details set cur_stock=cur_stock+?,tax_id=? where prod_dtl_id=?", new Object[]{qty, tempProd.getTaxId(), tempProd.getProdDtlId()});
							}
						}
						
						double balance = 0;
						
						String sql = "select ifNull(balance,0) balance from ledger where ledger_id = (select max(ledger_id) from ledger where dist_id=?)";
						
						List<TransactionBean> balList = jdbcTemplate.query(sql, new Object[]{newPurBean.getDistId()}, new RowMapper<TransactionBean>() {
							 
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
						
						strLedInsert.append("insert into ledger(dist_id,pur_id, description, credit, balance) values(?,?,?,?,?)");
						
						jdbcTemplate.update(strLedInsert.toString() , new Object[]{newPurBean.getDistId(), newPurId, 
								"New Bill Added", newPurBean.getNetAmt(), (balance + newPurBean.getNetAmt())});
						
						result = success;
					}
				}else{
					result = -1;
				}
			}else{
				StringBuilder strQuery = new StringBuilder(); 
				strQuery.append("update purchase set bill_no=?,dist_id=?,pur_dt=?,gross_amt=?,total_vat=?,net_amt=?,round_off_amt=?,status=?,tot_scheme=? ");
				strQuery.append(" where pur_id=?");
					
				int success = jdbcTemplate.update(strQuery.toString(), new Object[]{oldPurBean.getBillNo(), oldPurBean.getDistId(), new java.sql.Date(sdf.parse(oldPurBean.getBillDate()).getTime()), oldPurBean.getGrossAmt(),
						oldPurBean.getTotTax(), oldPurBean.getNetAmt(), oldPurBean.getRoundOffAmt(),"ST",oldPurBean.getTotScheme(), oldPurBean.getPurId()});
				
				if(success > 0){
					Map<Integer, ProductBean> oldProdMap = new HashMap<Integer, ProductBean>();
					Map<Integer, ProductBean> newProdMap = new HashMap<Integer, ProductBean>();
					
					Iterator<ProductBean> itrProd = oldPurBean.getProdList().iterator();
					Iterator<ProductBean> itrOldProd = oldPurBean.getOldProdList().iterator();
	
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
					itrProd = oldPurBean.getProdList().iterator();
					itrOldProd = oldPurBean.getOldProdList().iterator();
					
					while(itrOldProd.hasNext()){
						ProductBean prodBean = itrOldProd.next();
						System.out.println("Iter old="+prodBean.getProdDtlId());
						
						if(newProdMap.containsKey(prodBean.getProdDtlId())){
							ProductBean newProdBean = newProdMap.get(prodBean.getProdDtlId());
							System.out.println("Iter new prod id="+newProdBean.getProdDtlId());	
							strQuery = new StringBuilder();
	
							strQuery.append("update purchase_prod_details set qty_pur=?,disc1_perc=?,disc1_val=?,disc2_perc=?,disc2_val=?,scheme_perc=?,scheme_val=?,tax_val=?,tot_amt=? ");
							strQuery.append(" where pur_id=? and prod_dtl_id=?");
	
							success = jdbcTemplate.update(strQuery.toString(), new Object[]{newProdBean.getQty(), newProdBean.getDisc1(), newProdBean.getDisc1Val(), newProdBean.getDisc2(), newProdBean.getDisc2Val(),
								newProdBean.getScheme(), newProdBean.getSchemeVal(),newProdBean.getTaxVal(), newProdBean.getTotAmt(), oldPurBean.getPurId(), newProdBean.getProdDtlId()});
							
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
								
								int stockToAdd = qty - oldQty;
								
								System.out.println("quantity to add="+qty);
								
								jdbcTemplate.update("update product_details set cur_stock=cur_stock+(?),tax_id=? where prod_dtl_id=?", new Object[]{qty, newProdBean.getTaxId(), newProdBean.getProdDtlId()});
							}
						}else{
							success = 0;
							
							success = jdbcTemplate.update("delete from purchase_prod_details where pur_id=? and prod_dtl_id=?", new Object[]{ oldPurBean.getPurId(), prodBean.getProdDtlId()});
							
							if(success > 0){
								String strQuery1 = "select count(*) count from purchase_prod_details where prod_dtl_id=?";
								List<ProductBean> prodDtlList = jdbcTemplate.query(strQuery1, new Object[]{ prodBean.getProdDtlId()}, new RowMapper<ProductBean>() {

									@Override
						            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
						            	ProductBean prodBean = new ProductBean();
						            	prodBean.setCount(rs.getInt("count"));
						            	
						            	return prodBean;
						            }
								});
								
								if(prodDtlList.get(0).getCount() == 0){
									jdbcTemplate.update("delete from product_details where prod_dtl_id=?", new Object[]{prodBean.getProdDtlId()});
								}
							}
							
						}
					}
					
					while(itrProd.hasNext()){
						ProductBean prodBean = itrProd.next();
						
						if(oldProdMap.containsKey(prodBean.getProdDtlId())){
							//do nothing
						}else{
							strQuery = new StringBuilder();
							
							strQuery.append("select count(*) prodDtlCount from product_details where prod_id=? and mrp=? and rate=?");
							
							List<ProductBean> prodList = jdbcTemplate.query(strQuery.toString(), new Object[]{ prodBean.getProdId(), prodBean.getMrp(), prodBean.getRate() }, new RowMapper<ProductBean>() {

								@Override
					            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
					            	ProductBean prodBean = new ProductBean();
					            	prodBean.setCount(rs.getInt("prodDtlCount"));
					            	
					            	return prodBean;
					            }
							});
							
							if(prodList.get(0).getCount() == 0){
								jdbcTemplate.update("insert into product_details(prod_id,mrp,rate,cur_stock,tax_id) values(?,?,?,?,?)"
								, new Object[]{prodBean.getProdId(), prodBean.getMrp(), prodBean.getRate(),0, prodBean.getTaxId()});
								
								strQuery = new StringBuilder();
								
								strQuery.append("select prod_dtl_id from product_details where prod_id=? and mrp=? and rate=?");
								
								List<ProductBean> prodDtlList = jdbcTemplate.query(strQuery.toString(), new Object[]{ prodBean.getProdId(), prodBean.getMrp(), prodBean.getRate() }, new RowMapper<ProductBean>() {

									@Override
						            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
						            	ProductBean prodBean = new ProductBean();
						            	prodBean.setProdDtlId(rs.getInt("prod_dtl_id"));
						            	
						            	return prodBean;
						            }
								});
								
								prodBean.setProdDtlId(prodDtlList.get(0).getProdDtlId());
								
								System.out.println("prodDtl"+prodBean.getProdDtlId());
							}
							
							strQuery = new StringBuilder();
	
							strQuery.append("insert into purchase_prod_details(prod_dtl_id,pur_id,qty_pur,disc1_perc,disc1_val,disc2_perc,disc2_val,scheme_perc,scheme_val,tax_val,tot_amt) ");
							strQuery.append(" values(?,?,?,?,?,?,?,?,?,?,?)");
	
							success = jdbcTemplate.update(strQuery.toString(), new Object[]{prodBean.getProdDtlId(), oldPurBean.getPurId(), prodBean.getQty(),
								prodBean.getDisc1(),prodBean.getDisc1Val(),prodBean.getDisc2(), prodBean.getDisc2Val(), prodBean.getScheme(), prodBean.getSchemeVal(),
								prodBean.getTaxVal(),prodBean.getTotAmt()});
							
							if(success > 0){
								int qty = 0;
								if(prodBean.getScheme() == 0.5){
									qty = prodBean.getQty();
								}else{
									qty = prodBean.getQty() + new Double(prodBean.getSchemeVal()).intValue();
								}
								jdbcTemplate.update("update product_details set cur_stock=cur_stock+?,tax_id=? where prod_dtl_id=?", new Object[]{qty, prodBean.getTaxId(), prodBean.getProdDtlId()});
							}
						}
					}
					
					double balance = 0;
					
					String sql = "select ifNull(balance,0) balance from ledger where ledger_id = (select max(ledger_id) from ledger where dist_id=?)";
					
					List<TransactionBean> balList = jdbcTemplate.query(sql, new Object[]{oldPurBean.getDistId()}, new RowMapper<TransactionBean>() {
						 
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
					
					strLedInsert.append("insert into ledger(dist_id,pur_id, description, credit, balance) values(?,?,?,?,?)");
					
					jdbcTemplate.update(strLedInsert.toString() , new Object[]{oldPurBean.getDistId(), oldPurBean.getPurId(), 
							"New Bill Added", oldPurBean.getNetAmt(), (balance + oldPurBean.getNetAmt())});
					
					result=success;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public List<PurchaseBean> fetchAllBill(){
		List<PurchaseBean> purBeanList = null;
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder sqlQuery = new StringBuilder(); 
			sqlQuery.append("select pur_id,bill_no,pur_dt,d.dist_id,d.dist_name,gross_amt,total_vat,net_amt,round_off_amt,tot_scheme ");
			sqlQuery.append("from purchase p, distributor d where status='SD' and p.dist_id=d.dist_id");
			
			purBeanList = jdbcTemplate.query(sqlQuery.toString(), new RowMapper<PurchaseBean>() {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	            @Override
	            public PurchaseBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	PurchaseBean purBean = new PurchaseBean();

	            	purBean.setPurId(rs.getLong("pur_id"));
	            	purBean.setBillNo(rs.getString("bill_no"));
	            	purBean.setBillDate(sdf.format(new java.util.Date(rs.getDate("pur_dt").getTime())));
	            	purBean.setDistId(rs.getString("dist_id"));
	            	purBean.setDistName(rs.getString("dist_name"));
	            	purBean.setGrossAmt(rs.getDouble("gross_amt"));
	            	purBean.setTotTax(rs.getDouble("total_vat"));
	            	purBean.setNetAmt(rs.getDouble("net_amt"));
	            	purBean.setRoundOffAmt(rs.getDouble("round_off_amt"));
	            	purBean.setTotScheme(rs.getDouble("tot_scheme"));
					
	                return purBean;
	            }
	             
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return purBeanList;
	}
	
	public PurchaseBean fetchPurchase(long purId){
		List<PurchaseBean> purBeanList = null;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder sqlQuery = new StringBuilder(); 
			sqlQuery.append("select pur_id,bill_no,pur_dt,d.dist_id,d.dist_name,gross_amt,tot_scheme,total_vat,net_amt,round_off_amt,tot_scheme ");
			sqlQuery.append("from purchase p, distributor d where pur_id=? and p.dist_id=d.dist_id");
			
			purBeanList = jdbcTemplate.query(sqlQuery.toString(), new Object[]{purId}, new RowMapper<PurchaseBean>() {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	            @Override
	            public PurchaseBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	PurchaseBean purBean = new PurchaseBean();

	            	purBean.setPurId(rs.getLong("pur_id"));
	            	purBean.setBillNo(rs.getString("bill_no"));
	            	purBean.setBillDate(sdf.format(new java.util.Date(rs.getDate("pur_dt").getTime())));
	            	purBean.setDistId(rs.getString("dist_id"));
	            	purBean.setDistName(rs.getString("dist_name"));
	            	purBean.setGrossAmt(rs.getDouble("gross_amt"));
	            	purBean.setTotTax(rs.getDouble("total_vat"));
	            	purBean.setNetAmt(rs.getDouble("net_amt"));
	            	purBean.setRoundOffAmt(rs.getDouble("round_off_amt"));
	            	purBean.setTotScheme(rs.getDouble("tot_scheme"));
					purBean.setOldProdList(fetchProducts(rs.getLong("pur_id")));
	                
					return purBean;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return purBeanList.get(0);
	}
	
	public List<ProductBean> fetchProducts(long purId){
		List<ProductBean> prodBeanList = null;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			
			StringBuilder sqlQuery = new StringBuilder(); 
			sqlQuery.append("select p.prod_id,p.prod_name,p.prod_code,p.man_id,m.man_name,p.pckg_size,pd.prod_dtl_id,pd.mrp,pd.rate,pd.cur_stock,pd.tax_id,t.tax_perc,");
			sqlQuery.append("ppd.qty_pur,ppd.disc1_perc,ppd.disc1_val,ppd.disc2_perc,ppd.disc2_val,ppd.scheme_perc,ppd.scheme_val,ppd.tax_val,ppd.tot_amt ");
			sqlQuery.append("from product p,product_details pd,purchase_prod_details ppd,tax t,manufacturer m ");
			sqlQuery.append("where ppd.prod_dtl_id = pd.prod_dtl_id and pd.prod_id = p.prod_id and pd.tax_id = t.tax_id and ");
			sqlQuery.append("p.man_id = m.man_id and ppd.pur_id=?");
			
			prodBeanList = jdbcTemplate.query(sqlQuery.toString(), new Object[]{purId}, new RowMapper<ProductBean>() {
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
					prodForm.setRate(rs.getDouble("pd.rate"));
					prodForm.setTaxId(rs.getInt("pd.tax_id"));
					prodForm.setTaxPerc(rs.getDouble("t.tax_perc"));
					prodForm.setQty(rs.getInt("ppd.qty_pur"));
					prodForm.setDisc1(rs.getDouble("ppd.disc1_perc"));
					prodForm.setDisc1Val(rs.getDouble("ppd.disc1_val"));
					prodForm.setDisc2(rs.getDouble("ppd.disc2_perc"));
					prodForm.setDisc2Val(rs.getDouble("ppd.disc2_val"));
					prodForm.setScheme(rs.getDouble("ppd.scheme_perc"));
					prodForm.setSchemeVal(rs.getDouble("ppd.scheme_val"));
					prodForm.setTaxVal(rs.getDouble("ppd.tax_val"));
					prodForm.setStock(rs.getInt("pd.cur_stock"));
					prodForm.setTotAmt(rs.getDouble("ppd.tot_amt"));
					
	                return prodForm;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
		}
		return prodBeanList;
	}
	
	public int deletePurchase(String purId){
		int result = 0;
		//List<ProductBean> prodBeanList = null;
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
			/*StringBuilder strFetchStock = new StringBuilder();
			strFetchStock.append("select prod_dtl_id,qty_pur,scheme_perc,scheme_val from purchase_prod_details where pur_id=?");
			
			prodBeanList = jdbcTemplate.query(strFetchStock.toString(), new Object[]{purId}, new RowMapper<ProductBean>() {
	            @Override
	            public ProductBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
	            	ProductBean prodForm = new ProductBean();

	            	prodForm.setProdDtlId(rs.getInt("prod_dtl_id"));
					prodForm.setQty(rs.getInt("qty_pur"));
					prodForm.setScheme(rs.getDouble("scheme_perc"));
					prodForm.setSchemeVal(rs.getDouble("scheme_val"));
					
	                return prodForm;
	            }
	        });
			
			Iterator<ProductBean> itrProd = prodBeanList.iterator();
			String upQuery = "update product_details set cur_stock=cur_stock-? where prod_dtl_id=?";
			while(itrProd.hasNext()){
				ProductBean prodBean = itrProd.next();
				int qty = 0;
				if(prodBean.getScheme() - Double.valueOf(prodBean.getSchemeVal()).intValue() == 0){
					qty = prodBean.getQty() + Double.valueOf(prodBean.getSchemeVal()).intValue();
				}else{
					qty = prodBean.getQty();
				}
				jdbcTemplate.update(upQuery, new Object[]{qty, prodBean.getProdDtlId()});
			}*/
			
			result = jdbcTemplate.update("delete from purchase_prod_details where pur_id=?", new Object[]{purId});
			result = jdbcTemplate.update("delete from purchase where pur_id=?", new Object[]{purId});
			
			if(result > 0){
				System.out.println("purchase deleted successfully.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
