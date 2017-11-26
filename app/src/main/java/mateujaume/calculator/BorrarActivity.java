package mateujaume.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class BorrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addButtonSiListener();
        addButtonNoListener();

    }

    public void addButtonSiListener() {
        Button buttonSi = findViewById(R.id.buttonSi);
        buttonSi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });
    }

    public void addButtonNoListener() {
        Button buttonNo = findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

}
