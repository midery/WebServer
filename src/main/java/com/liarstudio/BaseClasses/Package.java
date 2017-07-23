package com.liarstudio.BaseClasses;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.Date;


@DatabaseTable(tableName = "package")
public class Package {

    @DatabaseField
    private String commentary;
    @SerializedName("date")
    private Calendar dateCalendar;

    @DatabaseField(canBeNull = false)
    private transient Date date;
    @DatabaseField(columnName =  "courier_id", canBeNull = false)
    private int courierId;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private double price;
    @DatabaseField(canBeNull = false, foreign = true)
    private Person recipient;
    @DatabaseField(canBeNull = false, foreign = true)
    private Person sender;

    @DatabaseField(columnName = "size_x", canBeNull = false)
    private double sizeX;

    @DatabaseField(columnName = "size_y", canBeNull = false)
    private double sizeY;

    @DatabaseField(columnName = "size_z", canBeNull = false)
    private double sizeZ;

    @DatabaseField(canBeNull = false)
    private Integer status;
    @DatabaseField(canBeNull = false)
    private double weight;

    @DatabaseField(generatedId = true)
    private Long id;


    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Calendar getCalendar() {
        return dateCalendar;
    }

    public void setCalendar (Calendar date) {

        this.dateCalendar = date;
    }
    public void setCalendar (Date date) {

        this.dateCalendar = Calendar.getInstance();
        this.dateCalendar.setTime(date);
    }

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public double getSizeX() {
        return sizeX;
    }

    public void setSizeX(double sizeX) {
        this.sizeX = sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    public void setSizeY(double sizeY) {
        this.sizeY = sizeY;
    }

    public double getSizeZ() {
        return sizeZ;
    }

    public void setSizeZ(double sizeZ) {
        this.sizeZ = sizeZ;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }



}
