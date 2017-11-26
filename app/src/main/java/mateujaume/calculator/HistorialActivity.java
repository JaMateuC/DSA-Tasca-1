package mateujaume.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistorialActivity extends AppCompatActivity {

    private ArrayList<String> listOperationInit;
    private ArrayList<String> listOperation;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.listOperationInit = new ArrayList<>();
        this.listOperation = new ArrayList<>();

        Bundle extras = getIntent().getExtras();

        this.listOperationInit.addAll(extras.getStringArrayList("operationList"));
        ListView operList = findViewById(R.id.listaOperations);

        adapter = new ArrayAdapter<>(
                this,android.R.layout.simple_list_item_1,
                listOperation);

        operList.setAdapter(adapter);

        listCreator();

        adapter.notifyDataSetChanged();

        addButtonExitListener();

        itemClick(operList);

        addButtonEsborrarListener();

    }

    public void addButtonExitListener(){

        Button buttonEqual = findViewById(R.id.buttonExit);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intres = getIntent();
                intres.putExtra("opertaionList", listOperationInit);
                setResult(2,intres);
                finish();
            }
        });

    }

    public void addButtonEsborrarListener(){

        Button buttonBorrar = findViewById(R.id.buttonBorrar);
        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent borrarIntent = new Intent(v.getContext(),BorrarActivity.class);
                startActivityForResult(borrarIntent,2);
            }
        });

    }

    public void listCreator(){

        int count = 1;

        for(String oper: listOperationInit){

            listOperation.add(count + " : " + oper);

            count++;

        }

    }

    public void itemClick(ListView operList){

        operList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String operation = (String)adapterView.getItemAtPosition(i);

                Intent operModi = new Intent(view.getContext(),ModificarActivity.class);
                operModi.putExtra("operation",operation);
                startActivityForResult(operModi,1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            Bundle modif = data.getExtras();
            String operation = modif.getString("operationStr");
            String[] splited = operation.split("\\s+");

            String position = splited[0];
            String num1 = splited[2];
            String operator = splited[3];
            String num2 = splited[4];
            String resultR = splited[6];

            Intent intres = getIntent();
            intres.putExtra("position", position);
            intres.putExtra("num1", num1);
            intres.putExtra("num2", num2);
            intres.putExtra("resultR", resultR);
            intres.putExtra("operator", operator);
            setResult(RESULT_OK, intres);
            finish();
        }else if(requestCode == 1 && resultCode == 2){

            Bundle modif = data.getExtras();
            String operation = modif.getString("operationStr");
            listOperationInit.remove(listOperation.indexOf(operation));
            listOperation.clear();
            listCreator();
            adapter.notifyDataSetChanged();

        }else if(requestCode == 2 && resultCode == 1){

            listOperationInit.clear();
            listOperation.clear();
            listCreator();
            adapter.notifyDataSetChanged();

        }
    }

}
