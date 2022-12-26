package controllers.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.entities.Cat;
import models.service.cat.CatService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@WebServlet("/catChange")
public class ChangeCat extends HttpServlet {
    CatService catService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        catService = (CatService) config.getServletContext().getAttribute("catService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("newMainImage") != null && req.getParameter("newMainImage") != "") {
            catService.saveMainImg(req.getParameter("newMainImage"), Long.parseLong(req.getParameter("nameOfCat")));
            resp.sendRedirect("/");
            return;
        }
        Cat cat = null;
        cat = models.entities.Cat.builder()
                .id(Long.parseLong(req.getParameter("id")))
                .age(Integer.parseInt(req.getParameter("age")))
                .owner(req.getParameter("owner"))
                .color(req.getParameter("color"))
                .cost(Long.valueOf(req.getParameter("cost")))
                .name(req.getParameter("name"))
                .build();
        catService.update(cat);
        resp.sendRedirect("/");
    }
}
