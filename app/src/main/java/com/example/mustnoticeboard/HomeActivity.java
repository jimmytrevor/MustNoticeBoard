package com.example.mustnoticeboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    //Declaring the Action bar Drawer Toogle
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView=findViewById(R.id.drawable_nav);

        drawerLayout=findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);//The on and Off Definitions
        drawerLayout.addDrawerListener(mToggle);//Adding A listener to the Drawer Layout
        //Syncing the Toggle Button
        mToggle.syncState();
        setFragemt(new AboutFragment());

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       //setting default fragment on Activity creation

        //setting an onclick listener on the navigation view
       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               int click=menuItem.getItemId();
               switch (click){
                   case R.id.help:
                       setFragemt(new HelpFragment());
                       return true;
                   case R.id.share:
                       Intent intent=new Intent(Intent.ACTION_SEND);
                       intent.setType("text/plain");
                       intent.putExtra("MY APP",true);

                       startActivity(Intent.createChooser(intent,"SHARE THE APP NOW"));
                       Toast.makeText(getApplicationContext(),"sharing the app",Toast.LENGTH_SHORT).show();
                       return true;
                   case R.id.about:
                       setFragemt(new AboutFragment());
                       return true;
                   case R.id.privacy:
                       return true;
                   case R.id.logout:
                      final AlertDialog.Builder alertLogout=new AlertDialog.Builder(HomeActivity.this);
                      alertLogout.setTitle("Must Computing Service Unit");
                      alertLogout.setCancelable(false);
                      alertLogout.setMessage("Confirm that you want to logout");
                      alertLogout.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.cancel();
                          }
                      });
                      alertLogout.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              HomeActivity.this.finish();
                              startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                          }
                      });
                      AlertDialog alert= alertLogout.create();
                      alert.show();
                       return true;
                       default:
                           return false;
               }
           }
       });
    }

    public void setFragemt(Fragment fragemt){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragemt).commit();
    }
}
