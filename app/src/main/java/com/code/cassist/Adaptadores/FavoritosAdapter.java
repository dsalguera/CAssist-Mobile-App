package com.code.cassist.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.cassist.Clases.PublicacionesListaFavoritos;
import com.code.cassist.Interfaces.PrevisualizarPublicacion;
import com.code.cassist.PrevisualizarFavoritos;
import com.code.cassist.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolder> {

    public static final String KEY_ID_PUB = "id_publicacion";
    public static final String KEY_TITULO = "titulo";
    public static final String KEY_IMAGEN = "imagen";
    public static final String KEY_DESC = "descripcion";
    public static final String KEY_TEL = "telefono";
    public static final String KEY_DIRECCION = "direccion";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CREACION = "fecha_creacion";
    public static final String KEY_ID_USU = "nombre_usuario";

    private List<PublicacionesListaFavoritos> publicacionesListaFavoritos;
    private Context context;

    public FavoritosAdapter(List<PublicacionesListaFavoritos> publicacionesListaFavoritos, Context context){
        this.publicacionesListaFavoritos = publicacionesListaFavoritos;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_favoritos, parent, false);
        FavoritosAdapter.ViewHolder viewHolder = new FavoritosAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PublicacionesListaFavoritos publicacionesListaFavoritox = publicacionesListaFavoritos.get(position);
        holder.itemTitleF.setText(publicacionesListaFavoritox.getTitulo());
        holder.itemDescF.setText(publicacionesListaFavoritox.getDescripcion());
        holder.itemFechaF.setText(publicacionesListaFavoritox.getFecha_creacion());
        holder.itemUsuarioF.setText(publicacionesListaFavoritox.getId_usuario());

        Picasso.with(context).load(publicacionesListaFavoritox.getImagen()).into(holder.itemImageF);
    }

    @Override
    public int getItemCount() {
        return publicacionesListaFavoritos.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitleF;
        public TextView itemDescF;
        public ImageView itemImageF;
        public TextView itemFechaF;
        public TextView itemUsuarioF;


        public ViewHolder(final View itemView) {
            super(itemView);

            itemTitleF = (TextView) itemView.findViewById(R.id.item_titleF);
            itemImageF = (ImageView) itemView.findViewById(R.id.item_imageF);
            itemDescF = (TextView) itemView.findViewById(R.id.item_descF);
            itemFechaF = (TextView) itemView.findViewById(R.id.item_creacionF);
            itemUsuarioF = (TextView) itemView.findViewById(R.id.item_usuarioF);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), PrevisualizarFavoritos.class);

                    PublicacionesListaFavoritos publicacionesListaFavoritos1 = publicacionesListaFavoritos.get(position);
                    intent.putExtra(KEY_ID_PUB, publicacionesListaFavoritos1.getId_pub());
                    intent.putExtra(KEY_TITULO, publicacionesListaFavoritos1.getTitulo());
                    intent.putExtra(KEY_IMAGEN, publicacionesListaFavoritos1.getImagen());
                    intent.putExtra(KEY_DESC, publicacionesListaFavoritos1.getDescripcion());
                    intent.putExtra(KEY_TEL, publicacionesListaFavoritos1.getTelefono());
                    intent.putExtra(KEY_DIRECCION, publicacionesListaFavoritos1.getDireccion());
                    intent.putExtra(KEY_EMAIL, publicacionesListaFavoritos1.getEmail());
                    intent.putExtra(KEY_CREACION, publicacionesListaFavoritos1.getFecha_creacion());
                    intent.putExtra(KEY_ID_USU, publicacionesListaFavoritos1.getId_usuario());

                    view.getContext().startActivity(intent);
                }
            });
        }

    }

}
