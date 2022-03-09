package com.yanxu.book.auth;

import com.yanxu.book.entity.User;
import com.yanxu.book.service.impl.UserLoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserLoginServiceImp UserLoginServiceImp;

    public JsonLoginSuccessHandler(UserLoginServiceImp UserLoginServiceImp) {
        this.UserLoginServiceImp = UserLoginServiceImp;
    }

    public JsonLoginSuccessHandler(){}

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        if (roles.contains("ROLE_manager")) {
            response.sendRedirect(basePath + "book/managerGetBookList");
            String s=authentication.getPrincipal().toString();
            String[] strings=s.split(";");
            String[] userName=strings[0].split(":");
            User user=new User();
            user.setUserId(userName[2].trim());
            request.getSession().setAttribute("user",user);
            return;
        }
        String s=authentication.getPrincipal().toString();
        String[] strings=s.split(";");
        String[] userName=strings[0].split(":");
        User user=new User();
        user.setUserId(userName[2].trim());
        request.getSession().setAttribute("user",user);
        response.sendRedirect(basePath + "book/userGetBookList");
    }
}

