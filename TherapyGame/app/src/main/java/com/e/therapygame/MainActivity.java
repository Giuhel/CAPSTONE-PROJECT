package com.e.therapygame;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.e.therapygame.actividades.AcercaDeActivity;
import com.e.therapygame.actividades.AjustesActivity;
import com.e.therapygame.actividades.ContendorInstruccionesActivity;
import com.e.therapygame.clases.ConexionSQLiteHelper;
import com.e.therapygame.clases.PreferenciasJuego;
import com.e.therapygame.clases.utilidades;
import com.e.therapygame.dialogos.DialogoTipoJuegoFragment;
import com.e.therapygame.fragments.GestionJugadorFragment;
import com.e.therapygame.fragments.InicioFragment;
import com.e.therapygame.fragments.RankigFragment;
import com.e.therapygame.fragments.RegistroJugadorFragment;
import com.e.therapygame.interfaces.IComunicaFragments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity implements IComunicaFragments {

    Fragment fragmentInicio, registroJugadorFragment,gestionJugadorFragment,rankingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this,R.xml.preferencias,false);
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        PreferenciasJuego.obtenerPreferencias(preferences,this);

        utilidades.obtenerListaAvatars();
        utilidades.consultarListajugadores(this);
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,utilidades.NOMBRE_BD,null,1);

        //genero---------------------------------------------------------------------------------------------
        SharedPreferences.Editor editor=preferences.edit();
        int registro=Integer.parseInt(preferences.getString("registro","0"));
        if(registro==1){

        }else{
            editor.putString("registro","1");
            editor.commit();
            SQLiteDatabase db=conn.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(utilidades.CAMPO_ID_GENERO,"1");
            values.put(utilidades.CAMPO_TIPO_GENERO,"Masculino");
            db.insert(utilidades.TABLA_GENERO,utilidades.CAMPO_ID_GENERO,values);
            db.close();

            SQLiteDatabase db2=conn.getWritableDatabase();
            ContentValues values2=new ContentValues();
            values2.put(utilidades.CAMPO_ID_GENERO,"2");
            values2.put(utilidades.CAMPO_TIPO_GENERO,"Femenino");
            db2.insert(utilidades.TABLA_GENERO,utilidades.CAMPO_ID_GENERO,values2);
            db2.close();
        }
        //---------------------------------------------------------

        fragmentInicio =new InicioFragment();
        registroJugadorFragment= new RegistroJugadorFragment();
        gestionJugadorFragment=new GestionJugadorFragment();
        rankingFragment=new RankigFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragmentInicio).commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public AlertDialog dialogoGestionUsuario(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Gestionar Jugador").setMessage("Señale si quiere registrar un nuevo jugador o si desea seleccionar uno " +
                "existente.\n\n" + "Tambien podra modificar un jugador desde la opcion SELECCIONAR").setNegativeButton("REGISTRAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,registroJugadorFragment).commit();
                    }
                })
                .setPositiveButton("SELECCIONAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,gestionJugadorFragment).commit();
            }
        });
        return builder.create();
    }

    @Override
    public void IniciarJuego() {
        if (utilidades.listaJugadores!=null && utilidades.listaJugadores.size()>0)
        {
            DialogoTipoJuegoFragment dialogoTipoJuego=new DialogoTipoJuegoFragment();
            dialogoTipoJuego.show(getSupportFragmentManager(),"DialogoTipoJuego");
        }else{
            Toast.makeText(getApplicationContext(),"Debe registrar un Jugador",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void llamarAjustes() {
        Intent intent=new Intent(this, AjustesActivity.class);
        startActivity(intent);
    }

    @Override
    public void consultaRanking() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,rankingFragment).commit();
    }

    @Override
    public void consultaInstrucciones() {
        //Toast.makeText(getApplicationContext(),"iniciar instruccion Activity",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, ContendorInstruccionesActivity.class);
        startActivity(intent);
    }

    @Override
    public void gestionarUsuario() {
        dialogoGestionUsuario().show();
    }

    @Override
    public void consultaInformacion() {
        Intent intent=new Intent(this, AcercaDeActivity.class);
        startActivity(intent);
    }

    @Override
    public void mostrarMenu() {
        utilidades.obtenerListaAvatars();
        utilidades.consultarListajugadores(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragmentInicio).commit();
    }

    //controla la pulsacion del boton atras
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de Therapy Game?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }

        return super.onKeyDown(keyCode, event);
    }
    //----------------------------------------------------------------------------------------------
}