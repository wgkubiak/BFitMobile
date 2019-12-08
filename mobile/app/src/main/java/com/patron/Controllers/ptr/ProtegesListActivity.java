package com.patron.Controllers.ptr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.patron.Controllers.DownloadJSON;
import com.patron.R;

// TODO: List of proteges based on patron id
public class ProtegesListActivity extends AppCompatActivity {

    int protegeID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proteges_list);

        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/proteges/" + protegeID);
    }
}
