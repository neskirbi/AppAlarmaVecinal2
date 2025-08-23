package com.app.appalarmavecinal.Registro;

public class RegistroPresenter implements RegistroContract.Presenter, RegistroContract.OnRegistrationFinishedListener {

    private RegistroContract.View view;
    private RegistroContract.Interactor interactor;

    public RegistroPresenter(RegistroContract.View view, RegistroContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void validateRegistration(String name, String apellido, String email, String password, String confirmPassword) {
        view.clearErrors();

        boolean isValid = true;

        // Validar nombre
        if (name.isEmpty()) {
            view.setNameError("El nombre es requerido");
            isValid = false;
        } else if (name.length() < 3) {
            view.setNameError("El nombre debe tener al menos 3 caracteres");
            isValid = false;
        }

        // Validar apellido
        if (apellido.isEmpty()) {
            view.setApellidoError("El apellido es requerido");
            isValid = false;
        } else if (apellido.length() < 3) {
            view.setApellidoError("El apellido debe tener al menos 3 caracteres");
            isValid = false;
        }

        // Validar email
        if (email.isEmpty()) {
            view.setEmailError("El email es requerido");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.setEmailError("Formato de email inválido");
            isValid = false;
        }

        // Validar contraseña
        if (password.isEmpty()) {
            view.setPasswordError("La contraseña es requerida");
            isValid = false;
        } else if (password.length() < 6) {
            view.setPasswordError("La contraseña debe tener al menos 6 caracteres");
            isValid = false;
        }

        // Validar confirmación de contraseña
        if (confirmPassword.isEmpty()) {
            view.setConfirmPasswordError("Confirma tu contraseña");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            view.setConfirmPasswordError("Las contraseñas no coinciden");
            isValid = false;
        }

        if (isValid) {
            view.showProgress();
            interactor.registerUser(name, apellido, email, password, this);
        }
    }

    @Override
    public void onSuccess() {
        view.hideProgress();
        view.showRegistrationSuccess();
    }

    @Override
    public void onEmailError(String message) {
        view.hideProgress();
        view.setEmailError(message);
    }

    @Override
    public void onPasswordError(String message) {
        view.hideProgress();
        view.setPasswordError(message);
    }

    @Override
    public void onNameError(String message) {
        view.hideProgress();
        view.setNameError(message);
    }

    @Override
    public void onApellidoError(String message) {
        view.hideProgress();
        view.setApellidoError(message);
    }


    @Override
    public void onFailure(String message) {
        view.hideProgress();
        view.showRegistrationError(message);
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}