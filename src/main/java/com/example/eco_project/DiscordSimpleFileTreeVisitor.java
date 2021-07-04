package com.example.eco_project;

import DiscordClasses.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class DiscordSimpleFileTreeVisitor extends SimpleFileVisitor {

   private static final File leaderB     = new File("Leader_board" + ".txt");
   private static final File grants      = new File("Grants" + ".txt");
   private static final File allUsersF       = new File("AllUsers" + ".txt");

    @SneakyThrows
    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {

        ArrayList<Message> innerList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(file.toString()));                      //parse file

        JSONObject jsonObject = (JSONObject) obj;                                    //cast to Json object

        JSONArray jsonArrayMessages = (JSONArray) jsonObject.get("messages");        //get json Messages

        ObjectMapper mapper = new ObjectMapper();

        for (Object objects : jsonArrayMessages
        ) {
            StringReader reader = new StringReader(((JSONObject) objects).toJSONString());
            Message message = mapper.readValue(reader, Message.class);
            EcoProjectApplication.listMessages.add(message);
            innerList.add(message);
        }

        Execute.extractTheData(innerList, file.toString());

        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        Execute.extractTheData(EcoProjectApplication.listMessages, "AllInOne.txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(allUsersF))){
            EcoProjectApplication.allUsersAutor.forEach( userID -> {
                        try {
                            writer.write("<@"+ userID+"> ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        return super.postVisitDirectory(dir, exc);
    }

    public DiscordSimpleFileTreeVisitor() throws IOException {    }
}
