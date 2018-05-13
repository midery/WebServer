package com.liarstudio.BaseClasses;

import com.google.gson.annotations.SerializedName;

public class Dimensions {
    @SerializedName("x")
    double x;

    @SerializedName("y")
    double y;

    @SerializedName("z")
    double z;

    @SerializedName("weight")
    double weight;

    public Dimensions(double x, double y, double z, double weight) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.weight = weight;
    }
}
