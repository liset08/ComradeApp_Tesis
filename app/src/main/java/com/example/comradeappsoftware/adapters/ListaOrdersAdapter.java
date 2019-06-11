package com.example.comradeappsoftware.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.activities.ListaNotasActivity;
import com.example.comradeappsoftware.activities.ListaProductoxNotasActivity;
import com.example.comradeappsoftware.activities.MenuMainActivity;
import com.example.comradeappsoftware.models.OrderDetails;
import com.example.comradeappsoftware.models.Orders;
import com.example.comradeappsoftware.models.VariablesGlobales;

import java.util.ArrayList;
import java.util.List;

public class ListaOrdersAdapter extends BaseAdapter {
    private static final String TAG = ListaOrdersAdapter.class.getSimpleName();
    public Activity activity;
    private List<Orders> orderDetails;
    private LayoutInflater mInflater;

    public ListaOrdersAdapter(Activity activity) {
        this.orderDetails = new ArrayList<>();
        this.activity = activity;

    }

    public void setListaOrdersAdapter(List<Orders> orderDetails) {
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


    @SuppressLint("ResourceAsColor")
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, parent, false);
        final Orders orderDetail = this.orderDetails.get(position);


        TextView ditritoproducto = convertView.findViewById(R.id.txt_distrito_order);
        TextView precioProducto = convertView.findViewById(R.id.txt_total_orders);
        TextView marca = convertView.findViewById(R.id.txt_sede_order);
        TextView fecha = convertView.findViewById(R.id.txt_fecha_order);
        LinearLayout backgr = convertView.findViewById(R.id.sede_color);

if (String.valueOf(orderDetail.getSedes_idsede().getIdsupermercado().getNombre()).equalsIgnoreCase("plaza vea")){
    backgr.setBackgroundColor(Color.RED);
}else {
    backgr.setBackgroundColor(R.color.colorPrimary);

}


        ditritoproducto.setText(String.valueOf(orderDetail.getSedes_idsede().getIddistrito().getNombre()));
       precioProducto.setText(String.valueOf(orderDetail.getOrderdetail().get(position).getDescuento()));
       marca.setText(String.valueOf(orderDetail.getSedes_idsede().getIdsupermercado().getNombre())); //obtner la marca dcon el id del producto
        fecha.setText(String.valueOf(orderDetail.getFec_pedido()));
          convertView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  VariablesGlobales.setId_order(orderDetail.getIdpedido());
                  VariablesGlobales.setOrders(orderDetails);
                 activity.startActivity(new Intent(activity, ListaProductoxNotasActivity.class));

              }
          });


        return convertView;
    }
    @Override
    public int getCount() {
        return this.orderDetails.size();
    }

public void setFilter(List<Orders> listOrders){
        this.orderDetails = new ArrayList<>();
        this.orderDetails.addAll(listOrders);
        notifyDataSetChanged();
}
}
