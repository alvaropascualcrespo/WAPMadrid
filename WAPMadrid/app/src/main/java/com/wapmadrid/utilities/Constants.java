package com.wapmadrid.utilities;

/**
 * Created by Ismael on 18/05/2015.
 */
public class Constants {


    public static final String FRAGMENT_POSITION = "FRAGMENT_POSITION";
    public static final int RESULT_EDIT = 0;
    public static final int NUEVO_GRUPO = 1;
    public static final int RESULT_SELECTED_ROUTE = 2;
    public static final String GROUP_ID = "GROUP_ID";
    public static final String WALKER_ID = "WALKER_ID";
    public static final int RESULT_SELECTED_CMS = 3;

    public static String getSmoker(int smoker){
        switch (smoker){
            case 0: return "No";
            case 1: return "Si";
            default: return "No indicado";
        }
    }

    public static String getAlcohol(int alcohol){
        switch (alcohol){
            case 0: return "No";
            case 1: return "Moderadamente";
            case 2: return "Con Frecuencia";
            default: return "No indicado";
        }
    }
}
