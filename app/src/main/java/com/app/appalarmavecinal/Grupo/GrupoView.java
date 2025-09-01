package com.app.appalarmavecinal.Grupo;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.app.appalarmavecinal.Models.GrupoDAO;
import com.app.appalarmavecinal.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class GrupoView extends AppCompatActivity {
    private View btnMiembros, btnCrear;
    private View btnSalir, btnUnirse;
    private GrupoDAO grupoDAO;
    private TextView txtGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grupo_view);

        btnMiembros = findViewById(R.id.btn_miembros);
        btnCrear = findViewById(R.id.btn_crear);

        btnSalir = findViewById(R.id.btn_salir_grupo);
        btnUnirse = findViewById(R.id.btn_unirse_grupo);

        txtGrupo = findViewById(R.id.grupo);

        grupoDAO = new GrupoDAO(getApplicationContext());

        // Configurar click listener para el botón crear
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoCrearGrupo();
            }
        });

        checkGrupo();
    }

    private void mostrarDialogoCrearGrupo() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_crear_grupo_view);
        dialog.setCancelable(true);

        // Configurar el tamaño del diálogo
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

        // Configurar botones del diálogo
        MaterialButton btnCancelar = dialog.findViewById(R.id.btnCancelar);
        MaterialButton btnCrearGrupo = dialog.findViewById(R.id.btnCrearGrupo);

        // Configurar los EditText
        TextInputEditText etNombreGrupo = dialog.findViewById(R.id.etNombreGrupo);
        TextInputEditText etDescripcionGrupo = dialog.findViewById(R.id.etDescripcionGrupo);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCrearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombreGrupo.getText().toString().trim();
                String descripcion = etDescripcionGrupo.getText().toString().trim();

                if (nombre.isEmpty()) {
                    etNombreGrupo.setError("El nombre del grupo es requerido");
                    return;
                }

                // Aquí tu lógica para crear el grupo
                crearGrupo(nombre, descripcion);
                dialog.dismiss();
                checkGrupo(); // Actualizar la vista
            }
        });

        dialog.show();
    }

    private void crearGrupo(String nombre, String descripcion) {
        // Aquí va tu lógica para crear el grupo en la base de datos
        Log.d("GrupoView", "Creando grupo: " + nombre + " - " + descripcion);

        // Ejemplo: grupoDAO.crearGrupo(nombre, descripcion);

        Toast.makeText(this, "Grupo '" + nombre + "' creado exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void checkGrupo() {
        boolean existe = grupoDAO.hayGrupoGuardado();
        Log.d("GrupoView", "hayGrupoGuardado() -> " + existe);

        if (existe) {
            btnMiembros.setVisibility(View.VISIBLE);
            btnCrear.setVisibility(View.GONE);

            btnSalir.setVisibility(View.VISIBLE);
            btnUnirse.setVisibility(View.GONE);

            // Obtener y mostrar nombre
            String nombreGrupo = grupoDAO.getNombreGrupo();
            txtGrupo.setText(nombreGrupo);


        } else {
            btnCrear.setVisibility(View.VISIBLE);
            btnMiembros.setVisibility(View.GONE);

            txtGrupo.setText(""); // o "Sin grupo"

            btnUnirse.setVisibility(View.VISIBLE);
            btnSalir.setVisibility(View.GONE);
        }
    }
}