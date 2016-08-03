package com.facci.conversorsv;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivitySV extends AppCompatActivity {

    final String[]  dat= new String[] {"DOLAR","EURO","PESO MEXICANO"};

    private Spinner monedaActualSV;
    private Spinner monedaCambioSV;
    private EditText valorCambioSV;
    private TextView resultadoSV;

    final private double DolarEuro = 0.87;
    final private double PesoDolar = 0.54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_sv);

        ArrayAdapter<String> adaptado = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,dat);
        monedaActualSV = (Spinner) findViewById(R.id.monedaActualSV);
        monedaActualSV.setAdapter(adaptado);
        monedaCambioSV = (Spinner) findViewById(R.id.monedaCambioSV);
      //  monedaCambioSV.setAdapter(adaptado);

        SharedPreferences preferencias =getSharedPreferences("Mis preferencias",Context.MODE_PRIVATE);
        String tmpMonedaActual = preferencias.getString("monedaActual","");
        String tmpMonedaCambio = preferencias.getString("monedaCambio","");

        if (!tmpMonedaActual.equals("")){
            int indice = adaptado.getPosition(tmpMonedaActual);
            monedaActualSV.setSelection(indice);

        }
        if (!tmpMonedaCambio.equals("")){
            int indice = adaptado.getPosition(tmpMonedaCambio);
            monedaCambioSV.setSelection(indice);

        }
    }

    public void clickConver(View V){
        monedaActualSV = (Spinner) findViewById(R.id.monedaActualSV);
        monedaCambioSV = (Spinner) findViewById(R.id.monedaCambioSV);
        valorCambioSV =(EditText) findViewById(R.id.valorCambioSV);
        resultadoSV =(TextView) findViewById(R.id.resultadoSV);

        String monedaActual= monedaActualSV.getSelectedItem().toString();
        String monedaCambio=monedaCambioSV.getSelectedItem().toString();
        double valorCambio = Double.parseDouble(valorCambioSV.getText().toString());
        double resultados =procesarConver(monedaActual,monedaCambio,valorCambio);

        if (resultados>0){
            resultadoSV.setText(String.format("Por 5%.2f s%, usted recibirá 5%.2f s%",valorCambio,monedaActual,resultados,monedaCambio));
            valorCambioSV.setText("");
            SharedPreferences preferencias =getSharedPreferences("Mis preferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();


            editor.putString("monedaActual",monedaActual);
            editor.putString("monedaCambio",monedaCambio);

            editor.commit();


        } else{
         resultadoSV.setText(String.format("Usted recibirá"));
            Toast.makeText(MainActivitySV.this,"Las opciones elegidas no tienen ningun factor de conversión",Toast.LENGTH_SHORT).show();

        }

    }


    private double procesarConver(String monedaActual, String monedaCambio, double valorCambio){
double resultadoConver = 0;

switch (monedaActual){
    case "DOLAR":
        if (monedaCambio.equals("EURO"))
            resultadoConver = valorCambio*DolarEuro;
        if (monedaCambio.equals("PESO MEXICANO"))
            resultadoConver = valorCambio/ PesoDolar;
        break;
    case "EURO":
        if (monedaCambio.equals("DOLAR"))
            resultadoConver = valorCambio/ DolarEuro;
        break;
    case  "PESO MEXICANO":
        if (monedaCambio.equals("DOLAR"))
            resultadoConver = valorCambio * PesoDolar;
        break;
}
        return resultadoConver;
    }
}
