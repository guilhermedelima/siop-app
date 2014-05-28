package br.gov.planejamento.siop_app.controller;

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
import android.widget.Spinner;
import br.gov.planejamento.siop_app.dao.ProgramaTrabalhoDAO;
import br.gov.planejamento.siop_app.util.Validator;
import br.unb.valuesapp.R;

public class QueryActivity extends Activity {

	private Spinner exercicioSp;
	private EditText unidadeET;
	private EditText ptET;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query);
		
		exercicioSp = (Spinner) findViewById(R.id.spinnerExercicio);
		unidadeET = (EditText) findViewById(R.id.editTextUnidade);
		ptET = (EditText) findViewById(R.id.editTextPt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public void query(View v){
		ConnectivityManager manager;
		NetworkInfo info;
		
		manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		info = manager.getActiveNetworkInfo();
		
		int exercicio = Integer.valueOf(exercicioSp.getSelectedItem().toString());
		String unidade = unidadeET.getText().toString();
		String pt = ptET.getText().toString();
		
		if(!info.isConnectedOrConnecting() || unidade == null || unidade.length() != 5 || pt == null || pt.length() != 16){
			sendErrorMessage(info, unidade, pt);
		} else {
			SearchThread thread;
			
			thread = new SearchThread(this, exercicio, unidade, pt);
			thread.execute();
		}
	}
	
	private void sendErrorMessage(NetworkInfo info, String unidade, String pt) {
		StringBuilder errorMsg = new StringBuilder();
		
		if(!info.isConnected()) {
			errorMsg.append(getString(R.string.internetAcessError));
		} else {
			if(unidade == null || unidade.length() != 5)
				errorMsg.append(getString(R.string.invalidUnidadeError) + "\n");
			if(pt == null || pt.length() != 16)
				errorMsg.append(getString(R.string.invalidPtError) + "\n");
		}
		
		AlertDialog alert;
		AlertDialog.Builder builder;
		
		builder = new AlertDialog.Builder(this)
			.setTitle(getString(R.string.titleError))
			.setPositiveButton("OK", null)
			.setMessage(errorMsg.toString());
		
		alert = builder.create();
		alert.show();
	}
	
	private class SearchThread extends AsyncTask<Void, Void, Boolean>{

		private Context myContext;
		private ProgressDialog dialog;
		private List<Double> values;
		private int exercicio;
		
		public SearchThread(Context context, int exercicio, String unidade, String pt){
			this.myContext = context;
			this.exercicio = exercicio;
		}
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(myContext);
			dialog.setMessage(getString(R.string.loading));
			dialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... value) {

			ProgramaTrabalhoDAO dao;
			
			dao = new ProgramaTrabalhoDAO();
			values = dao.getValues(exercicio);
			
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
//				valuesIntent.putExtra(VALUES_ARRAY, (ArrayList<Double>)values);
//				valuesIntent.putExtra(SELECTED_YEAR, exercicio);
				
				startActivity(valuesIntent);
			}else{
				
				AlertDialog alert;
				AlertDialog.Builder builder;
				
				builder = new AlertDialog.Builder(myContext);
//				builder.setTitle(ERROR_TITLE);
//				builder.setMessage(ERROR_SERVICE);
				builder.setPositiveButton("OK", null);
				
				alert = builder.create();
				alert.show();
			}
		}
	}	
}
