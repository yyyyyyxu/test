//package com.yanxu.book.aspect;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import com.yanxu.book.entity.User;
//import com.yanxu.book.entity.UserLoginHistory;
//import com.yanxu.book.mapper.UserLoginHistoryMapper;
//import com.yanxu.book.mapper.UserMapper;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class LoginInAspect {
//
//    @Autowired
//    UserLoginHistoryMapper userLoginHistoryMapper;
//
//    public static String name;
//
//    @Autowired
//    UserMapper userMapper;
//
//    @Pointcut(value = "execution(* com.yanxu.book.auth.AbstractBookUserDetailsAuthenticationProvider.authenticate(..))")
//    public void pointcut(){}
//
//    @AfterReturning(value = "pointcut()",returning = "ret")
//    public void BeforeLoginInHistory(JoinPoint joinPoint,Object ret){
//        Object[] authenticationTokenClass = joinPoint.getArgs();
//        UsernamePasswordAuthenticationToken authenticationTokens = (UsernamePasswordAuthenticationToken) authenticationTokenClass[0];
//        UserLoginHistory userLoginHistory = new UserLoginHistory();
//        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserId,(String) authenticationTokens.getPrincipal()));
//        user.setFaultTime(user.getFaultTime()+1);
//        userMapper.update(user,new UpdateWrapper<User>().lambda().eq(User::getUserId,(String) authenticationTokens.getPrincipal()));
//        userLoginHistory.setUserName((String) authenticationTokens.getPrincipal());
//        userLoginHistoryMapper.insert(userLoginHistory);
//    }
//}
