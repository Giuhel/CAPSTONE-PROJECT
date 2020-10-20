package com.e.therapygame.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.therapygame.R;
import com.e.therapygame.clases.utilidades;
import com.e.therapygame.clases.vo.AvatarVo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorAvatar extends RecyclerView.Adapter<AdaptadorAvatar.ViewHolderAvatar> {

    List<AvatarVo> listAvatars;
    View vista;
    int posicionMarcada=0;

    public AdaptadorAvatar(List<AvatarVo> listAvatars) {
        this.listAvatars = listAvatars;
    }

    @NonNull
    @Override
    public ViewHolderAvatar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_avatar,null,false);

        return new ViewHolderAvatar(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAvatar viewHolderAvatar, int i) {
        viewHolderAvatar.imgAvatar.setImageResource(listAvatars.get(i).getAvatarId());
        final int pos=i;

        viewHolderAvatar.cardAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicionMarcada=pos;
                utilidades.avatarSeleccion=listAvatars.get(pos);
                utilidades.avatarIdSeleccion=pos+1;
                notifyDataSetChanged();
            }
        });

        if(utilidades.avatarIdSeleccion==0){
            if (posicionMarcada==i){
                viewHolderAvatar.barraSeleccion.setBackgroundColor(vista.getResources().getColor(R.color.colorPrimaryDark));
            }else{
                viewHolderAvatar.barraSeleccion.setBackgroundColor(vista.getResources().getColor(R.color.colorBlanco));
            }
        }else {
            if(utilidades.avatarIdSeleccion-1==pos){
                viewHolderAvatar.barraSeleccion.setBackgroundColor(vista.getResources().getColor(R.color.colorPrimaryDark));
            }else{
                viewHolderAvatar.barraSeleccion.setBackgroundColor(vista.getResources().getColor(R.color.colorBlanco));
            }
        }



    }

    @Override
    public int getItemCount() {
        return listAvatars.size();
    }

    public class ViewHolderAvatar extends RecyclerView.ViewHolder {

        CardView cardAvatar;
        ImageView imgAvatar;
        TextView barraSeleccion;

        public ViewHolderAvatar(@NonNull View itemView) {
            super(itemView);
            cardAvatar=itemView.findViewById(R.id.cardAvatar);
            imgAvatar=itemView.findViewById(R.id.idAvatar);
            barraSeleccion=itemView.findViewById(R.id.barraSeleccionId);
        }
    }
}
