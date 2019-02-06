package Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
            for (int j = 1; j <= dataBase.countTableSize("utasok"); j++){
                if (getBuszSzabadHelyekSzama() > 0 && dataBase.getAnything("boolean", j, "utasUtazikE") == 0 && dataBase.getAnything("boolean", j, "utasVanEBerlete") == 1) {
                        dataBase.setAnything("boolean", j, "utasUtazikE", "true");
                        setBuszSzabadHelyekSzama(getBuszSzabadHelyekSzama() - 1);
                        System.out.println("felszállt, mivel volt bérlete");
                        i++;
                } else if (dataBase.getAnything("boolean", j, "utasVanEJegye") == 1) {
                    dataBase.setAnything("boolean", j, "utasVanEJegye", "false");
                    dataBase.setAnything("boolean", j, "utasUtazikE", "true");
                    setBuszSzabadHelyekSzama(getBuszSzabadHelyekSzama() - 1);
                    System.out.println("felszáll, volt jegye");
                    i++;
                } else {
                    System.out.print("Nem tudott felszállni: " + dataBase.getAnything("int", j, "id")+ " ");

                }
                if (i >= buszMennyiUtas){
                    return;
                }
        }
    }

    void buszLeszallUtas(int buszMennyiUtas) throws SQLException {
        for (int i = 1; i <= buszMennyiUtas; i++) {
            if (/*utasok.size() > 0 &&*/dataBase.getAnything("boolean", i, "utasUtazikE") == 1) {
                dataBase.setAnything("boolean", i, "utasUtazikE", "false");
                setBuszSzabadHelyekSzama(getBuszSzabadHelyekSzama() + 1);
            } else {
                System.out.println("Üres a busz, vagy az utas nem utazik: " + dataBase.getAnything("string", i, "utasNev"));
            }
        }
    }

    void buszEllenorzes(int buszMennyiUtas) throws SQLException {
        int buntetesekSzama = 0;
        for (int i = 1; i <= buszMennyiUtas; i++) {
            if (dataBase.getAnything("boolean", i, "utasVanEBerlete") == 1) {
                //System.out.println(dataBase.getAnything("int", i, "id") + " volt bérlete");
            } else if (dataBase.getAnything("boolean", i, "utasVanEJegye") == 1) {
                dataBase.setAnything("boolean", i, "utasVanEJegye", "false");
                System.out.println(dataBase.getAnything("int", i, "id") + " jegyet elhasznált (bérlet nincs)");
                if (dataBase.getAnything("boolean", i, "utasEgyenleg") > 450) {
                    dataBase.setNewIntValue(i, "utasEgyenleg", "450", "-");
                    dataBase.setAnything("boolean", i, "utasVanEJegye", "true");
                    System.out.println(dataBase.getAnything("int", i, "id") + " vett jegyet miután nem volt se jegye se bérlete, de elég pénze rá");
                }
            } else {
                dataBase.setNewIntValue(i, "utasEgyenleg", "16000", "-");
                //utasok.remove(utasok.get(i));     //nem műödik
                buntetesekSzama++;
            }
        }
        System.out.println(buntetesekSzama + " büntetés volt a buszon(nem első ajtós)");
    }

    void buszKozlekedik() throws SQLException, InterruptedException {
        List<String > allomasok = dataBase.getAllomasokLista("134");
        Random r = new Random();
        for (int i = 0; i < allomasok.size(); i++){
            buszAktualisMegallo = allomasok.get(i);
            System.out.println("Busz aktuális megállója:("+i+") " + buszAktualisMegallo + "----------------------------");
            buszFelszallUtas(r.nextInt(10));
            //buszLeszallUtas();
            System.out.println("buszSzabadhelyekszama "+getBuszSzabadHelyekSzama());
            Thread.sleep(1000);
        }
    }


    int getBuszSzabadHelyekSzama() {
        return buszSzabadHelyekSzama;
    }

    void setBuszSzabadHelyekSzama(int buszSzabadHelyekSzama) {
        this.buszSzabadHelyekSzama = buszSzabadHelyekSzama;
    }

}
