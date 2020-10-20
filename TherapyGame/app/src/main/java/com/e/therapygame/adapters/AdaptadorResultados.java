package com.e.therapygame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.therapygame.R;
import com.e.therapygame.clases.utilidades;
import com.e.therapygame.clases.vo.ResultadosVo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorResultados extends RecyclerView.Adapter<AdaptadorResultados.ViewHolderJugador> {

    List<ResultadosVo> listaResultados;
    View vista;



    public AdaptadorResultados(List<ResultadosVo> listaResultados) {
        this.listaResultados = listaResultados;
    }

    @NonNull
    @Override
    public AdaptadorResultados.ViewHolderJugador onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        vista= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_resultados,viewGroup,false);
        return new ViewHolderJugador(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderJugador viewHolderJugador, int i) {

        viewHolderJugador.imgAvatar.setImageResource(utilidades.listaAvatars.get(listaResultados.get(i).getAvatar()-1).getAvatarId());

        viewHolderJugador.txtNombre.setText(listaResultados.get(i).getNombre());
        if(listaResultados.get(i).getGenero().equals("1")){
            viewHolderJugador.txtGenero.setText("Masculino");
        }else{
            viewHolderJugador.txtGenero.setText("Femenino");
        }
        viewHolderJugador.txtNivel.setText(listaResultados.get(i).getNivel());
        viewHolderJugador.txtPuntos.setText(listaResultados.get(i).getPuntos()+"");
        viewHolderJugador.txtModo.setText(listaResultados.get(i).getModo());
    }

    @Override
    public int getItemCount() {
        return listaResultados.size();
    }

    public class ViewHolderJugador extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView txtNombre;
        TextView txtGenero;
        TextView txtNivel;
        TextView txtModo;
        TextView txtPuntos;



        public ViewHolderJugador(@NonNull View itemView) {
            super(itemView);
            imgAvatar=itemView.findViewById(R.id.idAvatar);
            txtNombre=itemView.findViewById(R.id.idNombre);
            txtGenero=itemView.findViewById(R.id.idGenero);
            txtNivel=itemView.findViewById(R.id.idNivel);
            txtModo=itemView.findViewById(R.id.idModo);
            txtPuntos=itemView.findViewById(R.id.idPuntos);

        }
    }
}
