package org.universum.aplikacioni.paraqitja_provimeve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.HashMap;
import helper.SQLiteHandler;
import helper.SessionManager;

public class AdministrataActivity extends Activity {

    private ImageButton mImageButtonProfile;
    private ImageButton mImageButtonAfati;
    private ImageButton mImageButtonLogout;
    private TextView mUserName;
    private TextView mUserLname;
    private TextView mUserKampusi;
    private String mUserId;
    private String mUserLName;
    private String mUserFName;
    private String mUserKAmpusi;

    SQLiteHandler objHandler;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrata);

        mImageButtonProfile = (ImageButton) findViewById(R.id.mImageButtonProfile);
        mImageButtonAfati = (ImageButton) findViewById(R.id.mImageButtonAfati);
        mImageButtonLogout = (ImageButton) findViewById(R.id.mImageButtonLogout);
        mUserName = (TextView) findViewById(R.id.mUserName);
        mUserLname = (TextView) findViewById(R.id.mUserLname);
        mUserKampusi = (TextView) findViewById(R.id.mUserKampusi);

        objHandler = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        final HashMap<String, String> user = objHandler.getUserDetails();
        mUserId = user.get("id");
        mUserFName = user.get("name");
        mUserLName = user.get("lastname");
        mUserKAmpusi = user.get("kampusi");

        mUserName.setText(mUserFName);
        mUserLname.setText(mUserLName);
        mUserKampusi.setText(mUserKAmpusi);

        mImageButtonProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(AdministrataActivity.this, AdministrataActivity.class);
                        startActivity(objIntent);
                    }
                });
        mImageButtonAfati.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(AdministrataActivity.this, ProvimetAdmActivity.class);
                        startActivity(objIntent);
                    }
                });
        mImageButtonLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(AdministrataActivity.this, InfoAdmActivity.class);
                        startActivity(objIntent);
                    }
                });
    }
}
