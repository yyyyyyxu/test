package com.yanxu.book.settingEnum;

public enum ActivityException {


    BORROW_BOOK_NUMBER_GETMAX("10100", "借阅图书数已达到最大");

    private String code;
    private String message;

    ActivityException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    }