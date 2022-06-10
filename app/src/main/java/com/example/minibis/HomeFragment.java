package com.example.minibis;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HomeFragment extends Fragment {

    Toolbar toolbar;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
   // NavigationView navigationView;
   // DrawerLayout drawerLayout;
    int[] images = {R.drawable.slider1,R.drawable.slider2,R.drawable.slider3,R.drawable.slider4};

    public HomeFragment(){

    }

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_home, container, false);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

       // drawerLayout = view.findViewById(R.id.drawerlayout);
        toolbar=(Toolbar) view.findViewById(R.id.maintoolbar);
       // navigationView = view.findViewById(R.id.sidemenu);
        viewPager = view.findViewById(R.id.viewpager1);
        viewPagerAdapter = new ViewPagerAdapter(HomeFragment.this,images);
        viewPager.setAdapter(viewPagerAdapter);

       // ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        //drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();
        return view;
    }

    public void onCreate(@NonNull Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    public void onCreateOptionMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.homemenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
   public boolean onOptionItemSelected(MenuItem item){
   // public void onListItemClick(ListView l, View v, int p, long id){

        int id = item.getItemId();

        if(id == R.id.login_icon){

           /* FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            HomeFragment llf = new HomeFragment();
            ft.replace(R.id.login_icon, llf);
            ft.commit();*/
            Intent l = new Intent(getActivity(), log_sign_options.class);
            startActivity(l);
           // return true;
        }
        return super.onContextItemSelected(item);

    }




}