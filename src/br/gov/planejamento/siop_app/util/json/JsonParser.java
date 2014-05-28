package br.gov.planejamento.siop_app.util.json;

import java.util.List;

public interface JsonParser<T> {

	public List<T> convertJsonToList(String responseText);
	
}
