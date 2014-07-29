package br.gov.planejamento.siop_app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import br.gov.planejamento.siop_app.R;
import br.gov.planejamento.siop_app.dao.ProgramaTrabalhoDAO;
import br.gov.planejamento.siop_app.dao.QueryDAO;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.model.Query;
import br.gov.planejamento.siop_app.model.adapter.QueryAdapter;
import br.gov.planejamento.siop_app.service.EndpointSPARQL;
import br.gov.planejamento.siop_app.util.Validator;
import br.gov.planejamento.siop_app.util.json.JsonParser;
import br.gov.planejamento.siop_app.util.json.JsonProgramaTrabalhoParser;

public class QueryActivity extends Activity {

	public static final Integer ACTION_SAVE = 1;
	public static final Integer ACTION_DELETE = 2;
	
	private Spinner exercicioSp;
	private EditText unidadeET;
	private EditText programaTrabalhoET;

	private QueryAdapter adapter;
	private ListView queryListView;
	private AlertDialog alertListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_query);
		
		ListQueryThread thread;
		
		exercicioSp = (Spinner) findViewById(R.id.spinnerQueryExercicio);
		unidadeET = (EditText) findViewById(R.id.editTextUnidade);
		programaTrabalhoET = (EditText) findViewById(R.id.editTextPt);
		
		adapter = null;
		
		thread = new ListQueryThread(this);
		thread.execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.query, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return adapter != null;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch( item.getItemId() ){
		case R.id.item_query_save:
			saveQuery();
			return true;
		
		case R.id.item_query_show:
			showListQuery();
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void saveQuery(){
		
		final int year;
		final String unidade;
		final String pt; 
		
		year = Integer.valueOf(exercicioSp.getSelectedItem().toString());
		unidade = unidadeET.getText().toString();
		pt = programaTrabalhoET.getText().toString();
		
		if(Validator.checkPT(pt) && Validator.checkUO(unidade)){
			
			AlertDialog dialog;
			AlertDialog.Builder builder;
			final EditText nameEditText;
			
			nameEditText = new EditText(this);
			nameEditText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
			
			builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.dialogGetQueryName));
			builder.setMessage(getString(R.string.messageGetQueryName));
			builder.setView(nameEditText);
			builder.setPositiveButton(getString(R.string.dialogPositive), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int whitch) {
					String text;
					QueryActionThread thread;
					Query query;
					
					text = nameEditText.getText().toString();
					
					if( Validator.checkName(text) ){
						query = new Query(text, unidade, pt, year);
						thread = new QueryActionThread(QueryActivity.this, query, QueryActivity.ACTION_SAVE);
						
						thread.execute();
					}else{
						
						Validator.showDialogError(QueryActivity.this, getString(R.string.invalidName));
					}
				}
			});
			builder.setNegativeButton(getString(R.string.dialogNegative), null);
			
			dialog = builder.create();
			dialog.show();
			
		}else{
			
			sendErrorMessage(unidade, pt);
		}
	}
	
	public void showListQuery(){
		
		if( adapter.getCount() > 0)
			alertListView.show();	
		else	
			Validator.showDialogError(this, getString(R.string.emptyQueryList));	
	}
	
	public void query(View v){
		
		int year = Integer.valueOf(exercicioSp.getSelectedItem().toString());
		String unidade = unidadeET.getText().toString();
		String pt = programaTrabalhoET.getText().toString();
		
		if( Validator.checkInternetAccess(this) && Validator.checkPT(pt) && Validator.checkUO(unidade)){
			
			SearchThread thread;
			
			thread = new SearchThread(this, year, unidade, pt);
			thread.execute();
			
		}else{
			
			sendErrorMessage(unidade, pt);
		}
	}
	
	private void sendErrorMessage(String unidade, String pt) {
		StringBuilder errorMsg = new StringBuilder();
		
		if(!Validator.checkUO(unidade))
			errorMsg.append(getString(R.string.invalidUnidadeError) + "\n");
		else if(!Validator.checkPT(pt))
			errorMsg.append(getString(R.string.invalidPtError) + "\n");
		else if(!Validator.checkInternetAccess(this))
			errorMsg.append(getString(R.string.internetAccessError));
		
		Validator.showDialogError(this, errorMsg.toString());
	}
	
	public static class SearchThread extends AsyncTask<Void, Void, Boolean>{

		private Context myContext;
		private ProgressDialog dialog;
		private Item ptResult;
		private int year;
		private String unidade;
		private String pt;
		
		public SearchThread(Context context, int exercicio, String unidade, String pt){
			this.myContext = context;
			this.year = exercicio;
			this.unidade = unidade;
			this.pt = pt;
		}
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(myContext);
			dialog.setMessage(myContext.getString(R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... value) {

			ProgramaTrabalhoDAO dao;
			JsonParser<HashMap<String, Object>> parser;
			EndpointSPARQL endpoit;
			
			endpoit = new EndpointSPARQL();
			parser = new JsonProgramaTrabalhoParser();
			
			dao = new ProgramaTrabalhoDAO(endpoit, parser);
			
			ptResult = dao.getProgramaTrabalho(year, unidade, pt);
			
			return ptResult != null;
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
				valuesIntent.putExtra(ValuesActivity.VALUE_ITEM, ptResult);
				valuesIntent.putExtra(ValuesActivity.VALUE_PT, pt);
				valuesIntent.putExtra(ValuesActivity.VALUE_UO, unidade);
				valuesIntent.putExtra(ValuesActivity.VALUE_ACTION, ValuesActivity.ACTION_PT);
				
				myContext.startActivity(valuesIntent);
			}else{

				Validator.showDialogError(myContext, myContext.getString(R.string.invalidItem));
			}
		}
	}
	
	
	private class QueryActionThread extends AsyncTask<Void, Void, Boolean>{
		
		private Query query;
		private Context myContext;
		private ProgressDialog dialog;
		private int action;
		
		public QueryActionThread(Context myContext, Query query, int action) {
			this.myContext = myContext;
			this.query = query;
			this.action = action;
		}

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(myContext);
			dialog.setMessage(getString(R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {

			Query q;
			QueryDAO dao;
			
			dao = new QueryDAO(myContext.getApplicationContext());
			
			if( action == ACTION_SAVE ){
				for(int i=0; i<adapter.getCount(); i++){
					q = adapter.getItem(i);
					
					if( q.getName().equals(query.getName()) ){
						return false;
					}
				}
				
				if ( dao.save(query) );
					adapter.add(query);
				
			}else{
				
				if( dao.delete(query) )
					adapter.remove(query);
			}
			
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			
			if(result)	
				adapter.notifyDataSetChanged();
			else
				Validator.showDialogError(myContext, getString(R.string.invalidSaveQueryName));
		}		
	}
	
	private class ListQueryThread extends AsyncTask<Void, Void, Boolean>{
		
		private Context myContext;
		private ProgressDialog dialog;
		private List<Query> listQuery;
		
		public ListQueryThread(Context myContext) {
			this.myContext = myContext;
		}

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(myContext);
			dialog.setMessage(getString(R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {

			QueryDAO dao;
			
			dao = new QueryDAO(myContext.getApplicationContext());
			
			listQuery = dao.getAllObjects();			
			
			return listQuery != null;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			
			if(!result){
				listQuery = new ArrayList<Query>();
			}
			
			queryListView = new ListView(myContext);
			queryListView.setCacheColorHint(Color.TRANSPARENT);
			queryListView.setBackgroundColor(Color.WHITE);
			
			adapter = new QueryAdapter(myContext, R.layout.item_query, listQuery);
			queryListView.setAdapter(adapter);
			
			registerForContextMenu(queryListView);
			
			AlertDialog.Builder builder;
			
			builder = new AlertDialog.Builder(myContext);	
			builder.setTitle(getString(R.string.dialogShowList));
			builder.setView(queryListView);
			builder.setPositiveButton("Ok", null);
			
			alertListView = builder.create();
		}		
	}
	
	@Override
	public void onCreateContextMenu(final ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		menu.setHeaderTitle(R.string.context_menu_title);
		menu.add(0, R.id.item_query_context_search, 0, getString(R.string.context_menu_exec));
		menu.add(0, R.id.item_query_context_delete, 1, getString(R.string.context_menu_delete));
		
		menu.getItem(0).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem clickedItem) {
				AdapterContextMenuInfo info;
				Query q;
				SearchThread thread;
				
				info = (AdapterContextMenuInfo) clickedItem.getMenuInfo();
				q = adapter.getItem(info.position);
				
				menu.setHeaderTitle(q.getName());
				
				if( Validator.checkInternetAccess(QueryActivity.this) ){
					
					thread = new SearchThread(QueryActivity.this, q.getYear(), q.getUo(), q.getPtCod());
					thread.execute();
					
				}else{
					
					Validator.showDialogError(QueryActivity.this, getString(R.string.internetAccessError));
				}
				
				alertListView.cancel();
				
				return true;
			}
		});
		
		menu.getItem(1).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem clickedItem) {
				
				AdapterContextMenuInfo info;
				Query q;
				QueryActionThread thread;
				
				info = (AdapterContextMenuInfo) clickedItem.getMenuInfo();
				q = adapter.getItem(info.position);
				
				menu.setHeaderTitle(q.getName());

				thread = new QueryActionThread(QueryActivity.this, q, ACTION_DELETE);
				thread.execute();
				
				alertListView.cancel();
				
				return true;
			}
		});
	}
}