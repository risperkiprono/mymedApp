package com.example.mymedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.lang.String;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.appbar.AppBarLayout;

import java.io.File;
import java.util.zip.Inflater;

import static com.example.mymedapp.R.menu.menu;

public class appbar extends AppCompatActivity {
//declaring variables
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide phone toolbar

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.appbar);


       // Initializing toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //setting Back button
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
//methods to implement back button

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id == R.id.share){
            ApplicationInfo api =getApplicationContext().getApplicationInfo();
            String apkpath =api.sourceDir;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, String.valueOf(Boolean.parseBoolean(String.valueOf(Uri.fromFile(new File(apkpath))))));
            startActivity(Intent.createChooser(intent,"ShareVia"));
        }
        return true;
    }
}