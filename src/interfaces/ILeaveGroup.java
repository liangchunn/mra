package interfaces;

import extraClasses.MyResult;

/**
 * Created by Cody on 19.01.18.
 */
public interface ILeaveGroup {
    MyResult leaveGroup(String groupName, Integer userId);
}
