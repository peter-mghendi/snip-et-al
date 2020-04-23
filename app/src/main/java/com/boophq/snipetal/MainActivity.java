package com.boophq.snipetal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;

    private SnipViewModel snipViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_snip);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSnipActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String subject = data.getStringExtra(AddSnipActivity.EXTRA_SUBJECT);
            String content = data.getStringExtra(AddSnipActivity.EXTRA_CONTENT);
            int priority = data.getIntExtra(AddSnipActivity.EXTRA_PRIORITY, 1);

            Snip snip = new Snip(subject, content, priority);
            snipViewModel.insert(snip);

            Toast.makeText(this, "Snip saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Snip not saved", Toast.LENGTH_SHORT).show();
        }
    }
}