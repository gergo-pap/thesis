package Main;

import javafx.scene.control.TextField;

public class Beallitasok {

    public TextField utaskorMinTF;
    public TextField utaskorMaxTF;
    public TextField utasEgyenlegIgTF;
    public TextField utasJegyTF;
    public TextField utasBerletTF;

    private DataBase dataBase;

    public Beallitasok(){
        getVariables();
    }



    private void getVariables(){
        utaskorMinTF.setText(""+dataBase.getUtaskorMin());
        utaskorMaxTF.setText(""+dataBase.getUtaskorMax());
        utasEgyenlegIgTF.setText(""+dataBase.getUtasEgyenlegMax());
        utasJegyTF.setText(""+dataBase.getUtasJegy());
        utasBerletTF.setText(""+dataBase.getUtasBerlet());
    }



}
