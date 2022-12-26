package controllers.servlets.filteres;

import models.entities.Cat;
import models.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.cat.CatService;

import java.io.IOException;

@WebFilter(urlPatterns = {"/cat"})
public class CatFilter extends HttpFilter {
    CatService catService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        catService = (CatService) config.getServletContext().getAttribute("catService");
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Cat cat = catService.findById(Long.parseLong(req.getParameter("ClickCat")));
        if (cat == null) {
            res.sendRedirect("/");
            return;
        }
        if (req.getSession().getAttribute("CurrentUser") != null) {
            User user = (User) req.getSession().getAttribute("CurrentUser");
            if (user.isAdmin()) {
                req.getSession().setAttribute("ClickCat", Long.parseLong(req.getParameter("ClickCat")));
                res.sendRedirect("/catAdmin");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
