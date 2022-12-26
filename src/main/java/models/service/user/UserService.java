package models.service.user;

import jakarta.servlet.http.HttpServletRequest;
import models.entities.Cat;
import models.entities.User;

import java.util.List;

public interface UserService {
    String signUp(String nick, String firstName, String lastName, String mail, String password,String img);
    void downloadDefaultAvatar();
    void addNewAvatar(Long id, String path);
    User findUser(String mail, String password);
    String update(Long id,String nick, String firstName, String lastName, String mail, String password,boolean admin,String salt);
    void signAsAdmin(Long id);
    void signAsUser(Long id);
    List<User> findAllUsers(String column,String order,String columnForFilter,String argumentOfFilter);
    void deleteById(Long idOfUser);
    List<Cat> getAvailableCats(Long id_of_user);
    String get_SHA_512_SecurePassword(String passwordToHash, String salt);

}
