package br.gov.planejamento.siop_app.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.gov.planejamento.siop_app.R;
import br.gov.planejamento.siop_app.model.Item;

public class ClassifierAdapter extends ArrayAdapter<Item>{

	private List<Item> classifierList;
	private LayoutInflater inflater;
	
	public ClassifierAdapter(Context context, int resource, List<Item> objects) {
		super(context, resource, objects);

		this.classifierList = objects;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		Item item;
		
		if( convertView == null ){
			convertView = (View) inflater.inflate(R.layout.item_classifier_label, null);
			
			holder = new ViewHolder();
			holder.classifierLabel = (TextView) convertView.findViewById(R.id.textViewListClassifierLabel);
			
			convertView.setTag(holder);
		}else
			holder = (ViewHolder) convertView.getTag();
		
		item = classifierList.get(position);
		
		holder.classifierLabel.setText(item.getClassifierList().get(0).getLabel());
		
		return convertView;
	}
	
	private static class ViewHolder{
		TextView classifierLabel;
	}

}
