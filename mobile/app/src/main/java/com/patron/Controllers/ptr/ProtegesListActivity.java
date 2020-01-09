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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.patron.Controllers.AddProtege;
import com.patron.Controllers.DownloadJSON;
import com.patron.Controllers.UnsubProtege;
import com.patron.Controllers.global.CreateUserActivity;
import com.patron.Controllers.global.LoginActivity;
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
    ImageButton removeDialogBtn;
    ImageView whiteBg;

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
        removeDialogBtn = (ImageButton) findViewById(R.id.removeDialog);
        whiteBg = (ImageView) findViewById(R.id.whiteBg);

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
                whiteBg.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
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

                        Intent intent = new Intent(ProtegesListActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Nie", null)
                .show();
    }

    public void delProtege() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProtegesListActivity.this);
        View mView =  getLayoutInflater().inflate(R.layout.unsub_dialog, null);
        final EditText mText = (EditText) mView.findViewById(R.id.removeData);
        final Button mBtn = (Button) mView.findViewById(R.id.removeBtn);

        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/proteges");

        mBtn.setVisibility(View.VISIBLE);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String id = intent.getStringExtra("patron_id");
                final JSONArray data = DownloadJSON.tempArray;
                final String protegeName = mText.getText().toString();
                final String[] splitName;

                splitName = protegeName.split(" ");

                Log.i("Delete ", "Init");

                mBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //TODO: There is error with adapter, it doesnt exist while firstname and secondname is outside of func
                            String firstName = splitName[0];
                            String secondName = splitName[1];

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonPart = data.getJSONObject(i);

                                String tempFirstName = jsonPart.getString("protege_firstname");
                                String tempLastName = jsonPart.getString("protege_lastname");
                                String tempID = jsonPart.getString("protege_id");
                                String currentPatron = jsonPart.getString("protege_patron");

                                if ((tempFirstName.equals(firstName)) && (tempLastName.equals(secondName)) && (currentPatron.equals(id))) {
                                    UnsubProtege unsubProtege = new UnsubProtege();
                                    unsubProtege.execute("https://patronapi.herokuapp.com/proteges/edit/" + tempID);


                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                }

                                //TODO: Something when whole array is mapped, but nothing that matches isnt found
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(),
                                    "Brak wystarczających danych!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                            mBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, 2000);
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
        final Button mBtn = (Button) mView.findViewById(R.id.addDataBtn);

        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/proteges");

        mBtn.setVisibility(View.VISIBLE);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String id = intent.getStringExtra("patron_id");
                final JSONArray data = DownloadJSON.tempArray;

                final String protegeName = mText.getText().toString();
                final String[] splitName;

                splitName = protegeName.split(" ");

                Log.i("Update ", "Init");

                mBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //TODO: There is error with adapter, it doesnt exist while firstname and secondname is outside of func
                            String firstName = splitName[0];
                            String secondName = splitName[1];

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonPart = data.getJSONObject(i);

                                String tempFirstName = jsonPart.getString("protege_firstname");
                                String tempLastName = jsonPart.getString("protege_lastname");
                                String tempID = jsonPart.getString("protege_id");
                                String currentPatron = jsonPart.getString("protege_patron");

                                Log.i("currentpatron: ", currentPatron);

                                if ((tempFirstName.equals(firstName)) && (tempLastName.equals(secondName)) &&
                                        ((currentPatron.equals("null")) || currentPatron == null)) {
                                    Log.i("WENT", "User id: " + tempID + " Protege: " + id);

                                    // TODO: First input isn't working. Only after closing dialog and opening it again, request is send
                                    AddProtege addProtege = new AddProtege();
                                    addProtege.execute("https://patronapi.herokuapp.com/proteges/edit/set/" + tempID, id);

                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                }

                                //TODO: Something when whole array is mapped, but nothing that matches isnt found
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(),
                                    "Brak wystarczających danych!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                            mBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, 2000);
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
