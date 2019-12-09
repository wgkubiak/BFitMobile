package com.patron.Controllers.ptr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.patron.Controllers.DownloadJSON;
import com.patron.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: List of proteges based on patron id
public class ProtegesListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProtegesAdapter protegesAdapter;

    List<Protege> protegeList;

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

        protegeList.add(
                new Protege(
                        "1",
                        "Janusz",
                        "Muzykant",
                        "149",
                        "70",
                        "145/78"
                )
        );

        protegeList.add(
                new Protege(
                        "1",
                        "Anna",
                        "Mariant",
                        "57",
                        "87",
                        "125/71"
                )
        );

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

        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/proteges/" + id);
        JSONArray data = DownloadJSON.tempArray;

        try {
            for(int i = 0; i < data.length(); i++) {
                JSONObject jsonPart = data.getJSONObject(i);
                //TODO: Array the same as previous? Not changing


                Log.i("ProtegeListPatron ID", id);
                String protegeID = jsonPart.getString("protege_id");
                String firstName = jsonPart.getString("protege_firstname");
                String lastName = jsonPart.getString("protege_lastname");
                String weight = jsonPart.getString("exam_weight");
                String glucose = jsonPart.getString("exam_glucose");
                String pressure = jsonPart.getString("exam_pressure");

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

        protegesAdapter = new ProtegesAdapter(this, protegeList);
        recyclerView.setAdapter(protegesAdapter);
    }
}
