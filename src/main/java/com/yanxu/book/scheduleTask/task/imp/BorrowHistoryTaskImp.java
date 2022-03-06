package com.yanxu.book.scheduleTask.task.imp;

import com.yanxu.book.entity.UserLoginHistory;
import com.yanxu.book.scheduleTask.task.Task;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class BorrowHistoryTaskImp implements Task {



    private static BorrowHistoryTaskImp borrowHistoryTask;

    private String taskName="借阅历史定时";

    public static BorrowHistoryTaskImp getBorrowHistoryTask() {
        if (borrowHistoryTask == null) {
            synchronized (BorrowHistoryTaskImp.class) {
                if (borrowHistoryTask == null) {
                    borrowHistoryTask = new BorrowHistoryTaskImp();
                    borrowHistoryTask.setTaskName("BorrowHistoryTask");
                }
            }
        }
        return borrowHistoryTask;
    }

    @Override
    public void run() {

    }

    @Override
    public Task getInstance() {
        return BorrowHistoryTaskImp.getBorrowHistoryTask();
    }

    @Override
    public String getName() {
        return taskName;
    }

    @Override
    public String getCode() {
        return null;
    }
}
