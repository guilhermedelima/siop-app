package br.gov.planejamento.siop_app.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.gov.planejamento.siop_app.R;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.model.adapter.ClassifierAdapter;

public class ClassifierListActivity extends Activity {
	
	public static final String VALUE_LIST = "classificador";
	
	private List<Item> classifierList;
	private ListView classifierListView;
	private ClassifierAdapter adapter;
	private TextView titleTextView;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_classifier_list);
		
		Bundle bundle;
		String text;
		Item item;
		
		bundle = getIntent().getExtras();
		classifierList = (ArrayList<Item>)bundle.get(VALUE_LIST);		
		classifierListView = (ListView) findViewById(R.id.listViewClassifier);
		titleTextView = (TextView) findViewById(R.id.textViewClassifierListTitle);
		
		item = classifierList.get(0);
		text = item.getClassifierList().get(0).getType().getName() +" - "+ item.getYear();
		
		titleTextView.setText(text);
		adapter = new ClassifierAdapter(this, R.layout.item_classifier_label, classifierList);
		classifierListView.setAdapter(adapter);
		classifierListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent itemIntent;
				Item item;
				
				item = (Item) parent.getAdapter().getItem(position);
				itemIntent = new Intent(ClassifierListActivity.this, ValuesActivity.class);
				itemIntent.putExtra(ValuesActivity.VALUE_ITEM, item);
				itemIntent.putExtra(ValuesActivity.VALUE_ACTION, ValuesActivity.ACTION_CLASSIFIER);
				
				startActivity(itemIntent);
			}
		});
	}
	
	public void back(View v){
		finish();
	}

}
