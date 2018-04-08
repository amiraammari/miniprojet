package com.example.amira.rechercheresterant;

import android.app.Application;

/**
 * Created by Amira on 05/08/2018.
 */

public class Global extends Application {
    private String name;
    private int id;

    public Global(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Global(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
