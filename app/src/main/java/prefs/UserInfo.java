package prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 10/1/2016.
 */

public class UserInfo {
    private static final String TAG = UserSession.class.getSimpleName();

    private static final String PREF_NAME = "userinfo";
    private static final String KEY_USERNAME = "username";

    private static final String KEY_idusuario = "idusuario";
    private static final String KEY_tipousuario = "tipousuario";
    private static final String KEY_imagen = "imagen";
    private static final String KEY_primerNombre = "primernombre";
    private static final String KEY_segundoNombre = "segundonombre";
    private static final String KEY_primerApellido = "primerapellido";
    private static final String KEY_segundoApellido = "segundoapellido";


    private static final String KEY_EMAIL = "email";
    private static final String KEY_celular = "celular";
    private static final String KEY_telefono = "telefono";

    private static final String KEY_fecha_nac = "fecha_nac";
    private static final String KEY_creacion = "fecha_creacion";


    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public UserInfo(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(PREF_NAME, ctx.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setUsername(String username){
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public void setIDUsuario(String idUsuario){
        editor.putString(KEY_idusuario, idUsuario);
        editor.apply();
    }

    public void setTipoUsuario(String tipoUsuario){
        editor.putString(KEY_tipousuario, tipoUsuario);
        editor.apply();
    }

    public void setImagenUsuario(String imagenUsuario){
        editor.putString(KEY_imagen, imagenUsuario);
        editor.apply();
    }

    /*
    * Nombres
    * */

    public void setPrimerNombre (String primerNombre){
        editor.putString(KEY_primerNombre, primerNombre);
        editor.apply();
    }

    public void setSegundoNombre(String segundoNombre){
        editor.putString(KEY_segundoNombre, segundoNombre);
        editor.apply();
    }

    public void setPrimerApellido(String primerApellido){
        editor.putString(KEY_primerApellido, primerApellido);
        editor.apply();
    }

    public void setSegundoApellido(String segundoApellido){
        editor.putString(KEY_segundoApellido, segundoApellido);
        editor.apply();
    }

    /*
    * Info basica
    * */

    public void setEmail(String email){
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public void setCelular(String celular){
        editor.putString(KEY_celular, celular);
        editor.apply();
    }

    public void setTelefono(String telefono){
        editor.putString(KEY_telefono, telefono);
        editor.apply();
    }

    public void setFechaNac(String fechaNac){
        editor.putString(KEY_fecha_nac, fechaNac);
        editor.apply();
    }

    public void setFechaCreacion(String fechaCreacion){
        editor.putString(KEY_creacion, fechaCreacion);
        editor.apply();
    }

    /*
    *
    * */

    public void clearUserInfo(){
        editor.clear();
        editor.commit();
    }

    public String getKeyUsername(){return prefs.getString(KEY_USERNAME, "");}

    public String getKEY_idusuario(){return prefs.getString(KEY_idusuario, "");}
    public String getKEY_tipousuario(){return prefs.getString(KEY_tipousuario, "");}
    public String getKEY_imagen(){return prefs.getString(KEY_imagen, "");}

    public String getKEY_primerNombre(){return prefs.getString(KEY_primerNombre, "");}
    public String getKEY_segundoNombre(){return prefs.getString(KEY_segundoNombre, "");}
    public String getKEY_primerApellido(){return prefs.getString(KEY_primerApellido, "");}
    public String getKEY_segundoApellido(){return prefs.getString(KEY_segundoApellido, "");}

    public String getKeyEmail(){return prefs.getString(KEY_EMAIL, "");}

    public String getKEY_celular(){return prefs.getString(KEY_celular, "");}
    public String getKEY_telefono(){return prefs.getString(KEY_telefono, "");}

    public String getKEY_fecha_nac(){return prefs.getString(KEY_fecha_nac, "");}
    public String getKEY_creacion(){return prefs.getString(KEY_creacion, "");}
}
