package com.example.minibis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class seller_navigation extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_navigation);

        showHomeFragment();
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.body_container_seller,new seller_HomeFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.seller_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.seller_home:
                        fragment = new seller_HomeFragment();
                        break;

                    case R.id.addproduct:
                        fragment = new AddProduct();
                        break;

                    case R.id.seller_profile:
                        fragment = new seller_ProfileFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container_seller,fragment).commit();
                return true;
            }
        });
    }

    private void showHomeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.body_container_seller,new seller_HomeFragment()).commit();
    }
}