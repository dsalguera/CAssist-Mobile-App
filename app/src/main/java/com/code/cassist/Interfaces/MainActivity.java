package com.code.cassist.Interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.code.cassist.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Descubrir descubrir = new Descubrir();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hacemos que comienze con el primer fragmento
        setTitle("");

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_replace, descubrir).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(MenuItem menuItem) {
                    int id = menuItem.getItemId();

                    if (id == R.id.bottom_search){
                        setTitle("");
                        //Descubrir descubrir = new Descubrir();


                        //FragmentManager fragmentManager = getSupportFragmentManager();
                        //fragmentManager.beginTransaction().replace(R.id.fragment, descubrir).commit();

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

                        fragmentTransaction.replace(R.id.fragment_replace,descubrir).addToBackStack(null).commit();


                    }else if(id == R.id.bottom_favorites){
                        setTitle("");
                        Favoritos favoritos = new Favoritos();
                        //FragmentManager fragmentManager = getSupportFragmentManager();
                        //fragmentManager.beginTransaction().replace(R.id.fragment, favoritos).commit();

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

                        fragmentTransaction.replace(R.id.fragment_replace,favoritos).commit();

                    }else if (id == R.id.bottom_profile) {
                        setTitle("");
                        Perfil perfil = new Perfil();
                        //FragmentManager fragmentManager = getSupportFragmentManager();
                        //fragmentManager.beginTransaction().replace(R.id.fragment, perfil).commit();

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

                        fragmentTransaction.replace(R.id.fragment_replace,perfil).commit();

                    }else if(id == R.id.bottom_mypubs){
                        setTitle("");
                        MisPublicaciones misPublicaciones = new MisPublicaciones();
                        //FragmentManager fragmentManager = getSupportFragmentManager();
                        //fragmentManager.beginTransaction().replace(R.id.fragment, perfil).commit();

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

                        fragmentTransaction.replace(R.id.fragment_replace,misPublicaciones).commit();

                    }
                    return true;
            }
        });
    }



}
