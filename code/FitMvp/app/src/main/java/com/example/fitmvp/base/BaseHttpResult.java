package com.example.fitmvp.base;

/**
 * Created by GaoSheng on 2016/10/21.
 * 抽取的一个基类的bean,直接在泛型中传data就行
 */

public class BaseHttpResult<T> {
    private Integer code;
    // private String message;
    private T data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /*
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    */

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /*
    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
    */
}
