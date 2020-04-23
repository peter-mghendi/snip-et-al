package com.boophq.snipetal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SnipViewModel snipViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.snip_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SnipAdapter adapter = new SnipAdapter();
        recyclerView.setAdapter(adapter);

        snipViewModel = new ViewModelProvider(this).get(SnipViewModel.class);
        snipViewModel.getAllSnips().observe(this, new Observer<List<Snip>>() {
            @Override
            public void onChanged(List<Snip> snips) {
                adapter.setSnips(snips);
            }
        });
    }
}