package com.sicnu.sta.utils;

public class ResultUtils<T> {

    private static final int CODE_SUCCESS = 200;
    private static final int CODE_FAIL = 500;

    private static final String MSG_SUCCESS = "success";
    private static final String MSG_FAIL = "failed";

    private static final String SUCCESS = "操作成功";
    private static final String FAIL = "操作失败";
    private static final String ERROR = "异常错误";

    private int code;
    private String msg;
    private T data;

    public ResultUtils() {
    }

    public ResultUtils(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultUtils(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultUtils<Object> success() {
        return  new ResultUtils<>(CODE_SUCCESS, MSG_SUCCESS, SUCCESS);
    }

    public static ResultUtils<Object> success(Object data) {
        return  new ResultUtils<>(CODE_SUCCESS, MSG_SUCCESS, data);
    }



    public static ResultUtils<Object> fail() {
        return new ResultUtils<>(CODE_FAIL, MSG_FAIL, FAIL);
    }

    public static ResultUtils<Object> fail(String data) {
        return new ResultUtils<>(CODE_FAIL, MSG_FAIL, data);
    }

    public static ResultUtils<Object> error() {
        return new ResultUtils<>(CODE_FAIL, MSG_FAIL, ERROR);
    }

    public static ResultUtils<Object> error(String data) {
        return new ResultUtils<>(CODE_FAIL, MSG_FAIL, data);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultUtils{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
