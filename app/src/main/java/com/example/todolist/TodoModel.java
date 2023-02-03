package com.example.todolist;

public class TodoModel {
    int ToDoId;
    String ToDoTitle;
    String ToDoDescription;
    String Time;

    TodoModel(int ToDoId, String ToDoTitle, String ToDoDescription, String Time){
       this.ToDoId = ToDoId;
       this.ToDoTitle = ToDoTitle;
       this.ToDoDescription = ToDoDescription;
       this.Time = Time;
    }

    public int getToDoId() {
        return ToDoId;
    }

    public void setToDoId(int ToDoId) {
        this.ToDoId = ToDoId;
    }

    public String getToDoTitle() {
        return ToDoTitle;
    }

    public void setToDoTitle(String toDoTitle) {
        this.ToDoTitle = toDoTitle;
    }

    public String getToDoDescription() {
        return ToDoDescription;
    }

    public void setToDoDescription(String toDoDescription) {
        this.ToDoDescription = toDoDescription;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }
}
