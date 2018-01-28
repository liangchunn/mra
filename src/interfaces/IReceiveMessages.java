package interfaces;

import datatypes.ChatData;

import java.util.ArrayList;

/**
 * Created by Cody on 17.01.18.
 */
public interface IReceiveMessages {
    /**
     * Show all messages from a specified group name
     * @param groupName group name of chats to show
     * @return a list of chat data
     */
    ArrayList<ChatData> showMessages(String groupName);
}
