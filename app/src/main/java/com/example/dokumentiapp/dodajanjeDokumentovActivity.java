package com.example.dokumentiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class dodajanjeDokumentovActivity extends AppCompatActivity {

    private TextView status;
    private EditText ime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodajanje_dokumentov);
    }
}