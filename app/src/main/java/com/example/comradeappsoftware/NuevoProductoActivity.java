package com.example.comradeappsoftware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NuevoProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);
    }

    public void BackList(View view){
        startActivity(new Intent(NuevoProductoActivity.this, ListaProductosActivity.class));
    }
}
