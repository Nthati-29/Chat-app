/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121_poe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Login class handles user registration, validation checks (username, password, 
 * cell phone number using regular expressions), and user authentication status.
 */
public class Login {
    // Stored regitration variables
    private String registeredUsername;
    private String registeredPassword;
    private String registeredPhoneNumber;
    private String firstName;
    private String lastName;

   /**
     * Checks if the username contains an underscore and is no more than five characters long.
     * @param username
     * @return 
     */
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    /**
     * Checks if the password meets complexity rules:
     * - At least 8 characters long
     * - Contains a capital letter
     * - Contains a number
     * - Contains a special character
     * @param password
     * @return 
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasCapital = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Checks if the cell phone number contains an international country code and correct format.
     * Reference/Attribution: Regular expression pattern adapted from standard Java regex documentation 
     * for international E.164 phone number formatting matching digits.
     * @param cellNumber
     * @return 
     */
   public boolean checkCellPhoneNumber(String cellNumber) {
        if (cellNumber == null) {
            return false;
        }
        String regex = "^\\+\\d{1,3}\\d{9,10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellNumber);
        return matcher.matches();
    }

    /**
     * Registers the user by validating inputs and storing valid credentials.
     * @param username
     * @param password
     * @param phoneNumber
     * @param firstName
     * @param lastName
     * @return 
     */
    public String registerUser(String username, String password, String phoneNumber, String firstName, String lastName) {
        
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(phoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // Save details upon successful validation
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredPhoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    /**
     * Verifies entered credentials against stored credentials during login.
     * @param enteredUsername
     * @param enteredPassword
     * @return 
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (registeredUsername != null && registeredPassword != null) {
            return registeredUsername.equals(enteredUsername) && registeredPassword.equals(enteredPassword);
        }
        return false;
    }

    /**
     * Returns the status message for login attempts.
     * @param loginSuccess
     * @return 
     */
    public String returnLoginStatus(boolean loginSuccess) {
         if (loginSuccess) {
            String fName = firstName != null ? firstName : "";
            String lName = lastName != null ? lastName : "";
            return "Welcome " + fName + ", " + lName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
    /**
     * Getter for the registered phone number, in case calling code needs it.
     * @return 
     */
    public String getRegisteredPhoneNumber() {
        return registeredPhoneNumber;
    }
}
    
