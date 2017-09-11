package com.chembrovich.bsuir.photosaver.Presenters;

import com.chembrovich.bsuir.photosaver.Constants;
import com.chembrovich.bsuir.photosaver.Models.SigningResponse;
import com.chembrovich.bsuir.photosaver.Models.User;
import com.chembrovich.bsuir.photosaver.Models.UserCredential;
import com.chembrovich.bsuir.photosaver.Models.ApiError;
import com.chembrovich.bsuir.photosaver.Network.ApiErrorHandler;
import com.chembrovich.bsuir.photosaver.Models.ApiErrorResponse;
import com.chembrovich.bsuir.photosaver.Network.Network;
import com.chembrovich.bsuir.photosaver.Presenters.interfaces.LoginPresenterInterface;
import com.chembrovich.bsuir.photosaver.Views.interfaces.LoginViewInterface;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Response;

public class LoginPresenter implements LoginPresenterInterface {

    private final LoginViewInterface view;
    private Network network;
    private Network.SigningCallBack signingCallBack;

    public LoginPresenter(LoginViewInterface loginView) {
        this.view = loginView;
        network = new Network();

        signingCallBack = new Network.SigningCallBack() {
            @Override
            public void onResponse(Response<SigningResponse> response) {
                if (response.isSuccessful()) {
                    onSuccessLogIn(response.body().getUser());
                }
                if (response.errorBody() != null) {
                    onApiError(response);
                }
            }

            @Override
            public void onFailure() {
                view.showMessage(Constants.NO_INTERNET_ERROR_MESSAGE);
            }
        };
    }

    @Override
    public void logInUser(String userLogin, String userPassword) {
        if (checkCredentials(userLogin, userPassword)) {
            network.signInUser(new UserCredential(userLogin, userPassword), signingCallBack);
        }
    }

    private void onSuccessLogIn(User user) {
        view.showMessage("You are successfully auth, " + user.getLogin() + "\n" + user.getToken());
    }

    private void onApiError(Response<SigningResponse> response) {
        ApiErrorResponse apiErrorResponse = ApiErrorHandler.handleRegistrationError(response);
        switch (apiErrorResponse.getError()) {
            case Constants.VALIDATION_ERROR:
                ApiError apiError = apiErrorResponse.getValid().get(0);
                view.showMessage(apiError.getField() + apiError.getMessage());
                break;
            case Constants.INCORRECT_LOG_IN_ERROR:
                view.showMessage(Constants.INCORRECT_LOG_IN_ERROR_MESSAGE);
                break;
        }
    }

    private boolean checkCredentials(String login, String password) {
        if (login.isEmpty()) {
            view.showMessage(Constants.EMPTY_LOGIN_ERROR_MESSAGE);
            return false;
        }
        if (!login.matches("[a-z0-9_-]+")) {
            view.showMessage(Constants.INCORRECT_LOGIN_ERROR_MESSAGE);
            return false;
        }
        if (login.length() < 4 || login.length() > 32) {
            view.showMessage(Constants.INCORRECT_LOGIN_SIZE_ERROR_MESSAGE);
            return false;
        }
        if (password.isEmpty()) {
            view.showMessage(Constants.EMPTY_PASSWORD_ERROR_MESSAGE);
            return false;
        }
        if (password.length() < 8 || password.length() > 500) {
            view.showMessage(Constants.INCORRECT_PASSWORD_SIZE_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private String getMd5Hash(String value) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(value.getBytes(), 0, value.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
}
