package com.sunil.materialdesignwithdb.SecondClass;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunil.materialdesignwithdb.Adapter.UserRecyclerAdapter;
import com.sunil.materialdesignwithdb.Model.User;
import com.sunil.materialdesignwithdb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunil on 28-03-2017.
 */

public class UsesListActivity extends AppCompatActivity {
    private AppCompatActivity activity = UsesListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User>listUsers;
    private UserRecyclerAdapter userRecyclerAdapter;
    private DataBaseHelper dataBaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uses_list);
        getSupportActionBar().setTitle(" ");
        initViews();
        initObjects();
    }
    private void initViews() {
        textViewName = (AppCompatTextView)findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView)findViewById(R.id.recyclerViewUsers);
    }

    private void initObjects() {
        listUsers  = new ArrayList<>();
        userRecyclerAdapter = new UserRecyclerAdapter(listUsers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(userRecyclerAdapter);
        dataBaseHelper = new DataBaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(dataBaseHelper.getAllUser());
                return null;
            }
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                userRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
