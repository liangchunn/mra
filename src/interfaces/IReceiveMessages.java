package interfaces;

import datatypes.ChatData;

import java.util.ArrayList;

/**
 * Created by Cody on 17.01.18.
 */
public interface IReceiveMessages {
    ArrayList<ChatData> showMessages(String groupName);
}
