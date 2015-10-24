package sdk;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

public class Config {

    private static String host;
    private static String port;
    private static String username;
    private static String password;
    private static String dbname;
    private static String hashingSalt;
    private static String encryptionkey;

    private static int FIELD_HEIGHT;
    private static int FIELD_WIDTH;
    private static int BOARD_HEIGHT;
    private static int BOARD_WIDTH;

    private static int count;
    private static int delay;
    private static int boardStartXY;

    //TODO: is this neccesary? when using in switch values have to be static. Seems like a stupid workaround?
    private static char UP;
    private static char DOWN;
    private static char LEFT;
    private static char RIGHT;

    private static int replayWidth;
    private static int replayHeight;


    public static void init () {

        JSONParser jsonParser = new JSONParser();

        try {

            //Initialize imported Java-class FileReader as json object
            //with the specific path to the .json file.
            FileReader json = new FileReader("src/config.json");

            //Initialize Object class as json, parsed by jsonParsed.
            Object obj = jsonParser.parse(json);

            //Instantiate JSONObject class as jsonObject equal to obj object.
            JSONObject jsonObject = (JSONObject) obj;

            //Use set-methods for defining static variables from json-file.
            setHost((String) jsonObject.get("host"));
            setPort((String) jsonObject.get("port"));
            setUsername((String) jsonObject.get("username"));

            //setUsername("root");

            setDbname((String) jsonObject.get("dbname"));

            setPassword((String) jsonObject.get("password"));
            setEncryptionkey((String) jsonObject.get("encryptionkey"));
            setHashingSalt((String) jsonObject.get("hashingSalt"));

//            setUP((char)Integer.parseInt((String) jsonObject.get("up")));
//            setDOWN((char) Integer.parseInt((String) jsonObject.get("down")));
//            setLEFT((char) Integer.parseInt((String) jsonObject.get("left")));
//            setRIGHT((char) Integer.parseInt((String) jsonObject.get("right")));

            setFieldHeight(Integer.parseInt((String) jsonObject.get("fieldheight")));
            setFieldWidth(Integer.parseInt((String) jsonObject.get("fieldwidth")));
            setBoardHeight(Integer.parseInt((String) jsonObject.get("boardheight")));
            setBoardWidth(Integer.parseInt((String) jsonObject.get("boardwidth")));

            setCount(Integer.parseInt((String) jsonObject.get("count")));
            setDelay(Integer.parseInt((String) jsonObject.get("delay")));

            setBoardStartXY(Integer.parseInt((String) jsonObject.get("boardstartxy")));


            setReplayWidth(Integer.parseInt((String) jsonObject.get("replaywidth")));
            setReplayHeight(Integer.parseInt((String) jsonObject.get("replayheight")));


        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Config.host = host;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        Config.port = port;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Config.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Config.password = password;
    }

    public static String getDbname() {
        return dbname;
    }

    public static void setDbname(String dbname) {
        Config.dbname = dbname;
    }

    public static String getHashingSalt() {
        return hashingSalt;
    }

    public static void setHashingSalt(String hashingSalt) {
        Config.hashingSalt = hashingSalt;
    }

    public static String getEncryptionkey() {
        return encryptionkey;
    }

    public static void setEncryptionkey(String encryptionkey) {
        Config.encryptionkey = encryptionkey;
    }

    public static final char getUP() {
        return UP;
    }

    public static void setUP(char UP) {
        Config.UP = UP;
    }

    public static char getDOWN() {
        return DOWN;
    }

    public static void setDOWN(char DOWN) {
        Config.DOWN = DOWN;
    }

    public static char getLEFT() {
        return LEFT;
    }

    public static void setLEFT(char LEFT) {
        Config.LEFT = LEFT;
    }

    public static char getRIGHT() {
        return RIGHT;
    }

    public static void setRIGHT(char RIGHT) {
        Config.RIGHT = RIGHT;
    }

    public static int getFieldHeight() {
        return FIELD_HEIGHT;
    }

    public static void setFieldHeight(int fieldHeight) {
        FIELD_HEIGHT = fieldHeight;
    }

    public static int getFieldWidth() {
        return FIELD_WIDTH;
    }

    public static void setFieldWidth(int fieldWidth) {
        FIELD_WIDTH = fieldWidth;
    }

    public static int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    public static void setBoardHeight(int boardHeight) {
        BOARD_HEIGHT = boardHeight;
    }

    public static int getBoardWidth() {
        return BOARD_WIDTH;
    }

    public static void setBoardWidth(int boardWidth) {
        BOARD_WIDTH = boardWidth;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Config.count = count;
    }

    public static int getDelay() {
        return delay;
    }

    public static void setDelay(int delay) {
        Config.delay = delay;
    }

    public static int getBoardStartXY() {
        return boardStartXY;
    }

    public static void setBoardStartXY(int boardStartXY) {
        Config.boardStartXY = boardStartXY;
    }

    public static int getReplayWidth() {
        return replayWidth;
    }

    public static void setReplayWidth(int replayWidth) {
        Config.replayWidth = replayWidth;
    }

    public static int getReplayHeight() {
        return replayHeight;
    }

    public static void setReplayHeight(int replayHeight) {
        Config.replayHeight = replayHeight;
    }
}
