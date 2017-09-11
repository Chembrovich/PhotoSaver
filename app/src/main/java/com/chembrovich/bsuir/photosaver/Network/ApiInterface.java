package com.chembrovich.bsuir.photosaver.Network;

import com.chembrovich.bsuir.photosaver.Models.SigningResponse;
import com.chembrovich.bsuir.photosaver.Models.UserCredential;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("api/account/signup")
    Call<SigningResponse> signUpUser(@Body UserCredential userCredential);

    @POST("api/account/signin")
    Call<SigningResponse> signInUser(@Body UserCredential userCredential);
}
