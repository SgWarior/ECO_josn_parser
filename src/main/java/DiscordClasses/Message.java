package DiscordClasses;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
