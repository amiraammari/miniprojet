package com.example.amira.rechercheresterant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ComposentFood extends AppCompatActivity {
    ProgressBar progressBar;
    SeekBar hrisa,salade,fromage,catchap,mayonnaise;
    TextView nomF;
    WifiManager wifiManager;
    WifiInfo wifiInfo;
    Spinner spinner;
    RadioGroup pate,suppliment;
    RadioButton pates,supliments;
    Button demande;
    String num,name;
    int Shrisa,Sfromage,Scatchap,Ssalade,Smayonnaise,idd=0;
    ArrayList<String> list=new ArrayList<String>();
    @SuppressLint({"WifiManagerLeak", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composent_food);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Ajout Demande");
        wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiInfo=wifiManager.getConnectionInfo();
        progressBar=(ProgressBar)findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.GONE);
        nomF=(TextView)findViewById(R.id.nom);
        hrisa=(SeekBar)findViewById(R.id.hrisa);
        fromage=(SeekBar)findViewById(R.id.fromage);
        catchap=(SeekBar)findViewById(R.id.catchap);
        salade=(SeekBar)findViewById(R.id.salade);
        mayonnaise=(SeekBar)findViewById(R.id.mayonnaise);
        spinner=(Spinner)findViewById(R.id.spinner);
        pate=(RadioGroup)findViewById(R.id.pate);
        suppliment=(RadioGroup)findViewById(R.id.supl);
        demande=(Button)findViewById(R.id.valide);
        SharedPreferences loginId = getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
        for(int i=1;i<loginId.getInt("numtable",0);i++)
        list.add(i+"");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        spinner.setAdapter(adapter);
        Intent intent = getIntent();
        idd=intent.getIntExtra("id",0);
        if (idd!=0){GetDemande(idd);}
        name=intent.getStringExtra("nom");
        nomF.setText(name);
        hrisa.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Shrisa=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        salade.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Ssalade=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        mayonnaise.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Smayonnaise=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        catchap.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Scatchap=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        fromage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Sfromage=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                num=list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Connection cic = new Connection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            //if (wifiInfo.getSSID().equals('"'+Information.restoB.getString("wifi")+'"')) {
            demande.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int fid = pate.getCheckedRadioButtonId();
                    pates = (RadioButton) findViewById(fid);
                    int sid = suppliment.getCheckedRadioButtonId();
                    supliments = (RadioButton) findViewById(sid);
                    String p="";
                    String s="";
                    try {
                        s= supliments.getText().toString();
                    }catch (Exception e){}
                    try {
                        p= pates.getText().toString();
                        SharedPreferences loginId = getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
                        Ajout(Shrisa, Sfromage, Ssalade, Smayonnaise, Scatchap, p, s, num, loginId.getInt("id",0), name, 0);
                        Intent intent = new Intent(ComposentFood.this, MenuFoud.class);
                        startActivity(intent);
                    }catch (Exception e){
                            Toast.makeText(ComposentFood.this, "Selectionner le Pate", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //}else {Toast.makeText(ComposentFood.this,"Connecter sur le wifi de la resto",Toast.LENGTH_SHORT).show();}
        }
    }

    public void GetDemande(int idd) {
        Connection cic = new Connection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "http://amiraammeri1.000webhostapp.com/restau/demande.php?idd="+idd,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonResponse = jsonArray.getJSONObject(0);
                                JSONArray jsonArray_usersS = jsonResponse.getJSONArray("all_user");
                                JSONObject responsS = jsonArray_usersS.getJSONObject(0);
                                if(responsS.getInt("etat")>2){
                                    Intent intent = new Intent(ComposentFood.this, Demande.class);
                                    Toast.makeText(ComposentFood.this, "votre demande est en cour", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                                hrisa.setProgress(Integer.parseInt(responsS.getString("hrissa")));
                                fromage.setProgress(Integer.parseInt(responsS.getString("fromage")));
                                salade.setProgress(Integer.parseInt(responsS.getString("salade")));
                                catchap.setProgress(Integer.parseInt(responsS.getString("catchap")));
                                mayonnaise.setProgress(Integer.parseInt(responsS.getString("mayonnaise")));
                                progressBar.setVisibility(View.INVISIBLE);

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

    void Ajout(int Shrisa,int Sfromage,int Ssalade,int Smayonnaise,int Scatchap,String p,String s,String num,int id,String name,int etat) {
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            JSONObject json = new JSONObject();
            json.put("numtable", num);
            json.put("name", name);
            json.put("pate", p);
            json.put("supliment", s);
            json.put("hrissa", Shrisa);
            json.put("fromage", Sfromage);
            json.put("salade", Ssalade);
            json.put("catchap", Scatchap);
            json.put("mayonnaise", Smayonnaise);
            json.put("idf", id);
            json.put("etat", etat);
            SharedPreferences loginId = getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
            json.put("mac", loginId.getString("mac",""));
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,10000);
            HttpConnectionParams.setSoTimeout(httpParams, 10000);
            HttpClient client = new DefaultHttpClient(httpParams);
            String url = "http://amiraammeri1.000webhostapp.com/restau/ajouteDemande.php";
            HttpPost request = new HttpPost(url);
            request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
            request.setHeader("json", json.toString());
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                Log.i("Read from server", result);
            }
        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(),Toast.LENGTH_LONG).show(); }
    }

    private String convertStreamToString(InputStream is) {
        ByteArrayOutputStream oas = new ByteArrayOutputStream();
        copyStream(is, oas);
        String t = oas.toString();
        try {
            oas.close(); oas = null;
        } catch (IOException e) { e.printStackTrace(); }
        return t;
    }

    private void copyStream(InputStream is, ByteArrayOutputStream os) { final int buffer_size = 1024;
        try
        { byte[] bytes=new byte[buffer_size];
            for(;;)
            { int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count); }
        }
        catch(Exception ex){}
    }

}
