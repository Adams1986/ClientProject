package example;


import java.util.Scanner;

/**
 * Created by ADI on 16-10-2015.
 */
public class App {
  public static void main(String[] argv) {

    ClientMethods client = new ClientMethods();

    User currentUser = new User();

    Scanner input = new Scanner(System.in);

    System.out.println("Enter username: ");
//    String username = input.next();
    currentUser.setUsername(input.next());

    System.out.println("Enter password: ");
//    String password = input.next();
    currentUser.setPassword(input.next());
    currentUser = client.getAuthenticatedUser(currentUser);

    if(currentUser != null){

      int menu = client.mainMenu();

      switch (menu){

        case 1:
          System.out.println("Type in new password: ");
          currentUser.setPassword(input.next());
          client.updatePassword(currentUser);
          break;

        case 2:
          User createdUser = new User();
          System.out.println("Type new users first name: ");
          createdUser.setFirstName(input.next());
          System.out.println("Type new users last name: ");
          createdUser.setLastName(input.next());
          System.out.println("Type new users email: ");
          createdUser.setEmail(input.next());
          System.out.println("Type new users username: ");
          createdUser.setUsername(input.next());
          System.out.println("Type new users password: ");
          createdUser.setPassword(input.next());

          //print and create. Multifunction FTW
          System.out.println(client.createUser(createdUser));
          break;

        case 3:
          //TODO: play a game?
          client.play();

        case 4:
          User userTodelete = new User();
          System.out.println("Type username of user to delete: ");
          userTodelete.setUsername(input.next());

          System.out.println(client.softDeleteUser(userTodelete));
          break;

        default:
          //TODO
      }
    }

  }
}
