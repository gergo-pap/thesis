package Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Busz {

    String buszJaratSzam;
    int buszJegyAr;
    int buszKapacitas;
    int buszSzabadHelyekSzama;
    boolean buszElsoAjtose;
    int buszBuntetesekSzama;

    DataBase dataBase = new DataBase();

    public Busz(int buszKapacitas) throws SQLException, ClassNotFoundException {
        this.buszKapacitas = buszKapacitas;
    }

    public Busz(String buszJaratSzam, int buszKapacitas) throws SQLException, ClassNotFoundException {
        this.buszJaratSzam = buszJaratSzam;
        this.buszKapacitas = buszKapacitas;
        buszSzabadHelyekSzama = buszKapacitas;

    }

    public void buszFelszallUtas(int buszMennyiUtas) throws SQLException {
        for (int i = 1; i <= buszMennyiUtas; i++) {
                if (getBuszSzabadHelyekSzama() > 0 && dataBase.getAnything("boolean", i, "utasUtazikE") == 0) {
                    dataBase.setAnything("boolean", i, "utasUtazikE", "true");
                    setBuszSzabadHelyekSzama(getBuszSzabadHelyekSzama() - 1);
                } else if (dataBase.getAnything("boolean", i, "utasVanEJegye") == 1) {
                    dataBase.setAnything("boolean", i, "utasVanEJegye", "false");
                } else {
                    System.out.println("Nincs szabad hely a buszon vagy.." + i);
                }
        }
    }

    public void buszLeszallUtas(int buszMennyiUtas) throws SQLException {
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

    public int getBuszSzabadHelyekSzama() {
        return buszSzabadHelyekSzama;
    }

    public void setBuszSzabadHelyekSzama(int buszSzabadHelyekSzama) {
        this.buszSzabadHelyekSzama = buszSzabadHelyekSzama;
    }

}
