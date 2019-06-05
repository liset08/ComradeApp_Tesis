package com.example.comradeappsoftware.services;

import com.example.comradeappsoftware.models.ChainProduct;
import com.example.comradeappsoftware.models.Product;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    String API_BASE_URL = "http://192.168.43.213:8000";


    @Multipart
    @POST("api/recproduct")
    Call<ChainProduct> predictImage(@Part("id_sede") RequestBody id,
                                    @Part MultipartBody.Part file);
    @GET("api/products/{idproducto}")
    Call<Product> products(@Path("idproducto") int idproducto);

}
