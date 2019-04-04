package com.example.mustnoticeboard;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    //Declaring the Action bar Drawer Toogle
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout=findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);//The on and Off Definitions
        drawerLayout.addDrawerListener(mToggle);//Adding A listener to the Drawer Layout
        //Syncing the Toggle Button
        mToggle.syncState();
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
