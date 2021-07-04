package com.example.eco_project;

import DiscordClasses.Mention;
import DiscordClasses.Message;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SaldoTable {

    private static Set<String> teamID = new HashSet<>(
            Arrays.asList("818215417956925460","344148051973373952","804530443188961300","313479401419309063","768554690480701472"));

    static HashMap<String,DiscordUser> discordUserHashMap = new HashMap<>();

    public static HashMap<String,DiscordUser> createReport(ArrayList<Message> listMessages) throws IOException {
        for (Message message : listMessages) {
            addIfNotExistInUsersList(message);
            updateInfoAboutDiscordAuthor(message);
            if(message.mentions.size()>0)updateInfoAboutMentioned(message);
        }
        writeMapIntoFile("EcoStatsDiscord.txt", discordUserHashMap);
        return discordUserHashMap;

    }

    private static void updateInfoAboutDiscordAuthor(Message message) {
        DiscordUser author = discordUserHashMap.get(message.author.id);

        author.incrementSendMessages();
        author.increaseEmojiPlaced(message.reactions.size());
        author.increaseMake_mentions(message.mentions.size());

        if(message.content.startsWith("!send ")&& message.mentions.size()>0){
            String findNumber = message.content.replace("!send ", "");
            for (int i = 0; i < message.mentions.size(); i++) {
                findNumber.replace(message.mentions.get(i).nickname, "");
            }
            findNumber.trim();
            //---> todo прочитаь следующий за этим сумму поинтов double or Integer
        }
        else if(message.content.startsWith("!points")) author.chekPoints++;

    }

    private static void addIfNotExistInUsersList(Message message) {
        discordUserHashMap.putIfAbsent(message.author.id,
                new DiscordUser(message.author.id, message.author.nickname+"#"+message.author.discriminator));

     //   if (message.mentions!=null || message.mentions.size()>0)
             for (Mention mention : message.mentions) {
            discordUserHashMap.putIfAbsent(mention.id,
                new DiscordUser(mention.id, mention.nickname + mention.discriminator));
        }
    }

    private static void updateInfoAboutMentioned(Message message){
        DiscordUser author = discordUserHashMap.get(message.author.id);

        for (Mention mention : message.mentions) {
            discordUserHashMap.get(mention.id).incrementUserMentioned();

            if(teamID.contains(message.author.id))
                    discordUserHashMap.get(mention.id).incrementWasMentionedBuyTeam();

            if(teamID.contains(mention.id))author.incrementMakeMentionsTeam();
        }
    }

    private static void writeMapIntoFile(String fileName, HashMap<String, DiscordUser> map) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write(
                    "DiscordName\tSentMessage\tMadeMentions\tEmojisPlaced\twasMentioned\tmakeTeamMentions\tWasMentionedByTeam\n");
            for (Map.Entry<String, DiscordUser> userEntry : map.entrySet()) {
                writer.write(userEntry.getValue().toString());
            }
        }
    }

}

