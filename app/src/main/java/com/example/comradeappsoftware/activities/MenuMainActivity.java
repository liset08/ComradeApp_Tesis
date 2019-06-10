package com.example.comradeappsoftware.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.activities.ProductoModulo1.ListaProductosActivity;
import com.example.comradeappsoftware.models.Orders;
import com.example.comradeappsoftware.models.Sede;
import com.example.comradeappsoftware.models.VariablesGlobales;
import com.example.comradeappsoftware.services.ApiService;
import com.example.comradeappsoftware.services.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuMainActivity extends AppCompatActivity {

    public String longitud,latitud;
    private static final String TAG = MenuMainActivity.class.getSimpleName();
    private ApiService service;
    private SharedPreferences sharedPreferences;
    private  String name, lastname;
    public int  id_sede,id_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        service = ApiServiceGenerator.createService(ApiService.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        name = sharedPreferences.getString("nombre",null);
        lastname = sharedPreferences.getString("apellidos",null);
        id_user = sharedPreferences.getInt("id_user",0);

        TextView txt_name = findViewById(R.id.txt_name_user);
        txt_name.setText(name+""+ lastname);


    }
    public void ListaNotas(View view){
        startActivity(new Intent(MenuMainActivity.this, ListaNotasActivity.class));
    }

    public void NewLista(View view){
        //permisos
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

        } else {
            locationStart();
        }
    }

        //inicio de los permisos de localizacion
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        LocationListener Local = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

            final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }

        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        if (mlocManager != null) {
            //Existe GPS_PROVIDER obtiene ubicación
            location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if(location == null){ //Trata con NETWORK_PROVIDER
            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, Local);
            if (mlocManager != null) {
                //Existe NETWORK_PROVIDER obtiene ubicación
                location = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        if(location != null) {
            latitud = "-12.045254"; //location.getAltitude()
            longitud = "-76.953479" ;

            EnvioCoordenadas();
        //    startActivity(new Intent(MenuMainActivity.this, ListaProductosActivity.class));

        }else {
            Toast.makeText(this, "No se pudo obtener geolocalización", Toast.LENGTH_LONG).show();
        }



    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              /*  Toast.makeText(MenuMainActivity.this,  "\n Lat = "
                        + latitud + "\n Long = " + longitud, Toast.LENGTH_SHORT).show();*/
                locationStart();

                return;
            }
        }
    }


//Verificar coordenadas


    private void EnvioCoordenadas(){
        Call<Sede> call = service.obtenerCoordenadas(latitud,longitud);


        call.enqueue(new Callback<Sede>() {
            @Override
            public void onResponse(Call<Sede> call, Response<Sede> response) {
                try {
                    int statusCode = response.code();
                    Log.e(TAG, "HTTP status code : " + statusCode);

                    if (response.isSuccessful()) {

                        Sede responseMessage = response.body();

                        if (statusCode ==200){
                            VariablesGlobales.setId_sede(responseMessage.getIdsede());
                            id_sede = responseMessage.getIdsede();
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuMainActivity.this);

                            View child = getLayoutInflater().inflate(R.layout.popup_check_sede, null);
                            alertDialogBuilder.setView(child);

                            final AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            alertDialog.show();

                            Button btn_aceptar = alertDialog.findViewById(R.id.validate_map);
                            Button btn_cancel = alertDialog.findViewById(R.id.cancel_map);

                            btn_aceptar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                NewOrder();
                                }
                            });

                            btn_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });

                        }
                    }else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuMainActivity.this);

                        View child = getLayoutInflater().inflate(R.layout.popup_advertencia, null);
                        alertDialogBuilder.setView(child);

                        final AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        alertDialog.show();
                        Button btn_aceptar_info = alertDialog.findViewById(R.id.btn_aceptar_popup_info);

                        btn_aceptar_info.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();

                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(MenuMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onThrowable: " + e.toString(), e);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(MenuMainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void NewOrder(){
        Call<Orders> call = service.CreatePedido("2019-03-16",id_user,id_sede);


        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                try {
                    int statusCode = response.code();
                    Log.e(TAG, "HTTP status code : " + statusCode);

                    if (response.isSuccessful()) {

                        Orders responseMessage = response.body();

                        VariablesGlobales.setId_pedido(responseMessage.getIdpedido());
                        startActivity(new Intent(MenuMainActivity.this, ListaProductosActivity.class));
                        finish();

                    }
                } catch (Exception e) {
                    Toast.makeText(MenuMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onThrowable: " + e.toString(), e);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(MenuMainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }


}
