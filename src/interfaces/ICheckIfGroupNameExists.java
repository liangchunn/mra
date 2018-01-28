package interfaces;

/**
 * Created by liangchun on 14.01.18.
 */
public interface ICheckIfGroupNameExists {
    /**
     * Checks if a given group name exists
     * @param groupName the group name to check
     * @return whether if a group exists
     */
    boolean checkIfGroupNameExists(String groupName);
}
