public abstract class Tehe {
    int raskusaste;
    double vastus;
    String tehe;

    public abstract void genTehe();

    public boolean kontrolliVastus(double pakkumine) {
        return pakkumine == vastus;
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
        return (int) (Math.round(Math.random() * (ulempiir - alampiir) + alampiir));
    }
}
