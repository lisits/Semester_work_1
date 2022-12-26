package models.service.cat.impl;

import models.entities.Cat;
import models.service.cat.CatService;
import models.service.order.OrderService;
import models.service.order.impl.OrderServiceImpl;
import models.repositories.cat.CatRepository;
import models.repositories.cat.impl.CatRepositoryImpl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class CatServiceImpl implements CatService {
    CatRepository catRepository = new CatRepositoryImpl();
    OrderService orderService = new OrderServiceImpl();

    public Long signUp(String name, String owner, String color, int age, Long cost) {
        Cat cat = models.entities.Cat.builder()
                .name(name)
                .owner(owner)
                .color(color)
                .age(age)
                .cost(cost)
                .build();
        Long idOfNewCat = catRepository.save(cat);
        addDefaultCatAvatar(idOfNewCat);
        return idOfNewCat;
    }

    public void saveMainImg(String img, Long id) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new URL(img).openStream());
            new File("..\\fileForImages\\PreviewCat\\" + id).mkdirs();
            File file = new File("..\\fileForImages\\PreviewCat\\" + id + "\\" + id + ".png");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public List<Long> purchasedCats(Long idOfUser) {
        List<Long> OrdersOfUser = orderService.getOrdersOfThisUser(idOfUser);
        return orderService.getCatsOfThisOrders(OrdersOfUser);
    }

    public List<Cat> getListOfCats(String column, String order, String columnForFilter, String argumentOfFilter) {
        return catRepository.getListOfCats(column, order, columnForFilter, argumentOfFilter);
    }

    public void deleteById(Long idOfCat) {
        catRepository.delete(idOfCat);
    }

    public Cat findById(Long id) {
        return catRepository.findById(id);
    }

    public void update(Cat cat) {
        catRepository.update(cat);
    }

    public void downloadDefaultCatAvatar() {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new URL("https://w7.pngwing.com/pngs/514/468/png-transparent-black-cat-dog-cat-relationship-silhouette-cat-mammal-animals-cat-like-mammal.png").openStream());
            new File("..\\fileForImages\\PreviewCat").mkdirs();
            File file = new File("..\\fileForImages\\PreviewCat\\def.png");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addDefaultCatAvatar(Long id) {
        try {
            new File("..\\fileForImages\\PreviewCat\\" + id).mkdirs();
            File file1 = new File("..\\fileForImages\\PreviewCat\\def.png");
            File file2 = new File("..\\fileForImages\\PreviewCat\\" + id + "\\" + id + ".png");
            Files.copy(file1.toPath(), file2.toPath());
        } catch (IOException e1) {
            downloadDefaultCatAvatar();
        }
    }
}