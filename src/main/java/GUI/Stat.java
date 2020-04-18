package GUI;

import java.io.*;
import java.util.Scanner;

public class Stat {
    private statistikaTuup tuup;
    private String file;
    private long aeg;
    private File stats;

    public Stat(statistikaTuup tuup, String file) {
        this.tuup = tuup;
        this.file = file;
        this.stats = new File(file);
    }

    public void setAeg(long aeg){
        this.aeg = aeg;
    }

    public void lisaAndmed(boolean õige) throws IOException {
        switch (this.tuup) {
            case HEADJAVEAD:
                String[] andmed;
                double kokku = 0;
                int n = 0;
                try (Scanner sc = new Scanner(stats)) {
                   while (sc.hasNextLine()) {
                       andmed = sc.nextLine().split(" ");
                       kokku = Double.parseDouble(andmed[0]);
                       n = Integer.parseInt(andmed[1]);
                   }
                }
                try (FileWriter stat1 = new FileWriter(stats)) {
                    try (BufferedWriter statHJV = new BufferedWriter(stat1)) {
                        if (õige) {
                            kokku++; n++;
                        } else {
                            kokku++;
                        }
                        statHJV.write(kokku+" "+n);
                    }
                }
                break;

            case AJAKULU:
                kokku = 0;
                n = 0;
                try (Scanner sc = new Scanner(stats)) {
                    while (sc.hasNextLine()) {
                        andmed = sc.nextLine().split(" ");
                        kokku = Double.parseDouble(andmed[0]);
                        n = Integer.parseInt(andmed[1]);
                    }
                }
                try (FileWriter stat2 = new FileWriter(stats)) {
                    try (BufferedWriter statAK = new BufferedWriter(stat2)) {
                        if (kokku == 0 && n == 0) {
                            statAK.write(aeg+" "+(n+1));
                            break;
                        }
                        if (õige) {
                            statAK.write(((kokku * n + aeg)/(n+1))+" "+(n+1));
                        } else {
                            statAK.write((kokku * n + aeg)/(n)+" "+n);
                        }
                    }
                }
                break;
        }
    }

    public double[] loeStatistikat() throws IOException {
        double[] andmed = new double[2];
        try (Scanner sc = new Scanner(stats)) {
            while (sc.hasNextLine()) {
                String[] andmejupid = sc.nextLine().split(" ");
                andmed[0] = Double.parseDouble(andmejupid[0]);
                andmed[1] = Double.parseDouble(andmejupid[1]);
            }
        }

        return andmed;
    }

    public static void failid() throws IOException {
        File headjavead = new File("headjavead.txt");
        headjavead.createNewFile();
        File ajakulu = new File("ajakulu.txt");
        ajakulu.createNewFile();
    }

    public static void failidTuhjaks() throws IOException {
        try (FileOutputStream writer = new FileOutputStream("headjavead.txt")) {
            writer.write(("").getBytes());
        }
        try (FileOutputStream writer2 = new FileOutputStream("ajakulu.txt")) {
            writer2.write(("").getBytes());
        }
    }
}

enum statistikaTuup {
    HEADJAVEAD, AJAKULU
}
