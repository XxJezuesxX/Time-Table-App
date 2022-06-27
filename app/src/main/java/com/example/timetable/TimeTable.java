package com.example.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeTable extends AppCompatActivity implements saveFolderDialog.saveFolderDialogListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Button addBtn;

    ArrayList<row> rows = new ArrayList<>();
    private int indexOfEachRow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table);

        setupAppElements();
        createRecyclerView();

        addEmptyRow();
        indexOfEachRow = rows.get(rows.size() - 1).getIndex();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check whether the last row's credentials are empty or not
                if(rows.get(rows.size()-1).getTasksName().matches("") || rows.get(rows.size()-1).getHours() == 0) {
                    Toast.makeText(TimeTable.this, "Fill out previous row's content", Toast.LENGTH_SHORT).show();
                    return;
                }

                int totalHours = 0;
                for(int i=0; i < rows.size(); i++){
                    totalHours = totalHours + (rows.get(i).getHours());
                }

                if (totalHours <= 24) {
                    addEmptyRow();
                    mAdapter.notifyItemInserted(rows.size());
                } else {
                    Toast.makeText(TimeTable.this, "Total hours have exceeded 24 hours", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void createRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TimeTableAdapter(rows, TimeTable.this);
        recyclerView.setAdapter(mAdapter);
    }

    public void addEmptyRow(){

        rows.add(new row());
        for(int i = 0; i < rows.size(); i++) {
            rows.get(rows.size() - 1).setIndex(indexOfEachRow+i);
        }
        rows.get(rows.size() - 1).setTasksName("");

    }

    public void setupAppElements(){
        addBtn = findViewById(R.id.add);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.collectData:
                //connect the total hours with the pie chart
                savePopUpWindow();
                return true;
            case R.id.delete:
                rows.clear();
                addEmptyRow();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void savePopUpWindow(){
        saveFolderDialog sFD = new saveFolderDialog();
        sFD.show(getSupportFragmentManager(),"save folder dialogue");
    }

    @Override
    public void assignFolderName(String fileName) {
        Intent intent = new Intent(TimeTable.this, Log.class);
        intent.putExtra("fileKaNaam",fileName);
        startActivity(intent);
    }
}
