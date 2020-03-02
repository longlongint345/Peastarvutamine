public class Tehe {
    private Tehtetuup tuup;
    private int raskusaste;
    private int arv1, arv2;
    private double vastus;
    private String tehe;

    public Tehe(Tehtetuup tuup){
        this.tuup = tuup;
    }

    public void genTehe(){
        switch (this.tuup){
            case LIITMINE:
                break;
            case LAHUTAMINE:
                break;
            case KORRUTAMINE:
                break;
            case JAGAMINE:
                break;
            default:
                System.out.println("Midagi on valesti");
        }
    }
    public boolean kontrolliVastus(int pakkumine){
        return pakkumine == (int)this.vastus;
    }
    public boolean kontrolliVastus(double pakkumine){
        return pakkumine == vastus;
    }
    public void setRaskusaste(int raskusaste) {
        this.raskusaste = raskusaste;
    }

    public Tehtetuup getTuup() {
        return tuup;
    }

    public int getRaskusaste() {
        return raskusaste;
    }

    public int getArv1() {
        return arv1;
    }

    public int getArv2() {
        return arv2;
    }

    public double getVastus() {
        return vastus;
    }

    public String getTehe() {
        return tehe;
    }
}
enum Tehtetuup{
    LIITMINE, LAHUTAMINE, KORRUTAMINE, JAGAMINE
}
