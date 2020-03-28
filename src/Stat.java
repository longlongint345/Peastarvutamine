import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Stat {
    private statistikaTuup tuup;
    private String file;
    private int toevaartus;
    private List<Long> vaheajad = new ArrayList<>();

    public Stat(statistikaTuup tuup, String file) {
        this.tuup = tuup;
        this.file = file;
    }

    public void setToevaartus(int toevaartus) {
        this.toevaartus = toevaartus;
    }

    public void lisaAndmed() throws IOException {
        switch (this.tuup) {
            // kui on IOException siis võib .close() kasutades fail jääda sulgemata
            case HEADJAVEAD:
                try (FileWriter stat1 = new FileWriter(file, true)) {
                    try (BufferedWriter statHJV = new BufferedWriter(stat1)) {
                        statHJV.write(String.valueOf(toevaartus));
                        statHJV.newLine();
                    }
                }
                break;
            case AJAKULU:
                try (FileWriter stat2 = new FileWriter(file, true)) {
                    try (BufferedWriter statAK = new BufferedWriter(stat2)) {
                        statAK.write(String.valueOf(arvutaAeg(vaheajad)));
                        vaheajad.clear();
                        statAK.newLine();
                    }
                }
                break;
        }
    }

    public void loeStatistikat() throws IOException {
        File statistika = new File(file);
        List<Integer> vastused = new ArrayList<>();
        try (Scanner sc = new Scanner(statistika)) {
            double oiged = 0;
            while (sc.hasNextLine()) {
                int x = Integer.parseInt(sc.nextLine());
                if (x == 1) {
                    oiged++;
                }
                vastused.add(x);
            }
            System.out.println("Kõigist vastuste arvust " + vastused.size() + " on õigesti vastatud " + (int) oiged + " korda" +
                    " ja valesti " + (int) (vastused.size() - oiged) + " korda.");
            if (vastused.size() > 0)
                System.out.println("Oled seni vastanud " + (Math.round(oiged / vastused.size() * 100)) + "%-lise täpsusega.");
            System.out.println();
        }
    }

    public void loeAjakuluStatistikat() throws IOException {
        DecimalFormat df = new DecimalFormat("0.##");
        File statistika = new File(file);
        double vastused = 0;
        try (Scanner sc = new Scanner(statistika)) {
            int n = 0;
            while (sc.hasNextLine()) {
                vastused += Double.parseDouble(sc.nextLine());
                n++;
            }
            System.out.println("Kokku on ühe tehte arvutamiseks kulunud aega keskmiselt " + df.format(vastused / n / 1000) + " sekundit.");
        }
    }


    public void liidaAeg(long vaheaeg) {
        vaheajad.add(vaheaeg);
    }

    private long arvutaAeg(List<Long> vaheajad) {
        long koguAeg = 0;
        if (vaheajad.size() >= 2) {
            for (Long aLong : vaheajad) {
                koguAeg += aLong;
            }
        } else {
            koguAeg = vaheajad.get(0);
        }
        return koguAeg;
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
