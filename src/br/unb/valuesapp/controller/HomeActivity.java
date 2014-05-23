package br.unb.valuesapp.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import br.unb.valuesapp.R;
import br.unb.valuesapp.data.ValuesDAO;
import br.unb.valuesapp.util.Validator;

public class HomeActivity extends Activity {

	private EditText yearEditText;

	public static final String VALUES_ARRAY = "valuesArray";
	public static final String SELECTED_YEAR = "year";
	
	private static final String ERROR_TITLE = "Erro!";
	private static final String ERROR_INVALID_YEAR = "Exercício Inválido";
	private static final String ERROR_INTERNET_ACCESS = "Acesso a rede indisponível";
	private static final String ERROR_SERVICE= "Erro ao se conectar com servidor";
	private static final String PROGRESS_MESSAGE = "Carregando...";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		yearEditText = (EditText) findViewById(R.id.editTextYear);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public void query(View v){
		
		Integer year;
		ConnectivityManager manager;
		NetworkInfo info;
		
		year = Validator.convertYear( yearEditText.getText().toString() );
		manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		info = manager.getActiveNetworkInfo();
		
		if( year == null || info==null || !info.isConnectedOrConnecting()){
			AlertDialog alert;
			AlertDialog.Builder builder;
			
			builder = new AlertDialog.Builder(this);
			builder.setTitle(ERROR_TITLE);
			builder.setMessage(year==null ? ERROR_INVALID_YEAR : ERROR_INTERNET_ACCESS);
			builder.setPositiveButton("OK", null);
			
			alert = builder.create();
			alert.show();
			
		}else{
			
			SearchThread thread;
			
			thread = new SearchThread(this, year);
			thread.execute();
		}
	}
	
	private class SearchThread extends AsyncTask<Void, Void, Boolean>{

		private Context myContext;
		private ProgressDialog dialog;
		private List<Double> values;
		private Integer year;
		
		public SearchThread(Context context, Integer year){
			this.myContext = context;
			this.year = year;
		}
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(myContext);
			dialog.setMessage(PROGRESS_MESSAGE);
			dialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... value) {

			ValuesDAO dao;
			
			dao = new ValuesDAO();
			values = dao.getValues(year);
			
			return !(values == null || values.isEmpty());
		}
		
		@Override
		protected void onProgressUpdate(Void... value) {
		}
		
		@Override
		protected void onPostExecute(Boolean isOk) {

			dialog.cancel();
			
			if( isOk ){
				Intent valuesIntent;
				
				valuesIntent = new Intent(myContext, ValuesActivity.class);
				valuesIntent.putExtra(VALUES_ARRAY, (ArrayList<Double>)values);
				valuesIntent.putExtra(SELECTED_YEAR, year);
				
				startActivity(valuesIntent);
			}else{
				
				AlertDialog alert;
				AlertDialog.Builder builder;
				
				builder = new AlertDialog.Builder(myContext);
				builder.setTitle(ERROR_TITLE);
				builder.setMessage(ERROR_SERVICE);
				builder.setPositiveButton("OK", null);
				
				alert = builder.create();
				alert.show();
			}
		}
	}	
}
