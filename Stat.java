import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Stat {
    private statistikaTuup tuup;
    private String file;
    private int tõeväärtus;
    private List<Long> vaheajad = new ArrayList<>();

    public Stat(statistikaTuup tuup, String file){
       this.tuup = tuup;
       this.file = file;
    }

    public void setTõeväärtus(int tõeväärtus) {
        this.tõeväärtus = tõeväärtus;
    }

    public void lisaAndmed() throws IOException {
        switch (this.tuup) {
            case HEADJAVEAD:
                FileWriter stat1 = new FileWriter(file, true);
                BufferedWriter statHJV = new BufferedWriter(stat1);
                statHJV.write(String.valueOf(tõeväärtus));
                statHJV.newLine();
                statHJV.close();
                break;
            case AJAKULU:
                FileWriter stat2 = new FileWriter(file, true);
                BufferedWriter statAK = new BufferedWriter(stat2);
                statAK.write(String.valueOf(arvutaAeg(vaheajad)));
                vaheajad.clear();
                statAK.newLine();
                statAK.close();
                break;
        }
    }
    public void close() throws IOException {
        FileWriter stat1 = new FileWriter(file, true);
        BufferedWriter statHJV = new BufferedWriter(stat1);
        statHJV.close();
    }

    public void loeStatistikat() throws IOException {
        File statistika = new File(file);
        List<Integer> vastused = new ArrayList<>();
        Scanner sc = new Scanner(statistika);
        double õiged = 0;
        while (sc.hasNextLine()) {
            int x = Integer.parseInt(sc.nextLine());
            if (x == 1) {
                õiged++;
            }
            vastused.add(x);
        }
        System.out.println("Kõigist vastuste arvust "+vastused.size()+" on õigesti vastatud "+(int)õiged+" korda" +
                " ja valesti "+(int)(vastused.size()-õiged) + " korda.");
        if (vastused.size() > 0) System.out.println("Oled seni vastanud "+ (Math.round(õiged/vastused.size()*100))+"%-lise täpsusega.");
        System.out.println();
    }

    public void loeAjakuluStatistikat() throws IOException {
        DecimalFormat df = new DecimalFormat("0.##");
        File statistika = new File(file);
        double vastused = 0;
        Scanner sc = new Scanner(statistika);
        int n = 0;
        while (sc.hasNextLine()) {
            vastused += Double.parseDouble(sc.nextLine());
            n++;
        }
        System.out.println("Kokku on ühe tehte arvutamiseks kulunud aega keskmiselt "+df.format(vastused/n/1000)+" sekundit.");
    }


    public void liidaAeg(long vaheaeg){
        vaheajad.add(vaheaeg);
    }

    private long arvutaAeg(List<Long> vaheajad){
        long koguAeg = 0;
        if (vaheajad.size() >= 2) {
            for (int i = 0; i < vaheajad.size(); i++) {
                koguAeg += vaheajad.get(i);
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

    public static void failidTühjaks() throws IOException {
        FileOutputStream writer = new FileOutputStream("headjavead.txt");
        writer.write(("").getBytes());
        writer.close();
        FileOutputStream writer2 = new FileOutputStream("ajakulu.txt");
        writer2.write(("").getBytes());
        writer2.close();
    }
}
enum statistikaTuup {
    HEADJAVEAD, AJAKULU;
}
