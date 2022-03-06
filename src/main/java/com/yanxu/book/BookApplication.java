package com.yanxu.book;

import com.yanxu.book.scheduleTask.TaskScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookApplication.class, args);
        System.out.println("nihao");
    }

}
