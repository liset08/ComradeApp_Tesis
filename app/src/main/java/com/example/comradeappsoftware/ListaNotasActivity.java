package com.example.comradeappsoftware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
    }

    public void GoListaProductoXNota(View view){
        startActivity(new Intent(ListaNotasActivity.this, ListaProductoxNotasActivity.class));

    }
}
