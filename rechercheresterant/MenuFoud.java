package com.example.amira.rechercheresterant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuFoud extends AppCompatActivity {

    ProgressBar progressBar;
    ExpandaleListeAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<ItemsFood>> listDataChild;
    List<ItemsFood> sand = new ArrayList<ItemsFood>();
    List<ItemsFood> pizza = new ArrayList<ItemsFood>();
    List<ItemsFood> plas = new ArrayList<ItemsFood>();
    List<ItemsFood> spag = new ArrayList<ItemsFood>();
    ArrayList<String> list=new ArrayList<>();
    SharedPreferences loginId;
    List<ItemsFood> famille = null;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_foud);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Menu");
        progressBar=(ProgressBar)findViewById(R.id.progressBar7);
        progressBar.setVisibility(View.GONE);
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        expListView = (ExpandableListView) findViewById(R.id.exp);
        loginId = getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
        int id= loginId.getInt("id",0);
        try {
            String url = "http://amiraammeri1.000webhostapp.com/restau/getFamille.php?idf="+id;
            new famille().execute(url);
        }catch (Exception e){
            Log.e("log_tag", "Error in url " + e.toString());}
    }


    public void GetMenu(final int val) {
        Connection cic = new Connection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "http://amiraammeri1.000webhostapp.com/restau/menuClient.php?id="+val,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonResponse = jsonArray.getJSONObject(0);
                                JSONArray jsonArray_usersS = jsonResponse.getJSONArray("all_user");
                                for (int j = 0; j < list.size(); j++) {
                                    listDataHeader.add(list.get(j));
                                    famille = new ArrayList<>();
                                    for (int i = 0; i < jsonArray_usersS.length(); i++) {
                                        JSONObject responsS = jsonArray_usersS.getJSONObject(i);
                                        if (responsS.getString("nom").contains(list.get(j)))
                                            famille.add(new ItemsFood(responsS.getString("nom") + " : " + responsS.getString("sold")));
                                    }
                                    listDataChild.put(listDataHeader.get(j), famille);
                                }

                                listAdapter = new ExpandaleListeAdapter(MenuFoud.this, listDataHeader, listDataChild);
                                expListView.setAdapter(listAdapter);
                                listAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.INVISIBLE);
                                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                                    @Override
                                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                        Intent intent = new Intent(MenuFoud.this, ComposentFood.class);
                                        ItemsFood s = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                                        intent.putExtra("nom", s.nom);
                                        startActivity(intent);
                                        return true;
                                    }
                                });
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

    String result;
    private class famille extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {}
        @Override
        protected String  doInBackground(String... params) {
            InputStream isr = null;
            try{
                String URL=params[0];
                java.net.URL url = new URL( URL.replaceAll(" ","%20"));
                URLConnection urlConnection = url.openConnection();
                isr  = new BufferedInputStream(urlConnection.getInputStream());
            }
            catch(Exception e){
                Log.e("log_tag", "Error in http connection " + e.toString());
            }
            try{
                assert isr != null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                isr.close();
                result=sb.toString();
            }
            catch(Exception e){
                Log.e("log_tag", "Error  converting result " + e.toString());
            }
            return null;
        }
        protected void onPostExecute(String  result2){
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    list.add(json.getString("nom"));
                }
                GetMenu(loginId.getInt("id",0));
            }catch (Exception e) {Log.e("log_tag", result+"\nError Parsing Data"+e.toString());
            }
        }
    }
}
