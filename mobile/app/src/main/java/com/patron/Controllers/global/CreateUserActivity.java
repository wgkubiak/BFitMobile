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
                swapView(!isChecked);
            }
        });
    }

    void swapView(boolean option) {

        TextView mailInput = (TextView) findViewById(R.id.mailPtrInput);
        TextView passwordInput = (TextView) findViewById(R.id.passPtrInput);
        TextView passwordConfirmInput = (TextView) findViewById(R.id.passConfirmPtrInput);
        TextView firstnameInput = (TextView) findViewById(R.id.firstnamePtrInput);
        TextView secondnameInput = (TextView) findViewById(R.id.lastnamePtrInput);
        TextView phoneInput = (TextView) findViewById(R.id.phonePtrInput);
        Button swapActivityCreateBtn = (Button) findViewById(R.id.swapActivityCreate);

        if(option) {
            mailInput.setVisibility(View.VISIBLE);
            passwordInput.setVisibility(View.VISIBLE);
            passwordConfirmInput.setVisibility(View.VISIBLE);
            firstnameInput.setVisibility(View.VISIBLE);
            secondnameInput.setVisibility(View.VISIBLE);
            phoneInput.setVisibility(View.VISIBLE);
            swapActivityCreateBtn.setVisibility(View.VISIBLE);
        } else {
            mailInput.setVisibility(View.GONE);
            passwordInput.setVisibility(View.GONE);
            passwordConfirmInput.setVisibility(View.GONE);
            firstnameInput.setVisibility(View.GONE);
            secondnameInput.setVisibility(View.GONE);
            phoneInput.setVisibility(View.GONE);
            swapActivityCreateBtn.setVisibility(View.GONE);
        }
    }
}