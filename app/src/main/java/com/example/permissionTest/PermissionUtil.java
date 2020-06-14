package com.example.permissionTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

class PermissionUtil {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    PermissionUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.permission_preferences),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    void updatePermissionPreferences(String permission){
        switch (permission){
            case "camera":
                editor.putBoolean(context.getString(R.string.permission_camera),true);
                editor.commit();
                break;
            case "contacts":
                editor.putBoolean(context.getString(R.string.permission_contacts),true);
                editor.commit();
                break;
            case "storage":
            editor.putBoolean(context.getString(R.string.permission_storage),true);
            editor.commit();
            break;
        }
    }

    boolean checkPermissionPreferences(String permission){
        boolean isShown = false;
        switch(permission){
            case "camera":
                isShown = sharedPreferences.getBoolean(context.getString(R.string.permission_camera),false);
                break;
            case "contacts":
                isShown = sharedPreferences.getBoolean(context.getString(R.string.permission_contacts),false);
                break;
            case "storage":
                isShown = sharedPreferences.getBoolean(context.getString(R.string.permission_storage),false);
                break;
        }
        return isShown;
    }
}
