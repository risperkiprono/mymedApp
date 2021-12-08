package com.example.mymedapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceHelper {
    Context context;
    SharedPreferences pref;
    public PreferenceHelper(Context context) {
        this.context = context;
    }

    public void saveUserEmail(String email)
    {
      pref=context.getSharedPreferences("user",MODE_PRIVATE);
      SharedPreferences.Editor editor=pref.edit();
      editor.putString("email",email);
      editor.commit();
    }

    public String getUserEmail(){
        pref=context.getSharedPreferences("user", MODE_PRIVATE);
        String email=pref.getString("email","");
        return  email;
    }

    public void saveAdminValues(String email)
    {
        pref=context.getSharedPreferences("admin",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("email",email);
        editor.commit();
    }

    public String getAdminEmail(){
        pref=context.getSharedPreferences("admin", MODE_PRIVATE);
        String email=pref.getString("email","");
        return  email;
    }
}
