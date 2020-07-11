package com.example.permissionTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 125;
    private static final int REQUEST_STORAGE = 225;
    private static final int REQUEST_CONTACTS = 325;
    private static final int REQUEST_GROUP_PERMISSIONS = 425;

    private static final int TXT_CAMERA = 1;
    private static final int TXT_STORAGE = 2;
    private static final int TXT_CONTACTS = 3;

    PermissionUtil permissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionUtil = new PermissionUtil(this);
    }

    int checkPermission(int permission){
        int status = PackageManager.PERMISSION_DENIED;
        switch(permission){
            case TXT_CAMERA:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                break;
            case TXT_STORAGE:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case TXT_CONTACTS:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                break;
        }
        return status;
    }

    private void requestPermission(int permission){
        switch(permission){
            case TXT_CAMERA:
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
                break;
            case TXT_STORAGE:
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_STORAGE);
                break;
            case TXT_CONTACTS:
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CONTACTS);
                break;
        }
    }

    private void showPermissionExplanation(final int permission){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (permission == TXT_CAMERA){
            builder.setMessage("This app need the access of camera to function properly");
            builder.setTitle("Camera Permission Needed");
        }
        else if (permission == TXT_CONTACTS){
            builder.setMessage("This app need the access of contacts to function properly");
            builder.setTitle("Contacts Permission Needed");
        }
        else if (permission == TXT_STORAGE){
            builder.setMessage("This app need the access of storage to function properly");
            builder.setTitle("Storage Permission Needed");
        }

        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(permission==TXT_CAMERA)
                    requestPermission(TXT_CAMERA);
                else if (permission==TXT_STORAGE)
                    requestPermission(TXT_STORAGE);
                else if (permission==TXT_CONTACTS)
                    requestPermission(TXT_CONTACTS);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void openCamera(View view) {
        if(checkPermission(TXT_CAMERA)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA)){
                showPermissionExplanation(TXT_CAMERA);
            }
            else if(permissionUtil.checkPermissionPreferences("camera")){
                requestPermission(TXT_CAMERA);
                permissionUtil.updatePermissionPreferences("camera");
            }
            else{
                Toast.makeText(this, "Please allow camera permission in your app settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        }
        else{
            Toast.makeText(this, "You have camera permission", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ResultActivity.class);
            intent.putExtra("message","You can now take photos and record videos");
            this.startActivity(intent);
        }
    }

    public void openStorage(View view) {
        if(checkPermission(TXT_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                showPermissionExplanation(TXT_STORAGE);
            }
            else if(permissionUtil.checkPermissionPreferences("storage")){
                requestPermission(TXT_STORAGE);
                permissionUtil.updatePermissionPreferences("storage");
            }
            else{
                Toast.makeText(this, "Please allow storage permission in your app settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        }
        else{
            Toast.makeText(this, "You have storage permission", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ResultActivity.class);
            intent.putExtra("message","You can now take photos and record videos");
            this.startActivity(intent);
        }
    }

    public void openContacts(View view) {
        if(checkPermission(TXT_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_CONTACTS)){
                showPermissionExplanation(TXT_CONTACTS);
            }
            else if(permissionUtil.checkPermissionPreferences("contacts")){
                requestPermission(TXT_CONTACTS);
                permissionUtil.updatePermissionPreferences("contacts");
            }
            else{
                Toast.makeText(this, "Please allow contacts permission in your app settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        }
        else{
            Toast.makeText(this, "You have contacts permission", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ResultActivity.class);
            intent.putExtra("message","You can now take photos and record videos");
            this.startActivity(intent);
        }
    }

    public void getAllPermissions(View view) {
        ArrayList<String> permissionNeeded = new ArrayList<>();
        ArrayList<String> permissionAvailable = new ArrayList<>();
        permissionAvailable.add(Manifest.permission.CAMERA);
        permissionAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionAvailable.add(Manifest.permission.READ_CONTACTS);

        for (String permission : permissionAvailable){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                permissionNeeded.add(permission);
        }

        requestGroupPermission(permissionNeeded);
    }

    private void requestGroupPermission(ArrayList<String> permission){
        if (permission.isEmpty()){
            Toast.makeText(this, "All the requests have been granted, no additional request required", Toast.LENGTH_SHORT).show();
        }
        else {
            String[] permissionList = new String[permission.size()];
            permission.toArray(permissionList);

            ActivityCompat.requestPermissions(MainActivity.this, permissionList,REQUEST_GROUP_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CAMERA:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "You have camera permission", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra("message", "You can now take pics");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "Camera permission turned off", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_STORAGE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "You have storage permission", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra("message", "You can now store utilities");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "Storage permission turned off", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CONTACTS:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "You have contacts permission", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra("message", "You can now contact people");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "Contacts permission turned off", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_GROUP_PERMISSIONS:
                StringBuilder result = new StringBuilder();
                int i=0;
                for(String perm:permissions){
                    String status;
                    if(grantResults[i]==PackageManager.PERMISSION_GRANTED)
                        status = "Granted";
                    else
                        status = "Denied";
                    result.append("\n").append(perm).append(" : ").append(status);
                    i++;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Group Permission Details: ");
                builder.setMessage(result.toString());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}