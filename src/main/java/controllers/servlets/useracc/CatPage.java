package controllers.servlets.useracc;

import models.entities.Cat;
import models.entities.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.cat.CatService;

import java.io.IOException;
import java.util.List;

@WebServlet("/cat")
public class CatPage extends HttpServlet {
    CatService catService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        catService = (CatService) config.getServletContext().getAttribute("catService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cat cat = catService.findById(Long.parseLong(req.getParameter("ClickCat")));
        if (req.getSession().getAttribute("CurrentUser") != null) {
            User user = (User) req.getSession().getAttribute("CurrentUser");
            List<Long> PurchasedCats = catService.purchasedCats(user.getId());
            if (!PurchasedCats.isEmpty()) {
                req.setAttribute("PurchasedCats", PurchasedCats);
            }
        }
        req.setAttribute("Cat", cat);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/cat.jsp");
        requestDispatcher.forward(req, resp);
    }

}
