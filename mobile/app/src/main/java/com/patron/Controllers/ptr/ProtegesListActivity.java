package com.patron.Controllers.ptr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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
                    "25",
                    "Wojtek",
                    "Kubiak",
                    "726582123",
                    "M"
                )
        );

        protegeList.add(
                new Protege(
                        "1",
                        "25",
                        "Wojtek",
                        "Kubiak",
                        "726582123",
                        "M"
                )
        );

        protegeList.add(
                new Protege(
                        "1",
                        "25",
                        "Wojtek",
                        "Kubiak",
                        "726582123",
                        "M"
                )
        );

        protegeList.add(
                new Protege(
                        "1",
                        "25",
                        "Wojtek",
                        "Kubiak",
                        "726582123",
                        "M"
                )
        );

        protegeList.add(
                new Protege(
                        "1",
                        "25",
                        "Wojtek",
                        "Kubiak",
                        "726582123",
                        "M"
                )
        );



        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/proteges/" + id);
        JSONArray data = DownloadJSON.tempArray;

        try {
            for(int i = 0; i < data.length(); i++) {
                JSONObject jsonPart = data.getJSONObject(i);
                //TODO: Data downloaded before activity starts
                Log.i("ProtegeListPatron ID", id);
                String firstName = jsonPart.getString("protege_firstname");
                String lastName = jsonPart.getString("protege_lastname");

                Log.i("Firstname", firstName);
                Log.i("Lastname", lastName);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        protegesAdapter = new ProtegesAdapter(this, protegeList);
        recyclerView.setAdapter(protegesAdapter);
    }
}
