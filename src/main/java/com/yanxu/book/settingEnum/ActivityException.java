package com.yanxu.book.settingEnum;

public enum ActivityException {


    BORROW_BOOK_NUMBER_GETMAX("10100", "借阅图书数已达到最大"),
    BORROW_BOOK_FORBID("10101","借阅图书权限已被禁止，请联系管理员");

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