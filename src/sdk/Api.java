package sdk;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.util.Scanner;

/**
 * Created by ADI on 18-10-2015.
 */
public class Api {

    Client client = Client.create();

    WebResource webResource = null;
    ClientResponse response = null;

    private String encryptionKey = "123456";

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
                    ClientResponse.class, Security.decrypt(new Gson().toJson(user), encryptionKey));

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
