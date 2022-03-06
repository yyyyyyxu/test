//package com.yanxu.book.aspect;
//
//import com.yanxu.book.entity.LoginFailureHistory;
//import com.yanxu.book.entity.UserLoginHistory;
//import com.yanxu.book.mapper.UserLoginFailureHistoryMapper;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class loginFailureAspect {
//
//    @Autowired
//    UserLoginFailureHistoryMapper userLoginFailureHistoryMapper;
//
//    @Pointcut(value = "execution(* org.springframework.security.authentication.ProviderManager.authenticate(..))")
//    public void pointcut() {
//    }
//
//    @AfterReturning(returning = "ret", value = "pointcut()")
//    public void BeforeLoginInHistory(JoinPoint joinPoint, Object ret) {
//        Object[] authenticationTokenClass = joinPoint.getArgs();
//        if (ret instanceof Exception) {
//            Authentication authenticationTokens = (Authentication) authenticationTokenClass[0];
//            LoginFailureHistory loginFailureHistory = new LoginFailureHistory();
//            loginFailureHistory.setUserName((String) authenticationTokens.getPrincipal());
//            userLoginFailureHistoryMapper.insert(loginFailureHistory);
//        }
//
//    }
//
//}
