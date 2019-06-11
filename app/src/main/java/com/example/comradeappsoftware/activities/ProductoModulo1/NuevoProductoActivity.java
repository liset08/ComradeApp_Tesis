package com.example.comradeappsoftware.activities.ProductoModulo1;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.models.ChainProduct;
import com.example.comradeappsoftware.models.OrderDetails;
import com.example.comradeappsoftware.models.Orders;
import com.example.comradeappsoftware.models.Product;
import com.example.comradeappsoftware.models.VariablesGlobales;
import com.example.comradeappsoftware.services.ApiService;
import com.example.comradeappsoftware.services.ApiServiceGenerator;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoProductoActivity extends AppCompatActivity {

    ImageView imageView_Product;
    TextView textView_Producto ,textView_cantidad,textView_total;
    EditText textView_Precio;
    private ApiService service;
    //Variables del contador
public int contador =1,id_producto,cantidad;
    Button btnDisminuir,btnAumentar;
    TextView txtNumero;
    private String precio,total;

    private String[] items = {"Camera" , "Gallery"};

    private static String[] PERMISSIONS_LIST = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    private static final int PERMISSIONS_REQUEST = 100;
    public static final int REQUEST_CODE_CAMERA     = 0012;
    public static final int REQUEST_CODE_GALLERY    = 0013;
    private static final String TAG = NuevoProductoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        service = ApiServiceGenerator.createService(ApiService.class);

        Contador();
        textView_Precio = findViewById(R.id.textView_Precio);
        textView_Producto = findViewById(R.id.textView_Producto);
        textView_cantidad = findViewById(R.id.txt_numero);
        textView_total = findViewById(R.id.total_product);


        imageView_Product = findViewById(R.id.imageView_Product);
        imageView_Product.setImageResource(R.drawable.camera);
        imageView_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
                /**/
            }
        });




        checkAllPermissions();

    }


    private void checkAllPermissions() {
        boolean permissionRequired = false;
        for (String permission : PERMISSIONS_LIST){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                permissionRequired = true;
        }
        if(permissionRequired){
            ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, PERMISSIONS_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSIONS_REQUEST: {
                for (int i=0; i<grantResults.length; i++){
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, PERMISSIONS_LIST[i] + " permissions declined!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                finishAffinity();
                            }
                        }, Toast.LENGTH_LONG);
                    }
                }
            }
        }
    }


    private void openImage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(items[i].equals("Camera")){
                    EasyImage.openCamera(NuevoProductoActivity.this,REQUEST_CODE_CAMERA);
                }else if(items[i].equals("Gallery")){
                    EasyImage.openGallery(NuevoProductoActivity.this,REQUEST_CODE_GALLERY);
                }
            }
        });

        AlertDialog dialog= builder.create();
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles, type);

            }

            private void onPhotosReturned(List<File> imageFile, int type) {
                switch (type){
                    case REQUEST_CODE_CAMERA:
                        textView_Precio.setText(" --- ");
                        textView_Producto.setText(" --- ");
                        Picasso.with(NuevoProductoActivity.this).load(imageFile.get(0)).into(imageView_Product);
                        predict(imageFile.get(0),1);
                        break;
                    case REQUEST_CODE_GALLERY:

                        textView_Precio.setText(" --- ");
                        textView_Producto.setText(" --- ");
                        Picasso.with(NuevoProductoActivity.this).load(imageFile.get(0)).into(imageView_Product);
                        predict(imageFile.get(0),1);


                        break;
                }

            }


            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(NuevoProductoActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });


    }

    // Redimensionar una imagen bitmap
    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }


    public void predict(File image, int idsede){

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Bitmap bitmap = BitmapFactory.decodeFile(image.getPath());

        bitmap = scaleBitmapDown(bitmap, 290);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file", image.getName(), requestFile);
        final String idsedes = String.valueOf(idsede);
        RequestBody id_sede = (RequestBody) RequestBody.create(MultipartBody.FORM, idsedes);


        Call<ChainProduct> call = api.predictImage(id_sede, imagePart);
        call.enqueue(new Callback<ChainProduct>() {
            @Override
            public void onResponse(Call<ChainProduct> call, Response<ChainProduct> response) {
                try{
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ChainProduct chainProduct = response.body();

                        textView_Precio.setText(chainProduct.getPrecio().toString());
                        predictNombre(chainProduct.getIdproducto());



                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(NuevoProductoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) { }
                }
            }

            @Override
            public void onFailure(Call<ChainProduct> call, Throwable t) {

            }
        });
    }

    public void predictNombre(int idproduct){
        ApiService api = ApiServiceGenerator.createService(ApiService.class);
        Call<Product> call = api.products(idproduct);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                try{
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Product product = response.body();

                        textView_Producto.setText(product.getNombre());
                      VariablesGlobales.setId_producto_predict(product.getIdproducto());




                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(NuevoProductoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) { }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(NuevoProductoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void Contador(){

        btnAumentar = findViewById(R.id.btn_adicionar);
        btnDisminuir = findViewById(R.id.btn_disminuir);
         txtNumero= findViewById(R.id.txt_numero);
btnAumentar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        contador++;

        txtNumero.setText(String.valueOf(contador));
   mostrarTotalProduct();
    }
});

btnDisminuir.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            contador--;

        txtNumero.setText(String.valueOf(contador));
        mostrarTotalProduct();
    }
});


    }

public void mostrarTotalProduct(){
    cantidad = Integer.parseInt(textView_cantidad.getText().toString());
    precio = textView_Precio.getText().toString();
    total = String.valueOf(cantidad * Double.parseDouble(precio));
    textView_total.setText(total);
}
    //registra el producto

    public void BackList(View view){
    }

    public void NewOrderDetails(View view){


        //CAMBIA EL ID DEL PRODUCTO
        //VariablesGlobales.getId_producto_predict() => id ´producto service
        Call<OrderDetails> call = service.CreateOrdenPedido(VariablesGlobales.getId_pedido(),2,cantidad,precio, total);


        call.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                try {
                    int statusCode = response.code();
                    Log.e(TAG, "HTTP status code : " + statusCode);

                    if (response.isSuccessful()) {

                        OrderDetails responseMessage = response.body();

                        //     VariablesGlobales.set(responseMessage.getIdpedido());
                        startActivity(new Intent(NuevoProductoActivity.this, ListaProductosActivity.class));
                    finish();


                    }
                } catch (Exception e) {
                    Toast.makeText(NuevoProductoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onThrowable: " + e.toString(), e);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(NuevoProductoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
