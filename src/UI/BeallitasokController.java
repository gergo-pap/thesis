package UI;

import Main.Beallitasok;
import Main.Database;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class BeallitasokController {

    private Beallitasok beallitasok;
    private Database database;
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

    void initializeData(Beallitasok beallitasok, Database database) {
        this.beallitasok = beallitasok;
        this.database = database;
        this.resetUI();
    }

    private void resetUI() {
        this.utasBerletTF.setText(this.beallitasok.getUtasBerletTF().toString());
        this.utasKorMinTF.setText(this.beallitasok.getUtasKorMinTF().toString());
        this.utasKorMaxTF.setText(this.beallitasok.getUtasKorMaxTF().toString());
        this.utasEgyenlegIgTF.setText(this.beallitasok.getUtasEgyenlegIgTF().toString());
        this.utasJegyTF.setText(this.beallitasok.getUtasJegyTF().toString());
        this.utasBerletTF.setText(this.beallitasok.getUtasBerletTF().toString());
    }

    private void applySettings() throws SQLException {
        this.beallitasok.setUtasBerletTF(Integer.parseInt(this.utasBerletTF.getText()));
        this.beallitasok.setUtasKorMinTF(Integer.parseInt(this.utasKorMinTF.getText()));
        this.beallitasok.setUtasKorMaxTF(Integer.parseInt(this.utasKorMaxTF.getText()));
        this.beallitasok.setUtasEgyenlegIgTF(Integer.parseInt(this.utasEgyenlegIgTF.getText()));
        this.beallitasok.setUtasJegyTF(Integer.parseInt(this.utasJegyTF.getText()));
        this.beallitasok.setUtasBerletTF(Integer.parseInt(this.utasBerletTF.getText()));

        database.loadSettings(this.beallitasok);
        database.refreshAllRow();
    }

    public void OnApply(MouseEvent mouseEvent) throws SQLException {
        this.applySettings();
    }

    public void OnReset(MouseEvent mouseEvent) {
        this.resetUI();
    }
}
