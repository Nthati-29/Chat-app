package com.quickchat.quickchat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for QuickChat (Part 2 and Part 3).
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
        QuickChat.resetArrays();
        // Test Data Message 1
        QuickChat.recordMessage("+27838968976", "+27834557896", "Did you get the cake?", "Sent", "1000000001");
        // Test Data Message 2
        QuickChat.recordMessage("+27838968976", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", "1000000002");
        // Test Data Message 3
        QuickChat.recordMessage("+27838968976", "+27834484567", "Yohoooo, I am at your gate.", "Disregard", "1000000003");
        // Test Data Message 4
        QuickChat.recordMessage("0838884567", "0838884567", "It is dinner time !", "Sent", "0838884567");
        // Test Data Message 5
        QuickChat.recordMessage("+27838968976", "+27838884567", "Ok, I am leaving without you.", "Stored", "1000000005");
    }

    @AfterEach
    public void tearDown() {
    }

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
        int mockNum = 1;
        String mockMessage = "Hi tonight";

        String hash = QuickChat.createMessageHash(mockId, mockNum, mockMessage);
        assertEquals("00:1:HITONIGHT", hash);
    }

    @Test
    public void testMessageIDCreation_Success() {
        String mockId = "1234567890";
        assertTrue(QuickChat.checkMessageID(mockId));
    }

    @Test
    public void testMessageIDCreation_Failure() {
        String mockId = "12345678901";
        assertFalse(QuickChat.checkMessageID(mockId));
    }

    // --- Part 3 Unit Tests ---

    @Test
    public void testSentMessagesArrayPopulated() {
        String actual = QuickChat.sentMessages[0] + ", " + QuickChat.sentMessages[1];
        assertEquals("Did you get the cake?, It is dinner time !", actual);
    }

    @Test
    public void testDisplayLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.", QuickChat.displayLongestMessage());
    }

    @Test
    public void testSearchForMessageID() {
        assertEquals("Recipient: 0838884567\nMessage: It is dinner time !", QuickChat.searchByMessageID("0838884567"));
    }

    @Test
    public void testSearchMessagesForRecipient() {
        String expected = "Where are you? You are late! I have asked you to be on time.\nOk, I am leaving without you.";
        assertEquals(expected, QuickChat.searchMessagesByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        String msg2Hash = QuickChat.messageHashes[1];
        assertEquals(
                "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                QuickChat.deleteMessageByHash(msg2Hash)
        );
    }

    @Test
    public void testDisplayReport() {
        String expected = ("=== SENT MESSAGES REPORT ===\n"
                + "Message Hash: " + QuickChat.sentHashes[0] + "\n"
                + "Recipient   : +27834557896\n"
                + "Message     : Did you get the cake?\n"
                + "----------------------------\n"
                + "Message Hash: " + QuickChat.sentHashes[1] + "\n"
                + "Recipient   : 0838884567\n"
                + "Message     : It is dinner time !").replace("\r\n", "\n").trim();

        String actual = QuickChat.displaySentMessagesReport().replace("\r\n", "\n").trim();
        assertEquals(expected, actual);
    }
}