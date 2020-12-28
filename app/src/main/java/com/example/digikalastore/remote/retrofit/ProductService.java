package com.example.digikalastore.remote.retrofit;

import com.example.digikalastore.model.Product;
import com.example.digikalastore.model.Review;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface  ProductService {

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Observable<List<Product>> getAllProducts();

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getPeoducts();

    @GET("products/{id}?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<Product> retrieveProduct(@Path("id") int id);


    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getProductsByCategory(@Query("category") int categoryId);

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getProductsByOrder(@Query("search") String search, @Query("orderby") String orderby, @Query("order") String order);

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa&orderby=date&order=desc")
    Observable<List<Product>> getLatestProducts();

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa&orderby=rating&order=desc")
    Observable<List<Product>> getBestProducts();

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa&orderby=popularity&order=desc")
    Observable<List<Product>> getMostVisitedProducts();

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getProductList(@Query("page") int page);

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> serarchProducts(@Query("search") String search);


    @GET("products/reviews?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Review>> getReviews(@Query("product") int product);

    @FormUrlEncoded
    @POST("products/reviews?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<Review> sendReview(@Field("product_id") int productId,
                            @Field("review") String review,
                            @Field("reviewer") String reviewer,
                            @Field("reviewer_email") String reviewerEmail,
                            @Field("rating") int rating);

    @DELETE("products/reviews/{id}?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<Review> deleteReview(@Path("id") int id);
}
