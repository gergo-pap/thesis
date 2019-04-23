package Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Database {
    private int utasKorMin;
    private int utasKorMax;
    private int utasEgyenlegMax;
    private int utasJegy;
    private int utasBerlet;

    private Connection db;
    private JSONObject jaratok;

    Database() throws ClassNotFoundException, SQLException, IOException, ParseException {
        initializeSQL();
        initializeJSON();
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

    private void initializeSQL() throws ClassNotFoundException, SQLException {
        String driver = "org.sqlite.JDBC";
        String url = "jdbc:sqlite:utasok.db";

        Class.forName(driver);
        db = DriverManager.getConnection(url);
    }

    private void initializeJSON() throws IOException, ParseException {
        InputStream json_stream = this.getClass().getResourceAsStream("/jaratok.json");
        InputStreamReader json_reader = new InputStreamReader(json_stream, StandardCharsets.UTF_8);

        this.jaratok = (JSONObject) new JSONParser().parse(json_reader);
    }

    public void loadSettings(Beallitasok beallitasok) {
        this.utasKorMin = beallitasok.getUtasKorMinTF();
        this.utasKorMax = beallitasok.getUtasKorMaxTF();
        this.utasEgyenlegMax = beallitasok.getUtasEgyenlegIgTF();
        this.utasJegy = beallitasok.getUtasJegyTF();
        this.utasBerlet = beallitasok.getUtasBerletTF();
    }

    public void refreshAllRow() throws SQLException {
        Random r = new Random();

        for (int i = 0; i <= countTableSize(); i++) {
            PreparedStatement posted = db.prepareStatement(
                    "UPDATE utasok SET "
                            + "utasNev = ?, "
                            + "utasKor = ?, "
                            + "utasEgyenleg = ?, "
                            + "utasVanEBerlete = ?, "
                            + "utasVanEJegye = ?, "
                            + "utasUtazikE = ?"
                            + "WHERE id = ?");

            randomizeUtas(r, posted);
            posted.setInt(7, i);
            checkSQL(posted);
        }
    }

    void createUtasokTable() throws SQLException {
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

    private void postUtas() throws SQLException {
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

        randomizeUtas(r, posted);
        checkSQL(posted);
    }

    private void randomizeUtas(Random r, PreparedStatement posted) throws SQLException {
        posted.setString(1, NameGenerator());
        posted.setInt(2, r.nextInt((this.utasKorMax - this.utasKorMin) + 1) + this.utasKorMin);
        posted.setInt(3, r.nextInt(this.utasEgyenlegMax));
        posted.setBoolean(4, randomPercent(this.utasBerlet));
        posted.setBoolean(5, randomPercent(this.utasJegy));
        posted.setBoolean(6, false);
    }

    void postUtasNumberOfTimes(int num) throws SQLException {
        for (int i = 0; i < num; i++) {
            postUtas();
        }
    }

    List<Allomas> getAllomasokLista(String jaratSzam) {
        JSONObject jarat = (JSONObject) this.jaratok.get(jaratSzam);

        List<Allomas> allomasok = new ArrayList<>();
        JSONObject megallo;

        for (Object megalloObj : (JSONArray) jarat.get("megallok")) {
            megallo = (JSONObject) megalloObj;
            allomasok.add(new Allomas(
                    (String) megallo.get("name"),
                    ((Long) megallo.get("x")).doubleValue(),
                    ((Long) megallo.get("y")).doubleValue()
            ));
        }

        return allomasok;
    }

    BuszInfo getBuszInfo(String jaratSzam) {
        JSONObject jarat = (JSONObject) this.jaratok.get(jaratSzam);
        JSONObject busz = (JSONObject) jarat.get("busz");

        return new BuszInfo(
                ((Long) busz.get("kapacitas")).intValue(),
                (boolean) busz.get("elso_ajtos")
        );
    }

    int countTableSize() throws SQLException {
        Statement stat = db.createStatement();
        ResultSet rs = stat.executeQuery("SELECT COUNT(*) FROM utasok");

        return rs.getInt(1);
    }

    int getAnything(String varibaleType, int id, String row) throws SQLException {
        PreparedStatement statement = db.prepareStatement("SELECT * FROM utasok where id = ?");
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        int eredmeny;

        while (result.next()) {
            switch (varibaleType.toLowerCase()) {
                case "boolean":
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
