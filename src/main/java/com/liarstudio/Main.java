package com.liarstudio;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.liarstudio.BaseClasses.Package;
import com.liarstudio.BaseClasses.Person;
import com.liarstudio.BaseClasses.User;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.sql.SQLException;

/**
 * Created by M1DERY on 20.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        try {
            Class.forName("org.postgresql.Driver");
            ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);


            TableUtils.createTable(cs, Package.class);
            TableUtils.createTable(cs, Person.class);
            TableUtils.createTable(cs, User.class);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't find PostgreSQL driver!");
        } catch (SQLException sqle) {
            System.out.println("Can't estabilish SQL connection!");
        }
    }
}
