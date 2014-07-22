package br.gov.planejamento.siop_app.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.gov.planejamento.siop_app.dao.sqlite.SiopDatabase;
import br.gov.planejamento.siop_app.model.Query;

public class QueryDAO {
	
	private SiopDatabase dbSiop;
	
	public QueryDAO(Context context){
		dbSiop = SiopDatabase.getInstance(context);
	}
	
	public boolean save(Query query){
		
		SQLiteDatabase db;
		ContentValues values;
		long insertStatus;
		
		values = new ContentValues();
		values.put(SiopDatabase.QUERY_NAME, query.getName());
		values.put(SiopDatabase.QUERY_UO, query.getUo());
		values.put(SiopDatabase.QUERY_PT, query.getPtCod());
		values.put(SiopDatabase.QUERY_YEAR, query.getYear());
		
		db = dbSiop.getWritableDatabase();
		insertStatus = db.insertOrThrow(SiopDatabase.TABLE_QUERY, null, values);
		db.close();
		
		return insertStatus >= 0;
	}
	
	public boolean delete(Query query){
		
		SQLiteDatabase db;
		String whereClause;
		int deletedRows;
		
		whereClause = SiopDatabase.QUERY_NAME+ " LIKE '" +query.getName()+ "'"; 
		
		db = dbSiop.getWritableDatabase();
		deletedRows = db.delete(SiopDatabase.TABLE_QUERY, whereClause, null);
		db.close();
		
		return deletedRows > 0;
	}

	public List<Query> getAllObjects(){
		
		SQLiteDatabase db;
		Cursor result;
		String sqlQuery;
		String name, uo, pt;
		int year;
		List<Query> listQueries;
		
		sqlQuery = "select * from " + SiopDatabase.TABLE_QUERY;
		listQueries = new ArrayList<Query>();
		
		db = dbSiop.getWritableDatabase();
		result = db.rawQuery(sqlQuery, null);
		
		while( result.moveToNext() ){
			
			name = result.getString( result.getColumnIndex(SiopDatabase.QUERY_NAME) );
			uo = result.getString( result.getColumnIndex(SiopDatabase.QUERY_UO) );
			pt = result.getString( result.getColumnIndex(SiopDatabase.QUERY_PT) );
			year = result.getInt( result.getColumnIndex(SiopDatabase.QUERY_YEAR) );
			
			listQueries.add( new Query(name, uo, pt, year) );
		}
		
		result.close();
		db.close();
		
		return listQueries;
	}
	
}