package interfaces;

import java.util.ArrayList;

/**
 * Created by liangchun on 14.01.18.
 */
public interface ICreateGroup {
    boolean createGroup(String groupName, String adminUserName, ArrayList<String> memberUserNames);
}
