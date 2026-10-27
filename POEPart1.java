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
 
        String senderNumber = login.getCellPhoneNumber();
        Message messageTools = new Message();
 
        System.out.println("\nWelcome to QuickChat.");
 
        System.out.print("How many messages would you like to enter? ");
        int messageLimit = Integer.parseInt(input.nextLine());
 
        boolean running = true;
 
        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
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
 
                    System.out.println("\n1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Choose an option: ");
                    int sendChoice = Integer.parseInt(input.nextLine());
 
                    String flag;
                    String resultMessage;
 
                    if (sendChoice == 1) {
                        flag = "Sent";
                        resultMessage = "Message successfully sent.";
                    } else if (sendChoice == 2) {
                        flag = "Disregard";
                        resultMessage = "Press 0 to delete the message.";
                    } else if (sendChoice == 3) {
                        flag = "Stored";
                        resultMessage = "Message successfully stored.";
                    } else {
                        System.out.println("Invalid option selected.");
                        continue;
                    }
 
                    message.addMessage(senderNumber, recipient, text, flag);
                    System.out.println(resultMessage);
 
                    if (flag.equals("Stored")) {
                        message.storeMessageToFile();
                    }
 
                    if (flag.equals("Sent")) {
                        System.out.println("\nMessage ID: " + message.getMessageID());
                        System.out.println("Message Hash: " + message.getMessageHash());
                        System.out.println("Recipient: " + message.getRecipient());
                        System.out.println("Message: " + message.getMessageText());
                    }
                }
 
                System.out.println("\nTotal messages sent: " + messageTools.returnTotalMessagesSent());
 
            } else if (choice == 2) {
                System.out.println("Coming Soon.");
            } else if (choice == 3) {
                running = false;
            } else if (choice == 4) {
                messageTools.loadStoredMessagesFromFile();
 
                boolean inStoredMenu = true;
 
                while (inStoredMenu) {
                    System.out.println("\n--- Stored Messages ---");
                    System.out.println("1) Display sender and recipient of all stored messages");
                    System.out.println("2) Display the longest stored message");
                    System.out.println("3) Search for a message ID");
                    System.out.println("4) Search for all messages stored for a recipient");
                    System.out.println("5) Delete a message using the message hash");
                    System.out.println("6) Display a report of all stored messages");
                    System.out.println("7) Back to main menu");
                    System.out.print("Choose an option: ");
                    int storedChoice = Integer.parseInt(input.nextLine());
 
                    if (storedChoice == 1) {
                        System.out.println(messageTools.displayStoredSendersAndRecipients());
                    } else if (storedChoice == 2) {
                        System.out.println(messageTools.displayLongestStoredMessage());
                    } else if (storedChoice == 3) {
                        System.out.print("Enter the message ID: ");
                        String searchID = input.nextLine();
                        System.out.println(messageTools.searchStoredMessageByID(searchID));
                    } else if (storedChoice == 4) {
                        System.out.print("Enter the recipient cell number: ");
                        String searchRecipient = input.nextLine();
                        System.out.println(messageTools.searchStoredMessagesByRecipient(searchRecipient));
                    } else if (storedChoice == 5) {
                        System.out.print("Enter the message hash: ");
                        String searchHash = input.nextLine();
                        System.out.println(messageTools.deleteStoredMessageByHash(searchHash));
                    } else if (storedChoice == 6) {
                        System.out.println(messageTools.displayStoredMessagesReport());
                    } else if (storedChoice == 7) {
                        inStoredMenu = false;
                    } else {
                        System.out.println("Invalid option, please try again.");
                    }
                }
            } else {
                System.out.println("Invalid option, please try again.");
            }
        }
 
        input.close();
    }
}

    
    

