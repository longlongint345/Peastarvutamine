public class Tehe {
    private Tehtetuup tuup;
    private int raskusaste;
    private double arv1, arv2;
    private double vastus;
    private String tehe;

    public Tehe(Tehtetuup tuup) {
        this.tuup = tuup;
    }

    public void genTehe() {
        // Meetod genereerib tehtetüübile vastava String-kujul tehte ja salvestab selle vastuse

        int alampiir = -(int) Math.pow(10, raskusaste) + 1;
        int ulempiir = (int) Math.pow(10, raskusaste) - 1;

        this.arv1 = suvalineInt(alampiir, ulempiir);
        this.arv2 = suvalineInt(alampiir, ulempiir);

        switch (this.tuup) {
            case LIITMINE:
                this.vastus = this.arv1 + this.arv2;
                if (arv2 < 0) this.tehe = String.format("%.0f+(%.0f)=", arv1, arv2);
                else this.tehe = String.format("%.0f+%.0f=", arv1, arv2);
                break;
            case LAHUTAMINE:
                this.vastus = this.arv1 - this.arv2;
                if (arv2 < 0) this.tehe = String.format("%.0f-(%.0f)=", arv1, arv2);
                else this.tehe = String.format("%.0f-%.0f=", arv1, arv2);
                break;
            case KORRUTAMINE:
                this.vastus = this.arv1 * this.arv2;
                if (arv2 < 0) this.tehe = String.format("%.0f*(%.0f)=", arv1, arv2);
                else this.tehe = String.format("%.0f*%.0f=", arv1, arv2);
                break;
            case JAGAMINE:
                // todo Jagamine. Tehe tuleb genererida nii, et ei tekiks sobimatuid kümnendmurde ja nulliga jagamist
                this.tehe = "1/1=";
                this.vastus = 1;
                break;
            default:
                System.out.println("Midagi on valesti");
        }
    }

    public boolean kontrolliVastus(double pakkumine) {
        return pakkumine == vastus;
    }

    public void setRaskusaste(int raskusaste) {
        this.raskusaste = raskusaste;
    }

    private static int suvalineInt(int alampiir, int ulempiir) {
        return (int) (Math.round(Math.random() * (ulempiir - alampiir) + alampiir));
    }

    public Tehtetuup getTuup() {
        return tuup;
    }

    public int getRaskusaste() {
        return raskusaste;
    }

    public double getArv1() {
        return arv1;
    }

    public double getArv2() {
        return arv2;
    }

    public double getVastus() {
        return vastus;
    }

    public String getTehe() {
        return tehe;
    }
}

enum Tehtetuup {
    LIITMINE, LAHUTAMINE, KORRUTAMINE, JAGAMINE
}
