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

}
