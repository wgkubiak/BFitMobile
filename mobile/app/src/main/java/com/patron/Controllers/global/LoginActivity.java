package com.patron.Controllers.global;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.patron.Controllers.ptg.AddExamActivity;
import com.patron.Controllers.ptr.ProtegesListActivity;
import com.patron.R;

public class LoginActivity extends AppCompatActivity {

    private String roleTxt = "Opiekun";
    private String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button logLoginButton = (Button) findViewById(R.id.logLoginBtn);
        Button swapActivityCreate = (Button) findViewById(R.id.swapActivityCreate);


        final TextView logRoleText = (TextView) findViewById(R.id.logRoleText);
        logRoleText.setText("Opiekun");

        logLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roleTxt.equals("Podopieczny")) {
                    openAddExam();
                } else
                    openProtegesList();
            }
        });

        final Switch roleSwitch = (Switch) findViewById(R.id.logRoleSwitch);
        roleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                roleTxt = isChecked ? "Podopieczny" : "Opiekun";
                logRoleText.setText(roleTxt);
            }
        });

        swapActivityCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateUser();
            }
        });

    }

    private void openAddExam() {
        Intent intent = new Intent(this, AddExamActivity.class);
        startActivity(intent);
    }

    private void openProtegesList() {
        Intent intent = new Intent(this, ProtegesListActivity.class);
        startActivity(intent);
    }

    private void openCreateUser() {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }
}
