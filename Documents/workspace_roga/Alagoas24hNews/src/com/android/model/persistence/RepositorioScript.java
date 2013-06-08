package com.android.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RepositorioScript {
	
	private SQLiteDatabase db;
	
	// Context variable to set context of main activity
	private Context context;

	// A string to represent the script sql to create the database
	private static final String[] SCRIPT_DATABASE_CREATE = new String[]{
		"CREATE TABLE favorites	   (title text not null primary key, date text not null, description text not null);"
		};
	
	// Database name
	private static final String NOME_BANCO = "rssFeed";
	// Database version
	private static final int VERSAO_BANCO = 1;
	
	// Utility class to create and refresh database
	private static SQLiteHelper dbHelper;
	

	public RepositorioScript(Context ctx) {
		// Sets the Content variable
		context = ctx;
		
		// Build the database using a scrip SQL defined
		dbHelper = new SQLiteHelper(ctx, NOME_BANCO, VERSAO_BANCO, SCRIPT_DATABASE_CREATE, null);
		
		// Be able the writable database
		db = dbHelper.getWritableDatabase();
	}
	
	// Method to close database
	public void fechar() {
		if(dbHelper != null){
			dbHelper.close();
		}
	}
}
