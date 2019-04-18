package UI;

import Main.DataBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;

public class BeallitasokController {

    private TextField utaskorMinTF;
    private TextField utaskorMaxTF;
    private TextField utasEgyenlegIgTF;
    private TextField utasJegyTF;
    private TextField utasBerletTF;


    public BeallitasokController() throws IOException, ParseException, SQLException, ClassNotFoundException {
        showBeallitasok();
        //utaskorMinTF.setText("sd");
        //getVariables();
    }

    public void showBeallitasok() throws IOException, SQLException, ClassNotFoundException, ParseException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UI.beallitasok.fxml"));
        fxmlLoader.setController(new MainController());
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        getVariables();
    }

    public void getVariables() throws SQLException, ClassNotFoundException {
        DataBase dataBase = new DataBase();
        this.utaskorMinTF.setText("ssad"/*+this.dataBase.getUtaskorMin()*/);/*
        this.utaskorMaxTF.setText(""+this.dataBase.getUtaskorMax());
        this.utasEgyenlegIgTF.setText(""+this.dataBase.getUtasEgyenlegMax());
        this.utasJegyTF.setText(""+this.dataBase.getUtasJegy());
        this.utasBerletTF.setText(""+this.dataBase.getUtasBerlet());*/
    }

    public void setVaribales() throws SQLException, ClassNotFoundException {
        DataBase dataBase = new DataBase();
        dataBase.setUtaskorMin(Integer.parseInt(this.utaskorMinTF.getText()));
        dataBase.setUtaskorMax(Integer.parseInt(this.utaskorMaxTF.getText()));
        dataBase.setUtasEgyenlegMax(Integer.parseInt(this.utasEgyenlegIgTF.getText()));
        dataBase.setUtasJegy(Integer.parseInt(this.utasJegyTF.getText()));
        dataBase.setUtasBerlet(Integer.parseInt(this.utasBerletTF.getText()));
    }


}
