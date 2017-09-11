package com.chembrovich.bsuir.photosaver.Network;

import com.chembrovich.bsuir.photosaver.Models.ApiErrorResponse;
import com.chembrovich.bsuir.photosaver.Models.SigningResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.chembrovich.bsuir.photosaver.Constants.BASE_URL;

public class ApiErrorHandler {

    public static ApiErrorResponse handleRegistrationError(Response<SigningResponse> response) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Converter<ResponseBody, ApiErrorResponse> converter = retrofit.responseBodyConverter(ApiErrorResponse.class, new Annotation[0]);

        ApiErrorResponse errorResponse;
        try {
            errorResponse = converter.convert(response.errorBody());

        } catch (IOException ex) {
            return new ApiErrorResponse();
        }
        return errorResponse;
    }
}
