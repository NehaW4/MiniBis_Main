package com.example.minibis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView navigationView;
    Fragment homeFragment,catagoryFragment,profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,100);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,101);
        checkPermission(Manifest.permission.CAMERA,102);
        checkPermission(Manifest.permission.INTERNET,103);
        checkPermission(Manifest.permission.ACCESS_NETWORK_STATE,104);

        homeFragment=new HomeFragment();
        profileFragment=new ProfileFragment();
        catagoryFragment=new CategoryFragment();

        navigationView=(BottomNavigationView) findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,homeFragment).commit();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,homeFragment).commit();
                        fragment = homeFragment;
                        break;

                    case R.id.category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,catagoryFragment).commit();
                        break;

                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,profileFragment).commit();
                        break;

                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.homemenu, menu);
        return true;

    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){
                    Log.e("MyActivity", "onMenuOpened", e);
                }
                catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishAffinity();
    }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        }
    }
}