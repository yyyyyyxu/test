package com.yanxu.book.scheduleTask.task;

public interface Task extends Runnable{

     Task getInstance();
     String getName();
     String getCode();
}
