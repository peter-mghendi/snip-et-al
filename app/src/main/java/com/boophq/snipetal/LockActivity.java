package com.boophq.snipetal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LockActivity extends AppCompatActivity {
    private static final int DELAY = 500;
    public List<String> digitList = new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
    }

    // TODO Bind array to dots, bind dot color to var, observe array
    private void validate() {
        final boolean valid = digitList.toString().equals("1234");

        // TODO turn dots green/red

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (valid) {
                    startActivity(new Intent(LockActivity.this, MainActivity.class));
                    finish();
                }

                // TODO Clear array, reset dot color
            }
        }, DELAY);
    }

    public void onDigitClicked(View view) {
        if (digitList.size() < 4) digitList.add(((Button) view).getText().toString()); // TODO Bind disabled state
        if (digitList.size() == 4) validate();
    }

    public void onBackspaceClicked(View view) {
        if (!digitList.isEmpty()) digitList.remove(digitList.size() - 1); // TODO Bind disabled state
    }
}
