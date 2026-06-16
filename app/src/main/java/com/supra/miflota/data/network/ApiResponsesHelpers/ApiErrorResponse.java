package com.supra.miflota.data.network.ApiResponsesHelpers;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ApiErrorResponse
{
    @SerializedName("title")
    private String title;
    @SerializedName("status")
    private int status;
    @SerializedName("errors")
    private Map<String,String> errors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ApiErrorResponse{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", errors=" + errors +
                '}';
    }
}
