package example;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.util.Scanner;

/**
 * Created by ADI on 18-10-2015.
 */
public class ClientMethods {

    Client client = Client.create();

    WebResource webResource = null;
    ClientResponse response = null;

    public User getUserObject(){

        User user = null;

        try {
            webResource = client.resource("http://localhost:998/helloworld/user1");


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

            System.out.println(user.getUsername());
        }
        return user;
    }

    public User getAuthenticatedUser(User user){

        try {
            webResource = client.resource("http://localhost:998/helloworld/loginauthorization/");

            response = webResource.accept("application/json").post(ClientResponse.class, user.toEncryptedJson());
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


            return new Gson().fromJson(decryptedUser, User.class);

        }

        return null;
    }

    public void updatePassword(User user){

        try {
            webResource = client.resource("http://localhost:998/helloworld/updateuser/");

            response = webResource.type("application/json").put(
                    ClientResponse.class, user.toEncryptedJson());
            System.out.println(user.toEncryptedJson());
            System.out.println(new Security().decrypt(user.toEncryptedJson(), "1"));

        } catch (ClientHandlerException e) {

            System.out.println("Test");
        }
        if(response != null) {
            if (response.getStatus() != 200) {

                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            System.out.println(response.getEntity(String.class));
        }
    }

    public int mainMenu(){

        System.out.println("Login successful");
        System.out.println("Welcome to menu");
        System.out.println("1 - Change password");
        System.out.println("2 - Create new user");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        return choice;
    }

    public String createUser(User user) {

        try {
            webResource = client.resource("http://localhost:998/helloworld/createuser/");

            response = webResource.type("application/json").post(
                    ClientResponse.class, user.toEncryptedJson());

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

    public String softDeleteUser(User user){

        try {
            webResource = client.resource("http://localhost:998/helloworld/softdeleteuser/");

            response = webResource.type("application/json").put(ClientResponse.class, user.toEncryptedJson());
        }
        catch (ClientHandlerException e){
            System.out.println("Error");
        }

        if (response != null){
            if (response.getStatus() != 200){

                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            return response.getEntity(String.class);
        }
        return "";
    }

    public void play() {

        Game game = new Game();

        game.start();
    }
}
