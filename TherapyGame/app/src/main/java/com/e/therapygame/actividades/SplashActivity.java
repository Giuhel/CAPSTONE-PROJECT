package com.e.therapygame.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.therapygame.MainActivity;
import com.e.therapygame.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    private static int Splash=3500;

    Animation topAmin,bottonAmin;
    ImageView logo;
    TextView texto1,texto2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //animacion
        topAmin = AnimationUtils.loadAnimation(this,R.anim.top_animatiom);
        bottonAmin = AnimationUtils.loadAnimation(this,R.anim.botton_animation);

        logo=findViewById(R.id.logo);
        texto1=findViewById(R.id.texto1);
        texto2=findViewById(R.id.texto2);

        logo.setAnimation(topAmin);
        texto1.setAnimation(bottonAmin);
        texto2.setAnimation(bottonAmin);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=preferences.edit();
                int bandera=Integer.parseInt(preferences.getString("bandera","0"));

                if(bandera==1){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    editor.putString("bandera","1");
                    editor.commit();

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);

                    Intent intent2 = new Intent(SplashActivity.this, ContendorInstruccionesActivity.class);
                    startActivity(intent2);
                }
                finish();
            }
        }, Splash);
    }

    @Override
    public void onBackPressed() {

    }
}