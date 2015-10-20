package example;


import java.util.Scanner;

/**
 * Created by ADI on 16-10-2015.
 */
public class App {
  public static void main(String[] argv) {

    ClientMethods client = new ClientMethods();

    Scanner input = new Scanner(System.in);

    User user = new User();

    System.out.println("Enter username: ");
//    String username = input.next();
    user.setUsername(input.next());

    System.out.println("Enter password: ");
//    String password = input.next();
    user.setPassword(input.next());

    client.getUserObject();

    if(client.isAuthenticated(user)){

      System.out.println("Login successful");
    }

    System.out.println("Change password");
    System.out.println("Type in new password: ");
    user.setPassword(input.next());
    client.updatePassword(user);

  }
}
