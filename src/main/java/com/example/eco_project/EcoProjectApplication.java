package com.example.eco_project;

import DiscordClasses.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.*;


@SpringBootApplication
public class EcoProjectApplication {

    public static ArrayList<Message> listMessages = new ArrayList<>();
    public static Set<String> allUsersAutor = new HashSet<>();
    public static Set<String> teamID = new HashSet<>(
            Arrays.asList("818215417956925460","344148051973373952","804530443188961300","313479401419309063","768554690480701472"));
                            //@Bryce  | Eco#0263   @Dave | Eco#2900     @Hen | Eco#7458   @Henry  |  Eco#9415    @Andy | Eco#0001

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(EcoProjectApplication.class, args);

        Path start= Paths.get("src/main/resources/Jsons");

        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader("src/main/resources/Jsons/Eco.json"));

        JSONObject jsonObject = (JSONObject) obj;

        JSONArray jsonArrayMessages = (JSONArray) jsonObject.get("messages");

        ObjectMapper mapper = new ObjectMapper();

        for (Object objec : jsonArrayMessages
             ) {
            StringReader reader = new StringReader(((JSONObject) objec).toJSONString());
            Message message = mapper.readValue(reader, Message.class);
            listMessages.add(message);
        }

        Files.walkFileTree(start,new DiscordSimpleFileTreeVisitor());
        SaldoTable.createReport(listMessages);

        }
}


