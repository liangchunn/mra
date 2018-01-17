package interfaces;

import datatypes.ChatData;
import java.util.ArrayList;

/**
 * Interface that provides all method to interact with a guest.
 * 
 * @author swe.uni-due.de
 *
 */
public interface UCmds {
	boolean createGroup(String groupName, Integer adminId, ArrayList<Integer> memberIds);
	boolean addUserToGroup(String groupName, Integer userId);

	boolean chatLogin(String groupName, Integer userId);

	boolean sendMessages(String groupName, Integer userId, String message);

	ArrayList<ChatData> receiveMessages(String groupName);
}
