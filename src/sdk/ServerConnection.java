package sdk;

import com.sun.jersey.api.client.*;

/**
 * Created by simonadams on 09/11/15.
 */
public class ServerConnection {

    public static String get (String path) {

//        return Client.create()
//                .resource("http://" + Config.getIpAddress() + ":" +Config.getServerPort() + "/api/" + path)
//                .accept("application/json")
//                .get(ClientResponse.class)
//                .getEntity(String.class);

        String message = "";
        Client client = Client.create();

        try {

            WebResource webResource = client.resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/" + path);
            System.out.println("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/" + path);
            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            if (response != null) {

                message = response.getEntity(String.class);

            }

        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static String post (String path, String data) {

//        return Client.create()
//                .resource("http://" + Config.getIpAddress() + ":9998/api/" + path)
//                .accept("application/json")
//                .post(ClientResponse.class, new Gson().toJson(data))
//                .getEntity(String.class);

        String message = "";
        Client client = Client.create();

        try {

            WebResource webResource = client.resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/" + path);

            ClientResponse response = webResource.accept("application/json")
                    .post(ClientResponse.class, data);

            if (response != null) {

                message = response.getEntity(String.class);

            }

        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return message;
    }

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
