package com.medical.app.configurations;

import javax.sql.DataSource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;

@Configuration
@ComponentScan(basePackages="com.medical.app")
public class MedicalAppConfig {
	
	public DataSource getDataSource(){
		DataSource dataSource = null;
		try{
			JndiTemplate jndiTemplate = new JndiTemplate();
			dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/pharma");
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataSource;
	}
}

