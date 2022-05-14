package com.example.app_news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.window.SplashScreen;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    private ProgressBar spinner;
    private Button enter;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Breakfast news");
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        enter = (Button)findViewById(R.id.btn_enter);
        enter.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                enter.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
            }
        }, 4000);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e("ErrorAUTH", errString.toString());

            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Успешно!",
                        Toast.LENGTH_SHORT).show();
                Intent authorizationIntent = new Intent(MainActivity.this,Authorization.class);
                startActivity(authorizationIntent);

            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e("FailedAUTH", "FAIL!!!");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Авторизация")
                .setSubtitle("Прислоните палец")
                .setNegativeButtonText("Отмена")
                .build();
        enter.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });
    }
}