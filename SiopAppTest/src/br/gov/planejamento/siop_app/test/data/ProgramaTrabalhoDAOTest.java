package br.gov.planejamento.siop_app.test.data;

import java.util.HashMap;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.gov.planejamento.siop_app.dao.ProgramaTrabalhoDAO;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.service.EndpointSPARQL;
import br.gov.planejamento.siop_app.util.json.JsonParser;
import br.gov.planejamento.siop_app.util.json.JsonProgramaTrabalhoParser;

public class ProgramaTrabalhoDAOTest extends TestCase{
	
	private ProgramaTrabalhoDAO dao;
	
	@Override
	public void setUp() throws Exception {
		EndpointSPARQL endpoit = new EndpointSPARQL();
		JsonParser<HashMap<String, Object>> parser = new JsonProgramaTrabalhoParser();
		
		dao = new ProgramaTrabalhoDAO(endpoit, parser);
	}
	
	public void testQuery(){
		String pt, unidade;
		Item item;
		
		pt = "02.122.0570.20GP.0053";
		unidade = "14107";
		item = dao.getProgramaTrabalho(2012, unidade, pt);
		
		Assert.assertNotNull(item);
		Assert.assertNotNull(item.getClassifierList());
		Assert.assertTrue(item.getClassifierList().size() == 6);
		Assert.assertEquals(12651855.0, item.getValueProjetoLei());
		Assert.assertEquals("Judiciária", item.getClassifierList().get(0).getLabel());
		Assert.assertEquals("02", item.getClassifierList().get(0).getCode());
	}
	
	public void testInvalidPT(){
		
		String pt, unidade;
		Item item;
		
		pt = "02.122.0570.20GP.0053";
		unidade = "14107";
		item = dao.getProgramaTrabalho(1900, unidade, pt);
		
		Assert.assertNull(item);
	}

}
