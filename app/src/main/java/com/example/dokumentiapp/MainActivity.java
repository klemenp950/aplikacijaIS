package com.example.dokumentiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView dokumenti;
    private String url = "https://dokumenti.azurewebsites.net/api/v1/dokument";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        dokumenti = (TextView) findViewById(R.id.dokument);
    }
    
    public void prikaziDokumente(View view){
        if(view != null){
            JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener){
                @Override
                public Map<String,String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ApiKey", "SecretKey");
                    return params;
                }
            };
            requestQueue.add(request);
        }
    }

    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response){
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String ime = object.getString("ime");
                    String steviloZnakov = object.getString("steviloZnakov");
                    String datum = object.getString("datum");

                    data.add(ime + " " + steviloZnakov + " " + datum);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }

            dokumenti.setText("");

            for (String row: data){
                String currentText = dokumenti.getText().toString();
                dokumenti.setText(currentText + "\n\n" + row);
            }
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };
}