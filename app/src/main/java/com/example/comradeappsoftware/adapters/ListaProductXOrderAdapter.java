package com.example.comradeappsoftware.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.models.OrderDetails;
import com.example.comradeappsoftware.models.Orders;
import com.example.comradeappsoftware.services.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListaProductXOrderAdapter extends BaseAdapter {

    private static final String TAG = ListaProductXOrderAdapter.class.getSimpleName();
    private Activity activity;
    private List<OrderDetails> orderDetails;
    private LayoutInflater mInflater;

    public ListaProductXOrderAdapter(Activity activity) {
        this.orderDetails = new ArrayList<>();
        this.activity = activity;

    }

    public void setListaProductXOrderAdapter(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }



    @Override
    public Object getItem(int position) {
        return orderDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderdetail_order, parent, false);
        final OrderDetails orderDetail = this.orderDetails.get(position);

        TextView cantproducto = convertView.findViewById(R.id.cantidad_product_order);
        TextView nameProducto = convertView.findViewById(R.id.name_product_order);
        TextView precioProducto = convertView.findViewById(R.id.price_product_order);
        ImageView imagenProducto = convertView.findViewById(R.id.img_product_order);

        String url = ApiService.API_BASE_URL + "/media/images/" + orderDetail.getIdproducto().getIdproducto()+".jpg";
        Picasso.with(convertView.getContext()).load(url).into(imagenProducto);
        nameProducto.setText(orderDetail.getIdproducto().getNombre());
        cantproducto.setText(String.valueOf(orderDetail.getCantidad()));
        precioProducto.setText(String.valueOf(orderDetail.getPrecioxunidad()));

        return convertView;
    }
    @Override
    public int getCount() {
        return orderDetails.size();
    }

}
