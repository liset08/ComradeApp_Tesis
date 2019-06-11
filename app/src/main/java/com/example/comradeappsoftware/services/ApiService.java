package com.example.comradeappsoftware.services;

import com.example.comradeappsoftware.models.ChainProduct;
import com.example.comradeappsoftware.models.Login;
import com.example.comradeappsoftware.models.OrderDetails;
import com.example.comradeappsoftware.models.Orders;
import com.example.comradeappsoftware.models.Product;
import com.example.comradeappsoftware.models.Sede;
import com.example.comradeappsoftware.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    String API_BASE_URL = "https://api-comrade-jrevata.c9users.io:8081";


    @Multipart
    @POST("api/recproduct")
    Call<ChainProduct> predictImage(@Part("id_sede") RequestBody id,
                                    @Part MultipartBody.Part file);

    @GET("api/products/{idproducto}")
    Call<Product> products(@Path("idproducto") int idproducto);

//Login y registro de  un usuaro
    @POST("api/authenticate/")
    Call<User> login(@Body Login login);

    @FormUrlEncoded
    @POST("api/users/")
    Call<User> createUsuario(@Field("apellidos") String apellidos,
                             @Field("nombres") String nombres,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("fec_registro") String fec_registro,
                             @Field("distritos_iddistrito") Integer distritos_iddistrito,
                             @Field("dni") String dni);

//Cordenas de la sede
    @FormUrlEncoded
    @POST("api/coordinates")
    Call<Sede> obtenerCoordenadas(@Field("latitud") String latitud,
                                  @Field("longitud") String longitud);

    //Creacion de un pedido
    @FormUrlEncoded
    @POST("api/createorder/")
    Call<Orders> CreatePedido(@Field("usuarios_idusuario") Integer usuarios_idusuario,
                              @Field("sedes_idsede") Integer sedes_idsede);

    //lista de productos con el id de orden
    @FormUrlEncoded
    @POST("api/consultorder")
    Call<List<OrderDetails>> getOrderDetails(@Field("idpedido") int idpedido);

    //Creacion de un pedido
    @FormUrlEncoded
    @POST("api/createorderdetail/")
    Call<OrderDetails> CreateOrdenPedido(@Field("idpedido") Integer idpedido,
                                         @Field("idproducto") Integer idproducto,
                                         @Field("cantidad") Integer cantidad,
                                         @Field("precioxunidad") String precioxunidad,
                                         @Field("descuento") String descuento);

    //DELETE PRODUCTO
    @DELETE("api/orderdetails/{idpedidodeta}/")
    Call<OrderDetails> destroyProducto(@Path("idpedidodeta") Integer idpedidodeta );

    //LISTA DE NOTAS POR USUARIO
    @FormUrlEncoded
    @POST("api/orderbyuser/")
    Call<List<Orders>> getorders(@Field("idusuario") int idusuario);

    //Lista de pedidos por idpedido

    @GET("api/orders/{idpedido}")
    Call<Orders> getorderspedido(@Path("idpedido") int idpedido);

}