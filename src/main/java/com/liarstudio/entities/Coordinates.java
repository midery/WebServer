package com.liarstudio.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.liarstudio.utils.TableNames;

@DatabaseTable(tableName = TableNames.COORDINATES)
public class Coordinates {

    @DatabaseField(generatedId = true, canBeNull = false)
    private Long id;

    @DatabaseField(columnName = "latitude")
    @SerializedName("latitude")
    private Double latitude;

    @DatabaseField(columnName = "longitude")
    @SerializedName("longitude")
    private Double longitude;

    public Coordinates() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Coordinates(Long id, Double latitude, Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
