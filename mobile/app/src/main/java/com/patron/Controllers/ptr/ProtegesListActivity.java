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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.patron.Controllers.DownloadJSON;
import com.patron.Controllers.UnsubProtege;
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
    Button addDialogBtn;
    Button removeDialogBtn;
    ImageView btnBg;

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
        addDialogBtn = (Button) findViewById(R.id.addDialog);
        removeDialogBtn = (Button) findViewById(R.id.removeDialog);

        btnBg = (ImageView) findViewById(R.id.btnBg);

        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/proteges/" + id);

        addDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProtege();
            }
        });

        removeDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delProtege();
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

                        Log.i("ProtegeListPatron ID", id);
                        final String protegeID = jsonPart.getString("protege_id");
                        final String firstName = jsonPart.getString("protege_firstname");
                        final String lastName = jsonPart.getString("protege_lastname");

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
                addDialogBtn.setVisibility(View.VISIBLE);
                removeDialogBtn.setVisibility(View.VISIBLE);
                btnBg.setVisibility(View.VISIBLE);


                protegesAdapter = new ProtegesAdapter(ProtegesListActivity.this, protegeList);
                recyclerView.setAdapter(protegesAdapter);
            }
        }, 3000);
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

    public void delProtege() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProtegesListActivity.this);
        View mView =  getLayoutInflater().inflate(R.layout.unsub_dialog, null);
        EditText mText = (EditText) mView.findViewById(R.id.removeData);
        Button mBtn = (Button) mView.findViewById(R.id.removeBtn);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = 9;
                UnsubProtege unsubProtege = new UnsubProtege();
                unsubProtege.execute("https://patronapi.herokuapp.com/proteges/edit/" + id);
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void addProtege() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProtegesListActivity.this);
        View mView =  getLayoutInflater().inflate(R.layout.add_dialog, null);
        final EditText mText = (EditText) mView.findViewById(R.id.addData);
        Button mBtn = (Button) mView.findViewById(R.id.addDataBtn);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String protegeName = mText.getText().toString();
                String[] splitName;
                final String firstName, secondName;

                Log.i("protegeName", protegeName);
                try {
                    splitName = protegeName.split(" ");

                    firstName = splitName[0];
                    secondName = splitName[1];

                    Log.i("Firstname", firstName);
                    Log.i("Secondname", secondName);

                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Brak wystarczających danych!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }


            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

}
