package com.marcelokmats.lanchonete.api;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("pedido")
    Call<Sandwich> getOrder();

    @GET("ingrediente")
    Call<List<Ingredient>> getIngredients();

    @GET("lanche")
    Call<Sandwich> getSandwiches();

    @GET("promocao")
    Call<Sandwich> getPromotions();
}
