package com.patron.Controllers.global;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.patron.Controllers.ptr.ProtegesListActivity;
import com.patron.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button logLoginButton = (Button) findViewById(R.id.logLoginBtn);
        logLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProtegesList();
            }
        });
    }

    private void openProtegesList() {
        Intent intent = new Intent(this, ProtegesListActivity.class);
        startActivity(intent);
    }
}
