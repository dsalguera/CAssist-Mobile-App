package com.code.cassist.Interfaces;


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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.code.cassist.Adaptadores.DescubrirAdapter;
import com.code.cassist.Clases.PublicacionesListaDescubrir;
import com.code.cassist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Descubrir extends Fragment {

    private final static String URL_DATA = "https://code-brainers.000webhostapp.com/obtener_publicaciones.php";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    EditText txtSearch;
    TextView txtcreacion;
    String nombre_user;

    private UserInfo userInfo;

    List<PublicacionesListaDescubrir> publicacionesListaDescubrirs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_descubrir, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_descubrir);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        userInfo = new UserInfo(getContext());

        txtSearch = (EditText) rootView.findViewById(R.id.txtSearchPub);

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

        publicacionesListaDescubrirs = new ArrayList<>();

        loadUrlData();

        return rootView;
    }

    private void filter(String text){
        List<PublicacionesListaDescubrir> filtrados = new ArrayList<>();

        for(PublicacionesListaDescubrir item : publicacionesListaDescubrirs){
            if(item.getTitulo().toLowerCase().contains(text.toLowerCase())){
                filtrados.add(item);
            }
        }

        adapter = new DescubrirAdapter(filtrados, getContext());
        recyclerView.setAdapter(adapter);

    }

    private void loadUrlData() {

        /*final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Espere unos segundos mientras obtenemos los datos...");
        progressDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                //progressDialog.dismiss();

                try {


                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("publicacion");



                    for(int i = 0; i < array.length() ; i++){

                        JSONObject jo = array.getJSONObject(i);

                        PublicacionesListaDescubrir publicacion = new PublicacionesListaDescubrir(

                                new String(jo.getString("id_publicacion").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("titulo").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("imagen").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("descripcion").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("telefono").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("direccion").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("email").getBytes("ISO-8859-1"), "UTF-8"),
                                new String (jo.getString("fecha_creacion").getBytes("ISO-8859-1"), "UTF-8"),
                                nombre_user = new String (jo.getString("nombre_usuario").getBytes("ISO-8859-1"), "UTF-8"));


                        if (!nombre_user.equals(userInfo.getKeyUsername())){
                            publicacionesListaDescubrirs.add(publicacion);
                        }
                    }

                    adapter = new DescubrirAdapter(publicacionesListaDescubrirs, getContext());
                    recyclerView.setAdapter(adapter);

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();

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
