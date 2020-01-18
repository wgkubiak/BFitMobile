package com.patron.Controllers.global;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.patron.Controllers.DownloadJSON;
import com.patron.Controllers.PostPatron;
import com.patron.Controllers.PostProtege;
import com.patron.Controllers.ptr.ProtegesListActivity;
import com.patron.R;

import org.w3c.dom.Text;

public class CreateUserActivity extends AppCompatActivity {

    private String roleTxt = "Opiekun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        final TextView createRoleText = (TextView) findViewById(R.id.createTypeText);
        createRoleText.setText("Opiekun");

        final TextView mail = (TextView) findViewById(R.id.mailInput);
        final TextView password = (TextView) findViewById(R.id.passInput);
        final TextView passwordConfirm = (TextView) findViewById(R.id.passConfirmInput);
        final TextView fistName = (TextView) findViewById(R.id.firstnameInput);
        final TextView lastName = (TextView) findViewById(R.id.lastnameInput);
        final Button createBtn = (Button) findViewById(R.id.createAccBtn);

        final Switch roleSwitch = (Switch) findViewById(R.id.createUserTypeSwitch);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userMail = mail.getText().toString();
                String userPassword = password.getText().toString();
                String confirm = passwordConfirm.getText().toString();
                String userFirstName = fistName.getText().toString();
                String userLastName = lastName.getText().toString();

                if(userMail.length() <= 15 && userPassword.length() <= 15 && confirm.length() <= 15
                        && userFirstName.length() <= 15 && userLastName.length() <= 15) {
                    if(!userFirstName.equals("")) {
                        if(!userLastName.equals("")) {
                            if(!userPassword.equals("")) {
                                if(!confirm.equals("")) {
                                    if(!userMail.equals("")) {

                                        String capitalizeFirstName = userFirstName.substring(0, 1).toUpperCase()
                                                + userFirstName.substring(1).toLowerCase();
                                        String capitalizeLastName = userLastName.substring(0, 1).toUpperCase()
                                                + userLastName.substring(1).toLowerCase();

                                        if(roleTxt == "Opiekun") {
                                            if(userPassword.equals(confirm)) {
                                                PostPatron postPatron = new PostPatron();
                                                postPatron.execute("https://patronapi.herokuapp.com/patrons", userMail,
                                                        userPassword, capitalizeFirstName, capitalizeLastName);
                                                showToast("Stworzono konto opiekuna!");
                                                Intent intent = new Intent(CreateUserActivity.this, LoginActivity.class);
                                                finish();
                                                startActivity(intent);
                                            } else {
                                                showToast("Hasła nie są takie same!");
                                            }
                                        } else {
                                            if(userPassword.equals(confirm)) {
                                                PostProtege postProtege = new PostProtege();
                                                postProtege.execute("https://patronapi.herokuapp.com/proteges", capitalizeFirstName,
                                                        capitalizeLastName, userMail, userPassword);
                                                showToast("Stworzono konto podopiecznego!");

                                                Intent intent = new Intent(CreateUserActivity.this, LoginActivity.class);
                                                finish();
                                                startActivity(intent);
                                            } else {
                                                showToast("Hasła nie są takie same!");
                                            }
                                        }
                                    } else {
                                        showToast("Uzupełnij mail-a!");
                                    }
                                } else {
                                    showToast("Powtórz hasło!");
                                }
                            } else {
                                showToast("Uzupełnij hasło!");
                            }
                        } else {
                            showToast("Uzupełnij nazwisko!");
                        }
                    } else {
                        showToast("Uzupełnij imię!");
                    }
                } else {
                    showToast("Błędna długość danych!");
                }
            }
        });

        roleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

                roleTxt = isChecked ? "Podopieczny" : "Opiekun";

                Log.i("Role: ", roleTxt);
                createRoleText.setText(roleTxt);
            }
        });
    }

    private void showToast(String text) {
        Toast toast= Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Próba wyjścia!")
                .setMessage("Czy jesteś pewny/a?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CreateUserActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Nie", null)
                .show();
    }
}