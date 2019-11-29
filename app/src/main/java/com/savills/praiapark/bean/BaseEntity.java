package com.savills.praiapark.bean;


public class BaseEntity<T> {
    private static int SUCCESS_CODE = 0;//成功的code
    private int errorCode;
    private String errorMsg;
    private T result;

    public boolean isSuccess() {
        return getErrorCode() == SUCCESS_CODE;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", result=" + result +
                '}';
    }
}
