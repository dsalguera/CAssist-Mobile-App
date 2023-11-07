package com.code.cassist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import prefs.UserInfo;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.code.cassist.Adaptadores.DescubrirAdapter;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PrevisualizarMisPublicaciones extends AppCompatActivity {

    String id_publicacion;
    String id_usuario;
    private UserInfo userInfo;
    String urlUpload = "https://code-brainers.000webhostapp.com/delete_mypub.php";

    ImageView imgPrev;
    TextView txtdesc;
    TextView txttitulo;

    TextView txtdireccion;
    TextView txttelefono;
    TextView txtemail;
    TextView txtfechacreacion;
    TextView txtiduser;

    Button btnBorrar;
    Button LlamarPersona;

    ImageView closePub;

    String email, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previsualizar_mis_publicaciones);

        imgPrev = (ImageView) findViewById(R.id.imagenPrevM);
        txtdesc = (TextView) findViewById(R.id.desc_prevM);
        txttitulo = (TextView) findViewById(R.id.tituloPrevM);


        txtdireccion = (TextView) findViewById(R.id.direccion_prevM);
        txttelefono = (TextView) findViewById(R.id.telefono_prevM);
        txtemail = (TextView) findViewById(R.id.email_prevM);
        txtfechacreacion = (TextView) findViewById(R.id.creacion_prevM);
        txtiduser = (TextView) findViewById(R.id.id_usuarioM);

        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        closePub = (ImageView) findViewById(R.id.closePubPrevM);


        userInfo = new UserInfo(getApplicationContext());

        Intent intent = getIntent();
        id_publicacion = intent.getStringExtra(DescubrirAdapter.KEY_ID_PUB);
        id_usuario = userInfo.getKEY_idusuario();

        final String titulo = intent.getStringExtra(DescubrirAdapter.KEY_TITULO);
        final String descripcion = intent.getStringExtra(DescubrirAdapter.KEY_DESC);
        final String direccion = intent.getStringExtra(DescubrirAdapter.KEY_DIRECCION);
        telefono = intent.getStringExtra(DescubrirAdapter.KEY_TEL);
        email = intent.getStringExtra(DescubrirAdapter.KEY_EMAIL);
        final String creacion = intent.getStringExtra(DescubrirAdapter.KEY_CREACION);
        final String usuario = intent.getStringExtra(DescubrirAdapter.KEY_ID_USU);

        String image = intent.getStringExtra(DescubrirAdapter.KEY_IMAGEN);

        Picasso.with(this).load(image).into(imgPrev);

        txttitulo.setText(titulo);
        txtdesc.setText("Descripción: " + descripcion);

        txtdireccion.setText("Dirección: " + direccion);
        txttelefono.setText("Tel/Cel: " + telefono);
        txtemail.setText("Correo: " + email);
        txtfechacreacion.setText("Fecha creación: " + creacion);
        txtiduser.setText("Usuario publicado: " + usuario);

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrarPub();
            }
        });

        closePub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void BorrarPub(){
        /*
         *
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PrevisualizarMisPublicaciones.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PrevisualizarMisPublicaciones.this, "Error: "+error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id_usuario",id_usuario);
                params.put("id_publicacion",id_publicacion);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PrevisualizarMisPublicaciones.this);
        requestQueue.add(stringRequest);
    }

}
