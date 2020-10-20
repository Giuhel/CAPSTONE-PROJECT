package com.e.therapygame.clases;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.e.therapygame.R;
import com.e.therapygame.clases.vo.AvatarVo;
import com.e.therapygame.clases.vo.JugadorVO;

import java.util.ArrayList;

public class utilidades {

    public static ArrayList<AvatarVo> listaAvatars=null;
    public static ArrayList<JugadorVO> listaJugadores=null;

    public static AvatarVo avatarSeleccion=null;
    public static int avatarIdSeleccion=0;

    public static int correctas;
    public static int incorrectas;
    public static int puntaje;
    public static int nivelJuego;

    public static final  String NOMBRE_BD="therapy_bd";

    //creamos las constantes para los campos de la tabla jugador
    public static final  String TABLA_JUGADOR="jugador";
    public static final  String CAMPO_ID="id";
    public static final  String CAMPO_NOMBRE="nombre";
    public static final  String CAMPO_GENERO="genero";
    public static final  String CAMPO_AVATAR="avatar";

    //creamos las constantes para los campos de la tabla puntaje
    public static final  String TABLA_PUNTAJE="puntaje_jugador";
    public static final  String CAMPO_ID_JUGADOR="id";
    public static final  String CAMPO_PUNTOS="puntos";
    public static final  String CAMPO_PALABRAS_CORRECTEAS="correctas";
    public static final  String CAMPO_PALABRAS_INCORRECTAS="incorrectas";
    public static final  String CAMPO_NIVEL="nivel";
    public static final  String CAMPO_MODO_JUEGO="modo";

    //tabla genero
    public static final  String TABLA_GENERO="genero";
    public static final  String CAMPO_ID_GENERO="id";
    public static final  String CAMPO_TIPO_GENERO="tipo_Genero";
    //--------------

    public static final String CREAR_TABLA_JUGADOR="CREATE TABLE "+ TABLA_JUGADOR+ " ("+CAMPO_ID+" INTEGER PRIMARY KEY,"+CAMPO_NOMBRE+" TEXT,"+CAMPO_GENERO+" TEXT,"+CAMPO_AVATAR+" INTEGER)";
    public static final String CREAR_TABLA_PUNTAJE="CREATE TABLE "+ TABLA_PUNTAJE+ " ("+CAMPO_ID_JUGADOR+" INTEGER,"+CAMPO_PUNTOS+" INTEGER,"+CAMPO_PALABRAS_CORRECTEAS+" INTEGER,"+CAMPO_PALABRAS_INCORRECTAS+" INTEGER,"+CAMPO_NIVEL+" TEXT, "+CAMPO_MODO_JUEGO+" TEXT)";
    public static final String CREAR_TABLA_GENERO="CREATE TABLE "+ TABLA_GENERO+ " ("+CAMPO_ID_GENERO+" TEXT,"+CAMPO_TIPO_GENERO+" TEXT)";

    public static void obtenerListaAvatars(){

        listaAvatars=new ArrayList<AvatarVo>();

        listaAvatars.add(new AvatarVo(1, R.drawable.avatar1,"Avatar1"));
        listaAvatars.add(new AvatarVo(2, R.drawable.avatar2,"Avatar2"));
        listaAvatars.add(new AvatarVo(3, R.drawable.avatar3,"Avatar3"));
        listaAvatars.add(new AvatarVo(4, R.drawable.avatar4,"Avatar4"));
        listaAvatars.add(new AvatarVo(5, R.drawable.avatar5,"Avatar5"));
        listaAvatars.add(new AvatarVo(6, R.drawable.avatar6,"Avatar6"));
        listaAvatars.add(new AvatarVo(7, R.drawable.avatar7,"Avatar7"));
        listaAvatars.add(new AvatarVo(8, R.drawable.avatar8,"Avatar8"));
        listaAvatars.add(new AvatarVo(9, R.drawable.avatar9,"Avatar9"));
        listaAvatars.add(new AvatarVo(10, R.drawable.avatar10,"Avatar10"));
        listaAvatars.add(new AvatarVo(11, R.drawable.avatar11,"Avatar11"));
        listaAvatars.add(new AvatarVo(12, R.drawable.avatar12,"Avatar12"));

        avatarSeleccion=listaAvatars.get(0);
    }

    public static void consultarListajugadores(Activity actividad){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(actividad,NOMBRE_BD,null,1);
        SQLiteDatabase db=conn.getReadableDatabase();

        JugadorVO jugador=null;
        listaJugadores=new ArrayList<JugadorVO>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLA_JUGADOR,null);

        while (cursor.moveToNext()){
            jugador=new JugadorVO();
            jugador.setId(cursor.getInt(0));
            jugador.setNombre(cursor.getString(1));
            jugador.setGenero(cursor.getString(2));
            jugador.setAvatar(cursor.getInt(3));

            listaJugadores.add(jugador);
        }
        db.close();

    }
}
