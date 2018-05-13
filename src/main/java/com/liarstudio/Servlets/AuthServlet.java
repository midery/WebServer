package com.liarstudio.Servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.liarstudio.BaseClasses.User;
import com.liarstudio.PackageServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.*;
import java.util.stream.Collectors;

@WebServlet(name = "AuthServlet", urlPatterns = {"/auth"})
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = null;

        try {
            user = createUserDao(userFromRequestBody(request));
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find PostgreSQL driver!");
        } catch (SQLException e) {
            System.out.println("Can't estabilish SQL connection!");
        }

        PackageServletUtils.handleResponse(user, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = null;

        try {
            user  = checkCredentials(request.getParameter("email"), request.getParameter("password"));
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find PostgreSQL driver!");
        } catch (SQLException e) {
            System.out.println("Can't estabilish SQL connection!");
        }

        PackageServletUtils.handleResponse(user, response);
    }

    private User checkCredentials(String email, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);


        Dao<User, String> userDao = DaoManager.createDao(cs, User.class);

        User user;
        if ((user = userDao.queryBuilder().where().eq("email", email).queryForFirst()) != null) {
            if (user.getPassword().compareTo(password) == 0) {
                cs.close();
                return user;
            }
        }
        cs.close();
        return null;
    }


    private User createUserDao(User user) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);
        Dao<User, String> userDao = DaoManager.createDao(cs, User.class);

        if (userDao.queryBuilder().where().eq("email", user.getEmail()).queryForFirst() == null) {
            userDao.create(user);
            User newUser = userDao.queryBuilder().where().eq("email", user.getEmail()).queryForFirst();

            cs.close();
            return newUser;
        }
        cs.close();
        return null;
    }
    private User userFromRequestBody(HttpServletRequest request) throws IOException {
        String userJson = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(userJson, User.class);
    }

}
