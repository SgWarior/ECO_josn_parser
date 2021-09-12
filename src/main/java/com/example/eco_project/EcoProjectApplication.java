package com.example.eco_project;

import DiscordClasses.Message;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@SpringBootApplication
public class EcoProjectApplication {

    public static ArrayList<Message> globalListMessages = new ArrayList<>(100000);
    public static  ArrayList<Message> pointsLogMessages = new ArrayList<>();
    public static  ArrayList<Message> chekPointslist = new ArrayList<>();
    public static  HashSet<String> AllUsersMadetTranz = new HashSet<>();

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(EcoProjectApplication.class, args);

        Files.walkFileTree(Path.of("src/main/resources/Jsons/ECO"),new DiscordSimpleFileTreeVisitor());
        SaldoTable.createReport(globalListMessages);
        CrossTransfers.getTransactions(pointsLogMessages);
        CrossTransfers.getAllUsersWhatMadeATransaction(pointsLogMessages);
        LeaderBoardCreator.createLeaderBoard(chekPointslist);

        for (String s : AllUsersMadetTranz) {
            System.out.print(s+" ");
        }


    }
}


