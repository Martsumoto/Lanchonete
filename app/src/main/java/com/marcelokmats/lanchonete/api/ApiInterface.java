package com.marcelokmats.lanchonete.api;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Order;
import com.marcelokmats.lanchonete.model.Promotion;
import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("pedido")
    Observable<List<Order>> getOrders();

    @GET("ingrediente")
    Observable<List<Ingredient>> getIngredients();

    @GET("lanche")
    Observable<List<Sandwich>> getSandwiches();

    @GET("lanche/{id}")
    Call<Sandwich> getSandwich(@Path("id") String sandwichId);

    @GET("ingrediente/de/{id}")
    Call<List<Ingredient>> getSandwichIngredients(@Path("id") int sandwichId);

    @GET("promocao")
    Observable<List<Promotion>> getPromotions();

    @PUT("pedido/{id}")
    Observable<Response<Void>> insertSandwichIntoOrder(@Path("id") int sandwichId);

    @FormUrlEncoded
    @PUT("pedido/{id}")
    Observable<Response<Void>> insertCustomSandwichIntoOrder(@Path("id") int sandwichId,
                                             @Field("extras") String jsonIngredients);
}
