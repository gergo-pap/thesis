package Main;

import java.sql.SQLException;

public class Initialize {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataBase dataBase = new DataBase();
        dataBase.createUtasokTable();
        dataBase.postUtasNumberOfTimes(85);
    }
}
