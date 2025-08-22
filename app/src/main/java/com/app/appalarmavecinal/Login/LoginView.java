package com.app.appalarmavecinal.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.app.appalarmavecinal.PrincipalView;
import com.app.appalarmavecinal.R;
import com.app.appalarmavecinal.Registro.RegistroView;

public class LoginView extends AppCompatActivity {
    Button login ;
    TextView registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_view);


        login = findViewById(R.id.loginButton);
        registro = findViewById(R.id.registro);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PrincipalView.class));
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistroView.class));
            }
        });

    }
}