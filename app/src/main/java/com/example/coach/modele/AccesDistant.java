package com.example.coach.modele;

import android.util.Log;

import com.example.coach.controleur.Control;
import com.example.coach.outils.AccesHTTP;
import com.example.coach.outils.AsyncResponse;
import com.example.coach.outils.MesOutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class AccesDistant implements AsyncResponse {
    private static final String SERVEURADDR = "http://192.168.1.12/coach/serveurcoach.php";
    private Control control;

    public AccesDistant(){
        control = Control.getInstance(null);
        //super();
    }
    /**
     * retour du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) {
        Log.d("serveur", "****************" + output);
        // decoupage du msg recu
        String[] message = output.split("%");
        if (message.length > 1){
            if (message[0].equals("enreg")){
                Log.d("enreg", "***************" + message[1]);
            }else {
                if (message[0].equals("dernier")){

                    try {
                        JSONObject info = new JSONObject(message[1]);
                        Integer poids = info.getInt("poids");
                        Integer taille = info.getInt("taille");
                        Integer age = info.getInt("age");
                        Integer sexe = info.getInt("sexe");
                        String datemesure = info.getString("dateMesure");
                        Date date = MesOutils.convertStringToDate(datemesure, "yyyy-MM-dd hh:mm:ss");
                        Log.d("dateMysql", "****** retour mysql = "+ date);
                        Profil profil = new Profil(date, poids, taille, age, sexe);
                        control.setProfil(profil);

                    } catch (JSONException e) {
                        Log.d("Erreur", "Conversion jSON impossible : "+ e.toString());
                    }
                }else {
                    if (message[0].equals("Erreur")){
                        Log.d("Erreur", "***************" + message[1]);
                    }else {
                        if (message[0].equals("tous")){
                            Log.d("tous", "***********" + message[1]);
                            try {
                                JSONArray jsonArray = new JSONArray(message[1]);
                                ArrayList<Profil> lesProfils = new ArrayList<Profil>();
                                for(int i = 0; i<jsonArray.length(); i++){
                                    JSONObject info = new JSONObject(jsonArray.get(i).toString());
                                    Integer poids = info.getInt("poids");
                                    Integer taille = info.getInt("taille");
                                    Integer age = info.getInt("age");
                                    Integer sexe = info.getInt("sexe");
                                    String datemesure = info.getString("dateMesure");
                                    Date date = MesOutils.convertStringToDate(datemesure, "yyyy-MM-dd hh:mm:ss");
                                    Profil profil = new Profil(date, poids, taille, age, sexe);
                                    lesProfils.add(profil);
                                }
                                control.setLesProfils(lesProfils);
                            }catch (JSONException e){
                                Log.d("Erreur", "Conversion jSON impossible : "+ e.toString());
                            }

                        }
                    }
                }
            }
        }
    }

    public void envoi(String operation, JSONArray lesDonnesJSON){
        AccesHTTP accesDonnes = new AccesHTTP();
        // lien de delegation
        accesDonnes.delegate = this;
        // ajout parametre
        accesDonnes.addParam("operation", operation);
        Log.d("lesdonnees", "*********" + lesDonnesJSON);
        Log.d("lesdonnees string", "*********" + lesDonnesJSON.toString());
        accesDonnes.addParam("lesdonnees", lesDonnesJSON.toString());
        // envoie des donnes

        accesDonnes.execute(SERVEURADDR);
    }
}
