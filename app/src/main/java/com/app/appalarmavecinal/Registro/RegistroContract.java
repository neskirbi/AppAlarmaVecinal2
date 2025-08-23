package com.app.appalarmavecinal.Registro;

public interface RegistroContract {

    interface View {
        void showProgress();
        void hideProgress();
        void setEmailError(String message);
        void setPasswordError(String message);
        void setConfirmPasswordError(String message);
        void setNameError(String message);
        void setApellidoError(String s);
        void showRegistrationSuccess();
        void showRegistrationError(String message);
        void clearErrors();


    }

    interface Presenter {
        void validateRegistration(String name, String apellido, String email, String password, String confirmPassword);

        void onApellidoError(String message);

        void onDestroy();
    }

    interface Interactor {
        void registerUser(String name, String apellido, String email, String password, OnRegistrationFinishedListener listener);
    }

    interface OnRegistrationFinishedListener {
        void onSuccess();
        void onEmailError(String message);
        void onPasswordError(String message);
        void onNameError(String message);
        void onFailure(String message);
    }
}