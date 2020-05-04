package com.example.coach.modele;
import com.example.coach.outils.MesOutils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Profil implements Serializable, Comparable {
    //constantes
    private static final Integer minFemme = 15;
    private static final Integer maxFemme = 30;
    private static final Integer minHomme = 10;
    private static final Integer maxHomme = 25;

    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;
    private float img;
    private String message;

    public Date getDateMesure() {
        return dateMesure;
    }

    private Date dateMesure;

    public Profil(Date dateMesure, Integer poids, Integer taille, Integer age, Integer sexe) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.dateMesure = dateMesure;
        calculIMG();
        resultatIMG();
    }

    public Integer getPoids() {
        return poids;
    }

    public Integer getTaille() {
        return taille;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSexe() {
        return sexe;
    }

    public float getImg() {
        return img;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Calcul l'img d'une personne en fonction de ses informations
     */
    private void calculIMG(){
        float tailleM = ((float)taille)/100;
        this.img = (float) ((1.2*poids/(tailleM * tailleM)) + (0.23*age) - (10.83*sexe) - 5.4);
    }

    /**
     * retourne le message correspondant a l'img
     */
    private void resultatIMG(){
        Integer min;
        Integer max;
        if (sexe == 1){ // c'est un homme
            min = minHomme;
            max = maxHomme;
        }else { // c'est une femme
            min = minFemme;
            max = maxFemme;
        }
        message = "normal";
        if (img<min){ // si img est aux max
            message = "trop faible";
        }else{
            if (img>max){
                message = "trop élevé";
            }
        }
    }

    /**
     * Conversion du profil au format JSON Array
     * @return
     */
    public JSONArray convertToJSONArray(){
        List laListe = new ArrayList();
        laListe.add(MesOutils.convertDateToString(dateMesure));
        laListe.add(poids);
        laListe.add(taille);
        laListe.add(age);
        laListe.add(sexe);
        return new JSONArray(laListe);
    }

    @Override
    public int compareTo(Object o) {
        return dateMesure.compareTo(((Profil)o).getDateMesure());
    }
}
