package models.service.cat;

import models.entities.Cat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public interface CatService {
    Long signUp(String name, String owner, String color, int age, Long cost);
    List<Long> purchasedCats(Long idOfUser);
    List<Cat> getListOfCats(String column, String order, String columnForFilter, String argumentOfFilter);
    void deleteById(Long idOfCat);
    Cat findById(Long id);
    void saveMainImg(String img,Long id);
    void update(Cat cat);
    void downloadDefaultCatAvatar();
}
