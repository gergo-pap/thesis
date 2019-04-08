package Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBase {

    private Connection db;

    DataBase() throws ClassNotFoundException, SQLException {
        String driver = "org.sqlite.JDBC";
        String url = "jdbc:sqlite:test.db";

        Class.forName(driver);
        db = DriverManager.getConnection(url);
    }

    private static void checkSQL(PreparedStatement posted) throws SQLException {
        int rs = posted.executeUpdate();

        if (rs >= 0) {
            System.out.println("Done");
        } else {
            System.out.println("Error");
        }
    }

    private static boolean randomPercent(int percentage) {
        Random rand = new Random();
        return (rand.nextInt(100) < percentage);
    }

    public void createUtasokTable() throws SQLException {
        PreparedStatement create = db.prepareStatement(
                "CREATE TABLE IF NOT EXISTS utasok("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT   , "
                        + "utasNev          text            , "
                        + "utasKor          int             , "
                        + "utasEgyenleg 	int     	, "
                        + "utasVanEBerlete 	tinyint(1)	, "
                        + "utasVanEJegye 	tinyint(1)	, "
                        + "utasUtazikE 	tinyint(1))");
        checkSQL(create);
    }

    public void postUtas() throws SQLException {
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

    public List<Allomas> getAllomasokLista(String jaratSzam) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jaratok = (JSONObject) parser.parse(new FileReader("jaratok.json"));
        JSONArray jarat34 = (JSONArray) jaratok.get(jaratSzam);

        List<Allomas> allomasok = new ArrayList<>();
        JSONObject megallo;

        for (Object megalloObj : jarat34) {
            megallo = (JSONObject) megalloObj;
            allomasok.add(new Allomas(
                    (String) megallo.get("name"),
                    ((Long) megallo.get("x")).doubleValue(),
                    ((Long) megallo.get("y")).doubleValue()
            ));
        }

        return allomasok;
    }

    int countTableSize(String tableName) throws SQLException {
        PreparedStatement create = db.prepareStatement("SELECT COUNT(*) FROM " + tableName);
        ResultSet result = create.executeQuery();
        if (result.next()) {
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
                    return eredmeny;
                case "int":
                    eredmeny = result.getInt(row);
                    return eredmeny;
                case "string":
                    String eredmenyS = result.getString(row);
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

    }

    void setNewIntValue(int id, String row, String amounToModify, String symbol) throws SQLException {
        db.setAutoCommit(false);
        switch (symbol) {
            case "+":
                PreparedStatement statement = db.prepareStatement("UPDATE utasok SET " + row + " = " + row + "+ ? WHERE id = ?;");
                System.out.println("hozzáadott összeg " + row + " (id:" + id + "):" + amounToModify);
                statement.setInt(1, Integer.parseInt(amounToModify));
                statement.setInt(2, id);
                statement.executeUpdate();
                break;
            case "-":
                statement = db.prepareStatement("UPDATE utasok SET " + row + " = " + row + "- ? WHERE id = ?;");
                System.out.printf("levont összeg %s (id:%d):-%s%n", row, id, amounToModify);
                statement.setInt(1, Integer.parseInt(amounToModify));
                statement.setInt(2, id);
                statement.executeUpdate();
                break;
        }
    }

    private String NameGenerator() {
        String[] Beginning = {"Kis", "Nagy", "Kovács", "Pap", "Szabó",
                "Kovács", "Szűcs", "Barta", "Garaba", "Botos", "Kozma", "Szász", "Simon", "Pupek",
                "Pomozi", "Fülöp", "Horváth", "Balogh", "Szilágyi", "Illyés", "Németh", "Csontos", "Fekete",
                "Takács", "Détár", "Cinkóczi"};
        String[] Middle = {"András", "Virág", "Gábor", "Dóri", "Balázs", "Kristóf",
                "Ádám", "Zoltán", "Anita", "Nikoletta", "Klári", "Zita", "Csilla", "Adrián", "Marci",
                "Tímea", "Dominik", "Edina", "Bianka", "Marcell"};
        Random rand = new Random();
        return Beginning[rand.nextInt(Beginning.length)] + " " +
                Middle[rand.nextInt(Middle.length)];
    }

}
