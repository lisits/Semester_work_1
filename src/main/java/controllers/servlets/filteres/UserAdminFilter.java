package controllers.servlets.filteres;

import models.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/userAdmin")
public class UserAdminFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getSession().getAttribute("CurrentUser") == null) {
            res.sendRedirect("/userLogIn");
            return;
        } else if (req.getSession().getAttribute("CurrentUser") != null) {
            User user = (User) req.getSession().getAttribute("CurrentUser");
            if (!user.isAdmin()) {
                res.sendRedirect("/user");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
