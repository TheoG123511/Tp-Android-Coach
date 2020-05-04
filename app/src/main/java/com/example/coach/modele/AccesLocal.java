package com.example.coach.modele;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.coach.outils.MesOutils;
import com.example.coach.outils.MySQLiteOpenHelper;

import java.util.Date;

public class AccesLocal {
    private String nomBase = "bdCoach.sqlite";
    private Integer versionBase = 1;
    private MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;

    public AccesLocal(Context context){
        accesBD = new MySQLiteOpenHelper(context, nomBase, versionBase);

    }

    public void ajout(Profil profil){
        bd = accesBD.getWritableDatabase();
        String req = "INSERT INTO profil(datemesure, poids, taille, age, sexe) VALUES ";
        req += "(\""+ profil.getDateMesure() +"\","+ profil.getPoids() +","+ profil.getTaille() +","+ profil.getAge() +","+ profil.getSexe() +")";
        Log.d("Requete Sql", "ajout: " + req);
        bd.execSQL(req);

    }

    public Profil recupDernier(){
        bd = accesBD.getReadableDatabase();
        Profil profil = null;
        String req = "SELECT * FROM profil";
        Cursor cursor = bd.rawQuery(req, null);
        cursor.moveToLast();

        if (!cursor.isAfterLast()){
            Date date = MesOutils.convertStringToDate(cursor.getString(0));
            Integer poids = cursor.getInt(1);
            Integer taille = cursor.getInt(2);
            Integer age = cursor.getInt(3);
            Integer sexe = cursor.getInt(4);
            profil = new Profil(date, poids, taille, age, sexe);
        }

        cursor.close();
        return profil;
    }
}
