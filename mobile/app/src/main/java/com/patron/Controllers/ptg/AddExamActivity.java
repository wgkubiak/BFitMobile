package com.patron.Controllers.ptg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.patron.Controllers.PostExam;
import com.patron.Controllers.global.CreateUserActivity;
import com.patron.R;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddExamActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("exam_protege");

        Button addExamBtn = (Button) findViewById(R.id.addExamBtn);
        final TextView weight = (TextView) findViewById(R.id.weightInput);
        final TextView glucose = (TextView) findViewById(R.id.glucoseInput);
        final TextView pressure1 = (TextView) findViewById(R.id.pressureInput1);
        final TextView pressure2 = (TextView) findViewById(R.id.pressureInput2);
        final TextView dataSend = (TextView) findViewById(R.id.dataSend);
        final TextView slash = (TextView) findViewById(R.id.div);

        addExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String protegeWeight = weight.getText().toString() + "kg";
                    String protegeGlucose = glucose.getText().toString() + "mg/dL";
                    String protegePressure = pressure1.getText().toString() + "/"
                            + pressure2.getText().toString() + "mm Hg";

                    PostExam postExam = new PostExam();
                    postExam.execute("https://patronapi.herokuapp.com/exams", id, protegeWeight, protegeGlucose,
                            protegePressure);

                    Toast.makeText(AddExamActivity.this, "Zrobione!",
                            Toast.LENGTH_SHORT).show();

                    weight.setVisibility(View.GONE);
                    glucose.setVisibility(View.GONE);
                    pressure1.setVisibility(View.GONE);
                    pressure2.setVisibility(View.GONE);
                    slash.setVisibility(View.GONE);
                    dataSend.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}