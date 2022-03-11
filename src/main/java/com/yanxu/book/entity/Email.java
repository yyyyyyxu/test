package com.yanxu.book.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Email {
    @Value("${spring.mail.username}")
    private String from;
}
