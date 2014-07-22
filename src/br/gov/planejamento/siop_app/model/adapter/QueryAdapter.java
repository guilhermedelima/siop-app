package br.gov.planejamento.siop_app.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.gov.planejamento.siop_app.R;
import br.gov.planejamento.siop_app.model.Query;

public class QueryAdapter extends ArrayAdapter<Query>{
	
	private List<Query> queryList;
	private LayoutInflater inflater;

	public QueryAdapter(Context context, int resource, List<Query> objects) {
		super(context, resource, objects);

		queryList = objects;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		ViewHolder holder;
		Query q;
		
		if( convertView == null ){
			
			convertView = (View) inflater.inflate(R.layout.item_query, null);
			
			holder = new ViewHolder();
			holder.queryNameTextView = (TextView) convertView.findViewById(R.id.textViewQueryName);
			holder.queryYearTextView = (TextView) convertView.findViewById(R.id.textViewQueryYear);
			holder.queryUoTextView = (TextView) convertView.findViewById(R.id.textViewQueryUO);
			holder.queryPtTextView = (TextView) convertView.findViewById(R.id.textViewQueryPT);
			
			convertView.setTag(holder);
		}else
			holder = (ViewHolder) convertView.getTag();
		
		q = queryList.get(position);
		
		holder.queryNameTextView.setText(q.getName());
		holder.queryYearTextView.setText(String.valueOf(q.getYear()));
		holder.queryUoTextView.setText(q.getUo());
		holder.queryPtTextView.setText(q.getPtCod());
		
		return convertView;
	}
	
	private static class ViewHolder{
		TextView queryNameTextView;
		TextView queryYearTextView;
		TextView queryUoTextView;
		TextView queryPtTextView;
	}
}
