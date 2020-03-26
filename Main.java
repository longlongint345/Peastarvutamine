/*
Programmi struktuur

Pakun välja idee programmi struktuurist:

class Tehe
    isendiväljad:
        tüüp (liitmine, lahutamine, korrutamine, ...)
        int raskusaste (max mitemkohalised saavad arvud olla)
        int arv1, arv2
        double vastus
        String tehe
    meetodid:
        void genTehe()
        boolean kontrolliVastus()
        getterid (kõigile isendiväljadele)
        setter (raskusaste) // et saaks programmi käigus vastavalt kasutaja soovile muuta
        konstruktor (seab tüübi)
        ...

class Stat //Statistikaga seotu
    // idee on selles, et eri tüüpi andmete salvestamiseks ja lugemiseks võiks luua erinevad objektid
    // nt arvutamisele kulunud aja salvestamine üks objekt, vigade/õigete loendus teine
    // aga kõik toimub samade meetodite abil nt objekt.lisaandmeid(double kogus), kirjutaFaili()

class Main
    main meetod
        suhtlus kasutajaga
        main loop
*/

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        Tehe liitmine = new Tehe(Tehtetuup.LIITMINE);
        Tehe lahutamine = new Tehe(Tehtetuup.LAHUTAMINE);
        Tehe korrutamine = new Tehe(Tehtetuup.KORRUTAMINE);
        Tehe jagamine = new Tehe(Tehtetuup.JAGAMINE);

        Stat.failid();

        Stat HV = new Stat(statistikaTuup.HEADJAVEAD, "headjavead.txt");
        Stat AK = new Stat(statistikaTuup.AJAKULU, "ajakulu.txt");

        Scanner sc = new Scanner(System.in);


        //main loop
        System.out.println("Kui soovite arvutustehteid, vajutage ENTER.\nStatistika kuvamiseks sisestage "+"\"stat"+"\". "+
                "\nAndmete kustutamiseks sisestage "+"\"del"+"\".");
        String käsk = sc.nextLine();
        if (käsk.equals("stat")) {
            HV.loeStatistikat();
            AK.loeAjakuluStatistikat();
        } else if (käsk.equals("del")) {
            Stat.failidTühjaks();
            System.out.println("Andmed on kustutatud.");
        }
        while (true) {

            int raskusaste, suvaline = 1;
            char reziim;
            boolean vastusOige = true, uus_katse = false;
            System.out.print("Sisestage soovitud raskusaste (0 - välju programmist, 1 - ühekohalised, 2 - kahekohalised, jne (max 6): ");
            raskusaste = sc.nextInt();
            if (raskusaste == 0) break;
            liitmine.setRaskusaste(raskusaste);
            lahutamine.setRaskusaste(raskusaste);
            korrutamine.setRaskusaste(raskusaste);
            jagamine.setRaskusaste(raskusaste);
            System.out.print("Sisestage soovitud režiim (j - juhutehe, l - liitlahutamine): ");
            reziim = sc.next().charAt(0);
            System.out.print("\nVäljumiseks sisestage \"e\"\n");

            while (true) {
                try {
                    long start = System.currentTimeMillis();
                    if (reziim == 'l') {
                        if (vastusOige) suvaline = loos(2); // valib kumb tehe kuvada
                        if (suvaline == 1) {
                            vastusOige = teostaTehe(liitmine, vastusOige, uus_katse, HV, AK, start);
                        } else {
                            vastusOige = teostaTehe(lahutamine, vastusOige, uus_katse, HV, AK, start);
                        }

                    } else if (reziim == 'j') {
                        if (vastusOige) suvaline = loos(4);
                        switch (suvaline) {
                            case 1:
                                vastusOige = teostaTehe(liitmine, vastusOige, uus_katse, HV, AK, start);
                                break;
                            case 2:
                                vastusOige = teostaTehe(lahutamine, vastusOige, uus_katse, HV, AK, start);
                                break;
                            case 3:
                                vastusOige = teostaTehe(korrutamine, vastusOige, uus_katse, HV, AK, start);
                                break;
                            case 4:
                                vastusOige = teostaTehe(jagamine, vastusOige, uus_katse, HV, AK, start);;
                                break;
                        }
                    }

                    if (!vastusOige) {
                        System.out.println("Uuesti proovimiseks sisesta 1, loobumiseks 0");
                        if (sc.nextInt() == 0) {
                            uus_katse = false;
                        } else {
                            uus_katse = true;
                        }
                    }

                } catch (InputMismatchException | IOException e) {
                    System.out.println();
                    HV.close();
                    break;
                }
            }

        }
    }

    private static boolean teostaTehe(Tehe tehe, boolean korrektne, boolean uus_katse, Stat HV, Stat AK, long start) throws InputMismatchException, IOException {
        // Antud tehteobjekt ja boolean, mis näitab, kas eelmine teostatud tehe oli õigesti vastatud,
        // boolean uus_katse, mis näitab kas kasutaja soovib uut katset
        // Tulemus: Kuvab kasutajale vastamiseks tehte, arvestades eelmise tulemust
        // Tagastab true kui kasutaja pakutav vastus on õige
        // Kui eelmine tehe oli vale, kuvab selle uuesti

        Scanner sc = new Scanner(System.in);
        if (korrektne) {
            tehe.genTehe();
        }
        else if (!uus_katse) {
            System.out.println("Õige vastus oleks olnud: " + tehe.getVastus());
            tehe.genTehe();
        }
        System.out.print(tehe.getTehe());
        if (!tehe.kontrolliVastus(sc.nextDouble())) {
            long lõpp = System.currentTimeMillis()-start;
            System.out.println("Vale vastus!");
            HV.setTõeväärtus(0);
            HV.lisaAndmed();
            AK.liidaAeg(lõpp);
            return false;
        } else {
            long lõpp = System.currentTimeMillis()-start;
            HV.setTõeväärtus(1);
            HV.lisaAndmed();
            AK.liidaAeg(lõpp);
            AK.lisaAndmed();
            return true;
        }
    }

    private static int loos(int ulatus) {
        // Antud: int ulatus (see näitab mitu võimalikku loosi tulemust saab olla)
        // Tagastab täisarvu lõigust [1, ulatus]
        return (int) (Math.round(Math.random() * (ulatus - 1) + 1));
    }
}
