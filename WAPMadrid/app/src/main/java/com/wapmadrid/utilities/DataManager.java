package com.wapmadrid.utilities;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataManager {
	
	DBManager dbm;
	
	public DataManager(Context context){
		dbm = new DBManager(context);
	}
	
	public boolean checkLogin(){
			SQLiteDatabase db = dbm.getWritableDatabase();
			String[] rows = {"idProfile"};
			boolean logedin = false;
			Cursor c = db.query("UserLogin",rows,null,null,null,null,null);
			try{
				logedin = c.moveToFirst();
			} finally {
				 c.close();
				 db.close();
			}
			return logedin;
	}
	
	public String[] getCred(){
		SQLiteDatabase db = dbm.getWritableDatabase();
		String[] rows = {"idProfile,token"};	
		String[] aux = new String[2];
		Cursor c = db.query("UserLogin",rows,null,null,null,null,null);
		try{
			if (c.moveToFirst()){
				 aux[0] = c.getString(0);
				 aux[1] = c.getString(1);
			}
		} finally {
			 c.close();
			db.close();
		}
		return aux;
	}
	

	
	
	public void login(String idProfile, String token){
		SQLiteDatabase db = dbm.getWritableDatabase();
		try{
			db.execSQL("INSERT INTO UserLogin (idProfile,token) " +
	            	"VALUES (\'" + idProfile + "\',\'" + token + "\')");
		}catch (Exception e){
			Log.e("Error Login", e.getMessage());
		}
		db.close();
	}
	
	public void logout(){		
		SQLiteDatabase db = dbm.getWritableDatabase();
		db.delete("UserLogin",null,null);
		Log.e("db","Borrado");
		db.close();
	}
}
