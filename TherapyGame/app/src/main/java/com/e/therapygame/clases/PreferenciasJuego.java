package com.e.therapygame.clases;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.e.therapygame.R;

public class PreferenciasJuego {

    public static final String MODO_JUEGO = "modojuego";
    public static final String TIEMPO_JUEGO = "tiempoJuego";
    public static final String DURACION_PALABRA= "duracionPalabra";
    public static final String NUM_INTENTOS = "NumeroDeIntentos";

    public static String modoJuego;
    public static long tiempojuego, duracionPalabra;
    public static int numIntentos;


    public static final String JUGADOR_ID = "jugadorId";
    public static final String NICKNAME = "nickName";
    public static final String AVATAR_ID = "avatarId";
    public static int jugadorId;
    public static String nickName;
    public static int avatarId;

    public static final String FORMA_BANNER = "formaBanner";
    public static int formaBanner;

    public static final String COLOR_TEMA = "colorTemas";
    public static int colorTema;

    public static void asignarPreferenciasTema(SharedPreferences preferences, Context context){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(COLOR_TEMA,""+colorTema);
        editor.putString(FORMA_BANNER,""+formaBanner);
        editor.commit();

        obtenerPreferencias(preferences,context);

    }

    public static void asignarPreferenciasJugador(SharedPreferences preferences, Context context){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(JUGADOR_ID,""+jugadorId);
        editor.putString(NICKNAME,""+nickName);
        editor.putString(AVATAR_ID,""+avatarId);
        editor.commit();

        obtenerPreferencias(preferences,context);

    }

    public static void obtenerPreferencias(SharedPreferences preferences, Context context){
        String msjError="ok";

        modoJuego=preferences.getString(MODO_JUEGO,"TIEMPO");
        String tiempo=preferences.getString(TIEMPO_JUEGO,"1 Minuto");


        if(tiempo.equals("1 Minuto")){
            tiempojuego=10000;
        }else if(tiempo.equals("2 Minutos")){
            tiempojuego=20000;
        }else if(tiempo.equals("3 Minutos")){
            tiempojuego=30000;
        }else if(tiempo.equals("4 Minutos")){
            tiempojuego=40000;
        }else if(tiempo.equals("5 Minutos")){
            tiempojuego=50000;
        }

        String duracion=preferences.getString(DURACION_PALABRA,"1/2 Segundos");

        if(duracion.equals("1/2 Segundo")){
            duracionPalabra=500;
        }else if(duracion.equals("1 Segundo")){
            duracionPalabra=1000;
        }else if(duracion.equals("2 Segundos")){
        duracionPalabra=2000;
        }

        try {
        numIntentos =Integer.parseInt(preferences.getString(NUM_INTENTOS,"5"));
        }catch (NumberFormatException e){
            msjError="NUMERO DE INTENTOS";
        }

        try {
            formaBanner =Integer.parseInt(preferences.getString(FORMA_BANNER,""+ R.drawable.banner_cuadrado));
        }catch (NumberFormatException e){
            msjError="NUMERO DE INTENTOS";
        }

        try {
            colorTema =Integer.parseInt(preferences.getString(COLOR_TEMA,""+ R.color.colorPrimaryDark));
        }catch (NumberFormatException e){
            msjError="NUMERO DE INTENTOS";
        }

        if(!msjError.equals("ok")){
            Toast.makeText(context,"Verifique la configuracion en "+msjError,Toast.LENGTH_SHORT).show();
        }

        /*String msj="Modo Juego: "+modoJuego+"\n";
        msj+="Tiempo Juego: "+tiempojuego+"\n";
        msj+="Duracion Palabra: "+duracionPalabra+"\n";
        msj+="Num de intentos: "+numIntentos+"\n";

        Toast.makeText(context,msj,Toast.LENGTH_SHORT).show();*/


        jugadorId=Integer.parseInt(preferences.getString(JUGADOR_ID,"0"));
        nickName=preferences.getString(NICKNAME,"{ NA }");
        avatarId=Integer.parseInt(preferences.getString(AVATAR_ID,"1"));

    }

}

