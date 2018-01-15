package interfaces;

import java.util.ArrayList;

/**
 * Created by liangchun on 14.01.18.
 */
public interface ICreateGroup {
    boolean createGroup(String groupName, Integer adminId, ArrayList<Integer> memberIds);
}
