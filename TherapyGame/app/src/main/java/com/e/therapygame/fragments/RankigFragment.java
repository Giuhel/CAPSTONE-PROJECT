package com.e.therapygame.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.e.therapygame.R;
import com.e.therapygame.adapters.AdaptadorResultados;
import com.e.therapygame.clases.ConexionSQLiteHelper;
import com.e.therapygame.clases.PreferenciasJuego;
import com.e.therapygame.clases.utilidades;
import com.e.therapygame.clases.vo.JugadorVO;
import com.e.therapygame.clases.vo.ResultadosVo;
import com.e.therapygame.interfaces.IComunicaFragments;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankigFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Activity actividad;
    View vista;
    IComunicaFragments iComunicaFragments;

    Spinner comboTipoResultados,comboNivel,comboModo;
    RecyclerView recyclerJugadores;
    JugadorVO jugadorSeleccionado;
    ArrayList<ResultadosVo> listaResultados;

    ImageButton btnAtras,btnAyuda;
    TextView separador,txtmensaje;

    FloatingActionButton btnBuscar;

    RelativeLayout layoutFondo;

    public RankigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankigFragment newInstance(String param1, String param2) {
        RankigFragment fragment = new RankigFragment();
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
        vista = inflater.inflate(R.layout.fragment_rankig, container, false);

        comboTipoResultados=vista.findViewById(R.id.comboResultados);
        comboNivel=vista.findViewById(R.id.comboNivel);
        comboModo=vista.findViewById(R.id.comboModo);
        txtmensaje=vista.findViewById(R.id.txtSinDatos);
        layoutFondo=vista.findViewById(R.id.idLayoutFondo);

        ArrayList<String> listaNivel=new ArrayList<>();
        listaNivel.add("Todos");
        listaNivel.add("Facil");
        listaNivel.add("Medio");

        ArrayList<String> listaModo=new ArrayList<>();
        listaModo.add("Todos");
        listaModo.add("Intentos");
        listaModo.add("Tiempo");

        ArrayAdapter<CharSequence> adapterTipoRes=ArrayAdapter.createFromResource(actividad,R.array.Combo_resultados,android.R.layout.simple_spinner_item);
        comboTipoResultados.setAdapter(adapterTipoRes);

        ArrayAdapter<CharSequence> adapterNivel=new ArrayAdapter(actividad,android.R.layout.simple_spinner_item,listaNivel);
        comboNivel.setAdapter(adapterNivel);

        ArrayAdapter<CharSequence> adapterModo=new ArrayAdapter(actividad,android.R.layout.simple_spinner_item,listaModo);
        comboModo.setAdapter(adapterModo);

        recyclerJugadores=vista.findViewById(R.id.recyclerJugadoresId);
        recyclerJugadores.setLayoutManager(new LinearLayoutManager(this.actividad));
        recyclerJugadores.setHasFixedSize(true);

        separador=vista.findViewById(R.id.separadorId);
        btnAtras=vista.findViewById(R.id.btnIcoAtras);
        btnAyuda=vista.findViewById(R.id.btnAyuda);
        btnBuscar=vista.findViewById(R.id.idFabConsultar);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iComunicaFragments.mostrarMenu();
            }
        });

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                createSimpleDialog().show();
            }
        });

        consultarResultador("select j.id,j.nombre,j.genero,j.avatar,p.puntos,p.nivel,p.modo from "+
                utilidades.TABLA_JUGADOR+ " j,"+utilidades.TABLA_PUNTAJE+ " p where j.id=p.id and j.id="+ PreferenciasJuego.jugadorId);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int jugadorId=PreferenciasJuego.jugadorId;
               String tipoConsulta=comboTipoResultados.getSelectedItem().toString();
               String nivelJuego=comboNivel.getSelectedItem().toString();
               String modo=comboModo.getSelectedItem().toString();
               String consulta="";

                if(!tipoConsulta.equals("Individual")){
                    if(nivelJuego.equalsIgnoreCase("Todos") && modo.equalsIgnoreCase("Todos")){
                        consulta="select j.id,j.nombre,j.genero,j.avatar,p.puntos,p.nivel,p.modo from "+utilidades.TABLA_JUGADOR+" j,"+utilidades.TABLA_PUNTAJE+" p where j.id=p.id";
                    }else if(!nivelJuego.equalsIgnoreCase("Todos") && modo.equalsIgnoreCase("Todos")){
                        consulta="select j.id,j.nombre,j.genero,j.avatar,p.puntos,p.nivel,p.modo from "+utilidades.TABLA_JUGADOR+" j,"+utilidades.TABLA_PUNTAJE+" p where j.id=p.id and p.nivel='"+nivelJuego+"'";
                    }else if(nivelJuego.equalsIgnoreCase("Todos") && !modo.equalsIgnoreCase("Todos")){
                        consulta="select j.id,j.nombre,j.genero,j.avatar,p.puntos,p.nivel,p.modo from "+utilidades.TABLA_JUGADOR+" j,"+utilidades.TABLA_PUNTAJE+" p where j.id=p.id and p.modo='"+modo.toUpperCase().trim()+"'";
                    }else if(!nivelJuego.equalsIgnoreCase("Todos") && !modo.equalsIgnoreCase("Todos")){
                        consulta="select j.id,j.nombre,j.genero,j.avatar,p.puntos,p.nivel,p.modo from "+utilidades.TABLA_JUGADOR+" j,"+utilidades.TABLA_PUNTAJE+" p where j.id=p.id and p.nivel='"+nivelJuego+"' and p.modo='"+modo.toUpperCase().trim()+"'";
                    }
                }else{
                    if(nivelJuego.equalsIgnoreCase("Todos") && modo.equalsIgnoreCase("Todos")){
                        consulta="select j.id,j.nombre,j.genero,j.avatar,p.puntos,p.nivel,p.modo from "+utilidades.TABLA_JUGADOR+" j,"+utilidades.TABLA_PUNTAJE+" p where j.id=p.id and j.id="+jugadorId;
                    }else if(!nivelJuego.equalsIgnoreCase("Todos") && modo.equalsIgnoreCase("Todos")){
                        consulta="select j.id,j.nombre,j.genero,j.avatar,p.puntos,p.nivel,p.modo from "+utilidades.TABLA_JUGADOR+" j,"+utilidades.TABLA_PUNTAJE+" p where j.id=p.id and j.id="+jugadorId+" and p.nivel='"+nivelJuego+"'";
                    }else if(nivelJuego.equalsIgnoreCase("Todos") && !modo.equalsIgnoreCase("Todos")){
                        consulta="select j.id,j.nombre,j.genero,j.avatar,p.puntos,p.nivel,p.modo from "+utilidades.TABLA_JUGADOR+" j,"+utilidades.TABLA_PUNTAJE+" p where j.id=p.id and j.id="+jugadorId+" and p.modo='"+modo.toUpperCase().trim()+"'";
                    }else if(!nivelJuego.equalsIgnoreCase("Todos") && !modo.equalsIgnoreCase("Todos")){
                        consulta="select j.id,j.nombre,j.genero,j.avatar,p.puntos,p.nivel,p.modo from "+utilidades.TABLA_JUGADOR+" j,"+utilidades.TABLA_PUNTAJE+" p where j.id=p.id and j.id="+jugadorId+" and p.nivel='"+nivelJuego+"' and p.modo='"+modo.toUpperCase().trim()+"'";
                    }
                }
               consultarResultador(consulta);
            }
        });

        return vista;
    }

    private void consultarResultador(String consulta) {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(actividad,utilidades.NOMBRE_BD,null,1);
        SQLiteDatabase db=conn.getReadableDatabase();

        ResultadosVo resultadosVo=null;
        listaResultados = new ArrayList<ResultadosVo>();

        Log.i("consulta",""+consulta);
        Cursor cursor=db.rawQuery(consulta,null);

        while (cursor.moveToNext()){
            resultadosVo=new ResultadosVo();
            resultadosVo.setId(cursor.getInt(0));
            resultadosVo.setNombre(cursor.getString(1));
            resultadosVo.setGenero(cursor.getString(2));
            resultadosVo.setAvatar(cursor.getInt(3));
            resultadosVo.setPuntos(cursor.getInt(4));
            resultadosVo.setNivel(cursor.getString(5));
            resultadosVo.setModo(cursor.getString(6));

            listaResultados.add(resultadosVo);
        }

        if(listaResultados.size()>0){
            txtmensaje.setText("Se encontraron "+listaResultados.size()+" Resultados");
        }else {
            txtmensaje.setText("No hay datos Asociados");
        }
        db.close();
        llenarAdaptadorJugadores();

    }

    private void llenarAdaptadorJugadores() {
        AdaptadorResultados adaptadorResultados=new AdaptadorResultados(listaResultados);
        recyclerJugadores.setAdapter(adaptadorResultados);
    }

    public void asignarValoresPreferencias(){
        layoutFondo.setBackgroundColor(getResources().getColor(PreferenciasJuego.colorTema));
        separador.setBackgroundColor(getResources().getColor(PreferenciasJuego.colorTema));
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(actividad);

        builder.setTitle("Ayuda")
                .setMessage("Defina los parámetros de consulta y presione el botón para conocer el Ranking de Stroopers")
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
        if (context instanceof  Activity) {
            this.actividad= (Activity) context;
            iComunicaFragments= (IComunicaFragments) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        asignarValoresPreferencias();
    }


}