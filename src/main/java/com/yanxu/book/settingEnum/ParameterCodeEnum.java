package com.yanxu.book.settingEnum;

import lombok.AllArgsConstructor;
import lombok.Data;


public enum ParameterCodeEnum {

    LOGININ_HISTORY_TASK_RETAIN("1", "loginHistory"),
    BORROW_HISTORY_TASK_RETAIN("2", "borrowHistory"),
    REMIND_RETURNBOOK_TASK_RETAIN("3", "remindReturnBook");



    private String parameterCode;

    private String parameterName;


    ParameterCodeEnum(String ParameterCode, String parameterName) {
        this.parameterCode = ParameterCode;
        this.parameterName = parameterName;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterCode(String ParameterCode) {
        this.parameterCode = ParameterCode;
    }
}
