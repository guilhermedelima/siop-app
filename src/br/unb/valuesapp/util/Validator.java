package br.unb.valuesapp.util;

import java.text.DecimalFormat;


public class Validator {
	
	private static int START_YEAR = 2000;
	private static int LAST_YEAR = 2014;
	
	public static Integer convertYear(String number){
		Integer year;
		
		try{
			year = Integer.parseInt(number);
			
			if(year<START_YEAR || year>LAST_YEAR )
				year = null;
			
		}catch(NumberFormatException e){
			year = null;
		}
		
		return year;
	}

	public static String convertDouble(Double number){
		
		DecimalFormat formatter;
		
		formatter = new DecimalFormat("R$ #.00");
		
		return number == null ? "R$ 0,00" : formatter.format(number);
	}
}
