package it.polimi.ingsw.Main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is the main class of the client side of the application.
 * It is used to start the client side of the application.
 * It asks the user to choose the type of user interface he wants to play with and the ip address of the server.
 * Then it starts the client side of the application with the chosen user interface.
 * The user can choose between a text user interface and a graphical user interface and between RMI and Socket connection.
 * The user can choose between the following options:
 * 1) TUI + RMI
 * 2) TUI + Socket
 * 3) GUI + RMI
 * 4) GUI + Socket
 * The user must choose the ip address of the server.
 * The user can choose the ip address of the server by typing it in the console.
 */

/*
public class AppClient1 {
    /**
     * This is the main method of the client side of the application.
     * @param args the command line arguments
     * @throws NotBoundException exception thrown when a remote object is not bound
     * @throws RemoteException exception thrown when a remote operation fails

    public static void main(String[] args) throws NotBoundException, RemoteException {
        Scanner serverChoice = new Scanner(System.in);
        System.out.println("Choose the type of ui you want to play with: ");
        System.out.println(" 1) TUI + RMI \n 2) TUI + Socket\n 3) GUI + RMI\n 4) GUI + Socket");
        int choice= serverChoice.nextInt();
        if (choice == 1 || choice == 3) {
            System.out.println("Insert the ip address of the server");
            String ip = serverChoice.next();
            AppClientRMI1.main(args,choice,ip);
        }
        else if (choice == 2 || choice == 4) {
            System.out.println("Insert the ip address of the server");
            String ip = serverChoice.next();
            AppClientSocket1.main(args, choice,ip);}
        {
            //System.out.println("Invalid choice");
        }
    }
}*/


public class AppClient1 {
    public static void main(String[] args) throws NotBoundException, RemoteException {
        boolean isRegistered = false;
        boolean isValid = false;
        Scanner serverChoice = new Scanner(System.in);
        System.out.println("Choose the type of ui you want to play with: ");
        System.out.println(" 1) TUI + RMI \n 2) TUI + Socket\n 3) GUI + RMI\n 4) GUI + Socket");
        while (!isValid) {
            try {
                int choice = serverChoice.nextInt();
                if (choice == 1 || choice == 3) {
                    isValid = true;
                    while (!isRegistered) {
                        try {
                            System.out.println("Insert the ip address of the server");
                            String ip = serverChoice.next();
                            AppClientRMI1.main(args, choice, ip);
                            isRegistered = true;
                        } catch (RemoteException e) {
                            System.out.println("Invalid ip, try again");
                        }
                    }
                } else if (choice == 2 || choice == 4) {
                    isValid = true;
                    while (!isRegistered) {
                        try {

                            System.out.println("Insert the ip address of the server");
                            String ip = serverChoice.next();
                            AppClientSocket1.main(args, choice, ip);
                            isRegistered = true;
                        } catch (RemoteException e) {
                            System.out.println("Invalid ip, try again");
                        }
                    }
                } else {
                    System.out.println("Choose between 1 and 4");
                }
            }catch (InputMismatchException e){
                System.out.println("Please, insert an integer");
                serverChoice.next(); //flush the scanner
            }
        }
        {
            //System.out.println("Invalid choice");
        }
    }
}





