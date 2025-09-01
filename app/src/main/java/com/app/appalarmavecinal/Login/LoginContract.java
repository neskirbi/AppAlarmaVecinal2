package com.app.appalarmavecinal.Login;

public interface LoginContract {

    interface View {
        void showProgress();
        void hideProgress();
        void setEmailError(String error);
        void setPasswordError(String error);
        void navigateToHome();
        void showLoginError(String error);
    }

    interface Presenter {

        void detachView();
        void loginUser(String email, String password);
        boolean isUserLoggedIn();
    }

    interface Interactor {
        void performLogin(String email, String password, OnLoginFinishedListener listener);
        boolean isUserLoggedIn();

        interface OnLoginFinishedListener {
            void onSuccess();
            void onEmailError(String error);
            void onPasswordError(String error);
            void onFailure(String error);
        }
    }
}