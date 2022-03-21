package com.yanxu.book.scheduleTask;

import com.yanxu.book.scheduleTask.task.imp.BorrowHistoryTaskImp;
import com.yanxu.book.scheduleTask.task.Task;
import com.yanxu.book.scheduleTask.task.imp.RemindReturnBookTaskImp;
import com.yanxu.book.scheduleTask.task.imp.TheUserToUnlockTaskImp;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.List;

public class ThreadPoolUtil {
    private ThreadPoolUtil() {
    }

    private static volatile ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private static List<Task> taskList = new ArrayList<>();

    public static ThreadPoolTaskScheduler getInstance() {
        if (threadPoolTaskScheduler == null) {
            synchronized (ThreadPoolUtil.class) {
                if (threadPoolTaskScheduler == null) {
                    threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
                    threadPoolTaskScheduler.setPoolSize(5);
                    threadPoolTaskScheduler.setThreadNamePrefix("taskExecutor-");
                    threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
                    threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
                    threadPoolTaskScheduler.initialize();
                }
            }
        }
        return threadPoolTaskScheduler;
    }

    public static List<Task> getRunnableList(){
        return taskList;
    }

    static {
//        taskList.add(BorrowHistoryTaskImp.getBorrowHistoryTask());
        taskList.add(TheUserToUnlockTaskImp.getTheUserToUnlockTask());
    }
}

