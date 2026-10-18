/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.quickchat.quickchat;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author nthateng
 */
public class QuickChat {

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
                
                String choice = scanner.nextLine();
                
                switch (choice) {
                    case "1" -> handleSendMessages(scanner);
                    case "2" -> System.out.println("Coming Soon.");
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
            numMessages = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Returning to menu.");
            return;
        }

        int totalSent = 0;

        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n--- Entering Message " + i + " of " + numMessages + " ---");

            // Recipient Validation
            String recipient = "";
            while (true) {
                System.out.print("Enter recipient cell number (max 10 chars, start with +27): ");
                recipient = scanner.nextLine();
                if (recipient.length() <= 12 && recipient.startsWith("+27")) {
                    break;
                }
                System.out.println("Invalid recipient! Must be <= 10 characters and contain an international code (+27).");
            }

            // Message Content Validation
            String messageText = "";
            while (true) {
                System.out.print("Enter message (max 250 characters): ");
                messageText = scanner.nextLine();
                if (messageText.length() <= 250) {
                    System.out.println("Message sent");
                    break;
                } else {
                    System.out.println("Please enter a message of less than 250 characters.");
                }
            }

            // Unique 10-digit Message ID Generation
            Random rand = new Random();
            long messageId = 1000000000L + rand.nextLong(9000000000L);
            String idStr = String.valueOf(messageId);

            // Message Hash Generation
            String messageHash = generateMessageHash(idStr, i, messageText);

            // Message Actions
            System.out.println("\nChoose action for this message:");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to send later");
            System.out.print("Enter choice (1-3): ");
            String actionChoice = scanner.nextLine();

            switch (actionChoice) {
                case "1" -> {
                    System.out.println("Message successfully sent");
                    totalSent++;
                }
                case "2" -> {
                    System.out.println("Press 0 to delete the message");
                    String del = scanner.nextLine();
                    if (del.equals("0")) {
                        System.out.println("Message deleted successfully.");
                    }
                }
                case "3" -> 
                {
                    try {
                        String jsonEntry = String.format("{\n  \"messageId\": \"%s\",\n  \"messageHash\": \"%s\",\n  \"recipient\": \"%s\",\n  \"message\": \"%s\"\n}\n",
                                idStr, messageHash, recipient, messageText);
                        
                        java.nio.file.Files.write(
                                java.nio.file.Paths.get("messages.json"),
                                jsonEntry.getBytes(),
                                java.nio.file.StandardOpenOption.CREATE,
                                java.nio.file.StandardOpenOption.APPEND
                        );
                        System.out.println("Message successfully stored to messages.json");
                    } catch (java.io.IOException e) {
                        System.out.println("Error saving message to JSON file: " + e.getMessage());
                    }
                }
                default -> System.out.println("Invalid choice, defaulting to stored.");
            }

            // Display Full Details Summary
            System.out.println("\n========================================");
            System.out.println("           MESSAGE SUMMARY             ");
            System.out.println("========================================");
            System.out.println("Message ID  : " + idStr);
            System.out.println("Message Hash: " + messageHash);
            System.out.println("Recipient   : " + recipient);
            System.out.println("Message     : " + messageText);
            System.out.println("========================================");
        }

        System.out.println("\nAll messages processed. Total messages sent: " + totalSent);
    }

    public static String generateMessageHash(String idStr, int msgNumber, String messageText) {
        String firstTwoId = idStr.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        String combinedWords = (firstWord + lastWord).toUpperCase();
        return firstTwoId + ":" + msgNumber + ":" + combinedWords;
    }
}
