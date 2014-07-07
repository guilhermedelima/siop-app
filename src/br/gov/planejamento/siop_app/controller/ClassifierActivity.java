package br.gov.planejamento.siop_app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import br.gov.planejamento.siop_app.R;
import br.gov.planejamento.siop_app.dao.ClassifierDAO;
import br.gov.planejamento.siop_app.model.ClassifierType;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.service.EndpointSPARQL;
import br.gov.planejamento.siop_app.util.Validator;
import br.gov.planejamento.siop_app.util.json.JsonClassifierParser;
import br.gov.planejamento.siop_app.util.json.JsonParser;

public class ClassifierActivity extends Activity {

	private RadioGroup radioGroupClassifier;
	private Spinner yearSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_classifier);
		
		radioGroupClassifier = (RadioGroup) findViewById(R.id.radioGroupClassifiers);
		yearSpinner = (Spinner) findViewById(R.id.spinnerClassifierExercicio);
	}
	
	public void query(View v){
		ConnectivityManager manager;
		NetworkInfo info;
		int year, selectedId;
		String classifierText;
		ClassifierType type;
		SearchThread thread;
		
		manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		info = manager.getActiveNetworkInfo();
		
		if(info==null || !info.isConnectedOrConnecting()){
			Validator.showDialogError(this, getString(R.string.internetAcessError));
		}else{
			
			year = Integer.valueOf(yearSpinner.getSelectedItem().toString());
			selectedId = radioGroupClassifier.getCheckedRadioButtonId();
			classifierText = ((RadioButton)findViewById(selectedId)).getText().toString();
			
			if(ClassifierType.FUNCAO.getName().equals(classifierText))
				type = ClassifierType.FUNCAO;
			else if(ClassifierType.PROGRAMA.getName().equals(classifierText))
				type = ClassifierType.PROGRAMA;
			else
				type = ClassifierType.GND;
			
			thread = new SearchThread(this, type, year);
			thread.execute();
		}
	}
	
	private class SearchThread extends AsyncTask<Void, Void, Boolean>{

		private Context myContext;
		private ClassifierType type;
		private int year;
		private ProgressDialog dialog;
		private List<Item> classifierList;
		
		public SearchThread(Context context, ClassifierType type, int year){
			this.myContext = context;
			this.type = type;
			this.year = year;
		}
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(myContext);
			dialog.setMessage(getText(R.string.loading));
			dialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			
			EndpointSPARQL endpoint;
			JsonParser<List<HashMap<String, Object>>> parser;
			ClassifierDAO dao;
			
			endpoint = new EndpointSPARQL();
			parser = new JsonClassifierParser();
			dao = new ClassifierDAO(endpoint, parser);
			
			classifierList = dao.searchClassifier(type, year);
			
			return classifierList != null;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
		}
		
		@Override
		protected void onPostExecute(Boolean isOk) {
			
			dialog.cancel();
			
			if(isOk){
				Intent classifierListIntent;
				
				classifierListIntent = new Intent(myContext, ClassifierListActivity.class);
				classifierListIntent.putExtra(ClassifierListActivity.VALUE_LIST, new ArrayList<Item>(classifierList));
				
				startActivity(classifierListIntent);
			}else{
				
				Validator.showDialogError(myContext, getString(R.string.invalidClassifier));
			}
		}
		
	}


}
