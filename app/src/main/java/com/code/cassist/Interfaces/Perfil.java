package com.code.cassist.Interfaces;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import prefs.UserInfo;
import prefs.UserSession;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.cassist.Adaptadores.DescubrirAdapter;
import com.code.cassist.R;
import com.squareup.picasso.Picasso;

import prefs.UserInfo;
import prefs.UserSession;


public class Perfil extends Fragment {


    public Perfil() {

    }

    private TextView nombre_usuario, correo, fecha_creac, todoNombre, celular, telefono, fecha_nac;
    ImageView perfilImagen;

    private UserInfo userInfo;
    private UserSession userSession;

    Button btnCerrarSesionA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        userInfo = new UserInfo(getContext());
        userSession = new UserSession(getActivity());

        nombre_usuario  = (TextView)view.findViewById(R.id.lbluser);
        correo = (TextView) view.findViewById(R.id.lblcorreo);
        perfilImagen = (ImageView) view.findViewById(R.id.perfilImagen);
        fecha_creac = (TextView) view.findViewById(R.id.fecha_creacion);
        todoNombre = (TextView) view.findViewById(R.id.todoNombre);
        celular = (TextView) view.findViewById(R.id.celular);
        telefono = (TextView) view.findViewById(R.id.telefono);
        fecha_nac = (TextView) view.findViewById(R.id.nacimiento);

        btnCerrarSesionA = (Button) view.findViewById(R.id.btnCerrarSesionAbierta);

        if(!userSession.isUserLoggedin()){
            startActivity(new Intent(getContext(), Login.class));
            getActivity().finish();
        }

        String username = userInfo.getKeyUsername();
        String email = userInfo.getKeyEmail();
        String imagen = userInfo.getKEY_imagen();

        Picasso.with(getContext()).load(imagen).into(perfilImagen);

        fecha_creac.setText("Fecha creación: "+userInfo.getKEY_creacion());
        nombre_usuario.setText(username);
        todoNombre.setText(userInfo.getKEY_primerNombre()+" "+userInfo.getKEY_segundoNombre()+" "+userInfo.getKEY_primerApellido()+" "+userInfo.getKEY_segundoApellido());
        correo.setText(email);
        celular.setText("Celular: "+userInfo.getKEY_celular());
        telefono.setText("Teléfono: "+userInfo.getKEY_telefono());
        fecha_nac.setText("Fecha de nacimiento: "+userInfo.getKEY_fecha_nac());

        btnCerrarSesionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Cerrar la sesión").setMessage("¿Esta séguro que desea cerrar la sesión de "+nombre_usuario.getText()+"?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userSession.setLoggedin(false);
                                userInfo.clearUserInfo();
                                startActivity(new Intent(getActivity(), Login.class));
                                getActivity().finish();
                            }

                        })
                        .setNegativeButton("No", null).show();
            }
        });


        return view;
    }

}
