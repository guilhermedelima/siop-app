package br.gov.planejamento.siop_app.util;

import java.text.DecimalFormat;


public class Validator {

	public static String convertDouble(Double number){
		
		DecimalFormat formatter;
		
		formatter = new DecimalFormat("#,###,##0.00");
		
		return number == null ? "0,00" : formatter.format(number);
	}
	
	public static boolean checkPT(String pt){
		
		String regex = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{4}(\\.[0-9A-Z]{4}){2}";
		
		return pt != null && pt.matches(regex);
	}
	
	public static boolean checkUO(String uo){
		
		String regex = "[0-9]{5}";
		
		return uo != null && uo.matches(regex);
	}
}
