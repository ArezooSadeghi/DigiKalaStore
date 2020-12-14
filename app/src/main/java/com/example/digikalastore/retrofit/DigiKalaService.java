package com.example.digikalastore.retrofit;

import com.example.digikalastore.model.Category;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.remote.NetworkParams;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DigiKalaService {

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getProductList();

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> serarchProduct(@Query("search") String search);

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getNewestProduct(@Query("sortby") String sortby, @Query("search") String search);

    @GET("products/categories?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Category>> getCategories();
}
