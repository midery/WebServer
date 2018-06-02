package com.liarstudio.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.liarstudio.utils.DateFormatter;
import com.liarstudio.utils.TableNames;

import java.util.Date;


@DatabaseTable(tableName = TableNames.PACKAGES)
public class Package {

    @DatabaseField
    private String commentary;

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate() {
        this.stringDate = DateFormatter.toString(date);
    }

    /*@SerializedName("date")
        private Calendar dateCalendar;*/
    @SerializedName("date")
    private String stringDate;

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

    @DatabaseField(canBeNull = false, foreign = true)
    @SerializedName("dimensions")
    private Dimensions dimensions;

    @DatabaseField(canBeNull = false)
    private Integer status;

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(foreign = true)
    @SerializedName("coordinates")
    private Coordinates coordinates;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dimensions getDimensions() { return dimensions; }

    public Package setDimensions() { //TODO XYZ WEIGHT
        this.dimensions = new Dimensions();
        return this;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
        }


    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }



}
