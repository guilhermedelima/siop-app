package br.gov.planejamento.siop_app.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import br.gov.planejamento.siop_app.test.data.ClassifierDAOTest;
import br.gov.planejamento.siop_app.test.data.ProgramaTrabalhoDAOTest;
import br.gov.planejamento.siop_app.test.data.QueryDAOTest;
import br.gov.planejamento.siop_app.test.service.EndpointSPARQLTest;
import br.gov.planejamento.siop_app.test.service.InternetAccessTest;
import br.gov.planejamento.siop_app.test.util.ValidatorTest;
import br.gov.planejamento.siop_app.test.util.json.JsonClassifierParserTest;
import br.gov.planejamento.siop_app.test.util.json.JsonProgramaTrabalhoParserTest;
import br.gov.planejamento.siop_app.test.util.json.JsonValuesParserTest;

public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$

		suite.addTestSuite(InternetAccessTest.class);
		suite.addTestSuite(EndpointSPARQLTest.class);
		suite.addTestSuite(ValidatorTest.class);
		suite.addTestSuite(JsonValuesParserTest.class);
		suite.addTestSuite(JsonProgramaTrabalhoParserTest.class);
		suite.addTestSuite(JsonClassifierParserTest.class);
		suite.addTestSuite(ProgramaTrabalhoDAOTest.class);
		suite.addTestSuite(ClassifierDAOTest.class);
		suite.addTestSuite(QueryDAOTest.class);
		
		//$JUnit-END$
		return suite;
	}

}
