package Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Busz {

    String buszJaratSzam;
    String buszAktualisMegallo;
    int buszJegyAr;
    int buszKapacitas;
    int buszSzabadHelyekSzama;
    boolean buszElsoAjtose;
    int buszBuntetesekSzama;

    DataBase dataBase = new DataBase();

    Busz(int buszKapacitas) throws SQLException, ClassNotFoundException {
        this.buszKapacitas = buszKapacitas;
    }

    public Busz(String buszJaratSzam, int buszKapacitas) throws SQLException, ClassNotFoundException {
        this.buszJaratSzam = buszJaratSzam;
        this.buszKapacitas = buszKapacitas;
        buszSzabadHelyekSzama = buszKapacitas;

    }

    void buszFelszallUtas(int buszMennyiUtas) throws SQLException, InterruptedException {
            int i = 0;
            List<Integer> nemTudtakFelszallniLista = new ArrayList<>();
            for (int j = 1; j <= dataBase.countTableSize("utasok"); j++){
                if(dataBase.getAnything("boolean", j, "utasUtazikE") == 1 || buszMennyiUtas == 0){ }
                else {
                    if (getBuszSzabadHelyekSzama() > 0 &&  dataBase.getAnything("boolean", j, "utasVanEBerlete") == 1) {
                        felszallBerlettel(j);
                        i++;
                    } else if (dataBase.getAnything("boolean", j, "utasVanEJegye") == 1) {
                        felszallJeggyel(j);
                        i++;
                    } else {
                        nemTudtakFelszallniLista.add(dataBase.getAnything("int", j, "id"));
                    }
                    if (i == buszMennyiUtas) { //>=
                        System.out.println("--Buszra felszállt utasok száma: " + buszMennyiUtas + "--");
                        System.out.print("Nem tudtak felszallni: ");
                        for (Integer intek:nemTudtakFelszallniLista) {
                            System.out.print(intek+", ");
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

    void felszallJeggyel (int j) throws SQLException {
        dataBase.setAnything("boolean", j, "utasVanEJegye", "false");
        dataBase.setAnything("boolean", j, "utasUtazikE", "true");
        setBuszSzabadHelyekSzama(getBuszSzabadHelyekSzama() - 1);
        //System.out.println("--felszáll, jegy--" + j);
    }

    void buszLeszallOsszesUtas() throws SQLException {

        for (int j = 1; j <= dataBase.countTableSize("utasok"); j++) {
            leszallUtas(j);
        }
        System.out.println("Leszállt minden utas");
    }

    void buszLeszallUtas(int buszMennyiUtas) throws SQLException {
        int i = 0;
        for (int j = 1; j <= dataBase.countTableSize("utasok"); j++){
            if (i == buszMennyiUtas){
                System.out.println("lezállt ennyi utas: " + i);
                return;}
            else if (dataBase.getAnything("boolean", j, "utasUtazikE") == 1) {
                leszallUtas(j);
                i++;
            } else {
                System.out.println("Üres a busz, vagy az utas nem utazik: " + dataBase.getAnything("string", j, "id"));
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
        for (int i = 1; i <= buszKapacitas-getBuszSzabadHelyekSzama(); i++) {
            if (dataBase.getAnything("boolean", i, "utasUtazikE") == 1 || dataBase.getAnything("boolean", i, "utasVanEBerlete") == 1) {
            } else {
                if (dataBase.getAnything("boolean", i, "utasVanEJegye") == 1) {
                    dataBase.setAnything("boolean", i, "utasVanEJegye", "false");
                    System.out.println(dataBase.getAnything("int", i, "id") + " jegyet elhasznált (bérlet nincs)");
                }
                if (dataBase.getAnything("boolean", i, "utasEgyenleg") > 450) {
                        dataBase.setNewIntValue(i, "utasEgyenleg", "450", "-");
                        dataBase.setAnything("boolean", i, "utasVanEJegye", "true");
                        System.out.println(dataBase.getAnything("int", i, "id") + " vett jegyet miután nem volt se jegye se bérlete, de elég pénze rá");
                    }
                 else {
                    dataBase.setNewIntValue(i, "utasEgyenleg", "16000", "-");
                    buntetesekSzama++;
                }
            }
        }
        System.out.println(buntetesekSzama + " büntetés volt a buszon");
    }

    void buszKozlekedik() throws SQLException, InterruptedException {
        List<String > allomasok = dataBase.getAllomasokLista("134");
        Random r = new Random();
        for (int i = 0; i < allomasok.size(); i++){
            int rand = r.nextInt(10);
            buszAktualisMegallo = allomasok.get(i);
            System.out.println("----------------------------Busz aktuális megállója:("+i+") " + buszAktualisMegallo + "----------------------------");
            buszFelszallUtas(rand);
            System.out.println("buszSzabadhelyekszama "+getBuszSzabadHelyekSzama());
            buszEllenorzes();
            Thread.sleep(500);
        }
        //buszLeszallOsszesUtas();
    }


    int getBuszSzabadHelyekSzama() {
        return buszSzabadHelyekSzama;
    }

    void setBuszSzabadHelyekSzama(int buszSzabadHelyekSzama) {
        this.buszSzabadHelyekSzama = buszSzabadHelyekSzama;
    }

}
