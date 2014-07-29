package br.gov.planejamento.siop_app.util;

import java.text.DecimalFormat;

import br.gov.planejamento.siop_app.R;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Validator {

	public static String convertDouble(Double number){
		
		DecimalFormat formatter;
		
		formatter = new DecimalFormat("R$ #,###,##0.00");
		
		return number == null ? "R$ 0,00" : formatter.format(number);
	}
	
	public static boolean checkPT(String pt){
		
		String regex = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{4}(\\.[0-9A-Z]{4}){2}";
		
		return pt != null && pt.matches(regex);
	}
	
	public static boolean checkUO(String uo){
		
		String regex = "[0-9]{5}";
		
		return uo != null && uo.matches(regex);
	}
	
	public static boolean checkName(String name){
		
		String regex = "^[a-zA-Z0-9][a-zA-Z0-9 ]{2,19}$";
		
		return name != null && name.matches(regex);
	}
	
	public static void showDialogError(Context ActivityContext, String message){
		AlertDialog.Builder builder;
		AlertDialog dialog;
		
		builder = new AlertDialog.Builder(ActivityContext);
		builder.setTitle(ActivityContext.getString(R.string.titleError));
		builder.setMessage(message);
		builder.setPositiveButton("OK", null);
		
		dialog = builder.create();
		dialog.show();
	}
	
	public static boolean checkInternetAccess(Context myContext){
		
		ConnectivityManager manager;
		NetworkInfo info;

		manager = (ConnectivityManager) myContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		info = manager.getActiveNetworkInfo();
		
		return info!=null && info.isConnectedOrConnecting();
	}
}
