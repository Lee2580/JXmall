package com.lee.jxmall.member.exception;

public class PhoneExistException extends RuntimeException{

    public PhoneExistException(){
        super("该手机号已注册");
    }
}
