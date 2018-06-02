package com.liarstudio;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.liarstudio.entities.*;
import com.liarstudio.entities.Package;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        String db_url = "jdbc:postgresql://localhost:5433/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        try {
            Class.forName("org.postgresql.Driver");
            ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);

            TableUtils.createTable(cs, Package.class);
            TableUtils.createTable(cs, Person.class);
            TableUtils.createTable(cs, User.class);
            TableUtils.createTable(cs, Dimensions.class);
            TableUtils.createTable(cs, Coordinates.class);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't find PostgreSQL driver!");
        } catch (SQLException sqle) {
            System.out.println("Can't estabilish SQL connection!");
        }
    }
}
