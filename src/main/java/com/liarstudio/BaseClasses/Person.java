package com.liarstudio.BaseClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by M1DERY on 20.07.2017.
 */

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}