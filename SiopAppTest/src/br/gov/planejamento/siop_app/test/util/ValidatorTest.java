package br.gov.planejamento.siop_app.test.util;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.gov.planejamento.siop_app.util.Validator;

public class ValidatorTest extends TestCase{
	
	public void testFormatterCorrectDouble(){
		Assert.assertEquals("R$ 12,00", Validator.convertDouble(12.00)); 
	}

	public void testFormatterNullDouble(){
		Assert.assertEquals("R$ 0,00", Validator.convertDouble(null)); 
	}

	public void testLongDouble(){
		Assert.assertEquals("R$ 9.123.123.123,00", Validator.convertDouble(9123123123.0));
		Assert.assertEquals("R$ 140.123.123.123,00", Validator.convertDouble(140123123123.0));
		Assert.assertEquals("R$ 12.123,00", Validator.convertDouble(12123.0));
	}
	
	public void testPT(){
		Assert.assertTrue(Validator.checkPT("02.122.0570.20GP.0053"));
		Assert.assertTrue(Validator.checkPT("02.122.0570.XXXX.GGGG"));
		Assert.assertTrue(Validator.checkPT("02.122.0570.1111.0000"));
	}
	
	public void testInvalidPT(){
		Assert.assertFalse(Validator.checkPT("02.122.0570.20GP.005*"));
		Assert.assertFalse(Validator.checkPT("02.122.057A.20GP.005A"));
		Assert.assertFalse(Validator.checkPT("02.1X2.0570.20GP.005A"));
		Assert.assertFalse(Validator.checkPT("0A.122.0570.20GP.005A"));
		Assert.assertFalse(Validator.checkPT("0|19.122.0570.20GP.005A"));
		Assert.assertFalse(Validator.checkPT("09.122.0570.20GP."));
		Assert.assertFalse(Validator.checkPT("09.122.0570.20GP"));
		Assert.assertFalse(Validator.checkPT("09.122.0570.20GP.9999.9999"));
		Assert.assertFalse(Validator.checkPT(null));
	}

	public void testUO(){
		Assert.assertTrue(Validator.checkUO("00000"));
		Assert.assertTrue(Validator.checkUO("12345"));
		Assert.assertTrue(Validator.checkUO("99999"));
	}
	
	public void testInvalidUO(){
		Assert.assertFalse(Validator.checkUO("A0000"));
		Assert.assertFalse(Validator.checkUO("1111"));
		Assert.assertFalse(Validator.checkUO("*****"));
		Assert.assertFalse(Validator.checkUO(null));
	}
	
	public void testQueryName(){
		Assert.assertTrue(Validator.checkQueryName("Ab0"));
		Assert.assertTrue(Validator.checkQueryName("1234567890QWERTasdfg"));
		Assert.assertTrue(Validator.checkQueryName("Guilherme123"));
		Assert.assertTrue(Validator.checkQueryName("consulta basica 2"));
	}
	
	public void testInvalidName(){
		Assert.assertFalse(Validator.checkQueryName("Ab"));
		Assert.assertFalse(Validator.checkQueryName("1234567890QWERTasdfg1"));
		Assert.assertFalse(Validator.checkQueryName("Guilherme123;*"));
		Assert.assertFalse(Validator.checkQueryName(" amigo"));
		Assert.assertFalse(Validator.checkQueryName("   "));
	}
	
}
