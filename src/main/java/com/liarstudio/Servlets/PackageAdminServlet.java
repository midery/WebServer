package com.liarstudio.Servlets;

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
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.liarstudio.PackageServletUtils.*;

@WebServlet(name = "PackageAdminServlet", urlPatterns = {"/package/admin"})
public class PackageAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id;
        if ((id = getInt(req.getParameter("pack_id"))) != null) {
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


        List<Package> packages = packageDao.queryBuilder().where()
                .eq("status", 0).or()
                .eq("status", 3).or()
                .eq("status", 4).query();


        packages = fillPackages(packageDao, personDao, packages);

        cs.close();
        return packages;

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