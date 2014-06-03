package br.gov.planejamento.siop_app.test.util;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.gov.planejamento.siop_app.util.Validator;

public class ValidatorTest extends TestCase{
	
	public void testFormatterCorrectDouble(){
		Assert.assertEquals("12,00", Validator.convertDouble(12.00)); 
	}

	public void testFormatterNullDouble(){
		Assert.assertEquals("0,00", Validator.convertDouble(null)); 
	}

	public void testLongDouble(){
		Assert.assertEquals("9.123.123.123,00", Validator.convertDouble(9123123123.0));
		Assert.assertEquals("140.123.123.123,00", Validator.convertDouble(140123123123.0));
		Assert.assertEquals("12.123,00", Validator.convertDouble(12123.0));
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
	
}
