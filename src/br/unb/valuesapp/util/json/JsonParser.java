package br.unb.valuesapp.util.json;

import java.util.List;

public interface JsonParser<T> {

	public List<T> convertJsonToList(String responseText);
	
}
