package com.e.therapygame.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.e.therapygame.R;
import com.e.therapygame.clases.PreferenciasJuego;
import com.e.therapygame.clases.utilidades;
import com.e.therapygame.interfaces.IComunicaFragments;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    Activity actividad;
    RelativeLayout layoutFondo;
    CardView cardJugar,cardAjustes,cardRanking,cardIntrucciones,cardUsuario,cardInformacion;
    IComunicaFragments interfaceComunicaFragments;

    TextView textNickName;
    ImageView imageAvatar;
    ImageButton btnAyuda;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_inicio, container, false);
        cardJugar=vista.findViewById(R.id.cardJugar);
        cardAjustes=vista.findViewById(R.id.cardAjustes);
        cardRanking=vista.findViewById(R.id.cardmedallas);
        cardIntrucciones=vista.findViewById(R.id.cardinstruccion);
        cardUsuario=vista.findViewById(R.id.cardUsuario);
        cardInformacion=vista.findViewById(R.id.cardInfo);

        textNickName=vista.findViewById(R.id.textNicname);
        imageAvatar=vista.findViewById(R.id.avatarImage);
        layoutFondo=vista.findViewById(R.id.idLayoutFondo);
        btnAyuda=vista.findViewById(R.id.btnAyuda);

        textNickName.setText(PreferenciasJuego.nickName);

        if(PreferenciasJuego.avatarId==1){
            imageAvatar.setImageResource(R.drawable.cara_simio_banner);
        }else {
            imageAvatar.setImageResource(utilidades.listaAvatars.get(PreferenciasJuego.avatarId-1).getAvatarId());
        }

        eventoMenu();

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                createSimpleDialog().show();
            }
        });

        return vista;
    }

    public void asignarValoresPreferencias(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            layoutFondo.setBackground(getResources().getDrawable(PreferenciasJuego.formaBanner));
        }else {
            layoutFondo.setBackgroundDrawable(getResources().getDrawable(PreferenciasJuego.formaBanner));
        }
        Drawable shape=layoutFondo.getBackground();
        shape.setColorFilter(getResources().getColor(PreferenciasJuego.colorTema), PorterDuff.Mode.SRC);
    }

    private void eventoMenu() {
        cardJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.IniciarJuego();
            }
        });

        cardAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.llamarAjustes();
            }
        });

        cardRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.consultaRanking();
            }
        });

        cardIntrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.consultaInstrucciones();
            }
        });

        cardUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.gestionarUsuario();
            }
        });

        cardInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.consultaInformacion();
            }
        });
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(actividad);

        builder.setTitle("Ayuda")
                .setMessage("La aplicación cuenta con 2 niveles de juego que pueden ser configurables desde los Ajustes, para iniciar debes crear un Jugador o seleccionar uno de los existentes. Navega desde el menú principal")
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            actividad= (Activity) context;
            interfaceComunicaFragments= (IComunicaFragments) actividad;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        asignarValoresPreferencias();
    }


}