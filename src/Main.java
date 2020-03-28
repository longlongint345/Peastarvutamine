import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // Vajalike objektide loomine ------------------------------------
        Liitmistehe liitmine = new Liitmistehe();
        Lahutamistehe lahutamine = new Lahutamistehe();
        Korrutamistehe korrutamine = new Korrutamistehe();
        Jagamistehe jagamine = new Jagamistehe();

        Stat.failid();

        Stat HeadjaveadStat = new Stat(statistikaTuup.HEADJAVEAD, "headjavead.txt");
        Stat AjakuluStat = new Stat(statistikaTuup.AJAKULU, "ajakulu.txt");

        Scanner sc = new Scanner(System.in);

        //------------------------------------------------------------------
        // Kasutajapoolne statistika vaatamine / kustutamine ---------------
        System.out.println("Kui soovite arvutustehteid, vajutage ENTER.\nStatistika kuvamiseks sisestage " + "\"stat" + "\". " +
                "\nAndmete kustutamiseks sisestage " + "\"del" + "\".");
        String kask = sc.nextLine();
        if (kask.equals("stat")) {
            HeadjaveadStat.loeStatistikat();
            AjakuluStat.loeAjakuluStatistikat();
        } else if (kask.equals("del")) {
            Stat.failidTuhjaks();
            System.out.println("Andmed on kustutatud.");
        }
        //-------------------------------------------------------------------
        // peatsükkel
        while (true) {

            // Sessiooni seadistus ------------------------------------------
            int raskusaste, suvaline = 1;
            char reziim;
            boolean vastusOige = true, uus_katse = false;
            try {
                System.out.print("Sisestage soovitud raskusaste (0 - välju programmist, 1 - ühekohalised, 2 - kahekohalised, jne (max 6): ");
                raskusaste = sc.nextInt();
                if (raskusaste == 0) break;
                liitmine.setRaskusaste(raskusaste);
                lahutamine.setRaskusaste(raskusaste);
                korrutamine.setRaskusaste(raskusaste);
                jagamine.setRaskusaste(raskusaste);
                System.out.print("Sisestage soovitud režiim (j - juhutehe, l - liitlahutamine): ");
                reziim = sc.next().charAt(0);
                if (!(reziim == 'j' || reziim == 'l')) throw new Exception();
                if (reziim == 'j') {
                    System.out.println("Kas soovite jagamises kümnendmurde j/e");
                    if (sc.next().equals("j")) jagamine.setRaskem(true);
                    else jagamine.setRaskem(false);
                }
            } catch (Exception e) {
                System.out.println("\nVigane sisend!\n");
                break;
            }
            //---------------------------------------------------------------
            // Arvutamine, statistika kogumine ------------------------------
            System.out.print("\nVäljumiseks sisestage \"e\"\n");
            while (true) {
                // todo Hetkel on tekkinud selline olukord, et kuvatava tehte tüüp ei muutu, kui kasutaja pole seda tüüpi tehet õigesti vastanud (loobumisel tuleb sama tüüpi uus tehe)
                // teine variant on otsustada, et äkki nii ongi parem ?
                try {
                    long ajatempelAlgus = System.currentTimeMillis();

                    if (vastusOige) {
                        if (reziim == 'l') suvaline = loos(2);
                        else suvaline = loos(4);
                    }
                    switch (suvaline) {
                        case 1:
                            vastusOige = teostaTehe(liitmine, vastusOige, uus_katse, HeadjaveadStat, AjakuluStat, ajatempelAlgus);
                            break;
                        case 2:
                            vastusOige = teostaTehe(lahutamine, vastusOige, uus_katse, HeadjaveadStat, AjakuluStat, ajatempelAlgus);
                            break;
                        case 3:
                            vastusOige = teostaTehe(korrutamine, vastusOige, uus_katse, HeadjaveadStat, AjakuluStat, ajatempelAlgus);
                            break;
                        case 4:
                            vastusOige = teostaTehe(jagamine, vastusOige, uus_katse, HeadjaveadStat, AjakuluStat, ajatempelAlgus);
                            break;
                    }

                    if (!vastusOige) {
                        System.out.println("Uuesti proovimiseks sisesta 1, loobumiseks 0");
                        switch (sc.next()) {
                            case "1":
                                uus_katse = true;
                                break;
                            case "0":
                                uus_katse = false;
                                break;
                            default:
                                throw new InputMismatchException();
                        }
                    }

                } catch (InputMismatchException e) {
                    System.out.println();
                    break;
                }
            }
            //--------------------------------------------------------
        }
    }

    private static boolean teostaTehe(Tehe tehe, boolean eelmineKorrektne, boolean uus_katse, Stat headjaveadStat, Stat ajakuluStat, long startAeg) throws InputMismatchException, IOException {
        // Antud tehteobjekt ja boolean, mis näitab, kas eelmine teostatud tehe oli õigesti vastatud, + statistika kogumiseks vajalikud muutujad
        // boolean uus_katse, mis näitab kas kasutaja soovib uut katset
        // Tulemus: Kuvab kasutajale vastamiseks tehte, arvestades eelmise tulemust ning kogub selle kohta statistikat
        // Tagastab true kui kasutaja pakutav vastus on õige
        // Kui eelmine tehe oli vale, kuvab selle uuesti

        Scanner sc = new Scanner(System.in);
        if (eelmineKorrektne) { // kui eelnenud vastus oli korrektne
            tehe.genTehe();
        } else if (!uus_katse) { // kui vastus oli vale ja kasutaja ei soovinud uut katset
            System.out.println("Õige vastus oleks olnud: " + tehe.getVastus());
            tehe.genTehe();
        }
        System.out.print(tehe.getTehe());
        // todo Kui kasutaja sisestab koma asemel punkti, siis programm väljub arvutamisest
        if (!tehe.kontrolliVastus(sc.nextDouble())) { // vastuse kontrollimine
            long lopp = System.currentTimeMillis() - startAeg;
            headjaveadStat.setToevaartus(0);
            headjaveadStat.lisaAndmed();
            ajakuluStat.liidaAeg(lopp);
            System.out.println("Vale vastus!");
            return false;
        } else {
            long lopp = System.currentTimeMillis() - startAeg;
            headjaveadStat.setToevaartus(1);
            headjaveadStat.lisaAndmed();
            ajakuluStat.liidaAeg(lopp);
            ajakuluStat.lisaAndmed();
            return true;
        }
    }

    public static int loos(int ulatus) {
        // Antud: int ulatus (see näitab mitu võimalikku loosi tulemust saab olla)
        // Tagastab täisarvu lõigust [1, ulatus]
        return (int) (Math.round(Math.random() * (ulatus - 1) + 1));
    }
}
