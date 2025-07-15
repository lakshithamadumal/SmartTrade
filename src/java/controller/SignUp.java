package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.User;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.javadoc.internal.tool.Main;
import model.Mail;
import model.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Laky
 */
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject user = gson.fromJson(request.getReader(), JsonObject.class);

        String firstName = user.get("firstName").getAsString();
        String lastName = user.get("lastName").getAsString();
        String email = user.get("email").getAsString();
        String password = user.get("password").getAsString();

        //Data Save
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();

        User u = new User();
        u.setFirst_name(firstName);
        u.setLast_name(lastName);
        u.setEmail(email);
        u.setPassword(password);

        u.setCreated_at(new Date());

        //Verification
        String verificationCode = Util.generateCode();
        u.setVerification(verificationCode);

        s.save(u);
        s.beginTransaction().commit();

        //send email
        new Thread(new Runnable() {
            @Override
            public void run() {
                String emailBody = "<div style='"
                        + "font-family: Arial, sans-serif; "
                        + "background-color: #f4f4f4; "
                        + "padding: 30px; "
                        + "text-align: center;'>"
                        + "<div style='"
                        + "background-color: #ffffff; "
                        + "max-width: 600px; "
                        + "margin: auto; "
                        + "padding: 40px; "
                        + "border-radius: 8px; "
                        + "box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>"
                        + "<h2 style='color: #333;'>SmartTrade Verification</h2>"
                        + "<p style='font-size: 16px; color: #555;'>Use the code below to verify your email address:</p>"
                        + "<div style='"
                        + "font-size: 28px; "
                        + "font-weight: bold; "
                        + "color: #007bff; "
                        + "margin-top: 20px;'>"
                        + verificationCode + "</div>"
                        + "<p style='margin-top: 30px; font-size: 14px; color: #888;'>"
                        + "If you didn’t request this, please ignore this email."
                        + "</p>"
                        + "</div>"
                        + "</div>";

                Mail.sendMail(email, "SmartTrade Verification Code", emailBody);
            }
        }).start();

    }
}
