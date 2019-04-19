package Main;

import javafx.scene.control.TextArea;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;


public class Busz {
    private String buszJaratSzam;
    private Database database;

    private BuszInfo buszInfo;
    private int buszSzabadHelyekSzama;
    private List<Allomas> allomasok;
    private Allomas aktualisAllomas;
    private ListIterator<Allomas> hatralevoAllomasok;

    private String infoFelszallUtasok;
    private String infoLeszallUtasok;
    private String infoBuntetesek;
    private String infoEsemenyek;


    public Busz(String buszJaratSzam, Database database) {
        this.buszJaratSzam = buszJaratSzam;
        this.database = database;

        this.buszInfo = database.getBuszInfo(buszJaratSzam);
        this.buszSzabadHelyekSzama = this.buszInfo.getKapacitas();

        this.allomasok = database.getAllomasokLista(buszJaratSzam);
        buszAStartPoziciora();
    }

    public void buszAStartPoziciora() {
        hatralevoAllomasok = this.allomasok.listIterator();
        aktualisAllomas = hatralevoAllomasok.next();
    }

    public Allomas getAktualisAllomas() {

        return aktualisAllomas;
    }

    public boolean kovetkezoMegallo() throws Exception {
        Random r = new Random();

        if (!hatralevoAllomasok.hasNext()) {

            buszLeszallOsszesUtas();

            return false;
        }

        aktualisAllomas = hatralevoAllomasok.next();

        buszLeszallUtas(r.nextInt(6));
        buszFelszallUtas(r.nextInt(10));
        buszEllenorzes();

        return true;
    }

    public void buszFelszallUtas(int buszMennyiUtas) throws Exception {
        this.infoFelszallUtasok = "Felszállt utasok száma: " + buszMennyiUtas;
        int i = 0;
        List<Integer> nemTudtakFelszallniLista = new ArrayList<>();
        for (int j = 1; j <= database.countTableSize(); j++) {
            if (database.getAnything("boolean", j, "utasUtazikE") == 1 || buszMennyiUtas == 0) {
            } else {
                if (buszSzabadHelyekSzama > 0 && database.getAnything("boolean", j, "utasVanEBerlete") == 1) {
                    felszallBerlettel(j);
                    i++;
                } else if (database.getAnything("boolean", j, "utasVanEJegye") == 1) {
                    felszallJeggyel(j);
                    i++;
                } else {
                    nemTudtakFelszallniLista.add(database.getAnything("int", j, "id"));
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
        database.setAnything("boolean", j, "utasUtazikE", "true");
        buszSzabadHelyekSzama--;
    }

    void felszallJeggyel(int j) throws SQLException {
        database.setAnything("boolean", j, "utasVanEJegye", "false");
        database.setAnything("boolean", j, "utasUtazikE", "true");
        buszSzabadHelyekSzama--;
    }

    public void buszLeszallOsszesUtas() throws SQLException {

        for (int j = 1; j <= database.countTableSize(); j++) {
            leszallUtas(j);
        }
        this.infoEsemenyek = "Leszállt minden utas";
    }

    public void buszLeszallUtas(int buszMennyiUtas) throws SQLException {
        this.infoLeszallUtasok = "Leszállt utasok száma: " + buszMennyiUtas;
        int i = 0;
        for (int j = 1; j <= database.countTableSize(); j++) {
            if (i == buszMennyiUtas) {
                return;
            } else if (database.getAnything("boolean", j, "utasUtazikE") == 1) {
                leszallUtas(j);
                i++;
            }
        }
    }

    public void leszallUtas(int j) throws SQLException {
        database.setAnything("boolean", j, "utasUtazikE", "false");
        buszSzabadHelyekSzama++;
    }

    public void buszEllenorzes() throws SQLException {
        int buntetesekSzama = 0;
        for (int i = 1; i <= this.buszInfo.getKapacitas() - buszSzabadHelyekSzama; i++) {
            if (database.getAnything("boolean", i, "utasUtazikE") == 1 || database.getAnything("boolean", i, "utasVanEBerlete") == 1) {
            } else {
                if (database.getAnything("boolean", i, "utasVanEJegye") == 1) {
                    buszJegyetElhasznal(i);
                }
                if (database.getAnything("boolean", i, "utasEgyenleg") > 450) {
                    buszJegyetVesz(i);
                } else {
                    database.setNewIntValue(i, "utasEgyenleg", "16000", "-");
                    buntetesekSzama++;
                }
            }
        }
        this.infoBuntetesek = buntetesekSzama + " büntetés volt a buszon";
    }

    public void buszJegyetElhasznal(int i) throws SQLException {
        database.setAnything("boolean", i, "utasVanEJegye", "false");
        this.infoEsemenyek = database.getAnything("int", i, "id") + " jegyet elhasznált (bérlet nincs)";
    }

    public void buszJegyetVesz(int i) throws SQLException {
        database.setNewIntValue(i, "utasEgyenleg", "450", "-");
        database.setAnything("boolean", i, "utasVanEJegye", "true");
        this.infoEsemenyek = database.getAnything("int", i, "id") + " vett jegyet miután nem volt se jegye se bérlete, de elég pénze rá";
    }

    public String getBuszInfo() {
        return String.format(
            "Busz szabad helyek száma: %d\n%s\n%s\n%s\n%s",
                this.buszSzabadHelyekSzama,
                this.infoFelszallUtasok,
                this.infoLeszallUtasok,
                this.infoBuntetesek,
                this.infoEsemenyek
        );
    }
}
