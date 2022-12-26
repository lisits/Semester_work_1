package models.repositories.user;

import models.entities.User;

import java.util.List;

public interface UserRepository {
    Long save(User user);
    List<User> findAllUsers(String column,String order,String columnForFilter,String argumentOfFilter);
    User findById(Long idOfUser);
    User findIfUniq(String mail);
    void update(User user);
    void signAsAdmin(Long id);
    void signAsUser(Long id);
    List<Long> getIdOfAvailableCats(Long id_of_user);
    List<String> findAll(String requiredColumn);
    void delete(Long idOfUser);
}
