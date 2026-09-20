/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121_poe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Login class, verifying registration and authentication logic
 * using the mandatory test data provided in the POE instructions.
 */
public class LoginTest {
    private Login loginSystem;

    @BeforeEach
    public void setUp() {
        loginSystem = new Login();
        // Register a valid user state to test login methods later
        // Using the required test data: username "kyl_1", password "Ch&&sec@ke99!", phone "+27838968976"
        loginSystem.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
    }

    // ==========================================
    // 1. USERNAME TESTS
    // ==========================================

    @Test
    public void testCheckUserNameCorrect() {
        // Test Data: "kyl_1" -> Expected: true
        boolean result = loginSystem.checkUserName("kyl_1");
        assertTrue(result, "Username should be correctly formatted and return true.");
    }

    @Test
    public void testCheckUserNameIncorrect() {
        // Test Data: "kyle!!!!!!!" -> Expected: false
        boolean result = loginSystem.checkUserName("kyle!!!!!!!");
        assertFalse(result, "Username is too long or missing an underscore, should return false.");
    }

    // ==========================================
    // 2. PASSWORD COMPLEXITY TESTS
    // ==========================================

    @Test
    public void testCheckPasswordComplexityValid() {
        // Test Data: "Ch&&sec@ke99!" -> Expected: true
        boolean result = loginSystem.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(result, "Password meets all complexity rules and should return true.");
    }

    @Test
    public void testCheckPasswordComplexityInvalid() {
        // Test Data: "password" -> Expected: false
        boolean result = loginSystem.checkPasswordComplexity("password");
        assertFalse(result, "Password fails complexity rules (no capital, number, or special char), should return false.");
    }

    // ==========================================
    // 3. CELL PHONE NUMBER TESTS
    // ==========================================

    @Test
    public void testCheckCellPhoneNumberValid() {
        // Test Data: "+27838968976" -> Expected: true
        boolean result = loginSystem.checkCellPhoneNumber("+27838968976");
        assertTrue(result, "Cell phone number is correctly formatted with international code, should return true.");
    }

    @Test
    public void testCheckCellPhoneNumberInvalid() {
        // Test Data: "08966553" -> Expected: false
        boolean result = loginSystem.checkCellPhoneNumber("08966553");
        assertFalse(result, "Cell phone number is missing international code or invalid length, should return false.");
    }

    // ==========================================
    // 4. LOGIN AUTHENTICATION TESTS
    // ==========================================

    @Test
    public void testLoginUserSuccess() {
        // Test entering correct credentials -> Expected: true
        boolean result = loginSystem.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(result, "Login with correct credentials should return true.");
    }

    @Test
    public void testLoginUserFailure() {
        // Test entering incorrect credentials -> Expected: false
        boolean result = loginSystem.loginUser("kyl_1", "WrongPassword1!");
        assertFalse(result, "Login with incorrect credentials should return false.");
    }

    // ==========================================
    // 5. REGISTRATION MESSAGE & STATUS TESTS
    // ==========================================

    @Test
    public void testRegisterUserSuccessMessages() {
        Login freshSystem = new Login();
        String response = freshSystem.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        
        assertTrue(response.contains("Username successfully captured."));
        assertTrue(response.contains("Password successfully captured."));
        assertTrue(response.contains("Cell phone number successfully added."));
    }

    @Test
    public void testReturnLoginStatusSuccessMessage() {
        // Test status message formatting when login succeeds
        String status = loginSystem.returnLoginStatus(true);
        assertEquals("Welcome Kyle, Smith it is great to see you again.", status);
    }

    @Test
    public void testReturnLoginStatusFailureMessage() {
        // Test status message formatting when login fails
        String status = loginSystem.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", status);
    }
}






    
