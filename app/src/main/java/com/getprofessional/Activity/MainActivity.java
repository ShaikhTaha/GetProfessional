package com.getprofessional.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.getprofessional.Adapters.NavigationRVAdapter;
import com.getprofessional.Fragments.MainFragment;
import com.getprofessional.R;
import com.getprofessional.models.GP_BG_Data;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    DrawerLayout drawerLayout;
    RecyclerView recyclerView, mfRecyclerView;
    RecyclerView.Adapter adapter, mfAdapter;
    RecyclerView.LayoutManager layoutManager, mfLayoutManager;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    ArrayList<String> arrayList = new ArrayList<>();
    TextView customerName,customerEmail;
    GP_BG_Data DB ;
    String[] NameAndEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = new GP_BG_Data(this);
        //Start Of Navigation & Nav-RecyclerView Code.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NameAndEmail = DB.name_email();

        customerName = findViewById(R.id.navCustomerName);
        customerEmail =findViewById(R.id.navCustomeEmail);
        recyclerView = (RecyclerView) findViewById(R.id.navigation_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        customerName.setText(NameAndEmail[0]);
        customerEmail.setText(NameAndEmail[1]);

        String[] items = getResources().getStringArray(R.array.nav_items);
        for(String Item : items){
            arrayList.add(Item);
        }

        adapter = new NavigationRVAdapter(arrayList,this,drawerLayout);
        recyclerView.setAdapter(adapter);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //End Of Navigation & Nav-RecyclerView Code.

        //Start Of MainFragment Code.
        MainFragment mainFragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentLayout,mainFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
