/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe.part.pkg1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.io.FileWriter;
import java.io.IOException;
 
public class Message {
 
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
 
    private static int totalMessagesSent = 0;
    private static List<String> sentMessageDetails = new ArrayList<>();
 
    public Message() {
    }
 
    public String getMessageID() {
        return messageID;
    }
 
    public String getRecipient() {
        return recipient;
    }
 
    public String getMessageText() {
        return messageText;
    }
 
    public String getMessageHash() {
        return messageHash;
    }
 
    public void setDetails(String messageID, String recipient, String messageText, String messageHash) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = messageHash;
    }
 
    public boolean checkMessageID(String messageID) {
        if (messageID.length() <= 10) {
            return true;
        } else {
            return false;
        }
    }
 
    public String checkRecipientCell(String cellNumber) {
        // Regular expression syntax adapted from the official Java Pattern class documentation:
        // https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
        String pattern = "^\\+27\\d{7,9}$";
 
        if (cellNumber.length() <= 12 && Pattern.matches(pattern, cellNumber)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
 
    public String checkMessageLength(String messageText) {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
 
    public String generateMessageID() {
        Random random = new Random();
        long number = (long) (random.nextDouble() * 9000000000L) + 1000000000L;
        return String.valueOf(number);
    }
 
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String idPrefix = messageID.substring(0, 2);
 
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
 
        return idPrefix + ":" + messageNumber + ":" + firstWord + lastWord;
    }
 
    public String sentMessage(int choice) {
        if (choice == 1) {
            totalMessagesSent++;
            sentMessageDetails.add("Message ID: " + messageID
                    + "\nMessage Hash: " + messageHash
                    + "\nRecipient: " + recipient
                    + "\nMessage: " + messageText);
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            return "Message successfully stored.";
        } else {
            return "Invalid option selected.";
        }
    }
 
    public String printMessages() {
        StringBuilder allMessages = new StringBuilder();
 
        for (String details : sentMessageDetails) {
            allMessages.append(details).append("\n\n");
        }
 
        return allMessages.toString();
    }
 
    public int returnTotalMessagesSent() {
        return totalMessagesSent;
    }
 
    // JSON file-writing approach adapted from the official Java FileWriter documentation:
    // https://docs.oracle.com/javase/8/docs/api/java/io/FileWriter.html
    public boolean storeMessageToFile() {
        try {
            FileWriter writer = new FileWriter("messages.json", true);
 
            String json = "{"
                    + "\"messageID\":\"" + messageID + "\","
                    + "\"messageHash\":\"" + messageHash + "\","
                    + "\"recipient\":\"" + recipient + "\","
                    + "\"message\":\"" + messageText.replace("\"", "\\\"") + "\""
                    + "}";
 
            writer.write(json);
            writer.write(System.lineSeparator());
            writer.close();
 
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
