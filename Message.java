/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe.part.pkg1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
 
public class Message {
 
    private String messageID;
    private String sender;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String flag;
 
    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> disregardedMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();
    private static List<String> messageHashes = new ArrayList<>();
    private static List<String> messageIDs = new ArrayList<>();
    private static int messageCount = 0;
 
    public Message() {
    }
 
    public String getMessageID() {
        return messageID;
    }
 
    public String getSender() {
        return sender;
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
 
    public String getFlag() {
        return flag;
    }
 
    public List<Message> getSentMessages() {
        return sentMessages;
    }
 
    public List<Message> getDisregardedMessages() {
        return disregardedMessages;
    }
 
    public List<Message> getStoredMessages() {
        return storedMessages;
    }
 
    public List<String> getMessageHashes() {
        return messageHashes;
    }
 
    public List<String> getMessageIDs() {
        return messageIDs;
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
 
    public void addMessage(String sender, String recipient, String messageText, String flag) {
        String newID = generateMessageID();
        addMessage(newID, sender, recipient, messageText, flag);
    }
 
    public void addMessage(String messageID, String sender, String recipient, String messageText, String flag) {
        this.messageID = messageID;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.flag = flag;
 
        messageCount++;
        this.messageHash = createMessageHash(messageID, messageCount, messageText);
 
        messageIDs.add(messageID);
        messageHashes.add(messageHash);
 
        if (flag.equals("Sent")) {
            sentMessages.add(this);
        } else if (flag.equals("Disregard")) {
            disregardedMessages.add(this);
        } else if (flag.equals("Stored")) {
            storedMessages.add(this);
        }
    }
 
    public String printMessages() {
        StringBuilder allMessages = new StringBuilder();
 
        for (Message m : sentMessages) {
            allMessages.append("Message ID: ").append(m.messageID)
                    .append("\nMessage Hash: ").append(m.messageHash)
                    .append("\nRecipient: ").append(m.recipient)
                    .append("\nMessage: ").append(m.messageText)
                    .append("\n\n");
        }
 
        return allMessages.toString();
    }
 
    public int returnTotalMessagesSent() {
        return sentMessages.size();
    }
 
    public boolean storeMessageToFile() {
        try {
            // JSON file-writing approach adapted from the official Java FileWriter documentation:
            // https://docs.oracle.com/javase/8/docs/api/java/io/FileWriter.html
            FileWriter writer = new FileWriter("messages.json", true);
 
            String json = "{"
                    + "\"messageID\":\"" + messageID + "\","
                    + "\"sender\":\"" + sender + "\","
                    + "\"recipient\":\"" + recipient + "\","
                    + "\"message\":\"" + messageText.replace("\"", "\\\"") + "\","
                    + "\"messageHash\":\"" + messageHash + "\""
                    + "}";
 
            writer.write(json);
            writer.write(System.lineSeparator());
            writer.close();
 
            return true;
        } catch (IOException e) {
            return false;
        }
    }
 
    private String extractJsonValue(String line, String key) {
        String pattern = "\"" + key + "\":\"([^\"]*)\"";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);
 
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }
 
    public void loadStoredMessagesFromFile() {
        // Reading a text file line by line and pulling out values with regex,
        // adapted from common Java file-reading guides (see reference in project README).
        storedMessages.clear();
 
        try {
            BufferedReader reader = new BufferedReader(new FileReader("messages.json"));
            String line;
 
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
 
                Message loaded = new Message();
                loaded.messageID = extractJsonValue(line, "messageID");
                loaded.sender = extractJsonValue(line, "sender");
                loaded.recipient = extractJsonValue(line, "recipient");
                loaded.messageText = extractJsonValue(line, "message");
                loaded.messageHash = extractJsonValue(line, "messageHash");
                loaded.flag = "Stored";
 
                storedMessages.add(loaded);
            }
 
            reader.close();
        } catch (IOException e) {
            System.out.println("No stored messages file found yet.");
        }
    }
 
    private void saveAllStoredMessagesToFile() {
        try {
            FileWriter writer = new FileWriter("messages.json", false);
 
            for (Message m : storedMessages) {
                String json = "{"
                        + "\"messageID\":\"" + m.messageID + "\","
                        + "\"sender\":\"" + m.sender + "\","
                        + "\"recipient\":\"" + m.recipient + "\","
                        + "\"message\":\"" + m.messageText.replace("\"", "\\\"") + "\","
                        + "\"messageHash\":\"" + m.messageHash + "\""
                        + "}";
 
                writer.write(json);
                writer.write(System.lineSeparator());
            }
 
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not update the stored messages file.");
        }
    }
 
    public String displayLongestMessage() {
        List<Message> everyMessage = new ArrayList<>();
        everyMessage.addAll(sentMessages);
        everyMessage.addAll(storedMessages);
        everyMessage.addAll(disregardedMessages);
 
        if (everyMessage.isEmpty()) {
            return "There are no messages.";
        }
 
        String longest = everyMessage.get(0).messageText;
        for (Message m : everyMessage) {
            if (m.messageText.length() > longest.length()) {
                longest = m.messageText;
            }
        }
        return longest;
    }
 
    public String searchMessageByID(String id) {
        List<Message> everyMessage = new ArrayList<>();
        everyMessage.addAll(sentMessages);
        everyMessage.addAll(storedMessages);
        everyMessage.addAll(disregardedMessages);
 
        for (Message m : everyMessage) {
            if (m.messageID.equals(id)) {
                return m.messageText;
            }
        }
        return "No message found with that message ID.";
    }
 
    public String searchMessagesByRecipient(String recipient) {
        List<Message> sentAndStored = new ArrayList<>();
        sentAndStored.addAll(sentMessages);
        sentAndStored.addAll(storedMessages);
 
        StringBuilder result = new StringBuilder();
        for (Message m : sentAndStored) {
            if (m.recipient.equals(recipient)) {
                result.append(m.messageText).append(" ");
            }
        }
 
        if (result.length() == 0) {
            return "No messages found for that recipient.";
        } else {
            return result.toString().trim();
        }
    }
 
    public String displayStoredSendersAndRecipients() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }
 
        StringBuilder result = new StringBuilder();
        for (Message m : storedMessages) {
            result.append("Sender: ").append(m.sender)
                    .append(", Recipient: ").append(m.recipient)
                    .append("\n");
        }
        return result.toString();
    }
 
    public String displayLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }
 
        String longest = storedMessages.get(0).messageText;
        for (Message m : storedMessages) {
            if (m.messageText.length() > longest.length()) {
                longest = m.messageText;
            }
        }
        return longest;
    }
 
    public String searchStoredMessageByID(String id) {
        for (Message m : storedMessages) {
            if (m.messageID.equals(id)) {
                return "Recipient: " + m.recipient + ", Message: " + m.messageText;
            }
        }
        return "No stored message found with that message ID.";
    }
 
    public String searchStoredMessagesByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();
 
        for (Message m : storedMessages) {
            if (m.recipient.equals(recipient)) {
                result.append(m.messageText).append(" ");
            }
        }
 
        if (result.length() == 0) {
            return "No stored messages found for that recipient.";
        } else {
            return result.toString().trim();
        }
    }
 
    public String deleteStoredMessageByHash(String hash) {
        Message toRemove = null;
 
        for (Message m : storedMessages) {
            if (m.messageHash.equals(hash)) {
                toRemove = m;
                break;
            }
        }
 
        if (toRemove != null) {
            String text = toRemove.messageText;
            storedMessages.remove(toRemove);
            saveAllStoredMessagesToFile();
            return "Message: \"" + text + "\" successfully deleted.";
        } else {
            return "No stored message found with that hash.";
        }
    }
 
    public String displayStoredMessagesReport() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }
 
        StringBuilder report = new StringBuilder();
        for (Message m : storedMessages) {
            report.append("Message ID: ").append(m.messageID)
                    .append("\nMessage Hash: ").append(m.messageHash)
                    .append("\nSender: ").append(m.sender)
                    .append("\nRecipient: ").append(m.recipient)
                    .append("\nMessage: ").append(m.messageText)
                    .append("\n\n");
        }
        return report.toString();
    }
}
