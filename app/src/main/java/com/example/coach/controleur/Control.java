package com.example.coach.controleur;

import android.content.Context;
import android.util.Log;

import com.example.coach.modele.AccesDistant;
import com.example.coach.modele.Profil;
import com.example.coach.outils.Serializer;
import com.example.coach.vue.CalculActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public final class Control {

    private static Control instance = null;
    private static Profil profil;
    private static String nomFic = "saveprofil";
   // private static AccesLocal accesLocal;
    private static AccesDistant accesDistant;
    private static Context context;
    private ArrayList<Profil> lesProfils = new ArrayList<Profil>();

    private Control() {
        super();
    }

    /**
     *
     * @return une instance de la classe control (singleton)
     */
    public static final Control getInstance(Context context){
        if (context != null){
            Control.context = context;
        }
        if (instance == null){
            Control.instance = new Control();
            //accesLocal = new AccesLocal(context);
            accesDistant = new AccesDistant();
            accesDistant.envoi("tous", new JSONArray());
            // profil = accesLocal.recupDernier();
           // recupSerialize(context);

        }
        return Control.instance;
    }

    public ArrayList<Profil> getLesProfils() {
        return lesProfils;
    }

    public void setLesProfils(ArrayList<Profil> lesProfils) {
        this.lesProfils = lesProfils;
    }

    public void setProfil(Profil profil){
        Control.profil = profil;
        // ((CalculActivity)context).recupProfil();
    }

    /**
     * Creation du profil
     * @param poids
     * @param age
     * @param taille
     * @param sexe
     */
    public void creerProfil(Integer poids, Integer age, Integer taille, Integer sexe, Context context){
        Profil unProfil = new Profil(new Date(), poids, taille, age, sexe);
        lesProfils.add(unProfil);
       // accesLocal.ajout(profil);
       // Serializer.serialize(nomFic, profil, context);
        Log.d("date", "*************" + (new Date()));
        accesDistant.envoi("enreg", unProfil.convertToJSONArray());
    }

    /**
     *
     * @return retourne l'img calculer
     */
    public float getImg(){
        //return profil.getImg();
        return lesProfils.get(lesProfils.size() - 1).getImg();
    }

    /**
     *
     * @return retourne le message relatif a l'img calculer
     */
    public String getMessage(){
        // return profil.getMessage();
        return lesProfils.get(lesProfils.size() - 1).getMessage();
    }

    private static void recupSerialize(Context context){
        profil = (Profil) Serializer.deSerialize(nomFic, context);
    }

    public Integer getPoids(){
        if (profil == null){
            return null;
        }else {
            return profil.getPoids();
        }
    }

    public Integer getTaille(){
        if (profil == null){
            return null;
        }else {
            return profil.getTaille();
        }
    }

    public Integer getAge(){
        if (profil == null){
            return null;
        }else {
            return profil.getAge();
        }
    }

    public Integer getSexe(){
        if (profil == null){
            return null;
        }else {
            return profil.getSexe();
        }
    }

    /**
     * permet de supprimer un profil dans la base distante et dans la collection
     * @param profil
     */
    public void delProfil(Profil profil){
        accesDistant.envoi("del", profil.convertToJSONArray());
        lesProfils.remove(profil);
    }
}
