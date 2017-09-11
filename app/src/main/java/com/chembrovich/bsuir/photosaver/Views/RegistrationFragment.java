package com.chembrovich.bsuir.photosaver.Views;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chembrovich.bsuir.photosaver.Presenters.RegistrationPresenter;
import com.chembrovich.bsuir.photosaver.Presenters.interfaces.RegistrationPresenterInterface;
import com.chembrovich.bsuir.photosaver.R;
import com.chembrovich.bsuir.photosaver.Views.interfaces.RegistrationViewInterface;

public class RegistrationFragment extends Fragment implements RegistrationViewInterface {

    private EditText loginEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private Button btnSignUp;

    private View view;

    private RegistrationPresenterInterface presenter;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegistrationPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);

        loginEditText = view.findViewById(R.id.login_edit_text_register);
        passwordEditText = view.findViewById(R.id.password_edit_text_register);
        repeatPasswordEditText = view.findViewById(R.id.repeat_password_edit_text_register);
        btnSignUp = view.findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.registerUser(loginEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        repeatPasswordEditText.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void btnRegisterClicked(String result) {

    }

}
