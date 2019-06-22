package com.student.oclass.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库版本更新
 * @author lanyj
 *
 */
public class DBSQLVersion {

	/**
	 * 第一次创建
	 */
	public static void v1(SQLiteDatabase db) {
		// JadeAttributeType
		StringBuffer sql = new StringBuffer();
		//反馈建议
		sql = new StringBuffer();
		sql.append("CREATE TABLE feedBack(");
		sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");	
		sql.append("name VARCHAR(40),");
		sql.append("content VARCHAR(200),");
		sql.append("status INTEGER DEFAULT(0),");
		sql.append("remark VARCHAR(200),");	
		sql.append("time DATETIME DEFAULT(datetime('now','localtime')));");
		db.execSQL(sql.toString());
	}

	/**
	 * 更新sql， 注：每一次更新版本version加1
	 * 
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	public static void vUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {
//		List<String[]> updateSQL = new ArrayList<String[]>();
//		String[] sqls = new String[1];
//		StringBuffer sql = new StringBuffer();
//		/**
//		 *  
//		 *  2014-04-14 v1
//		 */
//		sqls = new String[1];
//		sql = new StringBuffer();
//		sql.append("CREATE TABLE feedBack(");
//		sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");	
//		sql.append("name VARCHAR(40),");
//		sql.append("content VARCHAR(200),");
//		sql.append("status INTEGER DEFAULT(0),");
//		sql.append("remark VARCHAR(200),");	
//		sql.append("time DATETIME DEFAULT(datetime('now','localtime')));");
//		sqls[0] = sql.toString();
//		updateSQL.add(sqls);
//		
//		
//		for (int i = oldVersion; i < updateSQL.size(); i++) {
//			String[] arrays = updateSQL.get(i);
//			for (int j = 0; j < arrays.length; j++) {
//				db.execSQL(arrays[j]);
//			}
//		}
	}
}