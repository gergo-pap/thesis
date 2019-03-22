package Main;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Busz {

    int buszJegyAr;
    boolean buszElsoAjtose;
    int buszBuntetesekSzama;
    DataBase dataBase = new DataBase();
    private String buszJaratSzam;
    private int buszKapacitas;
    private int buszSzabadHelyekSzama;
    private List<Allomas> allomasok;
    private Allomas aktualisAllomas;
    private ListIterator<Allomas> hatralevo_allomasok;
    private int allomas_index;

    public Busz(String buszJaratSzam, int buszKapacitas) throws SQLException, ClassNotFoundException, IOException, ParseException {
        this.buszJaratSzam = buszJaratSzam;
        this.buszKapacitas = buszKapacitas;
        this.buszSzabadHelyekSzama = buszKapacitas;
        this.allomasok = dataBase.getAllomasokLista(buszJaratSzam);
        buszAStartPoziciora();
    }

    void buszAStartPoziciora() {
        hatralevo_allomasok = this.allomasok.listIterator();
        allomas_index = 0;
        aktualisAllomas = hatralevo_allomasok.next();
        printState();
    }

    Allomas getAktualisAllomas() {
        return aktualisAllomas;
    }

    List<Allomas> getAllomasok() {
        return allomasok;
    }

    private void printState() {
        System.out.println("----------------------------" + this.buszJaratSzam + " busz aktuális megállója:(" + allomas_index + ") " + aktualisAllomas.getName() + " [" + aktualisAllomas.getX() + ", " + aktualisAllomas.getY() + "]----------------------------");
    }

    boolean kovetkezoMegallo() throws SQLException {
        if (!hatralevo_allomasok.hasNext()) {
            buszLeszallOsszesUtas();

            return false;
        }

        aktualisAllomas = hatralevo_allomasok.next();
        allomas_index++;
        printState();

        Random r = new Random();
        buszFelszallUtas(r.nextInt(10));
        System.out.println("buszSzabadhelyekszama " + getBuszSzabadHelyekSzama());

        buszEllenorzes();

        return true;
    }

    void buszFelszallUtas(int buszMennyiUtas) throws SQLException {
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
                if (i == buszMennyiUtas) { //>=
                    //System.out.println("--Buszra felszállt utasok száma: " + buszMennyiUtas + "--");
                    //System.out.print("Nem tudtak felszallni: ");
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
        //System.out.println("--felszállt,bérlet--" + j);
    }

    void felszallJeggyel(int j) throws SQLException {
        dataBase.setAnything("boolean", j, "utasVanEJegye", "false");
        dataBase.setAnything("boolean", j, "utasUtazikE", "true");
        setBuszSzabadHelyekSzama(getBuszSzabadHelyekSzama() - 1);
        //System.out.println("--felszáll, jegy--" + j);
    }

    public void buszLeszallOsszesUtas() throws SQLException {

        for (int j = 1; j <= dataBase.countTableSize("utasok"); j++) {
            leszallUtas(j);
        }
        System.out.println("Leszállt minden utas");
    }

    void buszLeszallUtas(int buszMennyiUtas) throws SQLException {
        int i = 0;
        for (int j = 1; j <= dataBase.countTableSize("utasok"); j++) {
            if (i == buszMennyiUtas) {
                //System.out.println("lezállt ennyi utas: " + i);
                return;
            } else if (dataBase.getAnything("boolean", j, "utasUtazikE") == 1) {
                leszallUtas(j);
                i++;
            } else {
                //System.out.println("Üres a busz, vagy az utas nem utazik: " + dataBase.getAnything("string", j, "id"));
            }
        }
    }

    void leszallUtas(int j) throws SQLException {
        dataBase.setAnything("boolean", j, "utasUtazikE", "false");
        //System.out.println("Leszállt az utas: " + dataBase.getAnything("string", j, "id"));
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
        System.out.println(buntetesekSzama + " büntetés volt a buszon");
    }


    void buszJegyetElhasznal(int i) throws SQLException {
        dataBase.setAnything("boolean", i, "utasVanEJegye", "false");
        System.out.println(dataBase.getAnything("int", i, "id") + " jegyet elhasznált (bérlet nincs)");
    }

    void buszJegyetVesz(int i) throws SQLException {
        dataBase.setNewIntValue(i, "utasEgyenleg", "450", "-");
        dataBase.setAnything("boolean", i, "utasVanEJegye", "true");
        System.out.println(dataBase.getAnything("int", i, "id") + " vett jegyet miután nem volt se jegye se bérlete, de elég pénze rá");
    }

    int getBuszSzabadHelyekSzama() {
        return buszSzabadHelyekSzama;
    }

    void setBuszSzabadHelyekSzama(int buszSzabadHelyekSzama) {
        this.buszSzabadHelyekSzama = buszSzabadHelyekSzama;
    }

}
