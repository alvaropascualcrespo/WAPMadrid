package com.wapmadrid.utilities;

public class Helper {

	public final static String BASE_API_URL = "http://www.proyectowap.tk:3100/api/";
	
	public static String getDefaultRutaPictureUrl(){
		return BASE_API_URL + "login/login.php"; 
	}
	
	public static String getRegistroUrl(){
		return BASE_API_URL + "walkers/register"; 
	}
	
	public static String getLoginUrl(){
		return BASE_API_URL + "walkers/login"; 
	}
	
	public static String getLogOutUrl(String id){
		return BASE_API_URL + "walkers/logout:" + id + ".php"; 
	}
	
	public static String getReadUrl(String id){
		return BASE_API_URL + "walkers/read:" + id;
	}
	
	public static String getCMSUrl(String id){
		return BASE_API_URL + "walkers/cms/:" + id; 
	}

	public static String getMiPerfilUrl(String id) {
		return BASE_API_URL + "walkers/read:" + id;
	}

	public static String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getSendMessageUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getDefaultProfilePictureUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getFriendsMessagesUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getPeticionesAmigosUrl(String id) {
		return BASE_API_URL + "walkers/read/friends:" + id;
	}

	public static String getAmigosUrl(String id) {
		return BASE_API_URL + "walkers/read:" + id;
	}

	public static String getGruposUrl(String id) {
		return BASE_API_URL + "walkers/groups:" + id;
	}
}
