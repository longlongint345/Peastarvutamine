/*
Programmi struktuur

Pakun välja esialgse idee programmi struktuurist

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

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Tehe liitmine = new Tehe(Tehtetuup.LIITMINE);
        Tehe lahutamine = new Tehe(Tehtetuup.LAHUTAMINE);
        Tehe korrutamine = new Tehe(Tehtetuup.KORRUTAMINE);
        Tehe jagamine = new Tehe(Tehtetuup.JAGAMINE);

        Scanner sc = new Scanner(System.in);

        //main loop
        while(true){





            if (true) {
                break;
            }
        }

    }
}
