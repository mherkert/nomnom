package com.mherkert.nomnom.domain;

import java.io.Serializable;

public class Meta implements Serializable{
    String color;

    public Meta(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
