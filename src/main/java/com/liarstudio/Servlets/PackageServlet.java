package com.liarstudio.Servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.liarstudio.BaseClasses.Package;
import com.liarstudio.BaseClasses.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

import static com.liarstudio.PackageServletUtils.*;


@WebServlet(name = "PackageServlet", urlPatterns = {"/package"})
public class PackageServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Package pack = packageFromRequestBody(request);

        boolean isAdded = false;
        try {
            isAdded = updatePackage(pack);
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find PostgreSQL driver!");
        } catch (SQLException e) {
            System.out.println("Can't estabilish SQL connection!");
        }
        if (isAdded)
            response.setStatus(HttpServletResponse.SC_OK);
        else
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer id;
        if ((id = getInt(request.getParameter("pack_id"))) == null) {
            List<Package> packages = null;
            try {
                packages = loadPackages();
            } catch (ClassNotFoundException e) {
                System.out.println("Can't find PostgreSQL driver!");
            } catch (SQLException e) {
                System.out.println("Can't estabilish SQL connection!");
            }
            handleResponse(packages, response);
        }
        else {

            Package pack = null;
            try {
                pack = loadPackageByID(id.intValue());
            } catch (ClassNotFoundException e) {
                System.out.println("Can't find PostgreSQL driver!");
            } catch (SQLException e) {
                System.out.println("Can't estabilish SQL connection!");
            }
            handleResponse(pack, response);
        }
    }




    List<Package> loadPackages() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);

        Dao<Package, String> packageDao = DaoManager.createDao(cs, Package.class);
        Dao<Person, String> personDao = DaoManager.createDao(cs, Person.class);


        List<Package> packages = fillPackages(packageDao, personDao, packageDao.queryBuilder().query());

        cs.close();
        return packages;

    }




    Package loadPackageByID(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);

        Dao<Package, String> packageDao = DaoManager.createDao(cs, Package.class);
        Dao<Person, String> personDao = DaoManager.createDao(cs, Person.class);

        Package pack;
        if ((pack = packageDao.queryBuilder().where().eq("id", id).queryForFirst())!=null) {
            pack.setCalendar(pack.getDate());
            pack.setSender(personDao.queryBuilder().where().eq("id", pack.getSender().getId()).queryForFirst());
            pack.setRecipient(personDao.queryBuilder().where().eq("id", pack.getRecipient().getId()).queryForFirst());
        }

        cs.close();
        return pack;
    }

    boolean updatePackage(Package pack) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);

        Dao<Package, String> packageDao = DaoManager.createDao(cs, Package.class);
        Dao<Person, String> personDao = DaoManager.createDao(cs, Person.class);

        if (pack.getId() == null || packageDao.queryBuilder().where().eq("id", pack.getId()).queryForFirst() == null) {
            personDao.create(pack.getSender());
            personDao.create(pack.getRecipient());

            pack.setId((long) 0);
            packageDao.create(pack);
        }
        else
        {
            personDao.update(pack.getSender());
            personDao.update(pack.getRecipient());

            packageDao.update(pack);
        }
        cs.close();
        return true;
    }

    private Package packageFromRequestBody(HttpServletRequest request) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = request.getReader().readLine()) != null)
            sb.append(line);

        Gson gson = new GsonBuilder().create();
        Package pkg = gson.fromJson(sb.toString(), Package.class);
        pkg.setDate(pkg.getCalendar().getTime());


        return pkg;
    }


}
