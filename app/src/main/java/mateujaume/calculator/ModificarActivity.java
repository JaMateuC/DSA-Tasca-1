package mateujaume.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ModificarActivity extends AppCompatActivity {

    private String operationStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle operation = getIntent().getExtras();
        operationStr = operation.getString("operation");

        TextView modifyOper = findViewById(R.id.textOper);

        modifyOper.setText(operationStr);

        addButtonModificarListener();
        addButtonEsborrarListener();

    }

    /*
    Boton que devuelve a la operacion prrincipal la operacion a modificar
     */
    public void addButtonModificarListener() {
        Button buttonModificar = findViewById(R.id.buttonModificar);
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intres = getIntent();
                intres.putExtra("operationStr",operationStr);
                setResult(RESULT_OK, intres);
                finish();
            }
        });
    }

    /*
    Boton qque devuelve la operacion al historial para poder ser eliminada
     */
    public void addButtonEsborrarListener() {
        Button buttonEsborrar = findViewById(R.id.buttonEsborrar);
        buttonEsborrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intres = getIntent();
                intres.putExtra("operationStr",operationStr);
                setResult(2, intres);
                setResult(2,intres);
                finish();
            }
        });
    }

}
