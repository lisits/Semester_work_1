package models.repositories.cat;

import models.entities.Cat;

import java.io.OutputStream;
import java.util.List;

public interface CatRepository {
    Long save(Cat cat);
    Cat findById(Long id);
    List<Cat> getListOfCats(String column, String order, String columnForFilter, String argumentOfFilter);
    void delete(Long idOfCat);
    void update(Cat cat);
}
