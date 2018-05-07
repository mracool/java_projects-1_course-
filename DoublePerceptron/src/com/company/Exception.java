package com.company;



public class Exception extends Throwable {
    public static String name;
    Exception(String name) {
        this.name = name;
    }
}