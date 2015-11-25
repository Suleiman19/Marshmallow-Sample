package com.grafixartist.marshmallowsample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;

import java.util.Arrays;

public class PermissionActivity extends AppCompatActivity implements OnPermissionCallback {

    final String PERMISSION = Manifest.permission.READ_CALENDAR;

    PermissionHelper permissionHelper;
    Button button;
    Toolbar toolbar;
    TextView status;

    final String DIALOG_TITLE = "Access Calendar";
    final String DIALOG_MESSAGE = "We need to access your Calendar to plan a schedule for you.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = (Button) findViewById(R.id.button);
        status = (TextView) findViewById(R.id.status);
        permissionHelper = PermissionHelper.getInstance(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permissionHelper.setForceAccepting(false).request(PERMISSION);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        status.setText("onRequestPermissionsResult() " + Arrays.toString(permissions)
                + "\nRequest Code: " + requestCode
                + "\nGrand Results: " + grantResults);

        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        status.setText("onActivityResult()");

        permissionHelper.onActivityForResult(requestCode);
    }

    @Override
    public void onPermissionGranted(String[] permissionName) {

        status.setText("onPermissionGranted() " + Arrays.toString(permissionName));

    }

    @Override
    public void onPermissionDeclined(String[] permissionName) {
        status.setText("onPermissionDeclined() " + Arrays.toString(permissionName));


    }

    @Override
    public void onPermissionPreGranted(String permissionsName) {
        status.setText("onPermissionPreGranted() " + permissionsName);

    }


    /**
     * Called when the user denied permission 1st time in System dialog
     *
     * @param permissionName
     */
    @Override
    public void onPermissionNeedExplanation(String permissionName) {
        status.setText("onPermissionNeedExplanation() " + permissionName);

        /*
        Show dialog here and ask permission again. Say why
         */

        showAlertDialog(DIALOG_TITLE, DIALOG_MESSAGE, PERMISSION);

    }

    @Override
    public void onPermissionReallyDeclined(String permissionName) {
        status.setText("onPermissionReallyDeclined() " + permissionName + "\nCan only be granted from settingsScreen");
//        Log.i("ReallyDeclined", "Permission " + permissionName + " can only be granted from settingsScreen");
    }

    @Override
    public void onNoPermissionNeeded() {
        status.setText("onNoPermissionNeeded() fallback for pre Marshmallow ");

    }


    private void showAlertDialog(String title, String message, final String permission) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        permissionHelper.requestAfterExplanation(permission);

                    }
                })
                .create();

        dialog.show();
    }

}
