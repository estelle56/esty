/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestbeignet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.B;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author estelle
 */
public class GESTbeignet extends Application {

    @Override
    public void start(Stage fen)// permet de definnir la fenetre principale
    {
        GridPane grid = new GridPane();//gridpane permet de definir le cotenu du stage et où seront placer les boutons
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);//espce horizontal entre 2 composants
        grid.setVgap(10);// vertical
        Scene sc = new Scene(grid, 500, 350);// definir la scene où seront placés les elements

        fen.setTitle("gestion de Beignetariat");// titre de la fenetre
        Text title = new Text();
        title.setText(" calcul montant à payer");
        title.setFont(Font.font("tahoma", FontWeight.BOLD, 15));
        grid.add(title, 0, 0, 2, 1);
        Label qtelabel = new Label("QUANTITE");
        grid.add(qtelabel, 0, 1);
        TextField qteField = new TextField();
        grid.add(qteField, 1, 1);
        Label pulabel = new Label("PU");
        grid.add(pulabel, 0, 2);
        TextField puField = new TextField();
        grid.add(puField, 1, 2);
        Button btn = new Button("CALCULER");
        HBox hbBtn = new HBox();
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        Text netPay = new Text("NET A PAYER");
        grid.add(netPay, 0, 5);
        sc.getStylesheets().add(GESTbeignet.class.getResource("beignet1.css").toExternalForm());

        btn.setOnAction((ActionEvent event) -> {
            System.out.println("VEILLER PATIENTER ");
            int qte = Integer.parseInt(qteField.getText());
            int prixU = Integer.parseInt(puField.getText());
            
            int total = qte * prixU;
            
            netPay.setText("net à payer: " + total);
            
            Connection myConne = null; // classe pour la connection a la bd
            try// veut dire de generer l'erreur si le code ne compile pas
            {
                Class.forName("com.mysql.jdbc.Driver"); //pour dire au programme que c'est le driver de mysql q'on use
                myConne = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bdbeignet", "root", "");
                //donner le localhost utiliser port username et password
                System.out.println("Connecter");
                
                PreparedStatement stmnt = null;//pour preparer les requette envoyer dans la bd
                
                stmnt = myConne.prepareStatement("insert into gestion values(?,?,?)");//inserer dans la table gestion
                stmnt.setInt(1, qte);
                stmnt.setInt(2, prixU);
                stmnt.setInt(3, total);
                stmnt.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        fen.setScene(sc);
        fen.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
