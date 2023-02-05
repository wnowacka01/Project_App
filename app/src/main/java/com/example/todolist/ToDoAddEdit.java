package com.example.todolist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ToDoAddEdit extends AppCompatActivity {

    TodoModel item = null;
    EditText todoTitle, todoDescription;
    Button saveButton;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        todoTitle = findViewById(R.id.ToDoTitle);
        todoDescription = findViewById(R.id.ToDoDescription);
        saveButton = findViewById(R.id.add_button);

        if (getIntent().getExtras() != null) {
            item = new TodoModel(getIntent().getExtras().getInt("_id"),
                    getIntent().getExtras().getString("title"),
                    getIntent().getExtras().getString("description"),
                    getIntent().getExtras().getString("time"));

            todoTitle.setText(item.getToDoTitle());
            todoDescription.setText(item.getToDoDescription());

        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todoTitle.getText().toString().length() > 0
                        && todoDescription.getText().toString().length() > 0) {
                    if (item != null) {
                        updateTodo();
                    } else if (item == null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            addTodo();
                        }
                    }
                } else {
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(VibrationEffect.createOneShot(1300, VibrationEffect.DEFAULT_AMPLITUDE));
                        Toast.makeText(getApplicationContext(), "Puste pole!", Toast.LENGTH_SHORT).show();
                    } else {
                        vibrator.vibrate(200);
                    }
                    
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTodo() {
        DataBase dataBase = new DataBase(this);
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", todoTitle.getText().toString().trim());
        contentValues.put("description", todoDescription.getText().toString().trim());

        Vibrator vibrator;

        long id = dataBase.Insert(contentValues);
        if (id > 0) {
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                Toast.makeText(this, "Zadanie zostało dodane.", Toast.LENGTH_SHORT).show();
            } else {
                vibrator.vibrate(200);
            }
        } else {
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                Toast.makeText(this, "Brak możliwości dodania.", Toast.LENGTH_SHORT).show();
            } else {
                vibrator.vibrate(200);
            }
        }

        finish();
    }

    public void updateTodo() {
        DataBase dataBase = new DataBase(this);
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", todoTitle.getText().toString().trim());
        contentValues.put("description", todoDescription.getText().toString().trim());
        contentValues.put("time", System.currentTimeMillis() + "");
        String[] selectionArgs = {item.getToDoId() + ""};

        long id = dataBase.Update(contentValues, "_id=?", selectionArgs);

        if (id > 0) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                Toast.makeText(this, "Twoje zadanie zostało zedytowane.", Toast.LENGTH_SHORT).show();
            } else {
                vibrator.vibrate(200);
            }
            
        } else {
            Toast.makeText(this, "Brak możliwości edycji.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}