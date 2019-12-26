package com.patron.Controllers.ptr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.patron.Controllers.DownloadJSON;
import com.patron.Controllers.UnsubProtege;
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
    ImageView btnBg;
    Button delBtn;
    final Handler handler = new Handler();

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
        btnBg = (ImageView) findViewById(R.id.btnBg);

        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/proteges/" + id);

        assignProtegeActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //unsubProtege(14);
                openAssignProtege(id);
            }
        });

        //final Handler handler = new Handler();
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

                            if(weight.equals("null")) {
                                weight = "BD";
                            }

                            if(glucose.equals("null")) {
                                glucose = "BD";
                            }

                            if(pressure.equals("null")) {
                                pressure = "BD";
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                            weight = "BD";
                            glucose = "BD";
                            pressure = "BD";
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
                btnBg.setVisibility(View.VISIBLE);

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

    private void unsubProtege(final int id) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Chcesz usunąć podopiecznego!")
                .setMessage("Czy jesteś pewny/a?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {

                            UnsubProtege unsubProtege = new UnsubProtege();
                            unsubProtege.execute("https://patronapi.herokuapp.com/proteges/edit/" + id);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("Welcome: ", "Wojtek");
                                }
                            }, 3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Nie", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Próba wylogowania!")
                .setMessage("Czy jesteś pewny/a?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DownloadJSON downloadJSON = new DownloadJSON();
                        downloadJSON.execute("https://patronapi.herokuapp.com/patrons/auth");
                        finish();
                    }
                })
                .setNegativeButton("Nie", null)
                .show();
    }
}
