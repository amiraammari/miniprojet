package com.example.amira.rechercheresterant;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
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

public class ListeRes extends AppCompatActivity  implements SearchView.OnQueryTextListener,SwipeRefreshLayout.OnRefreshListener {

    ArrayList<String> listIndex_Search = new ArrayList<String>();
    private ArrayList<String> listNom = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressBar progressBar;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_res);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Les restaurant");

        progressBar=(ProgressBar)findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        listView= (ListView) findViewById(R.id.listres);
        mSwipeRefreshLayout.setOnRefreshListener(ListeRes.this);
        GetRes();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
                Intent intent = new Intent(ListeRes.this, Information.class);
                intent.putExtra("nom",parent.getItemAtPosition(position)+"");
                startActivity(intent);
            }
        });
        }
    @Override
    public void onRefresh() {
        //appellé lors de l'action Pull To Refresh

        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void run() {
                //appellé après 2000 ms

                //vide la liste
                listNom.clear();

                //puis ajoute les nouveaux elements
                GetRes();

                //annonce à l'adapter que les données ont changés
                //arrayAdapter.notifyDataSetChanged();

                //avertie le SwipeRefreshLayout que la mise à jour a été effectuée
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }

    public void GetRes() {
        Connection cic = new Connection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "http://amiraammeri1.000webhostapp.com/restau/select.php",

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonResponse = jsonArray.getJSONObject(0);
                                JSONArray jsonArray_usersS = jsonResponse.getJSONArray("all_user");
                                for (int i = 0; i < jsonArray_usersS.length(); i++) {
                                    JSONObject responsS = jsonArray_usersS.getJSONObject(i);
                                    listNom.add(responsS.getString("nom"));
                                }
                                progressBar.setVisibility(View.INVISIBLE);

                                All_Names();
                                arrayAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                Toast.makeText(ListeRes.this, "refrech le page", Toast.LENGTH_SHORT).show();
                                GetRes();
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
        arrayAdapter = new ArrayAdapter<>(this, R.layout.itemlistresto,R.id.itemlister,listNom);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_searchable));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        search(newText);
        return false;
    }

    public void search(String word) {
        // if (word.length() > 1) {
        listIndex_Search.clear();
        for (int i = 0; i < listNom.size(); i++) {
            String name = listNom.get(i).toString();
            if (name.contains(word)) {
                listIndex_Search.add(name);
            }
        }
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.itemlistresto,R.id.itemlister, listIndex_Search);
        listView.setAdapter(arrayAdapter);

        // }
    }

}