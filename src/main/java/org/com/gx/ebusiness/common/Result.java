package org.com.gx.ebusiness.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code; // 200代表成功，500代表出错了
    private String msg;   // 给前端看的提示信息
    private T data;       // 真正要返回的数据

    // 成功的时候调这个
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    // 失败的时候调这个
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}