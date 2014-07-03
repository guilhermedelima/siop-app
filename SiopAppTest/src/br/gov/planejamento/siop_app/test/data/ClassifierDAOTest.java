package br.gov.planejamento.siop_app.test.data;

import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.gov.planejamento.siop_app.dao.ClassifierDAO;
import br.gov.planejamento.siop_app.model.Classifier;
import br.gov.planejamento.siop_app.model.ClassifierType;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.service.EndpointSPARQL;
import br.gov.planejamento.siop_app.util.json.JsonClassifierParser;
import br.gov.planejamento.siop_app.util.json.JsonParser;

public class ClassifierDAOTest extends TestCase{
	
	private ClassifierDAO dao;
	
	@Override
	public void setUp() throws Exception {
		EndpointSPARQL endpoint = new EndpointSPARQL();
		JsonParser<List<HashMap<String, Object>>> parser = new JsonClassifierParser();
		
		this.dao = new ClassifierDAO(endpoint, parser);
	}
	
	public void testFuncao2012(){
		
		List<Item> funcaoList;
		Classifier classifier;
		
		funcaoList = dao.searchClassifier(ClassifierType.FUNCAO, 2014);

		Assert.assertNotNull(funcaoList);
		Assert.assertFalse(funcaoList.isEmpty());
		
		classifier = funcaoList.get(10).getClassifierList().get(0);
		
		Assert.assertEquals("Trabalho", classifier.getLabel());
		Assert.assertEquals("11", classifier.getCode());
	}
	
	public void testInvalidFuncao(){
		
		List<Item> funcaoList;
		
		funcaoList = dao.searchClassifier(ClassifierType.FUNCAO, 1900);

		Assert.assertNull(funcaoList);
	}

}
