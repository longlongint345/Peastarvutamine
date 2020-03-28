public class Lahutamistehe extends Tehe {
    @Override
    public void genTehe() {

        int alampiir = -(int) Math.pow(10, raskusaste) + 1;
        int ulempiir = (int) Math.pow(10, raskusaste) - 1;
        double arv1 = suvalineInt(alampiir, ulempiir);
        double arv2 = suvalineInt(alampiir, ulempiir);

        this.vastus = arv1 - arv2;
        if (arv2 < 0) this.tehe = String.format("%.0f-(%.0f)=", arv1, arv2);
        else this.tehe = String.format("%.0f-%.0f=", arv1, arv2);
    }
}
