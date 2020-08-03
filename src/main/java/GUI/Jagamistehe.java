package GUI;

import java.text.DecimalFormat;

public class Jagamistehe extends Tehe {

    @Override
    void genKergem() {
        // Meetod genereerib jagamistehte, milles on ainult täisarvud (kergem variant)
        int raskusaste = this.getRaskusaste();
        boolean negatiivsus = this.kasNegatiivsedArvud();
        int alampiir = 0;
        if (negatiivsus){
            alampiir = -(int) Math.pow(10, raskusaste) + 1;
        }
        int ulempiir = (int) Math.pow(10, raskusaste) - 1;
        int arv1 = suvalineInt(alampiir, ulempiir);
        int arv2 = suvalineInt(alampiir, ulempiir);

        // jagatav / arv2 = arv1

        if (arv2 == 0) arv2++; // nulliga jagamise vältimine
        this.setVastus(arv1);
        int jagatav = arv1 * arv2;
        if (arv2 < 0) this.setTehe(String.format("%d/(%d)=", jagatav, arv2));
        else this.setTehe(String.format("%d/%d=", jagatav, arv2));

    }
    @Override
    void genRaskem() {
        // Meetod genereerib jagamistehte, kus võib leiduda ka kümnendmurde (raskem variant)
        int raskusaste = this.getRaskusaste();
        boolean negatiivsus = this.kasNegatiivsedArvud();
        int alampiir = 0, jagamisAlam = 0;
        if (negatiivsus) {
            alampiir = -(int) Math.pow(10, raskusaste) + 1;
            jagamisAlam = -(int) Math.pow(10, Main.loos(raskusaste - 1)) + 1;
        }
        int ulempiir = (int) Math.pow(10, raskusaste) - 1;
        int jagamisUlem = (int) Math.pow(10, raskusaste + 1) - 1;

        double arv1 = suvalineInt(alampiir, ulempiir);
        double arv2 = suvalineInt(alampiir, ulempiir);
        double arv3 = suvalineInt(jagamisAlam, ulempiir);
        double arv4 = suvalineInt(alampiir, jagamisUlem);

        double arvJagamine1 = arv1 * arv2;
        DecimalFormat df = new DecimalFormat("0.#####");

        if (arv1 != 0 && arv2 != 0) {
            double a = Math.random();
            if (raskusaste == 1) {
                arvJagamine1 /= 10;
                if (a < 1.0 / 3.0) {
                    int loos = Main.loos(2);
                    this.setVastus(arv1 / 10 / loos);
                    if (arv2 < 0)
                        this.setTehe(String.format("%s/(%s)=", df.format(arvJagamine1),
                                df.format(arv2 * loos)));
                    else this.setTehe(String.format("%s/%s=", df.format(arvJagamine1),
                            df.format(arv2 * loos)));
                } else if (a >= 1.0 / 3.0 && a < 2.0 / 3.0) {
                    this.setVastus(arv1);
                    if (arv2 < 0)
                        this.setTehe(String.format("%s/(%s)=", df.format(arvJagamine1),
                                df.format(arv2 / 10)));
                    else this.setTehe(String.format("%s/%s=", df.format(arvJagamine1),
                            df.format(arv2 / 10)));
                } else if (a >= 2.0 / 3.0) {
                    this.setVastus(arv2);
                    arvJagamine1 *= 10;
                    if (arv1 < 0)
                        this.setTehe(String.format("%s/(%s)=", df.format(arvJagamine1),
                                df.format(arv1)));
                    else this.setTehe(String.format("%s/%s=", df.format(arvJagamine1),
                            df.format(arv1)));
                }
            } else {
                if (a < 0.01) {
                    int jagatav = (int) arv2 / 10;
                    if (jagatav == 0) jagatav = (int) (Math.random() * 10 + 1);
                    this.setVastus(arv1);
                    if (jagatav < 0) this.setTehe(String.format("%.0f/(%d)=", (arv1 * jagatav), jagatav));
                    else this.setTehe(String.format("%.0f/%d=", (arv1 * jagatav), jagatav));
                } else {
                    double loos = 10;
                    if (raskusaste > 2) {
                        loos = Math.pow(10, Main.loos(2));
                    }
                    if (arv3 == 0) arv3 = (int) (Math.random() * Math.pow(10, raskusaste - 1) + 1);
                    this.setVastus((arv4 * arv3) / (arv3 * loos));
                    if (arv3 < 0)
                        this.setTehe(String.format("%s/(%s)=", df.format(arv4 * arv3 / (loos)), df.format(arv3)));
                    else this.setTehe(String.format("%s/%s=", df.format(arv4 * arv3 / loos), df.format(arv3)));
                }
            }
        } else {
            this.setVastus(0);
            int jagatav = (int) (Math.random() * Math.pow(10, raskusaste) + 1);
            this.setTehe(String.format("%s/%s=", 0,
                    df.format(jagatav)));
        }
    }
}
