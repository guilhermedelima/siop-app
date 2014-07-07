package br.gov.planejamento.siop_app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.gov.planejamento.siop_app.model.Classifier;
import br.gov.planejamento.siop_app.model.ClassifierType;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.service.EndpointSPARQL;
import br.gov.planejamento.siop_app.util.json.JsonParser;

public class ClassifierDAO {
	
	private EndpointSPARQL endpoint;
	private JsonParser<List<HashMap<String, Object>>> parser;
	
	public ClassifierDAO(EndpointSPARQL endpoint, JsonParser<List<HashMap<String, Object>>> parser){
		this.endpoint = endpoint;
		this.parser = parser;
	}
	
	public List<Item> searchClassifier(ClassifierType type, int year){
		String query, response;
		List<HashMap<String, Object>> listMap;
		List<Item> items;
		
		query = buildQuery(type, year);
		response = endpoint.execSPARQLQuery(query);
		listMap = response != null ? parser.convertJsonToObject(response) : null;
		
		items = listMap != null && !listMap.isEmpty() ? convertListToItems(listMap, type, year) : null;
		
		return items;
	}
	
	private List<Item> convertListToItems(List<HashMap<String, Object>> listMap, ClassifierType type, int year){
		
		List<Item> items;
		
		items = new ArrayList<Item>();
		
		for(HashMap<String, Object> map : listMap){
			items.add( convertMapToItem(map, type, year) );
		}
		
		return items;
	}
	
	
	private Item convertMapToItem(HashMap<String, Object> values, ClassifierType type, int year){
		
		double ploa, loa, leiMaisCredito, empenhado, liquidado, pago;
		String cod, label;
		Item item;
		List<Classifier> classifiers;
		
		cod = (String) values.get("cod"+type.getId());
		label = (String) values.get(type.getId());
	
		classifiers = new ArrayList<Classifier>();
		classifiers.add(new Classifier(label, cod, year, type));
		
		ploa = Double.valueOf( (String)values.get(Item.ValuesType.PLOA.toString()) );
		loa = Double.valueOf( (String)values.get(Item.ValuesType.LOA.toString()) );
		leiMaisCredito = Double.valueOf( (String)values.get(Item.ValuesType.LEI_MAIS_CREDITOS.toString()) );
		empenhado= Double.valueOf( (String)values.get(Item.ValuesType.EMPENHADO.toString()) );
		liquidado = Double.valueOf( (String)values.get(Item.ValuesType.LIQUIDADO.toString()) );
		pago = Double.valueOf( (String)values.get(Item.ValuesType.PAGO.toString()) );
		
		item = new Item(classifiers, year, ploa, loa, leiMaisCredito, liquidado, empenhado, pago);
			
		return item;
	}
	
	private String buildQuery(ClassifierType type, int year){

		String query = "SELECT " +
				"?cod"+type.getId()+" ?"+type.getId()+" "+
				"(SUM(?ploa) AS ?"+Item.ValuesType.PLOA+") "+
				"(SUM(?loa) AS ?"+Item.ValuesType.LOA+") "+
				"(SUM(?atual) AS ?"+Item.ValuesType.LEI_MAIS_CREDITOS+") "+
				"(SUM(?emp) AS ?"+Item.ValuesType.EMPENHADO+") "+
				"(SUM(?liq) AS ?"+Item.ValuesType.LIQUIDADO+") "+
				"(SUM(?pago) AS ?"+Item.ValuesType.PAGO+") "+
				"WHERE {"+
				"[] loa:temExercicio [loa:identificador "+year+"];";

		query += type.equals(ClassifierType.ORGAO) ? 
				"loa:"+ClassifierType.UO.getProperty()+" [loa:"+type.getProperty()+" [rdf:label ?"+type.getId()+" ; loa:codigo ?cod"+type.getId()+"]] ;"
				: "loa:"+type.getProperty()+" [rdf:label ?"+type.getId()+" ; loa:codigo ?cod"+type.getId()+"];";

		query += "loa:valorProjetoLei ?ploa ;"+
				"loa:valorDotacaoInicial ?loa ;"+
				"loa:valorLeiMaisCredito ?atual ;"+
				"loa:valorEmpenhado ?emp ;"+
				"loa:valorLiquidado ?liq ;"+
				"loa:valorPago ?pago "+
				"} " +
				"GROUP BY "+"?cod"+type.getId()+" ?"+type.getId()+" "+
				"ORDER BY "+"?cod"+type.getId()+" ?"+type.getId()+" ";


		return query;
	}

}
