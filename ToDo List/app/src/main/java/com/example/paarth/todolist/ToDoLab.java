package com.example.paarth.todolist;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ToDoLab {
    private static ToDoLab sToDoLab;
    public static ArrayList<ToDo> mToDos;

    public static ToDoLab get(Context context) {
        if (sToDoLab == null) {
            sToDoLab = new ToDoLab(context);
        }
        return sToDoLab;
    }

    private ToDoLab(Context context) {
        if(mToDos.size()==0)
            mToDos = new ArrayList<>();
    }


    public List<ToDo> getToDos() {
        return mToDos;
    }

    public ToDo getToDo(UUID id) {
        for (ToDo toDo : mToDos) {
            if (toDo.getId().equals(id)) {
                return toDo;
            }
        }
        return null;
    }

    public void addToDo(ToDo c) {

        mToDos.add(c);

    }

}
