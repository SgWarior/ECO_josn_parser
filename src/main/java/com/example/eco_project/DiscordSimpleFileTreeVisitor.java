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

import static com.example.eco_project.EcoProjectApplication.globalListMessages;
import static com.example.eco_project.EcoProjectApplication.pointsLogMessages;

public class DiscordSimpleFileTreeVisitor extends SimpleFileVisitor {

    @SneakyThrows
    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs){
        ArrayList<Message> allMessageList = new ArrayList<>();

        {//parse, cast to json, mapping add all mesage in list
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(file.toString()));                      //parse file
            JSONObject jsonObject = (JSONObject) obj;                                    //cast to Json object
            JSONArray jsonArrayMessages = (JSONArray) jsonObject.get("messages");        //get json Messages
            ObjectMapper mapper = new ObjectMapper();

            for (Object objects : jsonArrayMessages
            ) {
                StringReader reader = new StringReader(((JSONObject) objects).toJSONString());
                Message message = mapper.readValue(reader, Message.class);
                globalListMessages.add(message);
                allMessageList.add(message);
            }
        }
       // Execute.extractTheDataAndWrittenFile(allMessageList, file.toString());
        if(file.toString().contains("points-log")) pointsLogMessages = allMessageList;
        return super.visitFile(file, attrs);
    }

}
