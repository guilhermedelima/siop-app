package br.gov.planejamento.siop_app.test.util.json;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.gov.planejamento.siop_app.service.EndpointSPARQL;
import br.gov.planejamento.siop_app.util.json.JsonParser;
import br.gov.planejamento.siop_app.util.json.JsonValuesParser;

public class JsonValuesParserTest extends TestCase{
	
	private JsonParser<List<Double>> parser;
	private EndpointSPARQL service;
	
	@Override
	public void setUp() throws Exception {
		parser = new JsonValuesParser();
		service = new EndpointSPARQL();
	}
	
	public void testValuesFrom2013(){
		List<Double> values;
		String query, result;
		
		query = "PREFIX loa: <http://vocab.e.gov.br/2013/09/loa#>"+
				"select (sum(?val1) as ?ploa) (sum(?val2) as ?loa) where{ "+
				"  ?i loa:temExercicio [loa:identificador 2013] ; "+
				"  loa:valorProjetoLei ?val1; "+
				" loa:valorDotacaoInicial ?val2. "+
				"}";
		
		result = service.execSPARQLQuery(query);
		
		Assert.assertNotNull(result);
		
		values = parser.convertJsonToObject(result);
		
		Assert.assertNotNull(values);
		Assert.assertFalse(values.isEmpty());
		
		for(Double v : values){
			System.out.println(v);
		}
	}
	
	public void testValuesWithoutResult(){
		List<Double> values;
		String query, result;
		
		query = "PREFIX loa: <http://vocab.e.gov.br/2013/09/loa#>"+
				"select (sum(?val1) as ?ploa) (sum(?val2) as ?loa) where{ "+
				"  ?i loa:temExercicio [loa:identificador 1999] ; "+
				"  loa:valorProjetoLei ?val1; "+
				" loa:valorDotacaoInicial ?val2. "+
				"}";
		
		result = service.execSPARQLQuery(query);
		
		Assert.assertNotNull(result);
		
		values = parser.convertJsonToObject(result);
		
		Assert.assertNull(values);
	}

}
