package com.medical.app.controllers;

import java.util.ArrayList;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.medical.app.model.SalesBean;

public class SalesBeanDataProvider extends 
	JRAbstractBeanDataSourceProvider {

		public SalesBeanDataProvider() {
			super(SalesBean.class);
			// TODO Auto-generated constructor stub
		}

		public JRDataSource create(JasperReport report)throws JRException {
	    
			ArrayList collection = new ArrayList();
			SalesBean salForm = new SalesBean();
			salForm.setCustCode("C001");
			collection.add(salForm);
			return new JRBeanCollectionDataSource(collection);
		}

	public void dispose(JRDataSource dataSource) 
	    throws JRException {
	// nothing to dispose
	}
}
