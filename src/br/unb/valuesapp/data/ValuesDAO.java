package br.unb.valuesapp.data;

import java.util.List;

import br.unb.valuesapp.service.EndpointSPARQL;
import br.unb.valuesapp.util.json.JsonParser;
import br.unb.valuesapp.util.json.JsonValuesParser;

public class ValuesDAO {
	
	private static final String PLOA_SPARQL = "ploa";
	private static final String LOA_SPARQL = "loa";
	private static final String LEI_MAIS_CREDITO_SPARQL = "lei_mais_credito";
	private static final String EMPENHADO_SPARQL = "empenhado";
	private static final String LIQUIDADO_SPARQL = "liquidado";
	private static final String PAGO_SPARQL = "pago";
	
	public ValuesDAO(){
		
	}
	
	public List<Double> getValues(int year){
		
		String query, response;
		EndpointSPARQL endpoint;
		JsonParser<Double> parser;
		
		query = buildQuery(year);
		endpoint = new EndpointSPARQL();
		parser = new JsonValuesParser();
		
		response = endpoint.execSPARQLQuery(query);
		
		return response!=null ? parser.convertJsonToList(response) : null;
	}
	
	private String buildQuery(int year){
		
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				"PREFIX loa: <http://vocab.e.gov.br/2013/09/loa#>" +
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                "SELECT sum ( ?val1 ) as ?"+PLOA_SPARQL+" sum ( ?val2 ) as ?"+LOA_SPARQL+" sum(?val3) as ?"+LEI_MAIS_CREDITO_SPARQL+" sum(?val4) as ?"+EMPENHADO_SPARQL+" sum(?val5) as ?"+LIQUIDADO_SPARQL+" sum(?val6) as ?"+PAGO_SPARQL+" WHERE {" +
                "  ?i loa:temExercicio [loa:identificador "+year+"] ."+
                "  ?i loa:valorProjetoLei ?val1 ." +
                "  ?i loa:valorDotacaoInicial ?val2 ." +
                "  ?i loa:valorLeiMaisCredito ?val3 ." +
                "  ?i loa:valorEmpenhado ?val4 ." +
                "  ?i loa:valorLiquidado ?val5 ." +
                "  ?i loa:valorPago ?val6 ." +
                "}";
		
		return query;
	}

}
