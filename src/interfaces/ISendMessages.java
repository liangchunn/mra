package interfaces;

/**
 * Created by Cody on 17.01.18.
 */
public interface ISendMessages {
    /**
     * Saves a message from a user to a group
     * @param groupName group name to save to
     * @param userId user that created the message
     * @param message message to send
     * @return if message was successfully saved
     */
    boolean saveMessage(String groupName, Integer userId, String message);
}
