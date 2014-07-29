package br.gov.planejamento.siop_app.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import br.gov.planejamento.siop_app.R;
import br.gov.planejamento.siop_app.model.Classifier;
import br.gov.planejamento.siop_app.model.ClassifierType;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.model.adapter.ProgramaTrabalhoAdapter;
import br.gov.planejamento.siop_app.util.Validator;

public class ValuesActivity extends Activity {

	public static final String VALUE_ITEM = "item";
	public static final String VALUE_PT = "pt";
	public static final String VALUE_UO = "uo";
	public static final String VALUE_YEAR = "ano";
	public static final String VALUE_ACTION = "action";

	public static final Integer ACTION_PT = 1;
	public static final Integer ACTION_CLASSIFIER = 2;

	private Item item;
	private Integer action;
	private String pt, uo;
	private int year;
	private AlertDialog.Builder builderAdapterDialog;

	private TextView titleEditText;
	private TextView idTextView;
	private TextView codTextView;
	private TextView labelTextView;
	private TextView ploaTextView;
	private TextView loaTextView;
	private TextView leiMaisCreditoTextView;
	private TextView empenhadoTextView;
	private TextView liquidadoTextView;
	private TextView pagoTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_values);

		Bundle bundle;

		bundle = getIntent().getExtras();
		item = (Item) bundle.getSerializable(ValuesActivity.VALUE_ITEM);
		action = bundle.getInt(VALUE_ACTION);

		pt = bundle.getString(ValuesActivity.VALUE_PT);
		year = item.getYear();

		titleEditText = (TextView) findViewById(R.id.textViewValuesTitle);
		idTextView = (TextView) findViewById(R.id.textViewIDInfo);
		codTextView = (TextView) findViewById(R.id.textViewPtCod);
		labelTextView = (TextView) findViewById(R.id.textViewPtLabel);
		ploaTextView = (TextView) findViewById(R.id.editTextPloa);
		loaTextView = (TextView) findViewById(R.id.editTextLoa);
		leiMaisCreditoTextView = (TextView) findViewById(R.id.editTextLeiMaisCredito);
		empenhadoTextView = (TextView) findViewById(R.id.editTextEmpenhado);
		liquidadoTextView = (TextView) findViewById(R.id.editTextLiquidado);
		pagoTextView = (TextView) findViewById(R.id.editTextPago);

		titleEditText.append(" " + String.valueOf(year));
		ploaTextView.setText(Validator.convertDouble(item.getValueProjetoLei()));
		loaTextView.setText(Validator.convertDouble(item.getValueDotacaoInicial()));
		leiMaisCreditoTextView.setText(Validator.convertDouble(item.getValueLeiMaisCredito()));
		empenhadoTextView.setText(Validator.convertDouble(item.getValueEmpenhado()));
		liquidadoTextView.setText(Validator.convertDouble(item.getValueLiquidado()));
		pagoTextView.setText(Validator.convertDouble(item.getValuePago()));
		
		if(action == ACTION_PT){
			uo = bundle.getString(VALUE_UO);
			setupPT(pt);
		}else
			setupClassifier();
	}

	public void setupClassifier(){
		idTextView.setText(item.getClassifierList().get(0).getType().getName());
		codTextView.append(" " + item.getClassifierList().get(0).getCode());
		labelTextView.setText(item.getClassifierList().get(0).getLabel());
	}

	public void setupPT(String pt){
		String ptLabel;
		ProgramaTrabalhoAdapter adapter;

		ptLabel = null;

		for(Classifier classifier : item.getClassifierList()){
			if(classifier.getType().equals(ClassifierType.ACAO)){
				ptLabel = classifier.getLabel();
				break;
			}
		}

		idTextView.setText(getText(R.string.ptLabel));
		codTextView.setText(pt);
		labelTextView.setText(ptLabel);
		
		adapter = new ProgramaTrabalhoAdapter(this, R.layout.item_classifier, item.getClassifierList());
		
		builderAdapterDialog = new AlertDialog.Builder(this);
		builderAdapterDialog.setTitle(getString(R.string.menu_classifier_title));
		builderAdapterDialog.setAdapter(adapter, null);
		builderAdapterDialog.setPositiveButton("OK", null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.values, menu);
		return action!=null&&action==ACTION_PT ? true : false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.item_pt_info:
			showClassifierDialog();
			return true;
		case R.id.item_pt_shortcut:
			createShortcut();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void back(View v){
		finish();
	}

	private void showClassifierDialog(){
		AlertDialog alert;

		alert = builderAdapterDialog.create();
		alert.show();
	}
	
	public void createShortcut(){
		AlertDialog dialog;
		AlertDialog.Builder builder;
		final EditText nameEditText;
		
		nameEditText = new EditText(this);
		nameEditText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.dialog_shortcut_title));
		builder.setMessage(getString(R.string.dialog_shortcut_message));
		builder.setView(nameEditText);
		builder.setNegativeButton(getString(R.string.dialog_shortcut_negative), null);
		builder.setPositiveButton(getString(R.string.dialog_shortcut_positive), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int whitch) {
				String text;
				ShortcutIconResource icon;
				Intent shortCutIntent;
				Intent addIntent;

				text = nameEditText.getText().toString();
				
				if( Validator.checkName(text) ){
					
					shortCutIntent = new Intent(getApplicationContext(), HomeActivity.class);
					shortCutIntent.setAction(Intent.ACTION_MAIN);
				    
					shortCutIntent.putExtra(HomeActivity.VALUE_PT, pt);
					shortCutIntent.putExtra(HomeActivity.VALUE_UO, uo);
					shortCutIntent.putExtra(HomeActivity.VALUE_YEAR, year);
					
				    addIntent = new Intent();
				    icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.siop);
				    
				    addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
				    addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, text);
				    addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
				    
				    addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
					
				    getApplicationContext().sendBroadcast(addIntent);
				    
				}else{
					
					Validator.showDialogError(ValuesActivity.this, getString(R.string.invalidName));
				}
			}
		});
		
		dialog = builder.create();
		dialog.show();
	}
}