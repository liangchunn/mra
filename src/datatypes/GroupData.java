package datatypes;

import java.util.ArrayList;

/**
 * Created by liangchun on 14.01.18.
 */
public class GroupData {
    private String groupName;
    private Integer adminId;
    private ArrayList<Integer> memberIds;
    public GroupData(String groupName, Integer adminId, ArrayList<Integer> memberIds) {
        this.groupName = groupName;
        this.adminId = adminId;
        this.memberIds = memberIds;
    }
    public String getGroupName() { return groupName; }
    public Integer getAdminId() { return adminId; }
    public ArrayList<Integer> getMemberIds() { return memberIds; }
}
