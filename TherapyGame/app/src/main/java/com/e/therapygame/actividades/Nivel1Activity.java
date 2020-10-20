package com.e.therapygame.actividades;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.therapygame.R;
import com.e.therapygame.clases.PreferenciasJuego;
import com.e.therapygame.clases.utilidades;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class Nivel1Activity extends AppCompatActivity {

    private String arreglonombres[]=new String[4];
    private int arregloColores[]=new int[4];

    LinearLayout barraSuperior;
    TextView txtCantidad,txtCorrectas,txtRestante,txtPalabra,txtCambia,txtPuntaje;
    ProgressBar pTiempo;

    Button btnBien, btnMal;
    int colorR;   //define el rango de 1 a 4 para asignacion de color
    int palabraR;  //define el rango de 1 a 4 para asignacionde palabra del color
    String modo;   //modo de juego
    long tiempo;   //tiempo de juego
    long tiempoPalabra;  //tiempo de la palabra
    int cantidad, intentos;
    int [] milisegundos = {0,60000};

    private Handler miHandler=new Handler();
    boolean bandera,bandera1;
    int finalizaJuego=0;
    FloatingActionButton btnPause;
    int pausa=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel1);

        SharedPreferences preferences= android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        PreferenciasJuego.obtenerPreferencias(preferences,getApplicationContext());

        txtCantidad = findViewById(R.id.txtCantidad);
        txtCorrectas = findViewById(R.id.txtCorrectas);
        txtRestante = findViewById(R.id.txtRestantes);
        txtPalabra = findViewById(R.id.txtPalabra);
        txtCambia = findViewById(R.id.txtCambia);
        txtPuntaje=findViewById(R.id.txtPuntaje);
        pTiempo= findViewById(R.id.pTiempo);
        btnBien=findViewById(R.id.btnBien);
        btnMal=findViewById(R.id.btnMal);
        btnPause=findViewById(R.id.btnPause);
        barraSuperior=findViewById(R.id.barraSuperiorId);

        inicializarArreglos();
        definirAleatorios();
        inicializarValore();
        asignarvalores();
        iniciarJuego();

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausar();
            }
        });
    }

    private void pausar() {
        pausa++;
        if(pausa<=3){
            bandera1=false;
            final Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.item_pause);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            Button btnContinuar=dialog.findViewById(R.id.btnContinuar);

            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    milisegundos[0]=0;
                    bandera1=true;
                    definirAleatorios();
                    dialog.cancel();
                }
            });
            dialog.show();
        }else{
            Toast.makeText(getApplicationContext(),"No puedes utilizar mas pausas, el limite son tres ",Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializarArreglos() {

        arreglonombres[0]="AMARILLO";   arreglonombres[1]="AZUL";
        arreglonombres[2]="ROJO";   arreglonombres[3]="VERDE";

        arregloColores[0]=getResources().getColor(R.color.colorAmarrillo);
        arregloColores[1]=getResources().getColor(R.color.colorAzul);
        arregloColores[2]=getResources().getColor(R.color.colorRojo);
        arregloColores[3]=getResources().getColor(R.color.colorVerde);

    }

    private void definirAleatorios() {
        Random r=new Random();
        palabraR=r.nextInt(4);
        colorR=r.nextInt(4);

        txtPalabra.setText(arreglonombres[palabraR]);
        txtPalabra.setTextColor(arregloColores[colorR]);
    }

    private void asignarvalores() {
        txtCantidad.setText(Integer.toString(cantidad));
        txtCorrectas.setText(Integer.toString(utilidades.correctas));
        if (modo.equals("INTENTOS")){
            txtCambia.setText("Intentos Faltantes");
            txtRestante.setText(Integer.toString(intentos));
            txtPuntaje.setText(utilidades.puntaje+"");
        }else {
            txtCambia.setText("Tiempo Faltante");
            txtRestante.setText(Integer.toString((int) tiempo));
            txtPuntaje.setText(utilidades.puntaje+"");
        }
    }

    private void inicializarValore(){
        modo= PreferenciasJuego.modoJuego;
        tiempoPalabra=PreferenciasJuego.duracionPalabra;

        utilidades.correctas=0;
        utilidades.incorrectas=0;
        utilidades.puntaje=0;
        bandera=true;
        bandera1=true;

        if(modo.equals("INTENTOS")){
            intentos=PreferenciasJuego.numIntentos;
            pTiempo.setMax(intentos);
            pTiempo.setProgress(intentos);
        }else{
            intentos=0;
            tiempo=PreferenciasJuego.tiempojuego;
            milisegundos[1]= (int) tiempo;
            pTiempo.setMax((int) tiempo);
            pTiempo.setProgress((int) tiempo);
        }
    }


    private void iniciarJuego(){
        final  Thread mihilo=new Thread(){
            @Override
            public void run() {
                try {
                    while (bandera){
                        Thread.sleep(1);
                        ejecutarHiloJuego();
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        };
        mihilo.start();
    }

    private void ejecutarHiloJuego() {
        miHandler.post(new Runnable() {
            @Override
            public void run() {
                if(bandera1){
                    if(milisegundos[0]==tiempoPalabra){
                        milisegundos[0]=0;
                        cantidad++;
                        intentos--;
                        utilidades.incorrectas++;
                        definirAleatorios();
                        asignarvalores();
                        terminarJuego();
                    }
                    milisegundos[0]++;
                    milisegundos[1]--;
                    pTiempo.setProgress(intentos);
                    if(modo.equals("INTENTOS")){
                        if(milisegundos[1]==0){
                            milisegundos[1]=60000;
                        }
                    }else {
                        txtRestante.setText(Integer.toString(milisegundos[1]));
                        pTiempo.setProgress(milisegundos[1]);
                    }
                    terminarJuego();
                }
            }
        });
    }

    private void terminarJuego() {
        if(finalizaJuego==0 && (modo.equals("INTENTOS") && intentos==0) || (modo.equals("TIEMPO") && milisegundos[1]==0)){
            finalizaJuego=1;
            bandera = false;
            bandera1 = false;
            Intent miIntent=new Intent(Nivel1Activity.this,ResultadoJuegoActivity.class);
            startActivity(miIntent);
            finish();
        }
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBien:
                validarColores(palabraR,colorR,1);
                break;
            case  R.id.btnMal:
                validarColores(palabraR,colorR,2);
                break;

        }
    }

    private void validarColores(int aleatorio1, int aleatorio2, int evento) {
        if(evento==1){
            if(aleatorio1==aleatorio2){
                utilidades.puntaje+=10;
                utilidades.correctas++;
            }else{
                intentos--;
                utilidades.incorrectas++;
            }
        }else {
            if(aleatorio1!=aleatorio2){
                utilidades.puntaje+=10;
                utilidades.correctas++;
            }else{
                intentos--;
                utilidades.incorrectas++;
            }
        }
        cantidad++;
        terminarJuego();
        definirAleatorios();
        asignarvalores();
        milisegundos[0]=0;
    }


    @Override
    protected void onStop() {
        super.onStop();
        bandera = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bandera = false;
        bandera1 = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        bandera = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        asignarValoresPreferencias();
    }

    public void asignarValoresPreferencias(){
        barraSuperior.setBackgroundColor(getResources().getColor(PreferenciasJuego.colorTema));
    }

    @Override
    public void onBackPressed() {

    }
}