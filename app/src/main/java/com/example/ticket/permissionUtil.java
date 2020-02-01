package com.example.ticket;

import android.content.Context;
import android.content.SharedPreferences;

public class permissionUtil {


    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    permissionUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.PREFERENCES_PERMISSION), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void updatePermission(String permission) {
        switch (permission) {
            case "internet":
                editor.putBoolean(context.getString(R.string.PERMISSION_INTERNET), true);
                editor.commit();
                break;
            case "sms":
                editor.putBoolean(context.getString(R.string.PERMISSION_SMS), true);
                editor.commit();
                break;
        }

    }

    public boolean checkPermission(String permission) {
        boolean isShown = false;
        switch (permission) {
            case "internet":
                isShown = sharedPreferences.getBoolean(context.getString(R.string.PERMISSION_INTERNET), false);
                break;
            case "sms":
                isShown = sharedPreferences.getBoolean(context.getString(R.string.PERMISSION_SMS), false);
                break;
        }
        return isShown;
    }
}

