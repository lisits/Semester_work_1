package controllers.servlets.adminacc;

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

@WebServlet("/catAdmin")
public class CatPage extends HttpServlet {
    CatService catService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        catService = (CatService) config.getServletContext().getAttribute("catService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cat cat = catService.findById((Long) request.getSession().getAttribute("ClickCat"));
        if (request.getSession().getAttribute("CurrentUser") != null) {
            User user = (User) request.getSession().getAttribute("CurrentUser");
            List<Long> PurchasedCats = catService.purchasedCats(user.getId());
            if (!PurchasedCats.isEmpty()) {
                request.setAttribute("PurchasedCats", PurchasedCats);
            }
        }
        request.setAttribute("Cat", cat);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/views/catAdmin.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/catAdmin");
    }
}
