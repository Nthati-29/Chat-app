/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.quickchat.quickchat;

import com.mycompany.prog5121_poe.Login;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * QuickChat console application integrating Part 1 authentication (Login),
 * Part 2 messaging, and Part 3 parallel arrays, JSON loading, and reports.
 *
 * @author nthateng
 */
public class QuickChat {

    private static final String JSON_FILE_NAME = "messages.json";
    private static final String DEFAULT_SENDER = "+27838968976";
    private static final int MAX_RECORDS = 100;

    private static int totalMessagesSent = 0;
    private static String currentSenderNumber = DEFAULT_SENDER;

    // Parallel arrays populated dynamically without hardcoding
    public static String[] sentMessages = new String[MAX_RECORDS];
    public static String[] sentRecipients = new String[MAX_RECORDS];
    public static String[] sentSenders = new String[MAX_RECORDS];
    public static String[] sentHashes = new String[MAX_RECORDS];
    public static String[] sentIDs = new String[MAX_RECORDS];
    public static int sentCount = 0;

    public static String[] disregardedMessages = new String[MAX_RECORDS];
    public static String[] disregardedRecipients = new String[MAX_RECORDS];
    public static String[] disregardedSenders = new String[MAX_RECORDS];
    public static String[] disregardedHashes = new String[MAX_RECORDS];
    public static String[] disregardedIDs = new String[MAX_RECORDS];
    public static int disregardedCount = 0;

    public static String[] storedMessages = new String[MAX_RECORDS];
    public static String[] storedRecipients = new String[MAX_RECORDS];
    public static String[] storedSenders = new String[MAX_RECORDS];
    public static String[] storedHashes = new String[MAX_RECORDS];
    public static String[] storedIDs = new String[MAX_RECORDS];
    public static int storedCount = 0;

    public static String[] messageHashes = new String[MAX_RECORDS];
    public static String[] messageIDs = new String[MAX_RECORDS];
    public static String[] allMessages = new String[MAX_RECORDS];
    public static String[] allRecipients = new String[MAX_RECORDS];
    public static String[] allSenders = new String[MAX_RECORDS];
    public static String[] allFlags = new String[MAX_RECORDS];
    public static int allCount = 0;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Login authSystem = new Login();
            runAuthenticationFlow(scanner, authSystem);

            boolean running = true;
            System.out.println("\nWelcome to QuickChat.");
            while (running) {
                System.out.println("\n--- QuickChat Menu ---");
                System.out.println("Option 1) Send Messages");
                System.out.println("Option 2) Show recently sent messages");
                System.out.println("Option 3) Quit");
                System.out.println("Option 4) Stored Messages");
                System.out.print("Choose an option (1-4): ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1" -> handleSendMessages(scanner);
                    case "2" -> System.out.println(printMessages());
                    case "3" -> {
                        System.out.println("Exiting QuickChat. Goodbye!");
                        running = false;
                    }
                    case "4" -> handleStoredMessagesMenu(scanner);
                    default -> System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
                }
            }
        }
    }

    public static void runAuthenticationFlow(Scanner scanner, Login authSystem) {
        System.out.println("=========================================");
        System.out.println("        QUICKCHAT - PART 1: LOGIN        ");
        System.out.println("=========================================");

        System.out.print("Enter your First Name: ");
        String fName = scanner.nextLine().trim();

        System.out.print("Enter your Last Name: ");
        String lName = scanner.nextLine().trim();

        String username;
        while (true) {
            System.out.print("\nEnter Username (must contain '_' and be <= 5 chars, e.g., kyl_1): ");
            username = scanner.nextLine().trim();
            if (authSystem.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            }
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }

        String password;
        while (true) {
            System.out.print("\nEnter Password (>= 8 chars, 1 capital, 1 number, 1 special character): ");
            password = scanner.nextLine();
            if (authSystem.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            }
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }

        String cellPhone;
        while (true) {
            System.out.print("\nEnter Cell Phone Number (with international code, e.g., +27838968976): ");
            cellPhone = scanner.nextLine().trim();
            if (authSystem.checkCellPhoneNumber(cellPhone)) {
                System.out.println("Cell phone number successfully added.");
                break;
            }
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        }

        authSystem.registerUser(username, password, cellPhone, fName, lName);
        if (authSystem.getRegisteredPhoneNumber() != null) {
            currentSenderNumber = authSystem.getRegisteredPhoneNumber();
        }

        System.out.println("\n-----------------------------------------");
        System.out.println("            LOGIN FEATURE                ");
        System.out.println("-----------------------------------------");
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter Username: ");
            String inputUser = scanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String inputPass = scanner.nextLine();

            loggedIn = authSystem.loginUser(inputUser, inputPass);
            System.out.println(authSystem.returnLoginStatus(loggedIn));
        }
    }

    public static void resetArrays() {
        sentMessages = new String[MAX_RECORDS];
        sentRecipients = new String[MAX_RECORDS];
        sentSenders = new String[MAX_RECORDS];
        sentHashes = new String[MAX_RECORDS];
        sentIDs = new String[MAX_RECORDS];
        sentCount = 0;

        disregardedMessages = new String[MAX_RECORDS];
        disregardedRecipients = new String[MAX_RECORDS];
        disregardedSenders = new String[MAX_RECORDS];
        disregardedHashes = new String[MAX_RECORDS];
        disregardedIDs = new String[MAX_RECORDS];
        disregardedCount = 0;

        storedMessages = new String[MAX_RECORDS];
        storedRecipients = new String[MAX_RECORDS];
        storedSenders = new String[MAX_RECORDS];
        storedHashes = new String[MAX_RECORDS];
        storedIDs = new String[MAX_RECORDS];
        storedCount = 0;

        messageHashes = new String[MAX_RECORDS];
        messageIDs = new String[MAX_RECORDS];
        allMessages = new String[MAX_RECORDS];
        allRecipients = new String[MAX_RECORDS];
        allSenders = new String[MAX_RECORDS];
        allFlags = new String[MAX_RECORDS];
        allCount = 0;
        totalMessagesSent = 0;
    }

    public static void recordMessage(String sender, String recipient, String content, String flag, String customId) {
        if (allCount >= MAX_RECORDS) {
            return;
        }

        String id = (customId != null && !customId.isBlank()) ? customId : generateMessageID();
        String hash = createMessageHash(id, allCount, content);
        String resolvedSender = (sender != null && !sender.isBlank()) ? sender : currentSenderNumber;

        messageIDs[allCount] = id;
        messageHashes[allCount] = hash;
        allMessages[allCount] = content;
        allRecipients[allCount] = recipient;
        allSenders[allCount] = resolvedSender;
        allFlags[allCount] = flag;
        allCount++;

        if ("Sent".equalsIgnoreCase(flag)) {
            sentIDs[sentCount] = id;
            sentHashes[sentCount] = hash;
            sentMessages[sentCount] = content;
            sentRecipients[sentCount] = recipient;
            sentSenders[sentCount] = resolvedSender;
            sentCount++;
            totalMessagesSent++;
        } else if ("Disregard".equalsIgnoreCase(flag) || "Disregarded".equalsIgnoreCase(flag)) {
            disregardedIDs[disregardedCount] = id;
            disregardedHashes[disregardedCount] = hash;
            disregardedMessages[disregardedCount] = content;
            disregardedRecipients[disregardedCount] = recipient;
            disregardedSenders[disregardedCount] = resolvedSender;
            disregardedCount++;
        } else if ("Stored".equalsIgnoreCase(flag)) {
            storedIDs[storedCount] = id;
            storedHashes[storedCount] = hash;
            storedMessages[storedCount] = content;
            storedRecipients[storedCount] = recipient;
            storedSenders[storedCount] = resolvedSender;
            storedCount++;
        }
    }

    private static String generateMessageID() {
        Random rand = new Random();
        long messageId = 1000000000L + rand.nextLong(9000000000L);
        return String.valueOf(messageId);
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

            String recipient;
            while (true) {
                System.out.print("Enter recipient cell number: ");
                recipient = scanner.nextLine().trim();
                String validationResult = checkRecipientCell(recipient);
                System.out.println(validationResult);
                if (validationResult.contains("successfully captured")) {
                    break;
                }
            }

            String messageText;
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

            String idStr = generateMessageID();
            if (checkMessageID(idStr)) {
                System.out.println("Message ID generated: " + idStr);
            }

            String messageHash = createMessageHash(idStr, i, messageText);

            String actionResult = SendMessage(scanner);
            System.out.println(actionResult);

            if (actionResult.contains("successfully sent")) {
                recordMessage(currentSenderNumber, recipient, messageText, "Sent", idStr);
            } else if (actionResult.contains("delete the message")) {
                String del = scanner.nextLine().trim();
                if (del.equals("0")) {
                    System.out.println("Message deleted successfully.");
                }
                recordMessage(currentSenderNumber, recipient, messageText, "Disregard", idStr);
            } else if (actionResult.contains("successfully stored")) {
                recordMessage(currentSenderNumber, recipient, messageText, "Stored", idStr);
                storeMessage(idStr, messageHash, recipient, messageText);
            }

            System.out.println("\n========================================");
            System.out.println("           MESSAGE SUMMARY             ");
            System.out.println("========================================");
            System.out.println("Message ID  : " + idStr);
            System.out.println("Message Hash: " + messageHash);
            System.out.println("Recipient   : " + recipient);
            System.out.println("Message     : " + messageText);
            System.out.println("========================================");
        }

        System.out.println("\nAll messages processed. Total messages sent: " + returnTotalMessages());
    }

    public static void handleStoredMessagesMenu(Scanner scanner) {
        boolean inSubMenu = true;
        while (inSubMenu) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("0) Read stored messages from JSON file (" + JSON_FILE_NAME + ")");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest message");
            System.out.println("c) Search for a message ID and display recipient and message");
            System.out.println("d) Search for all messages sent or stored for a particular recipient");
            System.out.println("e) Delete a message using the message hash");
            System.out.println("f) Display a report listing full details of all sent messages");
            System.out.println("x) Return to main menu");
            System.out.print("Choose an option: ");

            String subChoice = scanner.nextLine().trim().toLowerCase();

            switch (subChoice) {
                case "0" -> System.out.println(readStoredMessagesFromJSON(JSON_FILE_NAME));
                case "a" -> System.out.println(displaySenderAndRecipientOfStored());
                case "b" -> System.out.println(displayLongestMessage());
                case "c" -> {
                    System.out.print("Enter Message ID to search: ");
                    String targetId = scanner.nextLine().trim();
                    System.out.println(searchByMessageID(targetId));
                }
                case "d" -> {
                    System.out.print("Enter recipient number to search: ");
                    String targetRecipient = scanner.nextLine().trim();
                    System.out.println(searchMessagesByRecipient(targetRecipient));
                }
                case "e" -> {
                    System.out.print("Enter Message Hash to delete: ");
                    String targetHash = scanner.nextLine().trim();
                    System.out.println(deleteMessageByHash(targetHash));
                }
                case "f" -> System.out.println(displaySentMessagesReport());
                case "x" -> inSubMenu = false;
                default -> System.out.println("Invalid option. Choose 0, a-f, or x.");
            }
        }
    }

    /**
     * Reads stored messages from a JSON file into the stored messages array and displays them.
     *
     * Attribution:
     * Source: Baeldung — "Intro to JSON in Java" (org.json JSONArray & JSONObject parsing)
     * URL: https://www.baeldung.com/java-org-json
     * Adapted to read JSON file contents via java.nio.file.Files and populate parallel arrays.
     * @param filePath
     * @return 
     */
    public static String readStoredMessagesFromJSON(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return "JSON file not found: " + filePath;
        }
        try {
            String rawJson = Files.readString(path).trim();
            JSONArray array = new JSONArray(rawJson);
            storedCount = 0;

            StringBuilder output = new StringBuilder("Loaded stored messages from JSON:\n");
            for (int i = 0; i < array.length() && storedCount < MAX_RECORDS; i++) {
                JSONObject item = array.getJSONObject(i);
                String id = item.optString("messageId", generateMessageID());
                String hash = item.optString("messageHash", createMessageHash(id, i, item.optString("message", "")));
                String sender = item.optString("sender", currentSenderNumber);
                String recipient = item.optString("recipient", "");
                String msg = item.optString("message", "");

                storedIDs[storedCount] = id;
                storedHashes[storedCount] = hash;
                storedSenders[storedCount] = sender;
                storedRecipients[storedCount] = recipient;
                storedMessages[storedCount] = msg;
                storedCount++;

                if (findIndexById(id) == -1 && allCount < MAX_RECORDS) {
                    messageIDs[allCount] = id;
                    messageHashes[allCount] = hash;
                    allSenders[allCount] = sender;
                    allRecipients[allCount] = recipient;
                    allMessages[allCount] = msg;
                    allFlags[allCount] = "Stored";
                    allCount++;
                }

                output.append("- [").append(id).append("] To: ").append(recipient)
                      .append(" | Message: ").append(msg).append("\n");
            }
            return output.toString().trim();
        } catch (IOException | JSONException e) {
            return "Error reading JSON file: " + e.getMessage();
        }
    }

    private static int findIndexById(String id) {
        for (int i = 0; i < allCount; i++) {
            if (id != null && id.equals(messageIDs[i])) {
                return i;
            }
        }
        return -1;
    }

    public static String displaySenderAndRecipientOfStored() {
        if (storedCount == 0) {
            return "No stored messages found.";
        }
        StringBuilder sb = new StringBuilder("Stored Messages (Sender & Recipient):\n");
        for (int i = 0; i < storedCount; i++) {
            sb.append((i + 1)).append(". Sender: ").append(storedSenders[i])
              .append(" | Recipient: ").append(storedRecipients[i]).append("\n");
        }
        return sb.toString().trim();
    }

    public static String displayLongestMessage() {
        String longest = "";
        for (int i = 0; i < allCount; i++) {
            if (allMessages[i] != null && allMessages[i].length() > longest.length()) {
                longest = allMessages[i];
            }
        }
        return longest.isEmpty() ? "No messages available." : longest;
    }

    public static String searchByMessageID(String id) {
        for (int i = 0; i < allCount; i++) {
            if (messageIDs[i] != null && messageIDs[i].equals(id)) {
                return "Recipient: " + allRecipients[i] + "\nMessage: " + allMessages[i];
            }
        }
        return "Message ID not found: " + id;
    }

    public static String searchMessagesByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allCount; i++) {
            if (allRecipients[i] != null && allRecipients[i].equals(recipient)
                    && ("Sent".equalsIgnoreCase(allFlags[i]) || "Stored".equalsIgnoreCase(allFlags[i]))) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(allMessages[i]);
            }
        }
        return sb.length() == 0 ? "No sent or stored messages found for recipient: " + recipient : sb.toString();
    }

    public static String deleteMessageByHash(String hash) {
        String deletedText = null;

        for (int i = 0; i < allCount; i++) {
            if (messageHashes[i] != null && messageHashes[i].equals(hash)) {
                deletedText = allMessages[i];
                for (int j = i; j < allCount - 1; j++) {
                    messageHashes[j] = messageHashes[j + 1];
                    messageIDs[j] = messageIDs[j + 1];
                    allMessages[j] = allMessages[j + 1];
                    allRecipients[j] = allRecipients[j + 1];
                    allSenders[j] = allSenders[j + 1];
                    allFlags[j] = allFlags[j + 1];
                }
                allCount--;
                break;
            }
        }

        for (int i = 0; i < storedCount; i++) {
            if (storedHashes[i] != null && storedHashes[i].equals(hash)) {
                if (deletedText == null) {
                    deletedText = storedMessages[i];
                }
                for (int j = i; j < storedCount - 1; j++) {
                    storedHashes[j] = storedHashes[j + 1];
                    storedIDs[j] = storedIDs[j + 1];
                    storedMessages[j] = storedMessages[j + 1];
                    storedRecipients[j] = storedRecipients[j + 1];
                    storedSenders[j] = storedSenders[j + 1];
                }
                storedCount--;
                break;
            }
        }

        for (int i = 0; i < sentCount; i++) {
            if (sentHashes[i] != null && sentHashes[i].equals(hash)) {
                if (deletedText == null) {
                    deletedText = sentMessages[i];
                }
                for (int j = i; j < sentCount - 1; j++) {
                    sentHashes[j] = sentHashes[j + 1];
                    sentIDs[j] = sentIDs[j + 1];
                    sentMessages[j] = sentMessages[j + 1];
                    sentRecipients[j] = sentRecipients[j + 1];
                    sentSenders[j] = sentSenders[j + 1];
                }
                sentCount--;
                break;
            }
        }

        if (deletedText != null) {
            return "Message: \"" + deletedText + "\" successfully deleted.";
        }
        return "Message with hash \"" + hash + "\" not found.";
    }

    public static String displaySentMessagesReport() {
        if (sentCount == 0) {
            return "No sent messages available for report.";
        }
        StringBuilder sb = new StringBuilder("=== SENT MESSAGES REPORT ===\n");
        for (int i = 0; i < sentCount; i++) {
            if (i > 0) {
                sb.append("----------------------------\n");
            }
            sb.append("Message Hash: ").append(sentHashes[i]).append("\n")
              .append("Recipient   : ").append(sentRecipients[i]).append("\n")
              .append("Message     : ").append(sentMessages[i]).append("\n");
        }
        return sb.toString().trim();
    }

    public static boolean checkMessageID(String idStr) {
        return idStr != null && idStr.length() <= 10;
    }

    public static String checkRecipientCell(String recipient) {
        if (recipient != null && recipient.startsWith("+27")) {
            String numberPart = recipient.substring(3);
            if (numberPart.matches("\\d{9,10}")) {
                return "Cell phone number successfully captured.";
            }
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public static String createMessageHash(String idStr, int msgNumber, String messageText) {
        String firstTwoId = (idStr != null && idStr.length() >= 2) ? idStr.substring(0, 2) : (idStr != null ? idStr : "");

        if (messageText == null || messageText.trim().isEmpty()) {
            return firstTwoId + ":" + msgNumber + ":";
        }

        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String combinedWords = (firstWord + lastWord).toUpperCase();
        return firstTwoId + ":" + msgNumber + ":" + combinedWords;
    }

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

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
}
