package com.supra.miflota.data.network.ApiResponsesHelpers;

import com.google.gson.annotations.SerializedName;

public class TokenResponse
{
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
