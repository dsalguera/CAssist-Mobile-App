package com.code.cassist.Interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import prefs.UserInfo;

import android.Manifest;
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

public class CrearPublicacion extends AppCompatActivity {

    Button btnElegir;
    Button enviarPub;
    ImageView imagenPub, cerrarP;
    Bitmap bitmap;
    EditText txttitulo, txtdescripcion, txttelefono, txtdireccion, txtemail;
    UserInfo userInfo;
    String urlUpload = "https://code-brainers.000webhostapp.com/register_pub.php";

    final int CODE_GALERY_REQUEST = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_publicacion);

        imagenPub = (ImageView) findViewById(R.id.imagenPub);
        cerrarP = (ImageView) findViewById(R.id.closePub);
        btnElegir = (Button) findViewById(R.id.btnElegir);
        enviarPub = (Button) findViewById(R.id.btnEnviar);

        txttitulo = (EditText) findViewById(R.id.txttituloP);
        txtdescripcion  = (EditText) findViewById(R.id.txtdescP);
        txttelefono  = (EditText) findViewById(R.id.txtcelP);
        txtdireccion  = (EditText) findViewById(R.id.txtdireccionP);
        txtemail  = (EditText) findViewById(R.id.txtcorreoP);

        userInfo = new UserInfo(getApplicationContext());

        cerrarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnElegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        CrearPublicacion.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE_GALERY_REQUEST
                );
            }
        });

        enviarPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagenPub.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.noimagen).getConstantState()){
                    Toast.makeText(CrearPublicacion.this, "Necesita seleccionar una imagen.", Toast.LENGTH_SHORT).show();
                }else{
                    if(txttitulo.getText().toString().equals("") ||
                        txtdescripcion.getText().toString().equals("") ||
                        txttelefono.getText().toString().equals("") ||
                        txtdireccion.getText().toString().equals("") ||
                        txtemail.getText().toString().equals("")){
                        Toast.makeText(CrearPublicacion.this, "Necesita rellenar todos los campos.", Toast.LENGTH_SHORT).show();
                    }else{
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(CrearPublicacion.this, response, Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener(){

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(CrearPublicacion.this, "Error: "+error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                String imageData = imageToString(bitmap);

                                params.put("titulo",txttitulo.getText().toString());
                                params.put("imagen",imageData);
                                params.put("descripcion",txtdescripcion.getText().toString());
                                params.put("telefono",txttelefono.getText().toString());
                                params.put("direccion",txtdireccion.getText().toString());
                                params.put("email",txtemail.getText().toString());
                                params.put("id_usuario",userInfo.getKEY_idusuario());

                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(CrearPublicacion.this);
                        requestQueue.add(stringRequest);
                    }
                }
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
                imagenPub.setImageBitmap(bitmap);

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
}
