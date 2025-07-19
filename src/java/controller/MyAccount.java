package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Address;
import hibernate.City;
import hibernate.HibernateUtil;
import hibernate.User;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PUT;
import static jdk.internal.org.jline.utils.Colors.s;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Laky
 */
@WebServlet(name = "MyAccount", urlPatterns = {"/MyAccount"})
public class MyAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession ses = request.getSession(false);
        if (ses != null && ses.getAttribute("user") != null) {
            User user = (User) ses.getAttribute("user");
            JsonObject responseObject = new JsonObject();

            responseObject.addProperty("firstName", user.getFirst_name());
            responseObject.addProperty("lastName", user.getLast_name());
            responseObject.addProperty("password", user.getPassword());

            String since = new SimpleDateFormat("MMM yyyy").format(user.getCreated_at());
            responseObject.addProperty("since", since);

            Gson gson = new Gson();
            String toJson = gson.toJson(responseObject);
            response.setContentType("application/json");
            response.getWriter().write(toJson);

        }
    }

    @Override
protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    Gson gson = new Gson();
    JsonObject userData = gson.fromJson(request.getReader(), JsonObject.class);

    String firstName = userData.get("firstName").getAsString();
    String lastName = userData.get("lastName").getAsString();
    String lineOne = userData.get("lineOne").getAsString();
    String lineTwo = userData.get("lineTwo").getAsString();
    String postalCode = userData.get("postalCode").getAsString();
    int cityId = userData.get("cityId").getAsInt();
    String currentPassword = userData.get("currentPassword").getAsString();
    String newPassword = userData.get("newPassword").getAsString();
    String confirmPassword = userData.get("confirmPassword").getAsString();

    JsonObject responseObject = new JsonObject();
    responseObject.addProperty("status", false);

    // Basic validations
    if (firstName.isEmpty()) {
        responseObject.addProperty("message", "First Name cannot be empty!");
    } else if (lastName.isEmpty()) {
        responseObject.addProperty("message", "Last Name cannot be empty!");
    } else if (lineOne.isEmpty()) {
        responseObject.addProperty("message", "Address Line One cannot be empty!");
    } else if (postalCode.isEmpty()) {
        responseObject.addProperty("message", "Postal Code cannot be empty!");
    } else if (cityId == 0) {
        responseObject.addProperty("message", "City must be selected!");
    } else if (currentPassword.isEmpty()) {
        responseObject.addProperty("message", "Current Password cannot be empty!");
    } else if (!newPassword.equals(confirmPassword)) {
        responseObject.addProperty("message", "New Password and Confirm Password do not match!");
    } else {
        HttpSession ses = request.getSession();
        User sessionUser = (User) ses.getAttribute("user");

        if (sessionUser != null) {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            Transaction tx = s.beginTransaction();

            try {
                // Fetch latest user from DB
                Criteria c = s.createCriteria(User.class);
                c.add(Restrictions.eq("email", sessionUser.getEmail()));
                User userFromDB = (User) c.uniqueResult();

                if (userFromDB != null) {
                    // Update fields
                    userFromDB.setFirst_name(firstName);
                    userFromDB.setLast_name(lastName);

                    if (!confirmPassword.isEmpty()) {
                        userFromDB.setPassword(confirmPassword);
                    }

                    // Load city by ID
                    City city = (City) s.get(City.class, cityId);

                    // Create and associate address
                    Address address = new Address();
                    address.setLine1(lineOne);
                    address.setLine2(lineTwo);
                    address.setPostal_code(postalCode);
                    address.setCity(city);
                    address.setUser(userFromDB);

                    // Save or update both
                    s.saveOrUpdate(userFromDB);
                    s.save(address);

                    tx.commit();
                    ses.setAttribute("user", userFromDB);

                    responseObject.addProperty("status", true);
                    responseObject.addProperty("message", "User Profile Updated!");
                } else {
                    responseObject.addProperty("message", "User not found in database!");
                }
            } catch (Exception e) {
                tx.rollback();
                responseObject.addProperty("message", "An error occurred: " + e.getMessage());
            } finally {
                s.close();
            }
        } else {
            responseObject.addProperty("message", "Session expired. Please login again.");
        }
    }

    // Send JSON response
    response.setContentType("application/json");
    response.getWriter().write(gson.toJson(responseObject));
}


}
