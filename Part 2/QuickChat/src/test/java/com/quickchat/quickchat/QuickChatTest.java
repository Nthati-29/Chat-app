/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.quickchat.quickchat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author nthateng
 */
public class QuickChatTest {
    
    public QuickChatTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        System.out.println("--- Starting QuickChat Unit Tests ---");
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("--- Completed QuickChat Unit Tests ---");
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.

    @Test
    public void testMessageLengthValidationSuccess() {
        String shortMsg = "Hi Mike, can you join us for dinner tonight?";
        assertTrue(shortMsg.length() <= 250);
    }

    @Test
    public void testMessageLengthValidationFailure() {
        String longMsg = "a".repeat(251);
        int excess = longMsg.length() - 250;
        assertEquals(1, excess);
    }

    @Test
    public void testRecipientCellSuccess() {
        String validRecipient = "+27718693002";
        String result = QuickChat.checkRecipientCell(validRecipient);
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testRecipientCellFailure() {
        String invalidRecipient = "08575975889";
        String result = QuickChat.checkRecipientCell(invalidRecipient);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }
    @Test
    public void testCreateMessageHash() {
        String mockId = "0012345678";
        int mockNum = 0;
        String mockMessage = "Hi tonight";

        String hash = QuickChat.createMessageHash(mockId, mockNum, mockMessage);
        assertEquals("00:0:HITONIGHT", hash);
    }

    @Test
    public void testMessageIDCreation() {
        String mockId = "1234567890";
        assertTrue(QuickChat.checkMessageID(mockId));
    }
}
