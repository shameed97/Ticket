package com.example.ticket;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PassengerActivity extends AppCompatActivity {
    private Spinner spinnerRoute;
    private EditText editTextP, editTextN;
    private String name, no, spinItemRoute;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        editTextN = findViewById(R.id.passNo);
        editTextP = findViewById(R.id.passName);
        builder = new AlertDialog.Builder(this);
        spinnerRoute = findViewById(R.id.routeSpinner);

        getRoutespin();
    }

    public void submit(View view) {

        name = editTextP.getText().toString();
        no = editTextN.getText().toString();

        int len=no.length();
        Log.d("check",""+len);
        builder.setTitle("Ticket Information :");

        if (name.equals("") || no.equals("") || spinnerRoute.getSelectedItem().equals("Select Route")) {
            builder.setMessage("Please Enter All The Fields...");
            displayMes("Please Enter All The Fields...");
        }
        else if (!String.valueOf(len).equals("10"))
        {
            builder.setMessage("Number must be 10 character long");
            displayMes("Number must be 10 character long");
        }
        else {

            String[] value = {name, no, spinItemRoute};
            Intent intent = new Intent(PassengerActivity.this, BookingActivity.class);
            intent.putExtra("value", value);
            startActivity(intent);
        }
    }


    public void displayMes(final String code) {
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (code.equals("Please Enter All The Fields...")) {
                    editTextN.setText("");
                    editTextP.setText("");
                }
                if (code.equals("Number must be 10 character long"))
                {
                    editTextN.setText("");
                    editTextP.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void getRoutespin() {

        //Code for Spinner Route
        List<String> number = new ArrayList<>();
        number.add(0, "Select Route");
        for (int i = 1; i <= 2; i++) {
            number.add("" + i);
            final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, number);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRoute.setAdapter(adapter);

            spinnerRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                    if (adapterView.getItemAtPosition(position).equals("Select Route")) {

                    } else {
                        spinItemRoute = adapterView.getItemAtPosition(position).toString();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        //Code for Spinner Route
    }
}
