package UI;

import Main.Beallitasok;
import Main.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class BeallitasokController {

    private Beallitasok beallitasok;

    @FXML
    private TextField utasKorMinTF;

    @FXML
    private TextField utasKorMaxTF;

    @FXML
    private TextField utasEgyenlegIgTF;

    @FXML
    private TextField utasJegyTF;

    @FXML
    private TextField utasBerletTF;

    public void initializeData(Beallitasok beallitasok) {
        this.beallitasok = beallitasok;

        this.resetUI();
    }

    public void resetUI() {
        this.utasBerletTF.setText(this.beallitasok.getUtasBerletTF().toString());
        this.utasKorMinTF.setText(this.beallitasok.getUtasKorMinTF().toString());
        this.utasKorMaxTF.setText(this.beallitasok.getUtasKorMaxTF().toString());
        this.utasEgyenlegIgTF.setText(this.beallitasok.getUtasEgyenlegIgTF().toString());
        this.utasJegyTF.setText(this.beallitasok.getUtasJegyTF().toString());
        this.utasBerletTF.setText(this.beallitasok.getUtasBerletTF().toString());
    }
}
