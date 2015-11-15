package sdk;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;

public class Config {

    private static String hashingSalt;
    private static String encryptionkey;

    private static int fieldHeight;
    private static int fieldWidth;
    private static int boardHeight;
    private static int boardWidth;

    private static int count;
    private static int delay;
    private static int zeroXY;

    //TODO: is this neccesary? when using in switch values have to be static. Seems like a stupid workaround?
    private static String up;
    private static String down;
    private static String left;
    private static String right;
    private static String awaiting;

    private static int replayWidth;
    private static int replayHeight;

    private static String loginAuthentication;
    private static String loginScreen;
    private static String mainMenuScreen;
    private static String replaySnakeScreen;
    private static String playSnakeScreen;
    private static String createNewGameScreen;

    private static String logoutMessage;
    private static String logoutTitle;
    private static String createUserScreen;
    private static String ipAddress;
    private static int serverPort;
    private static int appHeight;
    private static int appWidth;

    private static String [] columnNamesGameTable = new String[7];
    private static String [] columnNamesUserTable = new String[3];
    private static String gameChooserScreen;
    private static String serverPathUsers;
    private static String serverPathLogin;
    private static String serverPathGames;
    private static String serverPathGame;
    private static String serverPathScores;
    private static String btnPlayText;
    private static String btnSendText;
    private static String lblOpenGameText;
    private static String gameNameFieldTtt;
    private static int[] mapSizes = new int[3];

    private static int defaultXPosJComponent;
    private static int defaultWidthJComponent;
    private static int defaultHeightJComponent;
    private static int y1PosJComponent;
    private static int y6PosJComponent;
    private static int y7PosJComponent;
    private static int y8PosJComponent;
    private static int y10PosJComponent;
    private static int y9PosJComponent;
    private static int y5PosJComponent;
    private static int y3PosJComponent;
    private static int lblWidth;
    private static int y4PosJComponent;
    private static int x1PosJComponent;
    private static int width1JComponent;

    private static String infoLabelText;
    private static String firstNameLabelText;
    private static String lastNameLabelText;
    private static String emailLabelText;
    private static String usernameLabelText;
    private static String passwordLabelText;
    private static String btnCreateUserText;
    private static String btnBackToLoginText;

    private static String headerFont;
    private static int headerTextSize;

    private static String clearField;
    private static int width2JComponent;
    private static int y2PosJComponent;
    private static int indexOne;
    private static String appName;
    private static String btnJoinSelectedGameText;
    private static String btnCreateNewGameText;
    private static int y11PosJComponent;
    private static int depardieuWidth;
    private static int depardieuHeight;
    private static int x2PosJComponent;
    private static int width3JComponent;
    private static String loginInfoLabelText;
    private static String btnLoginText;
    private static String btnCreateNewUserText;
    private static String depardieuImagePath;
    private static int appBorderSize;
    private static String btnWatchReplayText;
    private static String btnShowHighScoreText;
    private static String btnDeleteGameText;
    private static String btnLogoutText;
    private static String missingGameNameText;
    private static String missingOpponentText;
    private static String missingGameSelectionText;
    private static String confirmedUserCreationText;
    private static String serverPathGamesInvitedById;
    private static String serverPathGamesHostedById;
    private static String serverPathOpenGames;
    private static String serverPathStartGames;
    private static String serverPathJoinGames;
    private static int moveOne;
    private static int numberForSnakePlacement;
    private static int keyStrokeModifier;
    private static String btnRefreshText;
    private static String btnDeleteText;
    private static String deleteGameScreen;
    private static String serverPathDeleteGames;
    private static String mapSizeChooserTtt;
    private static String lblCreateNewGameText;
    private static String gameChooserHeaderText;
    private static String[] typesOfGames = new String[2];
    private static String gameOverviewerHeaderText;
    private static String btnReplayText;
    private static String[] typesOfGamesToReplay = new String[6];
    private static String gameOverviewerScreen;
    private static int indexTwo;
    private static int indexThree;
    private static int indexFour;
    private static int indexFive;
    private static int indexSix;
    private static int indexSeven;


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

            setIpAddress((String) jsonObject.get("ipaddress"));
            setServerPort((int) (long) jsonObject.get("serverport"));

            setEncryptionkey((String) jsonObject.get("encryptionkey"));
            setHashingSalt((String) jsonObject.get("hashingSalt"));

            setUp((String) jsonObject.get("up"));
            setDown((String) jsonObject.get("down"));
            setLeft((String) jsonObject.get("left"));
            setRight((String) jsonObject.get("right"));
            setAwaiting((String) jsonObject.get("awaiting"));

            setAppHeight((int) (long) jsonObject.get("appheight"));
            setAppWidth((int) (long) jsonObject.get("appwidth"));
            setAppName((String) jsonObject.get("appname"));
            setAppBorderSize((int)(long) jsonObject.get("appbordersize"));
            setFieldHeight((int) (long) jsonObject.get("fieldheight"));
            setFieldWidth((int) (long) jsonObject.get("fieldwidth"));
            setBoardHeight((int) (long) jsonObject.get("boardheight"));
            setBoardWidth((int) (long) jsonObject.get("boardwidth"));

            setCount((int) (long) jsonObject.get("count"));
            setDelay((int) (long) jsonObject.get("delay"));

            setZeroXY((int) (long) jsonObject.get("zeroxy"));

            setReplayWidth((int) (long) jsonObject.get("replaywidth"));
            setReplayHeight((int) (long) jsonObject.get("replayheight"));

            setLoginAuthentication((String) jsonObject.get("loginauthentication"));
            setLoginScreen((String) jsonObject.get("loginscreen"));
            setMainMenuScreen((String) jsonObject.get("mainmenuscreen"));
            setCreateUserScreen((String) jsonObject.get("createuserscreen"));
            setReplaySnakeScreen((String) jsonObject.get("replaysnakescreen"));
            setPlaySnakeScreen((String) jsonObject.get("playsnakescreen"));
            setCreateNewGameScreen((String) jsonObject.get("gamewizardscreen"));
            setGameChooserScreen((String) jsonObject.get("createnewgamescreen"));
            setGameOverviewerScreen((String) jsonObject.get("gameoverviewerscreen"));

            setLogoutMessage((String) jsonObject.get("logoutmessage"));
            setLogoutTitle((String) jsonObject.get("logouttitle"));

            getColumnNamesUserTable()[0] = (String) jsonObject.get("firstname");
            getColumnNamesUserTable()[1] = (String) jsonObject.get("lastname");
            getColumnNamesUserTable()[2] = (String) jsonObject.get("username");

            getColumnNamesGameTable()[0] = (String) jsonObject.get("challenger");
            getColumnNamesGameTable()[1] = (String) jsonObject.get("opponent");
            getColumnNamesGameTable()[2] = (String) jsonObject.get("gamename");
            getColumnNamesGameTable()[3] = (String) jsonObject.get("gamestatus");
            getColumnNamesGameTable()[4] = (String) jsonObject.get("created");
            getColumnNamesGameTable()[5] = (String) jsonObject.get("winner");
            getColumnNamesGameTable()[6] = (String) jsonObject.get("mapsize");

            getMapSizes()[0] = (int)(long) jsonObject.get("smallmap");
            getMapSizes()[1] = (int)(long) jsonObject.get("mediummap");
            getMapSizes()[2] = (int)(long) jsonObject.get("largemap");

            getTypesOfGames()[0] = (String) jsonObject.get("invitedgamestext");
            getTypesOfGames()[1] = (String) jsonObject.get("opengamestext");

            getTypesOfGamesToReplay()[0] = (String) jsonObject.get("invitedgamestext");
            getTypesOfGamesToReplay()[1] = (String) jsonObject.get("opengamestext");
            getTypesOfGamesToReplay()[2] = (String) jsonObject.get("pendinggamestext");
            getTypesOfGamesToReplay()[3] = (String) jsonObject.get("hostedgamestext");
            getTypesOfGamesToReplay()[4] = (String) jsonObject.get("openbyidgamestext");
            getTypesOfGamesToReplay()[5] = (String) jsonObject.get("completedgamestext");

            setServerPathLogin((String) jsonObject.get("serverpathlogin"));
            setServerPathUsers((String) jsonObject.get("serverpathusers"));
            setServerPathGames((String) jsonObject.get("serverpathgames"));
            setServerPathGame((String) jsonObject.get("serverpathgame"));
            setServerPathScores((String) jsonObject.get("serverpathscores"));

            setBtnPlayText((String) jsonObject.get("btnplaytext"));
            setBtnSendText((String) jsonObject.get("btnsendtext"));
            setLblOpenGameText((String) jsonObject.get("lblopengametext"));
            setLblCreateNewGameText((String) jsonObject.get("lblcreatenewgametext"));
            setGameNameFieldTtt((String) jsonObject.get("gamenamefieldttt"));
            setMapSizeChooserTtt((String) jsonObject.get("mapsizechooserttt"));

            setDefaultXPosJComponent((int)(long) jsonObject.get("defaultxpositionjcomponent"));
            setDefaultWidthJComponent((int)(long) jsonObject.get("defaultwidthjcomponent"));
            setDefaultHeightJComponent((int)(long) jsonObject.get("defaultheightjcomponent"));
            setLblWidth((int)(long) jsonObject.get("labelwidth"));

            setWidth1JComponent((int)(long) jsonObject.get("width1jcomponent"));
            setWidth2JComponent((int)(long) jsonObject.get("width2jcomponent"));
            setWidth3JComponent((int)(long) jsonObject.get("width3jcomponent"));

            setX1PosJComponent((int)(long) jsonObject.get("x1positionjcomponent"));
            setX2PosJComponent((int)(long) jsonObject.get("x2positionjcomponent"));

            setY1PosJComponent((int)(long) jsonObject.get("y1positionjcomponent"));
            setY2PosJComponent((int)(long) jsonObject.get("y2positionjcomponent"));
            setY3PosJComponent((int)(long) jsonObject.get("y3positionjcomponent"));
            setY4PosJComponent((int)(long) jsonObject.get("y4positionjcomponent"));
            setY5PosJComponent((int)(long) jsonObject.get("y5positionjcomponent"));
            setY6PosJComponent((int)(long) jsonObject.get("y6positionjcomponent"));
            setY7PosJComponent((int)(long) jsonObject.get("y7positionjcomponent"));
            setY8PosJComponent((int)(long) jsonObject.get("y8positionjcomponent"));
            setY9PosJComponent((int)(long) jsonObject.get("y9positionjcomponent"));
            setY10PosJComponent((int)(long) jsonObject.get("y10positionjcomponent"));
            setY11PosJComponent((int)(long) jsonObject.get("y11positionjcomponent"));

            setInfoLabelText((String) jsonObject.get("infolabeltext"));
            setFirstNameLabelText((String) jsonObject.get("firstnamelabeltext"));
            setLastNameLabelText((String) jsonObject.get("lastnamelabeltext"));
            setEmailLabelText((String) jsonObject.get("emaillabeltext"));
            setUsernameLabelText((String) jsonObject.get("usernamelabeltext"));
            setPasswordLabelText((String) jsonObject.get("passwordlabeltext"));
            setBtnCreateUserText((String) jsonObject.get("btncreateusertext"));
            setBtnBackToLoginText((String) jsonObject.get("btnbacktologintext"));
            setHeaderFont((String) jsonObject.get("headerfont"));
            setHeaderTextSize((int)(long) jsonObject.get("headertextsize"));
            setClearField((String) jsonObject.get("clearfield"));
            setIndexOne((int)(long) jsonObject.get("indexone"));
            setIndexTwo((int) (long) jsonObject.get("indextwo"));
            setIndexThree((int) (long) jsonObject.get("indexthree"));
            setIndexFour((int) (long) jsonObject.get("indexfour"));
            setIndexFive((int) (long) jsonObject.get("indexfive"));
            setIndexSix((int) (long) jsonObject.get("indexsix"));
            setIndexSeven((int) (long) jsonObject.get("indexseven"));
            setBtnJoinSelectedGameText((String) jsonObject.get("btnjoinselectedgametext"));
            setBtnCreateNewGameText((String) jsonObject.get("btncreatenewgametext"));
            setDepardieuWidth((int)(long) jsonObject.get("depardieuwidth"));
            setDepardieuHeight((int)(long) jsonObject.get("depardieuheight"));
            setLoginInfoLabelText((String) jsonObject.get("logininfolabeltext"));
            setBtnLoginText((String) jsonObject.get("btnlogintext"));
            setBtnCreateNewUserText((String) jsonObject.get("btncreatenewusertext"));
            setDepardieuImagePath((String) jsonObject.get("depardieuimagepath"));
            setBtnWatchReplayText((String) jsonObject.get("btnwatchreplaytext"));
            setBtnShowHighScoreText((String) jsonObject.get("btnshowhighscoretext"));
            setBtnDeleteGameText((String) jsonObject.get("btndeletegametext"));
            setBtnLogoutText((String) jsonObject.get("btnlogouttext"));
            setBtnRefreshText(((String) jsonObject.get("btnrefreshtext")));

            setMissingGameNameText((String) jsonObject.get("missinggamenametext"));
            setMissingOpponentText((String) jsonObject.get("missingopponenttext"));
            setMissingGameSelectionText((String) jsonObject.get("missinggameselectiontext"));

            setConfirmedUserCreationText((String) jsonObject.get("confirmedusercreationtext"));

            setServerPathJoinGames((String) jsonObject.get("serverpathjoingames"));
            setServerPathStartGames((String) jsonObject.get("serverpathstartgames"));
            setServerPathGamesInvitedById((String) jsonObject.get("serverpathgamesinvitedbyid"));
            setServerPathGamesHostedById((String) jsonObject.get("serverpathgameshostedbyid"));
            setServerPathOpenGames((String) jsonObject.get("serverpathopengames"));
            setServerPathDeleteGames((String) jsonObject.get("serverpathdeletegames"));

            setMoveOne((int)(long) jsonObject.get("moveone"));
            setNumberForSnakePlacement((int)(long) jsonObject.get("numberforsnakeplacement"));
            setKeyStrokeModifier((int)(long) jsonObject.get("keystrokemodifier"));

            setBtnDeleteText((String) jsonObject.get("delete"));
            setDeleteGameScreen((String) jsonObject.get("deletegamescreen"));
            setGameChooserHeaderText((String) jsonObject.get("gamechooserheadertext"));
            setGameOverviewerHeaderText((String) jsonObject.get("gameoverviewerheadertext"));
            setBtnReplayText((String) jsonObject.get("btnreplaytext"));


        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static char getUp() {
        return up.charAt(Config.getIndexOne());
    }

    public static void setUp(String up) {
        Config.up = up;
    }

    public static char getDown() {
        return down.charAt(Config.getIndexOne());
    }

    public static void setDown(String down) {
        Config.down = down;
    }

    public static char getLeft() {
        return left.charAt(Config.getIndexOne());
    }

    public static void setLeft(String left) {
        Config.left = left;
    }

    public static char getRight() {
        return right.charAt(Config.getIndexOne());
    }

    public static void setRight(String right) {
        Config.right = right;
    }

    public static char getAwaiting() {
        return awaiting.charAt(Config.getIndexOne());
    }

    public static void setAwaiting(String awaiting) {
        Config.awaiting = awaiting;
    }

    public static int getFieldHeight() {
        return fieldHeight;
    }

    public static void setFieldHeight(int fieldHeight) {
        Config.fieldHeight = fieldHeight;
    }

    public static int getFieldWidth() {
        return fieldWidth;
    }

    public static void setFieldWidth(int fieldWidth) {
        Config.fieldWidth = fieldWidth;
    }

    public static int getBoardHeight() {
        return boardHeight;
    }

    public static void setBoardHeight(int boardHeight) {
        Config.boardHeight = boardHeight;
    }

    public static int getBoardWidth() {
        return boardWidth;
    }

    public static void setBoardWidth(int boardWidth) {
        Config.boardWidth = boardWidth;
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

    public static int getZeroXY() {
        return zeroXY;
    }

    public static void setZeroXY(int zeroXY) {
        Config.zeroXY = zeroXY;
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

    public static String getCreateUserScreen() {
        return createUserScreen;
    }

    public static void setCreateUserScreen(String createUserScreen) {
        Config.createUserScreen = createUserScreen;
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

    public static String getCreateNewGameScreen() {
        return createNewGameScreen;
    }

    public static void setCreateNewGameScreen(String createNewGameScreen) {
        Config.createNewGameScreen = createNewGameScreen;
    }

    public static String getLogoutMessage() {
        return logoutMessage;
    }

    public static void setLogoutMessage(String logoutMessage) {
        Config.logoutMessage = logoutMessage;
    }

    public static String getLogoutTitle() {
        return logoutTitle;
    }

    public static void setLogoutTitle(String logoutTitle) {
        Config.logoutTitle = logoutTitle;
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static void setIpAddress(String ipAddress) {
        Config.ipAddress = ipAddress;
    }

    public static int getAppHeight() {
        return appHeight;
    }

    public static void setAppHeight(int appHeight) {
        Config.appHeight = appHeight;
    }

    public static int getAppWidth() {
        return appWidth;
    }

    public static void setAppWidth(int appWidth) {
        Config.appWidth = appWidth;
    }

    public static String[] getColumnNamesGameTable() {
        return columnNamesGameTable;
    }

    public static void setColumnNamesGameTable(String[] columnNamesGameTable) {
        Config.columnNamesGameTable = columnNamesGameTable;
    }

    public static String[] getColumnNamesUserTable() {
        return columnNamesUserTable;
    }

    public static void setColumnNamesUserTable(String[] columnNamesUserTable) {
        Config.columnNamesUserTable = columnNamesUserTable;
    }

    public static String getGameChooserScreen() {
        return gameChooserScreen;
    }

    public static void setGameChooserScreen(String gameChooserScreen) {
        Config.gameChooserScreen = gameChooserScreen;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        Config.serverPort = serverPort;
    }

    public static String getServerPathUsers() {
        return serverPathUsers;
    }

    public static void setServerPathUsers(String serverPathUsers) {
        Config.serverPathUsers = serverPathUsers;
    }

    public static String getServerPathLogin() {
        return serverPathLogin;
    }

    public static void setServerPathLogin(String serverPathLogin) {
        Config.serverPathLogin = serverPathLogin;
    }

    public static String getServerPathGames() {
        return serverPathGames;
    }

    public static void setServerPathGames(String serverPathGames) {
        Config.serverPathGames = serverPathGames;
    }

    public static String getServerPathGame() {
        return serverPathGame;
    }

    public static void setServerPathGame(String serverPathGame) {
        Config.serverPathGame = serverPathGame;
    }

    public static String getServerPathScores() {
        return serverPathScores;
    }

    public static void setServerPathScores(String serverPathScores) {
        Config.serverPathScores = serverPathScores;
    }

    public static String getBtnPlayText() {
        return btnPlayText;
    }

    public static void setBtnPlayText(String btnPlayText) {
        Config.btnPlayText = btnPlayText;
    }

    public static String getBtnSendText() {
        return btnSendText;
    }

    public static void setBtnSendText(String btnSendText) {
        Config.btnSendText = btnSendText;
    }

    public static String getLblOpenGameText() {
        return lblOpenGameText;
    }

    public static void setLblOpenGameText(String lblOpenGameText) {
        Config.lblOpenGameText = lblOpenGameText;
    }

    public static String getGameNameFieldTtt() {
        return gameNameFieldTtt;
    }

    public static void setGameNameFieldTtt(String gameNameFieldTtt) {
        Config.gameNameFieldTtt = gameNameFieldTtt;
    }

    public static void setMapSizes(int[] mapSizes) {
        Config.mapSizes = mapSizes;
    }

    public static int[] getMapSizes() {
        return mapSizes;
    }

    public static void setDefaultXPosJComponent(int xPosJComponent) {
        Config.defaultXPosJComponent = xPosJComponent;
    }

    public static int getDefaultXPosJComponent() {
        return defaultXPosJComponent;
    }

    public static int getDefaultWidthJComponent() {
        return defaultWidthJComponent;
    }

    public static void setDefaultWidthJComponent(int defaultWidthJComponent) {
        Config.defaultWidthJComponent = defaultWidthJComponent;
    }

    public static int getDefaultHeightJComponent() {
        return defaultHeightJComponent;
    }

    public static void setDefaultHeightJComponent(int defaultHeightJComponent) {
        Config.defaultHeightJComponent = defaultHeightJComponent;
    }

    public static int getY1PosJComponent() {
        return y1PosJComponent;
    }

    public static void setY1PosJComponent(int y1PosJComponent) {
        Config.y1PosJComponent = y1PosJComponent;
    }

    public static int getY6PosJComponent() {
        return y6PosJComponent;
    }

    public static void setY6PosJComponent(int y6PosJComponent) {
        Config.y6PosJComponent = y6PosJComponent;
    }

    public static int getY7PosJComponent() {
        return y7PosJComponent;
    }

    public static void setY7PosJComponent(int y7PosJComponent) {
        Config.y7PosJComponent = y7PosJComponent;
    }

    public static int getY8PosJComponent() {
        return y8PosJComponent;
    }

    public static void setY8PosJComponent(int y8PosJComponent) {
        Config.y8PosJComponent = y8PosJComponent;
    }

    public static int getY10PosJComponent() {
        return y10PosJComponent;
    }

    public static void setY10PosJComponent(int y10PosJComponent) {
        Config.y10PosJComponent = y10PosJComponent;
    }

    public static int getY9PosJComponent() {
        return y9PosJComponent;
    }

    public static void setY9PosJComponent(int y9PosJComponent) {
        Config.y9PosJComponent = y9PosJComponent;
    }

    public static int getY5PosJComponent() {
        return y5PosJComponent;
    }

    public static void setY5PosJComponent(int y5PosJComponent) {
        Config.y5PosJComponent = y5PosJComponent;
    }

    public static int getY3PosJComponent() {
        return y3PosJComponent;
    }

    public static void setY3PosJComponent(int y3PosJComponent) {
        Config.y3PosJComponent = y3PosJComponent;
    }

    public static int getLblWidth() {
        return lblWidth;
    }

    public static void setLblWidth(int lblWidth) {
        Config.lblWidth = lblWidth;
    }

    public static int getY4PosJComponent() {
        return y4PosJComponent;
    }

    public static void setY4PosJComponent(int y4PosJComponent) {
        Config.y4PosJComponent = y4PosJComponent;
    }

    public static int getX1PosJComponent() {
        return x1PosJComponent;
    }

    public static void setX1PosJComponent(int x1PosJComponent) {
        Config.x1PosJComponent = x1PosJComponent;
    }

    public static int getWidth1JComponent() {
        return width1JComponent;
    }

    public static void setWidth1JComponent(int width1JComponent) {
        Config.width1JComponent = width1JComponent;
    }

    public static String getInfoLabelText() {
        return infoLabelText;
    }

    public static void setInfoLabelText(String infoLabelText) {
        Config.infoLabelText = infoLabelText;
    }


    public static String getFirstNameLabelText() {
        return firstNameLabelText;
    }

    public static void setFirstNameLabelText(String firstNameLabelText) {
        Config.firstNameLabelText = firstNameLabelText;
    }

    public static String getLastNameLabelText() {
        return lastNameLabelText;
    }

    public static void setLastNameLabelText(String lastNameLabelText) {
        Config.lastNameLabelText = lastNameLabelText;
    }


    public static String getEmailLabelText() {
        return emailLabelText;
    }

    public static void setEmailLabelText(String emailLabelText) {
        Config.emailLabelText = emailLabelText;
    }

    public static String getUsernameLabelText() {
        return usernameLabelText;
    }

    public static void setUsernameLabelText(String usernameLabelText) {
        Config.usernameLabelText = usernameLabelText;
    }


    public static String getPasswordLabelText() {
        return passwordLabelText;
    }

    public static void setPasswordLabelText(String passwordLabelText) {
        Config.passwordLabelText = passwordLabelText;
    }

    public static String getBtnCreateUserText() {
        return btnCreateUserText;
    }

    public static void setBtnCreateUserText(String btnCreateUserText) {
        Config.btnCreateUserText = btnCreateUserText;
    }

    public static String getBtnBackToLoginText() {
        return btnBackToLoginText;
    }

    public static void setBtnBackToLoginText(String btnBackToLoginText) {
        Config.btnBackToLoginText = btnBackToLoginText;
    }

    public static String getHeaderFont() {
        return headerFont;
    }

    public static void setHeaderFont(String headerFont) {
        Config.headerFont = headerFont;
    }

    public static int getHeaderTextSize() {
        return headerTextSize;
    }

    public static void setHeaderTextSize(int headerTextSize) {
        Config.headerTextSize = headerTextSize;
    }

    public static String getClearField() {
        return clearField;
    }

    public static void setClearField(String clearField) {
        Config.clearField = clearField;
    }

    public static int getWidth2JComponent() {
        return width2JComponent;
    }

    public static void setWidth2JComponent(int width2JComponent) {
        Config.width2JComponent = width2JComponent;
    }

    public static int getY2PosJComponent() {
        return y2PosJComponent;
    }

    public static void setY2PosJComponent(int y2PosJComponent) {
        Config.y2PosJComponent = y2PosJComponent;
    }

    public static int getIndexOne() {
        return indexOne;
    }

    public static void setIndexOne(int indexOne) {
        Config.indexOne = indexOne;
    }

    public static String getAppName() {
        return appName;
    }

    public static void setAppName(String appName) {
        Config.appName = appName;
    }


    public static String getBtnJoinSelectedGameText() {
        return btnJoinSelectedGameText;
    }

    public static void setBtnJoinSelectedGameText(String btnJoinSelectedGameText) {
        Config.btnJoinSelectedGameText = btnJoinSelectedGameText;
    }

    public static String getBtnCreateNewGameText() {
        return btnCreateNewGameText;
    }

    public static void setBtnCreateNewGameText(String btnCreateNewGameText) {
        Config.btnCreateNewGameText = btnCreateNewGameText;
    }

    public static int getY11PosJComponent() {
        return y11PosJComponent;
    }

    public static void setY11PosJComponent(int y11PosJComponent) {
        Config.y11PosJComponent = y11PosJComponent;
    }

    public static int getDepardieuWidth() {
        return depardieuWidth;
    }

    public static void setDepardieuWidth(int depardieuWidth) {
        Config.depardieuWidth = depardieuWidth;
    }

    public static int getDepardieuHeight() {
        return depardieuHeight;
    }

    public static void setDepardieuHeight(int depardieuHeight) {
        Config.depardieuHeight = depardieuHeight;
    }

    public static int getX2PosJComponent() {
        return x2PosJComponent;
    }

    public static void setX2PosJComponent(int x2PosJComponent) {
        Config.x2PosJComponent = x2PosJComponent;
    }

    public static int getWidth3JComponent() {
        return width3JComponent;
    }

    public static void setWidth3JComponent(int width3JComponent) {
        Config.width3JComponent = width3JComponent;
    }

    public static String getLoginInfoLabelText() {
        return loginInfoLabelText;
    }

    public static void setLoginInfoLabelText(String loginInfoLabelText) {
        Config.loginInfoLabelText = loginInfoLabelText;
    }

    public static String getBtnLoginText() {
        return btnLoginText;
    }

    public static void setBtnLoginText(String btnLoginText) {
        Config.btnLoginText = btnLoginText;
    }

    public static String getBtnCreateNewUserText() {
        return btnCreateNewUserText;
    }

    public static void setBtnCreateNewUserText(String btnCreateNewUserText) {
        Config.btnCreateNewUserText = btnCreateNewUserText;
    }

    public static String getDepardieuImagePath() {
        return depardieuImagePath;
    }

    public static void setDepardieuImagePath(String depardieuImagePath) {
        Config.depardieuImagePath = depardieuImagePath;
    }

    public static int getAppBorderSize() {
        return appBorderSize;
    }

    public static void setAppBorderSize(int appBorderSize) {
        Config.appBorderSize = appBorderSize;
    }

    public static String getBtnWatchReplayText() {
        return btnWatchReplayText;
    }

    public static void setBtnWatchReplayText(String btnWatchReplayText) {
        Config.btnWatchReplayText = btnWatchReplayText;
    }

    public static String getBtnShowHighScoreText() {
        return btnShowHighScoreText;
    }

    public static void setBtnShowHighScoreText(String btnShowHighScoreText) {
        Config.btnShowHighScoreText = btnShowHighScoreText;
    }

    public static String getBtnDeleteGameText() {
        return btnDeleteGameText;
    }

    public static void setBtnDeleteGameText(String btnDeleteGameText) {
        Config.btnDeleteGameText = btnDeleteGameText;
    }

    public static String getBtnLogoutText() {
        return btnLogoutText;
    }

    public static void setBtnLogoutText(String btnLogoutText) {
        Config.btnLogoutText = btnLogoutText;
    }

    public static String getMissingGameNameText() {
        return missingGameNameText;
    }

    public static void setMissingGameNameText(String missingGameNameText) {
        Config.missingGameNameText = missingGameNameText;
    }

    public static String getMissingOpponentText() {
        return missingOpponentText;
    }

    public static void setMissingOpponentText(String missingOpponentText) {
        Config.missingOpponentText = missingOpponentText;
    }

    public static String getMissingGameSelectionText() {
        return missingGameSelectionText;
    }

    public static void setMissingGameSelectionText(String missingGameSelectionText) {
        Config.missingGameSelectionText = missingGameSelectionText;
    }

    public static String getConfirmedUserCreationText() {
        return confirmedUserCreationText;
    }

    public static void setConfirmedUserCreationText(String confirmedUserCreationText) {
        Config.confirmedUserCreationText = confirmedUserCreationText;
    }

    public static String getServerPathGamesInvitedById() {
        return serverPathGamesInvitedById;
    }

    public static void setServerPathGamesInvitedById(String serverPathGamesInvitedById) {
        Config.serverPathGamesInvitedById = serverPathGamesInvitedById;
    }

    public static String getServerPathGamesHostedById() {
        return serverPathGamesHostedById;
    }

    public static void setServerPathGamesHostedById(String serverPathGamesHostedById) {
        Config.serverPathGamesHostedById = serverPathGamesHostedById;
    }

    public static String getServerPathOpenGames() {
        return serverPathOpenGames;
    }

    public static void setServerPathOpenGames(String serverPathOpenGames) {
        Config.serverPathOpenGames = serverPathOpenGames;
    }

    public static String getServerPathStartGames() {
        return serverPathStartGames;
    }

    public static void setServerPathStartGames(String serverPathStartGames) {
        Config.serverPathStartGames = serverPathStartGames;
    }

    public static String getServerPathJoinGames() {
        return serverPathJoinGames;
    }

    public static void setServerPathJoinGames(String serverPathJoinGames) {
        Config.serverPathJoinGames = serverPathJoinGames;
    }

    public static int getMoveOne() {
        return moveOne;
    }

    public static void setMoveOne(int moveOne) {
        Config.moveOne = moveOne;
    }

    public static int getNumberForSnakePlacement() {
        return numberForSnakePlacement;
    }

    public static void setNumberForSnakePlacement(int numberForSnakePlacement) {
        Config.numberForSnakePlacement = numberForSnakePlacement;
    }

    public static int getKeyStrokeModifier() {
        return keyStrokeModifier;
    }

    public static void setKeyStrokeModifier(int keyStrokeModifier) {
        Config.keyStrokeModifier = keyStrokeModifier;
    }

    public static String getBtnRefreshText() {
        return btnRefreshText;
    }

    public static void setBtnRefreshText(String btnRefreshText) {
        Config.btnRefreshText = btnRefreshText;
    }

    public static String getBtnDeleteText() {
        return btnDeleteText;
    }

    public static void setBtnDeleteText(String btnDeleteText) {
        Config.btnDeleteText = btnDeleteText;
    }

    public static String getDeleteGameScreen() {
        return deleteGameScreen;
    }

    public static void setDeleteGameScreen(String deleteGameScreen) {
        Config.deleteGameScreen = deleteGameScreen;
    }

    public static String getServerPathDeleteGames() {
        return serverPathDeleteGames;
    }

    public static void setServerPathDeleteGames(String serverPathDeleteGames) {
        Config.serverPathDeleteGames = serverPathDeleteGames;
    }

    public static String getMapSizeChooserTtt() {
        return mapSizeChooserTtt;
    }

    public static void setMapSizeChooserTtt(String mapSizeChooserTtt) {
        Config.mapSizeChooserTtt = mapSizeChooserTtt;
    }

    public static String getLblCreateNewGameText() {
        return lblCreateNewGameText;
    }

    public static void setLblCreateNewGameText(String lblCreateNewGameText) {
        Config.lblCreateNewGameText = lblCreateNewGameText;
    }

    public static String getGameChooserHeaderText() {
        return gameChooserHeaderText;
    }

    public static void setGameChooserHeaderText(String gameChooserHeaderText) {
        Config.gameChooserHeaderText = gameChooserHeaderText;
    }

    public static String[] getTypesOfGames() {
        return typesOfGames;
    }

    public static void setTypesOfGames(String[] typesOfGames) {
        Config.typesOfGames = typesOfGames;
    }

    public static String getGameOverviewerHeaderText() {
        return gameOverviewerHeaderText;
    }

    public static void setGameOverviewerHeaderText(String gameOverviewerHeaderText) {
        Config.gameOverviewerHeaderText = gameOverviewerHeaderText;
    }

    public static String getBtnReplayText() {
        return btnReplayText;
    }

    public static void setBtnReplayText(String btnReplayText) {
        Config.btnReplayText = btnReplayText;
    }

    public static String[] getTypesOfGamesToReplay() {
        return typesOfGamesToReplay;
    }

    public static void setTypesOfGamesToReplay(String[] typesOfGamesToReplay) {
        Config.typesOfGamesToReplay = typesOfGamesToReplay;
    }

    public static String getGameOverviewerScreen() {
        return gameOverviewerScreen;
    }

    public static void setGameOverviewerScreen(String gameOverviewerScreen) {
        Config.gameOverviewerScreen = gameOverviewerScreen;
    }

    public static int getIndexTwo() {
        return indexTwo;
    }

    public static void setIndexTwo(int indexTwo) {
        Config.indexTwo = indexTwo;
    }

    public static int getIndexThree() {
        return indexThree;
    }

    public static void setIndexThree(int indexThree) {
        Config.indexThree = indexThree;
    }

    public static int getIndexFour() {
        return indexFour;
    }

    public static void setIndexFour(int indexFour) {
        Config.indexFour = indexFour;
    }

    public static int getIndexFive() {
        return indexFive;
    }

    public static void setIndexFive(int indexFive) {
        Config.indexFive = indexFive;
    }

    public static int getIndexSix() {
        return indexSix;
    }

    public static void setIndexSix(int indexSix) {
        Config.indexSix = indexSix;
    }

    public static int getIndexSeven() {
        return indexSeven;
    }

    public static void setIndexSeven(int indexSeven) {
        Config.indexSeven = indexSeven;
    }
}
