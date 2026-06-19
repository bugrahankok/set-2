package com.example.labapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnHello = findViewById(R.id.btn_hello);
        MaterialButton btnIntroduce = findViewById(R.id.btn_introduce);
        MaterialButton btnGuess = findViewById(R.id.btn_guess);
        MaterialButton btnLamp = findViewById(R.id.btn_lamp);

        btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelloActivity.class);
                startActivity(intent);
            }
        });

        btnIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IntroduceActivity.class);
                startActivity(intent);
            }
        });

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuessActivity.class);
                startActivity(intent);
            }
        });

        btnLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LampActivity.class);
                startActivity(intent);
            }
        });
    }
}
