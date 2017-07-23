package com.liarstudio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;
import com.liarstudio.BaseClasses.Package;
import com.liarstudio.BaseClasses.Person;
import com.liarstudio.BaseClasses.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by M1DERY on 22.07.2017.
 */
public class PackageServletUtils {
    public static Integer getInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
    public static List<Package> fillPackages(Dao<Package, String> packageDao, Dao<Person, String> personDao, List<Package> packages)
            throws SQLException, ClassNotFoundException {
        if (packages!=null)
            for (Package pkg: packages) {
                pkg.setCalendar(pkg.getDate());
                pkg.setSender(personDao.queryBuilder().where().eq("id", pkg.getSender().getId()).queryForFirst());
                pkg.setRecipient(personDao.queryBuilder().where().eq("id", pkg.getRecipient().getId()).queryForFirst());
            }
        return packages;
    }
    public static void handleResponse(List<? extends Object> objects, HttpServletResponse response) throws ServletException, IOException {
        if (objects != null) {
            Gson gson = new GsonBuilder().create();
            JSONArray json = new JSONArray(gson.toJson(objects));
            json.write(response.getWriter());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static void handleResponse(Object object, HttpServletResponse response) throws ServletException, IOException{
        if (object != null) {
            Gson gson = new GsonBuilder().create();
            JSONObject json = new JSONObject(gson.toJson(object));
            json.write(response.getWriter());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}
