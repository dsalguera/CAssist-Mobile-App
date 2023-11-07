package com.code.cassist.Interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.code.cassist.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    Button btnCancelar;
    Button btnCrearCuenta;
    Bitmap bitmap;
    Button btnElegirImg;
    ImageView imgUser;

    EditText primerNombre, segundoNombre, primerApellido, segundoApellido, usuarioCorreo, contrasena, correo, celular, telefono, fechaNac;

    String urlUpload = "https://code-brainers.000webhostapp.com/register_user.php";
    final int CODE_GALERY_REQUEST = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnCancelar = (Button) findViewById(R.id.btnRegresar);
        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);
        btnElegirImg = (Button) findViewById(R.id.btnElegirUs);

        imgUser = (ImageView) findViewById(R.id.imageUser);

        primerNombre = (EditText) findViewById(R.id.txtprimerNombre);
        segundoNombre = (EditText) findViewById(R.id.txtsegundoNombre);
        primerApellido = (EditText) findViewById(R.id.txtprimerApellido);
        segundoApellido = (EditText) findViewById(R.id.txtsegundoApellido);

        usuarioCorreo = (EditText) findViewById(R.id.txtnombreuser);
        contrasena = (EditText) findViewById(R.id.txtpassU);
        correo = (EditText) findViewById(R.id.txtcorreo);
        telefono = (EditText) findViewById(R.id.txttelefono);
        celular = (EditText) findViewById(R.id.txtcelular);
        fechaNac = (EditText) findViewById(R.id.fechaNac);

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgUser.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.noimagen).getConstantState()){
                    Toast.makeText(Register.this, "Necesita seleccionar una imagen.", Toast.LENGTH_SHORT).show();
                }else {
                    if (usuarioCorreo.getText().toString().equals("") ||
                            contrasena.getText().toString().equals("") ||
                            primerNombre.getText().toString().equals("") ||
                            segundoNombre.getText().toString().equals("") ||
                            primerApellido.getText().toString().equals("") ||
                            segundoApellido.getText().toString().equals("") ||
                            correo.getText().toString().equals("") ||
                            telefono.getText().toString().equals("") ||
                            celular.getText().toString().equals("") ||
                            fechaNac.getText().toString().equals("")) {
                        Toast.makeText(Register.this, "Necesita rellenar todos los campos.", Toast.LENGTH_SHORT).show();
                    } else {
                        IngresarUser();
                    }
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnElegirImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        Register.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE_GALERY_REQUEST
                );
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == CODE_GALERY_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"),CODE_GALERY_REQUEST);
            }else{
                Toast.makeText(this, "No tiene permiso para acceder a la galería.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == CODE_GALERY_REQUEST && resultCode == RESULT_OK && data != null ){
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgUser.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encondedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return  encondedImage;
    }

    public void IngresarUser(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Espere unos segundos mientras creamos la cuenta");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Register.this, response, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Register.this, "Error: "+error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String imageData = imageToString(bitmap);

                params.put("nombre_usuario",usuarioCorreo.getText().toString());
                params.put("contrasena",contrasena.getText().toString());
                params.put("tipoUsuario","3");
                params.put("imagen",imageData);

                /*
                * Esta parte hay que trabajarla
                * */
                params.put("primerNombre",primerNombre.getText().toString());
                params.put("segundoNombre",segundoNombre.getText().toString());
                params.put("primerApellido",primerApellido.getText().toString());
                params.put("segundoApellido",segundoApellido.getText().toString());

                /*
                *
                * */
                params.put("email",correo.getText().toString());
                params.put("celular",celular.getText().toString());
                params.put("telefono",telefono.getText().toString());
                params.put("fecha_nac",fechaNac.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(stringRequest);

    }
}
