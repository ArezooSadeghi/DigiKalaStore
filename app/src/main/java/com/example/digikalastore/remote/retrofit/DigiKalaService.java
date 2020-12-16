package com.example.digikalastore.remote.retrofit;

import com.example.digikalastore.model.Category;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DigiKalaService {

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa&page=1&page=2&page=3&page=4&page=5&per-page=10")
    Call<List<Product>> getProducts();

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> serarchProducts(@Query("search") String search);

    @GET("products/categories?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Category>> getCategories();

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getProductsByCategory(@Query("category") String id);

    @GET("products/reviews?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Review>> getReviews(@Query("product") int[] productId);

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getProductsByOrder(@Query("search") String search, @Query("orderby") String orderby, @Query("order") String order);

}
