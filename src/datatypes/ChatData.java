package datatypes;

import java.sql.Timestamp;

/**
 * Created by Cody on 14.01.18.
 */
public class ChatData {
    private String groupName;
    private String creatorName;
    private Timestamp creationTime;
    private String messageText;

    public ChatData(String groupName, String creatorName, Timestamp creationTime, String message) {
        this.groupName = groupName;
        this.creatorName = creatorName;
        this.creationTime = creationTime;
        this.messageText = message;
    }

    public ChatData(String creatorName, Timestamp creationTime, String message) {
        this.creatorName = creatorName;
        this.creationTime = creationTime;
        this.messageText = message;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getMessageText() {
        return messageText;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }
}
