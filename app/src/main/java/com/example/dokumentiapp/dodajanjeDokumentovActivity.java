package com.example.dokumentiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class dodajanjeDokumentovActivity extends AppCompatActivity {

    private TextView status;
    private EditText imeDokumenta;
    private EditText steviloVrstic;
    private EditText steviloZnakov;
    private EditText velikost;
    private EditText datum;
    private EditText tipID;
    private EditText avtorID;

    private RequestQueue requestQueue;
    private String url = "https://dokumenti.azurewebsites.net/api/v1/dokument";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodajanje_dokumentov);

        imeDokumenta = (EditText) findViewById(R.id.imeDokumenta);
        steviloVrstic = (EditText) findViewById(R.id.steviloVrstic);
        steviloZnakov = (EditText) findViewById(R.id.steviloZnakov);
        velikost = (EditText) findViewById(R.id.velikost);
        datum = (EditText) findViewById(R.id.datum);
        tipID = (EditText) findViewById(R.id.tipID);
        avtorID = (EditText) findViewById(R.id.avtorID);
        status = (TextView) findViewById(R.id.status);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

    }

    public void addDocument(View view){
        this.status.setText("Posting to " + url);
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("imeDokumenta", imeDokumenta.getText());
            jsonBody.put("steviloVrstic", steviloVrstic.getText());
            jsonBody.put("steviloZnakov", steviloZnakov.getText());
            jsonBody.put("velikost", velikost.getText());
            jsonBody.put("datum", datum.getText());
            jsonBody.put("tipID", tipID.getText());
            jsonBody.put("avtorID", avtorID.getText());

            final String mRequestBody = jsonBody.toString();

            status.setText(mRequestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        status.setText(responseString);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }

                @Override
                public Map<String,String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ApiKey", "SecretKey");
                    params.put("Content-Type", "application/json");
                    return params;
                }

            };

            requestQueue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}