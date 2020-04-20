package GUI;

import java.util.Random;

public abstract class Tehe {
    int raskusaste;
    double vastus;
    String tehe;
    boolean raskem;

    public void genTehe(){
        if (raskem) genRaskem();
        else genKergem();
    }

    public boolean kontrolliVastus(double pakkumine) {
        return Math.abs(pakkumine - vastus) < 0.000001;
    }

    public void setRaskusaste(int raskusaste) {
        this.raskusaste = raskusaste;
    }

    public String getTehe() {
        return tehe;
    }

    public double getVastus() {
        return vastus;
    }

    public static int suvalineInt(int alampiir, int ulempiir) {
        Random random = new Random();
        return random.nextInt(ulempiir-alampiir+1)+alampiir;
    }

    public int getRaskusaste() {
        return raskusaste;
    }
    public boolean getRaskus(){
        return raskem;
    }
    public void setRaskem(boolean raskem){
        this.raskem = raskem;
    }
    abstract void genKergem();
    abstract void genRaskem();
}
