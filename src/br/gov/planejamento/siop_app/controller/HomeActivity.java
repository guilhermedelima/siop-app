package br.gov.planejamento.siop_app.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import br.gov.planejamento.siop_app.R;
import br.gov.planejamento.siop_app.util.Validator;

public class HomeActivity extends Activity {
	
	public static final String VALUE_PT = "pt";
	public static final String VALUE_UO = "uo";
	public static final String VALUE_YEAR = "ano";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		
		String pt, uo;
		int year;
		QueryActivity.SearchThread thread;
		
		pt = getIntent().getStringExtra(VALUE_PT);
		uo = getIntent().getStringExtra(VALUE_UO);
		year = getIntent().getIntExtra(VALUE_YEAR, 0);
		
		if( Validator.checkPT(pt) && Validator.checkUO(uo) && year>0 ){
			
			if( Validator.checkInternetAccess(this) ){
				thread = new QueryActivity.SearchThread(this, year, uo, pt);
				thread.execute();	
			}else{
				
				Validator.showDialogError(this, getString(R.string.internetAccessError));
			}
		}
	}
	
	public void nextActivity(View v){
		Intent nextIntent;
		int id;
		
		id = v.getId();
		nextIntent = new Intent(this, (id == R.id.buttonPT) ? QueryActivity.class : ClassifierActivity.class);
		startActivity(nextIntent);
	}

}
