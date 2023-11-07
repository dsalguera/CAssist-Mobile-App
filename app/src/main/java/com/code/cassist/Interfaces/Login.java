package com.code.cassist.Interfaces;

import androidx.appcompat.app.AppCompatActivity;
import prefs.UserInfo;
import prefs.UserSession;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.code.cassist.Adaptadores.DescubrirAdapter;
import com.code.cassist.AndroidLoginController;
import com.code.cassist.Clases.PublicacionesListaDescubrir;
import com.code.cassist.Clases.Usuario;
import com.code.cassist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {

    private final static String URL_DATA = "https://code-brainers.000webhostapp.com/obtener_usuario_app.php?";

    EditText txtuser;
    EditText txtcontra;
    Button btnIniciarS;
    Button btnRegistrar;
    TextView visitanos;
    private ProgressDialog progressDialog;
    private UserSession session;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtuser = (EditText) findViewById(R.id.txtusuario);
        txtcontra = (EditText) findViewById(R.id.txtpass);

        btnIniciarS = (Button) findViewById(R.id.btnIniciarSes);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrarse);

        visitanos = (TextView) findViewById(R.id.visitanos);

        progressDialog  = new ProgressDialog(this);
        session = new UserSession(this);
        userInfo = new UserInfo(this);

        if(session.isUserLoggedin()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        btnIniciarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iniciar_Sesion();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrarse();
            }
        });

        visitanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://code-brainers.000webhostapp.com/index.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }

    void Iniciar_Sesion(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Espere unos segundos mientras obtenemos los datos...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA+"email="+txtuser.getText().toString()+"&password="+txtcontra.getText().toString(), new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("usuario");

                    for(int i = 0; i < array.length() ; i++) {

                        JSONObject jo = array.getJSONObject(i);

                        String id_usuario = new String(jo.getString("id_usuario").getBytes("ISO-8859-1"), "UTF-8");
                        String nombre_usuario = new String(jo.getString("nombre_usuario").getBytes("ISO-8859-1"), "UTF-8");
                        //String contrasena = new String(jo.getString("contraseña").getBytes("ISO-8859-1"), "UTF-8");
                        String tipoUsuario = new String(jo.getString("tipoUsuario").getBytes("ISO-8859-1"), "UTF-8");
                        String imagen = new String(jo.getString("imagen").getBytes("ISO-8859-1"), "UTF-8");
                        String primerNombre = new String(jo.getString("primerNombre").getBytes("ISO-8859-1"), "UTF-8");
                        String segundoNombre = new String(jo.getString("segundoNombre").getBytes("ISO-8859-1"), "UTF-8");
                        String primerApellido = new String(jo.getString("primerApellido").getBytes("ISO-8859-1"), "UTF-8");
                        String segundoApellido = new String(jo.getString("segundoApellido").getBytes("ISO-8859-1"), "UTF-8");
                        String email = new String(jo.getString("email").getBytes("ISO-8859-1"), "UTF-8");
                        String celular = new String(jo.getString("celular").getBytes("ISO-8859-1"), "UTF-8");
                        String telefono = new String(jo.getString("telefono").getBytes("ISO-8859-1"), "UTF-8");
                        String fecha_nac = new String(jo.getString("fecha_nac").getBytes("ISO-8859-1"), "UTF-8");
                        String creacion = new String(jo.getString("fecha_creacion_cta").getBytes("ISO-8859-1"), "UTF-8");


                        // Inserting row in users table
                        userInfo.setIDUsuario(id_usuario);
                        userInfo.setUsername(nombre_usuario);
                        userInfo.setTipoUsuario(tipoUsuario);
                        userInfo.setImagenUsuario(imagen);

                        userInfo.setPrimerNombre(primerNombre);
                        userInfo.setSegundoNombre(segundoNombre);
                        userInfo.setPrimerApellido(primerApellido);
                        userInfo.setSegundoApellido(segundoApellido);

                        userInfo.setEmail(email);
                        userInfo.setCelular(celular);
                        userInfo.setTelefono(telefono);
                        userInfo.setFechaNac(fecha_nac);
                        userInfo.setFechaCreacion(creacion);

                        session.setLoggedin(true);
                    }

                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();


                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Asegurese de introducir los datos correctamente", Toast.LENGTH_LONG).show();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Ocurrio un error de lectura del servidor, pruebe nuevamente.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    void Registrarse(){
        startActivity(new Intent(Login.this, Register.class));
    }
}
