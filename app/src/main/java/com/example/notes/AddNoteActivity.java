package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private Spinner daysOfTheWeek;
    private RadioGroup radioGroupPriority;
    private NotesDBHelper notesDBHelper;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.multiLineDescription);
        daysOfTheWeek = findViewById(R.id.spinnerDayOfTheWeek);
        radioGroupPriority = findViewById(R.id.radioGroup);
        notesDBHelper = new NotesDBHelper(this);
        database = notesDBHelper.getWritableDatabase();
    }

    public void submitNote(View view) {
        String titleString = title.getText().toString();
        String descriptionString = description.getText().toString();
        String dayOfTheWeek = daysOfTheWeek.getSelectedItem().toString();
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());
        if(isFilled(titleString, descriptionString)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, titleString);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, descriptionString);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAYOFTHEWEEK, dayOfTheWeek);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFilled(String title, String description){
        return !title.isEmpty() && !description.isEmpty();
    }
}