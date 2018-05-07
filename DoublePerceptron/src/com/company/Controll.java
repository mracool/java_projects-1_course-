package com.company;


public class Controll {
    public static void main(String[] args) throws InterruptedException {
        Pers pers = new Pers();
        pers.init();
        startChecking(pers);
    }

    public static void startChecking (Pers pers) throws InterruptedException {
        Threads mainThread = new Threads();
        mainThread.setName("Main");
        Threads firstThread = new Threads();
        firstThread.setName("1");
        Threads secondThread = new Threads();
        secondThread.setName("2");
        Threads thirdThread = new Threads();
        thirdThread.setName("3");
        thirdThread.setInfo(pers);
        mainThread.setInfo(pers);
        firstThread.setInfo(pers);
        secondThread.setInfo(pers);
        thirdThread.setInfo(pers);
        mainThread.start();
        mainThread.join();
        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();
        thirdThread.start();
        startChecking(pers);
    }

}
