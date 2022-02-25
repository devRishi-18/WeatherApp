package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView result;
    private EditText ed;
    private String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed= findViewById(R.id.city);
        button= findViewById(R.id.check);
        result= findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city= ed.getText().toString();
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=ebb8215cb853f827e524a7e49281f911";
                JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject array= (JSONObject) response.get("main");
                            //JSONArray array=  response.getJSONArray("main");

                            String s= array.getString("temp");
                            Double ans= Double.parseDouble(s)-270.15;
                            result.setText(ans.toString().substring(0,5)+" °C");
                            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            result.setText(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        result.setText(error.toString());
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });
    }
//    public void get(View v){
//
//    }
}