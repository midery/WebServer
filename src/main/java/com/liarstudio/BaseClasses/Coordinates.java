package com.liarstudio.BaseClasses;

import com.google.gson.annotations.SerializedName;

public class Coordinates {
    @SerializedName("x")
    double x;

    @SerializedName("y")
    double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

}
