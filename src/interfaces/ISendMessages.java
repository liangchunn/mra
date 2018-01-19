package interfaces;

/**
 * Created by Cody on 17.01.18.
 */
public interface ISendMessages {
    boolean saveMessage(String groupName, Integer userId, String message);
}
