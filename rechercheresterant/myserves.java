package com.example.amira.rechercheresterant;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Amira on 02/09/2018.
 */

public class myserves extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static boolean ServiceIsRun=false;
    public static int CommnetID=0;
    public myserves() {
        super("MyWebRequestService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent wintent) {
        while ( ServiceIsRun) {
            String myurl = "http://amiraammeri1.000webhostapp.com/restau/notification.php?id="+CommnetID;
            try
            {
                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                NewsData=Stream2String(in);
                in.close();
                String msg="";
                // read jsn data
                JSONArray json = new JSONArray(NewsData);
                for (int i = 0; i < json.length(); i++) {
                    JSONObject newDataItem = json.getJSONObject(i);
                    msg+= newDataItem.getInt("etat")+ "!!\n";
                    CommnetID=  newDataItem.getInt("id");
                }

                // creat new intent
                Intent intent = new Intent();
                //set the action that will receive our broadcast
                intent.setAction("com.example.Broadcast");
                // add data to the bundle
                intent.putExtra("msg", msg);
                // send the data to broadcast
                sendBroadcast(intent);
                //delay for 50000ms

            } catch (Exception e) {
                Log.e("log_tag", "Error json "+e.toString());
                e.getMessage();
                Toast.makeText(this,"error url",Toast.LENGTH_SHORT).show();
            }
            try{
                Thread.sleep(20000);
            }catch (Exception ex){
                ex.getMessage();
                Toast.makeText(this,"error sleep",Toast.LENGTH_SHORT).show();
            }
        }
    }

    String NewsData="";

    public String Stream2String(InputStream inputStream) {
        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String Text="";
        try{
            while((line=bureader.readLine())!=null) {
                Text+=line;
            }
            inputStream.close();
        }catch (Exception ex){
            ex.getMessage();
            Toast.makeText(this,"error stream",Toast.LENGTH_SHORT).show();
        }
        return Text;
    }
}
