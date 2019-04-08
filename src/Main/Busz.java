package Main;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Busz {

    DataBase dataBase = new DataBase();
    private String buszJaratSzam;
    private int buszKapacitas;
    private int buszSzabadHelyekSzama;
    private List<Allomas> allomasok;
    private Allomas aktualisAllomas;
    private ListIterator<Allomas> hatralevoAllomasok;
    private int allomasIndex;


    Busz(String buszJaratSzam, int buszKapacitas) throws SQLException, ClassNotFoundException, IOException, ParseException {
        this.buszJaratSzam = buszJaratSzam;
        this.buszKapacitas = buszKapacitas;
        this.buszSzabadHelyekSzama = buszKapacitas;
        this.allomasok = dataBase.getAllomasokLista(buszJaratSzam);
        buszAStartPoziciora();
    }

    void buszAStartPoziciora() {
        hatralevoAllomasok = this.allomasok.listIterator();
        allomasIndex = 0;
        aktualisAllomas = hatralevoAllomasok.next();
        printState();
    }

    Allomas getAktualisAllomas() {

        return aktualisAllomas;
    }

    private void printState() {
        //MainController.labelAllomas.setText("" + this.buszJaratSzam + " busz aktuális megállója:(" + allomasIndex + ") " + aktualisAllomas.getName());
        System.out.println("----------------------------" + this.buszJaratSzam + " busz aktuális megállója:(" + allomasIndex + ") " + aktualisAllomas.getName() + " [" + aktualisAllomas.getX() + ", " + aktualisAllomas.getY() + "]----------------------------");
    }

    boolean kovetkezoMegallo() throws SQLException {
        Random r = new Random();

        if (!hatralevoAllomasok.hasNext()) {
            buszLeszallOsszesUtas();

            return false;
        }

        aktualisAllomas = hatralevoAllomasok.next();
        allomasIndex++;
        printState();

        buszLeszallUtas(r.nextInt(6));

        buszFelszallUtas(r.nextInt(10));
        MainController.labelSzabadhelyekSzama.setVisible(true);
        MainController.labelSzabadhelyekSzama.setText("Busz szabad helyek száma: " + buszSzabadHelyekSzama);
        buszEllenorzes();

        return true;
    }

    void buszFelszallUtas(int buszMennyiUtas) throws SQLException {
        MainController.labelFelszallutasok.setText("Felszállt utasok száma: " + buszMennyiUtas);
        MainController.labelFelszallutasok.setVisible(true);
        int i = 0;
        List<Integer> nemTudtakFelszallniLista = new ArrayList<>();
        for (int j = 1; j <= dataBase.countTableSize("utasok"); j++) {
            if (dataBase.getAnything("boolean", j, "utasUtazikE") == 1 || buszMennyiUtas == 0) {
            } else {
                if (getBuszSzabadHelyekSzama() > 0 && dataBase.getAnything("boolean", j, "utasVanEBerlete") == 1) {
                    felszallBerlettel(j);
                    i++;
                } else if (dataBase.getAnything("boolean", j, "utasVanEJegye") == 1) {
                    felszallJeggyel(j);
                    i++;
                } else {
                    nemTudtakFelszallniLista.add(dataBase.getAnything("int", j, "id"));
                }
                if (i == buszMennyiUtas) {
                    for (Integer intek : nemTudtakFelszallniLista) {
                        System.out.print(intek + ", ");
                    }
                    return;
                }
            }
        }

    }

    void felszallBerlettel(int j) throws SQLException {
        dataBase.setAnything("boolean", j, "utasUtazikE", "true");
        setBuszSzabadHelyekSzama(getBuszSzabadHelyekSzama() - 1);
    }

    void felszallJeggyel(int j) throws SQLException {
        dataBase.setAnything("boolean", j, "utasVanEJegye", "false");
        dataBase.setAnything("boolean", j, "utasUtazikE", "true");
        setBuszSzabadHelyekSzama(getBuszSzabadHelyekSzama() - 1);
    }

    private void buszLeszallOsszesUtas() throws SQLException {

        for (int j = 1; j <= dataBase.countTableSize("utasok"); j++) {
            leszallUtas(j);
        }
        MainController.labelEsemenyek.setVisible(true);
        MainController.labelEsemenyek.setText("Leszállt minden utas");
    }

    void buszLeszallUtas(int buszMennyiUtas) throws SQLException {
        MainController.labelLeszallutasok.setText("Felszállt utasok száma: " + buszMennyiUtas);
        MainController.labelLeszallutasok.setVisible(true);
        int i = 0;
        for (int j = 1; j <= dataBase.countTableSize("utasok"); j++) {
            if (i == buszMennyiUtas) {
                return;
            } else if (dataBase.getAnything("boolean", j, "utasUtazikE") == 1) {
                leszallUtas(j);
                i++;
            } else {
            }
        }
    }

    void leszallUtas(int j) throws SQLException {
        dataBase.setAnything("boolean", j, "utasUtazikE", "false");
        setBuszSzabadHelyekSzama(getBuszSzabadHelyekSzama() + 1);
    }

    void buszEllenorzes() throws SQLException {
        int buntetesekSzama = 0;
        for (int i = 1; i <= buszKapacitas - getBuszSzabadHelyekSzama(); i++) {
            if (dataBase.getAnything("boolean", i, "utasUtazikE") == 1 || dataBase.getAnything("boolean", i, "utasVanEBerlete") == 1) {
            } else {
                if (dataBase.getAnything("boolean", i, "utasVanEJegye") == 1) {
                    buszJegyetElhasznal(i);
                }
                if (dataBase.getAnything("boolean", i, "utasEgyenleg") > 450) {
                    buszJegyetVesz(i);
                } else {
                    dataBase.setNewIntValue(i, "utasEgyenleg", "16000", "-");
                    buntetesekSzama++;
                }
            }
        }
        MainController.labelBuntetesek.setVisible(true);
        MainController.labelBuntetesek.setText(buntetesekSzama + " büntetés volt a buszon");

    }


    void buszJegyetElhasznal(int i) throws SQLException {
        dataBase.setAnything("boolean", i, "utasVanEJegye", "false");
        MainController.labelEsemenyek.setVisible(true);
        MainController.labelEsemenyek.setText(dataBase.getAnything("int", i, "id") + " jegyet elhasznált (bérlet nincs)");

    }

    void buszJegyetVesz(int i) throws SQLException {
        dataBase.setNewIntValue(i, "utasEgyenleg", "450", "-");
        dataBase.setAnything("boolean", i, "utasVanEJegye", "true");
        MainController.labelEsemenyek.setVisible(true);
        MainController.labelEsemenyek.setText(dataBase.getAnything("int", i, "id") + " vett jegyet miután nem volt se jegye se bérlete, de elég pénze rá");

    }

    int getBuszSzabadHelyekSzama() {
        return buszSzabadHelyekSzama;
    }

    void setBuszSzabadHelyekSzama(int buszSzabadHelyekSzama) {
        this.buszSzabadHelyekSzama = buszSzabadHelyekSzama;
    }

}
