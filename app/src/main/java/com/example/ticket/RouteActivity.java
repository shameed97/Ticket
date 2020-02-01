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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteActivity extends AppCompatActivity {

    private Spinner spinnerRoute;
    private EditText editTextU, editTextP;
    private String name, password, spinItemRoute;
    private String con_url = "http://192.168.43.11/Bus/api/getLoginDetail.php";
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        spinnerRoute = findViewById(R.id.routeSpinner);
        editTextU = findViewById(R.id.username);
        editTextP = findViewById(R.id.password);
        builder = new AlertDialog.Builder(this);

        getRoutespin();
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

    public void submit(View view) {
        name = editTextU.getText().toString();
        password = editTextP.getText().toString();

        builder.setTitle("Login Information :");

        if (name.equals("") || password.equals("") || spinnerRoute.getSelectedItem().equals("Select Route")) {
            builder.setMessage("Please Enter All The Fields...");
            displayMes("Please Enter All The Fields...");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, con_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("res", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Log.d("jsonObject", jsonArray.toString());
                        JSONObject Object = jsonArray.getJSONObject(0);
                        String code = Object.getString("code");
                        String message = Object.getString("message");
                        Log.d("sha", code + message);
                        builder.setMessage(message);
                        displayMes(code);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("sha", e.toString());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("kee", error.toString());
                    error.printStackTrace();

                }
            }) {
                //Code For Send Data's to Server
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", name);
                    params.put("password", password);
                    return params;
                }
                //End Code For Send Data's to Server
            };
            Mysingleton.getInstance(RouteActivity.this).addToRequest(stringRequest);
        }
    }


    public void displayMes(final String code) {
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (code.equals("Please Enter All The Fields...")) {
                    editTextU.setText("");
                    editTextP.setText("");
                }
                if (code.equals("Login Success...")) {
                    editTextU.setText("");
                    editTextP.setText("");
                    String[] value = {name, spinItemRoute};
                    Intent intent = new Intent(RouteActivity.this, TicketActivity.class);
                    intent.putExtra("value", value);
                    startActivity(intent);

                }
                if (code.equals("Login Failed...")) {
                    editTextU.setText("");
                    editTextP.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
