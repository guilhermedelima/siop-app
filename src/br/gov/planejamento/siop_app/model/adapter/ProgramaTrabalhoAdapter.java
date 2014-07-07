package br.gov.planejamento.siop_app.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.gov.planejamento.siop_app.R;
import br.gov.planejamento.siop_app.model.Classifier;

public class ProgramaTrabalhoAdapter extends ArrayAdapter<Classifier>{

	private List<Classifier> classifierList;
	private LayoutInflater inflater;
	
	public ProgramaTrabalhoAdapter(Context context, int resource, List<Classifier> objects) {
		super(context, resource, objects);

		classifierList = objects;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		Classifier classifier;
		
		if(convertView == null){
			convertView = (View) inflater.inflate(R.layout.item_classifier, null);
			
			holder = new ViewHolder();
			holder.classifierType = (TextView) convertView.findViewById(R.id.textViewItemClassifierType);
			holder.classifierCod = (TextView) convertView.findViewById(R.id.textViewItemClassifierCod);
			holder.classifierLabel = (TextView) convertView.findViewById(R.id.textViewItemClassifierLabel);
		
			convertView.setTag(holder);
		}else
			holder = (ViewHolder) convertView.getTag();
		
		classifier = classifierList.get(position);
		
		holder.classifierType.setText(classifier.getType().getName() + ":");
		holder.classifierCod.setText(classifier.getCode());
		holder.classifierLabel.setText(classifier.getLabel());
		
		return convertView;
	}
	
	private static class ViewHolder{
		TextView classifierType;
		TextView classifierCod;
		TextView classifierLabel;
	}

}
