package org.universum.aplikacioni.paraqitja_provimeve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.AppConfig;
import app.AppController;
import helper.SQLiteHandler;
import helper.SessionManager;
import model.Lenda;

public class ProvimetActivity extends Activity implements View.OnClickListener {
    ArrayAdapter<String> mAdapterLendet;
    String[] listaLendeve;
    String id, departamenti;
    JSONObject mJsonLendet;
    ArrayList<String> selectedItems;
    SQLiteHandler objHandler;
    private ImageView mImageButtonProfile;
    private ImageView mImageButtonProvimet;
    private ImageView mImageButtonLogout;
    private CheckedTextView mCheckText;
    private String mUserId;
    private String mUserDepartmentId;
    private Button mButtonSubmit;
    private ListView mListViewLendet;
    private List<Lenda> mListaLendeve;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provimet);


        mImageButtonProfile = (ImageView) findViewById(R.id.mImageButtonProfile);
        mImageButtonProvimet = (ImageView) findViewById(R.id.mImageButtonProvimet);
        mImageButtonLogout = (ImageView) findViewById(R.id.mImageButtonLogout);
        mButtonSubmit = (Button) findViewById(R.id.mButtonSubmit);
        mListViewLendet = (ListView) findViewById(R.id.mListViewLendet);
        mCheckText = (CheckedTextView) findViewById(R.id.mCheckText);

        objHandler = new SQLiteHandler(getApplicationContext());

        final HashMap<String, String> userDetails = objHandler.getUserDetails();
        mUserId = userDetails.get("id");
        mUserDepartmentId = userDetails.get("departamenti");

        mListaLendeve = new ArrayList<Lenda>();

        populloLendet(mUserId, mUserDepartmentId);

        mButtonSubmit.setOnClickListener(this);

        mImageButtonProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(ProvimetActivity.this, StudentActivity.class);
                        startActivity(objIntent);
                    }
                });
        mImageButtonProvimet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(ProvimetActivity.this, ProvimetActivity.class);

                        startActivity(objIntent);
                    }
                });
        mImageButtonLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(ProvimetActivity.this, InfoActivity.class);
                        startActivity(objIntent);
                    }
                });
    }

    private void populloLendet(final String id, final String departamenti) {
        String tag_string_req = "req_kerko_lendet";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_KERKO_LENDET, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Kerko lendet", "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        JSONArray lendet = jObj.getJSONArray("lendet");
                        listaLendeve = new String[lendet.length()];


                        for (int i = 0; i < lendet.length(); i++) {

                            JSONObject lenda = lendet.getJSONObject(i);

                            listaLendeve[i] = lenda.getString("l_emri");
                            System.out.print("Lista e lendeve" + listaLendeve[i]);

                            mListaLendeve.add(new Lenda(lenda.getString("l_id"), lenda.getString("l_emri")));
                        }
                        System.out.print("Lista e lendeve" + listaLendeve);
                        setAdapter();
                        mListViewLendet.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        mListViewLendet.setAdapter(mAdapterLendet);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Kerko lendet ", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("departamenti", departamenti);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void setAdapter() {

        mAdapterLendet = new ArrayAdapter<String>(this, R.layout.adapter_checkedtextview, listaLendeve);
    }

    @Override
    public void onClick(View view) {
        SparseBooleanArray checked = mListViewLendet.getCheckedItemPositions();
        selectedItems = new ArrayList<>();
        Boolean notSureChecked = false;
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)) {
                selectedItems.add(mListaLendeve.get(position).getIdLenda());
            }
        }
        if (selectedItems.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Zgjedhe njeren nga lendet!", Toast.LENGTH_SHORT).show();
        } else {
            mJsonLendet = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            try {
                mJsonLendet.put("id", mUserId);

                for (int i = 0; i < selectedItems.size(); i++) {
                    JSONObject jObj = new JSONObject();
                    try {
                        jObj.put("lendaId", selectedItems.get(i));
                        System.out.println(selectedItems);
                        jsonArray.put(jObj);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    mJsonLendet.put("", jsonArray);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            saveDataToServer(mJsonLendet);
        }
    }

    private void saveDataToServer(final JSONObject mJsonLendet) {

        String tag_string_req = "req_provimet_paraqitura";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_KERKO_PROVIMET_PARAQITURA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Kerko provimet", "Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Intent objIntent = new Intent(ProvimetActivity.this, ProvimetStuActivity.class);
                        startActivity(objIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Kerko provimet ", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                int i = 0;
                for (String object : selectedItems) {
                    params.put("lendaId[" + (i++) + "]", object);
                }
                params.put("userId", mUserId);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
   