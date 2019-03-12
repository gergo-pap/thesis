package Main;
public class Main {


    public static void main(String[] args) throws Exception  {
        int mennyiUtasTEszt = 85;
        DataBase dataBase = new DataBase();
        dataBase.createUtasokTable();
        dataBase.postUtasNumberOfTimes(mennyiUtasTEszt);

        Busz busz = new Busz("34", 100);
        dataBase.createUtvonalakTable();
        dataBase.postUtvonal34("134");
        System.out.println(dataBase.getAllomasokLista("134"));
        busz.buszKozlekedik();

    }



}
