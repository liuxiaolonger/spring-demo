package com.etoc.utils;

public enum HttpStatus {

    OK(200, "请求成功"),
    BAD_REQUEST(400, "请求出错"),
    UNAUTHORIZED(401, "没有登录"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "找不到页面"),
    IS_TIMEOUT(666, "验证码过期"),
    CODE_MISMATHC(667, "验证码输入错误,请重新输入"),
    INTERNAL_SERVER_ERROR(500, "服务器出错");

    private final int value;

    private final String msg;

    HttpStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public int value() {
        return value;
    }

    public String msg() {
        return msg;
    }
}
