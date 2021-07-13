package com.example.eco_project;

import DiscordClasses.Mention;
import DiscordClasses.Message;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

import static com.example.eco_project.SaldoTable.discordUserHashMap;

public class CrossTransfers {

    private static Set<String> usersInPointLog = new HashSet<>(300);

    static TreeMap<String, TreeMap<String, Double>> tableEntity = new TreeMap<>();

    public CrossTransfers() {
    }

    private static void fillSetWithUsers(ArrayList<Message> allMessageList) {
        //список всех упомянуты юзверей
        for (Message message : allMessageList) {
            for (Mention mention : message.mentions) {
                if (message.content.contains("sent :points:"))usersInPointLog.add(mention.id);

            }
        }
    }

    private static void fillTreemap(ArrayList<Message> allMessageList) {

        //каждому юзеру по своему кортежу
        for (String user : usersInPointLog) {
            TreeMap<String, Double> cortege = new TreeMap<>();
            for (String userID : usersInPointLog) {
                cortege.put(userID, 0.0);
            }
            //заполнение базового пустого дерева -кортеж
            tableEntity.put(user, cortege);
        }
    }

    public static void getTransactions(ArrayList<Message> allMessageList) {

        fillSetWithUsers(allMessageList);
        fillTreemap(allMessageList);

        for (Message message : allMessageList) {
            String donor = null;
            int i = 0;
            String content = message.content.replace("@", "");
            Double amount = Double.parseDouble(content.split(":points:")[1].split(" ")[0].replace(",", ""));

            while (i < message.mentions.size()) {
                if (content.startsWith(message.mentions.get(i).name)) {

                    donor = message.mentions.get(i).id;
                    message.mentions.remove(i);
                }
                i++;
            }

            if (message.content.contains("sent :points:")) {
                for (Mention recipient : message.mentions) {
                    tableEntity.get(donor).merge(recipient.id, -amount, Double::sum);
                    tableEntity.get(recipient.id).merge(donor, amount, Double::sum);
                    tableEntity.get(donor).merge(donor, -amount, Double::sum);
                    tableEntity.get(recipient.id).merge(recipient.id, amount, Double::sum);
                }
            }
           // else for (Mention recipient : message.mentions)
        }

        reportTableInFile(tableEntity);
    }

    @SneakyThrows
    public static void reportTableInFile(TreeMap<String, TreeMap<String, Double>> tableEntity) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ExcelTablePoints.txt"))) {

            StringBuilder stringBuilder = new StringBuilder().append('\t');
            for (Map.Entry<String, TreeMap<String, Double>> stringTreeMapEntry : tableEntity.entrySet()) {

                String userName = discordUserHashMap.get(stringTreeMapEntry.getKey()).getDiscordNameAndDiscriminator();

                stringBuilder.append(userName).append('\t');
            }
            stringBuilder.append('\n');
            writer.write(stringBuilder.toString());

            StringBuilder sb = new StringBuilder();

            for (
                    Map.Entry<String, TreeMap<String, Double>> stringTreeMapEntry : tableEntity.entrySet()) {
                String userName = discordUserHashMap.get(stringTreeMapEntry.getKey()).getDiscordNameAndDiscriminator();
                sb.append(userName).append('\t');

                for (Map.Entry<String, Double> stringDoubleEntry : stringTreeMapEntry.getValue().entrySet()) {
                    if (stringDoubleEntry.getValue()==0.0) sb.append(" \t");
                    else sb.append(stringDoubleEntry.getValue().toString().replace('.',',')).append('\t');
                }
                sb.append('\n');

            }
            writer.write(sb.toString());
        }
    }
}





