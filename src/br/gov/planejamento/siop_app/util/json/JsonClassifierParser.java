package br.gov.planejamento.siop_app.util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonClassifierParser implements JsonParser<List<HashMap<String, Object>>>{

	@Override
	public List<HashMap<String, Object>> convertJsonToObject(String responseText) {

		JSONObject jsonObject;
		JSONObject headerObject;
		JSONObject resultObject;
		JSONArray jValuesArray;
		JSONArray jHeaderArray;
		List<String> header;
		List<HashMap<String, Object>> listMap;
		
		listMap = new ArrayList<HashMap<String,Object>>();
		header = new ArrayList<String>();
		
		try{
			jsonObject = new JSONObject(responseText);
			
			headerObject = jsonObject.getJSONObject("head");
			jHeaderArray = headerObject.getJSONArray("vars");
			
			for(int i=0; i<jHeaderArray.length(); i++){
				header.add( jHeaderArray.getString(i) );
			}
			
			resultObject = jsonObject.getJSONObject("results");
			jValuesArray = resultObject.getJSONArray("bindings");
			
			for(int i=0; i<jValuesArray.length(); i++){
				JSONObject jsonRow = jValuesArray.getJSONObject(i);
				HashMap<String, Object> itemMap = new HashMap<String, Object>();
				
				for(String var : header){
					JSONObject jsonHeaderObject = jsonRow.getJSONObject(var);
					itemMap.put(var, jsonHeaderObject.get("value"));
				}
				
				listMap.add(itemMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			listMap = null;
		}
		
		return listMap;
	}

}
