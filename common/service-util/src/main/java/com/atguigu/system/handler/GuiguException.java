package com.atguigu.system.handler;

import com.atguigu.common.result.ResultCodeEnum;

/**
 * @author zzj
 * @date 2022/9/20
 */
public class GuiguException extends RuntimeException {

    private Integer code;
    private String message;

    public GuiguException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public GuiguException(ResultCodeEnum resultCodeEnum){
        this.code = resultCodeEnum.getCode();
        this.message= resultCodeEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GuiguException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}