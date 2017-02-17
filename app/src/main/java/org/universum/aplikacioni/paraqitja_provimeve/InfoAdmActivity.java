package org.universum.aplikacioni.paraqitja_provimeve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import helper.SQLiteHandler;
import helper.SessionManager;


public class InfoAdmActivity extends Activity {
    private ImageButton mImageButtonProfile;
    private ImageButton mImageButtonProvimet;
    private ImageButton mImageButtonLogout;
    private Button mButtonLogout;

    SQLiteHandler objHandler;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mImageButtonProfile = (ImageButton) findViewById(R.id.mImageButtonProfile);
        mImageButtonProvimet = (ImageButton) findViewById(R.id.mImageButtonProvimet);
        mImageButtonLogout = (ImageButton) findViewById(R.id.mImageButtonLogout);
        mButtonLogout = (Button) findViewById(R.id.mButtonLogout);

        objHandler = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        mButtonLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutUser();
                    }
                });

        mImageButtonProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(InfoAdmActivity.this, AdministrataActivity.class);
                        startActivity(objIntent);
                    }
                });
        mImageButtonProvimet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(InfoAdmActivity.this, ProvimetAdmActivity.class);
                        startActivity(objIntent);
                    }
                });
        mImageButtonLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(InfoAdmActivity.this, InfoAdmActivity.class);
                        startActivity(objIntent);
                    }
                });
        if (!session.isLoggedIn()) {
            logoutUser();
        }
        mButtonLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        session.setLogin(false);

        objHandler.deleteUsers();

        Intent intent = new Intent(InfoAdmActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
