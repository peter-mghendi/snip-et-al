package com.boophq.snipetal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditSnipActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.boophq.snipetal.EXTRA_ID";
    public static final String EXTRA_SUBJECT =
            "com.boophq.snipetal.EXTRA_SUBJECT";
    public static final String EXTRA_CONTENT =
            "com.boophq.snipetal.EXTRA_CONTENT";
    public static final String EXTRA_PRIORITY =
            "com.boophq.snipetal.EXTRA_PRIORITY";

    private EditText editTextSubject;
    private EditText editTextContent;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_snip);

        editTextSubject = findViewById(R.id.edit_text_subject);
        editTextContent = findViewById(R.id.edit_text_content);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Snip");
            editTextSubject.setText(intent.getStringExtra(EXTRA_SUBJECT));
            editTextContent.setText(intent.getStringExtra(EXTRA_CONTENT));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_ID, 1));
        } else {
            setTitle("Add Snip");
        }
    }

    private void saveNote() {
        String subject = editTextSubject.getText().toString();
        String content = editTextContent.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (subject.trim().isEmpty()) {
            editTextSubject.requestFocus();
            Toast.makeText(this, "Please insert a subject.", Toast.LENGTH_SHORT).show();
            return;
        } else if (content.trim().isEmpty()) {
            editTextContent.requestFocus();
            Toast.makeText(this, "Please insert content", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_SUBJECT, subject);
        data.putExtra(EXTRA_CONTENT, content);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_snip_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_snip:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}