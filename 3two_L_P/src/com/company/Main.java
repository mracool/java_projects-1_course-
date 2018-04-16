package com.company;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws MyException {
        Perceptron perceptron = new Perceptron();

    }
}


class Perceptron {

    double[] enters;
    double[] hidden;
    double outer;
    double[][] wEH;
    double[] wHO;
    double[][] patterns = patterns();
    double temp;
    //= {
//            {0, 0},
// {5, 0},
// {0, 5},
// {5, 5}
//    };
    double[] answers = {0, 1, 1, 0};

    Perceptron() throws MyException {
        enters = new double[patterns[0].length];
        hidden = new double[2];
        wEH = new double[enters.length][hidden.length];
        wHO = new double[hidden.length];

        initWeights();
        study();
        for (int p = 0; p < patterns.length; p++) {
            for (int i = 0; i < enters.length; i++)
                enters[i] = patterns[p][i];

            countOuter();
            System.out.println(outer);
        }
        check_coords();
    }

    public double[][] patterns() {
        double[][] pattern = new double[4][2];
        Scanner obj = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.println("Input" + i + j);
                System.out.println();
                pattern[i][j] = obj.nextDouble();
            }
        }
        return pattern;
    }

    public void check_coords() throws MyException {
        System.out.println("Checking process started");
        Scanner obj = new Scanner(System.in);
        try {
            double y = obj.nextDouble();
            double x = obj.nextDouble();
            checking(x,y);
        } catch (MyException e) {
            System.out.println(e.name);
        }



    }
    public void checking(double x, double y) throws MyException {
        if (y == x) {
            temp = 0;
        } else if (y == 0 && x != y || y == 5 && x != y) {
            temp = 1;
        } else {
            throw new MyException("Input exception!");
        }
    }

    public void initWeights() {
        for (int i = 0; i < enters.length; i++) {
            for (int j = 0; j < wEH.length; j++) {
                wEH[i][j] = Math.random() * 0.2 + 0.1;
            }
        }
        for (int i = 0; i < wHO.length; i++)
            wHO[i] = Math.random() * 0.2 + 0.1;
    }

    public void countOuter() {
        for (int i = 0; i < hidden.length; i++) {
            hidden[i] = 0;
            for (int j = 0; j < enters.length; j++) {
                hidden[i] += enters[j] * wEH[j][i];
            }
            if (hidden[i] > 2.5) hidden[i] = 1;
            else hidden[i] = 0;
        }
        outer = 0;
        for (int i = 0; i < hidden.length; i++) {
            outer += hidden[i] * wHO[i];
        }
        if (outer > 2.5) outer = 1;
        else outer = 0;
    }

    public void study() {
        double[] err = new double[hidden.length];
        double gError = 0;
        do {
            gError = 0;
            for (int p = 0; p < patterns.length; p++) {
                for (int i = 0; i < enters.length; i++)
                    enters[i] = patterns[p][i];

                countOuter();

                double lErr = answers[p] - outer;
                gError += Math.abs(lErr);

                for (int i = 0; i < hidden.length; i++)
                    err[i] = lErr * wHO[i];
                for (int i = 0; i < enters.length; i++) {
                    for (int j = 0; j < hidden.length; j++) {
                        wEH[i][j] += 0.1 * err[j] * enters[i];

                    }
                }
                for (int i = 0; i < hidden.length; i++)
                    wHO[i] += 0.1 * lErr * hidden[i];
            }
        } while (gError != 0);
    }
}

class MyException extends Exception {
    String name;

    public MyException(String message) {
        this.name = message;
    }
}