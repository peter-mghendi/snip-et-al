package com.boophq.snipetal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_SNIP_REQUEST = 1;
    public static final int EDIT_SNIP_REQUEST = 2;

    private SnipViewModel snipViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_snip);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditSnipActivity.class);
                startActivityForResult(intent, ADD_SNIP_REQUEST);
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
                adapter.submitList(snips);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                snipViewModel.delete(adapter.getSnipAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Snip deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new SnipAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Snip snip) {
                Intent intent = new Intent(MainActivity.this, AddEditSnipActivity.class);
                intent.putExtra(AddEditSnipActivity.EXTRA_ID, snip.getId());
                intent.putExtra(AddEditSnipActivity.EXTRA_SUBJECT, snip.getSubject());
                intent.putExtra(AddEditSnipActivity.EXTRA_CONTENT, snip.getContent());
                intent.putExtra(AddEditSnipActivity.EXTRA_PRIORITY, snip.getPriority());
                startActivityForResult(intent, EDIT_SNIP_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SNIP_REQUEST && resultCode == RESULT_OK) {
            String subject = data.getStringExtra(AddEditSnipActivity.EXTRA_SUBJECT);
            String content = data.getStringExtra(AddEditSnipActivity.EXTRA_CONTENT);
            int priority = data.getIntExtra(AddEditSnipActivity.EXTRA_PRIORITY, 1);

            Snip snip = new Snip(subject, content, priority);
            snipViewModel.insert(snip);

            Toast.makeText(this, "Snip saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_SNIP_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditSnipActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Snip can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String subject = data.getStringExtra(AddEditSnipActivity.EXTRA_SUBJECT);
            String content = data.getStringExtra(AddEditSnipActivity.EXTRA_CONTENT);
            int priority = data.getIntExtra(AddEditSnipActivity.EXTRA_PRIORITY, 1);

            Snip snip = new Snip(subject, content, priority);
            snip.setId(id);
            snipViewModel.update(snip);

            Toast.makeText(this, "Snip updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Snip not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_snips:
                snipViewModel.deleteAllSnips();
                Toast.makeText(this, "All snips deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}