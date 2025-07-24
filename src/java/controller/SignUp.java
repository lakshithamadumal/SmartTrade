package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Response_DTO;
import dto.User_DTO;
import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Mail;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {

        Response_DTO response_DTO = new Response_DTO();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        final User_DTO user_DTO = gson.fromJson(requset.getReader(), User_DTO.class);

        if (user_DTO.getFirst_name().isEmpty()) {
            response_DTO.setContent("Please enter your First Name");

        } else if (user_DTO.getLast_name().isEmpty()) {
            response_DTO.setContent("Please enter your Last Name");

        } else if (user_DTO.getEmail().isEmpty()) {
            response_DTO.setContent("Please enter your Email");

        } else if (!Validations.isEmailValid(user_DTO.getEmail())) {
            response_DTO.setContent("Please enter a valid Email");

        } else if (user_DTO.getPassword().isEmpty()) {
            response_DTO.setContent("Please create a Password");

        } else if (!Validations.isPasswordValid(user_DTO.getPassword())) {
            response_DTO.setContent("Password must include at least one uppercase letter, "
                    + "number, special character, and not less than 8 characters");

        } else {

            Session session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("email", user_DTO.getEmail()));

            if (!criteria.list().isEmpty()) {
                response_DTO.setContent("Email already exists");
            } else {

                int code = (int) (Math.random() * 100000);

                final User user = new User();
                user.setEmail(user_DTO.getEmail());
                user.setFirst_name(user_DTO.getFirst_name());
                user.setLast_name(user_DTO.getLast_name());
                user.setPassword(user_DTO.getPassword());
                user.setVerification(String.valueOf(code));

                Thread sendMailThread = new Thread() {
                    @Override
                    public void run() {
                        Mail.sendMail(
                                user_DTO.getEmail(),
                                "Smart Trade Email Verification",
                                "<h1>Verification Code : " + user.getVerification() + "</h1>"
                        );
                    }

                };
                sendMailThread.start();
                session.save(user);
                session.beginTransaction().commit();

                requset.getSession().setAttribute("email", user_DTO.getEmail());
                response_DTO.setSuccess(true);
                response_DTO.setContent("Registration Complete. Please check your inbox for Verification Code!");

            }
            session.close();
        }
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(response_DTO));
    }

}
