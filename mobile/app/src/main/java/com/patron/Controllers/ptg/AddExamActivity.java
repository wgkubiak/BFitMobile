package com.patron.Controllers.ptg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.patron.Controllers.PostExam;
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
    public static final MediaType FORM = MediaType.parse("multipart/form-data");
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        Button addExamBtn = (Button) findViewById(R.id.addExamBtn);

        addExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    PostExam postExam = new PostExam();
                    postExam.execute("https://patronapi.herokuapp.com/exams", "1", "77.9kg", "117mg/dL",
                            "115/65mm Hg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}