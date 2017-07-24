package com.liarstudio.Servlets;

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
import java.sql.SQLException;
import java.util.List;

import static javax.swing.UIManager.getInt;

@WebServlet(name = "UserServlet", urlPatterns = {"/users", "/users/*"})
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        if (request.getPathInfo() != null) {
            String pathInfo = request.getPathInfo().replace("/", "");
            Integer id = PackageServletUtils.getInt(pathInfo);
            User user = null;

            try {
                user = loadUser(id.intValue());
            } catch (ClassNotFoundException e) {
                System.out.println("Can't find PostgreSQL driver!");
            } catch (SQLException e) {
                System.out.println("Can't estabilish SQL connection!");
            }
            PackageServletUtils.handleResponse(user, response);
        } else {
            List<User> users = null;
            Integer role = getInt(request.getParameter("role"));

            try {
                users = loadUsers(role.intValue());
            } catch (ClassNotFoundException e) {
                System.out.println("Can't find PostgreSQL driver!");
            } catch (SQLException e) {
                System.out.println("Can't estabilish SQL connection!");
            }

            PackageServletUtils.handleResponse(users, response);
        }
    }


    List<User> loadUsers(int role) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);

        Dao<User, String> userDao = DaoManager.createDao(cs, User.class);

        List<User> users = userDao.queryBuilder().where().eq("role", role).query();

        cs.close();
        return users;
    }

    User loadUser(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);

        Dao<User, String> userDao = DaoManager.createDao(cs, User.class);

        User user = userDao.queryBuilder().where().eq("id", id).queryForFirst();

        cs.close();
        return user;
    }

}
