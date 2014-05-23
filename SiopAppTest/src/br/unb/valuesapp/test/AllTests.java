package br.unb.valuesapp.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import br.unb.valuesapp.test.service.EndpointSPARQLTest;
import br.unb.valuesapp.test.service.InternetAccessTest;
import br.unb.valuesapp.test.util.ValidatorTest;
import br.unb.valuesapp.test.util.json.JsonValuesParserTest;

public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$

		suite.addTestSuite(InternetAccessTest.class);
		suite.addTestSuite(EndpointSPARQLTest.class);
		suite.addTestSuite(ValidatorTest.class);
		suite.addTestSuite(JsonValuesParserTest.class);
		
		//$JUnit-END$
		return suite;
	}

}
