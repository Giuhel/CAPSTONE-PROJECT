package com.e.therapygame.actividades;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.e.therapygame.R;
import com.e.therapygame.clases.PreferenciasJuego;
import com.e.therapygame.fragments.AjustesFragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AjustesActivity extends AppCompatActivity {

    RelativeLayout layoutFondo;
    ImageButton btnAyuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        layoutFondo=findViewById(R.id.idLayoutFondo);
        btnAyuda=findViewById(R.id.btnAyuda);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorAjustes,new AjustesFragment()).commit();

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
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIcoAtras:
                finish();break;
        }
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AjustesActivity.this);

        builder.setTitle("Ayuda")
                .setMessage("Desde Aquí podrás configurar las opciones del juego así como el tema de la aplicación.")
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