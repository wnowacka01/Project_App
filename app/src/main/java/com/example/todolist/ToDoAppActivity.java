package com.example.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class ToDoAppActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    ToDoAppAdapter adapter;
    ArrayList<TodoModel> todoList;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
    }

    public void setRecyclerView(ArrayList<TodoModel> list){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ToDoAppAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    public void LoadQuery(){
        todoList = new ArrayList<>();
        todoList.clear();
        DataBase dataBase = new DataBase(this);
        String[] projections = {"_id","title","description","time"};
        String[] selectionArgs = {""};
        Cursor cursor = dataBase.Query(projections, "", null, null);

        if(cursor.getCount() == 0){
            Toast.makeText(this, "Brak zadań na dziś.", Toast.LENGTH_SHORT).show();
        }
        else{
            if(cursor.moveToFirst()){
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));

                    todoList.add(new TodoModel(id, title, description, time));
                }while (cursor.moveToNext());
            }
        }
        if(todoList != null){
            Collections.reverse(todoList);
            setRecyclerView(todoList);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadQuery();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_button){
            startActivity(new Intent(ToDoAppActivity.this, ToDoAddEdit.class));
        }
    }
}