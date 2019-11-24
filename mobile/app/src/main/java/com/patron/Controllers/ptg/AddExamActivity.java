package com.patron.Controllers.ptg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.patron.R;

import org.w3c.dom.Text;

public class AddExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        Button addExamBtn = (Button) findViewById(R.id.addExamBtn);
        addExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSend();
            }
        });
    }

    void dataSend() {
        Button addExamBtn = (Button) findViewById(R.id.addExamBtn);
        TextView glucoseTipTxt = (TextView) findViewById(R.id.glucoseTip);
        TextView glucoseInput = (TextView) findViewById(R.id.glucoseInput);
        TextView pressureTipTxt = (TextView) findViewById(R.id.pressureTip);
        TextView pressureInput1 = (TextView) findViewById(R.id.pressureInput1);
        TextView pressureInput2 = (TextView) findViewById(R.id.pressureInput2);
        TextView slashTxt = (TextView) findViewById(R.id.slashTxt);
        TextView examsHeaderTxt = (TextView) findViewById(R.id.examsHeader);
        TextView weightTxt = (TextView) findViewById(R.id.weightTip);
        TextView weightInput = (TextView) findViewById(R.id.weightInput);

        TextView dataSendTxt = (TextView) findViewById(R.id.dataSendTxt);

        addExamBtn.setVisibility(View.GONE);
        glucoseTipTxt.setVisibility(View.GONE);
        glucoseInput.setVisibility(View.GONE);
        pressureTipTxt.setVisibility(View.GONE);
        pressureInput1.setVisibility(View.GONE);
        pressureInput2.setVisibility(View.GONE);
        slashTxt.setVisibility(View.GONE);
        examsHeaderTxt.setVisibility(View.GONE);
        weightTxt.setVisibility(View.GONE);
        weightInput.setVisibility(View.GONE);

        dataSendTxt.setVisibility(View.VISIBLE);
    }
}

