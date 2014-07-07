package br.gov.planejamento.siop_app.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import br.gov.planejamento.siop_app.R;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
	}
	
	public void nextActivity(View v){
		Intent nextIntent;
		int id;
		
		id = v.getId();
		nextIntent = new Intent(this, (id == R.id.buttonPT) ? QueryActivity.class : ClassifierActivity.class);
		startActivity(nextIntent);
	}

}
