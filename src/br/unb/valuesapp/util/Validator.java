package br.unb.valuesapp.util;


public class Validator {
	
	private static int START_YEAR = 2000;
	private static int LAST_YEAR = 2014;
	
	public static Integer convertYear(String number){
		Integer year;
		
		try{
			year = Integer.parseInt(number);
			
		}catch(NumberFormatException e){
			year = null;
		}
		
		return year>=START_YEAR && year<=LAST_YEAR ? year : 0;
	}

}
