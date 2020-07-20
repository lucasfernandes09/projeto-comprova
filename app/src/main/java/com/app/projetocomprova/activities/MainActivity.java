package com.app.projetocomprova.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.app.projetocomprova.NotificacaoWorker;
import com.app.projetocomprova.R;
import com.app.projetocomprova.fragments.ArquivosFragment;
import com.app.projetocomprova.fragments.ContatoFragment;
import com.app.projetocomprova.fragments.FAQsFragment;
import com.app.projetocomprova.fragments.HomeFragment;
import com.app.projetocomprova.fragments.ParceirosFragment;
import com.app.projetocomprova.fragments.SobreFragment;
import com.app.projetocomprova.fragments.WhatsappFragment;
import com.app.projetocomprova.model.Artigos;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //actionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_comprova_logo);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        //navigationView
        drawerLayout =  findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        iniciarHome();
        notificacaoPeriodica();

    }

    private void iniciarHome() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentLayout, homeFragment);
        transaction.commit();
    }

    private void notificacaoPeriodica() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest pwRequest =
                new PeriodicWorkRequest.Builder(NotificacaoWorker.class, 12, TimeUnit.HOURS, 1, TimeUnit.HOURS)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork("myUniqueWork", ExistingPeriodicWorkPolicy.KEEP, pwRequest);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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

        }else if(id == R.id.nav_contato) {
            ContatoFragment contatoFragment = new ContatoFragment();
            transaction.replace(R.id.contentLayout, contatoFragment);
            transaction.commit();

        }else if(id == R.id.nav_parceiros) {
            ParceirosFragment parceirosFragment = new ParceirosFragment();
            transaction.replace(R.id.contentLayout, parceirosFragment);
            transaction.commit();

        } else if (id == R.id.nav_arquivos) {
            /*ArquivosFragment arquviosFragment = new ArquivosFragment();
            transaction.replace(R.id.contentLayout, arquviosFragment);
            transaction.commit();*/
            Toast.makeText(MainActivity.this, "Em breve", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
