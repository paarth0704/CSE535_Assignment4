package com.example.paarth.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.paarth.todolist.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private static final String TAG = "LIST_1111";
    private static final String filename = "SharedValues";
    SharedPreferences sharedPrefs;
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        sharedPrefs=getSharedPreferences("MyData_232353535", Context.MODE_PRIVATE);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }



        String js = sharedPrefs.getString(TAG,"");
        if(!js.equals("")) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ToDo>>() {
            }.getType();
            ToDoLab.mToDos = gson.fromJson(js, type);
            Log.d("PAARTH","SHITTY MOTA");
        }
        else
            ToDoLab.mToDos = new ArrayList<ToDo>();
    }



    @Override
    protected void onPause() {

        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(ToDoLab.mToDos);
        editor.putString(TAG, json);
        editor.commit();
        Log.d("PAARTH","LAAKY MOTA");

        super.onPause();




    }
}
