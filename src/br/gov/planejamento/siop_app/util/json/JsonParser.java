package br.gov.planejamento.siop_app.util.json;


public interface JsonParser<T> {

	public T convertJsonToObject(String responseText);
	
}
