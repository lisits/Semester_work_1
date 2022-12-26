package controllers.servlets;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import models.entities.Cat;
import models.entities.User;

import java.util.*;

@WebListener
public class Listener implements HttpSessionAttributeListener {

    static Map<Long, Set<Cat>> SetOfSelectedCats = new HashMap<>();

    public static Map<Long, Set<Cat>> getSetOfSelectedCats() {
        return SetOfSelectedCats;
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        GetSelectedCat(event);
    }

    private void GetSelectedCat(HttpSessionBindingEvent event) {
        if (event.getName() == "SelectedCat") {
            User user = (User) event.getSession().getAttribute("CurrentUser");
            if (user != null && !SetOfSelectedCats.containsKey(user.getId())) {
                SetOfSelectedCats.put(user.getId(), new HashSet<>());
            }
            SetOfSelectedCats.get(user.getId()).add((Cat) event.getValue());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        GetSelectedCat(event);
    }

}


