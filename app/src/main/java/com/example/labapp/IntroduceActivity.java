package com.example.labapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class IntroduceActivity extends AppCompatActivity {

    private TextInputEditText etName;
    private TextView tvGreetingOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        etName = findViewById(R.id.et_name);
        tvGreetingOutput = findViewById(R.id.tv_greeting_output);
        MaterialButton btnWitaj = findViewById(R.id.btn_witaj);
        MaterialButton btnBack = findViewById(R.id.btn_back);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnWitaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etName.getText() != null ? etName.getText().toString().trim() : "";
                if (input.isEmpty()) {
                    tvGreetingOutput.setText("Przedstaw się.");
                } else {
                    tvGreetingOutput.setText("Witaj " + input);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
