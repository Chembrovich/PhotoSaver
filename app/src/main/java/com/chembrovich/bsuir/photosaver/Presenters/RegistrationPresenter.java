package com.chembrovich.bsuir.photosaver.Presenters;

import com.chembrovich.bsuir.photosaver.Constants;
import com.chembrovich.bsuir.photosaver.Models.SigningResponse;
import com.chembrovich.bsuir.photosaver.Models.User;
import com.chembrovich.bsuir.photosaver.Models.UserCredential;
import com.chembrovich.bsuir.photosaver.Models.ApiError;
import com.chembrovich.bsuir.photosaver.Network.ApiErrorHandler;
import com.chembrovich.bsuir.photosaver.Models.ApiErrorResponse;
import com.chembrovich.bsuir.photosaver.Network.Network;
import com.chembrovich.bsuir.photosaver.Presenters.interfaces.RegistrationPresenterInterface;
import com.chembrovich.bsuir.photosaver.Views.interfaces.RegistrationViewInterface;

import retrofit2.Response;

public class RegistrationPresenter implements RegistrationPresenterInterface {

    private final RegistrationViewInterface view;
    private Network network;
    private Network.SigningCallBack signingCallBack;

    public RegistrationPresenter(RegistrationViewInterface registrationView) {
        this.view = registrationView;
        network = new Network();

        signingCallBack = new Network.SigningCallBack() {
            @Override
            public void onResponse(Response<SigningResponse> response) {
                if (response.isSuccessful()) {
                    onSuccessRegistration(response.body().getUser());
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
    public void registerUser(String login, String password, String repeatPassword) {
        if (checkCredentials(login, password, repeatPassword)) {
            network.signUpUser(new UserCredential(login, password), signingCallBack);
        }
    }

    private void onSuccessRegistration(User user) {
        view.showMessage("You are successfully registered, " + user.getLogin() + "\n" + user.getToken());
    }

    private void onApiError(Response<SigningResponse> response) {
        ApiErrorResponse apiErrorResponse = ApiErrorHandler.handleRegistrationError(response);
        switch (apiErrorResponse.getError()) {
            case Constants.VALIDATION_ERROR:
                ApiError apiError = apiErrorResponse.getValid().get(0);
                view.showMessage(apiError.getField() + apiError.getMessage());
                break;
            case Constants.LOGIN_IS_USED_ERROR:
                view.showMessage(Constants.LOGIN_IS_USED_ERROR_MESSAGE);
                break;
        }
    }

    private boolean checkCredentials(String login, String password, String repeatPassword) {
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
        if (!password.equals(repeatPassword)) {
            view.showMessage(Constants.PASSWORDS_DONT_MATCH_ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
