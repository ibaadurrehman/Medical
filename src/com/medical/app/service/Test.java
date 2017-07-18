package com.medical.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		System.out.println(cal);
		System.out.println(cal.get(cal.DAY_OF_MONTH));
		System.out.println((cal.get(cal.MONTH)));
		System.out.println(Calendar.MARCH);
		System.out.println((cal.get(cal.MONTH)) > Calendar.MARCH);
		System.out.println(cal.get(Calendar.HOUR_OF_DAY));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String str = "01/04/"+(cal.get(cal.YEAR)-1)+" 23:59:59";
		try {
			System.out.println(sdf.parse(str));
			
			String sst = "001";
			
			System.out.println("lenght="+sst.length());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
