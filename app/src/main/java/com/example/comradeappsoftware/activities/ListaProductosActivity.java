package com.example.comradeappsoftware.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.comradeappsoftware.R;

public class ListaProductosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
    }
    public void NewProduct(View view){
        startActivity(new Intent(ListaProductosActivity.this, NuevoProductoActivity.class));
    }
}