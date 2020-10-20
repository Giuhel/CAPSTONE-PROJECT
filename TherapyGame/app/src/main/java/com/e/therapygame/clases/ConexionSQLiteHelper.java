package com.e.therapygame.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_JUGADOR);
        db.execSQL(utilidades.CREAR_TABLA_PUNTAJE);
        db.execSQL(utilidades.CREAR_TABLA_GENERO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+utilidades.TABLA_JUGADOR);
        db.execSQL("DROP TABLE IF EXISTS "+utilidades.TABLA_PUNTAJE);
        db.execSQL("DROP TABLE IF EXISTS "+utilidades.TABLA_GENERO);
        onCreate(db);
    }


}
