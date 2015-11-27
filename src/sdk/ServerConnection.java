package sdk;

import com.sun.jersey.api.client.*;

/**
 * Generic methods to access the server. One for each of the following: get, post, put and delete. Will likewise be used
 * in a generic way in the Api class which will only take and receive Strings in json format.
 */
public class ServerConnection {

    /**
     * get method. Receives a string with the path with which to get data from the server. Returns it in the message
     * variable
     * @param path
     * @return
     */
    public static String get (String path) {

        String message = "";
        Client client = Client.create();

        try {

            WebResource webResource = client.resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/" + path);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response != null) {

                message = response.getEntity(String.class);

            }

        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * Like the get method, it receives a string corresponding to the path from which it will connect with the server.
     * Also it receives data as a String in json format, which it sends to the server. Is done with the ClientResponse
     * object. The string variable message gets the answer from the server and the method returns the string.
     * @param path
     * @param data
     * @return
     */
    public static String post (String path, String data) {

        String message = "";
        Client client = Client.create();

        try {

            WebResource webResource = client.resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/" + path);

            ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, data);

            if (response != null) {

                message = response.getEntity(String.class);

            }

        } catch (ClientHandlerException e) {

            message = "{\"message\":\"Connection to server failed\"}";
        }
        return message;
    }

    /**
     * Delete method. Like the get method it receives a path as parameter and uses to connect to the server.
     * @param path
     * @return
     */
    public static String delete(String path){

        String message = "";
        Client client = Client.create();

        try {

            WebResource webResource = client.resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/" + path);

            ClientResponse response = webResource.accept("application/json").delete(ClientResponse.class);

            if (response != null) {

                message = response.getEntity(String.class);

            }
        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * Like the post method, it receives both a path and data, which it sends to the server through the .put method.
     * @param path
     * @param data
     * @return
     */
    public static String put(String path, String data) {

        String message = "";
        Client client = Client.create();

        try {

            WebResource webResource = client.resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/" + path);

            ClientResponse response = webResource.accept("application/json")
                    .put(ClientResponse.class, data);

            if (response != null) {

                message = response.getEntity(String.class);

            }

        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return message;
    }
}
