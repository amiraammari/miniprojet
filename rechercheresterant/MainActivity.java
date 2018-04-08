package com.example.amira.rechercheresterant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!myserves.ServiceIsRun) {
            myserves.ServiceIsRun  = true;
            //register the services to run in background
            Intent intent = new Intent(this, myserves.class);
            // start the services
            startService(intent);
        }

        final Button list = (Button) findViewById(R.id.liste);
        list.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListeRes.class);
                startActivity(intent);
            }
        });
    }





    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if(id==R.id.menu_home){
            Toast.makeText(this,"home",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.menu_hom){
            Toast.makeText(this,"hom",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }*/
}
