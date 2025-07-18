package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Laky
 */
@MultipartConfig
@WebServlet(name = "SaveProduct", urlPatterns = {"/SaveProduct"})
public class SaveProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String brandId = request.getParameter("brandId");
        String modelId = request.getParameter("modelId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String storageId = request.getParameter("storageId");
        String colorId = request.getParameter("colorId");
        String conditionId = request.getParameter("conditionId");
        String price = request.getParameter("price");
        String qty = request.getParameter("qty");
        String image1 = request.getParameter("image1");
        String image2 = request.getParameter("image2");
        String image3 = request.getParameter("image3");

        System.out.println("Brand ID: " + brandId);
        System.out.println("Model ID: " + modelId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Storage ID: " + storageId);
        System.out.println("Color ID: " + colorId);
        System.out.println("Condition ID: " + conditionId);
        System.out.println("Price: " + price);
        System.out.println("Quantity: " + qty);
        System.out.println("Image 1: " + image1);
        System.out.println("Image 2: " + image2);
        System.out.println("Image 3: " + image3);
    }

}
