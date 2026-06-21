package org.com.gx.ebusiness.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code; // 200 成功，500 失败
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }
}
