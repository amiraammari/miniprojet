package com.example.amira.rechercheresterant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
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
import java.util.HashMap;
import java.util.List;

public class Demande extends AppCompatActivity {

    ExpandaleListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Global>> listDataChild;
    List<Global> demande = new ArrayList<>();
    Global global;
    ProgressBar progressBar;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Demande");
        progressBar=(ProgressBar)findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.GONE);
        global= new Global();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataHeader.add("Votre Demande");
        expListView = (ExpandableListView) findViewById(R.id.expe);
        SharedPreferences loginId = getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
        GetDemande(loginId.getString("mac",""));
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(Demande.this, Renouvellement.class);
                Global g= listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                intent.putExtra("id",g.getId());
                startActivity(intent);
                return true;
            }
        });
    }

    public void GetDemande(String mac) {
        Connection cic = new Connection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "http://amiraammeri1.000webhostapp.com/restau/demandes.php?mac="+mac+"&idf="+1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonResponse = jsonArray.getJSONObject(0);
                                JSONArray jsonArray_usersS = jsonResponse.getJSONArray("all_user");
                                for (int i = 0; i < jsonArray_usersS.length(); i++) {
                                    JSONObject responsS = jsonArray_usersS.getJSONObject(i);
                                    demande.add(new Global(responsS.getString("nom"),responsS.getInt("id")));
                                }
                                listDataChild.put(listDataHeader.get(0), demande);
                                listAdapter = new ExpandaleListAdapter(Demande.this, listDataHeader,listDataChild);
                                expListView.setAdapter(listAdapter);
                                expListView.expandGroup(0);
                                progressBar.setVisibility(View.INVISIBLE);
                            } catch (JSONException e) {
                                Toast.makeText(Demande.this, "Vous n'a pas un demande", Toast.LENGTH_SHORT).show();
                                Toast.makeText(Demande.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Demande.this, Information.class);
                                startActivity(intent);
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

}
