package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.IOException;
import java.text.DecimalFormat;

public class Statistikavaade {
    private DecimalFormat df = new DecimalFormat("0.##");
    private BorderPane borderPane;
    private Stat HV = new Stat(statistikaTuup.HEADJAVEAD, "headjavead.txt");
    private Stat AK = new Stat(statistikaTuup.AJAKULU, "ajakulu.txt");
    private double[] keskmiseAjaAndmed = AK.loeStatistikat();
    private double[] täpsusAndmed = HV.loeStatistikat();

    public Statistikavaade(BorderPane borderPane) throws IOException {
        this.borderPane = borderPane;
        Stat.failid();
    }


    public void kuva() {
        VBox kõikElemendid = new VBox();
        HBox andmed = new HBox();
        VBox aeg = new VBox();
        VBox vead = new VBox();
        VBox kustuta = new VBox();

        kõikElemendid.setSpacing(40);
        kustuta.setSpacing(10);

        Label pais = new Label("Andmed");
        pais.setFont(new Font("Verdana", 32));
        pais.setTextFill(Color.rgb(255,255,255));
        andmed.getChildren().add(pais);
        andmed.setAlignment(Pos.CENTER);
        andmed.setPrefHeight(60);
        andmed.setStyle("-fx-background-color: #0096C9;");

        Text keskmineAegTekst = new Text("Ühe tehte äraarvamiseks on sul keskeltläbi kulunud");
        keskmineAegTekst.setFont(new Font("Verdana", 16));
        aeg.getChildren().add(keskmineAegTekst);
        aeg.setAlignment(Pos.CENTER);

        Text keskmineAeg = new Text(df.format(keskmiseAjaAndmed[0]/1000)+" sekundit.");
        if (keskmiseAjaAndmed[0] == 0) {
            keskmineAeg.setText("teadmata arv sekundeid. Sa ei ole veel tehteid sooritanud.");
        }
        keskmineAeg.setFont(new Font("Verdana", 16));
        aeg.getChildren().add(keskmineAeg);

        Text headJaVeadTekst = new Text("Kokku oled sa õigesti arvanud");
        headJaVeadTekst.setFont(new Font("Verdana", 16));
        vead.getChildren().add(headJaVeadTekst);
        vead.setAlignment(Pos.CENTER);

        String kord = "";
        if (täpsusAndmed[1] == 1) {
            kord = "korra";
        } else {
            kord = "korda";
        }
        Text täpsusProtsent = new Text(((int) täpsusAndmed[1]+" "+kord+" "+(int) täpsusAndmed[0]+
                "-st korrast. Protsentuaalne täpsus: "+(Math.round(täpsusAndmed[1]/täpsusAndmed[0]*100))+"%."));
        if (täpsusAndmed[0] == 0 && täpsusAndmed[1] == 0) {
            täpsusProtsent.setText("teadmata arv kordi. Sa ei ole veel tehteid sooritanud.");
        }
        täpsusProtsent.setFont(new Font("Verdana", 16));
        vead.getChildren().add(täpsusProtsent);
        Label tegevus = new Label("");
        if (täpsusAndmed[0] == 0 && täpsusAndmed[1] == 0 && keskmiseAjaAndmed[0] == 0) {
            tegevus.setText("Andmeid ei ole!");
        }
        Button kustutaAndmed = new Button("Andmete kustutamiseks vajuta siia");
        kustutaAndmed.setFont(new Font("Verdana",12));
        kustutaAndmed.setOnMouseClicked(e -> {
            try {
                Stat.failidTuhjaks();
                täpsusAndmed[0] = 0;
                täpsusAndmed[1] = 0;
                keskmiseAjaAndmed[0] = 0;
                kuva();
            } catch (IOException io) {
                kustutaAndmed.setText("Midagi läks valesti.");
            }
        });


        kustuta.getChildren().addAll(kustutaAndmed,tegevus);
        kustuta.setAlignment(Pos.CENTER);

        kõikElemendid.getChildren().addAll(andmed, aeg, vead, kustuta);

        borderPane.setCenter(kõikElemendid);
    }
}


