package com.mycompany.prog5121_poe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Login userSystem = new Login();
            System.out.println("=========================================");
            System.out.println("   THE IIE - PROGRAMMING 1A (PROG5121)   ");
            System.out.println("            PART 1: REGISTRATION         ");
            System.out.println("=========================================\n");
            System.out.print("Enter your First Name: ");
            String fName = scanner.nextLine();
            System.out.print("Enter your Last Name: ");
            String lName = scanner.nextLine();
            String username, password, cellPhone;
            // 1. Username Validation Loop
            while (true) {
                System.out.print("\nEnter Username (must contain '_' and be <= 5 chars, e.g., kyl_1): ");
                username = scanner.nextLine();
                if (userSystem.checkUserName(username)) {
                    System.out.println("Username successfully captured.");
                    break;
                } else {
                    System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
                }
            }   // 2. Password Validation Loop
            while (true) {
                System.out.print("\nEnter Password (>= 8 chars, 1 capital, 1 number, 1 special character): ");
                password = scanner.nextLine();
                if (userSystem.checkPasswordComplexity(password)) {
                    System.out.println("Password successfully captured.");
                    break;
                } else {
                    System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
                }
            }   // 3. Cell Phone Validation Loop
            while (true) {
                System.out.print("\nEnter Cell Phone Number (with international code, e.g., +27838968976): ");
                cellPhone = scanner.nextLine();
                if (userSystem.checkCellPhoneNumber(cellPhone)) {
                    System.out.println("Cell phone number successfully added.");
                    break;
                } else {
                    System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
                }
            }   // Complete user registration
            userSystem.registerUser(username, password, cellPhone, fName, lName);
            // 4. Login Feature Execution
            System.out.println("\n-----------------------------------------");
            System.out.println("            LOGIN FEATURE                ");
            System.out.println("-----------------------------------------");
            boolean loginSuccess = false;
            while (!loginSuccess) {
                System.out.print("Enter Username: ");
                String inputUser = scanner.nextLine();
                
                System.out.print("Enter Password: ");
                String inputPass = scanner.nextLine();
                
                loginSuccess = userSystem.loginUser(inputUser, inputPass);
                String statusMessage = userSystem.returnLoginStatus(loginSuccess);
                System.out.println(statusMessage + "\n");
            }
        }
    }
}