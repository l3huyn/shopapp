package com.project.shopapp.exceptions;

public class InvalidParamException extends Exception{
    //Thực thi lại phương thức của lớp cha
    public InvalidParamException(String message) {
        super(message);
    }
}
