package com.example.comradeappsoftware.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.adapters.ListaOrdersAdapter;
import com.example.comradeappsoftware.adapters.ListaProductXOrderAdapter;
import com.example.comradeappsoftware.models.OrderDetails;
import com.example.comradeappsoftware.models.Orders;
import com.example.comradeappsoftware.models.VariablesGlobales;
import com.example.comradeappsoftware.services.ApiService;
import com.example.comradeappsoftware.services.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaProductoxNotasActivity extends AppCompatActivity {
    public static String TAG = ListaProductoxNotasActivity.class.getSimpleName();

    private GridView gridView;
    private SharedPreferences sharedPreferences;
    private int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productox_notas);

        gridView = findViewById(R.id.grid_orders_product);
        gridView.setAdapter(new ListaProductXOrderAdapter(this));

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        id_user = sharedPreferences.getInt("id_user",0);

        List<Orders> orderDetails = VariablesGlobales.getOrders();
        Log.d(TAG, "ORDER"+ orderDetails);

ListaProducto();
    }

    public void ListaProducto(){
        ApiService api = ApiServiceGenerator.createService(ApiService.class);
        //VariablesGlobales.getId_pedido()
        Call<Orders> call = api.getorderspedido(VariablesGlobales.getId_order());
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                try{
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Orders orderDetails = response.body();
                        Log.d(TAG, "catador: " + orderDetails);
                        // List<OrderDetails> listaNombre = Arrays.asList(orderDetails);
                        List<OrderDetails> ordersList = orderDetails.getOrderdetail();

                        //     textView_Producto.setText(orderDetails.getNombre());
                        //     VariablesGlobales.setId_producto_predict(orderDetails.getIdproducto());

                        ListaProductXOrderAdapter adapter = (ListaProductXOrderAdapter) gridView.getAdapter();
                        adapter.setListaProductXOrderAdapter(ordersList);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(ListaProductoxNotasActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) { }
                }
            }

            @Override
            public void onFailure(Call <Orders> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(ListaProductoxNotasActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
