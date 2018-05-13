package com.liarstudio.BaseClasses;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "person")
public class Person {

    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    @DatabaseField(canBeNull = false)
    private String address;
    @DatabaseField(canBeNull = false)
    private String companyName;
    @DatabaseField(canBeNull = false)
    private double coordinatesX;
    @DatabaseField(canBeNull = false)
    private double coordinatesY;
    @DatabaseField
    private String email;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private String phone;
    @DatabaseField(canBeNull = false)
    private Integer type;

    @SerializedName("coordinates")
    private Coordinates coordinates;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(double coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public double getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(double coordinatesY) {
        this.coordinatesY = coordinatesY;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Person setCoordinates() {
        this.coordinates = new Coordinates(coordinatesX, coordinatesY);
        return this;
    }


    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        coordinatesX = coordinates.x;
        coordinatesY = coordinates.y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
