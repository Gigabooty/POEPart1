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
        boolean registered = false;
 
        System.out.println("=== Registration and Login App ===");
 
        // ----- Registration -----
        System.out.println("\n--- Register a new account ---");
 
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
 
        
        if (login.checkUserName(username) && login.checkPasswordComplexity(password)
                && login.checkCellPhoneNumber(cellPhoneNumber)) {
            registered = true;
        }
 
        // ----- Login -----
        if (registered) {
            System.out.println("\n--- Login ---");
 
            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();
 
            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();
 
            String loginMessage = login.returnLoginStatus(loginUsername, loginPassword);
            System.out.println(loginMessage);
        } else {
            System.out.println("\nRegistration failed, so login was not attempted.");
        }
 
        input.close();
    }
}

    
    

