package br.gov.planejamento.siop_app.controller;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import br.gov.planejamento.siop_app.util.Validator;
import br.unb.valuesapp.R;

public class ValuesActivity extends Activity {

	private List<Double> values;
	private Integer selectedYear;
	
	private TextView titleEditText;
	private TextView ploaTextView;
	private TextView loaTextView;
	private TextView leiMaisCreditoTextView;
	private TextView empenhadoTextView;
	private TextView liquidadoTextView;
	private TextView pagoTextView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_values);
		setupActionBar();
		
		Bundle bundle;
		
		bundle = getIntent().getExtras();
		
//		values = (List<Double>) bundle.getSerializable(QueryActivity.VALUES_ARRAY);
//		selectedYear = bundle.getInt(QueryActivity.SELECTED_YEAR);
		
		titleEditText = (TextView) findViewById(R.id.textViewValuesTitle);
		ploaTextView = (TextView) findViewById(R.id.editTextPloa);
		loaTextView = (TextView) findViewById(R.id.editTextLoa);
		leiMaisCreditoTextView = (TextView) findViewById(R.id.editTextLeiMaisCredito);
		empenhadoTextView = (TextView) findViewById(R.id.editTextEmpenhado);
		liquidadoTextView = (TextView) findViewById(R.id.editTextLiquidado);
		pagoTextView = (TextView) findViewById(R.id.editTextPago);
		
		titleEditText.append(selectedYear.toString());
		ploaTextView.setText( Validator.convertDouble(values.get(0)) );
		loaTextView.setText(Validator.convertDouble(values.get(1)));
		leiMaisCreditoTextView.setText(Validator.convertDouble(values.get(2)));
		empenhadoTextView.setText(Validator.convertDouble(values.get(3)));
		liquidadoTextView.setText(Validator.convertDouble(values.get(4)));
		pagoTextView.setText(Validator.convertDouble(values.get(5)));
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
		case android.R.id.home:

			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void back(View v){
		finish();
	}

}
