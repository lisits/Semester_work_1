package models.repositories.order;

import models.entities.Cat;
import models.entities.Order;

import java.util.List;

public interface OrderRepository {
    Long save(Order order);
    void SignUpCatToLibrary(Long id_of_user, Long id_of_game);
    void SignUpCatsFromBasket(Long orderId, Long gameId);
    List<Order> getEntitiesOrdersOfThisUser(Long idOfUser);
    List<Long> getCatsOfThisOrders(List<Long> orders);
    List<Long> getOrdersOfThisUser(Long idOfUser);
    List<Cat> getEntitiesCatsOfCertainOrder(Order order);
}
