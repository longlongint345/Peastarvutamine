import java.text.DecimalFormat;

public class Tehe {
    private Tehtetuup tuup;
    private int raskusaste;
    private double arv1, arv2, arv3, arv4, arvJagamine1;
    private double vastus;
    private String tehe;
    private String jagamiseVastus;

    public Tehe(Tehtetuup tuup) {
        this.tuup = tuup;
    }

    public void genTehe() {
        // Meetod genereerib tehtetüübile vastava String-kujul tehte ja salvestab selle vastuse

        int alampiir = -(int) Math.pow(10, raskusaste) + 1;
        int ulempiir = (int) Math.pow(10, raskusaste) - 1;
        int jagamisAlam = -(int) Math.pow(10, loos(raskusaste-1)) + 1;
        int jagamisUlem = (int) Math.pow(10, raskusaste+1) - 1;

        this.arv1 = suvalineInt(alampiir, ulempiir);
        this.arv2 = suvalineInt(alampiir, ulempiir);
        this.arv3 = suvalineInt(jagamisAlam, ulempiir);
        this.arv4 = suvalineInt(alampiir, jagamisUlem);

        this.arvJagamine1 = this.arv1 * this.arv2;
        DecimalFormat df = new DecimalFormat("0.#####");

        switch (this.tuup) {
            case JAGAMINE:
                if (arv1 != 0 && arv2 != 0) {
                    double a = Math.random();
                    if (raskusaste == 1) {
                        this.arvJagamine1 /= 10;
                        if (a < 1.0 / 3.0) {
                            int loos = loos(2);
                            this.vastus = this.arv1/10/loos;
                            System.out.println("arvjagamine1 / arv 1/10 = "+vastus);
                            if (arv2 < 0)
                                this.tehe = String.format("%s/(%s)=", df.format(arvJagamine1),
                                        df.format(arv2*loos));
                            else this.tehe = String.format("%s/%s=", df.format(arvJagamine1),
                                    df.format(arv2*loos));
                            break;
                        } else if (a >= 1.0 / 3.0 && a < 2.0 / 3.0) {
                            this.vastus = this.arv1;
                            System.out.println("arvjagamine1 / arv 2/10 = "+vastus);
                            if (arv2 < 0)
                                this.tehe = String.format("%s/(%s)=", df.format(arvJagamine1),
                                        df.format(arv2 / 10));
                            else this.tehe = String.format("%s/%s=", df.format(arvJagamine1),
                                    df.format(arv2 / 10));
                            break;
                        } else if (a >= 2.0 / 3.0) {
                            this.vastus = this.arv2;
                            System.out.println(vastus);
                            this.arvJagamine1 *= 10;
                            if (arv1 < 0)
                                this.tehe = String.format("%s/(%s)=", df.format(arvJagamine1),
                                        df.format(arv1));
                            else this.tehe = String.format("%s/%s=", df.format(arvJagamine1),
                                    df.format(arv1));
                            break;
                        }
                    } else {
                        if (a < 0.01) {
                            int jagatav = (int) this.arv2 / 10;
                            if (jagatav == 0) jagatav = (int) (Math.random() * 10 + 1);
                            this.vastus = this.arv1;
                            if (jagatav < 0) this.tehe = String.format("%.0f/(%d)=", (arv1 * jagatav), jagatav);
                            else this.tehe = String.format("%.0f/%d=", (arv1 * jagatav), jagatav);
                            break;
                        } else {
                            double loos = 10;
                            if (raskusaste > 2) {
                                loos = Math.pow(10, loos(2));
                            }
                            if (this.arv3 == 0) this.arv3 = (int) (Math.random() * Math.pow(10, raskusaste-1) + 1);
                            this.vastus = (this.arv4 * this.arv3) / (this.arv3 * loos);
                            if (arv3 < 0) this.tehe = String.format("%s/(%s)=", df.format(this.arv4 * this.arv3 / (loos)), df.format(this.arv3));
                            else this.tehe = String.format("%s/%s=", df.format(this.arv4 * this.arv3 / loos), df.format(this.arv3));
                            break;
                        }
                    }
                } else {
                    this.vastus = 0;
                    int jagatav = (int) (Math.random() * Math.pow(10, raskusaste) + 1);
                    this.tehe = String.format("%s/%s=", 0,
                            df.format(jagatav));
                    break;
                }

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
                System.out.println(vastus);
                if (arv2 < 0) this.tehe = String.format("%.0f*(%.0f)=", arv1, arv2);
                else this.tehe = String.format("%.0f*%.0f=", arv1, arv2);
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

    private static int loos(int ulatus) {
        return (int) (Math.round(Math.random() * (ulatus - 1) + 1));
    }

    public String getTehe() {
        return tehe;
    }

    public double getVastus(){
        return vastus;
    }
}

enum Tehtetuup {
    LIITMINE, LAHUTAMINE, KORRUTAMINE, JAGAMINE
}
