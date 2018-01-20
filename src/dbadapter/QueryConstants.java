package dbadapter;

/**
 * Created by liangchun on 20.01.18.
 */
public final class QueryConstants {
    private QueryConstants() {}
    public static final class GroupQueries {
        private GroupQueries() {}
        public static final String CREATE_GROUP = "INSERT INTO GroupDatabase (groupName, adminId) VALUES (?, ?)";
        public static final String ADD_TO_GROUP = "INSERT INTO GroupMembers(groupName, memberId) VALUES (?, ?)";
        public static final String CHECK_GROUP_EXISTENCE = "SELECT count(*) FROM GroupDatabase WHERE groupName=?;";
        public static final String GET_USER_ID = "SELECT userId FROM Users WHERE userName=?";

    }
}