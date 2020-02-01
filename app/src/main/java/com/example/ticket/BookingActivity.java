package com.example.ticket;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

public class BookingActivity extends AppCompatActivity {
    private Spinner spinnerPlace, spinnerTime, spinnerSex;
    private String place_url = "http://192.168.43.11/Bus/api/getplace.php";
    private String time_url = "http://192.168.43.11/Bus/api/getTime.php";
    private String pass_url = "http://192.168.43.11/Bus/api/getPassDetail.php";
    private List<String> data = new ArrayList<>();
    private List<String> data1 = new ArrayList<>();
    private String name, no, route, place, destination, placeId;
    private String start, end, t_id;
    private EditText editTextTic, editTextAdd;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private AlertDialog.Builder builder;
    private final String NOTIFICATION_CHANNEL = "booking notification";
    private final int NOTIFICATION_ID = 001;
    private static int NOTIFICATION_TIME = 3000;
    private String tic_no, address, placeS, sexS, timeS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        spinnerPlace = findViewById(R.id.placeSpinner);
        spinnerTime = findViewById(R.id.timeSpinner);
        spinnerSex = findViewById(R.id.sexSpinner);
        editTextTic = findViewById(R.id.ticNumber);
        editTextAdd = findViewById(R.id.passAdd);
        builder = new AlertDialog.Builder(this);

        String[] value = getIntent().getStringArrayExtra("value");
        assert value != null;
        name = value[0];
        no = value[1];
        route = value[2];

        getPlacespin();
        getSexspin();
        getTimeSpin();
    }

    private void getPlacespin() {
        //Code for Spinner Place

        //Code for Volley,StringRequest.....

        if (route.equals("1")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, place_url, new Response.Listener<String>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(String response) {

                    Log.d("res", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("jsonObject", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("placeList");
                        data.add(0, "Select Place");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject Object = jsonArray.getJSONObject(i);
                            place = Object.getString("place");
                            destination = Object.getString("desti");
                            placeId = Object.getString("id");
                            Log.d("sha", place + destination);
                            data.add(place + "-" + destination);
                            adapter1 = new ArrayAdapter<>(BookingActivity.this, android.R.layout.simple_spinner_item, data);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerPlace.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged();


                        }


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
                    return params;
                }
                //End Code For Send Data's to Server
            };
            Mysingleton.getInstance(BookingActivity.this).addToRequest(stringRequest);
        }
        //End Code for Volley,StringRequest.....

        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, place_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("res", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("jsonObject", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("placeList");
                        data.add(0, "Select Place");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject Object = jsonArray.getJSONObject(i);
                            place = Object.getString("place");
                            destination = Object.getString("desti");
                            placeId = Object.getString("id");
                            Log.d("sha", place + destination);
                            data.add(destination + "-" + place);

                            adapter1 = new ArrayAdapter<>(BookingActivity.this, android.R.layout.simple_spinner_item, data);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerPlace.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged();


                        }


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
                    return params;
                }
                //End Code For Send Data's to Server
            };
            Mysingleton.getInstance(BookingActivity.this).addToRequest(stringRequest);


        }
        //End Code for Volley,StringRequest.....

        //Spinner Code for getting values
        spinnerPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (!spinnerPlace.getSelectedItem().toString().equals("Select Route")) {

                    //spinItemget = adapterView.getItemAtPosition(position).toString();
                } else {

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Spinner Code for getting values

    }

    private void getSexspin() {

        //Code for Spinner Sex
        List<String> number = new ArrayList<>();
        number.add(0, "Select Sex");
        number.add("Male");
        number.add("Female");
        number.add("TransGender");
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, number);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(adapter);

        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (adapterView.getItemAtPosition(position).equals("Select Sex")) {

                } else {
                    //spinItemSex = adapterView.getItemAtPosition(position).toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Code for Spinner Sex
    }

    public void getTimeSpin() {
        //Code for Spinner Place

        //Code for Volley,StringRequest.....

        if (route.equals("1")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, time_url, new Response.Listener<String>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(String response) {

                    Log.d("res", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("jsonObject", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("TimeList");
                        data1.add(0, "Select Time");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject Object = jsonArray.getJSONObject(i);
                            start = Object.getString("start");
                            end = Object.getString("end");
                            t_id = Object.getString("id");
                            Log.d("sha", start + end);
                            data1.add(start + "-" + end);
                            adapter2 = new ArrayAdapter<>(BookingActivity.this, android.R.layout.simple_spinner_item, data1);
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerTime.setAdapter(adapter2);
                            adapter2.notifyDataSetChanged();


                        }


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
                    return params;
                }
                //End Code For Send Data's to Server
            };
            Mysingleton.getInstance(BookingActivity.this).addToRequest(stringRequest);
        }
        //End Code for Volley,StringRequest.....

        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, time_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("res", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("jsonObject", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("TimeList");
                        data1.add(0, "Select Time");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject Object = jsonArray.getJSONObject(i);
                            start = Object.getString("start");
                            end = Object.getString("end");
                            t_id = Object.getString("id");
                            Log.d("sha", start + end);
                            data1.add(end + "-" + start);

                            adapter2 = new ArrayAdapter<>(BookingActivity.this, android.R.layout.simple_spinner_item, data1);
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerTime.setAdapter(adapter2);
                            adapter2.notifyDataSetChanged();


                        }


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
                    return params;
                }
                //End Code For Send Data's to Server
            };
            Mysingleton.getInstance(BookingActivity.this).addToRequest(stringRequest);


        }
        //End Code for Volley,StringRequest.....

        //Spinner Code for getting values
        spinnerPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (!spinnerPlace.getSelectedItem().toString().equals("Select Route")) {

                    //spinItemget = adapterView.getItemAtPosition(position).toString();
                } else {

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Spinner Code for getting values

    }

    public void Booking(View view) {

        tic_no = editTextTic.getText().toString();
        address = editTextAdd.getText().toString();
        placeS = spinnerPlace.getSelectedItem().toString();
        sexS = spinnerSex.getSelectedItem().toString();
        timeS = spinnerTime.getSelectedItem().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pass_url, new Response.Listener<String>() {
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
                params.put("passname", name);
                params.put("passno", no);
                params.put("seat", tic_no);
                params.put("address", address);
                params.put("place", placeS);
                params.put("sex", sexS);
                params.put("time", timeS);
                return params;
            }
            //End Code For Send Data's to Server
        };
        Mysingleton.getInstance(BookingActivity.this).addToRequest(stringRequest);
    }

    //Code for AlertDialog
    public void displayMes(final String code) {
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (code.equals("1")) {
                    editTextTic.setText("");
                    editTextAdd.setText("");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            notification();
                        }
                    }, NOTIFICATION_TIME);
                }
                if (code.equals("2")) {
                    editTextTic.setText("");
                    editTextAdd.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Code for Notification
    public void notification() {
        notificationChannel();
        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL);
        builder1.setSmallIcon(R.drawable.logo);
        builder1.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder1.setSound(Uri.parse("android.resource://com.example.ticket/" + R.raw.noti));
        builder1.setContentTitle("Booking Notification :");
        builder1.setContentText("BUS NO : PY-7207 \nWelcome " + name + "," + "\n for " + tic_no + " no of person" +
                "\nRoute :" + placeS + "\nTime :" + timeS + "\nTicket Booked");
        //builder1.setSubText("Selected To Third Round...!");
        builder1.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder1.build());

    }

    public void notificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Booking Notification";
            String description = "BUS NO : PY-7207 \nWelcome " + name + "," + "\n for " + tic_no + " no of person" +
                    "\nRoute :" + placeS + "\nTime :" + timeS + "\nTicket Booked";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL, name, importance);
            notificationChannel.setDescription(description);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setSound(Uri.parse("android.resource://com.example.ticket/" + R.raw.noti), att);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);


        }
    }
    //Code for Notification
}
