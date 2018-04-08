package com.example.amira.rechercheresterant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class PlasDuJour extends AppCompatActivity {

    ArrayList<String> listMenu = new ArrayList<>();
    ListView listView ;
    ArrayAdapter<String> arrayAdapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plas_du_jour);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Plas du jour");
        progressBar=(ProgressBar)findViewById(R.id.progressBar8);
        progressBar.setVisibility(View.GONE);
        listView= (ListView) findViewById(R.id.listplas);
        SharedPreferences loginId = getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
        GetMenu(loginId.getInt("id",0));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlasDuJour.this, ComposentFood.class);
                intent.putExtra("nom",parent.getItemAtPosition(position)+"");
                startActivity(intent);
            }
        });
    }

    public void GetMenu(int val) {
        Connection cic = new Connection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "http://amiraammeri1.000webhostapp.com/restau/platdejour.php?id="+val,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonResponse = jsonArray.getJSONObject(0);
                                JSONArray jsonArray_usersS = jsonResponse.getJSONArray("all_user");
                                for (int i = 0; i < jsonArray_usersS.length(); i++) {
                                    JSONObject responsS = jsonArray_usersS.getJSONObject(i);
                                    String m=responsS.getString("nom")+" : "+responsS.getString("sold");
                                    listMenu.add(m);
                                }
                                All_Names();
                                arrayAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.INVISIBLE);
                            } catch (JSONException e) {
                                progressBar.setVisibility(View.INVISIBLE);
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
        arrayAdapter = new ArrayAdapter<>(this, R.layout.itemlistresto,R.id.itemlister,listMenu);
        listView.setAdapter(arrayAdapter);
    }
}
