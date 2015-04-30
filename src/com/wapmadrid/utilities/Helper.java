package com.wapmadrid.utilities;

public class Helper {

	public final static String BASE_API_URL = "http://wapmadrid.madridsalud.es//";
	
	public static String getDefaultRutaPictureUrl(){
		return BASE_API_URL + "login/login.php"; 
	}
	
	public static String getRegistroUrl(){
		return BASE_API_URL + "walkers/register.php"; 
	}
	
	public static String getLoginUrl(){
		return BASE_API_URL + "walkers/login.php"; 
	}
	
	public static String getLogOutUrl(String id){
		return BASE_API_URL + "walkers/logout:" + id + ".php"; 
	}
	
	public static String getReadUrl(String id){
		return BASE_API_URL + "walkers/read:" + id + ".php";
	}
}
