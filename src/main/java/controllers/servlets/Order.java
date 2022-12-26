package controllers.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.entities.Cat;
import models.entities.User;
import models.service.order.OrderService;
import models.service.order.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.*;

@WebServlet("/order")
public class Order extends HttpServlet {
    OrderService orderService = new OrderServiceImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        orderService = (OrderService) config.getServletContext().getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Long, Set<Cat>> SelectedCatsOfAllUsers = Listener.getSetOfSelectedCats();
        User user = (User) req.getSession().getAttribute("CurrentUser");
        req.setAttribute("ListOfSelectedCats", SelectedCatsOfAllUsers.get(user.getId()));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/order.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("CurrentUser");
        Map<Long, Set<Cat>> SelectedCatsOfAllUsers = Listener.getSetOfSelectedCats();
        if (req.getParameter("Delete") != null && req.getParameter("catsToDelete") != null) {
            String[] selectedCats = req.getParameterValues("catsToDelete");
            orderService.deleteCatsFromBasket(user.getId(), selectedCats);
        }
        if (req.getParameter("buy") != null) {
            Long idOfOrder = orderService.signUp(user.getId());
            Collection<Cat> cats = SelectedCatsOfAllUsers.get(user.getId());
            for (Cat cat : cats) {
                orderService.SignUpCatsFromBasket(idOfOrder, cat.getId());
                orderService.SignUpCatToLibrary(user.getId(), cat.getId());
            }
            Listener.getSetOfSelectedCats().put(user.getId(), new HashSet<>());
        }
        resp.sendRedirect("/order");
    }
}