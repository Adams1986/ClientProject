package sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.json.simple.JSONObject;
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
        JSONParser jsonParser = new JSONParser();
        Client client = Client.create();

        try {

            WebResource webResource = client.resource("http://" + Config.getIpAdresse() + ":9998/api/login");
            ClientResponse response = webResource.accept("application/json").post(ClientResponse.class,
                    new Gson().toJson(user));

            if(response != null) {

                message = response.getEntity(String.class);

                try {
                    Object obj = jsonParser.parse(message);
                    JSONObject jsonObject = (JSONObject) obj;
                    message = ((String) jsonObject.get("message"));

                    if (jsonObject.get("userid") != null)
                    user.setId((int)(long) jsonObject.get("userid"));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

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
    public static ArrayList<User> getUsers() {

        String message;
        Client client = Client.create();
        ArrayList<User> users = null;

        try {

            WebResource webResource = client.resource("http://" + Config.getIpAdresse() + ":9998/api/users");
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response != null) {

                message = response.getEntity(String.class);

                users = new Gson().fromJson(message, new TypeToken<ArrayList<User>>(){}.getType());


            }
        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return users;
    }

    //TODO: does not work for a delete. Needs to be path parameter on the server
    public static String deleteUser(int userID){


        String message = "";
        Client client = Client.create();

        try {

            WebResource webResource = client.resource("http://localhost:9998/api/user/" + userID);
            ClientResponse response = webResource.accept("application/json").delete(ClientResponse.class);

            if (response != null) {

                message = response.getEntity(String.class);

            }
        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return message;
    }

    //TODO: put urls into config file
    public static String createUser(User user){

        String message = "";
        JSONParser jsonParser = new JSONParser();
        Client client = Client.create();


        try {
            WebResource webResource = client.resource("http://" + Config.getIpAdresse() + ":9998/api/user");
            ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, new Gson().toJson(user));

            message = response.getEntity(String.class);
            System.out.println(message);

            try {
                //Initialize Object class as json, parsed by jsonParsed.
                Object obj = jsonParser.parse(message);

                //Instantiate JSONObject class as jsonObject equal to obj object.
                JSONObject jsonObject = (JSONObject) obj;

                //Use set-methods for defifing static variables from json-file.
                message = ((String) jsonObject.get("message"));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }

        return message;
    }

    public static String createGame(Game game){

        JSONParser jsonParser = new JSONParser();
        String message = "";
        Client client = Client.create();


        try {
            WebResource webResource = client.resource("http://" + Config.getIpAdresse() + ":9998/api/game/");

            ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, new Gson().toJson(game));

            message = response.getEntity(String.class);
            //Initialize Object class as json, parsed by jsonParsed.
            Object obj = jsonParser.parse(message);

            //Instantiate JSONObject class as jsonObject equal to obj object.
            JSONObject jsonObject = (JSONObject) obj;

            //Use set-methods for defifing static variables from json-file.
            message = ((String) jsonObject.get("message"));

        } catch (ParseException p){
            p.printStackTrace();
        }
        catch (ClientHandlerException e){
            e.printStackTrace();
        }
        return message;
    }

    public static String updateGame(Game game){

        JSONParser jsonParser = new JSONParser();
        String message = "";
        Client client = Client.create();


        try {
            WebResource webResource = client.resource("http://" + Config.getIpAdresse() + ":9998/api/game/update/");

            ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, new Gson().toJson(game));

            message = response.getEntity(String.class);
            //Initialize Object class as json, parsed by jsonParsed.
            Object obj = jsonParser.parse(message);

            //Instantiate JSONObject class as jsonObject equal to obj object.
            JSONObject jsonObject = (JSONObject) obj;

            //Use set-methods for defifing static variables from json-file.
            message = ((String) jsonObject.get("message"));

        } catch (ParseException p){
            p.printStackTrace();
        }
        catch (ClientHandlerException e){
            e.printStackTrace();
        }
        return message;
    }

    public static String startGame(int gameId) {

        String message = "";
        Client client = Client.create();


        try {
            WebResource webResource = client.resource("http://" + Config.getIpAdresse() + ":9998/api/startgame/" + gameId);

            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            message = response.getEntity(String.class);
            System.out.println(message);

        } catch (ClientHandlerException e){
            e.printStackTrace();
        }
        return message;

    }

    public static String deleteGame(int gameId) {

        String message = "";
        Client client = Client.create();


        try {
            WebResource webResource = client.resource("http://" + Config.getIpAdresse() + ":9998/api/game/" + gameId);

            ClientResponse response = webResource.accept("application/json").delete(ClientResponse.class);

            message = response.getEntity(String.class);
            System.out.println(message);

        } catch (ClientHandlerException e){
            e.printStackTrace();
        }
        return message;

    }

    public static Game getGame(int gameId){

        String message = "";
        Client client = Client.create();
        Game game = null;

        try {
            WebResource webResource = client.resource("http://" + Config.getIpAdresse() + ":9998/api/game/" + gameId);

            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            message = response.getEntity(String.class);

            game = new Gson().fromJson(message, Game.class);
            System.out.println(message);

        } catch (ClientHandlerException e){
            e.printStackTrace();
        }

        return game;
    }

    public static ArrayList<Game> getGamesInvitedByID(int userId){

        String response = Client
                .create()
                .resource("http://" + Config.getIpAdresse() + ":9998/api/games/guest/" + userId)
                .accept("application/json")
                .get(ClientResponse.class)
                .getEntity(String.class);

        return new Gson().fromJson(response, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    public static ArrayList<Game> getGamesByUserID(int userId){

        String response = Client
                .create()
                .resource("http://" + Config.getIpAdresse() + ":9998/api/games/" + userId)
                .accept("application/json")
                .get(ClientResponse.class)
                .getEntity(String.class);

        return new Gson().fromJson(response, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    /**
     * Looks better than the others right :)
     * @return
     */
    public static ArrayList<Score> getHighScore(){

        //TODO: change to this format
        String response = Client
                .create()
                .resource("http://" + Config.getIpAdresse() + ":9998/api/scores")
                .accept("application/json")
                .get(ClientResponse.class)
                .getEntity(String.class);

       return new Gson().fromJson(response,  new TypeToken<ArrayList<Score>>(){}.getType());

    }

    public static ArrayList<Score> getScores(int userId){

        String response = Client
                .create()
                .resource("http://" + Config.getIpAdresse() + ":9998/api/scores/" + userId)
                .accept("application/json")
                .get(ClientResponse.class)
                .getEntity(String.class);

        return new Gson().fromJson(response, new TypeToken<ArrayList<Score>>(){}.getType());
    }
}
