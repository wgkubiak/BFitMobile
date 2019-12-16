package com.patron.Controllers.global;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

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

        final Switch roleSwitch = (Switch) findViewById(R.id.createUserTypeSwitch);
        roleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                roleTxt = isChecked ? "Podopieczny" : "Opiekun";
                createRoleText.setText(roleTxt);
            }
        });
    }
}