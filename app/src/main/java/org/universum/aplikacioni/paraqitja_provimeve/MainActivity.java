package org.universum.aplikacioni.paraqitja_provimeve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.AppController;
import helper.SQLiteHandler;
import helper.SessionManager;

public class MainActivity extends AppCompatActivity {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private Button mButtonLogin;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    private SQLiteHandler objHandler;
    private SessionManager session;

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonLogin = (Button) findViewById(R.id.mButtonLogin);
        mEditTextEmail = (EditText) findViewById(R.id.mEditTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.mEditTextPassword);

        objHandler = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, StudentActivity.class);
            startActivity(intent);
            finish();
        }
        mButtonLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = mEditTextEmail.getText().toString().trim();
                        String password = mEditTextPassword.getText().toString().trim();

                        if (!email.isEmpty() && !password.isEmpty()) {
                            checkLogin(email, password);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Ju lutem mbushini te gjitha fushat!", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                });
    }

    private void checkLogin(final String email, final String password) {
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Login", "Login Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        session.setLogin(true);

                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String emri = user.getString("u_emri");
                        String mbiemri = user.getString("u_mbiemri");
                        String email = user.getString("u_email");
                        String kampusi = user.getString("u_kampusi");
                        String departamenti = user.getString("u_departamenti");
                        String indeksi = user.getString("u_indeksi");
                        String tipi = user.getString("u_tipi");
                        String datelindja = user.getString("u_datelindja");
                        String updated_at = user.getString("u_updatedAt");
                        String created_at = user.getString("u_createdAt");

                        objHandler.addUser(emri, mbiemri, email, uid, kampusi, departamenti, indeksi, tipi, datelindja, updated_at, created_at);

                        if (tipi.equals("1")) {

                            Intent intent = new Intent(MainActivity.this, AdministrataActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (tipi.equals("2")) {

                            Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                            Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", sha1Hash(password));

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    String sha1Hash(String toHash) {
        String hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = toHash.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            hash = bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
