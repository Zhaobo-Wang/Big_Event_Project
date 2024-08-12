package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;       // 响应状态码 0-成功 1-失败
    private String message;     // 显示信息
    private T data;             // 响应数据

    // 快速返回操作成功的结果（带响应数据）
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    // 快速返回操作成功的结果（不带响应数据）
    public static Result success() {
        return new Result<>(0, "操作成功", null);
    }

    // 快速返回操作失败的结果
    public static Result error(String message) {
        return new Result<>(1, message, null);
    }
}