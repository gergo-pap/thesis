package Main;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        int mennyiUtasTEszt = 50;
        DataBase dataBase = new DataBase();
        dataBase.createUtasokTable();
        //dataBase.postUtasNumberOfTimes(mennyiUtasTEszt);
        dataBase.setAnything("int", 2, "utasKor", "77");
        System.out.println(dataBase.getAnything("int", 2, "utasKor"));
        System.out.println(dataBase.getAnything("boolean", 4, "utasUtazikE"));

        Busz busz = new Busz("34", 50);
        busz.buszFelszallUtas(mennyiUtasTEszt);
        busz.buszEllenorzes(mennyiUtasTEszt);
        busz.buszLeszallUtas(mennyiUtasTEszt);
    }
}
