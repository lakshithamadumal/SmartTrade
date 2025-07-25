package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Payhere;

@WebServlet(name = "VerifyPayment", urlPatterns = {"/VerifyPayment"})
public class VerifyPayment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String merchant_id = request.getParameter("merchant_id");
        String order_id = request.getParameter("order_id");
        String payhere_amount = request.getParameter("payhere_amount");
        String payhere_currency = request.getParameter("payhere_currency");
        String status_code = request.getParameter("status_code");
        String md5sig = request.getParameter("md5sig");

        String merchant_secret = "NjY0NzI4Njg1BD45RTkwMDg2MjEyODM2MTAxMTQyNTk0Nzk5NjI3";
        String merchant_secret_md5hash = Payhere.generateMD5(merchant_secret);

        String generated_md5hash = Payhere.generateMD5(
                merchant_id
                + order_id
                + payhere_amount
                + payhere_currency
                + status_code
                + merchant_secret_md5hash
        );

        if (generated_md5hash.equals(md5sig) && status_code.equals("2")) {
            System.out.println("payment completed of " + order_id);
            //update your order status paid
        }

    }

}
