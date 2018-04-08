package com.example.amira.rechercheresterant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AutreAvis extends AppCompatActivity {

    ProgressBar progressBar;
    String nomavis="",comm="";
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> listavis = new ArrayList<>();
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autre_avis);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("les avis");
        SharedPreferences loginId = getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
        int id= loginId.getInt("id",0);
        ImageButton avis=(ImageButton) findViewById(R.id.avis);
        listView= (ListView) findViewById(R.id.avisinf) ;
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        Getavis(id);

        avis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AutreAvis.this, Avis.class);
                startActivity(intent);
            }
        });
    }

    public void Getavis(final int ida) {
        Connection cic = new Connection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();

        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "http://amiraammeri1.000webhostapp.com/restau/avisResto.php?idf="+ida,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonResponse = jsonArray.getJSONObject(0);
                                JSONArray jsonArray_usersS = jsonResponse.getJSONArray("all_user");
                                for (int i = 0; i < jsonArray_usersS.length(); i++) {
                                    JSONObject responsS = jsonArray_usersS.getJSONObject(i);
                                    nomavis=responsS.getString("nom");
                                    comm=responsS.getString("commentaire");
                                    listavis.add(nomavis+"\n"+comm);
                                }
                                All_Names();
                                progressBar.setVisibility(View.INVISIBLE);
                                arrayAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
            stringRequest.setShouldCache(false);

        }
    }

    public void All_Names() {
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.listdemande,R.id.childD,listavis);
        listView.setAdapter(arrayAdapter);
    }
}
