package com.patron.Controllers.global;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.patron.Controllers.DownloadJSON;
import com.patron.Controllers.ptg.AddExamActivity;
import com.patron.Controllers.ptr.Protege;
import com.patron.Controllers.ptr.ProtegesAdapter;
import com.patron.Controllers.ptr.ProtegesListActivity;
import com.patron.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private String roleTxt = "Opiekun";
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        final Button logLoginButton = (Button) findViewById(R.id.logLoginBtn);
        final Button swapActivityCreate = (Button) findViewById(R.id.swapActivityCreate);
        final TextView mailView = (TextView) findViewById(R.id.logEmail);
        final TextView passView = (TextView) findViewById(R.id.logPassword);
        final TextView logRoleText = (TextView) findViewById(R.id.logRoleText);
        final Switch roleSwitch = (Switch) findViewById(R.id.logRoleSwitch);
        final TextView logInfo = (TextView) findViewById(R.id.logNoAccInfo);
        final ProgressBar logProgress = (ProgressBar) findViewById(R.id.logProgress);
        final ImageView logo = (ImageView) findViewById(R.id.logo);
        final TextView loadingInfo = (TextView) findViewById(R.id.loadingInfo);

        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/patrons");

        logRoleText.setText("Opiekun");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                logLoginButton.setVisibility(View.VISIBLE);
                swapActivityCreate.setVisibility(View.VISIBLE);
                mailView.setVisibility(View.VISIBLE);
                passView.setVisibility(View.VISIBLE);
                logRoleText.setVisibility(View.VISIBLE);
                roleSwitch.setVisibility(View.VISIBLE);
                logInfo.setVisibility(View.VISIBLE);
                loadingInfo.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
                logProgress.setVisibility(View.GONE);
            }
        }, 5000);



        logLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray data = DownloadJSON.tempArray;
                try {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonPart = data.getJSONObject(i);

                        String m = mailView.getText().toString();
                        String p = passView.getText().toString();

                        m.replace("\n", "");

                        String tempMail = jsonPart.getString("patron_mail");
                        String tempPass = jsonPart.getString("patron_pass");

                        if (tempMail.equals(m) && tempPass.equals(p)) {
                            String patronID = jsonPart.getString("patron_id");

                            if (roleTxt.equals("Podopieczny")) {
                                finish();
                                openAddExam();
                            } else {
                                finish();
                                openProtegesList(patronID);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

    private void openProtegesList(String id) {
        Intent intent = new Intent(this, ProtegesListActivity.class);
        intent.putExtra("patron_id", id);

        startActivity(intent);
    }

    private void openCreateUser() {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }
}
