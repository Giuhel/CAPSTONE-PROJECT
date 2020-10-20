package com.e.therapygame.actividades;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Nivel2Activity extends AppCompatActivity {

    List<String> listaPalabras=new ArrayList<>();
    List<Integer> listaColores=new ArrayList<>();
    List<Integer> listaColoresTemp=new ArrayList<>();

    TextView txtCantidad,txtCorecctas,txtRestantes,txtPalabra,txtCambia,txtPuntaje;
    ProgressBar pTiempo;
    FloatingActionButton btnPause;
    Button btnColor1,btnColor2,btnColor3,btnColor4;
    boolean bandera=true;
    boolean bandera1=true;
    int finalizaJuego=0;
    int valorBoton;
    int colorR;
    int palabraR;
    int pausa;
    String modo;
    long tiempo;
    long tiempoPalabra;
    int cantidad,intentos;
    int[] milisegundos={0, (int) PreferenciasJuego.tiempojuego};

    LinearLayout barraSuperior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel2);

        SharedPreferences preferences=android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        PreferenciasJuego.obtenerPreferencias(preferences,getApplicationContext());

        txtCantidad=findViewById(R.id.txtCantidad);
        txtCorecctas=findViewById(R.id.txtCorrectas);
        txtRestantes=findViewById(R.id.txtRestantes);
        txtPalabra=findViewById(R.id.txtPalabra);
        txtCambia=findViewById(R.id.txtCambia);
        txtPuntaje=findViewById(R.id.txtPuntaje);
        pTiempo=findViewById(R.id.pTiempo);
        btnColor1=findViewById(R.id.btnColor1);
        btnColor2=findViewById(R.id.btnColor2);
        btnColor3=findViewById(R.id.btnColor3);
        btnColor4=findViewById(R.id.btnColor4);
        btnPause=findViewById(R.id.btnPause);
        barraSuperior=findViewById(R.id.barraSuperiorId);
        
        inicializarListas();
        definirBotonesAleatorios();
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

    private void iniciarJuego() {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (bandera){
                    try {
                        Thread.sleep(1);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(bandera1){
                                if(milisegundos[0]==tiempoPalabra){
                                    milisegundos[0]=0;
                                    cantidad++;
                                    intentos--;
                                    utilidades.incorrectas++;
                                    definirBotonesAleatorios();
                                    asignarvalores();
                                    terminarjuego();
                                }
                                milisegundos[0]++;
                                milisegundos[1]--;
                                pTiempo.setProgress(intentos);
                                if(modo.equals("INTENTOS")){
                                    if(milisegundos[1]==0){
                                        milisegundos[1]=60000;
                                    }
                                }else {
                                    txtRestantes.setText(Integer.toString(milisegundos[1]));
                                    pTiempo.setProgress(milisegundos[1]);
                                }
                                terminarjuego();
                            }
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private void terminarjuego() {
        if(finalizaJuego==0 && (modo.equals("INTENTOS") && intentos==0) || (modo.equals("TIEMPO") && milisegundos[1]==0)){
            btnColor1.setEnabled(false);
            btnColor2.setEnabled(false);
            btnColor3.setEnabled(false);
            btnColor4.setEnabled(false);
            finalizaJuego=1;
            bandera = false;
            bandera1 = false;
            Intent miIntent=new Intent(Nivel2Activity.this,ResultadoJuegoActivity.class);
            startActivity(miIntent);
            finish();
        }
    }

    private void asignarvalores() {
        txtCantidad.setText(Integer.toString(cantidad));
        txtCorecctas.setText(Integer.toString(utilidades.correctas));
        if (modo.equals("INTENTOS")){
            txtCambia.setText("Intentos Faltantes");
            txtRestantes.setText(Integer.toString(intentos));
            txtPuntaje.setText(utilidades.puntaje+"");
        }else {
            txtCambia.setText("Tiempo Faltante");
            txtRestantes.setText(Integer.toString((int) tiempo));
            txtPuntaje.setText(utilidades.puntaje+"");
        }
    }

    private void inicializarValore() {
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

    private void definirBotonesAleatorios() {
        listaColoresTemp=listaColores;
        Collections.shuffle(listaColoresTemp);
        palabraR= (int) (Math.random()*4);
        colorR= (int) (Math.random()*4);

        txtPalabra.setText(listaPalabras.get(palabraR));
        txtPalabra.setTextColor(listaColores.get(colorR));

        btnColor1.setBackgroundColor(listaColoresTemp.get(0));
        btnColor2.setBackgroundColor(listaColoresTemp.get(1));
        btnColor3.setBackgroundColor(listaColoresTemp.get(2));
        btnColor4.setBackgroundColor(listaColoresTemp.get(3));

    }

    private void inicializarListas() {
        listaPalabras=new ArrayList<>();
        listaColores=new ArrayList<>();

        listaPalabras.add("AMARILLO");
        listaColores.add(getResources().getColor(R.color.colorAmarrillo));
        listaPalabras.add("AZUL");
        listaColores.add(getResources().getColor(R.color.colorAzul));
        listaPalabras.add("ROJO");
        listaColores.add(getResources().getColor(R.color.colorRojo));
        listaPalabras.add("VERDE");
        listaColores.add(getResources().getColor(R.color.colorVerde));


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
                    definirBotonesAleatorios();
                    dialog.cancel();
                }
            });
            dialog.show();
        }else{
            Toast.makeText(getApplicationContext(),"No puedes utilizar mas pausas, el limite son tres ",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnColor1:
                valorBoton=1;
                validar();
                break;

            case R.id.btnColor2:
                valorBoton=2;
                validar();
                break;

            case R.id.btnColor3:
                valorBoton=3;
                validar();
                break;

            case R.id.btnColor4:
                valorBoton=4;
                validar();
                break;
        }
    }

    private void validar() {
        switch (valorBoton){
            case 1:
                if(colorR==0){
                    utilidades.correctas++;
                    utilidades.puntaje+=10;
                }else{
                    utilidades.incorrectas++;
                    intentos--;
                }
                break;
            case 2:
                if(colorR==1){
                    utilidades.correctas++;
                    utilidades.puntaje+=10;
                }else{
                    utilidades.incorrectas++;
                    intentos--;
                }
                break;
            case 3:
                if(colorR==2){
                    utilidades.correctas++;
                    utilidades.puntaje+=10;
                }else{
                    utilidades.incorrectas++;
                    intentos--;
                }
                break;
            case 4:
                if(colorR==4){
                    utilidades.correctas++;
                    utilidades.puntaje+=10;
                }else{
                    utilidades.incorrectas++;
                    intentos--;
                }
                break;
        }
        cantidad++;
        terminarjuego();
        definirBotonesAleatorios();
        asignarvalores();
        milisegundos[0]=0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        bandera1 = false;
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
        bandera1 = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        asignarValoresPreferencias();
    }

    @Override
    public void onBackPressed() {

    }

    public void asignarValoresPreferencias(){
        barraSuperior.setBackgroundColor(getResources().getColor(PreferenciasJuego.colorTema));
    }
}