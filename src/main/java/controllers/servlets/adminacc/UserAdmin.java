package controllers.servlets.adminacc;

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
import models.service.cat.CatService;
import models.service.order.OrderService;
import models.service.user.UserService;
import controllers.servlets.Listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/userAdmin")
public class UserAdmin extends HttpServlet {
    String columnUserForOrder;
    String userDescOrAsc;
    String columnUserForFilter;
    String userFilterArgument;
    String columnCatForOrder;
    String descOrAsc;
    String columnCatForFilter;
    String filterArgument;
    UserService userService;
    CatService catService;
    OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        columnUserForOrder = "id";
        userDescOrAsc = "asc";
        columnUserForFilter = null;
        userFilterArgument = null;
        columnCatForOrder = "id";
        descOrAsc = "asc";
        columnCatForFilter = null;
        filterArgument = null;
        userService = (UserService) config.getServletContext().getAttribute("userService");
        catService = (CatService) config.getServletContext().getAttribute("catService");
        orderService = (OrderService) config.getServletContext().getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("CurrentUser");
        request.setAttribute("AvailableCats", userService.getAvailableCats(user.getId()));
        if (request.getParameter("FilterUser") != null) {
            columnUserForOrder = request.getParameter("columnCatForOrder");
            userDescOrAsc = request.getParameter("descOrAsc");
            columnUserForFilter = request.getParameter("columnCatForFilter");
            userFilterArgument = request.getParameter("filterArgument");
        }
        List<Cat> catList = catService.getListOfCats(columnCatForOrder, descOrAsc, columnCatForFilter, filterArgument);
        request.setAttribute("CatList", catList);
        request.setAttribute("ListOfUsers", userService.findAllUsers(columnUserForOrder, userDescOrAsc, columnUserForFilter, userFilterArgument));


        List<Order> allOrdersOfThisUser = orderService.getEntitiesOrdersOfThisUser(user.getId());
        Map<Order, List<Cat>> purchasedCats = new HashMap<>();
        for (Order order : allOrdersOfThisUser) {
            purchasedCats.put(order, orderService.getEntitiesCatsOfCertainOrder(order));
        }
        request.setAttribute("history", purchasedCats);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/views/userAdmin.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("deleteCat") != null) {
            String[] selectedCats = request.getParameterValues("cats");
            if (selectedCats != null) {
                for (int i = 0; i < selectedCats.length; i++) {
                    catService.deleteById(Long.parseLong(selectedCats[i]));
                }
            }
        }

        if (request.getParameter("delete") != null || request.getParameter("signAsAdmin") != null || request.getParameter("writeOffAsAdmin") != null) {
            String[] selectedUsers = request.getParameterValues("owner");
            if (request.getParameter("delete") != null && selectedUsers != null) {
                for (int i = 0; i < selectedUsers.length; i++) {
                    userService.deleteById(Long.parseLong(selectedUsers[i]));
                }
            }
            if (request.getParameter("signAsAdmin") != null && selectedUsers != null) {
                for (int i = 0; i < selectedUsers.length; i++) {
                    userService.signAsAdmin(Long.parseLong(selectedUsers[i]));
                }
            }
            if (request.getParameter("writeOffAsAdmin") != null && selectedUsers != null) {
                for (int i = 0; i < selectedUsers.length; i++) {
                    userService.signAsUser(Long.parseLong(selectedUsers[i]));
                }
            }
        }
        response.sendRedirect("/userAdmin");
    }
}