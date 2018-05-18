package com.example.anda_pc.myapplicationwebservices.data.remote;

/**
 * Created by Freddy Lazo on 16/04/2018.
 */

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
