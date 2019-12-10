package com.patron.Controllers.ptr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.patron.Controllers.DownloadJSON;
import com.patron.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

// TODO: Class that allows patron to add protege based on his ID
public class AssignProtegeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_protege);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("patron_id");

        final TextView assignProtegeText = (TextView) findViewById(R.id.protegeAssignName);
        final Button assignProtegeBtn = (Button) findViewById(R.id.assignProtegeBtn);

        assignProtegeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String protegeName = assignProtegeText.getText().toString();
                String[] splitName;
                final String firstName, secondName;

                Log.i("protegeName", protegeName);
                try {
                    splitName = protegeName.split(" ");

                    firstName = splitName[0];
                    secondName = splitName[1];

                    Log.i("Patron ID", id);
                    Log.i("Firstname", firstName);
                    Log.i("Secondname", secondName);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AssignProtegeActivity.this, firstName + " " +
                                            secondName + " został/a dodany/a do grupy.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, 2000);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AssignProtegeActivity.this, "Brak wystarczających danych!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
