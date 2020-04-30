package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class JuhisteVaade {
    private BorderPane borderPane;

    public JuhisteVaade(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
    public void kuva() {
        HBox header = new HBox();
        VBox juhised = new VBox();
        VBox kõikElemendid = new VBox();
        kõikElemendid.setSpacing(40);

        Label pais = new Label("Juhised");
        pais.setFont(new Font("Verdana", 32));
        pais.setTextFill(Color.rgb(255,255,255));
        header.getChildren().add(pais);
        header.setAlignment(Pos.CENTER);
        header.setPrefHeight(60);
        header.setStyle("-fx-background-color: #0096C9;");

        Text juhtnöörid = new Text("Tere, hea kasutaja!\n\n" +
                "Arvutamise alustamiseks vali Fail -> Vali režiim, vali endale sobivast arvutustüübist raskusaste vajutades nupule.\n" +
                "Näiteks veerust 'Liitmine' vajutades nupule '2-kohalised'.\n\n" +
                "Kui soovid, et etteantud tehe sisaldaks kümnendmurde, vali Sätted -> Soovin kümnendmurde. Kümnend-\n" +
                "murdude valiku eemaldamiseks pead Sätete alt Soovin kümnendmurde eest linnukese ära võtma.\n\n" +
                "Statistika nägemiseks ja kustutamiseks vali menüüribalt Statistika -> Kuva statistika.\n\n" +
                "Õige vastuse kuvamiseks arvutamisel vajutage tühikut.\n\n" +
                "Tekkinud probleemide korral saata meil\n" +
                "Patrikule: patrik_pruunsild@hotmail.com või Andrele: andre.anijarv@outlook.com");
        juhtnöörid.setFont(new Font("Verdana", 16));
        juhised.getChildren().add(juhtnöörid);
        juhised.setAlignment(Pos.CENTER);

        kõikElemendid.getChildren().addAll(header,juhised);
        borderPane.setCenter(kõikElemendid);
    }
}
