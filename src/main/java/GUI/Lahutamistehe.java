package GUI;

public class Lahutamistehe extends Tehe {

    @Override
    void genKergem() {
        int raskusaste = this.getRaskusaste();
        boolean negatiivsus = this.kasNegatiivsedArvud();
        int alampiir = 0;
        if (negatiivsus){
            alampiir = -(int) Math.pow(10, raskusaste) + 1;
        }
        int ulempiir = (int) Math.pow(10, raskusaste) - 1;

        double arv1 = suvalineInt(alampiir, ulempiir);
        double arv2 = suvalineInt(alampiir, ulempiir);
        // et vastuseks ei tuleks negatiivset arvu, kui on valitud ainult positiivsed
        if (!negatiivsus && arv1 < arv2){
            double tmp = arv1;
            arv1 = arv2;
            arv2 = tmp;
        }

        this.setVastus(arv1 - arv2);
        if (arv2 < 0) this.setTehe(String.format("%.0f-(%.0f)=", arv1, arv2));
        else this.setTehe(String.format("%.0f-%.0f=", arv1, arv2));
    }

    @Override
    void genRaskem() {
        int raskusaste = this.getRaskusaste();
        boolean negatiivsus = kasNegatiivsedArvud();

        int komakohtadeArv1 = Main.loos(3) - 1;
        int komakohtadeArv2 = Main.loos(3) - 1;
        int alampiir1 = 0;
        int alampiir2 = 0;
        if (negatiivsus) {
            alampiir1 = -(int) Math.pow(10, raskusaste + komakohtadeArv1) + 1;
            alampiir2 = -(int) Math.pow(10, raskusaste + komakohtadeArv2) + 1;
        }
        int ulempiir1 = (int) Math.pow(10, raskusaste + komakohtadeArv1) - 1;
        int ulempiir2 = (int) Math.pow(10, raskusaste + komakohtadeArv2) - 1;

        double arv1 = suvalineInt(alampiir1, ulempiir1)/ Math.pow(10, komakohtadeArv1);
        double arv2 = suvalineInt(alampiir2, ulempiir2)/ Math.pow(10, komakohtadeArv2);
        this.setVastus(arv1 - arv2);

        if (arv2 < 0) this.setTehe(String.format("%."+komakohtadeArv1+"f-(%."+komakohtadeArv2+"f)=", arv1, arv2));
        else if (arv2 >=0) this.setTehe(String.format("%."+komakohtadeArv1+"f-%."+komakohtadeArv2+"f=", arv1, arv2));
    }
}
