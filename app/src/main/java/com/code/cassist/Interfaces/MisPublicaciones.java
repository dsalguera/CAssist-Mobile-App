package com.code.cassist.Interfaces;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import prefs.UserInfo;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.code.cassist.Adaptadores.DescubrirAdapter;
import com.code.cassist.Adaptadores.MisPublicacionesAdapter;
import com.code.cassist.Clases.PublicacionesListaDescubrir;
import com.code.cassist.Clases.PublicacionesListaMisPublicaciones;
import com.code.cassist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MisPublicaciones extends Fragment {


    private final static String URL_DATA = "https://code-brainers.000webhostapp.com/obtener_mis_publicaciones.php?usuario=";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    EditText txtSearch;
    Button btnNueva;

    private UserInfo userInfo;

    List<PublicacionesListaMisPublicaciones> publicacionesListaMisPublicacionesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_mis_publicaciones, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_mis_pubs);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        txtSearch = (EditText) rootView.findViewById(R.id.txtSearchMyPub);

        btnNueva = (Button) rootView.findViewById(R.id.btnNuevaPub);

        userInfo = new UserInfo(getContext());

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrearPublicacion.class);
                startActivity(intent);
            }
        });

        publicacionesListaMisPublicacionesList = new ArrayList<>();

        loadUrlData();

        return rootView;
    }

    private void filter(String text){
        List<PublicacionesListaMisPublicaciones> filtrados = new ArrayList<>();

        for(PublicacionesListaMisPublicaciones item : publicacionesListaMisPublicacionesList){
            if(item.getTitulo().toLowerCase().contains(text.toLowerCase())){
                filtrados.add(item);
            }
        }

        adapter = new MisPublicacionesAdapter(filtrados, getContext());
        recyclerView.setAdapter(adapter);

    }

    private void loadUrlData() {

        /*final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Espere unos segundos mientras obtenemos los datos...");
        progressDialog.show();*/



        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA+userInfo.getKEY_idusuario(), new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                //progressDialog.dismiss();

                try {


                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("publicacion_favorito");

                    for(int i = 0; i < array.length() ; i++){

                        JSONObject jo = array.getJSONObject(i);

                        PublicacionesListaMisPublicaciones publicacion = new PublicacionesListaMisPublicaciones(

                                new String(jo.getString("id_publicacion").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("titulo").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("imagen").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("descripcion").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("telefono").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("direccion").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("email").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("fecha_creacion").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("nombre_usuario").getBytes("ISO-8859-1"), "UTF-8"));


                        publicacionesListaMisPublicacionesList.add(publicacion);
                    }

                    adapter = new MisPublicacionesAdapter(publicacionesListaMisPublicacionesList, getContext());
                    recyclerView.setAdapter(adapter);

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "No posee publicaciones, pruebe agregar una.", Toast.LENGTH_SHORT).show();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Ocurrio un error de lectura del servidor: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
