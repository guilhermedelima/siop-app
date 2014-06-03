package br.gov.planejamento.siop_app.util;

import java.text.DecimalFormat;


public class Validator {

	public static String convertDouble(Double number){
		
		DecimalFormat formatter;
		
		formatter = new DecimalFormat("#,###,##0.00");
		
		return number == null ? "0,00" : formatter.format(number);
	}
}
