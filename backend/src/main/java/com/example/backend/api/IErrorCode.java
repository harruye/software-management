package com.example.backend.api;


public interface IErrorCode {
    /**
     * 返回码
     */
    long getCode();

    /**
     * 返回信息
     */
    String getMessage();
}
