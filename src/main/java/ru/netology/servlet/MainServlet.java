package ru.netology.servlet;

import ru.netology.controller.PostController;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.AppConfig;
@WebServlet(name = "MainServlet", urlPatterns = "/api/posts/*")
public class MainServlet extends HttpServlet {
    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String DELETE_METHOD = "DELETE";
    private static final String API_PATH = "/api/posts";
    private static final String ID_REGEX = "\\d+";

    private PostController controller;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        controller = context.getBean(PostController.class);
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            if (GET_METHOD.equals(method) && API_PATH.equals(path)) {
                controller.all(resp);
                return;
            }

            if (GET_METHOD.equals(method) && path.matches(API_PATH + "/" + ID_REGEX)) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.getById(id, resp);
                return;
            }

            if (POST_METHOD.equals(method) && API_PATH.equals(path)) {
                controller.save(req.getReader(), resp);
                return;
            }

            if (DELETE_METHOD.equals(method) && path.matches(API_PATH + "/" + ID_REGEX)) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
