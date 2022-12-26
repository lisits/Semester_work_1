package models.service.order.impl;


import models.entities.Cat;
import models.entities.Order;
import models.service.order.OrderService;
import models.repositories.order.OrderRepository;
import models.repositories.order.impl.OrderRepositoryImpl;
import controllers.servlets.Listener;

import java.util.*;

public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository=new OrderRepositoryImpl();
    public Long signUp(Long UserId) {
        Map<Long, Set<Cat>> SelectedCatsOfAllUsers = Listener.getSetOfSelectedCats();
        Collection<Cat> cats = SelectedCatsOfAllUsers.get(UserId);
        Long costOfSelectedCats = 0L;
        for(Cat cat : cats){
            costOfSelectedCats = costOfSelectedCats + cat.getCost();
        }
        Order order=Order.builder()
                .UserId(UserId)
                .totalCost(costOfSelectedCats)
                .build();
         return orderRepository.save(order);
    }

    public void SignUpCatToLibrary(Long id_of_user, Long catId){
        orderRepository.SignUpCatToLibrary(id_of_user, catId);
    }

    public void SignUpCatsFromBasket(Long idOfOrder, Long catId){
        orderRepository.SignUpCatsFromBasket(idOfOrder, catId);
    }

    public List<Long> getCatsOfThisOrders(List<Long> orders){
        return orderRepository.getCatsOfThisOrders(orders);
    }

    public List<Long> getOrdersOfThisUser(Long idOfUser){
       return orderRepository.getOrdersOfThisUser(idOfUser);
    }


    public List<Cat> getEntitiesCatsOfCertainOrder(Order order){
        return orderRepository.getEntitiesCatsOfCertainOrder(order);
    }

    public List<Order> getEntitiesOrdersOfThisUser(Long idOfUser){
       return orderRepository.getEntitiesOrdersOfThisUser(idOfUser);
    }

    public void deleteCatsFromBasket(Long idOfUser, String[] catsToDelete){
        Set<Cat> cats = Listener.getSetOfSelectedCats().get(idOfUser);
        Set<Cat> deleteCats =new HashSet<>();
        for(Cat cat : cats){
            for(int i = 0; i < catsToDelete.length; i++){
                if(Long.parseLong(catsToDelete[i])== cat.getId()){
                    deleteCats.add(cat);
                }
            }
        }
        for(Cat cat : deleteCats){
            Listener.getSetOfSelectedCats().get(idOfUser).remove(cat);
        }
    }
}
