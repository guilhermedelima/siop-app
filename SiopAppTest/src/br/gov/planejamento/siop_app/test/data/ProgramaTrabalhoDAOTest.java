package br.gov.planejamento.siop_app.test.data;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.gov.planejamento.siop_app.dao.ProgramaTrabalhoDAO;
import br.gov.planejamento.siop_app.model.Item;

public class ProgramaTrabalhoDAOTest extends TestCase{
	
	private ProgramaTrabalhoDAO dao;
	
	@Override
	public void setUp() throws Exception {
		dao = new ProgramaTrabalhoDAO();
	}
	
	public void testQuery(){
		String pt, unidade;
		Item item;
		
		pt = "02.122.0570.20GP";
		unidade = "14107";
		item = dao.getProgramaTrabalho(2012, unidade, pt);
		
		Assert.assertNotNull(item);
		Assert.assertNotNull(item.getClassifierList());
		Assert.assertTrue(item.getClassifierList().size() == 5);
		Assert.assertEquals(12651855.0, item.getValueProjetoLei());
		Assert.assertEquals("Judiciária", item.getClassifierList().get(2).getLabel());
		Assert.assertEquals("02", item.getClassifierList().get(2).getCode());
	}

}
