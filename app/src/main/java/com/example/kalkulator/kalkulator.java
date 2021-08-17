package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class kalkulator extends AppCompatActivity {


    int[] btnNumerik = {R.id.btn_nol, R.id.btn_satu, R.id.btn_dua, R.id.btn_tiga, R.id.btn_empat,
    R.id.btn_lima, R.id.btn_enam, R.id.btn_tujuh, R.id.btn_delapan, R.id.btn_sembilan};

    int[] btnOperator = {R.id.btn_tambah, R.id.btn_kurang, R.id.btn_kali, R.id.btn_bagi};

    TextView txt_result;
    boolean stateError;
    boolean lastNumerik;
    boolean lastDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkulator);

        txt_result = findViewById(R.id.txt_result);
        setNumerikOnClickListener();
        setOperatorOnClickListener();
    }

    public void setNumerikOnClickListener() {
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                if (stateError) {
                    txt_result.setText(btn.getText());
                    stateError = false;
                } else {
                    txt_result.append(btn.getText());
                }
                lastNumerik = true;
            }
        };
        for (int id : btnNumerik) {
            findViewById(id).setOnClickListener(click);
        }
    }
        private void setOperatorOnClickListener(){
            View.OnClickListener click = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lastNumerik && !stateError){
                        Button btn = (Button) v;
                        txt_result.append(btn.getText());
                        lastNumerik = false;
                        lastDot = false;
                    }
                }
            };
            for (int id : btnOperator){
                findViewById(id).setOnClickListener(click);
            }
            findViewById(R.id.btn_titik).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lastNumerik && !stateError&&!lastDot){
                        txt_result.append(".");
                        lastNumerik = false;
                        lastDot = true;
                    }
                }
            });
            findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_result.setText("");
                    lastNumerik = false;
                    lastDot = false;
                    stateError = false;
                }
            });
            findViewById(R.id.btn_sama_dengan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEqual();
                }
            });
        }
        private void onEqual(){
            if(lastNumerik && !stateError){
                txt_result.getText().toString();
                String txt = txt_result.getText().toString();
                Expression expression = new ExpressionBuilder(txt).build();
                try {
                    // Calculate the result and display
                    double result = expression.evaluate();
                    txt_result.setText(Double.toString(result));
                    lastDot = true; // Result contains a dot
                } catch (ArithmeticException ex) {
                    // Display an error message
                    txt_result.setText("Error");
                    stateError = true;
                    lastNumerik = false;
            }
        }
    }
}