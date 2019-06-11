package com.example.comradeappsoftware.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.models.OrderDetails;
import com.example.comradeappsoftware.services.ApiService;
import com.example.comradeappsoftware.services.ApiServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaProductoAdapter extends RecyclerView.Adapter<ListaProductoAdapter.ViewHolder> {


    private static final String TAG = ListaProductoAdapter.class.getSimpleName();
    private Activity activity;
    private List<OrderDetails> orderDetails;

    public ListaProductoAdapter(Activity activity) {
        this.orderDetails = new ArrayList<>();
        this.activity = activity;

    }

    public void setListaProductoAdapter(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameproducto;
        public TextView precioProducto;
        public TextView marca;
        public TextView cantidad;
        public ImageView imageView,btn_delete;



        public ViewHolder(View itemView) {
            super(itemView);

            nameproducto = itemView.findViewById(R.id.etxt_nameproduct);
            precioProducto = itemView.findViewById(R.id.etxt_priceproduct);
            marca = itemView.findViewById(R.id.etxt_marcaproduct);
            cantidad = itemView.findViewById(R.id.etxt_cantiproduct);
            imageView = itemView.findViewById(R.id.image_product);
            btn_delete = itemView.findViewById(R.id.btn_delete_product);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newproducto, parent, false);
    return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final OrderDetails orderDetail = this.orderDetails.get(position);
viewHolder.nameproducto.setText(orderDetail.getIdproducto().getNombre());
        viewHolder.precioProducto.setText(String.valueOf(orderDetail.getDescuento()));
        viewHolder.marca.setText(String.valueOf(orderDetail.getIdproducto().getIdmarca())); //obtner la marca dcon el id del producto
        viewHolder.cantidad.setText(String.valueOf(orderDetail.getCantidad()));
        String url = ApiService.API_BASE_URL + "/media/images/" + orderDetail.getIdproducto().getIdproducto()+".jpg";
        Log.d("URLIMAGE",url);
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.imageView);

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                ApiService service = ApiServiceGenerator.createService(ApiService.class);

                Call<OrderDetails> call = service.destroyProducto(orderDetail.getIdpedidodeta());

                call.enqueue(new Callback<OrderDetails>() {
                    @Override
                    public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                        try {

                            int statusCode = response.code();
                            Log.d(TAG, "HTTP status code: " + statusCode);

                            if (response.isSuccessful()) {


                                // Eliminar item del recyclerView y notificar cambios
                                orderDetails.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, orderDetails.size());


                            } else {
                                Log.e(TAG, "onError: " + response.errorBody().string());
                                throw new Exception("Error en el servicio");
                            }

                        } catch (Throwable t) {
                            try {
                                Log.e(TAG, "onThrowable: " + t.toString(), t);
                                Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }catch (Throwable x){}
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderDetails> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.toString());
                        Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return this.orderDetails.size();
    }


}
