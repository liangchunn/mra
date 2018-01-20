package interfaces;

import datatypes.ChatData;
import extraClasses.MyResult;

import java.util.ArrayList;

/**
 * Interface that provides all method to interact with a guest.
 * 
 * @author swe.uni-due.de
 *
 */
public interface UCmds {
	boolean createGroup(String groupName, String adminUserName, ArrayList<String> memberUserNames);
	boolean addUserToGroup(String groupName, String userName);
	boolean chatLogin(String groupName, Integer userId);
	boolean sendMessages(String groupName, Integer userId, String message);
	ArrayList<ChatData> receiveMessages(String groupName);
	MyResult leaveGroup(String groupName, Integer userId);
}
