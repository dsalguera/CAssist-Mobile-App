package com.code.cassist.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.cassist.Clases.PublicacionesListaDescubrir;
import com.code.cassist.Interfaces.PrevisualizarPublicacion;
import com.code.cassist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DescubrirAdapter extends RecyclerView.Adapter<DescubrirAdapter.ViewHolder> {

    public static final String KEY_ID_PUB = "id_publicacion";
    public static final String KEY_TITULO = "titulo";
    public static final String KEY_IMAGEN = "imagen";
    public static final String KEY_DESC = "descripcion";
    public static final String KEY_TEL = "telefono";
    public static final String KEY_DIRECCION = "direccion";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CREACION = "fecha_creacion";
    public static final String KEY_ID_USU = "nombre_usuario";

    private List<PublicacionesListaDescubrir> publicacionesListaDescubrirs;
    private Context context;

    public DescubrirAdapter(List<PublicacionesListaDescubrir> publicacionesListaDescubrirs, Context context){
        this.publicacionesListaDescubrirs = publicacionesListaDescubrirs;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_descubrir, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PublicacionesListaDescubrir publicacionesListaDescubrir = publicacionesListaDescubrirs.get(position);
        holder.itemTitle.setText(publicacionesListaDescubrir.getTitulo());
        holder.itemDesc.setText(publicacionesListaDescubrir.getDescripcion());
        holder.itemFecha.setText(publicacionesListaDescubrir.getFecha_creacion());
        holder.itemUsuario.setText(publicacionesListaDescubrir.getId_usuario());

        Picasso.with(context)
                .load(publicacionesListaDescubrir.getImagen())
                .into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return publicacionesListaDescubrirs.size();
    }

    public void filterList(ArrayList<PublicacionesListaDescubrir> filtrados){
        publicacionesListaDescubrirs = filtrados;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView itemTitle;
        public TextView itemDesc;
        public ImageView itemImage;
        public TextView itemFecha;
        public TextView itemUsuario;

        public ViewHolder(final View itemView) {
            super(itemView);


            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemDesc = (TextView) itemView.findViewById(R.id.item_desc);
            itemFecha = (TextView) itemView.findViewById(R.id.item_creacion);
            itemUsuario = (TextView) itemView.findViewById(R.id.item_usuario);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), PrevisualizarPublicacion.class);

                    PublicacionesListaDescubrir publicacionesListaDescubrir1 = publicacionesListaDescubrirs.get(position);
                    intent.putExtra(KEY_ID_PUB, publicacionesListaDescubrir1.getId_pub());
                    intent.putExtra(KEY_TITULO, publicacionesListaDescubrir1.getTitulo());
                    intent.putExtra(KEY_IMAGEN, publicacionesListaDescubrir1.getImagen());
                    intent.putExtra(KEY_DESC, publicacionesListaDescubrir1.getDescripcion());
                    intent.putExtra(KEY_TEL, publicacionesListaDescubrir1.getTelefono());
                    intent.putExtra(KEY_DIRECCION, publicacionesListaDescubrir1.getDireccion());
                    intent.putExtra(KEY_EMAIL, publicacionesListaDescubrir1.getEmail());
                    intent.putExtra(KEY_CREACION, publicacionesListaDescubrir1.getFecha_creacion());
                    intent.putExtra(KEY_ID_USU, publicacionesListaDescubrir1.getId_usuario());

                    view.getContext().startActivity(intent);
                }
            });
        }

    }


}
