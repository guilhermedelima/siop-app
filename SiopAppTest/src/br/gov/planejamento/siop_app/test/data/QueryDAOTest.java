package br.gov.planejamento.siop_app.test.data;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import br.gov.planejamento.siop_app.dao.QueryDAO;
import br.gov.planejamento.siop_app.dao.sqlite.SiopDatabase;
import br.gov.planejamento.siop_app.model.Query;

public class QueryDAOTest extends AndroidTestCase{
	
	private RenamingDelegatingContext testContext;
	private QueryDAO dao;
	
	//TODO: Testar Porque RenamingDelegatingContext Não cria um database separado
	@Override
	public void setUp() throws Exception {
		testContext = new RenamingDelegatingContext(getContext(), "test_");
		dao = new QueryDAO(testContext);
	}
	
	public void testSaveQuery(){
		Query q;
		boolean isOK;
		
		q = new Query("qToInsert", "14107", "02.122.0570.20GP.0053", 2013);
		isOK = dao.save(q);
		
		Assert.assertTrue(isOK);
	}
	
	public void testDeleteQuery(){
		Query q;
		boolean isSaved, isDeleted;
		
		q = new Query("qToDelete", "14107", "02.122.0570.20GP.0053", 2013);
		isSaved = dao.save(q);
		
		Assert.assertTrue(isSaved);
		
		isDeleted = dao.delete(q);
		
		Assert.assertTrue(isDeleted);
	}
	
	public void testInsertThreeQueries(){
		
		Query q1, q2, q3;
		List<Query> list;
		List<String> nameList;
		boolean isSaved;
		
		q1 = new Query("qList1", "14107", "02.122.0570.20GP.0053", 2013);
		q2 = new Query("qList2", "14107", "02.122.0570.20GP.0053", 2013);
		q3 = new Query("qList3", "14107", "02.122.0570.20GP.0053", 2013);
		
		isSaved = dao.save(q1);
		isSaved &= dao.save(q2);
		isSaved &= dao.save(q3);
		
		Assert.assertTrue(isSaved);
		
		list = dao.getAllObjects();
		
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() >= 3);
		
		nameList = new ArrayList<String>();
		
		for(Query q : list){
			nameList.add(q.getName());
		}
		
		Assert.assertTrue( nameList.contains(q1.getName()) );
		Assert.assertTrue( nameList.contains(q2.getName()) );
		Assert.assertTrue( nameList.contains(q3.getName()) );
	}
	
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		SiopDatabase db = SiopDatabase.getInstance(testContext);
		db.clearDatabase();
		db.close();
	}
}
