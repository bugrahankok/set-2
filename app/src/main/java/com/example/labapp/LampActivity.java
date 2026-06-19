package com.example.labapp;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class LampActivity extends AppCompatActivity {

    private Lamp lamp;
    private View vLampIndicator;
    private TextView tvLampSwitch;
    private TextView tvLightOutput;
    private TextView tvBulbState;
    private TextView tvIntensity;
    private TextView tvTestReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);

        lamp = new Lamp();

        vLampIndicator = findViewById(R.id.v_lamp_indicator);
        tvLampSwitch = findViewById(R.id.tv_lamp_switch);
        tvLightOutput = findViewById(R.id.tv_light_output);
        tvBulbState = findViewById(R.id.tv_bulb_state);
        tvIntensity = findViewById(R.id.tv_intensity);
        tvTestReport = findViewById(R.id.tv_test_report);

        MaterialButton btnBack = findViewById(R.id.btn_back);
        MaterialButton btnTurnOn = findViewById(R.id.btn_turn_on);
        MaterialButton btnTurnOff = findViewById(R.id.btn_turn_off);
        MaterialButton btnBrighten = findViewById(R.id.btn_brighten);
        MaterialButton btnDim = findViewById(R.id.btn_dim);
        MaterialButton btnReplaceBulb = findViewById(R.id.btn_replace_bulb);
        MaterialButton btnRunTests = findViewById(R.id.btn_run_tests);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnTurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lamp.turnOn();
                updateUI();
            }
        });

        btnTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lamp.turnOff();
                updateUI();
            }
        });

        btnBrighten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lamp.brighten();
                updateUI();
            }
        });

        btnDim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lamp.dim();
                updateUI();
            }
        });

        btnReplaceBulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lamp.replaceBulb();
                updateUI();
            }
        });

        btnRunTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAutomatedTests();
            }
        });

        updateUI();
    }

    private void updateUI() {
        tvLampSwitch.setText(lamp.isOn() ? "ON" : "OFF");
        tvLampSwitch.setTextColor(lamp.isOn() ? Color.parseColor("#4CAF50") : Color.parseColor("#F44336"));

        tvLightOutput.setText(lamp.isShining() ? "SHINING" : "NO LIGHT");
        tvLightOutput.setTextColor(lamp.isShining() ? Color.parseColor("#FFC107") : Color.parseColor("#F44336"));

        tvBulbState.setText(lamp.isBulbBurned() ? "BURNED" : "NORMAL");
        tvBulbState.setTextColor(lamp.isBulbBurned() ? Color.parseColor("#F44336") : Color.parseColor("#2196F3"));

        tvIntensity.setText(lamp.getIntensity() + "/10");

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);

        if (lamp.isBulbBurned()) {
            drawable.setColor(Color.parseColor("#263238"));
            drawable.setStroke(4, Color.parseColor("#F44336"));
        } else if (lamp.isShining()) {
            int alpha = (int) (155 + (lamp.getIntensity() * 10));
            int color = Color.argb(alpha, 255, 235, 59);
            drawable.setColor(color);
            drawable.setStroke(4, Color.parseColor("#FFC107"));
        } else {
            drawable.setColor(Color.parseColor("#B0BEC5"));
            drawable.setStroke(4, Color.parseColor("#78909C"));
        }
        vLampIndicator.setBackground(drawable);
    }

    private void runAutomatedTests() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- RUNNING AUTOMATED TESTS ---\n\n");
        boolean allPassed = true;

        try {
            Lamp testLamp = new Lamp();
            testLamp.turnOn();
            boolean step1 = testLamp.isOn() && testLamp.getIntensity() == 1 && testLamp.isShining() && !testLamp.isBulbBurned();
            testLamp.turnOff();
            boolean step2 = !testLamp.isOn() && testLamp.getIntensity() == 0 && !testLamp.isShining() && !testLamp.isBulbBurned();
            if (step1 && step2) {
                sb.append("1. Turn lamp on & off: PASS\n");
                Log.d("LampBulbTest", "Turn lamp on & off: PASS");
            } else {
                sb.append("1. Turn lamp on & off: FAIL\n");
                Log.e("LampBulbTest", "Turn lamp on & off: FAIL");
                allPassed = false;
            }
        } catch (Exception e) {
            sb.append("1. Turn lamp on & off: FAIL (Exception)\n");
            allPassed = false;
        }

        try {
            Lamp testLamp = new Lamp();
            testLamp.turnOn();
            boolean pass = true;
            for (int i = 0; i < 9; i++) {
                testLamp.brighten();
                if (testLamp.getIntensity() != i + 2) {
                    pass = false;
                }
            }
            if (pass && testLamp.getIntensity() == 10 && testLamp.isOn() && testLamp.isShining() && !testLamp.isBulbBurned()) {
                sb.append("2. Brighten to 10: PASS\n");
                Log.d("LampBulbTest", "Brighten to 10: PASS");
            } else {
                sb.append("2. Brighten to 10: FAIL\n");
                Log.e("LampBulbTest", "Brighten to 10: FAIL");
                allPassed = false;
            }
        } catch (Exception e) {
            sb.append("2. Brighten to 10: FAIL (Exception)\n");
            allPassed = false;
        }

        try {
            Lamp testLamp = new Lamp();
            testLamp.turnOn();
            for (int i = 0; i < 9; i++) {
                testLamp.brighten();
            }
            testLamp.brighten();
            if (testLamp.isBulbBurned() && !testLamp.isOn() && testLamp.getIntensity() == 0 && !testLamp.isShining()) {
                sb.append("3. Brighten above 10 (burn): PASS\n");
                Log.d("LampBulbTest", "Brighten above 10 (burn): PASS");
            } else {
                sb.append("3. Brighten above 10 (burn): FAIL\n");
                Log.e("LampBulbTest", "Brighten above 10 (burn): FAIL");
                allPassed = false;
            }
        } catch (Exception e) {
            sb.append("3. Brighten above 10 (burn): FAIL (Exception)\n");
            allPassed = false;
        }

        try {
            Lamp testLamp = new Lamp();
            testLamp.turnOn();
            testLamp.dim();
            if (!testLamp.isOn() && testLamp.getIntensity() == 0 && !testLamp.isShining()) {
                sb.append("4. Dim to 0 (turn off): PASS\n");
                Log.d("LampBulbTest", "Dim to 0 (turn off): PASS");
            } else {
                sb.append("4. Dim to 0 (turn off): FAIL\n");
                Log.e("LampBulbTest", "Dim to 0 (turn off): FAIL");
                allPassed = false;
            }
        } catch (Exception e) {
            sb.append("4. Dim to 0 (turn off): FAIL (Exception)\n");
            allPassed = false;
        }

        try {
            Lamp testLamp = new Lamp();
            boolean res = testLamp.replaceBulb();
            if (res && !testLamp.isOn() && !testLamp.isBulbBurned()) {
                sb.append("5. Replace bulb while off: PASS\n");
                Log.d("LampBulbTest", "Replace bulb while off: PASS");
            } else {
                sb.append("5. Replace bulb while off: FAIL\n");
                Log.e("LampBulbTest", "Replace bulb while off: FAIL");
                allPassed = false;
            }
        } catch (Exception e) {
            sb.append("5. Replace bulb while off: FAIL (Exception)\n");
            allPassed = false;
        }

        try {
            Lamp testLamp = new Lamp();
            testLamp.turnOn();
            boolean res = testLamp.replaceBulb();
            if (!res && testLamp.isOn()) {
                sb.append("6. Replace bulb while on: PASS\n");
                Log.d("LampBulbTest", "Replace bulb while on: PASS");
            } else {
                sb.append("6. Replace bulb while on: FAIL\n");
                Log.e("LampBulbTest", "Replace bulb while on: FAIL");
                allPassed = false;
            }
        } catch (Exception e) {
            sb.append("6. Replace bulb while on: FAIL (Exception)\n");
            allPassed = false;
        }

        try {
            Lamp testLamp = new Lamp();
            testLamp.turnOn();
            for (int i = 0; i < 10; i++) {
                testLamp.brighten();
            }
            testLamp.turnOn();
            if (!testLamp.isShining() && testLamp.isBulbBurned()) {
                sb.append("7. Turn on with burned bulb: PASS\n");
                Log.d("LampBulbTest", "Turn on with burned bulb: PASS");
            } else {
                sb.append("7. Turn on with burned bulb: FAIL\n");
                Log.e("LampBulbTest", "Turn on with burned bulb: FAIL");
                allPassed = false;
            }
        } catch (Exception e) {
            sb.append("7. Turn on with burned bulb: FAIL (Exception)\n");
            allPassed = false;
        }

        sb.append("\n");
        if (allPassed) {
            sb.append("--- TEST RUN COMPLETED: ALL PASSED ---");
        } else {
            sb.append("--- TEST RUN COMPLETED: SOME FAILED ---");
        }

        tvTestReport.setText(sb.toString());
    }
}
