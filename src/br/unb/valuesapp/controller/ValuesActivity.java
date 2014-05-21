package br.unb.valuesapp.controller;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import br.unb.valuesapp.R;

public class ValuesActivity extends Activity {

	private List<Double> values;
	private Integer selectedYear;
	
	private TextView titleEditText;
	private EditText ploaEditText;
	private EditText loaEditText;
	private EditText leiMaisCreditoEditText;
	private EditText empenhadoEditText;
	private EditText liquidadoEditText;
	private EditText pagoEditText;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_values);
		setupActionBar();
		
		Bundle bundle;
		
		bundle = getIntent().getExtras();
		
		values = (List<Double>) bundle.getSerializable(HomeActivity.VALUES_ARRAY);
		selectedYear = bundle.getInt(HomeActivity.SELECTED_YEAR);
		
		titleEditText = (TextView) findViewById(R.id.textViewValuesTitle);
		ploaEditText = (EditText) findViewById(R.id.editTextPloa);
		loaEditText = (EditText) findViewById(R.id.editTextLoa);
		leiMaisCreditoEditText = (EditText) findViewById(R.id.editTextLeiMaisCredito);
		empenhadoEditText = (EditText) findViewById(R.id.editTextEmpenhado);
		liquidadoEditText = (EditText) findViewById(R.id.editTextLiquidado);
		pagoEditText = (EditText) findViewById(R.id.editTextPago);
		
		titleEditText.append(selectedYear.toString());
		ploaEditText.setText(values.get(0).toString());
		loaEditText.setText(values.get(1).toString());
		leiMaisCreditoEditText.setText(values.get(2).toString());
		empenhadoEditText.setText(values.get(3).toString());
		liquidadoEditText.setText(values.get(4).toString());
		pagoEditText.setText(values.get(5).toString());
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
