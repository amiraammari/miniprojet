package com.example.amira.rechercheresterant;

/**
 * Created by Amira on 11/08/2018.
 */

public class List_Item {

    public String name;
    public String img;

    public List_Item(String name, String img){
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
