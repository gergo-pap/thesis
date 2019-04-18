package Main;

import javafx.scene.control.TextField;

import java.sql.SQLException;

public class Beallitasok {

    public TextField utaskorMinTF;
    public TextField utaskorMaxTF;
    public TextField utasEgyenlegIgTF;
    public TextField utasJegyTF;
    public TextField utasBerletTF;

    private DataBase dataBase;

    public Beallitasok() throws SQLException, ClassNotFoundException {
        getVariables();
    }



    private void getVariables() throws SQLException, ClassNotFoundException {
        dataBase = new DataBase();
        this.utaskorMinTF.setText(""+this.dataBase.getUtaskorMin());
        this.utaskorMaxTF.setText(""+this.dataBase.getUtaskorMax());
        this.utasEgyenlegIgTF.setText(""+this.dataBase.getUtasEgyenlegMax());
        this.utasJegyTF.setText(""+this.dataBase.getUtasJegy());
        this.utasBerletTF.setText(""+this.dataBase.getUtasBerlet());
    }



}
