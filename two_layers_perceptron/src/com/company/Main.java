package com.company;

import java.io.*;
public class Main {
    public static void main(String[] args) {

        // Создание перцептрона
        Perceptron perceptron = new Perceptron();

    }
}


class Perceptron {

    double[] enters;
    double[] hidden;
    double outer;
    double[][] wEH;
    double[] wHO;
    double[][] patterns = readFile();
            //{
          //  {0, 0}, {5, 0}, {0, 5}, {5, 5}
   // };
    double[] answers = readResult();
                    //{ 1, 1, 0,0};


    Perceptron() {
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

    }


    public double [] readResult(){
        double output [] = new double[4];
        String fileName = "Книга1.csv";
        File file= new File(fileName);

        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            int i = 0;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(";");
                output[i] = Double.parseDouble(elements[2]);
                //r[i] = Double.parseDouble(elements[2]);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }




    public double [][] readFile(){
        double pattern [][] = new double[4][2];
        String fileName = "Книга1.csv";
        File file= new File(fileName);

        try (BufferedReader br =
                new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            int i = 0;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(";");
                pattern[i][0] = Double.parseDouble(elements[0]);
                pattern[i][1] = Double.parseDouble(elements[1]);
                //r[i] = Double.parseDouble(elements[2]);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    return pattern;
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