package com.example.coach.outils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class MesOutils {
    /**
     * Convertion string to Date
     * @param uneDate
     * @return
     */
    public static Date convertStringToDate(String uneDate){
        return convertStringToDate(uneDate,"EEE MM dd hh:mm:ss 'GMT' yyyy");

    }

    /**
     * Convertion d'une date en chaine sous la forme yyyy-MM-dd hh:mm:ss
     * @param uneDate
     * @return
     */
    public static String convertDateToString(Date uneDate){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return date.format(uneDate);
    }

    /**
     * Convertie une date sous forme de string avec un format
     * @param uneDate
     * @param formatAttendu
     * @return
     */
    public static Date convertStringToDate(String uneDate, String formatAttendu){
        SimpleDateFormat formatter = new SimpleDateFormat(formatAttendu);
        try {
            Date date = formatter.parse(uneDate);
            return date;
        }catch (ParseException e){
            Log.d("Date Formatage", "Erreur date impossible a parser : "+ e.toString());
        }
        return null;
    }

    public static String format2Deciaml(float valeur){
        return String.format("%.01f", valeur);
    }
}
