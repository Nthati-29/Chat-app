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
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    @Test
    public void testGenerateMessageHash() {
        String mockId = "1234567890";
        int mockNum = 1;
        String mockMessage = "Hi thanks for chatting";

        String hash = QuickChat.generateMessageHash(mockId, mockNum, mockMessage);
        assertEquals("12:1:HICHATTING", hash);
    }

    @Test
    public void testMessageLengthValidation() {
        String shortMsg = "Hello World";
        assertTrue(shortMsg.length() <= 250);
    }
}
