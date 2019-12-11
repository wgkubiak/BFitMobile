package com.patron.Controllers.ptr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.patron.Controllers.DownloadJSON;
import com.patron.Controllers.ptg.AddExamActivity;
import com.patron.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// TODO: List of proteges based on patron id
public class ProtegesListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProtegesAdapter protegesAdapter;
    List<Protege> protegeList;
    ProgressBar progressBar;
    Button assignProtegeActivityBtn;
    Button delBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("patron_id");

        setContentView(R.layout.activity_proteges_list);

        protegeList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignProtegeActivityBtn = (Button) findViewById(R.id.assignActivityBtn);

        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/proteges/" + id);

        assignProtegeActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAssignProtege(id);
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                JSONArray data = DownloadJSON.tempArray;
                try {
                    for(int i = 0; i < data.length(); i++) {    // < DownloadJSON.tempArray

                        JSONObject jsonPart = data.getJSONObject(i);
                        //TODO: try running before downloadJSON.execute

                        Log.i("ProtegeListPatron ID", id);
                        String protegeID = jsonPart.getString("protege_id");
                        String firstName = jsonPart.getString("protege_firstname");
                        String lastName = jsonPart.getString("protege_lastname");

                        String weight, glucose, pressure;

                        // In case if there is no weight input, temporally hardcoded data after catch
                        try {
                            weight = jsonPart.getString("exam_weight");
                            glucose = jsonPart.getString("exam_glucose");
                            pressure = jsonPart.getString("exam_pressure");
                        } catch (Exception e) {
                            e.printStackTrace();

                            weight = "55";
                            glucose = "99";
                            pressure = "123/23";
                        }



                        protegeList.add(
                                new Protege(
                                        protegeID,
                                        firstName,
                                        lastName,
                                        weight,
                                        glucose,
                                        pressure
                                )
                        );
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }

                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.GONE);
                assignProtegeActivityBtn.setVisibility(View.VISIBLE);

                protegesAdapter = new ProtegesAdapter(ProtegesListActivity.this, protegeList);
                recyclerView.setAdapter(protegesAdapter);
            }
        }, 3000);
    }

    private void openAssignProtege(String id) {
        Intent intent = new Intent(this, AssignProtegeActivity.class);
        intent.putExtra("patron_id", id);
        startActivity(intent);
    }
}


// TODO: Put this into activity with list of measures

//        Button addExamBtn = (Button) findViewById(R.id.addExamBtn);
//        addExamBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        new AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Chcesz usunąć podopiecznego!")
//                .setMessage("Czy jesteś pewny/a?")
//                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(ProtegesListActivity.this, "Podopieczny usunięty!", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNegativeButton("Nie", null)
//                .show();
