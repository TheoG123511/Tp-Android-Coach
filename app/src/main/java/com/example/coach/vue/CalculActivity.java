package com.example.coach.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coach.R;
import com.example.coach.controleur.Control;
import com.example.coach.outils.MesOutils;

public class CalculActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);
        init();
    }

    private Control controle;
    private EditText txtPoids;
    private EditText txtTaille;
    private EditText txtAge;
    private RadioButton rdHomme;
    private RadioButton rdFemme;
    private TextView lblMG;
    private ImageView imgSmiley;
    private Button btnCalc;

    private void init(){
        txtPoids = (EditText) findViewById(R.id.txtPoids);
        txtTaille = (EditText) findViewById(R.id.txtTaille);
        txtAge = (EditText) findViewById(R.id.txtAge);
        rdHomme = (RadioButton) findViewById(R.id.rdHomme);
        rdFemme = (RadioButton) findViewById(R.id.rdFemme);
        lblMG = (TextView) findViewById(R.id.lblIMG);
        imgSmiley = (ImageView) findViewById(R.id.imgSmiley);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        controle = Control.getInstance(this);
        ecouteCalcul();
        ecouteRetourMenu();
        recupProfil();
    }

    private void ecouteRetourMenu(){
        ((ImageButton)findViewById(R.id.btnAccueil)).setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(CalculActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void ecouteCalcul(){
        btnCalc.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                //Toast.makeText(CalculActivity.this, "teste", Toast.LENGTH_SHORT).show();
                Integer poids = 0;
                Integer taille = 0;
                Integer age = 0;
                Integer sexe = 0;
                try{
                    poids = Integer.parseInt(txtPoids.getText().toString());
                    taille = Integer.parseInt(txtTaille.getText().toString());
                    age = Integer.parseInt(txtAge.getText().toString());

                }catch (Exception e){}

                if (rdHomme.isChecked()){
                    sexe = 1;
                }
                if (poids == 0 || taille == 0 || age == 0){
                    Toast.makeText(CalculActivity.this, "Veuillez bien tous rentrer", Toast.LENGTH_SHORT).show();
                }else {
                    affichResult(poids, age, taille, sexe);
                }
            }
        });
    }

    private void affichResult(Integer poids, Integer age, Integer taille, Integer sexe){
        controle.creerProfil(poids, age, taille, sexe, this);
        float img = controle.getImg();
        String message = controle.getMessage();
        lblMG.setText(MesOutils.format2Deciaml(img) + " " + message);
        if (message == "normal"){
            lblMG.setTextColor(Color.GREEN);
            imgSmiley.setImageResource(R.drawable.normal);

        }else {
            lblMG.setTextColor(Color.RED);
            if (message== "trop élevé"){
                imgSmiley.setImageResource(R.drawable.graisse);
            }else {
                imgSmiley.setImageResource(R.drawable.maigre);
            }

        }

    }

    public void recupProfil(){
        if (controle.getPoids() != null){
            txtPoids.setText(controle.getPoids().toString());
            txtAge.setText(controle.getAge().toString());
            txtTaille.setText(controle.getTaille().toString());
            rdFemme.setChecked(true);
            if (controle.getSexe() == 1){
                rdHomme.setChecked(true);
            }
            // remettre a vide les information
            controle.setProfil(null);
            // ((Button)findViewById(R.id.btnCalc)).performClick();
        }
    }

}
