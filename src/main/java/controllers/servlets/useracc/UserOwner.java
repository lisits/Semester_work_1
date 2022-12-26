package controllers.servlets.useracc;

import models.entities.Cat;
import models.entities.Order;
import models.entities.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.order.OrderService;
import models.service.user.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/user")
public class UserOwner extends HttpServlet {
    UserService userService;
    OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        orderService = (OrderService) config.getServletContext().getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("CurrentUser");
        req.setAttribute("AvailableCats", userService.getAvailableCats(user.getId()));
        List<Order> allOrdersOfThisUser = orderService.getEntitiesOrdersOfThisUser(user.getId());
        Map<Order, List<Cat>> purchasedCats = new HashMap<>();
        for (Order order : allOrdersOfThisUser) {
            purchasedCats.put(order, orderService.getEntitiesCatsOfCertainOrder(order));
        }
        System.out.println(purchasedCats);
        req.setAttribute("history", purchasedCats);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/user.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("CurrentUser");
        resp.sendRedirect("/userLogIn");
    }
}