package Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.jar.Pack200.Packer.PASS;

public class DataBase {

    private Connection db;

    DataBase() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/transportsimulation?useSSL=false";
        String username = "root";
        String password = "";

        Class.forName(driver);
        db = DriverManager.getConnection(url, username, password);
        //System.out.println("Connected");
    }

    void createUtasokTable() throws SQLException {
        PreparedStatement create = db.prepareStatement(
                "CREATE TABLE IF NOT EXISTS utasok("
                        + "id int NOT NULL AUTO_INCREMENT   , "
                        + "utasNev          text            , "
                        + "utasKor          int             , "
                        + "utasEgyenleg 	int     	, "
                        + "utasVanEBerlete 	tinyint(1)	, "
                        + "utasVanEJegye 	tinyint(1)	, "
                        + "utasUtazikE 	tinyint(1)	, "
                        + "PRIMARY KEY(id))");
        //create.executeUpdate();
        checkSQL(create);
    }
    void createUtvonalakTable() throws SQLException {
            PreparedStatement create = db.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS utvonalak("
                            + "id int NOT NULL AUTO_INCREMENT      ,"
                            + "jaratSzam          text             ,"
                            + "allomasok          text            ,"
                            + "PRIMARY KEY(id))");
            checkSQL(create);
            System.out.println("createUtavonalakTable Complete.");
    }

    void dropTable() throws SQLException {
        PreparedStatement create = db.prepareStatement("DROP TABLE utasok");
        create.execute();
        System.out.println("Drop done");

        System.out.println("Drop done");
    }

    void postUtas() throws SQLException {
        Random r = new Random();
        PreparedStatement posted = db.prepareStatement(
                "INSERT INTO utasok "
                        + "("
                        + "utasNev,"
                        + "utasKor,"
                        + "utasEgyenleg,"
                        + "utasVanEBerlete,"
                        + "utasVanEJegye,"
                        + "utasUtazikE"
                        + ") "
                        + "VALUES (?,?,?,?,?,?)");

        posted.setString(1, NameGenerator());
        posted.setInt(2, r.nextInt((99 - 15) + 1) + 15);
        posted.setInt(3, r.nextInt(50000));
        posted.setBoolean(4, randomPercent(85));
        posted.setBoolean(5, randomPercent(50));
        posted.setBoolean(6, false);
        checkSQL(posted);
    }

    public void postUtasNumberOfTimes(int num) throws SQLException {
        for (int i = 0; i < num; i++) {
            postUtas();
        }
    }
    public void postUtvonal34(String jaratSzam) throws SQLException {

        ArrayList<String> allomasok34 = new ArrayList<>();
        allomasok34.add("Békásmegyer, Újmegyeri tér");
        allomasok34.add("Hadrianus utca");
        allomasok34.add("Madzsar József utca / Hadrianus utca");
        allomasok34.add("Szolgáltatóház");
        allomasok34.add("Békásmegyer H");
        allomasok34.add("Madzsar József utca / Pünkösdfürdő utca");
        allomasok34.add("Medgyessy Ferenc utca");
        allomasok34.add("Boglár utca");
        allomasok34.add("Pünkösdfürdő");

        String var1 = jaratSzam;
        for (String allomasok341 : allomasok34) {
                PreparedStatement posted = db.prepareStatement(
                        "INSERT INTO utvonalak "
                                + "("
                                + "jaratSzam,"
                                + "allomasok"
                                + ") "
                                + "VALUES (?,?)");
                posted.setString(1, var1);
                posted.setString(2, allomasok341);
                posted.executeUpdate();
            System.out.println("Allomasokkal feltolve");
        }
    }

    List<String> getAllomasokLista(String jaratSzam) throws SQLException {
        List<String> allomasokLista = new ArrayList<>();
        PreparedStatement statement = db.prepareStatement("SELECT * FROM utvonalak where jaratSzam =" + jaratSzam);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            String eredmeny = result.getString("allomasok");
            allomasokLista.add(eredmeny);
        }
        return allomasokLista;
    }

    int countTableSize(String tableName) throws SQLException {
        PreparedStatement create = db.prepareStatement("SELECT COUNT(*) FROM " + tableName);
        ResultSet result = create.executeQuery();
        if (result.next()) {
            //System.out.println(result.getInt(1));
        }
        return result.getInt(1);
    }

    int getAnything(String varibaleType, int id, String row) throws SQLException {
        PreparedStatement statement = db.prepareStatement("SELECT * FROM utasok where id =?");
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        int eredmeny;
        while (result.next()) {
            switch (varibaleType.toLowerCase()) {
                case "boolean":
                    eredmeny = result.getInt(row);
                    //System.out.println(row + "(id:" + id + "):" + eredmeny);
                    return eredmeny;
                case "int":
                    eredmeny = result.getInt(row);
                    //System.out.println(row + "(id:" + id + "):" + eredmeny);
                    return eredmeny;
                case "string":
                    String eredmenyS = result.getString(row);
                    //System.out.println(row + "(id:" + id + "):" + eredmenyS);
                    return Integer.parseInt(eredmenyS);
            }
        }

        return 4;
    }

    void setAnything(String varibaleType, int id, String row, String newValue) throws SQLException {
        db.setAutoCommit(false);
        PreparedStatement statement = db.prepareStatement("UPDATE utasok SET " + row + " = ? WHERE id = ?;");

        switch (varibaleType.toLowerCase()) {
            case "int":
                statement.setInt(1, Integer.parseInt(newValue));
                break;
            case "string":
                statement.setString(1, newValue);
                break;
            case "boolean":
                statement.setBoolean(1, Boolean.parseBoolean(newValue));
                break;
        }
        statement.setInt(2, id);
        statement.executeUpdate();

        //System.out.print("új " + row + " (id:" + id + "):" + newValue);
    }

    void setNewIntValue(int id, String row, String amounToModify, String symbol) throws SQLException {
        db.setAutoCommit(false);
        switch (symbol){
            case "+":
                PreparedStatement statement = db.prepareStatement("UPDATE utasok SET " + row + " = "+ row +"+ ? WHERE id = ?;");
                System.out.println("hozzáadott összeg " + row + " (id:" + id + "):" + amounToModify);
                statement.setInt(1, Integer.parseInt(amounToModify));
                statement.setInt(2, id);
                statement.executeUpdate();
                break;
            case  "-":
                statement = db.prepareStatement("UPDATE utasok SET " + row + " = " + row + "- ? WHERE id = ?;");
                System.out.printf("levont összeg %s (id:%d):-%s%n", row, id, amounToModify);
                statement.setInt(1, Integer.parseInt(amounToModify));
                statement.setInt(2, id);
                statement.executeUpdate();
                break;
        }
    }
    public String NameGenerator() {
        String[] Beginning = { "Kis", "Nagy", "Kovács", "Pap", "Szabó",
                "Kovács", "Szűcs", "Barta", "Garaba", "Botos", "Kozma", "Szász", "Simon", "Pupek",
                "Pomozi", "Fülöp", "Horváth", "Balogh", "Szilágyi", "Illyés", "Németh", "Csontos", "Fekete",
                "Takács", "Détár" };
        String[] Middle = { "András", "Virág", "Gábor", "Dóri", "Balázs", "Kristóf",
                "Ádám", "Zoltán", "Anita", "Nikoletta", "Klári", "Zita", "Csilla", "Adrián", "Marci",
                "Tímea", "Dominik", "Edina", "Bianka", "Marcell" };
        Random rand = new Random();
        return Beginning[rand.nextInt(Beginning.length)] + " "+
                Middle[rand.nextInt(Middle.length)];
    }

    private static void checkSQL(PreparedStatement posted) throws SQLException {
        int rs = posted.executeUpdate();

        if (rs >= 0) {
            System.out.println("Done");
        } else {
            System.out.println("Error");
        }
    }


    void deleteDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/transportsimulation?useSSL=false";
        String username = "root";
        String password = "";
        List<String> keys = new ArrayList<>();
        keys.add("id");
        keys.add("utasNev");

        String sql = "DELETE FROM utasok where first_key = ? AND second_key = ANY(?) ";
        db = DriverManager.getConnection(url, username, password);
        PreparedStatement ps = db.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ps.setString(1,"id");
        ps.setArray(2, ps.getConnection().createArrayOf("utasNev", keys.toArray()));

        int res = ps.executeUpdate();
        System.out.println("Drop database succesful");
    }

    private static boolean randomPercent(int percentage) {
        Random rand = new Random();

        return (rand.nextInt(100) < percentage);
    }
}
