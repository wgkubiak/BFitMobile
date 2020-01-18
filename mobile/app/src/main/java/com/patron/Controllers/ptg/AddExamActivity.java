package com.patron.Controllers.ptg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.patron.Controllers.DownloadJSON;
import com.patron.Controllers.PostExam;
import com.patron.Controllers.global.CreateUserActivity;
import com.patron.Controllers.global.LoginActivity;
import com.patron.R;

public class AddExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("exam_protege");

        final Button addExamBtn = (Button) findViewById(R.id.addExamBtn);
        final TextView weight = (TextView) findViewById(R.id.weightInput);
        final TextView glucose = (TextView) findViewById(R.id.glucoseInput);
        final TextView pressure1 = (TextView) findViewById(R.id.pressureInput1);
        final TextView pressure2 = (TextView) findViewById(R.id.pressureInput2);
        final TextView dataSend = (TextView) findViewById(R.id.dataSend);
        final TextView slash = (TextView) findViewById(R.id.div);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView3);

        addExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(weight.length() < 5 && glucose.length() < 3 && pressure1.length() < 3
                            && pressure2.length() < 3) {
                        if(!weight.getText().toString().equals("")) {
                            if(!glucose.getText().toString().equals("")) {
                                if(!pressure1.getText().toString().equals("")
                                        && !pressure2.getText().toString().equals("") ) {

                                    String protegeWeight = weight.getText().toString() + "kg";
                                    String protegeGlucose = glucose.getText().toString() + "mg/dL";
                                    String protegePressure = pressure1.getText().toString() + "/"
                                            + pressure2.getText().toString() + "mm Hg";

                                    PostExam postExam = new PostExam();
                                    postExam.execute("https://patronapi.herokuapp.com/exams", id, protegeWeight, protegeGlucose,
                                            protegePressure);

                                    Toast toast= Toast.makeText(getApplicationContext(),
                                            "Wpis dodany!", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toast.show();

                                    weight.setVisibility(View.INVISIBLE);
                                    glucose.setVisibility(View.INVISIBLE);
                                    pressure1.setVisibility(View.INVISIBLE);
                                    pressure2.setVisibility(View.INVISIBLE);
                                    slash.setVisibility(View.INVISIBLE);
                                    dataSend.setVisibility(View.VISIBLE);
                                    addExamBtn.setVisibility(View.GONE);

                                    imageView.setImageResource(R.color.colorAccent);
                                    dataSend.setTextColor(Color.WHITE);
                                } else {
                                    Toast toast= Toast.makeText(getApplicationContext(),
                                            "Uzupełnij ciśnienie!", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toast.show();
                                }
                            } else {
                                Toast toast= Toast.makeText(getApplicationContext(),
                                        "Uzupełnij glukozę!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            }
                        } else {
                            Toast toast= Toast.makeText(getApplicationContext(),
                                    "Uzupełnij wagę!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    } else {
                        showToast("Błędna długość danych!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Próba wylogowania!")
                .setMessage("Czy jesteś pewny/a?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AddExamActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Nie", null)
                .show();
    }

    private void showToast(String text) {
        Toast toast= Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}