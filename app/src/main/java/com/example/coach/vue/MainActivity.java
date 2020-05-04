package com.example.coach.vue;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coach.R;
import com.example.coach.controleur.Control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private Control control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        control = Control.getInstance(this);
        setContentView(R.layout.activity_main);
        ecouteMenu((ImageButton)findViewById(R.id.btnMenuIMG), CalculActivity.class);
        ecouteMenu((ImageButton)findViewById(R.id.btnMenuHistorique), HistoActivity.class);

    }

    private void ecouteMenu(ImageButton imageButton, final Class classe){
         imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
