package com.liarstudio.Servlets;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.liarstudio.BaseClasses.Package;
import com.liarstudio.BaseClasses.Person;
import com.liarstudio.PackageServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.liarstudio.PackageServletUtils.*;

@WebServlet(name = "PackageCourierServlet", urlPatterns = { "/package/courier/*" })
public class PackageCourierServlet extends HttpServlet {

    private static String PATH_NEW = "new";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Package> packages = null;

        try {
            String pathInfo = request.getPathInfo();
            boolean isNew = pathInfo.contains(PATH_NEW);
            Integer id = getIdFrom(pathInfo);
            if (id != null)
                packages = loadPackages(id, isNew);
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find PostgreSQL driver!");
        } catch (SQLException e) {
            System.out.println("Can't estabilish SQL connection!");
        }
        handleResponse(packages, response);
    }

    private List<Package> loadPackages(int id, boolean isNew) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        String db_url = "jdbc:postgresql://localhost:5432/courierservice";
        String db_user = "postgres";
        String db_password = "postgres";

        ConnectionSource cs = new JdbcConnectionSource(db_url, db_user, db_password);

        Dao<Package, String> packageDao = DaoManager.createDao(cs, Package.class);
        Dao<Person, String> personDao = DaoManager.createDao(cs, Person.class);

        List<Package> packages = isNew ?
                packageDao.queryBuilder()
                        .where()
                        .eq("courier_id", id)
                        .and()
                        .eq("status", 0)
                        .query()
                :
                packageDao.queryBuilder()
                        .where()
                        .eq("courier_id", id)
                        .and().
                        not().eq("status", 0)
                        .and()
                        .not().eq("status", 3)
                        .query();

        packages = PackageServletUtils.fillPackages(packageDao, personDao, packages);
        cs.close();
        return packages;

    }



}
