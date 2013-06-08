package com.android.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	
	private static final String CATEGORIA = "livro";
	private String[] scriptSQLCreate;
	private String scriptSQLDelete;
	
	public SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptSQLCreate, String scriptSQLDelete) {
		super(context, nomeBanco, null, versaoBanco);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
	}

	@Override
	//Cria o novo banco de dados
	public void onCreate(SQLiteDatabase db) {
		int qtdScripts = scriptSQLCreate.length;
		//Executa cada comando SQL passado como parametro
		for(int a = 0; a < qtdScripts; a++){
			String sql = scriptSQLCreate[a];
			Log.i(CATEGORIA, sql);
			//Cria o banco de dados usando o script de criacao
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
