package com.yanxu.book.exeception;

import org.springframework.security.core.AuthenticationException;


public class LoginFailureExecept extends AuthenticationException {


    public LoginFailureExecept(String msg, Throwable t) {
        super(msg, t);
    }

    public LoginFailureExecept(String msg) {
        super(msg);
    }
}
