package com.app.appalarmavecinal.Principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.app.appalarmavecinal.Grupo.GrupoView;
import com.app.appalarmavecinal.Models.Usuario;
import com.app.appalarmavecinal.Models.UsuarioDAO;
import com.app.appalarmavecinal.R;
import com.app.appalarmavecinal.Login.LoginView; // Asegúrate de importar tu LoginView
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.app.appalarmavecinal.databinding.ActivityPrincipalBinding;

import java.text.BreakIterator;

public class PrincipalView extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPrincipalBinding binding;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView tvNombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPrincipal.toolbar);
        binding.appBarPrincipal.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });


        // Cargar el nombre del usuario



        drawer = binding.drawerLayout;
        navigationView = binding.navView;

        // Obtener referencia al TextView del header
        View headerView = navigationView.getHeaderView(0);
        tvNombreUsuario = headerView.findViewById(R.id.nombre_usuario);

        cargarNombreUsuario();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Agregar listener personalizado para manejar el ítem de salir
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_salir) {
                    // Acción para salir
                    salirALogin();
                    drawer.closeDrawers(); // Cerrar el drawer
                    return true;
                }

                if (id == R.id.nav_grupo) {

                   startActivity(new Intent(getApplicationContext(), GrupoView.class));
                    return true;
                }

                // Para los demás ítems, dejar que NavigationUI los maneje
                return NavigationUI.onNavDestinationSelected(menuItem, navController);
            }
        });
    }

    private void salirALogin() {
        // 1. Eliminar datos del usuario de SQLite
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        usuarioDAO.eliminarUsuario();

        // 2. Cerrar sesión en el servidor (si es necesario)
        // Aquí puedes agregar una llamada a tu API para cerrar sesión

        // 3. Redirigir al LoginView y limpiar el stack de actividades
        Intent intent = new Intent(this, LoginView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void cargarNombreUsuario() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = usuarioDAO.obtenerUsuario();

        if (usuario != null && usuario.getNombres() != null && !usuario.getNombres().isEmpty()) {
            String nombreCompleto = usuario.getNombres();
            if (usuario.getApellidos() != null && !usuario.getApellidos().isEmpty()) {
                nombreCompleto += " " + usuario.getApellidos();
            }
            tvNombreUsuario.setText(nombreCompleto);
        } else {
            tvNombreUsuario.setText("Usuario"); // Texto por defecto
        }
    }



}