package com.example.amira.rechercheresterant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.amira.rechercheresterant.R.id.imageView;


public class Information extends AppCompatActivity {


    private SharedPreferences restoId;
    TextView nom,ouverte;
    WifiManager wifiManager;
    WifiInfo wifiInfo;
    ImageButton avis,contact,menu,plas,image;
    String name,mac;
    ProgressBar progressBar;
    ImageView img;
    Button demande;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Information");
        restoId=getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
        wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiInfo=wifiManager.getConnectionInfo();
        progressBar=(ProgressBar)findViewById(R.id.progressBar6);
        progressBar.setVisibility(View.GONE);
        mac=wifiInfo.getMacAddress();
        SharedPreferences.Editor editor=restoId.edit();
        editor.putString("mac",mac);
        img=(ImageView) findViewById(imageView);
        avis=(ImageButton) findViewById(R.id.avis);
        menu=(ImageButton) findViewById(R.id.menu);
        contact=(ImageButton) findViewById(R.id.contact);
        plas=(ImageButton) findViewById(R.id.plas);
        image=(ImageButton) findViewById(R.id.gallery);
        nom=(TextView) findViewById(R.id.nom);
        ouverte=(TextView) findViewById(R.id.ouverte);
        demande=(Button)findViewById(R.id.demande);
        Intent intent = getIntent();
        name=intent.getStringExtra("nom");
        if(name!=null){
        editor.putString("nom",name);
        }
        editor.apply();
        nom.setText(restoId.getString("nom",""));
        GetRes(restoId.getString("nom",""));
        demande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Information.this, Demande.class);
                startActivity(intent);
            }
        });
    }

    public void GetRes(final String val) {
        Connection cic = new Connection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "http://amiraammeri1.000webhostapp.com/restau/info.php?nom="+val.replaceAll(" ","%20"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonResponse = jsonArray.getJSONObject(0);
                                JSONArray jsonArray_usersS = jsonResponse.getJSONArray("all_user");
                                JSONObject responsS = jsonArray_usersS.getJSONObject(0);
                                final int num=responsS.getInt("id");
                                SharedPreferences.Editor editor=restoId.edit();
                                editor.putInt("id",num);
                                avis.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Information.this, AutreAvis.class);
                                        startActivity(intent);
                                    }
                                });
                                menu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Information.this, MenuFoud.class);
                                        startActivity(intent);
                                    }
                                });
                                image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Information.this, Gallerys.class);
                                        startActivity(intent);
                                    }
                                });
                                plas.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Information.this, PlasDuJour.class);
                                        startActivity(intent);
                                    }
                                });
                                editor.putString("web",responsS.getString("web"));
                                editor.putString("lat",responsS.getDouble("poslat")+"");
                                editor.putString("lag",responsS.getDouble("poslag")+"");
                                editor.putString("email",responsS.getString("email"));
                                editor.putString("adresse",responsS.getString("adresse"));
                                editor.putString("wifi",responsS.getString("wifi"));
                                editor.putInt("numtable",responsS.getInt("numtable"));
                                editor.putString("tel",responsS.getString("tel"));
                                editor.commit();
                                contact.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent teli = new Intent(Information.this, Contact.class);
                                        startActivity(teli);
                                    }
                                });
                                String ouv="ouverture : "+responsS.getString("ouverte");
                                ouverte.setText(ouv);
                                Picasso.with(Information.this).load(responsS.getString("image")).into(img);
                                progressBar.setVisibility(View.INVISIBLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Information.this, "Vous n'a pas un demande", Toast.LENGTH_SHORT).show();

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
