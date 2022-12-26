package models.service.user.impl;

import models.entities.Cat;
import models.entities.User;
import models.repositories.user.UserRepository;
import models.repositories.user.impl.UserRepositoryImpl;
import models.service.cat.CatService;
import models.service.cat.impl.CatServiceImpl;
import models.service.user.UserService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserRepository userRepository = new UserRepositoryImpl();
    CatService catService = new CatServiceImpl();

    public String signUp(String nick, String firstName, String lastName, String mail, String password, String img) {
        if (nick.equals("") || mail.equals("") || password.equals("")) {
            return "Please fill in all required fields!";
        }
        List<String> checkColumnNick = userRepository.findAll("nick");
        List<String> checkColumnMail = userRepository.findAll("mail");
        if (checkColumnMail.contains(mail)) {
            return "This email is already in use";
        }
        if (checkColumnNick.contains(nick)) {
            return "This nick is already in use";
        }
        String salt;
        try {
            salt = getSalt();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }

        User user = User.builder()
                .nick(nick)
                .firstName(firstName)
                .lastName(lastName)
                .mail(mail)
                .password(get_SHA_512_SecurePassword(password, salt))
                .salt(salt)
                .build();
        Long id = userRepository.save(user);
        addDefaultAvatar(id);
        return "All clear!";
    }

    private void addDefaultAvatar(Long id) {
        try {
//            new File("..\\fileForImages\\UsersAvatar").mkdirs();
//            //Input Stream - тут должен лежать URI файла (change URL)
////            BufferedInputStream inputStream = new BufferedInputStream(new URL("file:///C:/Users/lisit/Desktop/bitchFaces/20201003_125022.jpg").openStream());
//            File usersPath = new File("..\\fileForImages\\UsersAvatar\\"  + id + ".png");
//
//            File newPath = new File("..\\fileForImages\\UsersAvatar\\" + id + ".png");
//            Files.copy(usersPath.toPath(), newPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
////            Files.copy(inputStream, newPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
            new File("..\\fileForImages\\UsersAvatar").mkdirs();
            File file1 = new File("..\\fileForImages\\UsersAvatar\\def.png");
            File file2 = new File("..\\fileForImages\\UsersAvatar\\" + id + ".png");
            Files.copy(file1.toPath(), file2.toPath());
        } catch (IOException e1) {
            throw new IllegalArgumentException();
        }
    }

    public void addNewAvatar(Long id, String path) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new URL(path).openStream());
            new File("..\\fileForImages\\UsersAvatar").mkdirs();
            File newPath = new File("..\\fileForImages\\UsersAvatar\\" + id + ".png");
            Files.copy(inputStream, newPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadDefaultAvatar() {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new URL("file:///C:/Users/lisit/Desktop/bitchFaces/20201003_125022.jpg").openStream());
            new File("..\\fileForImages\\UsersAvatar").mkdirs();
            File file = new File("..\\fileForImages\\UsersAvatar\\def.png");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User findUser(String mail, String password) {
        User user = userRepository.findIfUniq(mail);
        if (user != null && user.getPassword().equals(get_SHA_512_SecurePassword(password, user.getSalt()))) {
            return user;
        }
        return null;
    }

    public String update(Long id, String nick, String firstName, String lastName, String mail, String password, boolean admin, String salt) {
        if (nick.equals("") || mail.equals("") || password.equals("")) {
            return "Please fill in all required fields!";
        }
        List<User> checkColumn = userRepository.findAllUsers("id", "asc", null, null);
        for (long i = 0; i < checkColumn.size(); i++) {
            if (checkColumn.get((int) i).getId() != id && checkColumn.get((int) i).getMail().equals(mail)) {
                return "This email is already in use";
            }
        }

        for (long i = 0; i < checkColumn.size(); i++) {
            if (checkColumn.get((int) i).getId() != id && checkColumn.get((int) i).getNick().equals(nick)) {
                return "This nick is already in use";
            }
        }

        User user = User.builder()
                .id(id)
                .nick(nick)
                .firstName(firstName)
                .lastName(lastName)
                .mail(mail)
                .password(get_SHA_512_SecurePassword(password, salt))
                .isAdmin(admin)
                .salt(salt)
                .build();
        userRepository.update(user);
        return "All clear!";
    }

    public void signAsAdmin(Long id) {
        userRepository.signAsAdmin(id);
    }

    public void signAsUser(Long id) {
        userRepository.signAsUser(id);
    }

    public List<User> findAllUsers(String column, String order, String columnForFilter, String argumentOfFilter) {
        return userRepository.findAllUsers(column, order, columnForFilter, argumentOfFilter);
    }

    public void deleteById(Long idOfUser) {
        userRepository.delete(idOfUser);
    }

    public List<Cat> getAvailableCats(Long id_of_user) {
        List<Long> idOfAvailableCats = userRepository.getIdOfAvailableCats(id_of_user);
        List<Cat> availableCats = new LinkedList<>();
        for (Long id : idOfAvailableCats) {
            availableCats.add(catService.findById(id));
        }
        return availableCats;
    }


    public String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
        return generatedPassword;
    }

    public String getSalt() throws NoSuchAlgorithmException {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
    }
}