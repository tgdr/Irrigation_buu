package lty.buu.irrigation_buu.activity;



import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import lty.buu.irrigation_buu.IrrigationApplication;
import lty.buu.irrigation_buu.R;
import lty.buu.irrigation_buu.fragment.frag_Envir;
import lty.buu.irrigation_buu.fragment.frag_irr_knowledge;
import lty.buu.irrigation_buu.fragment.frag_realtimesensor;
import lty.buu.irrigation_buu.http.BaseRequest;
import lty.buu.irrigation_buu.http.request.Getyz;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    IrrigationApplication app;
    FragmentManager manager;
    FragmentTransaction trans;
    Toolbar toolbar;
    Getyz yzrequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        app = (IrrigationApplication) getApplication();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        yzrequest = new Getyz(app);
        yzrequest.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
            @Override
            public void onRequestLinstener() {
                app.getSensorConfig().maxLight= (int) yzrequest.getYz_light1();
                app.getSensorConfig().minLight= (int) yzrequest.getYz_light();
                app.getSensorConfig().maxAirTemperature= (int) yzrequest.getYz_tem1();
                app.getSensorConfig().minAirTemperature= (int) yzrequest.getYz_tem();

                app.getSensorConfig().maxAirHumidity= (int) yzrequest.getYz_hum1();
                app.getSensorConfig().minAirHumidity= (int) yzrequest.getYz_hum();
                app.getSensorConfig().maxSoilHumidity= (int) yzrequest.getYz_soil1();
                app.getSensorConfig().minSoilHumidity= (int) yzrequest.getYz_soil();
            }
        });
        app.requestOneThread(yzrequest);
      //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_showdata) {
            // Handle the camera action
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            trans =  manager.beginTransaction();
            trans.setCustomAnimations(R.anim.scale_in,R.anim.scale_out);
            trans.replace(R.id.mycontent,new frag_Envir());

            trans.commit();
        } else if (id == R.id.nav_showirr) {
            startActivity(new Intent(MainActivity.this,RealTimeDataActivity.class));

           // overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        } else if (id == R.id.nav_autoctrl) {

        } else if (id == R.id.nav_irrknowledge) {
            trans =  manager.beginTransaction();
            trans.setCustomAnimations(R.anim.my_card_flip_left_in,
                    R.anim.my_card_flip_left_out, R.anim.my_card_flip_right_in,
                    R.anim.my_card_flip_right_out);
            trans.replace(R.id.mycontent,new frag_irr_knowledge());

            trans.commit();
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_explain) {

        }
        else if (id == R.id.nav_exit) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
