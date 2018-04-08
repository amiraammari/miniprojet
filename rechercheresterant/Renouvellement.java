package com.example.amira.rechercheresterant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
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

public class Renouvellement extends AppCompatActivity {

    ProgressBar progressBar;
    SeekBar hrisa,salade,fromage,catchap,mayonnaise;
    TextView nomF,numtab;
    RadioGroup pate,suppliment;
    RadioButton pates,supliments;
    Button demande,annuler;
    String name;
    int Shrisa,Sfromage,Scatchap,Ssalade,Smayonnaise,idd=0;
    ArrayList<String> list=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_renouvellement);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Verifie demande");
        progressBar=(ProgressBar)findViewById(R.id.progressBarR);
        progressBar.setVisibility(View.GONE);
        nomF=(TextView)findViewById(R.id.nom);
        numtab=(TextView)findViewById(R.id.numtab);
        hrisa=(SeekBar)findViewById(R.id.hrisa);
        fromage=(SeekBar)findViewById(R.id.fromage);
        catchap=(SeekBar)findViewById(R.id.catchap);
        salade=(SeekBar)findViewById(R.id.salade);
        mayonnaise=(SeekBar)findViewById(R.id.mayonnaise);
        pate=(RadioGroup)findViewById(R.id.pate);
        suppliment=(RadioGroup)findViewById(R.id.supl);
        demande=(Button)findViewById(R.id.valide);
        annuler=(Button)findViewById(R.id.annuler);
        Intent intent = getIntent();
        idd=intent.getIntExtra("id",0);
        if (idd!=0){GetDemande(idd);}
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
        Connection cic = new Connection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            annuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDemande(idd);
                    suprimer(idd);
                    Intent intent = new Intent(Renouvellement.this, Demande.class);
                    Toast.makeText(Renouvellement.this, "votre demande est annuler", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });

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
                    String num=numtab.getText().toString();
                    try {
                        s= supliments.getText().toString();
                    }catch (Exception e){}
                    try {
                        p= pates.getText().toString();
                        SharedPreferences loginId = getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
                        update(Shrisa, Sfromage, Ssalade, Smayonnaise, Scatchap, p, s, num, loginId.getInt("id",0), name, 0);
                    }catch (Exception e){
                        Toast.makeText(Renouvellement.this, "Selectionner le Pate", Toast.LENGTH_SHORT).show();
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
                                if(responsS.getInt("etat")==2){
                                    Intent intent = new Intent(Renouvellement.this, Demande.class);
                                    Toast.makeText(Renouvellement.this, "votre demande est en cour", Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                }
                                if(responsS.getInt("etat")==1){
                                    Toast.makeText(Renouvellement.this, "votre demande est en attent", Toast.LENGTH_LONG).show();
                                }
                                name=responsS.getString("nom");
                                hrisa.setProgress(Integer.parseInt(responsS.getString("hrissa")));
                                fromage.setProgress(Integer.parseInt(responsS.getString("fromage")));
                                salade.setProgress(Integer.parseInt(responsS.getString("salade")));
                                catchap.setProgress(Integer.parseInt(responsS.getString("catchap")));
                                mayonnaise.setProgress(Integer.parseInt(responsS.getString("mayonnaise")));
                                nomF.setText(responsS.getString("nom"));
                                numtab.setText(responsS.getString("numtable"));
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

    void update(int Shrisa,int Sfromage,int Ssalade,int Smayonnaise,int Scatchap,String p,String s,String num,int id,String name,int etat) {
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
            json.put("id", idd);
            json.put("etat", etat);
            SharedPreferences loginId = getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
            json.put("mac", loginId.getString("mac",""));
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,10000);
            HttpConnectionParams.setSoTimeout(httpParams, 10000);
            HttpClient client = new DefaultHttpClient(httpParams);
            String url = "http://amiraammeri1.000webhostapp.com/restau/updateR.php";
            HttpPost request = new HttpPost(url);
            request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
            request.setHeader("json", json.toString());
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                Log.i("Read from server", result);
                Toast.makeText(this, result,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Renouvellement.this, Demande.class);
                startActivity(intent);
            }else Toast.makeText(this, "entity null",Toast.LENGTH_LONG).show();
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

    protected void suprimer(int id) {
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            JSONObject json = new JSONObject();
            json.put("ids", id);
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,10000);
            HttpConnectionParams.setSoTimeout(httpParams, 10000);
            HttpClient client = new DefaultHttpClient(httpParams);
            String url = "http://amiraammeri1.000webhostapp.com/restau/suprimer.php";
            HttpPost request = new HttpPost(url);
            request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
            request.setHeader("json", json.toString());
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
// If the response does not enclose an entity, there is no need
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                Log.i("Read from server", result);
            }
        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(),Toast.LENGTH_LONG).show(); }
    }

}
