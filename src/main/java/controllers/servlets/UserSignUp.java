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

@WebServlet("/userSignIn")
public class UserSignUp extends HttpServlet {
    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/userSignUp.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = userService.signUp(req.getParameter("nick"), req.getParameter("first_name"), req.getParameter("last_name"), req.getParameter("mail"), req.getParameter("password"), req.getParameter("img"));

        if (!result.equals("All clear!")) {
            req.getSession().setAttribute("Warning", result);
            resp.sendRedirect("/userSignIn");
            return;
        }

        req.getSession().setAttribute("CurrentUser", userService.findUser(req.getParameter("mail"), req.getParameter("password")));
        req.getSession().removeAttribute("Warning");
        resp.sendRedirect("/user");
    }
}