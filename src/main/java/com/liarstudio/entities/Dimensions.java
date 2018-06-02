package com.liarstudio.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.liarstudio.utils.TableNames;


@DatabaseTable(tableName = TableNames.DIMENSIONS)
public class Dimensions {

    @DatabaseField(generatedId = true, canBeNull = false)
    Long id;

    @DatabaseField(columnName = "x")
    @SerializedName("x")
    Double x;

    @DatabaseField(columnName = "y")
    @SerializedName("y")
    Double y;

    @DatabaseField(columnName = "z")
    @SerializedName("z")
    Double z;

    @DatabaseField(columnName = "weight")
    @SerializedName("weight")
    Double weight;

    public Dimensions() { }

    public Dimensions(Long id, Double x, Double y, Double z, Double weight) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
