package controllers.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.user.UserService;

import java.io.IOException;

@WebServlet("/userLogIn")
public class UserLogIn extends HttpServlet {
    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("CurrentUser") != null) {
            resp.sendRedirect("/user");
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/userLogIn.jsp");
            requestDispatcher.forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (userService.findUser(req.getParameter("LogInmail"), req.getParameter("LogInpassword")) != null) {
            req.getSession().setAttribute("CurrentUser", userService.findUser(req.getParameter("LogInmail"), req.getParameter("LogInpassword")));
            req.getSession().removeAttribute("NotFound");
        } else {
            req.getSession().setAttribute("NotFound", "Wrong mail or password");
        }
        resp.sendRedirect("/user");
    }
}