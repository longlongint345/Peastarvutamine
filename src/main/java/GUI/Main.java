package GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;


public class Main extends Application {

    @Override
    public void start(Stage peaLava) throws IOException {
        BorderPane borderPane = new BorderPane();
        Arvutamisvaade arvutamisvaade = new Arvutamisvaade(borderPane);
        JuhisteVaade juhised = new JuhisteVaade(borderPane);

        // Menüüriba
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Fail");
            MenuItem valireziimMenuItem = new MenuItem("Vali režiim");
            valireziimMenuItem.setOnAction(e -> kuvaReziimivalikud(arvutamisvaade, borderPane));
            MenuItem sulgeMenuItem = new MenuItem("Sulge");
            sulgeMenuItem.setOnAction(e -> peaLava.close());
            fileMenu.getItems().addAll(valireziimMenuItem, sulgeMenuItem);

        Menu sattedMenu = new Menu("Sätted");
            RadioMenuItem kumnendMenuItem = new RadioMenuItem("Soovin kümnendmurde");
            kumnendMenuItem.setOnAction(e -> {
                if (kumnendMenuItem.isSelected()) arvutamisvaade.setKasKumnendmurrud(true);
                else arvutamisvaade.setKasKumnendmurrud(false);
            });
            sattedMenu.getItems().add(kumnendMenuItem);

        Menu statMenu = new Menu("Statistika");
            MenuItem kuvaStatMenuItem = new MenuItem("Kuva statistika");
            kuvaStatMenuItem.setOnAction(event -> {
                try {
                    Statistikavaade statistikavaade = new Statistikavaade(borderPane);
                    statistikavaade.kuva();
                } catch (IOException e) {
                    System.out.println("Ei saanud vaadet kuvada.");
                }
            });
            statMenu.getItems().addAll(kuvaStatMenuItem);

        Menu abiMenu = new Menu("Abi");
            MenuItem juhendMenuItem = new MenuItem("Juhised");
            juhendMenuItem.setOnAction(e -> juhised.kuva());
            MenuItem infoMenuItem = new MenuItem("Info");
            infoMenuItem.setOnAction(e -> borderPane.setCenter(new Label("Versioon 1.0\n\nProgrammi autorid on Andre Anijärv ja Patrik Pruunsild.")));
            abiMenu.getItems().addAll(juhendMenuItem, infoMenuItem);
        menuBar.getMenus().addAll(fileMenu, sattedMenu, statMenu, abiMenu);

        borderPane.setTop(menuBar);

        kuvaReziimivalikud(arvutamisvaade, borderPane);


        // jalus
        HBox jalusHbox = new HBox();
        jalusHbox.getChildren().add(new Label("Koduekraanile saab tagasi, kui valida Fail -> Vali režiim"));
        jalusHbox.setAlignment(Pos.CENTER);
        jalusHbox.setPrefHeight(30);
        borderPane.setBottom(jalusHbox);

        Scene startStseen = new Scene(borderPane, 1000, 600);
        peaLava.setScene(startStseen);

        peaLava.setTitle("Peastarvutamine");
        peaLava.show();
    }

    /**
     * Meetod kuvab etteantud borderpane'i keskele arvutamise režiimi valikud (ning annab ka nuppudele funktsionaalsuse)
     * @param arvutamisvaade - et nupud kuhugi viiksid
     * @param borderPane
     */

    public static void kuvaReziimivalikud(Arvutamisvaade arvutamisvaade, BorderPane borderPane){

        VBox keskmisedelemendidVbox = new VBox();
        keskmisedelemendidVbox.setSpacing(50);
        // Päis
        HBox ulemineRiba = new HBox();
        Label pais = new Label("Vali režiim:");
        pais.setFont(new Font("Verdana", 32));
        pais.setTextFill(Color.rgb(255,255,255));
        ulemineRiba.getChildren().add(pais);
        ulemineRiba.setAlignment(Pos.CENTER);
        ulemineRiba.setPrefHeight(60);
        ulemineRiba.setStyle("-fx-background-color: #0096C9;");
        keskmisedelemendidVbox.getChildren().add(ulemineRiba);

        // valikud
        HBox tehtevalikudHbox = new HBox();
            // liitmisvalik
        VBox liitmisvalikudVbox = new VBox();
        liitmisvalikudVbox.setAlignment(Pos.CENTER);
        Label liimineLabel = new Label("Liitmine");
        liitmisvalikudVbox.getChildren().add(liimineLabel);
        Button[] liitmisnupud = new Button[]{
                new Button("1-kohalised"),
                new Button("2-kohalised"),
                new Button("3-kohalised"),
                new Button("4-kohalised"),
                new Button("5-kohalised"),
                new Button("6-kohalised")
        };
        seadistaNupud(liitmisnupud,Tehetetuup.LIITMINE,arvutamisvaade);

        liitmisvalikudVbox.getChildren().addAll(liitmisnupud);
        liitmisvalikudVbox.setSpacing(5);
        tehtevalikudHbox.getChildren().add(liitmisvalikudVbox);
            // lahutamisvalik
        VBox lahutamismisvalikudVbox = new VBox();
        lahutamismisvalikudVbox.setAlignment(Pos.CENTER);
        Label lahutamineLabel = new Label("Lahutamine");
        lahutamismisvalikudVbox.getChildren().add(lahutamineLabel);
        Button[] lahutamismisnupud = new Button[]{
                new Button("1-kohalised"),
                new Button("2-kohalised"),
                new Button("3-kohalised"),
                new Button("4-kohalised"),
                new Button("5-kohalised"),
                new Button("6-kohalised")
        };
        seadistaNupud(lahutamismisnupud,Tehetetuup.LAHUTAMINE,arvutamisvaade);

        lahutamismisvalikudVbox.getChildren().addAll(lahutamismisnupud);
        lahutamismisvalikudVbox.setSpacing(5);
        tehtevalikudHbox.getChildren().add(lahutamismisvalikudVbox);
            // korrutamisvalik
        VBox korrutamismisvalikudVbox = new VBox();
        korrutamismisvalikudVbox.setAlignment(Pos.CENTER);
        Label korrutamineLabel = new Label("Korrutamine");
        korrutamismisvalikudVbox.getChildren().add(korrutamineLabel);
        Button[] korrutamisnupud = new Button[]{
                new Button("1-kohalised"),
                new Button("2-kohalised"),
                new Button("3-kohalised"),
                new Button("4-kohalised"),
                new Button("5-kohalised"),
                new Button("6-kohalised")
        };
        seadistaNupud(korrutamisnupud,Tehetetuup.KORRUTAMINE,arvutamisvaade);

        korrutamismisvalikudVbox.getChildren().addAll(korrutamisnupud);
        korrutamismisvalikudVbox.setSpacing(5);
        tehtevalikudHbox.getChildren().add(korrutamismisvalikudVbox);
            // jagamisvalik
        VBox jagamisvalikudVbox = new VBox();
        jagamisvalikudVbox.setAlignment(Pos.CENTER);
        Label jagamisLabel = new Label("Jagamine");
        jagamisvalikudVbox.getChildren().add(jagamisLabel);
        Button[] jagamisnupud = new Button[]{
                new Button("1-kohalised"),
                new Button("2-kohalised"),
                new Button("3-kohalised"),
                new Button("4-kohalised"),
                new Button("5-kohalised"),
                new Button("6-kohalised")
        };
        seadistaNupud(jagamisnupud,Tehetetuup.JAGAMINE,arvutamisvaade);

        jagamisvalikudVbox.getChildren().addAll(jagamisnupud);
        jagamisvalikudVbox.setSpacing(5);
        tehtevalikudHbox.getChildren().add(jagamisvalikudVbox);
            //liitlahutamine
        VBox liitlahutamiseValikudVbox = new VBox();
        liitlahutamiseValikudVbox.setAlignment(Pos.CENTER);
        Label liitlahutamisLabel = new Label("Liitlahutamine");
        liitlahutamiseValikudVbox.getChildren().add(liitlahutamisLabel);
        Button[] liitlahutamisnupud = new Button[]{
                new Button("1-kohalised"),
                new Button("2-kohalised"),
                new Button("3-kohalised"),
                new Button("4-kohalised"),
                new Button("5-kohalised"),
                new Button("6-kohalised")
        };
        seadistaNupud(liitlahutamisnupud,Tehetetuup.LIITLAHUTAMINE,arvutamisvaade);

        liitlahutamiseValikudVbox.getChildren().addAll(liitlahutamisnupud);
        liitlahutamiseValikudVbox.setSpacing(5);
        tehtevalikudHbox.getChildren().add(liitlahutamiseValikudVbox);
            // juhutehe
        VBox juhutehteValikudVbox = new VBox();
        juhutehteValikudVbox.setAlignment(Pos.CENTER);
        Label juhutehteLabel = new Label("Juhutehe");
        juhutehteValikudVbox.getChildren().add(juhutehteLabel);
        Button[] juhutehtenupud = new Button[]{
                new Button("1-kohalised"),
                new Button("2-kohalised"),
                new Button("3-kohalised"),
                new Button("4-kohalised"),
                new Button("5-kohalised"),
                new Button("6-kohalised")
        };
        seadistaNupud(juhutehtenupud,Tehetetuup.JUHUTEHE,arvutamisvaade);

        juhutehteValikudVbox.getChildren().addAll(juhutehtenupud);
        juhutehteValikudVbox.setSpacing(5);
        tehtevalikudHbox.getChildren().add(juhutehteValikudVbox);


        tehtevalikudHbox.setSpacing(25);
        tehtevalikudHbox.setAlignment(Pos.CENTER);
        keskmisedelemendidVbox.getChildren().add(tehtevalikudHbox);
        borderPane.setCenter(keskmisedelemendidVbox);
    }

    /**
     * Meetod on mõeldud nuppudele funktsionaalsuse andmiseks (koodi kokkuhoid - nuppe on palju)
     * @param nupujada - see peab olema alati pikkusega 6
     * @param tehetetuup - nupu kohta käiv tehtetüüp
     * @param arvutamisvaade - vajalik, et nupp saaks selle vaate avada
     */
    private static void seadistaNupud(Button[] nupujada, Tehetetuup tehetetuup, Arvutamisvaade arvutamisvaade){
        for (int i = 0; i <= 5; i++){
            int finalI = i + 1;
            nupujada[i].setOnMouseClicked(e -> {
                arvutamisvaade.setTehetetuup(tehetetuup);
                arvutamisvaade.setRaskusaste((byte)finalI);
                arvutamisvaade.kuva();
            });
        }
    }

    public static void main(String[] args)  {
        launch(args);
    }
    public static int loos(int ulatus) {
        // Antud: int ulatus (see näitab mitu võimalikku loosi tulemust saab olla)
        // Tagastab täisarvu lõigust [1, ulatus]
        Random random = new Random();
        return random.nextInt(ulatus)+1;
    }
}
