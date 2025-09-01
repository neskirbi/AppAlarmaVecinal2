package com.app.appalarmavecinal.Principal.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.appalarmavecinal.R;
import com.app.appalarmavecinal.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private ImageView imageViewAlarma;
    private Vibrator vibrator;
    private Handler handler = new Handler();
    private Runnable longPressRunnable;
    private boolean isPressed = false;
    private FragmentHomeBinding binding;

    private TextView tvCountdown, tvEstado;
    private int countdown = 5;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        com.app.appalarmavecinal.Principal.ui.home.HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(com.app.appalarmavecinal.Principal.ui.home.HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        tvCountdown = binding.tvCountdown;
        tvEstado = binding.tvEstado;

        // Configurar la imagen clickeable
        imageViewAlarma = binding.imageViewAlarma;
        // Inicializar Vibrator
        vibrator = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);

        imageViewAlarma = root.findViewById(R.id.imageViewAlarma);

        // Configurar OnTouchListener para detectar presión prolongada
        imageViewAlarma.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Iniciar presión
                        startLongPress();
                        return true;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Cancelar presión
                        cancelLongPress();
                        return true;
                }
                return false;
            }
        });

        return root;
    }

    private void startLongPress() {
        isPressed = true;
        countdown = 5;

        // Mostrar contador
        tvCountdown.setVisibility(View.VISIBLE);
        tvCountdown.setText(String.valueOf(countdown));
        tvEstado.setText("Activando...");
        tvEstado.setTextColor(getResources().getColor(R.color.colorOrange));

        // Iniciar vibración
        long[] pattern = {0, 200, 800}; // Vibración lenta
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(pattern, 0);
        }

        // Contador regresivo cada segundo
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isPressed && countdown > 0) {
                    countdown--;
                    tvCountdown.setText(String.valueOf(countdown));
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);

        // Programar acción final
        longPressRunnable = new Runnable() {
            @Override
            public void run() {
                if (isPressed) {
                    executeAction();
                }
            }
        };
        handler.postDelayed(longPressRunnable, 5000);
    }

    private void cancelLongPress() {
        isPressed = false;
        tvCountdown.setVisibility(View.INVISIBLE);
        tvEstado.setText("SISTEMA LISTO");
        tvEstado.setTextColor(getResources().getColor(R.color.colorGreen));

        if (vibrator != null) {
            vibrator.cancel();
        }
        if (longPressRunnable != null) {
            handler.removeCallbacks(longPressRunnable);
        }
        handler.removeCallbacksAndMessages(null);
    }

    // En executeAction():
    private void executeAction() {
        tvEstado.setText("¡Alarma activada!");
        tvEstado.setTextColor(getResources().getColor(R.color.colorRed));
        Toast.makeText(getContext(), "¡Alarma enviada a todos los vecinos!", Toast.LENGTH_SHORT).show();

        // Aquí tu código para enviar la alarma al servidor/grupo
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Limpiar para evitar memory leaks
        cancelLongPress();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}