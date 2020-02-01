package com.example.ticket;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    private static final int REQUST_INTERNET = 123;
    private static final int REQUST_SMS = 456;
    private static final int REQUEST_GROUP = 990;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        permission();
    }

    public void Conductor(View view) {
        startActivity(new Intent(WelcomeActivity.this, RouteActivity.class));
    }

    public void Passenger(View view) {

        startActivity(new Intent(WelcomeActivity.this, PassengerActivity.class));
    }

    private void permission() {

        ArrayList<String> permissionNeeded = new ArrayList<>();
        ArrayList<String> permissionAvailable = new ArrayList<>();
        permissionAvailable.add(Manifest.permission.INTERNET);
        permissionAvailable.add(Manifest.permission.SEND_SMS);

        for (String permission : permissionAvailable) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                permissionNeeded.add(permission);
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        REQUST_SMS);
            }
        }
        requestGroup(permissionNeeded);

    }

    private void requestGroup(ArrayList<String> permissions) {

        String[] permissionList = new String[permissions.size()];
        permissions.toArray(permissionList);
        ActivityCompat.requestPermissions(this, permissionList, REQUEST_GROUP);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUST_INTERNET:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    break;
            case REQUST_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    break;
        }
    }
}
