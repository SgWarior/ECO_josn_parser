package com.example.eco_project;

import DiscordClasses.Message;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LinksAndStatistics {
    public static void getAllLinksWithSatistic(ArrayList<Message> messageArrayList) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (Message message : messageArrayList) {

            DiscordUser ds = SaldoTable.discordUserHashMap.get(message.author.id);

            if(message.content.contains("https://docs.google.com/")){
                String link= null;

                for (String s : message.content.split("[ \t\n]")) {
                    if (s.startsWith("https://docs.google.com/"))
                        link= s;
                }

                sb
                        .append(ds.getReviewToUser())
                        .append('\n')
                        .append(link)
                        .append("\n\n");

            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("hiBryce.txt"))) {
            writer.write(sb.toString());
        }
    }
}
