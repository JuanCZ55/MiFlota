package com.supra.miflota.data.network;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private Context context;
    public AuthInterceptor(Context context){
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = ApiClient.leerToken(context);
        if(originalRequest.url().encodedPath().contains("/login")){
            return chain.proceed(originalRequest);
        }
        if(token != null){
            Request newRequest = originalRequest.newBuilder().header("Authorization",token).build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(originalRequest);
    }
}
