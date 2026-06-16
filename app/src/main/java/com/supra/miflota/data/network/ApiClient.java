package com.supra.miflota.data.network;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public final static String BASE_URL = "http://10.0.2.2:5097/api/";
    private static Retrofit retrofit= null;
    public static Retrofit getClient(Context context)
    {
        if(retrofit == null){
            OkHttpClient client =
                    new OkHttpClient.Builder().addInterceptor(new AuthInterceptor(context)).build();
            retrofit =
                    new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        }
        return retrofit;
    }
    public static void saveToken(Context context, String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences("token.xml",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token","Bearer " + token);
        editor.apply();
    }
    public static String leerToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("token.xml",
                Context.MODE_PRIVATE);
        return sharedPreferences.getString("token",null);
    }
    public static void borrarToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("token.xml",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }
}
