/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Utilities;
import model.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Laky
 */
@WebServlet(name = "LoadSingleProduct", urlPatterns = {"/LoadSingleProduct"})
public class LoadSingleProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", Boolean.FALSE);

        String productId = request.getParameter("id");

        System.out.println(productId);

        if (Util.isInteger(productId)) {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();

            try {
                Product product = (Product) s.get(Product.class, Integer.valueOf(productId));
                if (product.getStatus().getName().equals("Active")) {
                    product.getUser().setEmail(null);
                    product.getUser().setPassword(null);
                    product.getUser().setVerification(null);
                    product.getUser().setId(-1);
                    product.getUser().setCreated_at(null);

                    Criteria c1 = s.createCriteria(Model.class);
                    c1.add(Restrictions.eq("brand", product.getModel().getBrand()));
                    List<Model> modelList = c1.list();

                    Criteria c2 = s.createCriteria(Product.class);
                    c2.add(Restrictions.in("model", modelList));
                    c2.add(Restrictions.ne("id", product.getId()));
                    c2.setMaxResults(6);
                    List<Product> ProductList = c2.list();

                    for (Product pr : ProductList) {
                        pr.getUser().setEmail(null);
                        pr.getUser().setPassword(null);
                        pr.getUser().setVerification(null);
                        pr.getUser().setId(-1);
                        pr.getUser().setCreated_at(null);
                    }

                    responseObject.add("product", gson.toJsonTree(product));
                    responseObject.addProperty("status", true);
                } else {
                    responseObject.addProperty("message", "Product Not Found!");
                }
            } catch (Exception e) {
                responseObject.addProperty("message", "Product Not Found!");
            }

        }
        String responseText = gson.toJson(responseObject);
        response.setContentType("application/json");
        response.getWriter().write(responseText);
    }
}
