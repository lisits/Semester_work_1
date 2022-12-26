package models.repositories.user.impl;


import models.entities.User;
import models.repositories.connection.PostgresProvider;
import models.repositories.user.UserRepository;
import models.repositories.SqlGenerator;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final Connection connection= PostgresProvider.getConnection();

    private static final String SQL_SELECT_USER_BY_MAIL ="SELECT * FROM users WHERE mail = ?";

    private static final String SQL_UPDATE_TO_ADMIN ="UPDATE users SET admin = true WHERE id = ?";

    private static final String SQL_UPDATE_TO_USER ="UPDATE users SET admin = false WHERE id = ?";

    private static final String SQL_SELECT_AVAILABLE_CATS ="SELECT id_of_cat FROM library WHERE id_of_user = ?";

    public  Long save(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.insert(user));
            ResultSet resultSet= statement.executeQuery();
            resultSet.next();
            return resultSet.getLong("id");
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public List<User> findAllUsers(String column,String order,String columnForFilter,String argumentOfFilter){
        List<User> users = new LinkedList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.findAll("users", column, order, columnForFilter, argumentOfFilter));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = User.builder()
                        .id(Long.valueOf(resultSet.getString("id")))
                        .nick(resultSet.getString("nick"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .mail(resultSet.getString("mail"))
                        .password(resultSet.getString("password"))
                        .isAdmin(resultSet.getBoolean("admin"))
                        .salt(resultSet.getString("salt"))
                        .build();
                users.add(user);
            }
        } catch (SQLException e){
            throw new IllegalArgumentException();
        }
        return users;
    }

    public User findById(Long idOfUser){
        User user = User.builder()
                .id(idOfUser)
                .build();
        try{
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.findById(user));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                user = User.builder()
                        .id(Long.valueOf(resultSet.getString("id")))
                        .nick(resultSet.getString("nick"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .mail(resultSet.getString("mail"))
                        .password(resultSet.getString("password"))
                        .isAdmin(resultSet.getBoolean("admin"))
                        .salt(resultSet.getString("salt"))
                        .build();
            }
        } catch (SQLException e){
            throw new IllegalArgumentException();
        }
        return user;
    }

    public User findIfUniq(String mail){
        try {
            User user = null;
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_MAIL);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            try{
                while (resultSet.next()){
                    user = User.builder()
                            .id(Long.valueOf(resultSet.getString("id")))
                            .nick(resultSet.getString("nick"))
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .mail(mail)
                            .password(resultSet.getString("password"))
                            .isAdmin(resultSet.getBoolean("admin"))
                            .salt(resultSet.getString("salt"))
                            .build();
                }
                return user;
            } catch (SQLException e){
                throw new IllegalArgumentException();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public void update(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.update(user));
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public void signAsAdmin(Long id){
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TO_ADMIN);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public void signAsUser(Long id){
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TO_USER);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public List<Long> getIdOfAvailableCats(Long id_of_user){
        List<Long> catsIds = new LinkedList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_AVAILABLE_CATS);
            statement.setLong(1, id_of_user);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                catsIds.add(resultSet.getLong("id_of_cat"));
            }
        } catch (SQLException e){
            throw new IllegalArgumentException();
        }
        return catsIds;
    }

    public List<String> findAll(String requiredColumn){
        List<String> users = new LinkedList<>();
        try{
            String SQL="SELECT " + requiredColumn + " FROM users order by id";
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                users.add(resultSet.getString(requiredColumn));
            }
        } catch (SQLException e){
            throw new IllegalArgumentException();
        }
        return users;
    }

    public void delete(Long idOfUser){
        try {
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.deleteById(idOfUser,"users"));
            statement.execute();
        } catch (SQLException e){
            throw new IllegalArgumentException();
        }
    }
}
