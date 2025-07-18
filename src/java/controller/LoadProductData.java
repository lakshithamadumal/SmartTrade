package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Brand;
import hibernate.Color;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Quality;
import hibernate.Status;
import hibernate.Storage;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Laky
 */
@WebServlet(name = "LoadProductData", urlPatterns = {"/LoadProductData"})
public class LoadProductData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();

        //Search Brand
        Criteria c1 = s.createCriteria(Brand.class);
        List<Brand> brandList = c1.list();

        //Search Model
        Criteria c2 = s.createCriteria(Model.class);
        List<Model> modelList = c2.list();

        //Search Color
        Criteria c3 = s.createCriteria(Color.class);
        List<Color> colorlList = c3.list();

        //Search Quality
        Criteria c4 = s.createCriteria(Quality.class);
        List<Quality> qualitylList = c4.list();

        //Search Storage
        Criteria c5 = s.createCriteria(Storage.class);
        List<Storage> storagelList = c5.list();

        s.close();

        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", Boolean.TRUE);

        responseObject.add("brandList", gson.toJsonTree(brandList));
        responseObject.add("modelList", gson.toJsonTree(modelList));
        responseObject.add("colorlList", gson.toJsonTree(colorlList));
        responseObject.add("qualitylList", gson.toJsonTree(qualitylList));
        responseObject.add("storagelList", gson.toJsonTree(storagelList));

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseObject));

    }

}
