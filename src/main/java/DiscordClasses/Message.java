package DiscordClasses;

import java.util.Date;
import java.util.List;

public class Message{
    public String id;
    public String type;
    public Date timestamp;
    public Date timestampEdited;
    public Object callEndedTimestamp;
    public boolean isPinned;
    public String content;
    public Author author;
    public List<Attachment> attachments;
    public List<Embed> embeds;
    public List<Reaction> reactions;
    public List<Mention> mentions;
    public Reference reference;
}
