package GUI;

public class Lahutamistehe extends Tehe {

    @Override
    void genKergem() {
        int alampiir = -(int) Math.pow(10, raskusaste) + 1;
        int ulempiir = (int) Math.pow(10, raskusaste) - 1;
        double arv1 = suvalineInt(alampiir, ulempiir);
        double arv2 = suvalineInt(alampiir, ulempiir);

        this.vastus = arv1 - arv2;
        if (arv2 < 0) this.tehe = String.format("%.0f-(%.0f)=", arv1, arv2);
        else this.tehe = String.format("%.0f-%.0f=", arv1, arv2);
    }

    @Override
    void genRaskem() {
        int komakohtadeArv1 = Main.loos(3)-1;
        int komakohtadeArv2 = Main.loos(3)-1;
        int alampiir1 = -(int) Math.pow(10, raskusaste+komakohtadeArv1) + 1;
        int ulempiir1 = (int) Math.pow(10, raskusaste+komakohtadeArv1) - 1;
        int alampiir2 = -(int) Math.pow(10, raskusaste+komakohtadeArv2) + 1;
        int ulempiir2 = (int) Math.pow(10, raskusaste+komakohtadeArv2) - 1;

        double arv1 = suvalineInt(alampiir1, ulempiir1)/ Math.pow(10, komakohtadeArv1);
        double arv2 = suvalineInt(alampiir2, ulempiir2)/ Math.pow(10, komakohtadeArv2);
        this.vastus = arv1 - arv2;

        if (arv2 < 0) this.tehe = String.format("%."+komakohtadeArv1+"f-(%."+komakohtadeArv2+"f)=", arv1, arv2);
        else if (arv2 >=0) this.tehe = String.format("%."+komakohtadeArv1+"f-%."+komakohtadeArv2+"f=", arv1, arv2);
    }
}
