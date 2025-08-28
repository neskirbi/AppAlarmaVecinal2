package com.app.appalarmavecinal.Login;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private LoginContract.Interactor interactor;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.interactor = new LoginInteractor();
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loginUser(String email, String pass) {
        if (view != null) {
            view.showProgress();
        }

        interactor.performLogin(email, pass, new LoginContract.Interactor.OnLoginFinishedListener() {
            @Override
            public void onSuccess() {
                if (view != null) {
                    view.hideProgress();
                    view.navigateToHome();
                }
            }

            @Override
            public void onEmailError(String error) {
                if (view != null) {
                    view.hideProgress();
                    view.setEmailError(error);
                }
            }

            @Override
            public void onPasswordError(String error) {
                if (view != null) {
                    view.hideProgress();
                    view.setPasswordError(error);
                }
            }

            @Override
            public void onFailure(String error) {
                if (view != null) {
                    view.hideProgress();
                    view.showLoginError(error);
                }
            }
        });
    }

    @Override
    public void navigateToRegister() {
        if (view != null) {
            // La navegación se maneja en la View
        }
    }
}