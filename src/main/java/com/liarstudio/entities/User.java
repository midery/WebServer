package com.liarstudio.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.liarstudio.utils.TableNames;


@DatabaseTable(tableName = TableNames.USERS)
public class User {
    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(unique = true, canBeNull = false)
    private String email;

    @DatabaseField(canBeNull = false)
    private String password;

    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private int role;

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public User(String email, String password, String name, int role) {
        this(email, password);
        this.name = name;
        this.role = role;
    }

    public User(int id, String email, String password, String name, int role) {
        this(email, password, name, role);
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


}
