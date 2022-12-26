package controllers.servlets;

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

@WebServlet("")
public class MainPage extends HttpServlet {
    CatService catService;
    String columnCatForOrder;
    String descOrAsc;
    String columnCatForFilter;
    String filterArgument;

    @Override
    public void init(ServletConfig config) throws ServletException {
        catService = (CatService) config.getServletContext().getAttribute("catService");
        columnCatForOrder = "id";
        descOrAsc = "asc";
        columnCatForFilter = null;
        filterArgument = null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("CurrentUser") != null) {
            User user = (User) req.getSession().getAttribute("CurrentUser");
            List<Long> PurchasedCats = catService.purchasedCats(user.getId());
            if (!PurchasedCats.isEmpty()) {
                req.setAttribute("PurchasedCats", PurchasedCats);
            }
        }
        if (req.getParameter("Filter") != null) {
            columnCatForOrder = req.getParameter("columnCatForOrder");
            descOrAsc = req.getParameter("descOrAsc");
            columnCatForFilter = req.getParameter("columnCatForFilter");
            filterArgument = req.getParameter("filterArgument");
        }
        List<Cat> catList = catService.getListOfCats(columnCatForOrder, descOrAsc, columnCatForFilter, filterArgument);
        req.setAttribute("catList", catList);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/index.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("CurrentUser") == null) {
            resp.sendRedirect("userLogIn");
        } else {
            Long num = Long.parseLong(req.getParameter("SelectedCat"));
            req.getSession().setAttribute("SelectedCat", catService.findById(num));
            req.getSession().removeAttribute("SelectedCat");
            resp.sendRedirect("/");
        }
    }
}
