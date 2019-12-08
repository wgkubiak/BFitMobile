package com.patron.Controllers.ptr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.patron.Controllers.DownloadJSON;
import com.patron.R;

import org.json.JSONArray;
import org.json.JSONObject;

// TODO: List of proteges based on patron id
public class ProtegesListActivity extends AppCompatActivity {

    int protegeID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proteges_list);

        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/proteges/" + protegeID);

        JSONArray data = DownloadJSON.tempArray;

        try {
            for(int i = 0; i < data.length(); i++) {
                JSONObject jsonPart = data.getJSONObject(i);
                //TODO: Data downloaded before activity starts
                String firstName = jsonPart.getString("protege_firstname");
                String lastName = jsonPart.getString("protege_lastname");

                Log.i("Firstname", firstName);
                Log.i("Lastname", lastName);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
