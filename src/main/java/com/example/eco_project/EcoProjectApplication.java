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

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(EcoProjectApplication.class, args);

     // JSONParser parser = new JSONParser();
     // Object obj = parser.parse(new FileReader("src/main/resources/Jsons/Eco.json"));
     // JSONObject jsonObject = (JSONObject) obj;
     // JSONArray jsonArrayMessages = (JSONArray) jsonObject.get("messages");
     // ObjectMapper mapper = new ObjectMapper();
     // for (Object objec : jsonArrayMessages
     //      ) {
     //     StringReader reader = new StringReader(((JSONObject) objec).toJSONString());
     //     Message message = mapper.readValue(reader, Message.class);
     //     listMessages.add(message);
     // }

        Files.walkFileTree(Path.of("src/main/resources/Jsons/ECO"),new DiscordSimpleFileTreeVisitor());
        SaldoTable.createReport(globalListMessages);
        CrossTransfers.getTransactions(pointsLogMessages);

        }
}


