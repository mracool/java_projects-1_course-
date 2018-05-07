package com.company;
import java.io.*;

public class Pers {

    Pers pers;

    public void setPers(Pers pers) {
        this.pers = pers;
    }

    double[] x, h ,why;
    double[] r = new double[16];
    double[][] wxh;
    double[][] pat = new double[16][2];
    double y;

    public void init() {
        x = new double[pat[0].length];
        h = new double[2];
        wxh = new double[x.length][h.length];
        why = new double[h.length];
        read();
        System.out.println("Начальные значения весов первого слоя");
        wxh[0][0] = 0.5;
        wxh[0][1] = 0.2;
        wxh[1][0] = 0.6;
        wxh[1][1] = 0.4;
        for (int i = 0; i < wxh.length; i++) {
            for (int j = 0; j < wxh[i].length; j++) {
                System.out.println("wxh[" + i + "][" + j + "]=" + wxh[i][j]);
            }
        }
        System.out.println("Начальные значения весов второго слоя");
        why[0] = 0.6;
        why[1] = -0.1;
        for (int i = 0; i < why.length; i++) {
            System.out.println("why[" + i + "]=" + why[i]);
        }
        study();
    }

    public void cy() {
        for (int i = 0; i < h.length; i++) {
            h[i] = 0;
            for (int j = 0; j < x.length; j++)
                h[i] += x[j] * wxh[j][i];
            if (h[i] > 0.5)
                h[i] = 1;
            else
                h[i] = 0;
        }
        y = 0;
        for (int i = 0; i < h.length; i++)
            y += h[i] * why[i];
        if (y > 0.5)
            y = 1;
        else
            y = 0;
    }

    public void study() {
        double[] err = new double[h.length];
        double gEr = 0;
        double m = 0;
        do {
            gEr = 0;
            for (int p = 0; p < pat.length; p++) {
                for (int i = 0; i < x.length; i++)
                    x[i] = pat[p][i];
                cy();
                double lEr = r[p] - y;
                gEr += Math.abs(lEr);
                for (int i = 0; i < h.length; i++)
                    err[i] = lEr * why[i];
                for (int i = 0; i < x.length; i++) {
                    for (int j = 0; j < h.length; j++) {
                        wxh[i][j] += 0.5 * err[j] * x[i];
                    }
                }
                for (int i = 0; i < h.length; i++)
                    why[i] += 0.5 * lEr * h[i];
            }
            m++;
        } while (gEr != 0);
        System.out.println("Количество итераций равно " + m);
        System.out.println(" Веса первого слоя");
        for (int i = 0; i < wxh.length; i++) {
            for (int j = 0; j < wxh[i].length; j++) {
                System.out.println("wxh[" + i + "][" + j + "]=" + wxh[i][j]);
            }
        }
        System.out.println("Веса второго слоя");
        for (int i = 0; i < why.length; i++) {
            System.out.println("why[" + i + "]=" + why[i]);
        }
    }

    public void read() {
        String fileName = "Книга1.csv";
        File file = new File(fileName);
        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            int i = 0;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(";");
                pat[i][0] = Double.parseDouble(elements[0]);
                pat[i][1] = Double.parseDouble(elements[1]);
                r[i] = Double.parseDouble(elements[2]);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void countFirstHide() {
        h[0] = 0;
        for (int j = 0; j < x.length; j++) {
            h[0] += x[j] * wxh[j][0];
        }
        if (h[0] > 0.5)
            h[0] = 1;
        else
            h[0] = 0;
    }

    public void countSecondHide() {
        h[1] = 0;
        for (int j = 0; j < x.length; j++) {
            h[1] += x[j] * wxh[j][1];
        }
        if (h[1] > 0.5)
            h[1] = 1;
        else
            h[1] = 0;
    }

    public void countOuter() {
        y = 0;
        for (int i = 0; i < h.length; i++) {
            y += h[i] * why[i];
        }
        if (y > 0.5)
            y = 1;
        else
            y = 0;
        System.out.println("Точка заданна в множестве - " + y);
    }

}
