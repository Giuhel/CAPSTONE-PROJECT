package com.e.therapygame.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.therapygame.R;
import com.e.therapygame.adapters.AdaptadorAvatar;
import com.e.therapygame.adapters.AdaptadorJugador;
import com.e.therapygame.clases.ConexionSQLiteHelper;
import com.e.therapygame.clases.PreferenciasJuego;
import com.e.therapygame.clases.utilidades;
import com.e.therapygame.clases.vo.JugadorVO;
import com.e.therapygame.interfaces.IComunicaFragments;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GestionJugadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GestionJugadorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int eventoEliminar=0;
    Activity actividad;
    View vista;
    IComunicaFragments iComunicaFragments;
    FloatingActionsMenu grupBotones;
    FloatingActionButton fabConfirmar,fabActualizar,fabEliminar;

    RecyclerView recyclerAvatars,recyclerJugadores;

    ImageButton btnAtras,btnAyuda;
    TextView barraSeleccion;
    EditText campoNick;
    RadioButton radioM,radioF;

    JugadorVO jugadorSeleccionado;

    RelativeLayout layoutFondo;


    public GestionJugadorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GestionJugadorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GestionJugadorFragment newInstance(String param1, String param2) {
        GestionJugadorFragment fragment = new GestionJugadorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_gestion_jugador, container, false);

        recyclerAvatars =vista.findViewById(R.id.recyclerAvatarsId);
        recyclerJugadores =vista.findViewById(R.id.recyclerJugadorId);


        btnAtras=vista.findViewById(R.id.btnIcoAtras);
        grupBotones=vista.findViewById(R.id.grupoFab);
        fabActualizar=vista.findViewById(R.id.idFabActualizar);
        fabEliminar=vista.findViewById(R.id.idFabEliminar);
        fabConfirmar=vista.findViewById(R.id.idFAbConfirmar);


        campoNick=vista.findViewById(R.id.campoNickName);
        radioM=vista.findViewById(R.id.radioM);
        radioF=vista.findViewById(R.id.radioF);
        barraSeleccion=vista.findViewById(R.id.barraSeleccionId);
        layoutFondo=vista.findViewById(R.id.idLayoutFondo);


        recyclerJugadores.setLayoutManager(new LinearLayoutManager(this.actividad));
        recyclerJugadores.setHasFixedSize(true);

        recyclerAvatars.setLayoutManager(new GridLayoutManager(this.actividad,3));
        recyclerAvatars.setHasFixedSize(true);

        btnAyuda=vista.findViewById(R.id.btnAyuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSimpleDialog().show();
            }
        });


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eventoEliminar==0){
                    iComunicaFragments.mostrarMenu();
                    campoNick.setText("");
                }else{
                    Toast.makeText(actividad,"Debe seleccionar un Jugador",Toast.LENGTH_SHORT).show();
                }

            }
        });

        fabActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarJugador();
                llenarAdaptadorJugador();
                eventoEliminar=0;
                grupBotones.collapse();
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campoNick.getText().toString()!=null && !campoNick.getText().toString().trim().equals("")){
                    dialogoEliminar().show();
                    grupBotones.collapse();
                }else {
                    Toast.makeText(actividad,"Debe seleccionar un usuario apra eliminar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        fabConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campoNick.getText().toString()!=null && !campoNick.getText().toString().trim().equals("")){
                    PreferenciasJuego.nickName=jugadorSeleccionado.getNombre();
                    PreferenciasJuego.avatarId=utilidades.avatarSeleccion.getId();
                    PreferenciasJuego.jugadorId=jugadorSeleccionado.getId();
                    SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(actividad);
                    PreferenciasJuego.asignarPreferenciasJugador(preferences,actividad);
                    grupBotones.collapse();
                    campoNick.setText("");
                    eventoEliminar=0;
                    iComunicaFragments.mostrarMenu();
                }else {
                    Toast.makeText(actividad,"Verifique los datos para realizar la seleccion",Toast.LENGTH_SHORT).show();
                }

            }
        });

        llenarAdaptadorJugador();
        llenarAdaptadorAvatars(0);


        return vista;
    }

    public AlertDialog dialogoEliminar(){
        AlertDialog.Builder builder=new AlertDialog.Builder(actividad);
        builder.setTitle("Advertencia!!!")
                .setMessage("¿Estas segurp que desea eliminar a "+jugadorSeleccionado.getNombre().toUpperCase()+" y toda su informacion")
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setPositiveButton("ACEPTAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EliminarJugador();
                                llenarAdaptadorJugador();
                            }
                        });
        return builder.create();
    }

    private void ConfirmarJugador() {

    }

    private void actualizarJugador() {
        String genero="";

        if (radioM.isChecked()==true){
            genero="1";
        }else if(radioF.isChecked()==true){
            genero="2";
        }else {
            genero="No Seleccionado";
        }

        if (!genero.equals("No Seleccionado") && campoNick.getText().toString()!=null && !campoNick.getText().toString().trim().equals("")){

            int avatarId=utilidades.avatarSeleccion.getId();
            String nickName=campoNick.getText().toString();

            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(actividad,utilidades.NOMBRE_BD,null,1);
            SQLiteDatabase db=conn.getWritableDatabase();

            ContentValues values=new ContentValues();
            values.put(utilidades.CAMPO_NOMBRE,nickName);
            values.put(utilidades.CAMPO_GENERO,genero);
            values.put(utilidades.CAMPO_AVATAR,avatarId);

            int idResultante=db.update(utilidades.TABLA_JUGADOR,values,utilidades.CAMPO_ID+" = "+jugadorSeleccionado.getId(),null);

            if(idResultante!=-1){
                Toast.makeText(actividad,"Se Actualizo exitosamente",Toast.LENGTH_SHORT).show();
                recyclerJugadores.scrollToPosition(jugadorSeleccionado.getId()-1);
                utilidades.consultarListajugadores(actividad);

            }else{
                Toast.makeText(actividad,"No se pudo Actualizar el jugador \n",Toast.LENGTH_SHORT).show();
            }

            db.close();

        }else{
            Toast.makeText(actividad,"Verifique los datos para actualizar! ",Toast.LENGTH_SHORT).show();
        }
    }



    private void EliminarJugador() {
        if(campoNick.getText().toString()!=null && !campoNick.getText().toString().trim().equals("")){
            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(actividad,utilidades.NOMBRE_BD,null,1);
            SQLiteDatabase db=conn.getWritableDatabase();
            SQLiteDatabase db2=conn.getWritableDatabase();

            int idResultante=db.delete(utilidades.TABLA_JUGADOR,utilidades.CAMPO_ID+" = "+jugadorSeleccionado.getId(),null);
            db2.delete(utilidades.TABLA_PUNTAJE,utilidades.CAMPO_ID_JUGADOR+" = "+jugadorSeleccionado.getId(),null);
            if(idResultante!=-1){
                Toast.makeText(actividad,"El jugador Y todos sus datos se elimino con exito! ",Toast.LENGTH_SHORT).show();
                campoNick.setText("");
                radioM.setChecked(false);
                radioF.setChecked(false);
                recyclerJugadores.scrollToPosition(jugadorSeleccionado.getId());
                recyclerAvatars.scrollToPosition(0);
                utilidades.consultarListajugadores(actividad);

                if(utilidades.listaJugadores.size()==0){
                    eventoEliminar=0;
                }else{
                    eventoEliminar=1;
                }

                PreferenciasJuego.avatarId=1;
                PreferenciasJuego.nickName="NA";
                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(actividad);
                PreferenciasJuego.asignarPreferenciasJugador(preferences,actividad);

            }else {
                Toast.makeText(actividad,"El jugador no se pudo eliminar ",Toast.LENGTH_SHORT).show();
            }
            db.close();
        }else {
            Toast.makeText(actividad,"Debe seleccionar un jugador ",Toast.LENGTH_SHORT).show();
        }
    }

    private void llenarAdaptadorJugador() {
        //se asigna la lista de jugadores por defecto
        final AdaptadorJugador miAdaptadorJugadores=new AdaptadorJugador(utilidades.listaJugadores);
        miAdaptadorJugadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                grupBotones.collapse();
                jugadorSeleccionado=utilidades.listaJugadores.get(recyclerJugadores.getChildAdapterPosition(view));
                campoNick.setText(jugadorSeleccionado.getNombre());
                if(jugadorSeleccionado.getGenero().equals("1")){
                    radioM.setChecked(true);
                }else{
                    radioF.setChecked(true);
                }


                utilidades.avatarSeleccion=utilidades.listaAvatars.get(jugadorSeleccionado.getAvatar()-1);

                llenarAdaptadorAvatars(jugadorSeleccionado.getAvatar());

            }

        });

        recyclerJugadores.setAdapter(miAdaptadorJugadores);
    }

    private void llenarAdaptadorAvatars(int avatarId) {
        utilidades.avatarIdSeleccion=avatarId;

        AdaptadorAvatar miAdaptadorAvatars=new AdaptadorAvatar(utilidades.listaAvatars);
        recyclerAvatars.scrollToPosition(avatarId-1);
        recyclerAvatars.setAdapter(miAdaptadorAvatars);
    }

    public androidx.appcompat.app.AlertDialog createSimpleDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(actividad);

        builder.setTitle("Ayuda")
                .setMessage("Elija el jugador de la lista de Jugadores disponible, si lo desea puede modificar su información (Nick, Genero, Avatar) y posteriormente" +
                        "desde el menú de botones flotantes podrá actualizarlo, eliminarlo o seleccionarlo para Jugar.")
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.actividad= (Activity) context;
            iComunicaFragments= (IComunicaFragments) this.actividad;
        }
    }



    public void asignarValoresPreferencias(){
        layoutFondo.setBackgroundColor(getResources().getColor(PreferenciasJuego.colorTema));
    }



    @Override
    public void onResume() {
        super.onResume();
        asignarValoresPreferencias();
    }

}