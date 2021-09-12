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

        for (Message message : messageArrayList) {

            String pointsQuant = null;
            if (message.author.isBot && message.content.contains("currently has :points:")) {

                String pointOwner = message.content.split(", by my calculation, @")[1].split(" currently has")[0];

                Double havePoints =
                        Double.parseDouble(message.content.split("currently has :points:")[1].replaceAll(".$", "").replaceAll(",", ""));

                    leaderBoardMap.put(pointOwner, havePoints);

            }

        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("LeaderBoard.txt"))) {
            for (Map.Entry<String, Double> stringDoubleEntry : leaderBoardMap.entrySet()) {
                writer.write(stringDoubleEntry.getKey() + "\t" + stringDoubleEntry.getValue().toString().replace('.',',')+"\n");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
