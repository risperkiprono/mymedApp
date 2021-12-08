package com.example.mymedapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {
TextView text1;
Toolbar toolbar;
LinearLayout searchContainer;
EditText toolbarSearchView;
ImageView searchClearButton,hosp,lab,doccon,video;
ArrayAdapter arrayAdapter;
ArrayList<String>stringArrayList=new ArrayList<>();
ArrayAdapter<String>Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);
        text1 = findViewById(R.id.text1);
        toolbar = findViewById(R.id.toolbar);
        hosp = findViewById(R.id.hosp);
        lab = findViewById(R.id.lab);
        doccon=findViewById(R.id.doccon);
        video=findViewById(R.id.video);

        hosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GoogleMap.class);
                startActivity(intent);
            }
        });

        lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Google_Location.class);
                startActivity(intent);
            }
        });
       video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),VideoChatViewActivity.class);
                startActivity(intent);
            }
        });

        List<String>list_view=new ArrayList<>();
        list_view.add("Doctors");list_view.add("hospitals");list_view.add("specialist");
        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
     //.
        // ListView.setAdapter(ArrayAdapter)
        setSupportActionBar(toolbar);
        // t up search container view
        searchContainer = new LinearLayout(this);
        Toolbar.LayoutParams containerParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        containerParams.gravity = Gravity.CENTER_VERTICAL;
        searchContainer.setLayoutParams(containerParams);


        //Setup search view
        toolbarSearchView = new EditText(this);
        //Set width/height/gravity
        int[] textSizeAttr = new int[]{android.R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(new TypedValue().data, textSizeAttr);
        int actionBarHeight = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, actionBarHeight);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.weight = 1;
        toolbarSearchView.setLayoutParams(params);


        //Setup display
        toolbarSearchView.setBackgroundColor(Color.TRANSPARENT);
        toolbarSearchView.setPadding(2, 0, 0, 0);
        toolbarSearchView.setTextColor(Color.WHITE);
        toolbarSearchView.setGravity(Gravity.CENTER_VERTICAL);
        toolbarSearchView.setSingleLine(true);
        toolbarSearchView.setImeActionLabel("Search", EditorInfo.IME_ACTION_UNSPECIFIED);
        toolbarSearchView.setHint("Search");
        toolbarSearchView.setHintTextColor(Color.parseColor("#b3ffffff"));
        try {
            //Set cursor colour to white
            Field f = TextView.class.getDeclaredField("mCursorDrawbleRes");
            f.setAccessible(true);
            f.set(toolbarSearchView, R.drawable.edittext_whitecursor);

        } catch (Exception ignored) {

        }
        //Search text changed listener

        toolbarSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //    Fragment mainFragment=getFragmentManager().findFragmentById(R.id.container);
                //   if(mainFragment !=null && mainFragment instanceof MainListFragment)
                //    ((MainListFragment) mainFragment).search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <= 0) {
                    toolbarSearchView.setHintTextColor(Color.parseColor("#b3ffffff"));
                }
            }
        });
        ((LinearLayout) searchContainer).addView(toolbarSearchView);
        //Set up clear button
        searchClearButton = new ImageView(this);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
        LinearLayout.LayoutParams clearparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clearparams.gravity = Gravity.CENTER;
        searchClearButton.setLayoutParams(clearparams);
        searchClearButton.setImageResource(R.drawable.ic_baseline_close_24);
        searchClearButton.setPadding(px, 0, px, 0);
        searchClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarSearchView.setText("");
            }
        });
        ((LinearLayout) searchContainer).addView(searchClearButton);
        //Add search view to toolbar and hide it
        searchContainer.setVisibility(View.GONE);

        doccon.setOnClickListener(v ->

                startActivity(new Intent(getApplicationContext(), Findandbook.class))
        );


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu;adds items to toolbar if it is present.
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search);
      SearchView searchView=(SearchView) menuItem.getActionView();
   //   SearchView.setQueryHint("Search Here!");

     // SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        //  @Override
       //   public boolean onQueryTextSubmit(String query) {
       //       return false;
       //   }

       //   @Override
        //  public boolean onQueryTextChange(String newText) {
          //    arrayAdapter.getFilter().filter(query);
           //   return true;
       //   }
     // });
        return true;
    }
    //handles clicks on toolbar
//implementing share button
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