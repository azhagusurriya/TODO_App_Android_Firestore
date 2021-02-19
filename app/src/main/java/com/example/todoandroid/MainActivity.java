package com.example.todoandroid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapter.ChecklistAdapter;
import models.Checklist;
import viewmodels.ChecklistViewModel;
import viewmodels.UserViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnChecklistClickListener, OnChecklistLongClickListener{

    private  final String TAG = this.getClass().getCanonicalName();
    private FloatingActionButton fabAddTodo;
    private RecyclerView rvChecklists;
    private LinearLayoutManager viewManager;
    private ChecklistAdapter checklistAdapter;
    private ArrayList<Checklist> checklistArrayList;


    private ChecklistViewModel checklistViewModel;
    private UserViewModel userViewModel;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fabAddTodo = findViewById(R.id.fabAddTodo);
        this.fabAddTodo.setOnClickListener(this);

        this.checklistViewModel = ChecklistViewModel.getInstance();
        this.userViewModel = UserViewModel.getInstance();
        this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();

        this.rvChecklists = findViewById(R.id.rvTodos);
        this.checklistArrayList = new ArrayList<>();
        this.checklistAdapter = new ChecklistAdapter(this.getApplicationContext(), checklistArrayList, this, this);
        this.viewManager = new LinearLayoutManager(this.getApplicationContext());

        this.rvChecklists.setAdapter(this.checklistAdapter);
        this.rvChecklists.setLayoutManager(this.viewManager);
        this.rvChecklists.setHasFixedSize(true);
        this.rvChecklists.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL));

        this.checklistViewModel.getAllChecklistItems(userID);

        this.checklistViewModel.getChecklistRepository().checklistItems.observe(this, new Observer<List<Checklist>>() {
            @Override
            public void onChanged(List<Checklist> checklists) {
                if (checklists != null){
                    Log.e(TAG, "Data Changed : " + checklists.toString());
                    checklistArrayList.addAll(checklists);
                    checklistAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view != null)
        {
            if (view.getId() == this.fabAddTodo.getId()) {
                this.showDialog();
            }
        }
    }

    private void showDialog(){
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_todo, null);

        AlertDialog addDialog = new AlertDialog.Builder(this)
                .setTitle("Add new task")
                .setMessage("please provide task info")
                .setView(dialogView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //read the given task info
                        EditText edrNewTodo = dialogView.findViewById(R.id.edtNewTodo);
                        Log.d(TAG, "New Task Info : " +edrNewTodo.getText().toString());

                        Checklist newChecklist = new Checklist(edrNewTodo.getText().toString());

                        //save todo it to database
                        checklistViewModel.addCheckListItem(userID,newChecklist);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();

                addDialog.show();

    }

    @Override
    public void onChecklistClickListener(Checklist checklist) {

    }

    @Override
    public void onChecklistLongClickListener(Checklist checklist) {

        Log.e(TAG, "Item to be deleted : " + checklist.toString());
        this.checklistViewModel.deleteChecklistItem(userID, checklist.getId());

        //get the deletion status
        //add the observer for status
        //if success then only perform teh next line

        this.checklistArrayList.remove(checklist);

    }
}