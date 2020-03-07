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

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Tehe liitmine = new Tehe(Tehtetuup.LIITMINE);
        Tehe lahutamine = new Tehe(Tehtetuup.LAHUTAMINE);
        Tehe korrutamine = new Tehe(Tehtetuup.KORRUTAMINE);
        Tehe jagamine = new Tehe(Tehtetuup.JAGAMINE);

        Scanner sc = new Scanner(System.in);

        //main loop
        while (true) {

            int raskusaste, suvaline = 1;
            char reziim;
            boolean vastusOige = true;
            System.out.print("Sisestage soovitud raskusaste (0 - välju programmist, 1 - ühekohalised, 2 - kahekohalisd, jne (max 6): ");
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
                    if (reziim == 'l') {
                        if (vastusOige) suvaline = loos(2); // valib kumb tehe kuvada
                        if (suvaline == 1) vastusOige = teostaTehe(liitmine, vastusOige);
                        else vastusOige = teostaTehe(lahutamine, vastusOige);

                    } else if (reziim == 'j') {
                        if (vastusOige) suvaline = loos(4);
                        switch (suvaline) {
                            case 1:
                                vastusOige = teostaTehe(liitmine, vastusOige);
                                break;
                            case 2:
                                vastusOige = teostaTehe(lahutamine, vastusOige);
                                break;
                            case 3:
                                vastusOige = teostaTehe(korrutamine, vastusOige);
                                break;
                            case 4:
                                vastusOige = teostaTehe(jagamine, vastusOige);
                                break;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println();
                    break;
                }
            }

        }
    }

    private static boolean teostaTehe(Tehe tehe, boolean korrektne) throws InputMismatchException {
        // Antud tehteobjekt ja boolean, mis näitab, kas eelmine teostatud tehe oli õigesti vastatud
        // Tulemus: Kuvab kasutajale vastamiseks tehte, arvestades eelmise tulemust
        // Tagastab true kui kasutaja pakutav vastus on õige
        // Kui eelmine tehe oli vale, kuvab selle uuesti
        // todo Lisada võimalus õige vastus kuvada, kui kasutaja seda välja ei mõtle
        Scanner sc = new Scanner(System.in);
        if (korrektne) tehe.genTehe();
        System.out.print(tehe.getTehe());
        if (!tehe.kontrolliVastus(sc.nextDouble())) {
            System.out.println("Vale vastus!");
            return false;
        } else return true;
    }

    private static int loos(int ulatus) {
        // Antud: int ulatus (see näitab mitu võimalikku loosi tulemust saab olla)
        // Tagastab täisarvu lõigust [1, ulatus]
        return (int) (Math.round(Math.random() * (ulatus - 1) + 1));
    }
}
