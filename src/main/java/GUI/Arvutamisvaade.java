package GUI;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

enum Tehetetuup{
    LIITLAHUTAMINE,
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
    private Stat HV = new Stat(statistikaTuup.HEADJAVEAD, "headjavead.txt");
    private Stat AK = new Stat(statistikaTuup.AJAKULU, "ajakulu.txt");
    private boolean teheKuvatud;

    private Liitmistehe liitmistehe = new Liitmistehe();
    private Lahutamistehe lahutamine = new Lahutamistehe();
    private Korrutamistehe korrutamine = new Korrutamistehe();
    private Jagamistehe jagamine = new Jagamistehe();

    public Arvutamisvaade(BorderPane borderPane) throws IOException {
        teheKuvatud = false;
        this.borderPane = borderPane;
        Stat.failid();
    }

    public void setKasKumnendmurrud(boolean kasKumnendmurrud) {
        liitmistehe.setRaskem(kasKumnendmurrud);
        lahutamine.setRaskem(kasKumnendmurrud);
        korrutamine.setRaskem(kasKumnendmurrud);
        jagamine.setRaskem(kasKumnendmurrud);
    }

    public void setNegatiivsedArvud(boolean kasNeg){
        liitmistehe.setNegatiivsedArvud(kasNeg);
        lahutamine.setNegatiivsedArvud(kasNeg);
        korrutamine.setNegatiivsedArvud(kasNeg);
        jagamine.setNegatiivsedArvud(kasNeg);
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

    private Tehe annaTehe(Tehetetuup tehetetuup) {
    // meetod tagastab režiimile vastava tehte
        int suvaline;
        switch (tehetetuup) {
            case LIITMINE:
                return this.liitmistehe;
            case LAHUTAMINE:
                return this.lahutamine;
            case KORRUTAMINE:
                return this.korrutamine;
            case JAGAMINE:
                return this.jagamine;
            case LIITLAHUTAMINE:
                suvaline = Main.loos(2);
                if (suvaline == 1) {
                    return this.liitmistehe;
                } else {
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

    public void kuva() {
        // meetod kuvab arvutamisvaate
        GridPane arvutamisvaadeGridPane = new GridPane();
        assert tehe != null;
        tehe.genTehe();
        AtomicLong start = new AtomicLong(System.currentTimeMillis());
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

        for (int i = 0; i <= 9; i++) {
            numbrinupud[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            numbrinupud[i].setFont(nupufont);
            int finalI = i;
            numbrinupud[i].setOnMouseClicked(e -> {
                vastatav += Integer.toString(finalI);
                teheLabel.setText(teheLabel.getText() + finalI);
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
            teheLabel.setText(tehe.getTehe() + vastatav);
        });
        nuppTagasi.setOnMouseClicked(e -> {
            if (!vastatav.equals("")) {
                vastatav = vastatav.substring(0, vastatav.length() - 1);
                teheLabel.setText(tehe.getTehe() + vastatav);
            }
        });
        nuppKoma.setOnMouseClicked(e -> {
            if (!vastatav.equals("") && !vastatav.contains(",") && !vastatav.equals("-")) {
                vastatav += ",";
                teheLabel.setText(tehe.getTehe() + vastatav);
            }
        });
        nuppVasta.setOnMouseClicked(e -> {
            vasta(start, tehtevaliHbox, teheLabel);
        });

        ColumnConstraints tulpConstraint = new ColumnConstraints();
        RowConstraints ridaConstraint = new RowConstraints();
        ridaConstraint.setPercentHeight(25);
        tulpConstraint.setPercentWidth(20);
        for (int i = 0; i < 4; i++) {
            arvutamisvaadeGridPane.getColumnConstraints().add(tulpConstraint);
            arvutamisvaadeGridPane.getRowConstraints().add(ridaConstraint);
        }
        arvutamisvaadeGridPane.getColumnConstraints().add(tulpConstraint);

        arvutamisvaadeGridPane.add(tehtevaliHbox, 0, 0, 5, 1);
        arvutamisvaadeGridPane.add(numbrinupud[7], 0, 1, 1, 1);
        arvutamisvaadeGridPane.add(numbrinupud[8], 1, 1, 1, 1);
        arvutamisvaadeGridPane.add(numbrinupud[9], 2, 1, 1, 1);
        arvutamisvaadeGridPane.add(numbrinupud[4], 0, 2, 1, 1);
        arvutamisvaadeGridPane.add(numbrinupud[5], 1, 2, 1, 1);
        arvutamisvaadeGridPane.add(numbrinupud[6], 2, 2, 1, 1);
        arvutamisvaadeGridPane.add(numbrinupud[1], 0, 3, 1, 1);
        arvutamisvaadeGridPane.add(numbrinupud[2], 1, 3, 1, 1);
        arvutamisvaadeGridPane.add(numbrinupud[3], 2, 3, 1, 1);
        arvutamisvaadeGridPane.add(numbrinupud[0], 3, 3, 1, 1);
        arvutamisvaadeGridPane.add(nuppKoma, 4, 3, 1, 1);
        arvutamisvaadeGridPane.add(nuppNeg, 3, 2, 1, 1);
        arvutamisvaadeGridPane.add(nuppTagasi, 4, 2, 1, 1);
        arvutamisvaadeGridPane.add(nuppVasta, 3, 1, 2, 1);

        arvutamisvaadeGridPane.setAlignment(Pos.CENTER);

        // võimalus sisestadada klaviatuurilt
        arvutamisvaadeGridPane.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    String tipitud = "";
                    if (event.getCode() == KeyCode.DIGIT1 || event.getCode() == KeyCode.NUMPAD1) tipitud = "1";
                    else if (event.getCode() == KeyCode.DIGIT2 || event.getCode() == KeyCode.NUMPAD2) tipitud = "2";
                    else if (event.getCode() == KeyCode.DIGIT3 || event.getCode() == KeyCode.NUMPAD3) tipitud = "3";
                    else if (event.getCode() == KeyCode.DIGIT4 || event.getCode() == KeyCode.NUMPAD4) tipitud = "4";
                    else if (event.getCode() == KeyCode.DIGIT5 || event.getCode() == KeyCode.NUMPAD5) tipitud = "5";
                    else if (event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.NUMPAD6) tipitud = "6";
                    else if (event.getCode() == KeyCode.DIGIT7 || event.getCode() == KeyCode.NUMPAD7) tipitud = "7";
                    else if (event.getCode() == KeyCode.DIGIT8 || event.getCode() == KeyCode.NUMPAD8) tipitud = "8";
                    else if (event.getCode() == KeyCode.DIGIT9 || event.getCode() == KeyCode.NUMPAD9) tipitud = "9";
                    else if (event.getCode() == KeyCode.DIGIT0 || event.getCode() == KeyCode.NUMPAD0) tipitud = "0";
                    if (event.getCode().isDigitKey()) {
                        vastatav += tipitud;
                        teheLabel.setText(teheLabel.getText() + tipitud);
                    } else if (event.getCode() == KeyCode.ENTER) {
                        vasta(start, tehtevaliHbox, teheLabel);
                    } else if (event.getCode() == KeyCode.BACK_SPACE) {
                        if (!vastatav.equals("")) {
                            vastatav = vastatav.substring(0, vastatav.length() - 1);
                            teheLabel.setText(tehe.getTehe() + vastatav);
                        }
                    }else if(event.getCode() == KeyCode.MINUS || event.getCode() == KeyCode.SUBTRACT || event.getCode() == KeyCode.ADD){
                        if (vastatav.equals("")) vastatav = "-";
                        else if (vastatav.charAt(0) != '-') vastatav = "-" + vastatav;
                        else vastatav = vastatav.substring(1);
                        teheLabel.setText(tehe.getTehe() + vastatav);
                    }else if (event.getCode() == KeyCode.COMMA || event.getCode() == KeyCode.DECIMAL){
                        if (!vastatav.equals("") && !vastatav.contains(",") && !vastatav.equals("-")) {
                            vastatav += ",";
                            teheLabel.setText(tehe.getTehe() + vastatav);
                        }
                    }else if (event.getCode() == KeyCode.SPACE){
                        if (tehe.getRaskus()) teheLabel.setText(String.format(tehe.getTehe() + "%.4f", tehe.getVastus()));
                        else teheLabel.setText(String.format(tehe.getTehe() + "%.0f", tehe.getVastus()));
                        vastatav = Double.toString(tehe.getVastus());
                        teheKuvatud = true;
                    }
                }
            }
        });

        borderPane.setCenter(arvutamisvaadeGridPane);
    }
    // koodi kokkuhoid, meetod kasutatud nii nupule vajutades, kui klaviatuuril tippides
    // meetod sisaldab koodi, mis käivitub kui kasutaja otsustab tehet vastata
    public void vasta(AtomicLong start, HBox tehtevaliHbox, Label teheLabel){
        if (!vastatav.equals("") && !vastatav.equals("-")) {
            try {
                double pakkumine = Double.parseDouble(vastatav.replace(",", "."));
                if (tehe.kontrolliVastus(pakkumine)) {
                    AK.setAeg(System.currentTimeMillis() - start.get());
                    AK.lisaAndmed(!teheKuvatud);
                    HV.lisaAndmed(!teheKuvatud);
                    start.set(System.currentTimeMillis());
                    tehtevaliHbox.setStyle("-fx-background-color: #F4F4F4;");
                    tehe = annaTehe(tehetetuup);
                    assert tehe != null;
                    tehe.genTehe();
                } else {
                    tehtevaliHbox.setStyle("-fx-background-color: #FF0000;");
                    AK.setAeg(System.currentTimeMillis() - start.get());
                    AK.lisaAndmed(false);
                    HV.lisaAndmed(false);
                    start.set(System.currentTimeMillis());
                }
                vastatav = "";
                teheLabel.setText(tehe.getTehe());

            } catch (IOException ex) {
                System.out.println("Andmefaili(de)ga juhtus tõrge.");
                try {
                    Stat.failid();
                } catch (IOException exc) {
                    System.out.println("Andmefaile ei saanud taasluua, programm sulgub.");
                    System.exit(1);
                }
            }
            teheKuvatud = false;
        }
    }
}

