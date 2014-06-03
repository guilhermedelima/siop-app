package br.gov.planejamento.siop_app.test.util.json;

import java.util.HashMap;
import java.util.Map.Entry;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.gov.planejamento.siop_app.util.json.JsonParser;
import br.gov.planejamento.siop_app.util.json.JsonProgramaTrabalhoParser;

public class JsonProgramaTrabalhoParserTest extends TestCase{
	
	private String response;
	private JsonParser<HashMap<String, Object>> parser;
	
	@Override
	public void setUp() throws Exception {
		parser = new JsonProgramaTrabalhoParser();
		response = "{ \"head\": { \"link\": [], \"vars\": [\"funcao\", \"subfuncao\", \"uo\", \"programa\", \"acao\", \"ploa\", \"loa\", \"atual\", \"empenhado\", \"liquidado\", \"pago\"] },"+
				  "\"results\": { \"distinct\": false, \"ordered\": true, \"bindings\": ["+
				    "{ \"funcao\": { \"type\": \"literal\", \"value\": \"Judici\u00E1ria\" }	, \"subfuncao\": { \"type\": \"literal\", \"value\": \"Administra\u00E7\u00E3o Geral\" }	, \"uo\": { \"type\": \"literal\", \"value\": \"Tribunal Regional Eleitoral do Distrito Federal\" }	, \"programa\": { \"type\": \"literal\", \"value\": \"Gest\u00E3o do Processo Eleitoral\" }	, \"acao\": { \"type\": \"literal\", \"value\": \"Julgamento de Causas e Gest\u00E3o Administrativa na Justi\u00E7a Eleitoral\" }	, \"ploa\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"12651855\" }	, \"loa\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"12651855\" }	, \"atual\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"15560929\" }	, \"empenhado\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"14236189.59\" }	, \"liquidado\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"14236189.59\" }	, \"pago\": { \"type\": \"typed-literal\", \"datatype\": \"http://www.w3.org/2001/XMLSchema#double\", \"value\": \"9572739.35\" }} ] } }";
	}
	
	public void testResponseText(){
		HashMap<String, Object> map;
		
		map = parser.convertJsonToObject(response);
		
		Assert.assertNotNull(map);
		Assert.assertFalse(map.isEmpty());
		
		for(Entry<String, Object> entry : map.entrySet()){
			System.out.println(entry.getKey() +" -> "+ entry.getValue());
		}
	}
	
	public void testWrongResponseText(){
		HashMap<String, Object> map;
		
		map = parser.convertJsonToObject("Invalid Response Text");
		
		Assert.assertNull(map);
	}

}
