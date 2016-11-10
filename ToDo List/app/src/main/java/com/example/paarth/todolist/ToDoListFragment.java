package com.example.paarth.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ToDoListFragment extends Fragment {


    private RecyclerView mTodoRecyclerView;
    private TodoAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        mTodoRecyclerView = (RecyclerView) view.findViewById(R.id.todo_recycler_view);
        mTodoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        update();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_list, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_todo:
                ToDo todo = new ToDo();
                ToDoLab.get(getActivity()).addToDo(todo);
                Intent intent = ToDoPagerActivity.newIntent(getActivity(), todo.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void update() {
        ToDoLab toDoLab = ToDoLab.get(getActivity());
        List<ToDo> toDos = toDoLab.getToDos();

        if (mAdapter == null) {
            mAdapter = new TodoAdapter(toDos);
            mTodoRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    private class TodoHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;


        private ToDo mToDo;

        public TodoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_todo_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_todo_date_text_view);

        }

        public void bindToDo(ToDo todo) {
            mToDo = todo;
            mTitleTextView.setText(mToDo.getTitle());
            mDateTextView.setText(mToDo.getDate().toString());

        }

        @Override
        public void onClick(View v) {
            Intent intent = ToDoPagerActivity.newIntent(getActivity(), mToDo.getId());
            startActivity(intent);
        }
    }

    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {

        private List<ToDo> mToDos;

        public TodoAdapter(List<ToDo> toDos) {
            mToDos = toDos;
        }

        @Override
        public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_todo, parent, false);
            return new TodoHolder(view);
        }

        @Override
        public void onBindViewHolder(TodoHolder holder, int position) {
            ToDo todo = mToDos.get(position);
            holder.bindToDo(todo);
        }

        @Override
        public int getItemCount() {
            return mToDos.size();
        }
    }
}
