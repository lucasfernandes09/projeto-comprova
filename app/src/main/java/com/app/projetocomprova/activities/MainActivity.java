package com.app.projetocomprova.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.app.projetocomprova.R;
import com.app.projetocomprova.fragments.ArquivosFragment;
import com.app.projetocomprova.fragments.FAQsFragment;
import com.app.projetocomprova.fragments.HomeFragment;
import com.app.projetocomprova.fragments.ParceirosFragment;
import com.app.projetocomprova.fragments.SobreFragment;
import com.app.projetocomprova.fragments.WhatsappFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    //private Fragment homeFragment, arquviosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_comprova_logo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        drawerLayout =  findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentLayout, homeFragment);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(id == R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.contentLayout, homeFragment);
            transaction.commit();

        } else if (id == R.id.nav_pergunte) {
            startActivity(new Intent(this, PergunteActivity.class));

        } else if (id == R.id.nav_wpp) {
            WhatsappFragment whatsappFragment = new WhatsappFragment();
            transaction.replace(R.id.contentLayout, whatsappFragment);
            transaction.commit();

        } else if (id == R.id.nav_sobre) {
            SobreFragment sobreFragment = new SobreFragment();
            transaction.replace(R.id.contentLayout, sobreFragment);
            transaction.commit();

        } else if(id == R.id.nav_faqs) {
            FAQsFragment faQsFragment = new FAQsFragment();
            transaction.replace(R.id.contentLayout, faQsFragment);
            transaction.commit();

        } else if(id == R.id.nav_parceiros) {
            ParceirosFragment parceirosFragment = new ParceirosFragment();
            transaction.replace(R.id.contentLayout, parceirosFragment);
            transaction.commit();

        } else if (id == R.id.nav_arquivos) {
            ArquivosFragment arquviosFragment = new ArquivosFragment();
            transaction.replace(R.id.contentLayout, arquviosFragment);
            transaction.commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
