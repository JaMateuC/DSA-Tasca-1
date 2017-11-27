package mateujaume.calculator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Calculator extends AppCompatActivity {

    private Spinner operSpinner;
    private ArrayAdapter<CharSequence> adapter;
    public String operString;
    public ArrayList<String> operationList = null;
    public String lastOper;
    public Boolean modificar = false;
    public int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        //creacion del spinner para escoger el tipo de operacion
        operSpinner = findViewById(R.id.oper_spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.oper_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operSpinner.setAdapter(adapter);
        operSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                operString = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        operationList  = new ArrayList<String>();

        addButtonEqualListener();
        addButtonCListener();
        addButtonHistorialListener();

    }

    /*
    funcion para ejecutar la operacon deseada
     */
    public void addButtonEqualListener(){

        Button buttonEqual = findViewById(R.id.buttonEqual);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String resultTXT;
                EditText num1 = findViewById(R.id.num1);
                EditText num2 = findViewById(R.id.num2);
                TextView resultString = findViewById(R.id.result);

                String num1Txt = num1.getText().toString();
                String num2Txt = num2.getText().toString();

                    if (!num1Txt.isEmpty() && !num2Txt.isEmpty()) {

                        try {
                            int num1Val = Integer.parseInt(num1Txt);
                            int num2Val = Integer.parseInt(num2Txt);
                            int result = 0;

                            switch (operString) {
                                case "+":
                                    result = num1Val + num2Val;
                                    break;
                                case "-":
                                    result = num1Val - num2Val;
                                    break;
                                case "x":
                                    result = num1Val * num2Val;
                                    break;
                                case "/":
                                    result = num1Val / num2Val;
                                    break;
                            }
                            result = result * 100;
                            result = Math.round(result);
                            result = result / 100;
                            resultTXT = Integer.toString(result);

                            resultString.setText(resultTXT);

                            lastOper = num1Val + " " + operString + " " + num2Val + " = " + resultTXT;
                            //if para saber si es una operacion nueva o que se tiene que modificar
                            if(modificar){
                                operationList.set(position, lastOper);
                                modificar = false;
                            }else {
                                operationList.add(lastOper);
                            }

                        }catch (NumberFormatException e){
                            Toast.makeText(getBaseContext(), "Error: No son valores enteros", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        Toast.makeText(getBaseContext(), "Error: Campos vacios", Toast.LENGTH_LONG).show();

                    }
            }

        });

    }


    /*
    Boton que borra lo campos de la pagina principal
     */
    public void addButtonCListener(){

        Button buttonEqual = findViewById(R.id.buttonC);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText num1 = findViewById(R.id.num1);
                EditText num2 = findViewById(R.id.num2);
                TextView resultString = findViewById(R.id.result);

                num1.setText("");
                num2.setText("");
                resultString.setText("0");


            }
        });

    }

    /*
    Boton que abre la actividad de Historial
     */
    public void addButtonHistorialListener(){

        Button buttonHistorial = findViewById(R.id.buttonHistorial);
        buttonHistorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent historialIntent = new Intent(v.getContext(),HistorialActivity.class);
                historialIntent.putExtra("operationList", operationList); //añade la lista de operaciones que se han hecho de momento
                startActivityForResult(historialIntent,1);

            }
        });

    }

    /*
    Funciones que se ejecutan cuando se vuelve a esta actividad
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        /*
        Codigo que devulve cuando elk usuario quiere modificar una operacion del historial
         */
        if((requestCode == 1) && (resultCode == Activity.RESULT_OK)){
            Bundle resultAct = data.getExtras();
            String num1R = resultAct.getString("num1");
            String num2R = resultAct.getString("num2");
            String resultR = resultAct.getString("resultR");
            String operator = resultAct.getString("operator");
            EditText num1ET = findViewById(R.id.num1);
            EditText num2ET = findViewById(R.id.num2);
            TextView resultTV = findViewById(R.id.result);
            Spinner operatorSpin = findViewById(R.id.oper_spinner);
            num1ET.setText(num1R);
            num2ET.setText(num2R);
            resultTV.setText(resultR);
            int p = Integer.parseInt(resultAct.getString("position"));
            position = p - 1;
            this.modificar = true;

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.oper_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            operatorSpin.setAdapter(adapter);
            int spinnerPosition = adapter.getPosition(operator);
            operatorSpin.setSelection(spinnerPosition);
        }
        /*
        If que se devulve cuando se vulve del historial pero no se quiere modificar ningunna operacion
         */
        else if((requestCode == 1) && (resultCode == 2)){

            Bundle resultAct = data.getExtras();
            ArrayList<String> operationListReturn = resultAct.getStringArrayList("opertaionList");
            operationList.clear();
            operationList.addAll(operationListReturn);

        }

    }

}




