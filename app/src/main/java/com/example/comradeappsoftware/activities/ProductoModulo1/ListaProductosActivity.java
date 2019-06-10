package com.example.comradeappsoftware.activities.ProductoModulo1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.activities.MenuMainActivity;
import com.example.comradeappsoftware.adapters.ListaProductoAdapter;
import com.example.comradeappsoftware.models.OrderDetails;
import com.example.comradeappsoftware.models.Orders;
import com.example.comradeappsoftware.models.Product;
import com.example.comradeappsoftware.models.VariablesGlobales;
import com.example.comradeappsoftware.services.ApiService;
import com.example.comradeappsoftware.services.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaProductosActivity extends AppCompatActivity {
private static final String TAG = ListaProductosActivity.class.getSimpleName();
    private ApiService service;
    private RecyclerView recyclerView;
    public int  id_sede,id_user;
    private SharedPreferences sharedPreferences;
    private TextView total_edit,cantidad_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        service = ApiServiceGenerator.createService(ApiService.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        id_user = sharedPreferences.getInt("id_user",0);

        id_sede = VariablesGlobales.getId_sede();
        total_edit = findViewById(R.id.total_precio);
        cantidad_show = findViewById(R.id.txt_cantidad_show);
        //Listar
        recyclerView = findViewById(R.id.recyclerview_listaProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListaProductoAdapter(this));
        ListaProducto();

    }
    public void NewProduct(View view){
        startActivity(new Intent(ListaProductosActivity.this, NuevoProductoActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void ListaProducto(){
        ApiService api = ApiServiceGenerator.createService(ApiService.class);
        //VariablesGlobales.getId_pedido()
        Call<List<OrderDetails>> call = api.getOrderDetails(VariablesGlobales.getId_pedido());
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>>call, Response <List<OrderDetails>> response) {
                try{
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<OrderDetails> orderDetails = response.body();
                        Log.d(TAG, "catador: " + orderDetails);
                        //SUMA DEL PRECIO TOTAL
                        Double total = 0.00;
                        for (OrderDetails data : orderDetails) {
                            total += Double.parseDouble(data.getDescuento());
                        }
                        System.out.println(total);
                        total_edit.setText(String.valueOf(total));

                        //SUMA DE CANTIDAD DE PRODUCTOS

                        int num = 0;
                        for (OrderDetails data : orderDetails) {
                            num += data.getCantidad();
                        }
                        System.out.println(num);
                        cantidad_show.setText(String.valueOf(num));
                   //     textView_Producto.setText(orderDetails.getNombre());
                   //     VariablesGlobales.setId_producto_predict(orderDetails.getIdproducto());

                            ListaProductoAdapter adapter = (ListaProductoAdapter) recyclerView.getAdapter();
                            adapter.setListaProductoAdapter(orderDetails);
                            adapter.notifyDataSetChanged();


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(ListaProductosActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) { }
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(ListaProductosActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
              //  Toast.makeText(this, "Guardar lista...", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Seguro de guardar su lista de compra, ya no podrá realizar algun cambio?");

                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(ListaProductosActivity.this, MenuMainActivity.class));
                               finish();
                                dialog.cancel();
                            }
                        });
                builder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder.create();
                alert11.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);

                alert11.show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


}
