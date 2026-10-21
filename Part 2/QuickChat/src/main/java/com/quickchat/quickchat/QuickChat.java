/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.quickchat.quickchat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author nthateng
 */
public class QuickChat {

    private static final String JSON_FILE_NAME = "messages.json";
    private static int totalMessagesSent = 0;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            
            System.out.println("Welcome to QuickChat.");
            while (running) {
                System.out.println("\n--- QuickChat Menu ---");
                System.out.println("Option 1) Send Messages");
                System.out.println("Option 2) Show recently sent messages");
                System.out.println("Option 3) Quit");
                System.out.print("Choose an option (1-3): ");
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1" -> handleSendMessages(scanner);
                    case "2" -> System.out.println(printMessages());
                    case "3" -> {
                        System.out.println("Exiting QuickChat. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please choose 1, 2, or 3.");
                }
            }
        }
    }

    public static void handleSendMessages(Scanner scanner) {
        System.out.print("How many messages would you like to enter? ");
        int numMessages;
        try {
            numMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Returning to menu.");
            return;
        }

        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n--- Entering Message " + i + " of " + numMessages + " ---");

            // Recipient Validation using checkRecipientCell()
            String recipient = "";
            while (true) {
                System.out.print("Enter recipient cell number: ");
                recipient = scanner.nextLine().trim();
                String validationResult = checkRecipientCell(recipient);
                System.out.println(validationResult);
                if (validationResult.contains("successfully captured")) {
                    break;
                }
            }

            // Message Content Validation using checkMessageID() logic/length checks
            String messageText = "";
            while (true) {
                System.out.print("Enter message (max 250 characters): ");
                messageText = scanner.nextLine();
                if (messageText.length() <= 250) {
                    System.out.println("Message ready to send.");
                    break;
                } else {
                    int excess = messageText.length() - 250;
                    System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                }
            }

            // Unique 10-digit Message ID Generation
            Random rand = new Random();
            long messageId = 1000000000L + rand.nextLong(9000000000L);
            String idStr = String.valueOf(messageId);
            
            if (checkMessageID(idStr)) {
                System.out.println("Message ID generated: " + idStr);
            }

            // Message Hash Generation using createMessageHash()
            String messageHash = createMessageHash(idStr, i, messageText);

            // Message Actions using SendMessage()
            String actionResult = SendMessage(scanner);
            System.out.println(actionResult);

            if (actionResult.contains("successfully sent")) {
                totalMessagesSent++;
            } else if (actionResult.contains("delete the message")) {
                String del = scanner.nextLine().trim();
                if (del.equals("0")) {
                    System.out.println("Message deleted successfully.");
                }
            } else if (actionResult.contains("successfully stored")) {
                storeMessage(idStr, messageHash, recipient, messageText);
            }

            // Display Full Details Summary in required order: ID, Hash, Recipient, Message
            System.out.println("\n========================================");
            System.out.println("           MESSAGE SUMMARY             ");
            System.out.println("========================================");
            System.out.println("Message ID  : " + idStr);
            System.out.println("Message Hash: " + messageHash);
            System.out.println("Recipient   : " + recipient);
            System.out.println("Message     : " + messageText);
            System.out.println("========================================");
        }

        System.out.println("\nAll messages processed. Total messages sent: " + returnTotalMessagess());
    }

    /**
     * Ensures message ID is not more than ten characters.
     * @param idStr
     * @return 
     */
    public static boolean checkMessageID(String idStr) {
        return idStr != null && idStr.length() <= 10;
    }

    /**
     * Ensures recipient cell number is no more than ten chars long after international code (+27).
     * @param recipient
     * @return 
     */
    public static String checkRecipientCell(String recipient) {
        if (recipient != null && recipient.startsWith("+27")) {
            String numberPart = recipient.substring(3);
            if (numberPart.length() <= 10) {
                return "Cell phone number successfully captured.";
            }
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    /**
     * Creates and returns the Message Hash.
     * @param idStr
     * @param msgNumber
     * @param messageText
     * @return 
     */
    public static String createMessageHash(String idStr, int msgNumber, String messageText) {
        String firstTwoId = idStr.length() >= 2 ? idStr.substring(0, 2) : idStr;
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        String combinedWords = (firstWord + lastWord).toUpperCase();
        return firstTwoId + ":" + msgNumber + ":" + combinedWords;
    }

    /**
     * Allows the user to choose if they want to send, store, or disregard the message.
     * @param scanner
     * @return 
     */
    public static String SendMessage(Scanner scanner) {
        System.out.println("\nChoose action for this message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter choice (1-3): ");
        String choice = scanner.nextLine().trim();

        return switch (choice) {
            case "1" -> "Message successfully sent.";
            case "2" -> "Press 0 to delete the message.";
            case "3" -> "Message successfully stored.";
            default -> "Invalid choice. Message successfully stored.";
        };
    }

    /**
     * Stores messages in a JSON file.
     * @param idStr
     * @param messageHash
     * @param recipient
     * @param messageText
     */
    public static void storeMessage(String idStr, String messageHash, String recipient, String messageText) {
        try {
            String jsonEntry = String.format("{\n  \"messageId\": \"%s\",\n  \"messageHash\": \"%s\",\n  \"recipient\": \"%s\",\n  \"message\": \"%s\"\n}\n",
                    idStr, messageHash, recipient, messageText);
            
            Files.write(
                    Paths.get(JSON_FILE_NAME),
                    jsonEntry.getBytes(),
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.out.println("Error saving message to JSON file: " + e.getMessage());
        }
    }

    /**
     * Returns all the messages sent/stored while the program is running.
     * @return 
     */
    public static String printMessages() {
        Path path = Paths.get(JSON_FILE_NAME);
        if (!Files.exists(path)) {
            return "Coming Soon. No stored messages found.";
        }
        try {
            List<String> lines = Files.readAllLines(path);
            return String.join("\n", lines);
        } catch (IOException e) {
            return "Error reading messages file.";
        }
    }

    /**
     * Returns the total number of messages sent.
     * @return 
     */
    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }
}