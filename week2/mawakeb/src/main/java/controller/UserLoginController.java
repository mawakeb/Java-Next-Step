package controller;

import db.DataBase;
import model.Http.HttpRequest;
import model.Http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserLoginController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> paramMap = request.getParametersMap();
        User user = login(paramMap.get("userId"), paramMap.get("password"));

        if (user == null) {
            response.addHeader("Set-Cookie", "logined=false");
            response.sendRedirect("/user/login_failed.html");
            log.debug("LOGIN FAILED");
            return;
        }

        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
        log.debug("LOGIN SUCCESS: " + user.getUserId());
    }

    private static User login(String userId, String password){
        User user = DataBase.findUserById(userId);
        if (user != null && user.getPassword().equals(password)){
            return user;
        }

        return null;
    }


}

