package br.unb.valuesapp.util.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonValuesParser implements JsonParser<Double>{

	@Override
	public List<Double> convertJsonToList(String responseText) {

		JSONObject jsonObject;
		JSONObject headerObject;
		JSONObject resultObject;
		JSONArray jValuesArray;
		JSONArray jHeaderArray;
		List<Double> values;
		List<String> header;
		
		header = new ArrayList<String>();
		values = new ArrayList<Double>();
		
		try{
			jsonObject = new JSONObject(responseText);
			
			headerObject = jsonObject.getJSONObject("head");
			jHeaderArray = headerObject.getJSONArray("vars");
			for(int i=0; i<jHeaderArray.length(); i++){
				header.add( jHeaderArray.getString(i) );
			}
			
			resultObject = jsonObject.getJSONObject("results");
			jValuesArray = resultObject.getJSONArray("bindings");
			
			if( jValuesArray.length() == 1 ){
				JSONObject jRow = jValuesArray.getJSONObject(0);
				
				for(String var : header){
					JSONObject jsonHeader = jRow.getJSONObject(var);
					values.add( jsonHeader.getDouble("value") );
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			values = null;
		}
		
		return values;
	}

	
}
