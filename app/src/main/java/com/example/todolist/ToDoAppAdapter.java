package com.example.todolist;

import static android.content.Context.VIBRATOR_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoAppAdapter extends RecyclerView.Adapter<ToDoAppAdapter.ViewHolder> {

    Context context;
    ArrayList<TodoModel> todoList;

    ToDoAppAdapter(Context context, ArrayList<TodoModel> list) {
        this.todoList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.todoTitle.setText(todoList.get(position).getToDoTitle());
        holder.todoDescription.setText(todoList.get(position).getToDoDescription());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView todoTitle;
        TextView todoDescription;
        ImageView edit, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.ToDoTitle);
            todoDescription = itemView.findViewById(R.id.ToDoDescription);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.edit){
                Intent intent = new Intent(context, ToDoAddEdit.class);
                intent.putExtra("title", todoList.get(getAdapterPosition()).getToDoTitle());
                intent.putExtra("description", todoList.get(getAdapterPosition()).getToDoDescription());
                intent.putExtra("_id",todoList.get(getAdapterPosition()).getToDoId());
                intent.putExtra("time",todoList.get(getAdapterPosition()).getTime());
                context.startActivity(intent);
            }
            else if(v.getId() == R.id.delete){
                deleteTodo(todoList.get(getAdapterPosition()));
                ((ToDoAppActivity)context).LoadQuery();
            }

        }

        public void deleteTodo(TodoModel item) {
            DataBase dataBase = new DataBase(context);
            String[] selectionArgs = {item.getToDoId() + ""};
            dataBase.Delete("_id=?", selectionArgs);
            Toast.makeText(context, "Zadanie zostało pomyślnie usunięte.", Toast.LENGTH_SHORT).show();
        }
    }
}
