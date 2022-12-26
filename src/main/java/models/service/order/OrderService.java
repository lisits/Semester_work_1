package models.service.order;

import models.entities.Cat;
import models.entities.Order;

import java.util.List;

public interface OrderService {
    Long signUp(Long UserId);
    List<Order> getEntitiesOrdersOfThisUser(Long idOfUser);
    List<Cat> getEntitiesCatsOfCertainOrder(Order order);
    void SignUpCatToLibrary(Long id_of_user, Long catId);
    void SignUpCatsFromBasket(Long idOfOrder, Long catId);
    List<Long> getCatsOfThisOrders(List<Long> orders);
    List<Long> getOrdersOfThisUser(Long idOfUser);
    void deleteCatsFromBasket(Long idOfUser, String[] catsToDelete);

}
