package com.student.oclass.data;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * 数据库帮助类
 * @author lanyj
 *
 */
public class DBHelper extends SQLiteOpenHelper{
	
	private static final String DB_NAME = "oclass";
	private static final int DB_VERSION = 1;
	private  SQLiteDatabase db;
	private static DBHelper mdbHelper;
	public static DBHelper getInstance(Context context)
	{
		if(mdbHelper == null)
		{
			mdbHelper=new DBHelper(context);
		}
		return mdbHelper;
	}
	
	private DBHelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}
	
/*	private DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}*/

	@Override
	public void onCreate(SQLiteDatabase db) {
		DBSQLVersion.v1(db);
		this.db = db;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion==newVersion){
			return ;
		}
		DBSQLVersion.vUpdate(db, oldVersion, newVersion);
	}
	
	/*public void operateTable(SQLiteDatabase db, String actionString) {
		Class<DatabaseColumn>[] columnsClasses = DatabaseColumn.getSubClasses();
		DatabaseColumn columns = null;

		for (int i = 0; i < columnsClasses.length; i++) {
			try {
				columns = columnsClasses[i].newInstance();
				
				if ("".equals(actionString) || actionString == null) {
					db.execSQL(columns.getTableCreateor());
				} else {
					db.execSQL(actionString + columns.getTableName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/

	public long insert(String tableName, ContentValues values) {
		if (db == null)
			db = getWritableDatabase();
		return db.insert(tableName, null, values);
	}
	
	public void insertTransaction(List<Map<String,ContentValues>> data){
		if (db == null)
			db = getWritableDatabase();
		db.beginTransaction();
		try{
			String tableName;
			ContentValues values;
			for (int i = 0; i < data.size(); i++) {
				Iterator iterator = data.get(i).entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String,ContentValues> entry = (Entry<String, ContentValues>) iterator.next(); 
					tableName = (String) entry.getKey();
					values = (ContentValues) entry.getValue();
					db.insert(tableName, null, values);
				}
			}
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	/**
	 * 
	 * @param TableName
	 * @param id
	 * @return 影响行数
	 */
	public int delete(String TableName, int id) {
		if (db == null)
			db = getWritableDatabase();
		return db.delete(TableName, BaseColumns._ID + "=?",
				new String[] { String.valueOf(id) });
	}
	
	public int delete(String table,String whereClause,String[] whereArgs){
		if(db==null){
			db = getWritableDatabase();
		}
		return db.delete(table, whereClause, whereArgs);
	}

	/**
	 * @param TableName
	 * @param values
	 * @param WhereClause
	 * @param whereArgs
	 * @return 影响行数
	 */
	public int update(String TableName, ContentValues values,String WhereClause, String[] whereArgs) {
		if (db == null) {
			db = getWritableDatabase();
		}
		return db.update(TableName, values, WhereClause, whereArgs);
	}

	public Cursor query(String TableName, String[] columns, String whereStr,
			String[] whereArgs) {
		if (db == null) {
			db = getReadableDatabase();
		}
		return db.query(TableName, columns, whereStr, whereArgs, null, null,
				null);
	}

	public Cursor rawQuery(String sql, String[] args) {
		if (db == null) {
			db = getReadableDatabase();
		}
		return db.rawQuery(sql, args);
	}

	public void execSQL(String sql) {
		if (db == null) {
			db = getWritableDatabase();
		}
		db.execSQL(sql);
	}
	

	
	
	public void execSQL(String sql,Object[] bindArgs) {
		if (db == null) {
			db = getWritableDatabase();
		}
		db.execSQL(sql, bindArgs);
	}
	

	public void closeDb() {
		if (db != null) {
			db.close();
			db = null;
		}
	}

}
