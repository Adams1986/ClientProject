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
    private static String UP;
    private static String DOWN;
    private static String LEFT;
    private static String RIGHT;
    private static String AWAITING;

    private static int replayWidth;
    private static int replayHeight;

    private static String loginAuthentication;
    private static String loginScreen;
    private static String mainMenuScreen;
    private static String replaySnakeScreen;
    private static String playSnakeScreen;


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

            setUP((String) jsonObject.get("up"));
            setDOWN((String) jsonObject.get("down"));
            setLEFT((String) jsonObject.get("left"));
            setRIGHT((String) jsonObject.get("right"));
            setAWAITING((String) jsonObject.get("awaiting"));

            setFieldHeight(Integer.parseInt((String) jsonObject.get("fieldheight")));
            setFieldWidth(Integer.parseInt((String) jsonObject.get("fieldwidth")));
            setBoardHeight(Integer.parseInt((String) jsonObject.get("boardheight")));
            setBoardWidth(Integer.parseInt((String) jsonObject.get("boardwidth")));

            setCount(Integer.parseInt((String) jsonObject.get("count")));
            setDelay(Integer.parseInt((String) jsonObject.get("delay")));

            setBoardStartXY(Integer.parseInt((String) jsonObject.get("boardstartxy")));


            setReplayWidth(Integer.parseInt((String) jsonObject.get("replaywidth")));
            setReplayHeight(Integer.parseInt((String) jsonObject.get("replayheight")));

            setLoginAuthentication((String) jsonObject.get("loginauthentication"));
            setLoginScreen((String) jsonObject.get("loginscreen"));
            setMainMenuScreen((String) jsonObject.get("mainmenuscreen"));
            setReplaySnakeScreen((String) jsonObject.get("replaysnakescreen"));
            setPlaySnakeScreen((String) jsonObject.get("playsnakescreen"));


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
        return UP.charAt(0);
    }

    public static void setUP(String UP) {
        Config.UP = UP;
    }

    public static char getDOWN() {
        return DOWN.charAt(0);
    }

    public static void setDOWN(String DOWN) {
        Config.DOWN = DOWN;
    }

    public static char getLEFT() {
        return LEFT.charAt(0);
    }

    public static void setLEFT(String LEFT) {
        Config.LEFT = LEFT;
    }

    public static char getRIGHT() {
        return RIGHT.charAt(0);
    }

    public static void setRIGHT(String RIGHT) {
        Config.RIGHT = RIGHT;
    }

    public static char getAwaiting() {
        return AWAITING.charAt(0);
    }

    public static void setAWAITING(String AWAITING) {
        Config.AWAITING = AWAITING;
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

    public static String getLoginAuthentication() {
        return loginAuthentication;
    }

    public static void setLoginAuthentication(String loginAuthentication) {
        Config.loginAuthentication = loginAuthentication;
    }

    public static String getLoginScreen() {
        return loginScreen;
    }

    public static void setLoginScreen(String loginScreen) {
        Config.loginScreen = loginScreen;
    }

    public static String getMainMenuScreen() {
        return mainMenuScreen;
    }

    public static void setMainMenuScreen(String mainMenuScreen) {
        Config.mainMenuScreen = mainMenuScreen;
    }

    public static String getReplaySnakeScreen() {
        return replaySnakeScreen;
    }

    public static void setReplaySnakeScreen(String replaySnakeScreen) {
        Config.replaySnakeScreen = replaySnakeScreen;
    }

    public static String getPlaySnakeScreen() {
        return playSnakeScreen;
    }

    public static void setPlaySnakeScreen(String playSnakeScreen) {
        Config.playSnakeScreen = playSnakeScreen;
    }
}
