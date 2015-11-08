package sdk;

import java.sql.Date;

/**
 * Created by ADI on 07-11-2015.
 */
public class DTOEncryption {

    //TODO as parameter works by pointing to object, it is probably not necessary to return the object!
    public void encryptGame(Game game){

        game.setHost(encryptGamer(game.getHost()));
        game.setStatus(Security.encrypt(game.getStatus(), Config.getEncryptionkey()));
        game.setOpponentControls(Security.encrypt(game.getOpponentControls(), Config.getEncryptionkey()));
        game.setOpponent(encryptGamer(game.getHost()));
        game.setHostControls(Security.encrypt(game.getHostControls(), Config.getEncryptionkey()));
//        game.setCreated((Date) Security.encrypt(game.getCreated().toString(), Config.getEncryptionkey())); //TODO issues with encrypting a date
//        game.setGameId(Integer.parseInt(Security.encrypt(game.getGameId()+"", Config.getEncryptionkey())));//TODO also issue
//        game.setMapSize();
        game.setName(Security.encrypt(game.getName(), Config.getEncryptionkey()));
//        game.setWinner();
    }

    public Game decryptGame(Game game){

        game.setHost(encryptGamer(game.getHost()));
        game.setStatus(Security.decrypt(game.getStatus(), Config.getEncryptionkey()));
        game.setOpponentControls(Security.decrypt(game.getOpponentControls(), Config.getEncryptionkey()));
        game.setOpponent(encryptGamer(game.getHost()));
        game.setHostControls(Security.decrypt(game.getHostControls(), Config.getEncryptionkey()));
//        game.setCreated((Date) Security.decrypt(game.getCreated().toString(), Config.getEncryptionkey())); //TODO issues with encrypting a date
//        game.setGameId(Integer.parseInt(Security.decrypt(game.getGameId()+"", Config.getEncryptionkey())));//TODO also issue
//        game.setMapSize();
        game.setName(Security.decrypt(game.getName(), Config.getEncryptionkey()));
//        game.setWinner();

        return game;
    }

    //TODO, even necessary?
    public Gamer encryptGamer(Gamer gamer){

//        gamer.setControls();

        return gamer;
    }

    public Gamer decryptGamer(Gamer gamer){

        return gamer;
    }

    public Score encryptScore(Score score){

        return score;
    }

    public Score decryptScore(Score score){

        return score;
    }

    public User encryptUser(User user){

        return user;
    }

    public User decryptUser(User user){

        return user;
    }
}
