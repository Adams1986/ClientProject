package sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ADI on 18-10-2015.
 */
public class Api {

    Client client;
    WebResource webResource;
    ClientResponse response;

    /**
     * Authenticates a user passed in the parameter to see if user exists with username and password
     * @param user
     * @return String message with info regarding success of login attempt
     */
    public static String authenticateLogin(User user) {

        String message = "";
        Client client = Client.create();

        try {

            WebResource webResource = client.resource("http://localhost:9998/api/login");
            ClientResponse response = webResource.accept("application/json").post(ClientResponse.class,
                    new Gson().toJson(user));

            if(response != null) {
//                if (response.getStatus() != 200) {
//
//                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//                }

                message = response.getEntity(String.class);
            }

        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * Retrieves all users from the server and saves them in an arraylist
     * @return Arraylist of all users/gamers
     */
    public static ArrayList<Gamer> getUsers() {

        String message;
        Client client = Client.create();
        ArrayList<Gamer> users = null;

        try {

            WebResource webResource = client.resource("http://localhost:9998/api/user");
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response != null) {
//                if (response.getStatus() != 200) {
//
//                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//                }

                message = response.getEntity(String.class);

                users = new Gson().fromJson(message, new TypeToken<ArrayList<Gamer>>(){}.getType());


            }
        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return users;
    }

    //TODO: does not work for a delete
    public static boolean deleteUser(int userID){


        String message;
        Client client = Client.create();

        try {

            WebResource webResource = client.resource("http://localhost:9998/api/user/" + userID);
            ClientResponse response = webResource.accept("application/json").delete(ClientResponse.class);

            if (response != null) {
//                if (response.getStatus() != 200) {
//
//                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//                }

                message = response.getEntity(String.class);

            }
        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserObject(){

        User user = null;

        try {
            webResource = client.resource("http://localhost:9998/helloworld/user1");


            response = webResource.accept("application/json").get(ClientResponse.class);

        } catch (ClientHandlerException e) {

            System.out.println("Test");
        }

        if(response != null) {
            if (response.getStatus() != 200) {

                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            Security security = new Security();

            String encryptedUser = response.getEntity(String.class);

            String decryptedUser = security.decrypt(encryptedUser, "1");

            user = new Gson().fromJson(decryptedUser, User.class);

            System.out.println(user.getUserName());
        }
        return user;
    }

    public String getAuthenticatedUser(User user){

        try {
            webResource = client.resource("http://localhost:9998/api/login/");

            response = webResource.accept("application/json")
                    .post(ClientResponse.class, new Gson().toJson(user));
            System.out.println(new Gson().toJson(user));
//                    .post(ClientResponse.class, Security.encrypt(new Gson().toJson(user), encryptionKey));
        } catch (ClientHandlerException e) {

            System.out.println("Test");
        }

        if(response != null) {
            if (response.getStatus() != 200) {

                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            Security security = new Security();

            String encryptedUser = response.getEntity(String.class);

            String decryptedUser = security.decrypt(encryptedUser, "1");


            return encryptedUser;

        }

        return null;
    }

    public String createUser(User user) {

        try {
            webResource = client.resource("http://localhost:9998/helloworld/createuser/");

            response = webResource.type("application/json").post(
                    ClientResponse.class, Security.decrypt(new Gson().toJson(user), Config.getEncryptionkey()));

        } catch (ClientHandlerException e) {

            System.out.println("Test");
        }
        if(response != null) {
            if (response.getStatus() != 200) {

                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            return response.getEntity(String.class);
        }
        return "";

    }
}
