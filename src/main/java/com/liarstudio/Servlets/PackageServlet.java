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


@WebServlet(name = "PackageServlet", urlPatterns = {"/package", "/package/delete/*"})
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getIdFrom(req.getPathInfo());
        if (id != null) {
            boolean isDeleted = false;
            try {
                isDeleted = deletePackage(id);
            } catch (ClassNotFoundException e) {
                System.out.println("Can't find PostgreSQL driver!");
            } catch (SQLException e) {
                System.out.println("Can't estabilish SQL connection!");
            }
            if (isDeleted)
                resp.setStatus(HttpServletResponse.SC_OK);
            else
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);

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

        Package pkg;
        if ((pkg = packageDao.queryBuilder().where().eq("id", id).queryForFirst())!=null) {
            pkg.setStringDate();
            pkg.setDimensions();
            pkg.setSender(personDao.queryBuilder().where().eq("id", pkg.getSender().getId()).queryForFirst().setCoordinates());
            pkg.setRecipient(personDao.queryBuilder().where().eq("id", pkg.getRecipient().getId()).queryForFirst().setCoordinates());
        }

        cs.close();
        return pkg;
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
        //pkg.setDate(pkg.getCalendar().getTime());


        return pkg;
    }

    boolean deletePackage(Integer id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);

        Dao<Package, String> packageDao = DaoManager.createDao(cs, Package.class);


        packageDao.deleteById(id.toString());

        cs.close();
        return true;
    }
}
