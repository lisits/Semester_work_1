package models.repositories.cat.impl;

import models.entities.Cat;
import models.repositories.SqlGenerator;
import models.repositories.connection.PostgresProvider;
import models.repositories.cat.CatRepository;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CatRepositoryImpl implements CatRepository {

    private static final Connection connection = PostgresProvider.getConnection();

    public Long save(Cat cat) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.insert(cat));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong("id");
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public Cat findById(Long id) {
        try {
            Cat cat = models.entities.Cat.builder()
                    .id(id)
                    .build();
            Cat findCat = null;
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.findById(cat));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                findCat = models.entities.Cat.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .owner(resultSet.getString("owner"))
                        .color(resultSet.getString("color"))
                        .cost(resultSet.getLong("cost"))
                        .age(resultSet.getInt("age"))
                        .build();
            }
            return findCat;
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }


    public List<Cat> getListOfCats(String column, String order, String columnForFilter, String argumentOfFilter) {
        try {
            List<Cat> cats = new LinkedList<>();
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.findAll("cat", column, order, columnForFilter, argumentOfFilter));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cat cat = models.entities.Cat.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .owner(resultSet.getString("owner"))
                        .color(resultSet.getString("color"))
                        .cost(resultSet.getLong("cost"))
                        .age(resultSet.getInt("age"))
                        .build();
                cats.add(cat);
            }
            return cats;
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public void delete(Long idOfCat) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.deleteById(idOfCat, "cat"));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Cat cat) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.update(cat));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
