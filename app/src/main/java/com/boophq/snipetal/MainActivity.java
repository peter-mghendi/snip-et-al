package com.boophq.snipetal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SnipViewModel snipViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snipViewModel = new ViewModelProvider(this).get(SnipViewModel.class);
        snipViewModel.getAllSnips().observe(this, new Observer<List<Snip>>() {
            @Override
            public void onChanged(List<Snip> snips) {
                // TODO Update RecyclerView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}