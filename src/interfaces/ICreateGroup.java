package interfaces;

import java.util.ArrayList;

/**
 * Created by liangchun on 14.01.18.
 */
public interface ICreateGroup {
    /**
     * Creates a group with the groupName, admin's username and member user names
     * @param groupName new groupname to create
     * @param adminUserName admin's username
     * @param memberUserNames member's user names
     * @return if the command is successfully executed
     */
    boolean createGroup(String groupName, String adminUserName, ArrayList<String> memberUserNames);
}
