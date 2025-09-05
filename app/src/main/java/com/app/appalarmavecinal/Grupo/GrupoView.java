package com.app.appalarmavecinal.Grupo;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.app.appalarmavecinal.Models.Grupo;
import com.app.appalarmavecinal.Models.GrupoDAO;
import com.app.appalarmavecinal.Models.Usuario;
import com.app.appalarmavecinal.Models.UsuarioDAO;
import com.app.appalarmavecinal.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GrupoView extends AppCompatActivity {
    private ImageView qrImageView;
    private TextView tvMensajeQR,tvDescripQR;
    private View btnMiembros, btnCrear;
    private View btnSalir, btnUnirse;
    private GrupoDAO grupoDAO; // Cambia de GrupoDAO a GrupoDAO
    private TextView txtGrupo;
    private TextInputLayout gruponombreLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grupo_view);



        // Inicializar vistas
        qrImageView = findViewById(R.id.qrImageView);
        tvDescripQR = findViewById(R.id.tvDescripQR);
        tvMensajeQR = findViewById(R.id.tvMensajeQR);
        btnMiembros = findViewById(R.id.btn_miembros);
        btnCrear = findViewById(R.id.btn_crear);
        btnSalir = findViewById(R.id.btn_salir_grupo);
        btnUnirse = findViewById(R.id.btn_unirse_grupo);
        txtGrupo = findViewById(R.id.grupo);

        grupoDAO = new GrupoDAO(getApplicationContext()); // Ahora usa GrupoDAO

        // Configurar click listener para el botón crear
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoCrearGrupo();
            }
        });

        // Configurar click listener para el salir borar el grupo
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grupoDAO.eliminarGrupo();
                checkGrupo();
            }
        });




        checkGrupo();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        boolean existe = grupoDAO.hayGrupoGuardado();
        Log.d("GrupoView", "hayGrupoGuardado() -> " + existe);

        if (existe) {

            //Toast.makeText(this, "Existe: "+grupoDAO.getIdGrupo(), Toast.LENGTH_SHORT).show();

        } else {
            //Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show();
        }
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
        gruponombreLayout = dialog.findViewById(R.id.gruponombreLayout);
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
                    gruponombreLayout.setError("El nombre del grupo es requerido");
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
        try {
            // Obtener el ID del usuario actual (debes tener esta información)
            UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
            Usuario usuario = usuarioDAO.obtenerUsuario();

            if (usuario != null) {
                String idUsuario = usuario.getId_usuario();

                // Crear el grupo en la base de datos
                GrupoDAO grupoDAO = new GrupoDAO(getApplicationContext());
                boolean exito = grupoDAO.crearGrupo(nombre, descripcion, idUsuario);

                if (exito) {
                    Log.d("GrupoView", "Grupo creado: " + nombre + " - " + descripcion);
                    Toast.makeText(this, "Grupo '" + nombre + "' creado exitosamente", Toast.LENGTH_SHORT).show();

                    // Actualizar el usuario con el ID del grupo (opcional)
                    // usuarioDAO.actualizarGrupoUsuario(idUsuario, idGrupo);

                } else {
                    Toast.makeText(this, "Error al crear el grupo", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No se encontró información del usuario", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("GrupoView", "Error al crear grupo: " + e.getMessage());
            Toast.makeText(this, "Error al crear el grupo", Toast.LENGTH_SHORT).show();
        }
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
            Grupo grupo = grupoDAO.obtenerGrupoCompleto();
            txtGrupo.setText(grupo.getNombre());
            tvDescripQR.setText(grupo.getDescripcion());

            // Generar y mostrar QR con información del grupo
            generarQRGrupo();

        } else {
            btnCrear.setVisibility(View.VISIBLE);
            btnMiembros.setVisibility(View.GONE);
            txtGrupo.setText("Sin grupo");
            tvDescripQR.setText("");
            btnUnirse.setVisibility(View.VISIBLE);
            btnSalir.setVisibility(View.GONE);

            // Mostrar QR por defecto con mensaje
            mostrarQRPorDefecto();
        }
    }

    private void generarQRGrupo() {
        try {
            Grupo grupo = grupoDAO.obtenerGrupoCompleto();
            if (grupo != null) {
                // Crear string con la información del grupo
                String qrData = grupo.getId()
                        + "," + grupo.getNombre() +
                        "," + (grupo.getDescripcion() != null ? grupo.getDescripcion() : "");

                // Generar QR
                MultiFormatWriter writer = new MultiFormatWriter();
                BitMatrix matrix = writer.encode(qrData, BarcodeFormat.QR_CODE, 500, 500);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);

                qrImageView.setImageBitmap(bitmap);
                tvMensajeQR.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e("GrupoView", "Error al generar QR: " + e.getMessage());
            mostrarQRPorDefecto();
        }
    }

    private void mostrarQRPorDefecto() {
        qrImageView.setImageResource(R.drawable.ic_sacanner);
    }
}