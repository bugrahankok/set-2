package com.example.labapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Random;

public class GuessActivity extends AppCompatActivity {

    private int targetNumber;
    private int attemptCount = 1;
    private Random random;

    private TextView tvAttemptCount;
    private TextInputEditText etGuess;
    private TextView tvGuessFeedback;
    private MaterialButton btnGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        random = new Random();
        generateNewTargetNumber();

        tvAttemptCount = findViewById(R.id.tv_attempt_count);
        etGuess = findViewById(R.id.et_guess);
        tvGuessFeedback = findViewById(R.id.tv_guess_feedback);
        btnGuess = findViewById(R.id.btn_guess);
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

        updateAttemptText();

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGuess();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void generateNewTargetNumber() {
        targetNumber = random.nextInt(10) + 1;
    }

    private void updateAttemptText() {
        tvAttemptCount.setText("Current Attempt: " + attemptCount);
    }

    private void handleGuess() {
        String inputStr = etGuess.getText() != null ? etGuess.getText().toString().trim() : "";

        if (inputStr.isEmpty()) {
            tvGuessFeedback.setText("Please enter an integer number.");
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(inputStr);
        } catch (NumberFormatException e) {
            tvGuessFeedback.setText("Please enter an integer number.");
            return;
        }

        if (guess < 1 || guess > 10) {
            tvGuessFeedback.setText("Number must be in range 1..10.");
            return;
        }

        if (guess < targetNumber) {
            tvGuessFeedback.setText("Value too small");
            attemptCount++;
            updateAttemptText();
        } else if (guess > targetNumber) {
            tvGuessFeedback.setText("Value too large");
            attemptCount++;
            updateAttemptText();
        } else {
            if (attemptCount == 2) {
                tvGuessFeedback.setText("Correct — achieved on the 2nd attempt");
                btnGuess.setEnabled(false);
                etGuess.setEnabled(false);
            } else {
                tvGuessFeedback.setText("Correct, but not on the 2nd attempt. Try again.");
                attemptCount = 1;
                generateNewTargetNumber();
                updateAttemptText();
                etGuess.setText("");
            }
        }
    }
}
