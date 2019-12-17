package com.patron.Controllers.global;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.patron.Controllers.ptr.ProtegesListActivity;
import com.patron.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private String roleTxt = "Opiekun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        final Button logLoginButton = (Button) findViewById(R.id.logLoginBtn);
        final Button swapActivityCreate = (Button) findViewById(R.id.createAccHeader);
        final TextView mailView = (TextView) findViewById(R.id.logEmail);
        final TextView passView = (TextView) findViewById(R.id.logPassword);
        final TextView logRoleText = (TextView) findViewById(R.id.logRoleText);
        final Switch roleSwitch = (Switch) findViewById(R.id.logRoleSwitch);
        final ProgressBar logProgress = (ProgressBar) findViewById(R.id.logProgress);
        final TextView loadingInfo = (TextView) findViewById(R.id.loadingInfo);
        final ImageView whiteBg = (ImageView) findViewById(R.id.whiteLoginBg);
        final TextView header = (TextView) findViewById(R.id.textViewLoginHeader);

        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://patronapi.herokuapp.com/patrons/auth");

        logRoleText.setText("OPIEKUN");

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
                header.setVisibility(View.VISIBLE);
                whiteBg.setVisibility(View.VISIBLE);
                loadingInfo.setVisibility(View.GONE);
                logProgress.setVisibility(View.GONE);
            }
        }, 5000);

        final boolean tempIsChecked = false;

        logLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logUser(mailView, passView, "patron_mail","patron_pass", tempIsChecked);
            }
        });

        roleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                roleTxt = isChecked ? "PODOPIECZNY" : "OPIEKUN";
                logRoleText.setText(roleTxt);


                if(isChecked) {
                    logDownloadData("https://patronapi.herokuapp.com/proteges/auth", logProgress, logLoginButton);
                    Log.i("Changed into: ", "Protege");

                    logLoginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            logUser(mailView, passView, "protege_mail","protege_pass", isChecked);
                        }
                    });
                } else {
                    logDownloadData("https://patronapi.herokuapp.com/patrons/auth", logProgress, logLoginButton);

                    Log.i("Changed into: ", "Patron");
                    logLoginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            logUser(mailView, passView, "patron_mail","patron_pass", isChecked);
                        }
                    });
                }
            }
        });

        swapActivityCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateUser();
            }
        });

    }

    private void logDownloadData(String ur, final ProgressBar logProgress, final Button logLoginButton) {
        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute(ur);

        logProgress.setVisibility(View.VISIBLE);
        logLoginButton.setVisibility(View.INVISIBLE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                logLoginButton.setVisibility(View.VISIBLE);
                logProgress.setVisibility(View.GONE);
            }
        }, 2000);
    }

    private void logUser(final TextView mailView, final TextView passView,
                         final String mail, final String pass, final boolean user) {

                JSONArray data = DownloadJSON.tempArray;

                try {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonPart = data.getJSONObject(i);

                        String m = mailView.getText().toString();
                        String p = passView.getText().toString();

                        m.replace("\n", "");

                        String tempMail = jsonPart.getString(mail);
                        String tempPass = jsonPart.getString(pass);

                        Log.i("m: ", m);
                        Log.i("p", p);
                        Log.i("tempMail", tempMail);
                        Log.i("tempPass", tempPass);


                        if (tempMail.equals(m) && tempPass.equals(p)) {
                            if(user) {
                                String protegeID = jsonPart.getString("protege_id");
                                finish();

                                openAddExam(protegeID);
                            } else {
                                String patronID = jsonPart.getString("patron_id");

                                finish();
                                openProtegesList(patronID);
                            }
                        } else {
                            //TODO: info about wrong data
//
//                                        Toast.makeText(LoginActivity.this, "Złe dane!",
//                                                Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Coś poszło nie tak! Spróbuj ponownie.",
                            Toast.LENGTH_SHORT).show();
                }

    }

    private void openAddExam(String id) {
        Intent intent = new Intent(this, AddExamActivity.class);
        intent.putExtra("exam_protege", id);

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
