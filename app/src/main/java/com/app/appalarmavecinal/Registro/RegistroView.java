package com.app.appalarmavecinal.Registro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.app.appalarmavecinal.Login.LoginView;
import com.app.appalarmavecinal.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegistroView extends AppCompatActivity implements RegistroContract.View {

    private RegistroContract.Presenter presenter;
    private TextInputEditText nameInput, apellidoInput, emailInput, passwordInput, confirmPasswordInput;
    private Button registerButton;

    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_view);

        // Inicializar presenter
        presenter = new RegistroPresenter(this, new RegistroInteractor());

        // Binding de vistas

        login = findViewById(R.id.loginLink);
        nameInput = findViewById(R.id.nameInput);
        apellidoInput = findViewById(R.id.apellidoInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        registerButton = findViewById(R.id.registerButton);

        // Configurar listeners
        registerButton.setOnClickListener(v -> attemptRegistration());

        login.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginView.class));
        });
    }

    private void attemptRegistration() {
        String name = nameInput.getText().toString().trim();
        String apellido = apellidoInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        presenter.validateRegistration(name, apellido, email, password, confirmPassword);
    }

    @Override
    public void showProgress() {
        registerButton.setEnabled(false);
        registerButton.setText("Registrando...");
    }

    @Override
    public void hideProgress() {
        registerButton.setEnabled(true);
        registerButton.setText("Registrarse");
    }

    @Override
    public void setEmailError(String message) {
        emailInput.setError(message);
    }

    @Override
    public void setPasswordError(String message) {
        passwordInput.setError(message);
    }

    @Override
    public void setConfirmPasswordError(String message) {
        confirmPasswordInput.setError(message);
    }

    @Override
    public void setNameError(String message) {
        nameInput.setError(message);
    }

    @Override
    public void setApellidoError(String message) {
        apellidoInput.setError(message);
    }

    @Override
    public void showRegistrationSuccess() {
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        // Navegar a la siguiente actividad
        finish();
    }

    @Override
    public void showRegistrationError(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearErrors() {
        nameInput.setError(null);
        emailInput.setError(null);
        passwordInput.setError(null);
        confirmPasswordInput.setError(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}