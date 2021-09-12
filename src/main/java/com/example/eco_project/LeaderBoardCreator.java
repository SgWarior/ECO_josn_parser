package com.example.eco_project;

import DiscordClasses.Message;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderBoardCreator {

    public static void createLeaderBoard(ArrayList<Message> messageArrayList){
        HashMap<String, Double> leaderBoardMap = new HashMap<>();
        HashMap<String, String> userNickName = new HashMap<>();


        for (Message message : messageArrayList) {
            String pointsQuant = null;
            if (message.author.isBot && message.content.contains("currently has :points:")) {

                pointsQuant = message.content.split("currently has :points:")[1].replaceAll(".$", "");

                Double havePoints = Double.parseDouble(pointsQuant.replaceAll(",", ""));

                if (message.content.contains(", @" + message.mentions.get(0).nickname)||message.mentions.size()==1) {
                    leaderBoardMap.put(message.mentions.get(0).id, havePoints);
                    userNickName.put(message.mentions.get(0).id, message.mentions.get(0).nickname+message.mentions.get(0).discriminator);
                }
                else
                {
                    leaderBoardMap.put(message.mentions.get(1).id, havePoints);
                    userNickName.put(message.mentions.get(1).id, message.mentions.get(1).nickname+message.mentions.get(1).discriminator);
                }

            }

        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("LeaderBoard.txt"))) {
            for (Map.Entry<String, Double> stringDoubleEntry : leaderBoardMap.entrySet()) {
                writer.write(userNickName.get(stringDoubleEntry.getKey()) + "\t" + stringDoubleEntry.getValue()+"\n");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(messageArrayList.size());
    }


}
