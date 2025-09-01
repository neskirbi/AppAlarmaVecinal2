package com.app.appalarmavecinal.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.app.appalarmavecinal.Principal.PrincipalView;
import com.app.appalarmavecinal.R;
import com.app.appalarmavecinal.Registro.RegistroView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginView extends AppCompatActivity implements LoginContract.View {

    private TextInputEditText emailInput, passwordInput;
    private TextInputLayout emailLayout, passwordLayout;
    private Button loginButton;
    private TextView registro, forgotPassword;
    private ProgressDialog progressDialog;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_view);

        // Inicializar presenter
        presenter = new LoginPresenter(this,this);

        // Verificar si ya hay usuario logueado
        if (presenter.isUserLoggedIn()) {
            navigateToHome();
            return; // Importante: salir del onCreate para no mostrar el login
        }

        // Inicializar vistas
        initViews();
        setupListeners();
    }

    private void initViews() {
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        loginButton = findViewById(R.id.loginButton);
        registro = findViewById(R.id.registro);
        forgotPassword = findViewById(R.id.forgotPassword);

        // Configurar ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.setCancelable(false);
    }

    private void setupListeners() {
        loginButton.setOnClickListener(v -> attemptLogin());

        registro.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegistroView.class));
        });

        forgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Funcionalidad en desarrollo", Toast.LENGTH_SHORT).show();
        });
    }

    private void attemptLogin() {
        String email = emailInput.getText().toString().trim();
        String pass = passwordInput.getText().toString().trim();

        // Limpiar errores previos
        emailLayout.setError(null);
        passwordLayout.setError(null);

        presenter.loginUser(email, pass);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void setEmailError(String error) {
        emailLayout.setError(error);
        emailInput.requestFocus();
    }

    @Override
    public void setPasswordError(String error) {
        passwordLayout.setError(error);
        passwordInput.requestFocus();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, PrincipalView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        presenter.detachView();
    }
}