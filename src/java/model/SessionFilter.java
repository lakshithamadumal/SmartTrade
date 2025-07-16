package model;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Laky
 */
@WebFilter(filterName = "SessionFilter", urlPatterns = {"/sign-in.html", "/sign-up.html", "/verify-account.html"})
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
       
    }

}
