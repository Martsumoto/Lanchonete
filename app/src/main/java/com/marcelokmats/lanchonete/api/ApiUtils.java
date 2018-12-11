package com.marcelokmats.lanchonete.api;

public class ApiUtils {

    public static ApiInterface getInterface() {
        return ApiClient.getClient().create(ApiInterface.class);
    }

}
