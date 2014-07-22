package br.gov.planejamento.siop_app.dao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SiopDatabase extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "SiopDatabase";
	public static final int DATABASE_VERSION = 1;

	public static final String TABLE_QUERY = "query";
	public static final String QUERY_NAME = "nome";
	public static final String QUERY_UO = "unidade";
	public static final String QUERY_PT = "programa_trabalho";
	public static final String QUERY_YEAR = "ano";

	public static final String SQL_CREATE_TABLE_QUERY = 
			"create table if not exists " +TABLE_QUERY+ "( " +
				QUERY_NAME+ " text NOT NULL, " +
				QUERY_UO+ " text NOT NULL, " +
				QUERY_PT+ " text NOT NULL, " +
				QUERY_YEAR+ " integer NOT NULL, " +
			"  primary key (" +QUERY_NAME+ ")" +
			")";

	public static final String SQL_DROP_TABLE_QUERY = 
			"drop table if exists " +TABLE_QUERY;
	
	private static SiopDatabase instance = null; 

	private SiopDatabase(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static SiopDatabase getInstance(Context myContext){
		if( instance == null ){
			instance = new SiopDatabase(myContext.getApplicationContext());
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DROP_TABLE_QUERY);
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if( !db.isReadOnly() ){
			db.execSQL("PRAGMA foreign_keys = ON;");
		}
	}
	
	public void clearDatabase(){
		SQLiteDatabase db;
		
		db = getWritableDatabase();
		onUpgrade(db, 0, 0);
	}
}
