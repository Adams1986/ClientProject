package sdk;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
/**
 * This class contains methods which returns either a hashed or
 * encrypted/decrypted string from a string argument
 * @author Team Depardieu
 * Usage: security.hashing(String), security.encrypt(String message, String key),
 * security.decrypt(String message, String key)
 */
public class Security {

    /**
     * This method returns an encrypted (XOR) String
     * Source: http://stackoverflow.com/questions/13641563/xor-cipher-in-java-php-different-results
     * @param message
     * @param key
     * @return
     */
    public static String encrypt(String message, String key){
        try {
            if (message==null || key==null ) return null;

            char[] keys=key.toCharArray();
            char[] mesg=message.toCharArray();
            BASE64Encoder encoder = new BASE64Encoder();

            int ml=mesg.length;
            int kl=keys.length;
            char[] newmsg=new char[ml];

            for (int i=0; i<ml; i++){
                newmsg[i]=(char)(mesg[i]^keys[i%kl]);
            }
            mesg=null;
            keys=null;
            String temp = new String(newmsg);
            return new String(new BASE64Encoder().encodeBuffer(temp.getBytes()));
        }
        catch ( Exception e ) {
            return null;
        }
    }

    /**
     * This method returns a decrypted (XOR) String
     * Source: http://stackoverflow.com/questions/13641563/xor-cipher-in-java-php-different-results
     * @param message
     * @param key
     * @return
     */
    public static String decrypt(String message, String key){
        try {
            if (message==null || key==null ) return null;
            BASE64Decoder decoder = new BASE64Decoder();
            char[] keys=key.toCharArray();
            message = new String(decoder.decodeBuffer(message));
            char[] mesg=message.toCharArray();

            int ml=mesg.length;
            int kl=keys.length;
            char[] newmsg=new char[ml];

            for (int i=0; i<ml; i++){
                newmsg[i]=(char)(mesg[i]^keys[i%kl]);
            }
            mesg=null; keys=null;
            return new String(newmsg);
        }
        catch ( Exception e ) {
            return null;
        }
    }
}

