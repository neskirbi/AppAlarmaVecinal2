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
        void attachView(View view);
        void detachView();
        void loginUser(String email, String password);
        void navigateToRegister();
    }

    interface Interactor {
        void performLogin(String email, String password, OnLoginFinishedListener listener);

        interface OnLoginFinishedListener {
            void onSuccess();
            void onEmailError(String error);
            void onPasswordError(String error);
            void onFailure(String error);
        }
    }
}