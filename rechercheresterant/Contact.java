package com.example.amira.rechercheresterant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Contact extends AppCompatActivity {

    TextView tel,email,web,adress;
    ImageButton ibtel,ibemail,ibweb,ibadr;
    String lat;
    String lag;
    SharedPreferences loginId ;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("contact");
        loginId= getSharedPreferences("gerentResto", Context.MODE_PRIVATE);
        tel=(TextView)findViewById(R.id.tel);
        email=(TextView)findViewById(R.id.email);
        web=(TextView)findViewById(R.id.web);
        adress=(TextView)findViewById(R.id.adress);
        ibtel=(ImageButton)findViewById(R.id.imagetel);
        ibemail=(ImageButton)findViewById(R.id.imageemail);
        ibweb=(ImageButton)findViewById(R.id.imageweb);
        ibadr=(ImageButton)findViewById(R.id.imageadr);
        tel.setText(loginId.getString("tel",""));
        email.setText(loginId.getString("email",""));
        web.setText(loginId.getString("web",""));
        adress.setText(loginId.getString("adresse",""));
        lag=loginId.getString("lag","");
        lat=loginId.getString("lat","");
        ibtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teli = new Intent(Intent.ACTION_DIAL);
                teli.setData(Uri.parse("tel:"+loginId.getString("tel","")));
                if (teli.resolveActivity(getPackageManager())!=null){
                    startActivity(teli);
                }
            }
        });
        ibadr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent geo = new Intent(Intent.ACTION_VIEW);
                //startActivity(geo);
                geo.setData(Uri.parse("geo:"+lat+","+lag+"?z=18"));
                if (geo.resolveActivity(getPackageManager())!=null){
                    startActivity(geo);
                }
            }
        });
        ibweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent web = new Intent(Intent.ACTION_VIEW);
                web.setData(Uri.parse(loginId.getString("web","")));
                if (web.resolveActivity(getPackageManager())!=null){
                    startActivity(web);
                }
            }
        });
        ibemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{Intent email = new Intent(Intent.ACTION_SEND);
                email.setData(Uri.parse("mailto:"));
                email.setType("message/rfc822");
                email.putExtra(Intent.EXTRA_EMAIL,loginId.getString("email",""));
                startActivity(email);}catch (Exception e){
                    Toast.makeText(Contact.this,"No application email", Toast.LENGTH_SHORT).show();}
            }
        });

    }
}
