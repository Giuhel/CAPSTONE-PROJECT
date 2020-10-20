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
import com.e.therapygame.clases.vo.JugadorVO;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorJugador extends RecyclerView.Adapter<AdaptadorJugador.ViewHolderJugador> implements  View.OnClickListener{

    private View.OnClickListener listener;
    List<JugadorVO> listaJugadores;
    View vista;
    public AdaptadorJugador(List<JugadorVO> listaJugadores) {

        this.listaJugadores = listaJugadores;
    }

    @NonNull
    @Override
    public ViewHolderJugador onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        vista= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_jugador,viewGroup,false);
        vista.setOnClickListener(this);

        return new ViewHolderJugador(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderJugador viewHolderJugador, int i) {
        //se resta uno ya que buscamos la lista de elementos que inicia en la pos 0
        viewHolderJugador.imgAvatar.setImageResource(utilidades.listaAvatars.get(listaJugadores.get(i).getAvatar()-1).getAvatarId());
        viewHolderJugador.txtNombre.setText(listaJugadores.get(i).getNombre());
        if (listaJugadores.get(i).getGenero().equals("1")){
            viewHolderJugador.txtGenero.setText("Masculino");
        }else{
            viewHolderJugador.txtGenero.setText("Femenino");
        }



    }

    @Override
    public int getItemCount() {
        return listaJugadores.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;

    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderJugador extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtNombre,txtGenero;

        public ViewHolderJugador(@NonNull View itemView) {
            super(itemView);
            imgAvatar=itemView.findViewById(R.id.idAvatar1);
            txtNombre=itemView.findViewById(R.id.idNombre1);
            txtGenero=itemView.findViewById(R.id.idGenero1);
        }
    }
}
