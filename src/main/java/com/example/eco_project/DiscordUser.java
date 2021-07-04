package com.example.eco_project;

import java.util.Objects;

public class DiscordUser {

    private String ID;
    private String discordNameAndDiscriminator;

    private int sendMessages         = 0;
    private int make_mentions        = 0;
    private int emojisPlaced         = 0;

    private int wasMentioned         = 0;
    private int makeMentionsTeam     = 0;
    private int wasMentionedBuyTeam  = 0;

    Double balance=0.0;
    int chekPoints = 0;
    double pointsGranted = 0.0;
    double pointsReceived = 0.0;

    public String getID() {
        return ID;
    }

    public void incrementUserMentioned() {
        this.wasMentioned++;
    }

    public void incrementSendMessages() {
        this.sendMessages++;
    }

    public void increaseMake_mentions(int value) {
        this.make_mentions+=value;
    }

    public void incrementMakeMentionsTeam() {
        this.makeMentionsTeam++;
    }

    public void incrementWasMentionedBuyTeam() {
        this.wasMentionedBuyTeam++;
    }

    public void increaseEmojiPlaced(int emojiPlaced) {
        this.emojisPlaced += emojiPlaced;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscordUser that = (DiscordUser) o;
        return ID.equals(that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    public DiscordUser(String ID, String discordNameAndDiscriminator) {
        this.discordNameAndDiscriminator = discordNameAndDiscriminator;
        this.ID = ID;
    }

    @Override
    public String toString() {
        return
                discordNameAndDiscriminator + '\t' +
                        sendMessages + '\t'+
                        make_mentions + '\t'+
                        emojisPlaced + '\t'+
                        wasMentioned + '\t'+
                        makeMentionsTeam + '\t'+
                        wasMentionedBuyTeam+'\n';
    }
}
