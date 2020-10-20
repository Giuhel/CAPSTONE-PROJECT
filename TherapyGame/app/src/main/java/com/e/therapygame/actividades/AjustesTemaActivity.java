package com.e.therapygame.actividades;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.e.therapygame.R;
import com.e.therapygame.clases.PreferenciasJuego;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AjustesTemaActivity extends AppCompatActivity {

    Spinner comboTema;
    RelativeLayout layoutFondo;
    ImageButton btnAyuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes_tema);

        comboTema=findViewById(R.id.comboModoTema);
        layoutFondo=findViewById(R.id.idLayoutFondo);
        btnAyuda=findViewById(R.id.btnAyuda);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.tema,android.R.layout.simple_spinner_item);
        comboTema.setAdapter(adapter);

        asignarValoresPreferencias();

        comboTema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("CIRCULAR")){
                    PreferenciasJuego.formaBanner=R.drawable.banner_redondo;
                }else {
                    PreferenciasJuego.formaBanner=R.drawable.banner_cuadrado;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                createSimpleDialog().show();
            }
        });


    }

    public void asignarValoresPreferencias(){
        layoutFondo.setBackgroundColor(getResources().getColor(PreferenciasJuego.colorTema));
        if(PreferenciasJuego.formaBanner==R.drawable.banner_redondo){
            comboTema.setSelection(0);
        }else{
            comboTema.setSelection(1);
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardAmarillo: PreferenciasJuego.colorTema=R.color.colorAmarrillo; break;
            case R.id.cardCeleste: PreferenciasJuego.colorTema=R.color.colorCeleste; break;
            case R.id.cardRojo: PreferenciasJuego.colorTema=R.color.colorRojo; break;
            case R.id.cardVerde: PreferenciasJuego.colorTema=R.color.colorVerde; break;
            case R.id.cardNaranja: PreferenciasJuego.colorTema=R.color.colorNaranja; break;
            case R.id.cardLila: PreferenciasJuego.colorTema=R.color.colorLila; break;
            case R.id.cardMarron: PreferenciasJuego.colorTema=R.color.colorMarron; break;
            case R.id.cardVerdeLima: PreferenciasJuego.colorTema=R.color.colorVerdeLima; break;
            case R.id.cardGranada: PreferenciasJuego.colorTema=R.color.colorGranada; break;
            case R.id.cardTurquesa: PreferenciasJuego.colorTema=R.color.colorTurquesa; break;
            case R.id.cardVinotinto: PreferenciasJuego.colorTema=R.color.colorVinoTinto; break;
            case R.id.cardFucsia: PreferenciasJuego.colorTema=R.color.colorFucsia; break;
            case R.id.cardAzulRey: PreferenciasJuego.colorTema=R.color.colorAzulOscuro; break;
            case R.id.cardTeja: PreferenciasJuego.colorTema=R.color.colorTeja; break;
            case R.id.cardAbeto: PreferenciasJuego.colorTema=R.color.colorAbeto; break;
            case R.id.cardAzul: PreferenciasJuego.colorTema=R.color.colorAzul; break;
            case R.id.cardGris: PreferenciasJuego.colorTema=R.color.colorGris; break;
            case R.id.cardNegro: PreferenciasJuego.colorTema=R.color.colorNegro; break;
            case R.id.btnIcoAtras: finish(); break;
        }
        layoutFondo.setBackgroundColor(getResources().getColor(PreferenciasJuego.colorTema));
    }

    @Override
    protected void onDestroy() {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        PreferenciasJuego.asignarPreferenciasTema(preferences,getApplicationContext());
        super.onDestroy();
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AjustesTemaActivity.this);

        builder.setTitle("Ayuda")
                .setMessage("Selecciona el Diseño de la App y el Color del tema que deseas aplicar, solo bastará con dar Atrás y los cambios estarán configurados.")
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        asignarValoresPreferencias();
    }

    @Override
    public void onBackPressed() {

    }
}