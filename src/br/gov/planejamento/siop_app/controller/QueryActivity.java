package br.gov.planejamento.siop_app.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import br.gov.planejamento.siop_app.R;
import br.gov.planejamento.siop_app.dao.ProgramaTrabalhoDAO;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.util.Validator;

public class QueryActivity extends Activity {

	private Spinner exercicioSp;
	private EditText unidadeET;
	private EditText programaTrabalhoET;
	
	public static final String VALUE_ITEM = "item";
	public static final String VALUE_PT = "pt";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_query);
		
		exercicioSp = (Spinner) findViewById(R.id.spinnerExercicio);
		unidadeET = (EditText) findViewById(R.id.editTextUnidade);
		programaTrabalhoET = (EditText) findViewById(R.id.editTextPt);
	}
	
	public void query(View v){
		ConnectivityManager manager;
		NetworkInfo info;
		
		manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		info = manager.getActiveNetworkInfo();
		
		int exercicio = Integer.valueOf(exercicioSp.getSelectedItem().toString());
		String unidade = unidadeET.getText().toString();
		String pt = programaTrabalhoET.getText().toString();
		
		if(info == null || !info.isConnectedOrConnecting() || !Validator.checkPT(pt) || !Validator.checkUO(unidade)){
			sendErrorMessage(info, unidade, pt);
		} else {
			SearchThread thread;
			
			thread = new SearchThread(this, exercicio, unidade, pt);
			thread.execute();
		}
	}
	
	private void sendErrorMessage(NetworkInfo info, String unidade, String pt) {
		StringBuilder errorMsg = new StringBuilder();
		
		if(info==null || !info.isConnected()) {
			errorMsg.append(getString(R.string.internetAcessError));
		} else {
			if(!Validator.checkUO(unidade))
				errorMsg.append(getString(R.string.invalidUnidadeError) + "\n");
			if(!Validator.checkPT(pt))
				errorMsg.append(getString(R.string.invalidPtError) + "\n");
		}
		
		Validator.showDialogError(this, errorMsg.toString());
	}
	
	private class SearchThread extends AsyncTask<Void, Void, Boolean>{

		private Context myContext;
		private ProgressDialog dialog;
		private Item resultadoProgrmaTrabalho;
		private int exercicio;
		private String unidade;
		private String pt;
		
		public SearchThread(Context context, int exercicio, String unidade, String pt){
			this.myContext = context;
			this.exercicio = exercicio;
			this.unidade = unidade;
			this.pt = pt;
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
			resultadoProgrmaTrabalho = dao.getProgramaTrabalho(exercicio, unidade, pt);
			
			return resultadoProgrmaTrabalho != null;
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
				valuesIntent.putExtra(VALUE_ITEM, resultadoProgrmaTrabalho);
				valuesIntent.putExtra(VALUE_PT, pt);
				
				startActivity(valuesIntent);
			}else{

				Validator.showDialogError(myContext, getString(R.string.invalidItem));
			}
		}
	}	
}

