package models.repositories.order.impl;

import models.entities.Cat;
import models.entities.Order;
import models.repositories.SqlGenerator;
import models.repositories.connection.PostgresProvider;
import models.service.cat.CatService;
import models.repositories.order.OrderRepository;
import models.service.cat.impl.CatServiceImpl;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private static final Connection connection= PostgresProvider.getConnection();

    private static final String SQL_INSERT_TO_LIBRARY = "INSERT INTO library (id_of_user, id_of_cat) VALUES (?,?)";

    private static final String SQL_INSERT_TO_BASKET = "INSERT INTO basket (id_of_order, id_of_cat) VALUES (?,?);";

    private static final String SQL_FIND_CATS_OF_CERTAIN_ORDER = "SELECT * FROM basket WHERE id_of_order = ?";

    private static final String SQL_FIND_CATS_OF_ORDER = "SELECT id_of_cat FROM basket WHERE id_of_order = ?";

    private static final String SQL_FIND_ORDERS_ID = "SELECT id FROM order_of_cats WHERE id_of_user = ?";

    private static final String SQL_FIND_ALL_ORDERS = "SELECT * FROM order_of_cats WHERE id_of_user = ?";

    public Long save(Order order) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.insert(order));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong("id");
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public void SignUpCatToLibrary(Long id_of_user, Long id_of_cat){
        try{
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TO_LIBRARY);
            statement.setLong(1, id_of_user);
            statement.setLong(2, id_of_cat);
            statement.execute();
        } catch (SQLException e){
            throw new IllegalArgumentException();
        }
    }

    public void SignUpCatsFromBasket(Long orderId, Long catId){
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TO_BASKET);
            statement.setLong(1, orderId);
            statement.setLong(2, catId);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public List<Cat> getEntitiesCatsOfCertainOrder(Order order){
        List<Cat> cats = new LinkedList<>();
        CatService catService = new CatServiceImpl();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_CATS_OF_CERTAIN_ORDER);
            statement.setLong(1, order.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Cat cat = catService.findById(resultSet.getLong("id_of_cat"));
                cats.add(cat);
            }
        } catch (SQLException e){
            throw new IllegalArgumentException();
        }
        return cats;
    }

    public List<Long> getCatsOfThisOrders(List<Long> orders){
        List<Long> cats = new LinkedList<>();
        try {
            for(Long idOfOrder: orders){
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_CATS_OF_ORDER);
                statement.setLong(1, idOfOrder);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    cats.add(Long.valueOf(resultSet.getString("id_of_cat")));
                }
            }
        } catch (SQLException e){
            throw new IllegalArgumentException();
        }
        return cats;
    }


    public List<Long> getOrdersOfThisUser(Long idOfUser){
        try {
            List<Long> orders = new LinkedList<>();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDERS_ID);
            statement.setLong(1, idOfUser);
            ResultSet resultSet=statement.executeQuery();
            while(resultSet.next()){
                orders.add(Long.valueOf(resultSet.getString("id")));
            }
            return orders;
        } catch (SQLException e){
            throw new IllegalArgumentException();
        }
    }

    public List<Order> getEntitiesOrdersOfThisUser(Long idOfUser){
        try {
            List<Order> orders = new LinkedList<>();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS);
            statement.setLong(1, idOfUser);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Order order = Order.builder()
                        .id(resultSet.getLong("id"))
                        .UserId(resultSet.getLong("id_of_user"))
                        .date(resultSet.getDate("date"))
                        .time(resultSet.getTime("time"))
                        .totalCost(resultSet.getLong("total_cost"))
                        .build();
                orders.add(order);
            }
            return orders;
        } catch (SQLException e){
            throw new IllegalArgumentException();
        }
    }
}