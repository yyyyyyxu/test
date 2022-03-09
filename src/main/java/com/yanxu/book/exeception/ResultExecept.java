package com.yanxu.book.exeception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultExecept extends Exception {

    private String code;

    public ResultExecept(String code) {
        this.code = code;
    }

    public ResultExecept(String code, Object message) {
        super(message.toString());
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


}

