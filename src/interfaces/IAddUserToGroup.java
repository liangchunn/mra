package interfaces;

/**
 * Created by liangchun on 14.01.18.
 */
public interface IAddUserToGroup {
    /**
     * Adds a user to a group by group name and username
     * @param groupName the group name to add to
     * @param userName the user name to add to the group
     * @return if the command was successful
     */
    boolean addUserToGroup(String groupName, String userName);
}
