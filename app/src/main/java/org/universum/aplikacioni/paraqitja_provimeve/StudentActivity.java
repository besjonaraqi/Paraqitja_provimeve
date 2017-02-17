package org.universum.aplikacioni.paraqitja_provimeve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.AppController;
import helper.SQLiteHandler;
import helper.SessionManager;


public class StudentActivity extends Activity {

    private ImageButton mImageButtonProfile;
    private ImageButton mImageButtonProvimet;
    private ImageButton mImageButtonLogout;
    private String mUserId;
    private String mUserLName;
    private String mUserFName;
    private String mUserINdeksi;
    private String mUserKAmpusi;
    private String mUserDEpartamenti;
    private TextView mUserName;
    private TextView mUserLname;
    private TextView mUserIndeksi;
    private TextView mUserKampusi;

    SQLiteHandler objHandler;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        mImageButtonProfile = (ImageButton) findViewById(R.id.mImageButtonProfile);
        mImageButtonProvimet = (ImageButton) findViewById(R.id.mImageButtonProvimet);
        mImageButtonLogout = (ImageButton) findViewById(R.id.mImageButtonLogout);
        mUserName = (TextView) findViewById(R.id.mUserName);
        mUserLname = (TextView) findViewById(R.id.mUserLname);
        mUserIndeksi = (TextView) findViewById(R.id.mUserIndeksi);
        mUserKampusi = (TextView) findViewById(R.id.mUserKampusi);

        objHandler = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        final HashMap<String, String> user = objHandler.getUserDetails();
        mUserId = user.get("id");
        mUserFName = user.get("name");
        mUserLName = user.get("lastname");
        mUserINdeksi = user.get("indeksi");
        mUserKAmpusi = user.get("kampusi");
        mUserDEpartamenti = user.get("departamenti");

        mUserName.setText(mUserFName);
        mUserLname.setText(mUserLName);
        mUserIndeksi.setText(mUserINdeksi);
        mUserKampusi.setText(mUserKAmpusi);

        mImageButtonProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(StudentActivity.this, StudentActivity.class);
                        startActivity(objIntent);
                    }
                });
        mImageButtonProvimet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kerkoafatAktiv(mUserId, mUserDEpartamenti);

                    }
                });
        mImageButtonLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(StudentActivity.this, InfoActivity.class);
                        startActivity(objIntent);
                    }
                });
    }

    private void kerkoafatAktiv(final String mUserId, final String mUserDEpartamenti) {
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_KERKO_AFAT_AKTIV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Afat aktiv", "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Intent objIntent = new Intent(StudentActivity.this, ProvimetActivity.class);
                        startActivity(objIntent);
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                "Nuk mund te paraqisni provimet.Nuk eshte i hapur afati.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Afat aktiv", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
