package com.wapmadrid.utilities;

public class Helper {

    public final static String BASE_API_URL = "http://www.proyectowap.tk:3100/api/";

    public static String getDefaultRutaPictureUrl() {
        return "http://www.proyectowap.tk/images/profiles/profile_default.png";
    }

    public static String getRegistroUrl() {
        return BASE_API_URL + "walkers/register";
    }

    public static String getLoginUrl() {
        return BASE_API_URL + "walkers/login";
    }

    public static String getLogOutUrl(String id) {
        return BASE_API_URL + "walkers/logout:" + id;
    }

    public static String getReadUrl(String id) {
        return BASE_API_URL + "walkers/read/" + id;
    }

    public static String getCMSUrl(String id) {
        return BASE_API_URL + "walkers/cms/" + id;
    }

    public static String getMiPerfilUrl(String id) {
        return BASE_API_URL + "walkers/read/" + id;
    }

    public static String getUpdateDietaUrl(String id) {
        return BASE_API_URL + "walkers/update/diet/" + id;
    }

    public static String getUpdateStatusUrl(String id) {
        return BASE_API_URL + "walkers/update/status/" + id;
    }

    public static String getUpdateInfoUrl(String id) {
        return BASE_API_URL + "walkers/update/info/" + id;
    }

    public static String getCreateGroupUrl(String id) {
        return BASE_API_URL + "groups/create/" + id;
    }

    public static String getCreateRouteUrl(String id) {
        return BASE_API_URL + "routes/create/" + id;
    }

    public static String getGetRoutesUrl() {
        return BASE_API_URL + "routes/all";
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
        return "http://www.proyectowap.tk/images/profiles/profile_default.png";
    }

    public static String getFriendsMessagesUrl() {
        // TODO Auto-generated method stub
        return null;
    }

    public static String getPeticionesAmigosUrl(String id) {
        return BASE_API_URL + "walkers/read/friends/" + id;
    }

    public static String getAmigosUrl(String id) {
        return BASE_API_URL + "walkers/friends/" + id;
    }

    public static String getGruposUrl(String id) {
        return BASE_API_URL + "walkers/groups/" + id;
    }

    public static String getGruposListUrl(String id) {
        return BASE_API_URL + "groups/list/" + id;
    }

    public static String getReadGrupoUrl(String id) {
        return BASE_API_URL + "groups/" + id;
    }

    public static String getReadGrupoMembersUrl(String id) {
        return BASE_API_URL + "groups/members/" + id;
    }
 public static String getWalkerRoutesUrl(String id) {
        return BASE_API_URL + "walkers/routes/" + id;
    }


    public static String getJoinGrupoUrl(String id) {
        return BASE_API_URL + "groups/join/" + id;
    }

    public static String getReadRouteUrl(String id) {
        return BASE_API_URL + "routes/" + id;
    }

    public static String getEventsUrl() {
        return BASE_API_URL + "events";
        }

    public static String getCMSListUrl(String id) {
        return BASE_API_URL + "cms/list/" + id;
    }

    public static String getLeaveGrupoUrl(String id) {
        return BASE_API_URL + "groups/leave/" + id;
    }

    public static String getSendGrupoStatsUrl(String id) {
        return BASE_API_URL + "groups/stats/" + id;
    }

    public static String getUpdateGroupUrl(String id) {
        return BASE_API_URL + "groups/update/" + id;
    }

    public static String getMessagesUrl(String id) {
        return BASE_API_URL + "groups/messages/" + id;
    }
}
