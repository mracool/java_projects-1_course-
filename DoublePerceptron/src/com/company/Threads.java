package com.company;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;

public class Threads extends Thread {

    private Pers info;

    void setInfo(Pers pers) {
        this.info = pers;
    }


    public void run() {
        switch (currentThread().getName()) {
            case "Main":
                check();
                break;
            case "1":
                info.countFirstHide();
                break;
            case "2":
                info.countSecondHide();
                break;
            case "3":
                info.countOuter();
                break;
        }
    }


    public void check() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Введите координаты для проверки - ");
            String infoFromUser = bufferedReader.readLine();
           // String strAfter1 = infoFromUser.replaceAll("\\p{Punct}", " ");
            String[] coordinates =infoFromUser.split("\\p{Punct}");
            double x = Double.parseDouble(coordinates[0]);
            double y = Double.parseDouble(coordinates[1]);
            checkingCoord(x, y);
            info.x[0] = x;
            info.x[1] = y;
        } catch (Exception myException) {
            System.out.println("Введены координаты не из заданных множеств");
            check();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void checkingCoord(double A, double B) throws Exception {
        if (A >= -2 && A <= -1 && B >= 1 && B <= 2 || A <= 2 && A >= 1 && B >= 1 && B <= 2
                || A >= -2 && A <= -1 && B <= -1 && B >= -2 || A <= 2 && A >= 1 && B >= -2 && B <= -1) {
        } else
            throw new Exception("Введены координаты из не заданого множества");
    }

}