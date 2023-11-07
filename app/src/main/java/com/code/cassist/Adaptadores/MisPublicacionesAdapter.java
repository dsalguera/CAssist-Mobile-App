package com.code.cassist.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.cassist.Clases.PublicacionesListaMisPublicaciones;
import com.code.cassist.Interfaces.PrevisualizarPublicacion;
import com.code.cassist.PrevisualizarMisPublicaciones;
import com.code.cassist.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MisPublicacionesAdapter extends RecyclerView.Adapter<MisPublicacionesAdapter.ViewHolder> {

    public static final String KEY_ID_PUB = "id_publicacion";
    public static final String KEY_TITULO = "titulo";
    public static final String KEY_IMAGEN = "imagen";
    public static final String KEY_DESC = "descripcion";
    public static final String KEY_TEL = "telefono";
    public static final String KEY_DIRECCION = "direccion";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CREACION = "fecha_creacion";
    public static final String KEY_ID_USU = "nombre_usuario";

    private List<PublicacionesListaMisPublicaciones> publicacionesListaMisPublicaciones;
    private Context context;

    public MisPublicacionesAdapter(List<PublicacionesListaMisPublicaciones> publicacionesListaMisPublicaciones, Context context){
        this.publicacionesListaMisPublicaciones = publicacionesListaMisPublicaciones;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mypubs, parent, false);
        MisPublicacionesAdapter.ViewHolder viewHolder = new MisPublicacionesAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PublicacionesListaMisPublicaciones publicacionesListaMisPublicacionex = publicacionesListaMisPublicaciones.get(position);
        holder.itemTitleM.setText(publicacionesListaMisPublicacionex.getTitulo());
        holder.itemDescM.setText(publicacionesListaMisPublicacionex.getDescripcion());
        holder.itemFechaM.setText(publicacionesListaMisPublicacionex.getFecha_creacion());
        holder.itemUsuarioM.setText(publicacionesListaMisPublicacionex.getId_usuario());

        Picasso.with(context).load(publicacionesListaMisPublicacionex.getImagen()).into(holder.itemImageM);
    }

    @Override
    public int getItemCount() {
        return publicacionesListaMisPublicaciones.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitleM;
        public TextView itemDescM;
        public ImageView itemImageM;
        public TextView itemFechaM;
        public TextView itemUsuarioM;


        public ViewHolder(final View itemView) {
            super(itemView);

            itemTitleM = (TextView) itemView.findViewById(R.id.item_titleM);
            itemImageM = (ImageView) itemView.findViewById(R.id.item_imageM);
            itemDescM = (TextView) itemView.findViewById(R.id.item_descM);
            itemFechaM = (TextView) itemView.findViewById(R.id.item_creacionM);
            itemUsuarioM = (TextView) itemView.findViewById(R.id.item_usuarioM);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), PrevisualizarMisPublicaciones.class);

                    PublicacionesListaMisPublicaciones publicacionesListaMisPublicaciones1 = publicacionesListaMisPublicaciones.get(position);
                    intent.putExtra(KEY_ID_PUB, publicacionesListaMisPublicaciones1.getId_pub());
                    intent.putExtra(KEY_TITULO, publicacionesListaMisPublicaciones1.getTitulo());
                    intent.putExtra(KEY_IMAGEN, publicacionesListaMisPublicaciones1.getImagen());
                    intent.putExtra(KEY_DESC, publicacionesListaMisPublicaciones1.getDescripcion());
                    intent.putExtra(KEY_TEL, publicacionesListaMisPublicaciones1.getTelefono());
                    intent.putExtra(KEY_DIRECCION, publicacionesListaMisPublicaciones1.getDireccion());
                    intent.putExtra(KEY_EMAIL, publicacionesListaMisPublicaciones1.getEmail());
                    intent.putExtra(KEY_CREACION, publicacionesListaMisPublicaciones1.getFecha_creacion());
                    intent.putExtra(KEY_ID_USU, publicacionesListaMisPublicaciones1.getId_usuario());

                    view.getContext().startActivity(intent);
                }
            });
        }

    }

}
