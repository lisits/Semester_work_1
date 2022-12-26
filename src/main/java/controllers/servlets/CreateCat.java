package controllers.servlets;

import models.entities.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.cat.CatService;
import models.service.user.UserService;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/createCat")
public class CreateCat extends HttpServlet {
    CatService catService;
    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        catService = (CatService) config.getServletContext().getAttribute("catService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        catService.signUp(req.getParameter("name"), req.getParameter("owner"), req.getParameter("color"), Integer.parseInt(req.getParameter("age")), Long.parseLong(req.getParameter("cost")));
        resp.sendRedirect("/user");
    }
}