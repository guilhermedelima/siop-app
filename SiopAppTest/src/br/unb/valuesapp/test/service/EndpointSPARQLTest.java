package br.unb.valuesapp.test.service;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.unb.valuesapp.service.EndpointSPARQL;

public class EndpointSPARQLTest extends TestCase {
	
	private String query;
	
	@Override
	public void setUp(){
		query = "select distinct ?Concept where {[] a ?Concept}";
	}
	
	public void testHttpResponseWithValidQuery(){
		EndpointSPARQL service;
		String result;
		
		service = new EndpointSPARQL();
		result = service.execSPARQLQuery(query);
		
		Assert.assertNotNull(result);
		Assert.assertTrue(result.length() > 0);
		
		System.out.println(result);
	}

}
