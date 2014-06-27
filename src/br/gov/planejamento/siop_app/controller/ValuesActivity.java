package br.gov.planejamento.siop_app.controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import br.gov.planejamento.siop_app.R;
import br.gov.planejamento.siop_app.model.Classifier;
import br.gov.planejamento.siop_app.model.ClassifierType;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.model.adapter.ClassifierAdapter;
import br.gov.planejamento.siop_app.util.Validator;

public class ValuesActivity extends Activity {

	private Item item;
	
	private TextView titleEditText;
	private TextView ptCodTextView;
	private TextView ptLabelTextView;
	private TextView ploaTextView;
	private TextView loaTextView;
	private TextView leiMaisCreditoTextView;
	private TextView empenhadoTextView;
	private TextView liquidadoTextView;
	private TextView pagoTextView;
	private ClassifierAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_values);
		setupActionBar();
		
		Bundle bundle;
		String pt, ptLabel;
		
		bundle = getIntent().getExtras();
		item = (Item) bundle.getSerializable(QueryActivity.VALUE_ITEM);
		pt = (String) bundle.getString(QueryActivity.VALUE_PT);
		ptLabel = null;
		
		for(Classifier classifier : item.getClassifierList()){
			if(classifier.getType().equals(ClassifierType.ACAO)){
				ptLabel = classifier.getLabel();
				break;
			}
		}
		
		titleEditText = (TextView) findViewById(R.id.textViewValuesTitle);
		ptCodTextView = (TextView) findViewById(R.id.textViewPtCod);
		ptLabelTextView = (TextView) findViewById(R.id.textViewPtLabel);
		ploaTextView = (TextView) findViewById(R.id.editTextPloa);
		loaTextView = (TextView) findViewById(R.id.editTextLoa);
		leiMaisCreditoTextView = (TextView) findViewById(R.id.editTextLeiMaisCredito);
		empenhadoTextView = (TextView) findViewById(R.id.editTextEmpenhado);
		liquidadoTextView = (TextView) findViewById(R.id.editTextLiquidado);
		pagoTextView = (TextView) findViewById(R.id.editTextPago);
		
		titleEditText.append(" " + String.valueOf(item.getYear()));
		ptCodTextView.setText(pt);
		ptLabelTextView.setText(ptLabel);
		ploaTextView.setText(Validator.convertDouble(item.getValueProjetoLei()));
		loaTextView.setText(Validator.convertDouble(item.getValueDotacaoInicial()));
		leiMaisCreditoTextView.setText(Validator.convertDouble(item.getValueLeiMaisCredito()));
		empenhadoTextView.setText(Validator.convertDouble(item.getValueEmpenhado()));
		liquidadoTextView.setText(Validator.convertDouble(item.getValueLiquidado()));
		pagoTextView.setText(Validator.convertDouble(item.getValuePago()));
		
		adapter = new ClassifierAdapter(this, R.layout.item_classifier, item.getClassifierList());
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.values, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.item_pt_info:
				showClassifierDialog();
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public void back(View v){
		finish();
	}

	private void showClassifierDialog(){
		
		AlertDialog.Builder builder;
		AlertDialog alert;
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.menu_classifier_title));
		builder.setAdapter(adapter, null);
		builder.setPositiveButton("OK", null);
		
		alert = builder.create();
		alert.show();
	}
	
}
