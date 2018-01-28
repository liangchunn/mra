package interfaces;

import extraClasses.MyResult;

/**
 * Created by Cody on 19.01.18.
 */
public interface ILeaveGroup {
    /**
     * Leaves a group for the given userId
     * This command will either
     *      Run `deleteGroup` if the userId is also the adminId of a group
     *          or
     *      Run `removeUser` if the userId is not the adminId of the group
     * @param groupName
     * @param userId
     * @returnt a MyResult Instance with the second parameter 1 if it's a group admin and 2 if it's not a group admin
     */
    MyResult leaveGroup(String groupName, Integer userId);
}
