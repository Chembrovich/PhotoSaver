package com.chembrovich.bsuir.photosaver.Network;

import com.chembrovich.bsuir.photosaver.Models.SigningResponse;
import com.chembrovich.bsuir.photosaver.Models.User;
import com.chembrovich.bsuir.photosaver.Models.UserCredential;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.chembrovich.bsuir.photosaver.Constants.BASE_URL;

public class Network {
    private ApiInterface api;

    public interface SigningCallBack {
        void onResponse(Response<SigningResponse> response);

        void onFailure();
    }

    public Network() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiInterface.class);
    }

    public void signUpUser(UserCredential userCredential, final SigningCallBack signingCallBack) {
        api.signUpUser(userCredential).enqueue(new Callback<SigningResponse>() {
            @Override
            public void onResponse(Call<SigningResponse> call, Response<SigningResponse> response) {
                signingCallBack.onResponse(response);
            }

            @Override
            public void onFailure(Call<SigningResponse> call, Throwable t) {
                signingCallBack.onFailure();
            }
        });
    }

    public void signInUser(UserCredential userCredential,final SigningCallBack signingCallBack) {
        api.signInUser(userCredential).enqueue(new Callback<SigningResponse>() {
            @Override
            public void onResponse(Call<SigningResponse> call, Response<SigningResponse> response) {
                signingCallBack.onResponse(response);
            }

            @Override
            public void onFailure(Call<SigningResponse> call, Throwable t) {
                signingCallBack.onFailure();
            }
        });
    }
}
