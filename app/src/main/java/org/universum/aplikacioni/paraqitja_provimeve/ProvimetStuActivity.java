package org.universum.aplikacioni.paraqitja_provimeve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ProvimetStuActivity extends Activity {

    private ImageButton mImageButtonProfile;
    private ImageButton mImageButtonProvimet;
    private ImageButton mImageButtonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provimet_stu);

        mImageButtonProfile = (ImageButton) findViewById(R.id.mImageButtonProfile);
        mImageButtonProvimet = (ImageButton) findViewById(R.id.mImageButtonProvimet);
        mImageButtonLogout = (ImageButton) findViewById(R.id.mImageButtonLogout);

        mImageButtonProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(ProvimetStuActivity.this, StudentActivity.class);
                        startActivity(objIntent);
                    }
                });
        mImageButtonProvimet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(ProvimetStuActivity.this, ProvimetActivity.class);
                        startActivity(objIntent);
                    }
                });
        mImageButtonLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(ProvimetStuActivity.this, InfoStuActivity.class);
                        startActivity(objIntent);
                    }
                });
    }
}
