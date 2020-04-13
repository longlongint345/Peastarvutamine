package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

enum Tehetetuup{
    LIITLAHTUAMINE,
    JUHUTEHE,
    LIITMINE,
    LAHUTAMINE,
    KORRUTAMINE,
    JAGAMINE
}

public class Arvutamisvaade {
    private BorderPane borderPane;
    private Tehetetuup tehetetuup;
    private Tehe tehe;
    private static String vastatav = "";
    //private boolean kasKumnendmurrud;
    Liitmistehe liitmistehe = new Liitmistehe();
    Lahutamistehe lahutamine = new Lahutamistehe();
    Korrutamistehe korrutamine = new Korrutamistehe();
    Jagamistehe jagamine = new Jagamistehe();

    public Arvutamisvaade(BorderPane borderPane) {
        this.borderPane = borderPane;

    }

    public void setKasKumnendmurrud(boolean kasKumnendmurrud) {
        //this.kasKumnendmurrud = kasKumnendmurrud; // tulevikus saaks laiendada kõikidele tehetele, mitte ainult jagamisele
        jagamine.setRaskem(kasKumnendmurrud);
    }

    public void setTehetetuup(Tehetetuup tehetetuup) {
        this.tehetetuup = tehetetuup;
        this.tehe = annaTehe(tehetetuup);
    }

    public void setRaskusaste(byte raskusaste) {
        liitmistehe.setRaskusaste(raskusaste);
        lahutamine.setRaskusaste(raskusaste);
        korrutamine.setRaskusaste(raskusaste);
        jagamine.setRaskusaste(raskusaste);
    }
    private Tehe annaTehe(Tehetetuup tehetetuup){

        int suvaline;
        switch (tehetetuup){
            case LIITMINE:
                return this.liitmistehe;
            case LAHUTAMINE:
                return this.lahutamine;
            case KORRUTAMINE:
                return this.korrutamine;
            case JAGAMINE:
                return this.jagamine;
            case LIITLAHTUAMINE:
                suvaline = Main.loos(2);
                if (suvaline == 1){
                    return this.liitmistehe;
                }else{
                    return this.lahutamine;
                }
            case JUHUTEHE:
                suvaline = Main.loos(4);
                if (suvaline == 1) return this.liitmistehe;
                if (suvaline == 2) return this.lahutamine;
                if (suvaline == 3) return this.korrutamine;
                if (suvaline == 4) return this.jagamine;

        }
        return null;
    }

    public void kuva(){
        // todo Võimalus klaviatuurilt sisestada?
        GridPane arvutamisvaadeGridPane = new GridPane();
        assert tehe != null;
        tehe.genTehe();
        Label teheLabel = new Label(tehe.getTehe());
        HBox tehtevaliHbox = new HBox();
        tehtevaliHbox.getChildren().add(teheLabel);
        tehtevaliHbox.setAlignment(Pos.CENTER);


        Button[] numbrinupud = new Button[]{
                new Button("0"),
                new Button("1"),
                new Button("2"),
                new Button("3"),
                new Button("4"),
                new Button("5"),
                new Button("6"),
                new Button("7"),
                new Button("8"),
                new Button("9")
        };

        Font nupufont = new Font("Verdana", 24);
        Font tehteFont = new Font("Verdana", 40);

        teheLabel.setFont(tehteFont);

        for (int i = 0; i <= 9; i++){
            numbrinupud[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            numbrinupud[i].setFont(nupufont);
            int finalI = i;
            numbrinupud[i].setOnMouseClicked(e -> {
                vastatav += Integer.toString(finalI);
                teheLabel.setText(teheLabel.getText()+finalI);
            });
        }

        Button nuppNeg = new Button("+/-");
        Button nuppVasta = new Button("Vasta");
        Button nuppKoma = new Button(",");
        Button nuppTagasi = new Button("<-");
        nuppTagasi.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        nuppNeg.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        nuppVasta.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        nuppKoma.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        nuppTagasi.setFont(nupufont);
        nuppNeg.setFont(nupufont);
        nuppKoma.setFont(nupufont);
        nuppVasta.setFont(nupufont);

        nuppNeg.setOnMouseClicked(event -> {
            if (vastatav.equals("")) vastatav = "-";
            else if (vastatav.charAt(0) != '-') vastatav = "-" + vastatav;
            else vastatav = vastatav.substring(1);
            teheLabel.setText(tehe.getTehe()+vastatav);
        });
        nuppTagasi.setOnMouseClicked(e -> {
            if (!vastatav.equals("")) {
                vastatav = vastatav.substring(0, vastatav.length()-1);
                teheLabel.setText(tehe.getTehe() + vastatav);
            }
        });
        nuppKoma.setOnMouseClicked(e -> {
            if(!vastatav.equals("") && !vastatav.contains(",") && !vastatav.equals("-")){
                vastatav += ",";
                teheLabel.setText(tehe.getTehe() + vastatav);
            }
        });
        nuppVasta.setOnMouseClicked(e -> {
            if (!vastatav.equals("") && !vastatav.equals("-")) {
                double pakkumine = Double.parseDouble(vastatav.replace(",", "."));
                if (tehe.kontrolliVastus(pakkumine)) {
                    tehtevaliHbox.setStyle("-fx-background-color: #F4F4F4;");
                    tehe = annaTehe(tehetetuup);
                    assert tehe != null;
                    tehe.genTehe();
                }else{
                    tehtevaliHbox.setStyle("-fx-background-color: #FF0000;");
                }
                vastatav = "";
                teheLabel.setText(tehe.getTehe());
            }
        });

        ColumnConstraints tulpConstraint = new ColumnConstraints();
        RowConstraints ridaConstraint = new RowConstraints();
        ridaConstraint.setPercentHeight(25);
        tulpConstraint.setPercentWidth(20);
        for (int i = 0; i < 4; i++){
            arvutamisvaadeGridPane.getColumnConstraints().add(tulpConstraint);
            arvutamisvaadeGridPane.getRowConstraints().add(ridaConstraint);
        }
        arvutamisvaadeGridPane.getColumnConstraints().add(tulpConstraint);

        arvutamisvaadeGridPane.add(tehtevaliHbox,0,0, 5, 1);
        arvutamisvaadeGridPane.add(numbrinupud[7], 0,1, 1,1);
        arvutamisvaadeGridPane.add(numbrinupud[8], 1,1, 1,1);
        arvutamisvaadeGridPane.add(numbrinupud[9], 2,1, 1,1);
        arvutamisvaadeGridPane.add(numbrinupud[4], 0,2, 1,1);
        arvutamisvaadeGridPane.add(numbrinupud[5], 1,2, 1,1);
        arvutamisvaadeGridPane.add(numbrinupud[6], 2,2, 1,1);
        arvutamisvaadeGridPane.add(numbrinupud[1], 0,3, 1,1);
        arvutamisvaadeGridPane.add(numbrinupud[2], 1,3, 1,1);
        arvutamisvaadeGridPane.add(numbrinupud[3], 2,3, 1,1);
        arvutamisvaadeGridPane.add(numbrinupud[0], 3,3, 1,1);
        arvutamisvaadeGridPane.add(nuppKoma, 4, 3, 1,1);
        arvutamisvaadeGridPane.add(nuppNeg, 3,2, 1,1);
        arvutamisvaadeGridPane.add(nuppTagasi, 4,2, 1,1);
        arvutamisvaadeGridPane.add(nuppVasta, 3,1, 2,1);

        arvutamisvaadeGridPane.setAlignment(Pos.CENTER);





        borderPane.setCenter(arvutamisvaadeGridPane);
    }
}
