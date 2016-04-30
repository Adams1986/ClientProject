package sdk;

import com.sun.jersey.api.client.*;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Generic methods to access the server. One for each of the following: get, post, put and delete. Will likewise be used
 * in a generic way in the Api class which will only take and receive Strings in json format.
 */
public class ServerConnection {

    //Map used to store token received from the server
    private static MultivaluedMap<String, String> token;

    /**
     * get method. Receives a string with the path with which to get data from the server. Returns it in the message
     * variable
     * @param path
     * @return
     */
    public static String get (String path, String headerData) {

        String message = "";
        Client client = Client.create();
        String authorizationHeader = null;

        try {

            if (token != null){

                authorizationHeader = token.get(Config.getTokenHeaderKey()).get(Config.getIndexOne());
            }

            WebResource webResource = client.resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/" + path);

            ClientResponse response = webResource
                    .accept(Config.getCommunicationFormat())
                    .header(Config.getTokenHeaderKey(), authorizationHeader)
                    .header(Config.getHeaderDataKey(), headerData)
                    .get(ClientResponse.class);

            if (response != null) {

                message = response.getEntity(String.class);

            }

        } catch (ClientHandlerException e) {

            message = Config.getFailedServerConnectionText();
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
        String authorizationHeader = null;

        try {

            if (token != null){

                authorizationHeader = token.get(Config.getTokenHeaderKey()).get(0);
            }

            WebResource webResource = client.resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/" + path);

            ClientResponse response = webResource
                    .accept(Config.getCommunicationFormat())
                    .header(Config.getTokenHeaderKey(), authorizationHeader)
                    .post(ClientResponse.class, data);

            if (response != null) {

                message = response.getEntity(String.class);

                if (response.getHeaders().get(Config.getTokenHeaderKey()) != null)
                    token = response.getHeaders();

            }

        } catch (ClientHandlerException e) {

            message = Config.getFailedServerConnectionText();
        }
        return message;
    }

    /**
     * Delete method. Like the get method it receives a path as parameter and uses to connect to the server.
     * @param path
     * @return
     */
    public static String delete(String path, String headerData){

        String message = "";
        Client client = Client.create();
        String authorizationHeader = null;

        try {

            if (token != null){

                authorizationHeader = token.get(Config.getTokenHeaderKey()).get(Config.getIndexOne());
            }

            WebResource webResource = client.resource("http://" + Config.getIpAddress() + /*":" + Config.getServerPort() + */"/api/" + path);

            ClientResponse response = webResource
                    .accept(Config.getCommunicationFormat())
                    .header(Config.getTokenHeaderKey(), authorizationHeader)
                    .header(Config.getHeaderDataKey(), headerData)
                    .delete(ClientResponse.class);

            if (response != null) {

                message = response.getEntity(String.class);

            }
        } catch (ClientHandlerException e) {

            message = Config.getFailedServerConnectionText();
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
        String authorizationHeader = null;

        try {

            if (token != null){

                authorizationHeader = token.get(Config.getTokenHeaderKey()).get(Config.getIndexOne());
            }

            WebResource webResource = client.resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/" + path);

            ClientResponse response = webResource
                    .accept(Config.getCommunicationFormat())
                    .header(Config.getTokenHeaderKey(), authorizationHeader)
                    .put(ClientResponse.class, data);

            if (response != null) {

                message = response.getEntity(String.class);

            }

        } catch (ClientHandlerException e) {

            message = Config.getFailedServerConnectionText();
        }
        return message;
    }

    /**
     * Method sets the token to null. To be used when user logs out, so a token is not stored in the client.
     */
    public static void resetToken() {

        token = null;
    }
}
