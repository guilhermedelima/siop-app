package br.unb.valuesapp.test.util;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.unb.valuesapp.util.Validator;

public class ValidatorTest extends TestCase{
	
	public void testValidYear(){
		Integer year = 2012;
		Assert.assertEquals(year, Validator.convertYear("2012"));
	}
	
	public void testInvalidYear(){
		Assert.assertNull(Validator.convertYear("123412"));
		Assert.assertNull(Validator.convertYear("sdfnk"));
	}
	
	public void testFormatterCorrectDouble(){
		
		System.out.println(Validator.convertDouble(12.00));
		Assert.assertEquals("R$ 12,00", Validator.convertDouble(12.00)); 
	}
	
	public void testFormatterNullDouble(){
		Assert.assertEquals("R$ 0,00", Validator.convertDouble(null)); 
	}

}
