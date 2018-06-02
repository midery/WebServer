package com.liarstudio.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.liarstudio.utils.TableNames;

@DatabaseTable(tableName = TableNames.PERSONS)
public class Person {

    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    @DatabaseField(canBeNull = false)
    private String address;
    @DatabaseField(canBeNull = false)
    private String companyName;
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