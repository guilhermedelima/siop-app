package br.gov.planejamento.siop_app.util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonProgramaTrabalhoParser implements JsonParser<HashMap<String, Object>>{

	@Override
	public HashMap<String, Object> convertJsonToObject(String responseText) {

		JSONObject jsonObject;
		JSONObject headerObject;
		JSONObject resultObject;
		JSONArray jValuesArray;
		JSONArray jHeaderArray;
		HashMap<String, Object> hash;
		List<String> header;
		
		header = new ArrayList<String>();
		hash = new HashMap<String, Object>();
		
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
					hash.put(var, jsonHeader.get("value"));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			hash = null;
		}		
		
		return hash;
	}

	
}
