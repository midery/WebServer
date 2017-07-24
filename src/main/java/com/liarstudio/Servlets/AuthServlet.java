package com.liarstudio.Servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.liarstudio.BaseClasses.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "AuthServlet", urlPatterns = {"/auth"})
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = null;

        try {
            user = createUser(request.getParameter("email"), request.getParameter("name"), request.getParameter("password"));
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find PostgreSQL driver!");
        } catch (SQLException e) {
            System.out.println("Can't estabilish SQL connection!");
        }

        if (user != null) {
            Gson gson = new GsonBuilder().create();
            JSONObject json = new JSONObject(gson.toJson(user));
            json.write(response.getWriter());
            response.setStatus(HttpServletResponse.SC_OK);
        } else
            response.sendError(HttpServletResponse.SC_CONFLICT);

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

        if (user != null) {
            Gson gson = new GsonBuilder().create();
            JSONObject json = new JSONObject(gson.toJson(user));
            json.write(response.getWriter());
            response.setStatus(HttpServletResponse.SC_OK);
        } else
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

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


    private User createUser(String email, String name, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);
        Dao<User, String> userDao = DaoManager.createDao(cs, User.class);

        if (userDao.queryBuilder().where().eq("email", email).queryForFirst() == null) {
            userDao.create(new User(email, password, name, 0));

            User user = userDao.queryBuilder().where().eq("email", email).queryForFirst();

            cs.close();
            return user;
        }
        cs.close();
        return null;
    }

}
