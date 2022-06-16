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
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView navigationView;
    Fragment homeFragment,catagoryFragment,profileFragment;
    FirebaseUser currentUser;
    FirebaseFirestore firestore;
    DocumentSnapshot currentUserDoc;
    boolean isSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 100);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 101);
        checkPermission(Manifest.permission.CAMERA, 102);
        checkPermission(Manifest.permission.INTERNET, 103);
        checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, 104);
        
        if(!CommonUtility.isInternetAvailable())
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();

        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        profileFragment = new ProfileFragment();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            firestore = FirebaseFirestore.getInstance();
            firestore.collection("Users").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        currentUserDoc = task.getResult();
                        if (currentUserDoc.exists()) {
                            isSeller = currentUserDoc.getBoolean("isSeller");
                            setChanges();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (!CommonUtility.isInternetAvailable()) {
                        Toast.makeText(MainActivity.this, "Please Check your Intenet Connection", Toast.LENGTH_SHORT).show();
                    }
                    Log.w("FE", "Data Fetch Error: ", e);
                }
            });
        } else {
            setChanges();
        }
        if (!isSeller) {
            homeFragment = new HomeFragment();
            catagoryFragment = new CategoryFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, homeFragment).commit();

            navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, homeFragment).commit();
                            fragment = homeFragment;
                            break;

                        case R.id.category:
                            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, catagoryFragment).commit();
                            break;

                        case R.id.profile:
                            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, profileFragment).commit();
                            break;

                    }
                    return true;
                }
            });
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        }
    }
    private void setChanges(){
        if(isSeller){
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container,profileFragment).commit();
            navigationView.setVisibility(View.GONE);
        }
    }
}