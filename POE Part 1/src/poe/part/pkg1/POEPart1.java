/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poe.part.pkg1;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class POEPart1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
 
    



    
        Scanner input = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== Registration and Login ===");

        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter username (must contain an underscore and be no more than 5 characters): ");
        String username = input.nextLine();

        System.out.print("Enter password (min 8 characters, a capital letter, a number, a special character): ");
        String password = input.nextLine();

        System.out.print("Enter South African cell number (e.g. +27831234567): ");
        String cellPhoneNumber = input.nextLine();

        String registrationMessage = login.registerUser(username, password, cellPhoneNumber, firstName, lastName);
        System.out.println(registrationMessage);

        boolean registered = login.checkUserName(username) && login.checkPasswordComplexity(password)
                && login.checkCellPhoneNumber(cellPhoneNumber);

        if (!registered) {
            System.out.println("Registration failed. Cannot continue.");
            input.close();
            return;
        }

        System.out.print("\nEnter username to log in: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password to log in: ");
        String loginPassword = input.nextLine();

        boolean loggedIn = login.loginUser(loginUsername, loginPassword);
        System.out.println(login.returnLoginStatus(loginUsername, loginPassword));

        if (!loggedIn) {
            input.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages would you like to enter? ");
        int messageLimit = Integer.parseInt(input.nextLine());

        boolean running = true;

        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(input.nextLine());

            if (choice == 1) {
                for (int i = 0; i < messageLimit; i++) {
                    Message message = new Message();

                    System.out.print("\nEnter recipient cell number: ");
                    String recipient = input.nextLine();
                    String cellResult = message.checkRecipientCell(recipient);
                    System.out.println(cellResult);

                    if (!cellResult.equals("Cell phone number successfully captured.")) {
                        continue;
                    }

                    System.out.print("Enter your message: ");
                    String text = input.nextLine();
                    String lengthResult = message.checkMessageLength(text);
                    System.out.println(lengthResult);

                    if (!lengthResult.equals("Message ready to send.")) {
                        continue;
                    }

                    String messageID = message.generateMessageID();
                    String hash = message.createMessageHash(messageID, i, text);
                    message.setDetails(messageID, recipient, text, hash);

                    System.out.println("\n1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Choose an option: ");
                    int sendChoice = Integer.parseInt(input.nextLine());

                    String resultMessage = message.sentMessage(sendChoice);
                    System.out.println(resultMessage);

                    if (sendChoice == 1 || sendChoice == 3) {
                        message.storeMessageToFile();
                    }

                    if (sendChoice == 1) {
                        System.out.println("\nMessage ID: " + messageID);
                        System.out.println("Message Hash: " + hash);
                        System.out.println("Recipient: " + recipient);
                        System.out.println("Message: " + text);
                    }
                }

                Message totals = new Message();
                System.out.println("\nTotal messages sent: " + totals.returnTotalMessagesSent());

            } else if (choice == 2) {
                System.out.println("Coming Soon.");
            } else if (choice == 3) {
                running = false;
            } else {
                System.out.println("Invalid option, please try again.");
            }
        }

        input.close();
    }
}

    
    

