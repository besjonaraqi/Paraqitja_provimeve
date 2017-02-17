package org.universum.aplikacioni.paraqitja_provimeve;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.AppConfig;
import app.AppController;


public class ProvimetAdmActivity extends Activity {
    static final int DATE_DIALOG_ID = 999;
    static String dataFillimit;
    static String dataMbarimit;
    static boolean dataFillimitZgjedh = false;
    static boolean dataMbarimitZgjedh = false;
    static private EditText mEditTextDataFillimit, mEditTextDataMbarimit;
    Integer aktivizoAfatin = 0;
    private ImageButton mImageButtonProfile;
    private ImageButton mImageButtonProvimet;
    private ImageButton mImageButtonLogout;
    private Spinner mSpinnerMuaji;
    private Spinner mSpinnerViti;
    private Button mButtonSave;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provimet_adm);

        mImageButtonProfile = (ImageButton) findViewById(R.id.mImageButtonProfile);
        mImageButtonProvimet = (ImageButton) findViewById(R.id.mImageButtonProvimet);
        mImageButtonLogout = (ImageButton) findViewById(R.id.mImageButtonLogout);
        mSpinnerMuaji = (Spinner) findViewById(R.id.mSpinnerMuaji);
        mSpinnerViti = (Spinner) findViewById(R.id.mSpinnerViti);
        mEditTextDataFillimit = (EditText) findViewById(R.id.mEditTextDataFillimit);
        mEditTextDataMbarimit = (EditText) findViewById(R.id.mEditTextDataMbarimit);
        mButtonSave = (Button) findViewById(R.id.mButtonSave);

        addItemsOnSpinnerMuaji();
        addItemsOnSpinnerViti();

        mEditTextDataFillimit.setInputType(InputType.TYPE_NULL);
        mEditTextDataMbarimit.setInputType(InputType.TYPE_NULL);

        mEditTextDataFillimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataFillimitZgjedh = true;
                showDateTimePpickerDialog(view);
            }
        });

        mEditTextDataMbarimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataMbarimitZgjedh = true;
                showDateTimePpickerDialog(view);
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String afati = String.valueOf(mSpinnerMuaji.getSelectedItem()) + " " + String.valueOf(mSpinnerViti.getSelectedItem());
                ruajteDhenat(afati, dataFillimit, dataMbarimit);
            }

        });

        mImageButtonProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(ProvimetAdmActivity.this, AdministrataActivity.class);
                        startActivity(objIntent);
                    }
                });
        mImageButtonProvimet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(ProvimetAdmActivity.this, ProvimetAdmActivity.class);
                        startActivity(objIntent);
                    }
                });
        mImageButtonLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent objIntent = new Intent(ProvimetAdmActivity.this, InfoAdmActivity.class);
                        startActivity(objIntent);
                    }
                });
    }

    private void ruajteDhenat(final String afati, final String dataFillimit, final String dataMbarimit) {
        String tag_string_req = "req_krijo_afatin";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_KRIJO_AFATIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Krijo afatin", "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        Intent objIntent = new Intent(ProvimetAdmActivity.this, AdministrataActivity.class);
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
                Log.e("Krijo afatin", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("afati", afati);
                params.put("dataFillimit", dataFillimit);
                params.put("dataMbarimit", dataMbarimit);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void addItemsOnSpinnerMuaji() {
        List<String> list = new ArrayList<String>();
        list.add("Janar");
        list.add("Prill");
        list.add("Qershor");
        list.add("Shtator");
        list.add("Nentor");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMuaji.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinnerViti() {
        List<String> list = new ArrayList<String>();
        list.add("2017");
        list.add("2018");
        list.add("2019");
        list.add("2020");
        list.add("2021");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerViti.setAdapter(dataAdapter);
    }

    public void showDateTimePpickerDialog(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "");
    }

    @SuppressLint("ValidFragment")
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        public DatePickerFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            showDate(year, month + 1, day);
        }

        public void showDate(int year, int month, int day) {
            System.out.println("data: " + year + month + day);
            if (dataFillimitZgjedh) {
                dataFillimit = (new StringBuilder().append(year).append("-").append(month).append("-").append(day)).toString();
                mEditTextDataFillimit.setText(new StringBuilder().append(day).append(" - ").append(month).append(" - ").append(year));
                dataFillimitZgjedh = false;
            } else {
                dataMbarimit = (new StringBuilder().append(year).append("-").append(month).append("-").append(day)).toString();
                mEditTextDataMbarimit.setText(new StringBuilder().append(day).append(" - ").append(month).append(" - ").append(year));
                dataMbarimitZgjedh = false;
            }
        }
    }
}
