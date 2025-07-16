package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Laky
 */
@WebServlet(name = "SignIp", urlPatterns = {"/SignIp"})
public class SignIp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject SignIn = gson.fromJson(request.getReader(), JsonObject.class);
        
        String email = SignIn.get("email").getAsString();
        String password = SignIn.get("password").getAsString();
        
        System.out.println(email);
        System.out.println(password);

    }

}
