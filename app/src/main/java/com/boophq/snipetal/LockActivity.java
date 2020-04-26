package com.boophq.snipetal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.boophq.snipetal.databinding.ActivityLockBinding;

public class LockActivity extends AppCompatActivity {
    private static final int DELAY = 500;
    private ActivityLockBinding binding;
    ObservableField<String> pin = new ObservableField<>("");
    private int dotColor = Color.parseColor("#212121"),
            green = Color.parseColor("#4CAF50"),
            red = Color.parseColor("#F44336");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lock);

        binding.setDotColor(dotColor);
        binding.setDigitListSize(pin.get().length());

        pin.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.setDigitListSize(pin.get().length());
                if (pin.get().length() == 4) validate();
            }
        });
    }

    private void validate() {
        // TODO Handle hashing, compare hashes
        final boolean valid = pin.get().equals("1234");
        binding.setDotColor(valid ? green : red);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.setDotColor(dotColor);
                pin.set("");

                if (valid) {
                    startActivity(new Intent(LockActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, DELAY);
    }

    public void onDigitClicked(View view) {
        pin.set(pin.get() + ((Button) view).getText());
    }

    public void onBackspaceClicked(View view) {
        String currentPin = pin.get();
        if (currentPin.length() > 0)
            pin.set(new StringBuilder(currentPin).deleteCharAt(currentPin.length() - 1).toString());
    }
}
