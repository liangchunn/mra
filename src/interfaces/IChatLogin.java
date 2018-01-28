package interfaces;

/**
 * Created by Cody on 17.01.18.
 */
public interface IChatLogin {
    /**
     * Check's if a userId is eligible to post or see messages in the given group name
     * @param groupName group name to log into
     * @param userId user id to check
     * @return if a user is eligible for the group
     */
    boolean chatLogin(String groupName, Integer userId);
}
