package com.code.cassist.Interfaces;

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
import com.code.cassist.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PrevisualizarPublicacion extends AppCompatActivity {

    ImageView imgPrev;
    TextView txtdesc;
    TextView txttitulo;

    TextView txtdireccion;
    TextView txttelefono;
    TextView txtemail;
    TextView txtfechacreacion;
    TextView txtiduser;

    Button EnviarCorreo;
    Button LlamarPersona;

    ImageView closePub;
    ImageView addFav;

    String email, telefono;

    String id_publicacion;
    String id_usuario;
    private UserInfo userInfo;

    String urlUpload = "https://code-brainers.000webhostapp.com/add_pub.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previsualizar_publicacion);

        imgPrev = (ImageView) findViewById(R.id.imagenPrev);
        txtdesc = (TextView) findViewById(R.id.desc_prev);
        txttitulo = (TextView) findViewById(R.id.tituloPrev);
        addFav = (ImageView) findViewById(R.id.addFav);


        txtdireccion = (TextView) findViewById(R.id.direccion_prev);
        txttelefono = (TextView) findViewById(R.id.telefono_prev);
        txtemail = (TextView) findViewById(R.id.email_prev);
        txtfechacreacion = (TextView) findViewById(R.id.creacion_prev);
        txtiduser = (TextView) findViewById(R.id.id_usuario);

        EnviarCorreo = (Button) findViewById(R.id.btnEnviarCorreo);
        LlamarPersona = (Button) findViewById(R.id.btnLlamar);
        closePub = (ImageView) findViewById(R.id.closePubPrev);

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

        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarFavorito();
            }
        });

        EnviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        closePub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LlamarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(PrevisualizarPublicacion.this, "Número copiado : "+telefono, Toast.LENGTH_SHORT).show();


                //Copiar portapapeles
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Numero de Teléfono", telefono);
                clipboard.setPrimaryClip(clip);


                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+telefono));

                if (ActivityCompat.checkSelfPermission(PrevisualizarPublicacion.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

    }

    protected void sendEmail() {
        String[] TO = {email}; //aquí pon tu correo
        String[] CC = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        // Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Escribe aquí tu mensaje");

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(PrevisualizarPublicacion.this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }

    public void AgregarFavorito(){
        /*
        *
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PrevisualizarPublicacion.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PrevisualizarPublicacion.this, "Error: "+error.toString(), Toast.LENGTH_LONG).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(PrevisualizarPublicacion.this);
        requestQueue.add(stringRequest);

    }


}
