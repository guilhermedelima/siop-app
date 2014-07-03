package br.gov.planejamento.siop_app.test.util.json;

import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.gov.planejamento.siop_app.model.ClassifierType;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.util.json.JsonClassifierParser;
import br.gov.planejamento.siop_app.util.json.JsonParser;

public class JsonClassifierParserTest extends TestCase{
	
	private String response, emptyResponse;
	private JsonParser<List<HashMap<String, Object>>> parser;
	
	@Override
	public void setUp() throws Exception {
		response = "{ \"head\": { \"link\": [], \"vars\": [\"codfuncao\", \"funcao\", \"ploa\", \"loa\", \"atual\", \"empenhado\", \"liquidado\", \"pago\"] }, \"results\": { \"distinct\": false, \"ordered\": true, \"bindings\": [ { \"codfuncao\": { \"type\": \"literal\", \"value\": \"01\" }	, \"funcao\": { \"type\": \"literal\", \"value\": \"Legislativa\" }	, \"ploa\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"6983294099\" }	, \"loa\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"6950840498\" }	, \"atual\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"7001452254\" }	, \"empenhado\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"6136523012.1\" }	, \"liquidado\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"2552470823.06\" }	, \"pago\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"2549019393.75\" }},     { \"codfuncao\": { \"type\": \"literal\", \"value\": \"99\" }	, \"funcao\": { \"type\": \"literal\", \"value\": \"Reserva de Conting\u00EAncia\" }	, \"ploa\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"34567676643\" }	, \"loa\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"31577857866\" }	, \"atual\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"30955942735\" }	, \"empenhado\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"0\" }	, \"liquidado\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"0\" }	, \"pago\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"0\" }} ] } }";
		emptyResponse = "{ \"head\": { \"link\": [], \"vars\": [\"codfuncao\", \"funcao\", \"ploa\", \"loa\", \"atual\", \"empenhado\", \"liquidado\", \"pago\"] }, \"results\": { \"distinct\": false, \"ordered\": true, \"bindings\": [ ] } }";
		parser = new JsonClassifierParser();
	}
	
	public void testResponseText(){
		
		List<HashMap<String, Object>> listMap;
		
		listMap = parser.convertJsonToObject(response);
		
		Assert.assertNotNull(listMap);		
		Assert.assertEquals(2, listMap.size());
		
		for(HashMap<String, Object> item : listMap){
			Assert.assertTrue(item.containsKey("cod"+ClassifierType.FUNCAO.getId()));
			Assert.assertTrue(item.containsKey(ClassifierType.FUNCAO.getId()));
			Assert.assertTrue(item.containsKey(Item.ValuesType.PLOA.toString()));
			Assert.assertTrue(item.containsKey(Item.ValuesType.LOA.toString()));
			Assert.assertTrue(item.containsKey(Item.ValuesType.LEI_MAIS_CREDITOS.toString()));
			Assert.assertTrue(item.containsKey(Item.ValuesType.EMPENHADO.toString()));
			Assert.assertTrue(item.containsKey(Item.ValuesType.LIQUIDADO.toString()));
			Assert.assertTrue(item.containsKey(Item.ValuesType.PAGO.toString()));
			
//			for(Entry<String, Object> entry : item.entrySet()){
//				System.out.println(entry.getKey() +" -> "+ entry.getValue());
//			}
		}
	}
	
	public void testEmptyResponseText(){
		
		List<HashMap<String, Object>> listMap;
		
		listMap = parser.convertJsonToObject(emptyResponse);
		
		Assert.assertNotNull(listMap);
		Assert.assertTrue(listMap.isEmpty());
		
	}

	public void testWrongResponseText(){
		List<HashMap<String, Object>> listMap;
		
		listMap = parser.convertJsonToObject("Invalid Response Text");
		
		Assert.assertNull(listMap);
	}
	
}
