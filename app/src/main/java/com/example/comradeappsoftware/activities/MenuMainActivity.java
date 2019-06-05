package com.example.comradeappsoftware.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.comradeappsoftware.R;

public class MenuMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
    }

    public void NewLista(View view){
        startActivity(new Intent(MenuMainActivity.this, ListaProductosActivity.class));
    }
    public void ListaNotas(View view){
        startActivity(new Intent(MenuMainActivity.this, ListaNotasActivity.class));
    }
}
