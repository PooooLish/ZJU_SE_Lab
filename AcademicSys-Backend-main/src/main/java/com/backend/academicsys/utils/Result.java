package com.backend.academicsys.utils;

import java.util.HashMap;
import java.util.Map;

public class Result {
    private boolean success;
    private String message;
    private Map<String,Object> data=new HashMap<>();

    private Result(){}

    public static Result ok(){  //成功的静态方法
        Result r=new Result();
        r.setSuccess(true);
        r.setMessage("成功");
        return r;
    }

    public static Result  error(){  //失败的静态方法
        Result r=new Result();
        r.setSuccess(false);
        r.setMessage("失败");
        return r;
    }

    public Result data(String key,Object value){    //设置真的数据value，向map里添加一个键值对
        this.data.put(key, value);
        return this;
    }
    public Result message(String mes){
        this.setMessage(mes);
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
