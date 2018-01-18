package interfaces;

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
}
