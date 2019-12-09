package com.patron.Controllers.global;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.patron.Controllers.DownloadJSON;
import com.patron.Controllers.ptg.AddExamActivity;
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

        Button logLoginButton = (Button) findViewById(R.id.logLoginBtn);
        Button swapActivityCreate = (Button) findViewById(R.id.swapActivityCreate);

        final TextView logRoleText = (TextView) findViewById(R.id.logRoleText);
        logRoleText.setText("Opiekun");

        logLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DownloadJSON downloadJSON = new DownloadJSON();
                downloadJSON.execute("https://patronapi.herokuapp.com/patrons");
                JSONArray data = DownloadJSON.tempArray;

                try {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonPart = data.getJSONObject(i);

                        TextView mailView = (TextView) findViewById(R.id.logEmail);
                        TextView passView = (TextView) findViewById(R.id.logPassword);

                        String m = mailView.getText().toString();
                        String p = passView.getText().toString();

                        m.replace("\n", "");


                        String tempMail = jsonPart.getString("patron_mail");
                        String tempPass = jsonPart.getString("patron_pass");

                        boolean equality = tempMail.equals(m);

                        Log.i("Mail", tempMail);
                        Log.i("TV Mail", m);
                        Log.i("Equal: ", Boolean.toString(equality));

                        Log.i("Pass", tempPass);
                        Log.i("TV Pass", p);

                        if (tempMail.equals(m) && tempPass.equals(p)) {
                            String patronID = jsonPart.getString("patron_id");

                            if (roleTxt.equals("Podopieczny")) {
                                openAddExam();
                            } else {
                                Toast.makeText(getApplicationContext(), "Witaj, " + jsonPart.getString("patron_firstname") + "!",
                                        Toast.LENGTH_LONG).show();
//                                finish();
                                Log.i("patronID", patronID);
                                openProtegesList(patronID);
                            }
                        } else if (tempMail.equals(m) == false || tempPass.equals(p) == false ){
                            Toast.makeText(getApplicationContext(), "Błędne dane! Spróbuj ponownie!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
