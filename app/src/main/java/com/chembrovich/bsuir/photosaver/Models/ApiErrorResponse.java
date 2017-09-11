package com.chembrovich.bsuir.photosaver.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiErrorResponse {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("valid")
    @Expose
    private List<ApiError> valid = null;

    public ApiErrorResponse() {
        error = "Unknown error";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ApiError> getValid() {
        return valid;
    }

    public void setValid(List<ApiError> valid) {
        this.valid = valid;
    }
}
