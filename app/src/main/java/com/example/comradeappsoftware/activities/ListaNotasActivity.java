package com.example.comradeappsoftware.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.activities.ProductoModulo1.ListaProductosActivity;
import com.example.comradeappsoftware.adapters.ListaOrdersAdapter;
import com.example.comradeappsoftware.adapters.ListaProductoAdapter;
import com.example.comradeappsoftware.models.OrderDetails;
import com.example.comradeappsoftware.models.Orders;
import com.example.comradeappsoftware.models.VariablesGlobales;
import com.example.comradeappsoftware.services.ApiService;
import com.example.comradeappsoftware.services.ApiServiceGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaNotasActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
public static String TAG = ListaNotasActivity.class.getSimpleName();
    private GridView gridView;
    private SharedPreferences sharedPreferences;
    private int id_user;
    SearchView searchView;
    private List<Orders> orderDetails;
    ListaOrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        gridView = findViewById(R.id.grid_orders);
        gridView.setAdapter(new ListaOrdersAdapter(this));
searchView = findViewById(R.id.search_view);

searchView.setOnQueryTextListener(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        id_user = sharedPreferences.getInt("id_user",0);
        ListaProducto();
    }

    public void GoListaProductoXNota(View view){
        startActivity(new Intent(ListaNotasActivity.this, ListaProductoxNotasActivity.class));

    }

    public void ListaProducto(){
        ApiService api = ApiServiceGenerator.createService(ApiService.class);
        //VariablesGlobales.getId_pedido()
        Call<List<Orders>> call = api.getorders(id_user);
        call.enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>>call, Response<List<Orders>> response) {
                try{
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                      orderDetails = response.body();
                        Log.d(TAG, "catador: " + orderDetails);
                       // List<OrderDetails> listaNombre = Arrays.asList(orderDetails);


                        //     textView_Producto.setText(orderDetails.getNombre());
                        //     VariablesGlobales.setId_producto_predict(orderDetails.getIdproducto());

                         adapter = (ListaOrdersAdapter) gridView.getAdapter();
                        adapter.setListaOrdersAdapter(orderDetails);
                        adapter.notifyDataSetChanged();



                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(ListaNotasActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) { }
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(ListaNotasActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        try {

            List<Orders> listFilter= filter(orderDetails,newText);
       adapter.setFilter(listFilter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
//FILTRAR POR NOMBRE DE SUPERMERCADO

    private List<Orders> filter(List<Orders> order, String texto){
        List<Orders> Listafiltrada = new ArrayList<Orders>();
        try {
texto = texto.toLowerCase();
        for (Orders or: order){
            String order2 = or.getSedes_idsede().getIdsupermercado().getNombre().toLowerCase();
            if (order2.contains(texto)){
                Listafiltrada.add(or);
            }

        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  Listafiltrada;
    }
}
