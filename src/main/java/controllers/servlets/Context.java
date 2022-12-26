package controllers.servlets;

import models.entities.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import models.entities.Order;
import models.repositories.SqlGenerator;
import models.repositories.connection.PostgresProvider;
import models.service.cat.impl.CatServiceImpl;
import models.service.order.impl.OrderServiceImpl;
import models.service.user.impl.UserServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class Context implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Connection connection = PostgresProvider.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SqlGenerator.createTable(User.class));
            statement.execute(SqlGenerator.createTable(Order.class));
            statement.execute(SqlGenerator.createTable(Basket.class));
            statement.execute(SqlGenerator.createTable(Library.class));
            statement.execute(SqlGenerator.createTable(Cat.class));
            statement.execute(SqlGenerator.createTable(File.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UserServiceImpl userService = new UserServiceImpl();
        userService.downloadDefaultAvatar();
        CatServiceImpl catService = new CatServiceImpl();
        catService.downloadDefaultCatAvatar();
        sce.getServletContext().setAttribute("userService", new UserServiceImpl());
        sce.getServletContext().setAttribute("catService", new CatServiceImpl());
        sce.getServletContext().setAttribute("orderService", new OrderServiceImpl());
    }
}
