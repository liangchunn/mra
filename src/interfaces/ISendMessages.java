package interfaces;

import java.sql.Timestamp;

/**
 * Created by Cody on 17.01.18.
 */
public interface ISendMessages {
    boolean sendMessages(String groupName, Integer userId, String message);
}
