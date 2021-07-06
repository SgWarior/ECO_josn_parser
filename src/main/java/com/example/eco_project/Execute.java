package com.example.eco_project;

import DiscordClasses.Mention;
import DiscordClasses.Message;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Execute {

    @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Contract(pure = true)

    public static ArrayList<String> mentionedInThisMessage(Message message) {
        ArrayList<String> listMentioned= new ArrayList<>();
        for (Mention mention : message.mentions) {
           listMentioned.add(mention.nickname+"#"+ mention.discriminator);
        }
        return listMentioned;
    }

    public static String EcoStoryBotSends(ArrayList<Message> listMessages){
        String ecoStoryBot = "840506182018793493";

        String pattern = "( \\d*\\.?\\d* ):points:";

        Pattern ptrn = Pattern.compile(pattern);

        Matcher matcher;

        for (Message message : listMessages) {
            if(message.author.id.equals(ecoStoryBot)&& message.content.contains("!send"))
            { matcher = ptrn.matcher(message.content);
                if (matcher.find())
                    System.out.println(mentionedInThisMessage(message).get(0)+matcher.group(1));}
        }
        return null;
    }

   /* @SneakyThrows
    public static void extractTheDataAndWrittenFile(ArrayList<Message> listMessages, String fileNameOut) {
        TreeMap<String, Integer> mapGranted= new TreeMap<>();

        String pattern = " (\\d+) ";

        Pattern patrn = Pattern.compile(pattern);

        Matcher matcher;

        for (Message message : listMessages) {
            if (EcoProjectApplication.teamID.contains(message.author.id) && message.content.contains("!grant"))
            {
                matcher = patrn.matcher(message.content);
                if (matcher.find()){
                    Integer value = Integer.parseInt(matcher.group(1));
                for (String s : mentionedInThisMessage(message)) {
                    mapGranted.merge(s,value, Integer::sum);
                }}
            }
        }

        File file = new File(fileNameOut);
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(file.getName().replace(".json", ".txt")))){
        mapGranted.forEach( (s, value) -> {
                    try {
                        writer.write(s+"\t"+ value.toString()+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
       }
    }
   */
}

