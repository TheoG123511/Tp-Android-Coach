package com.example.coach.vue;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coach.R;
import com.example.coach.controleur.Control;
import com.example.coach.modele.Profil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HistoActivity extends AppCompatActivity {

    private Control control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histo);
        this.control = Control.getInstance(this);
        ecouteRetourMenu();
        creerList();
    }

    private void ecouteRetourMenu(){
        ((ImageButton)findViewById(R.id.btnRetourHisto)).setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(HistoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * creer la liste adapteur
     */
    private void creerList(){
        ArrayList<Profil> lesProfils = control.getLesProfils();
        Collections.sort(lesProfils, Collections.<Profil>reverseOrder());
        if (lesProfils != null){
            ListView lstHisto = (ListView)findViewById(R.id.lstHisto);
            HistoListAdapter adapter = new HistoListAdapter(this, lesProfils);
            lstHisto.setAdapter(adapter);
        }
    }

    /**
     * demande d'afficher le profil dans calculActivity
     * @param profil
     */
    public void afficheProfil(Profil profil){
        control.setProfil(profil);
        Intent intent = new Intent(HistoActivity.this, CalculActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
