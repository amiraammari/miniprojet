package com.example.amira.rechercheresterant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Avis extends AppCompatActivity {


    private RadioGroup frg,srg,drg,crg;
    private RadioButton f,s,d,c;
    private EditText nom,comm;
    String fs="",ds="",ss="",cs="",ns="",coms="";
    RatingBar ratingBar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("ajout avis");

        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        frg=(RadioGroup)findViewById(R.id.frg);
        srg=(RadioGroup)findViewById(R.id.srg);
        drg=(RadioGroup)findViewById(R.id.drg);
        crg=(RadioGroup)findViewById(R.id.crg);
        nom=(EditText) findViewById(R.id.nom);
        comm=(EditText)findViewById(R.id.comm);
        Button envoyer = (Button) findViewById(R.id.envoyer);
        Button annuler = (Button) findViewById(R.id.annuler);

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Avis.this, ListeRes.class);
                startActivity(intent);
                finish();
            }
        });
        Connection cic = new Connection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            envoyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int fid = frg.getCheckedRadioButtonId();
                    f = (RadioButton) findViewById(fid);
                    int sid = srg.getCheckedRadioButtonId();
                    s = (RadioButton) findViewById(sid);
                    int did = drg.getCheckedRadioButtonId();
                    d = (RadioButton) findViewById(did);
                    int cid = crg.getCheckedRadioButtonId();
                    c = (RadioButton) findViewById(cid);
                    double ratingValue = ratingBar.getRating();
                    try{fs = f.getText().toString();}catch (Exception ignored){}
                    try{ss = s.getText().toString();}catch (Exception ignored){}
                    try{ds = d.getText().toString();}catch (Exception ignored){}
                    try{cs = c.getText().toString();}catch (Exception ignored){}
                    coms = comm.getText().toString();
                    if(!Objects.equals(nom.getText().toString(), "")){
                        ns = nom.getText().toString();
                        SharedPreferences loginId = getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
                        int id= loginId.getInt("id",0);
                        Ajout(fs, ss, ds, cs, ns, coms, id, ratingValue);
                        Intent intent = new Intent(Avis.this, Information.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Avis.this,"Ecrir votre nom",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

        protected void Ajout(String food,String service,String decore,String cout,String nom,String comm,int id,double rating) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                JSONObject json = new JSONObject();
                json.put("food", food);
                json.put("service", service);
                json.put("decore", decore);
                json.put("cout", cout);
                json.put("nom", nom);
                json.put("commentaire", comm);
                json.put("id", id);
                json.put("evaluation", rating);
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams,10000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                HttpClient client = new DefaultHttpClient(httpParams);
                String url = "http://amiraammeri1.000webhostapp.com/restau/ajoutAvis.php";
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
                    Toast.makeText(this, result,Toast.LENGTH_LONG).show();
                }
            } catch (Throwable t) {
                Toast.makeText(this, "Request failed: " + t.toString(),Toast.LENGTH_LONG).show(); }
        }

        private String convertStreamToString(InputStream is) {
            ByteArrayOutputStream oas = new ByteArrayOutputStream();
            copyStream(is, oas);
            String t = oas.toString();
            try {
                oas.close();
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
            catch(Exception ignored){}
        }


}
