package example;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
/**
 * Created by ADI on 18-10-2015.
 */
public class ClientMethods {

    Client client = Client.create();

    WebResource webResource = null;
    ClientResponse response = null;

    public void getUserObject(){


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

            User user1 = new Gson().fromJson(decryptedUser, User.class);

            System.out.println(user1.getUsername());
        }
    }

    public boolean isAuthenticated(User currentUser){

        boolean isAuthenticated = true;

        try {
            webResource = client.resource("http://localhost:998/helloworld/loginauthorization/" + currentUser.getUsername());

            response = webResource.accept("application/json").get(ClientResponse.class);
        } catch (ClientHandlerException e) {

            System.out.println("Test");
        }

        if(response != null) {
            if (response.getStatus() != 200) {

                isAuthenticated = false;
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            Security security = new Security();

            String encryptedUser = response.getEntity(String.class);

            String decryptedUser = security.decrypt(encryptedUser, "1");

            User user1 = new Gson().fromJson(decryptedUser, User.class);

            System.out.println(user1.getUsername());



        }
        else
            isAuthenticated = false;

        return isAuthenticated;
    }

    public int mainMenu(){



        return -1;
    }

}
