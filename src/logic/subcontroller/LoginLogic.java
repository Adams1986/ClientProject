package logic.subcontroller;

import sdk.Config;

/**
 * Created by ADI on 24-10-2015.
 */
public class LoginLogic {

    public static boolean authenticate(String message) {

        return message.equals(Config.getLoginAuthentication());
    }
}
