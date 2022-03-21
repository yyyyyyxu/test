package com.yanxu.book.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class Email implements Serializable {
    private static final long serialVersionUID = -7696723325653722908L;
    @Value("${spring.mail.username}")
    private String from;
}
